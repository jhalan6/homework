package csh.park.console;

import csh.park.check.CheckID;
import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.data.PublicData;

import static csh.park.data.PublicData.*;

/**
 * Created by Alan on 15/12/9.
 */
public class Console {

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
        //这里的一个线程用来完成计时,计数,输出日志的功能.
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3*PublicData.midTime);
                    relativeTime+=3;
                    while (true) {
                        sleep(PublicData.midTime);
                        relativeTime++;
                        publicData.getParkFrame().repaint();
                        if (relativeTime % 60 == 0) {
                            System.out.println("第" + relativeTime / 60 + "次定时报告:");
                            System.out.println("\t截止目前本次仿真累计入场车数:" + totalCar);
                            System.out.println("\t截止目前本次仿真累计出场车数:" + (totalCar - 10 + remainCapability));
                            if (totalCar != 0)
                                System.out.println("\t截至目前本次仿真汽车的平均停车时间" + totalTime / (totalCar * PublicData.midTime) + "s");
                            else
                                System.out.println("\t截至目前本次仿真汽车的平均停车时间" + 0);
                            print.println("第" + relativeTime / 60 + "次定时报告:");
                            print.println("\t截止目前本次仿真累计入场车数:" + totalCar);
                            print.println("\t截止目前本次仿真累计出场车数:" + (totalCar - 10 + remainCapability));
                            if (totalCar != 0)
                                print.println("\t截至目前本次仿真汽车的平均停车时间" + totalTime / (totalCar * PublicData.midTime) + "s");
                            else
                                print.println("\t截至目前本次仿真汽车的平均停车时间" + 0);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 车辆离开后更新剩余车位数
     */
    public void carOut() {
        remainCapability++;
    }

    /**
     * 车辆进入后更改停车场内的剩余车位数,时间计数器,总车辆计数器等信息
     *
     * @param time 新进入停车场的车辆的目标停车时间
     */
    public void carIn(long time) {
        remainCapability--;
        totalTime += time;
        ++totalCar;
    }

    /**
     * 返回一个当前时间的字符串表示
     *
     * @return a:b的分钟表示方式
     */
    public String relativeTimeToString() {
        StringBuilder stringBuilder = new StringBuilder("当前时间:");
        stringBuilder.append(relativeTime / 60);
        stringBuilder.append(":" + relativeTime % 60);
        return stringBuilder.toString();
    }

    /**
     * 返回前门的状态信息,用来更新左上角的前门状态
     *
     * @return 前门状态信息的字符串表示
     */
    public String rearDoorToString() {
        StringBuilder stringBuilder = new StringBuilder("后门状态:");
        readDoorStatus(checkOut, stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * 返回停车场的状态信息,用来更新中间的停车场剩余车辆数目
     *
     * @return 剩余车位的字符串表示
     */
    public String capabilityToString() {
        StringBuilder stringBuilder = new StringBuilder("剩余车位:");
        stringBuilder.append(remainCapability);
        return stringBuilder.toString();
    }

    /**
     * 返回后门的状态信息,用来更新右上角的后门状态
     *
     * @return 后门状态的字符串表示
     */
    public String frontDoorToString() {
        StringBuilder stringBuilder = new StringBuilder("前门状态:");
        readDoorStatus(checkIn, stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * 根据CheckID类的属性识别出校验状态信息
     *
     * @param checkIn       前后门禁之一
     * @param stringBuilder 目前的状态信息StringBuilder,用来维护返回值
     */
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("前门状态:");
        readDoorStatus(checkIn, stringBuilder);
        stringBuilder.append("\t车位剩余:" + remainCapability + "\t后门状态:");
        readDoorStatus(checkOut, stringBuilder);
        return stringBuilder.toString();
    }

    public int getLeft() {
        return remainCapability;
    }
}
