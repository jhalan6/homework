package csh.park.car;

import csh.park.console.Console;
import csh.park.data.Employee;
import csh.park.data.LocationOfPark;
import csh.park.park.Park;
import csh.park.data.PublicData;

import java.util.*;

import static csh.park.data.PublicData.midTime;
import static csh.park.data.PublicData.print;

/**
 * Car类是一个车辆的表示方法,继承了线程类,用多线程技术完成每一辆车的控制,在run方法中完成车辆的种种行为,进入停车场,校验员工,泊车,出停车场
 * 同时在一些可能产生冲突的地方设置了避免冲突的算法.
 * Created by Alan on 15/12/9.
 */
public class Car extends Thread {
    private static int count = 0;
    private final Employee inData, outData;
    private final Park park;
    private final PublicData publicData;
    private final Console console;
    private final int time;

    public int getNumber() {
        return number;
    }

    private final int number;
    private int carTailX;
    private int carTailY;
    private int carHeadX;
    private int carHeadY;

    public int getCarTailX() {
        return carTailX;
    }

    public int getCarTailY() {
        return carTailY;
    }

    public int getCarHeadX() {
        return carHeadX;
    }

    public int getCarHeadY() {
        return carHeadY;
    }

    private int line = 2;
    private boolean beenHandled = false;
    private int conflictCount;
    private Random random;

    public Car(Employee outData, Employee inData, int time) {
        this.outData = outData;
        this.inData = inData;
        this.publicData = PublicData.getPublicData();
        this.time = time;
        console = publicData.getConsole();
        park = publicData.getPark();
        number = count++;
        conflictCount = 0;
        random = new Random();
        initCarUI();
    }

    @Override
    public void run() {
        super.run();
        carGoTo(2, 0);//走到门口
        carGoTo(2, 1);
        if (console.getLeft() > 0 && park.in(this)) {
            parkingCar();//停车找车位
//            print.println("汽车"+number+"在停车场内,将要停"+time%PublicData.midTime+"秒");
            mySleep(time);
//            print.println("汽车"+number+"将要驶出停车场");
            while (!moveToRearDoor()) {
                exponentialBackoff();
            }
            if (park.out(this)) {
                leavePark();
            } else {
                backToPark();
                waitHandleAndOut();
            }
        } else {
            carCanNotIn();
        }
    }

    /**
     * 这段关键代码完成了车辆进入停车场后,进入具体车尾的过程.
     */
    private void parkingCar() {
//        print.println("汽车"+number+"进入停车场停车");
        carGoTo(2, 2);
        line = carHeadY;
        while (true) {
            //为了避免进程阻塞,用新线程通知停车场,车辆已经进入停车场
            if (line == 3) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        park.carHadBeenIn();
                    }
                }.start();
            }
            //如果没有车位就向前一格看下一格的车位状况
            if ((line < publicData.getN() + 3) && !park.getStatus(1, line) && !park.getStatus(3, line)) {
                line += 1;
                if (!carGoTo(2, line)) {
                    while (line==3||line==2){
                        while (line==2){
                            carGoTo(2,line);
                            ++line;
                        }
                        if (carGoTo(2,line))
                            ++line;
                    }
                    backToPark();
                }
            } else break;
        }

        //挑一侧有车位的进去
        if (park.getStatus(1, line)) {//这里是上方的车位
            carGoTo(1, line);
            //进车位要花两个单位时间
            mySleep(PublicData.midTime);
            carGoTo(0, line);
        } else if (park.getStatus(3, line)) {                     //这里是下方的车位
            carGoTo(3, line);
            //进车位要花两个单位时间
            mySleep(PublicData.midTime);
            carGoTo(4, line);
        } else {
            backToPark();
        }
    }

    /**
     * 当车辆无法进入停车场时,就让车辆按照固定的顺序离开停车场
     */
    private void carCanNotIn() {
//        print.println("汽车"+number+"无法进入停车场而离开");
        carGoTo(3, 1);
        carGoTo(4, 1);
        mySleep(PublicData.midTime);
        dropCar();
        publicData.getParkFrame().repaint();
    }

    /**
     * 车辆在停车场中停留足够长的事件后,移动到后门的方法
     */
    private boolean moveToRearDoor() {
        reverseCar();
        line = carHeadY;
        while (!carGoTo(2, line)) {
            exponentialBackoff();
            line = carHeadY;
        }
        line++;
        mySleep(PublicData.midTime);
        while (line < publicData.getN() + 4) {
            if (!carGoTo(2, line)) {
                backToPark();
                return false;
            }
            line++;
        }
        return true;
    }

    /**
     * 出门校验通过后,离开停车场.这里不会堵车,所以不用增加退避算法
     */
    private void leavePark() {
        line = carHeadY + 1;
        while (line < publicData.getN() + 6) {
            try {
                carGoTo(2, line);
            } catch (ArrayIndexOutOfBoundsException e) {
                print.println(e);
                print.println("line=" + line);
            }
            line++;
        }
        line--;
        new Thread() {
            @Override
            public void run() {
                super.run();
                park.carHadBeenOut();
            }
        }.start();
        carGoTo(3, line);
        carGoTo(4, line);
        mySleep(PublicData.midTime);
        dropCar();
        publicData.getParkFrame().repaint();
    }

    /**
     * 在任何情况下可以回到车位
     */
    private void backToPark() {
        if (carHeadY == carTailY) {//车只走出车库一格,退回去就好
            reverseCar();
            int temp=carHeadX - (carTailX - carHeadX);
            carGoTo(temp>=0?temp:0, carHeadY);
            return;
        }
        while (true) {
            if (carHeadY == 2 || carHeadY == 3 + publicData.getN()) { //车辆达到两端边界,反向调头
                reverseCar();
            }
            if (park.getStatus(1, carHeadY)) {
                carGoTo(1, carHeadY);
                carGoTo(0, carHeadY);
                break;
            } else if (park.getStatus(3, carHeadY)) {
                carGoTo(3, carHeadY);
                carGoTo(4, carHeadY);
                break;
            } else if (!carGoTo(2, carHeadY + (carHeadY - carTailY))) {
                reverseCar();
            }
        }
//        reverseCar();
//        line=carHeadY;
//        while(!park.getStatus(1,line)&&!park.getStatus(3,line)){
//            line--;
//            carGoTo(2,line);
//        }
//        if (park.getStatus(1,line)){
//            carGoTo(1,line);
//            mySleep(PublicData.midTime);
//            carGoTo(0,line);
//        }else {
//            carGoTo(3,line);
//            mySleep(PublicData.midTime);
//            carGoTo(4,line);
//        }
    }

    /**
     * 回到停车位以后,等待被员工处理,罚款等操作,处理好了之后即可离开停车场
     */
    private void waitHandleAndOut() {
//        while (true) {
//            mySleep(PublicData.midTime);
//            System.err.println("不讲道理啊! ????:"+carHeadY);
//            if (beenHandled) {
//                System.err.println("zheline ????:"+carHeadY);
//                while (!moveToRearDoor()){
//                    exponentialBackoff();
//                }
//                if (park.out(this)) {
//                    leavePark();
//                }
//            }
//        }
        try {
            synchronized (this) {
                wait();
                while (!moveToRearDoor()) {
                    exponentialBackoff();
                }
                if (park.out(this)) {
                    leavePark();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void exponentialBackoff() {
        mySleep((random.nextInt(3^conflictCount) % 600) * midTime);
    }

    /**
     * 车辆从停车场的GUI中出现的时候,占据的是左上角的两个位置,称为UI初始化
     */
    private void initCarUI() {
        carTailX = 0;
        carTailY = 0;
        carHeadX = 1;
        carHeadY = 0;
        park.setStatusFalse(carTailX, carTailY);
        park.setStatusFalse(carHeadX, carHeadY);
        publicData.getParkFrame().repaint();
    }

    /**
     * 让车辆向前行进一步,行进一步后,原车辆的车头的位置,现在是车尾的位置.车辆的新位置是目的位置(车头)+原先车头的位置(新车尾)
     *
     * @param x 停车场的纵向位置,对应在之前的二维数组存储方式
     * @param y 停车场的纵向位置,对应在之前的二维数组存储方式
     */
    private boolean carGoTo(int x, int y) {
        while (true) {
            mySleep(PublicData.midTime);
            ++conflictCount;
            if (carUIGoTO(x, y)) {
                conflictCount = 0;
//                print.println(number+"号车到达"+"\tX="+x+"\tY="+y);
                return true;
            }
            if (conflictCount >= 5)
                return false;
        }
    }

    private void carGoTo(LocationOfPark l) {
        carGoTo(l.getX(), l.getY());
    }

    /**
     * 让车辆后退一步,原来车尾的位置变成车头,目的位置是新车尾的位置.
     *
     * @param x 停车场的纵向位置,对应在之前的二维数组存储方式
     * @param y 停车场的横向位置,对应在之前的二维数组存储方式
     */
    private void carBackTo(int x, int y) {
        while (true) {
            reverseCar();
            mySleep(PublicData.midTime);
            if (carUIGoTO(x, y)) {
//                print.println(number+"号车到达"+"\tX="+x+"\tY="+y);
                reverseCar();
                break;
            }
        }
    }

    private void carBackTo(LocationOfPark l) {
        carBackTo(l.getX(), l.getY());
    }

    /**
     * 这段关键代码改动了车辆位置在二维数组中的位置.根据给出的目的位置,将车移动到新位置,并把原先不用的位置设置为可以通过
     *
     * @param x 停车场的纵向位置,对应在之前的二维数组存储方式
     * @param y 停车场的纵向位置,对应在之前的二维数组存储方式
     * @return 如果这个位置不能过车则表示为false, 通过表示为true
     */
    private boolean carUIGoTO(int x, int y) {
        if (park.getStatus(x, y)) {
            park.setStatusTrue(carTailX, carTailY);
            park.setStatusFalse(x, y);
            park.setStatusFalse(carHeadX, carHeadY);
            carTailX = carHeadX;
            carTailY = carHeadY;
            carHeadX = x;
            carHeadY = y;
            publicData.getParkFrame().repaint();
            return true;
        } else {
//            print.println(number+"号车想要到达"+"\tX="+x+"\tY="+y+"但并没有");
            return false;
        }
    }

    /**
     * 将车辆的车头车尾位置进行调换,为的是可以进入下一个位置的时候车辆状态是正确的.
     */
    private void reverseCar() {
        int temp3X = carTailX;
        int temp3Y = carTailY;
        carTailX = carHeadX;
        carTailY = carHeadY;
        carHeadX = temp3X;
        carHeadY = temp3Y;
    }

    /**
     * 车辆线程的终点,用来使车辆消失
     */
    private void dropCar() {
        park.setStatusTrue(carHeadX, carHeadY);
        park.setStatusTrue(carTailX, carTailY);
        carHeadX = -1;
        carHeadY = -1;
        carTailX = -1;
        carTailY = -1;
    }

    /**
     * 一个用来完成一段时间线程休眠的函数,封装了try,catch
     *
     * @param time
     */
    private void mySleep(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 车辆经过工人处理后则标记为已经被处理过
     */
    public void handle() {
        this.beenHandled = true;
    }

    public Employee getInData() {
        return inData;
    }

    public Employee getOutData() {
        return outData;
    }

    public int getTime() {
        return time;
    }

    /**
     * 出口校验时,如果一个车是被处理过的,则不计较他是否满足出停车场的要求.
     *
     * @return
     */
    public boolean isHandled() {
        return beenHandled;
    }

    public boolean atParkLocation() {
        if (carHeadX == 0 || carHeadX == 4) {
            return true;
        } else return false;
    }
}
