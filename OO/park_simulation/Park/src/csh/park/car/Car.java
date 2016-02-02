package csh.park.car;

import csh.park.data.Employee;
import csh.park.park.Park;
import csh.park.data.PublicData;

import java.util.*;

import static csh.park.data.PublicData.*;

/**
 * Car类是一个车辆的表示方法,继承了线程类,用多线程技术完成每一辆车的控制,在run方法中完成车辆的种种行为,进入停车场,校验员工,泊车,出停车场
 * 同时在一些可能产生冲突的地方设置了避免冲突的算法.
 * 车辆错车的方式选取为,若遇到冲突则选择最近的空车位停进去,随机等一定时间长度再尝试一次出门
 * Created by Alan on 15/12/9.
 */
public class Car extends Thread {
    /**
     * 记录当前总共生成过多少个线程
     */
    private static int count = 0;
    /**
     * 车辆经过前后门时分别出示的信息
     */
    private final Employee inData, outData;
    /**
     * 车辆所属的停车场
     */
    private final Park park;
    /**
     * 公共数据引用
     */
    private final PublicData publicData;
    /**
     * 随机生成的车辆停留时间,单位毫秒
     */
    private final int time;
    /**
     * 车牌号
     */
    private final int number;
    /**
     * 车头的行坐标
     */
    private int carHeadX;
    /**
     * 车头的列坐标
     */
    private int carHeadY;
    /**
     * 车尾的行坐标
     */
    private int carTailX;
    /**
     * 车尾的列坐标
     */
    private int carTailY;
    /**
     * 车辆的一个标志符,与车辆的下一格位置相关
     */
    private int line = 2;
    /**
     * 车辆是否被工作人员处理过,经过处理的车辆是可以直接离开停车场的,所以默认为false
     */
    private boolean beenHandled = false;
    /**
     * 单步冲突次数,如果车想要向下一格前进,每单位时间进行一次校验,走到了则计数清零,没走到计数器+1
     */
    private int conflictTimeCount;
    /**
     * 车辆冲突次数,如果车辆单步冲突达到一定上限,则计入车辆冲突次数,采用二进制指数退避算法,作为计数器
     */
    private int carConflictCount=0;
    /**
     * 随机数生成器,用来生成退避时的等待时长
     */
    private Random random;

    /**
     * 构造函数
     * @param inData 进入停车场的员工信息
     * @param outData 离开停车场的员工信息
     * @param time 车辆的等待时长
     */
    public Car(Employee inData, Employee outData, int time) {
        this.outData = outData;
        this.inData = inData;
        this.publicData = PublicData.getPublicData();
        this.time = time;
        park = publicData.getPark();
        number = count++;
        conflictTimeCount = 0;
        random = new Random();
        initCarUI();
    }

    /**
     * 该方法实现了车辆要运行的各种情况,到门口,进不去就走,进去了就找车位,找到了就停,停车结束离开,离开不了就回到一个就近车尾.知道能走为止
     * 期间一旦产生冲突就执行退避算法
     */
    @Override
    public void run() {
        super.run();
        carGoTo(2, 0);//走到门口
        carGoTo(2, 1);
        if (publicData.getConsole().getLeft() > 0 && park.in(this)) {
            parkingCar();//停车找车位
//            print.println("汽车"+number+"在停车场内,将要停"+time%MID_TIME+"秒");
            mySleep(time);
//            print.println("汽车"+number+"将要驶出停车场");
            //多次尝试,知道能走到后门为止.
            //在while中车辆向后门移动,移动不到就退避
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
            mySleep(MID_TIME);
            carGoTo(0, line);
        } else if (park.getStatus(3, line)) {                     //这里是下方的车位
            carGoTo(3, line);
            //进车位要花两个单位时间
            mySleep(MID_TIME);
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
        dropCar();
        publicData.getParkFrame().repaint();
    }

    /**
     * 车辆在停车场中停留足够长的事件后,移动到后门的方法
     * @return 到了后门返回true,没到则回到就进车位返回false;
     */
    private boolean moveToRearDoor() {
        reverseCar();
        line = carHeadY;
        while (!carGoTo(2, line)) {
            exponentialBackoff();
            line = carHeadY;
        }
        line++;
        mySleep(MID_TIME);
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
        dropCar();
        publicData.getParkFrame().repaint();
    }

    /**
     * 在任何情况下可以回到车位,策略是,就近停车,左边能走走左边,右边能走走右边目标是回到一个车位上
     */
    private void backToPark() {
        carConflictCount++;//每次调用该方法的时候都是产生了冲突的时候
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
    }

    /**
     * 回到停车位以后,等待被员工处理(处理后员工会唤醒本线程),罚款等操作,处理好了之后即可离开停车场
     */
    private void waitHandleAndOut() {
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

    /**
     * 指数退避算法,根据当前车辆已冲突次数,随机决定下次等待的时长,
     * 例:冲突1次,随机等待0,1个单位时长
     * 冲突2次,随机等待0,1,2,3个单位时长
     * 冲突n次,随机等待0~2^n个单位时长
     * n大于10时,按10计算
     */
    private void exponentialBackoff() {
        mySleep(random.nextInt(2^(carConflictCount>=10?carConflictCount:10)) * MID_TIME);
    }

    /**
     * 车辆从停车场的GUI中出现的时候,占据的是左上角的两个位置,称为UI初始化
     */
    private void initCarUI() {
        //表示车头车尾位置
        carTailX = 0;
        carTailY = 0;
        carHeadX = 1;
        carHeadY = 0;
        //设置车头车尾状态信息
        park.setStatusFalse(carTailX, carTailY);
        park.setStatusFalse(carHeadX, carHeadY);
        publicData.getParkFrame().repaint();
    }

    /**
     * 让车辆向前行进一步,行进一步后,原车辆的车头的位置,现在是车尾的位置.车辆的新位置是目的位置(车头)+原先车头的位置(新车尾)
     *
     * @param x 停车场的纵向位置,对应在之前的二维数组存储方式
     * @param y 停车场的纵向位置,对应在之前的二维数组存储方式
     * @return 一定时间内走到了目的位置,返回true.超时未走到目的位置,返回false
     */
    private boolean carGoTo(int x, int y) {
        while (true) {
            mySleep(MID_TIME);
            ++conflictTimeCount;
            if (carUIGoTO(x, y)) {
                conflictTimeCount = 0;
//                print.println(number+"号车到达"+"\tX="+x+"\tY="+y);
                return true;
            }
            if (conflictTimeCount >= 5)
                return false;
        }
    }

    /**
     * 改动了车辆位置在二维数组中的位置.根据给出的目的位置,将车移动到新位置,并把原先不用的位置设置为可以通过
     *
     * @param x 停车场的纵向位置,对应在之前的二维数组存储方式
     * @param y 停车场的纵向位置,对应在之前的二维数组存储方式
     * @return 如果这个位置不能过车则表示为false, 通过表示为true
     */
    private boolean carUIGoTO(int x, int y) {
        if (park.getStatus(x, y)) {//如果能走
            park.setStatusTrue(carTailX, carTailY);//原车尾位置属性置为可通过
            park.setStatusFalse(x, y);//目的位置属性置为不可通过
            park.setStatusFalse(carHeadX, carHeadY);//原车头位置属性置为不可通过
            //改变车辆位置
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
     * 将车辆的车头车尾位置进行调换,目的是可以进入下一个位置的时候车辆状态是正确的.
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
        mySleep(MID_TIME);
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
     * @param time 等待时长等待的毫秒数
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

    /**
     * 出口校验时,如果一个车是被处理过的,则不计较他是否满足出停车场的要求.
     *
     * @return 车辆被工作人员处理状态
     */
    public boolean isHandled() {
        return beenHandled;
    }

    public boolean atParkLocation() {
        return carHeadX == 0 || carHeadX == 4;
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

    public int getNumber() {
        return number;
    }

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
}
