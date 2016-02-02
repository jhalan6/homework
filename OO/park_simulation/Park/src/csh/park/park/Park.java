package csh.park.park;

import csh.park.car.Car;
import csh.park.car.CarFactory;
import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.data.PublicData;

import static csh.park.data.PublicData.*;

import csh.park.parkWorker.FrontWorker;
import csh.park.parkWorker.RearWorker;
import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * Park类是个关键类,用来维护停车场的每一个位置的状态信息,同时包括一些相关属性,前后门禁引用等信息
 * Created by Alan on 15/12/9.
 */
public class Park {
    /**
     * 停车场的最大容量
     */
    protected int maxCar;
    /**
     * 用二维数组存储整个停车场,而真正的停车场,就是这个二维数组放大的结果
     */
    protected boolean[][] park;
    /**
     * 停车场的前门门禁
     */
    protected CheckIn checkIn;
    /**
     * 停车场的后门门禁
     */
    protected CheckOut checkOut;
    /**
     * 可以一直生产Car线程的carFactory引用
     */
    protected CarFactory carFactory;
    /**
     * 类中维护的PublicData对象引用
     */
    protected PublicData publicData;
    /**
     * 最大车辆数的一半,也就是停车场中一排车的数量
     */
    protected int halfMax;
    /**
     * 现在在停车场中的车的列表
     */
    protected ArrayList<Car> carsInPark;
    /**
     * 停车场前门的工人
     */
    protected FrontWorker frontWorker;
    /**
     * 停车场后门的工人
     */
    protected RearWorker rearWorker;

    /**
     * 空参数构造函数,初始化一些基本数据,启动一些辅助线程.
     */
    public Park() {
        //初始化一些基本信息
        carsInPark = new ArrayList<>();
        this.publicData = PublicData.getPublicData();
        maxCar = publicData.getConfig().getMaxCar();
        carFactory = new CarFactory();
        halfMax = maxCar / 2;
        initParkArray();
        //初始化前后门禁
        checkIn = new CheckIn();
        checkOut = new CheckOut();
        //初始化工作人员
        frontWorker = new FrontWorker(publicData.getInError());
        rearWorker = new RearWorker(publicData.getOutError());
        //启动汽车工厂,让前后门的工人开始工作
        carFactory.start();
        frontWorker.start();
        rearWorker.start();
    }

    /**
     * 初始化二维数组,二维数组是5行,halfMax+6列,每辆车占据两个格子
     */
    private void initParkArray() {
        //初始化二维数组
        park = new boolean[5][halfMax + 2 + 4];
        for (boolean[] temp : park)
            for (int i = halfMax + 5; i >= 0; --i) {
                temp[i] = true;
            }
        //将不可停车的部分置为false;
        park[0][0 + 2] = park[1][0 + 2] = park[3][0 + 2] = park[4][0 + 2] = false;
        park[0][halfMax + 1 + 2] = park[1][halfMax + 1 + 2] = park[3][halfMax + 1 + 2] = park[4][halfMax + 1 + 2] = false;
    }

    /**
     * 深度复制一组停车场数据
     *
     * @return 返回一个与原数组无关的一个二维数组, 但值相同
     */
    public boolean[][] getParkCopy() {
        boolean[][] clone = new boolean[5][halfMax + 2 + 4];
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < halfMax + 2 + 4; ++j) {
                clone[i][j] = park[i][j];
            }
        }
        return clone;
    }

    /**
     * 查看一个位置当前状态是否可以过车/停车
     *
     * @param x 纵向高度(位置)
     * @param y 横向宽度(位置)
     * @return 可以停车为true, 不可以停车为false
     */
    public boolean getStatus(int x, int y) {
        try {
            return park[x][y];
        } catch (Exception e) {
            print.println("x=" + x + "\ty=" + y);
            print.println(e);
        }
        return false;
    }

    /**
     * 把一个位置设置为可以过车,用于车辆离开某位置时对该位置的标记
     *
     * @param x 纵向高度(位置)
     * @param y 横向宽度(位置)
     */
    public void setStatusTrue(int x, int y) {
        park[x][y] = true;
    }

    /**
     * 把一个位置设置为不可以过车,用于车辆进入某位置的时候对该位置进行标记
     *
     * @param x 纵向高度(位置)
     * @param y 横向宽度(位置)
     */
    public void setStatusFalse(int x, int y) {
        park[x][y] = false;
    }

    /**
     * 进入一辆车的时候对车辆信息进行登记
     *
     * @param car 车辆信息
     * @return 可以进入返回true, 反之返回false
     */
    public boolean in(Car car) {
        Message message = checkIn.checkEmployee(car);
        if (message.getMessageType() == Message.MessageType.ok) {
            publicData.getConsole().carIn(car.getTime());
            carsInPark.add(car);
            return true;
        } else {
            publicData.getInError().add(message);
            return false;
        }
    }

    /**
     * 离开一辆车的时候对车辆信息进行登记
     *
     * @param car 车辆信息
     * @return 可以离开返回true, 反之返回false
     */
    public boolean out(Car car) {
        Message message = checkOut.checkEmployee(car);
        if (message.getMessageType() == Message.MessageType.ok) {
            carsInPark.remove(car);
            publicData.getConsole().carOut();
            return true;
        } else {
            publicData.getOutError().add(message);
            return false;
        }
    }

    /**
     * 车辆走出停车栏杆范围后停车场通知栏杆关闭
     */
    public void carHadBeenIn() {
        checkIn.carHadBeenLeaveBar();
    }

    /**
     * 车辆走出停车栏杆范围后停车场通知栏杆关闭
     */
    public void carHadBeenOut() {
        checkOut.carHadBeenLeaveBar();
    }

    /**
     * 返回最大车辆数的一半
     *
     * @return 一行内的车位数量
     */
    public int getHalfMax() {
        return halfMax;
    }

    /**
     * 返回停车场中车辆的集合
     *
     * @return 含有所有停车场内车辆引用的列表
     */
    public ArrayList<Car> getCarsInPark() {
        return carsInPark;
    }

    /**
     * 返回前门引用
     *
     * @return 前门引用
     */
    public CheckIn getCheckIn() {
        return checkIn;
    }

    /**
     * 返回后门引用
     *
     * @return 后门的一个引用
     */
    public CheckOut getCheckOut() {
        return checkOut;
    }
}