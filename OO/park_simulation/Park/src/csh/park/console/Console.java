package csh.park.console;

import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.data.PublicData;

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
                    sleep(3*1000);
                    relativeTime+=3;
                    while (true) {
                        sleep(1000);
                        relativeTime++;
                        publicData.getParkFrame().repaint();
                        if (relativeTime % 60 == 0) {
                            System.out.println("第" + relativeTime / 60 + "次定时报告:");
                            System.out.println("\t截止目前本次仿真累计入场车数:" + totalCar);
                            System.out.println("\t截止目前本次仿真累计出场车数:" + (totalCar - 10 + remainCapability));
                            if (totalCar != 0)
                                System.out.println("\t截至目前本次仿真汽车的平均停车时间" + totalTime / (totalCar * 1000) + "s");
                            else
                                System.out.println("\t截至目前本次仿真汽车的平均停车时间" + 0);
                            System.err.println("第" + relativeTime / 60 + "次定时报告:");
                            System.err.println("\t截止目前本次仿真累计入场车数:" + totalCar);
                            System.err.println("\t截止目前本次仿真累计出场车数:" + (totalCar - 10 + remainCapability));
                            if (totalCar != 0)
                                System.err.println("\t截至目前本次仿真汽车的平均停车时间" + totalTime / (totalCar * 1000) + "s");
                            else
                                System.err.println("\t截至目前本次仿真汽车的平均停车时间" + 0);
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

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("当前时间:");
        stringBuffer.append(relativeTime / 60);
        stringBuffer.append(":" + relativeTime % 60);
        stringBuffer.append("\t");
        stringBuffer.append("前门状态:");
        switch (checkIn.getDoorStatus()) {
            case yes:
                stringBuffer.append("校验通过\t");
                break;
            case no:
                stringBuffer.append("禁止进入\t");
                break;
            case nothing:
                stringBuffer.append("\t\t\t\t\t\t");
        }
        stringBuffer.append("\t");
        stringBuffer.append("剩余车位:");
        stringBuffer.append(remainCapability);
        stringBuffer.append("\t");
        stringBuffer.append("后门状态:");
        switch (checkOut.getDoorStatus()) {
            case yes:
                stringBuffer.append("校验通过\t");
                break;
            case no:
                stringBuffer.append("禁止进入\t");
                break;
            case nothing:
                stringBuffer.append("\t\t\t\t\t\t");
        }
        stringBuffer.append("\t");
        return stringBuffer.toString();
    }

    public int getLeft() {
        return remainCapability;
    }
}
