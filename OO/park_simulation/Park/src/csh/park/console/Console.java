package csh.park.console;

import csh.park.check.CheckID;
import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.data.PublicData;

import static csh.park.data.PublicData.*;

/**
 * Created by Alan on 15/12/9.
 */
public class Console extends Thread {

    private final PublicData publicData;
    private final int maxCar;
    private int remainCapability;
    private CheckIn checkIn;
    private CheckOut checkOut;
    private long totalTime = 0;
    private long totalCar = 0;
    private long relativeTime = 0;

    public Console() {
        this.publicData = PublicData.getPublicData();
        maxCar = publicData.getConfig().getMaxCar();
        remainCapability = maxCar;
        checkIn = publicData.getPark().getCheckIn();
        checkOut = publicData.getPark().getCheckOut();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        sleep(PublicData.midTime);
                        relativeTime++;
                        publicData.getParkFrame().repaint();
                        if (relativeTime % 60 == 0) {
                            System.out.println("第" + relativeTime /60+ "次定时报告:");
                            System.out.println("\t截止目前本次仿真累计入场车数:" + totalCar);
                            System.out.println("\t截止目前本次仿真累计出场车数:" + (totalCar - 10 + remainCapability));
                            if (totalCar!=0)
                                System.out.println("\t截至目前本次仿真汽车的平均停车时间" + totalTime / (totalCar * PublicData.midTime)+"s");
                            else
                                System.out.println("\t截至目前本次仿真汽车的平均停车时间"+0);
                            print.println("第" + relativeTime /60+ "次定时报告:");
                            print.println("\t截止目前本次仿真累计入场车数:" + totalCar);
                            print.println("\t截止目前本次仿真累计出场车数:" + (totalCar - 10 + remainCapability));
                            if (totalCar!=0)
                                print.println("\t截至目前本次仿真汽车的平均停车时间" + totalTime / (totalCar * PublicData.midTime)+"s");
                            else
                                print.println("\t截至目前本次仿真汽车的平均停车时间"+0);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void carInError() {

    }

    public void carIn(long time) {
        remainCapability--;
        totalTime += time;
        ++totalCar;
    }

    public void carOut() {
        remainCapability++;
    }

    public int getLeft() {
        return remainCapability;
    }

    public String relativeTimeTOString() {
        StringBuilder stringBuilder = new StringBuilder("当前时间:");
        stringBuilder.append(relativeTime/60);
        stringBuilder.append(":"+relativeTime%60);
        return stringBuilder.toString();
    }

    public String frontDoorToString() {
        StringBuilder stringBuilder = new StringBuilder("前门状态:");
        readDoorStatus(checkIn, stringBuilder);
        return stringBuilder.toString();
    }

    public String capabilityToString() {
        StringBuilder stringBuilder = new StringBuilder("剩余车位:");
        stringBuilder.append(remainCapability);
        return stringBuilder.toString();
    }

    public String rearDoorToString() {
        StringBuilder stringBuilder = new StringBuilder("后门状态:");
        readDoorStatus(checkOut, stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("前门状态:");
        readDoorStatus(checkIn, stringBuilder);
        stringBuilder.append("\t车位剩余:" + remainCapability + "\t后门状态:");
        readDoorStatus(checkOut, stringBuilder);
        return stringBuilder.toString();
    }

    private void readDoorStatus(CheckID checkIn, StringBuilder stringBuilder) {
        switch (checkIn.getDoorStatus()) {
            case yes:
                stringBuilder.append("校验通过\t");
                break;
            case no:
                stringBuilder.append("禁止进入\t");
                break;
            case nothing:
                stringBuilder.append("\t\t\t\t\t\t");
        }
    }
}
