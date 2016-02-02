package csh.park.console;

import csh.park.check.CheckID;
import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.data.PublicData;

import static csh.park.data.PublicData.*;

/**
 * Console类是一个控制台相关的程序,负责记录相对时间,剩余车位数,
 * Created by Alan on 15/12/9.
 */
public class Console {
    /**
     * 内部维护的一个publicData引用
     */
    private final PublicData publicData;
    /**
     * 该参数总是与当前剩余容纳量一致
     */
    private int remainCapability;
    /**
     * 维护的前门禁的引用
     */
    private CheckIn checkIn;
    /**
     * 维护的后门禁的引用
     */
    private CheckOut checkOut;
    /**
     * 所有已经进入停车场车辆的停车时间
     */
    private long totalTime = 0;
    /**
     * 所有进入过停车场(不论是否出了停车场)的车辆
     */
    private long totalCar = 0;
    /**
     * 当前的相对时间,数值为总时间除以单位时间
     */
    private long relativeTime = 0;

    public Console() {
        publicData = PublicData.getPublicData();
        remainCapability = publicData.getConfig().getMaxCar();
        checkIn = publicData.getPark().getCheckIn();
        checkOut = publicData.getPark().getCheckOut();
        //这里的一个线程用来完成计时,计数,输出日志的功能.
        createCountAndLogThread();
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
     * 重写toString方法
     *
     * @return 控制台相关的数据集合
     */
    @Override
    public String toString() {
        return relativeTimeToString() + frontDoorToString() + relativeTimeToString() + rearDoorToString();
    }

    /**
     * 用来查看当前的剩余容纳量
     *
     * @return 剩余车位数
     */
    public int getLeft() {
        return remainCapability;
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

    /**
     * 这个方法创建了一个进行报告并且定时更新停车场控制台信息的线程
     * 方法中用了很多System.out.println的方法.这里的System.out在PublicData中进行过重置,是输出到日志文件中的方法
     */
    private void createCountAndLogThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2 * MID_TIME);
                    relativeTime += 2;
                    while (true) {
                        //每一个单位时间相对时间要+1
                        sleep(MID_TIME);
                        relativeTime++;
                        publicData.getParkFrame().repaint();
                        if (relativeTime % 60 == 0) {
                            System.out.println("第" + relativeTime / 60 + "次定时报告:");
                            System.out.println("\t截止目前本次仿真累计入场车数:" + totalCar);
                            System.out.println("\t截止目前本次仿真累计出场车数:" + (totalCar - 10 + remainCapability));

                            //避免当前车辆数是0,造成除数为0
                            System.out.println("\t截至目前本次仿真汽车的平均停车时间" + (totalCar != 0 ? (totalTime / (totalCar * MID_TIME)) : 0) + "s");

                            print.println("第" + relativeTime / 60 + "次定时报告:");
                            print.println("\t截止目前本次仿真累计入场车数:" + totalCar);
                            print.println("\t截止目前本次仿真累计出场车数:" + (totalCar - 10 + remainCapability));
                            if (totalCar != 0)
                                print.println("\t截至目前本次仿真汽车的平均停车时间" + totalTime / (totalCar * MID_TIME) + "s");
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
}
