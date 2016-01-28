package csh.park.car;

import csh.park.console.Console;
import csh.park.data.Employee;
import csh.park.park.Park;
import csh.park.data.PublicData;
import static csh.park.data.PublicData.print;

/**
 * Created by Alan on 15/12/9.
 */
public class Car extends Thread{
    private static int count =0;
    private final Employee inData,outData;
    private final Park park;
    private final PublicData publicData;
    private final Console console;
    private final int time;
    private final int number;
    private int carTailX;
    private int carTailY;
    private int carHeadX;
    private int carHeadY;
    private int line=2;
    private boolean beenHandled=false;

    public Car(Employee outData, Employee inData,int time) {
        this.outData = outData;
        this.inData = inData;
        this.publicData=PublicData.getPublicData();
        this.time=time;
        console=publicData.getConsole();
        park=publicData.getPark();
        number= count++;
        initCarUI();
    }
    @Override
    public void run() {
        super.run();
        carMission();
    }

    private void carMission() {
        carGoTo(2,0);//走到门口
        carGoTo(2,1);
        if (console.getLeft()>0&&park.in(this)){
            parkingCar();//停车找车位
//            print.println("汽车"+number+"在停车场内,将要停"+time%PublicData.midTime+"秒");
            mySleep(time);
            reverseCar();
//            print.println("汽车"+number+"将要驶出停车场");
            moveToRearDoor();
            if(park.out(this)){
                leavePark();
            }else {
                backToPark();
                waitHandleAndOut();
            }
        }else {
            carCanNotIn();
        }
    }

    private void waitHandleAndOut() {
        while (true){
            mySleep(PublicData.midTime);
            if (beenHandled){
                moveToRearDoor();
                if (park.out(this)){
                    reverseCar();
                    leavePark();
                }
            }
        }
    }

    private void backToPark() {
        reverseCar();
        line=carHeadY;
        while(!park.getStatus(1,line)&&!park.getStatus(3,line)){
            line--;
            carGoTo(2,line);
        }
        if (park.getStatus(1,line)){
            carGoTo(1,line);
            mySleep(PublicData.midTime);
            carGoTo(0,line);
        }else {
            carGoTo(3,line);
            mySleep(PublicData.midTime);
            carGoTo(4,line);
        }
    }

    private void leavePark() {
        line=carHeadY+1;
        while (line<publicData.getN()+6){
            try {
                carGoTo(2,line);
            }catch (ArrayIndexOutOfBoundsException e){
                print.println(e);
                print.println("line="+line);
            }
            line++;
        }
        line--;
        new Thread(){
            @Override
            public void run() {
                super.run();
                park.carHadBeenOut();
            }
        }.start();
        carGoTo(3,line);
        carGoTo(4,line);
        mySleep(PublicData.midTime);
        dropCar();
        publicData.getParkFrame().repaint();
    }

    private void moveToRearDoor() {
        line=carHeadY;
        carGoTo(2,line);
        line++;
        mySleep(PublicData.midTime);
        while (line<publicData.getN()+4){
            carGoTo(2,line);
            line++;
        }
    }

    private void carCanNotIn() {
//        print.println("汽车"+number+"无法进入停车场而离开");
        carGoTo(3,1);
        carGoTo(4,1);
        mySleep(PublicData.midTime);
        dropCar();
        publicData.getParkFrame().repaint();
    }

    private void mySleep(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void parkingCar() {
//        print.println("汽车"+number+"进入停车场停车");
        carGoTo(2,2);
        while (true){
            if (line==3){
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        park.carHadBeenIn();
                    }
                }.start();
            }
            if ((line<publicData.getN()+3)&&!park.getStatus(1,line)&&!park.getStatus(3,line)) {
                line += 1;
                carGoTo(2, line);
            }else break;
        }
        if(park.getStatus(1,line)){
            carGoTo(1,line);
            mySleep(PublicData.midTime);
            carGoTo(0,line);
        }else {
            carGoTo(3,line);
            mySleep(PublicData.midTime);
            carGoTo(4,line);
        }
    }

    private void reverseCar() {
        int temp3X= carTailX;
        int temp3Y= carTailY;
        carTailX = carHeadX;
        carTailY = carHeadY;
        carHeadX =temp3X;
        carHeadY =temp3Y;
    }

    private void initCarUI() {
        carTailX =0;
        carTailY =0;
        carHeadX =1;
        carHeadY =0;
        park.setStatusFalse(carTailX, carTailY);
        park.setStatusFalse(carHeadX, carHeadY);
        publicData.getParkFrame().repaint();
    }
    private boolean carUIGoTO(int x,int y){
        if (park.getStatus(x,y)){
            park.setStatusTrue(carTailX, carTailY);
            park.setStatusFalse(x,y);
            park.setStatusFalse(carHeadX, carHeadY);
            carTailX = carHeadX;
            carTailY = carHeadY;
            carHeadX =x;
            carHeadY =y;
            publicData.getParkFrame().repaint();
            return true;
        }else {
//            print.println(number+"号车想要到达"+"\tX="+x+"\tY="+y+"但并没有");
            return false;
        }
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

    private void carGoTo(int x, int y){
        while(true){
            mySleep(PublicData.midTime);
            if (carUIGoTO(x,y)) {
//                print.println(number+"号车到达"+"\tX="+x+"\tY="+y);
                break;
            }
        }
    }

    /**
     * 车辆线程的终点,用来使车辆消失
     */
    private void dropCar(){
        park.setStatusTrue(carHeadX,carHeadY);
        park.setStatusTrue(carTailX,carTailY);
        carHeadX=-1;
        carHeadY=-1;
        carTailX=-1;
        carTailY=-1;
    }
    public void handle(){
        this.beenHandled=true;
    }
    public boolean isHandled(){
        return beenHandled;
    }

    public boolean atParkLocation() {
        if (carTailX==0||carTailX==4||carHeadX==0||carHeadX==4){
            return true;
        }else return false;
    }
}
