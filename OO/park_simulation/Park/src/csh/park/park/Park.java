package csh.park.park;

import csh.park.car.Car;
import csh.park.car.CarFactory;
import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.data.PublicData;
import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * Created by Alan on 15/12/9.
 */
public class Park {
    protected int maxCar;//停车场的最大容量
    protected boolean[][] park;//用二维数组存储整个停车场,而真正的停车场,就是这个二维数组放大的结果

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public CheckOut getCheckOut() {
        return checkOut;
    }

    protected CheckIn checkIn;
    protected CheckOut checkOut;
    protected CarFactory carFactory;
    protected PublicData publicData;
    private int halfMax;

    public ArrayList<Car> getCarsInPark() {
        return carsInPark;
    }

    private ArrayList<Car> carsInPark;
    public Park() {
        carsInPark=new ArrayList<Car>();
        this.publicData = PublicData.getPublicData();
        maxCar = publicData.getConfig().getMaxCar();
        carFactory=new CarFactory();
        carFactory.start();
        halfMax =maxCar/2;

        //初始化二维数组
        park=new boolean[5][halfMax +2+4];
        for (boolean[] temp:park)
            for (int i = halfMax +5; i>=0; --i){
                temp[i]=true;
            }
        //将不可停车的部分置为false;
        park[0][0+2]=park[1][0+2]=park[3][0+2]=park[4][0+2]=false;
        park[0][halfMax +1+2]=park[1][halfMax +1+2]=park[3][halfMax +1+2]=park[4][halfMax +1+2]=false;

        checkIn=new CheckIn();
        checkOut=new CheckOut();
    }

    /**
     * 深度复制一组停车场数据
     * @return 返回一个与原数组无关的一个二维数组,但值相同
     */
    public boolean[][] getParkCopy(){
        boolean[][] clone=new boolean[5][halfMax +2+4];
        for (int i=0;i<5;++i){
            for (int j = 0; j< halfMax +2+4; ++j){
                clone[i][j]=park[i][j];
            }
        }
        return clone;
    }

    /**
     * 查看一个位置当前状态是否可以过车/停车
     * @param x 纵向高度(位置)
     * @param y 横向宽度(位置)
     * @return 可以停车为true,不可以停车为false
     */
    public boolean getStatus(int x,int y){
        try {
            return park[x][y];
        }catch (Exception e){
            System.err.println("x="+x+"\ty="+y);
            System.err.println(e);
        }finally {
            return park[x][y];
        }
    }

    /**
     * 把一个位置设置为可以过车,用于车辆离开某位置时对该位置的标记
     * @param x 纵向高度(位置)
     * @param y 横向宽度(位置)
     */
    public void setStatusTrue(int x,int y){
       park[x][y]=true;
    }

    /**
     * 把一个位置设置为不可以过车,用于车辆进入某位置的时候对该位置进行标记
     * @param x 纵向高度(位置)
     * @param y 横向宽度(位置)
     */
    public void setStatusFalse(int x,int y){
        park[x][y]=false;
    }

    /**
     * 进入一辆车的时候对车辆信息进行登记
     * @param car 车辆信息
     * @return 可以进入否
     */
    public boolean in(Car car){
        Message message=checkIn.checkEmployee(car);
        if(message.getMessageType()==Message.MessageType.ok){
            publicData.getConsole().carIn(car.getTime());
            carsInPark.add(car);
            return true;
        } else{
            publicData.getInError().add(message);
            return false;
        }
    }

    /**
     * 离开一辆车的时候对车辆信息进行登记
     * @param car 车辆信息
     * @return 可以离开否
     */
    public boolean out(Car car){
        Message message=checkOut.checkEmployee(car);
        if(message.getMessageType()==Message.MessageType.ok){
            carsInPark.remove(car);
            publicData.getConsole().carOut();
            return true;
        } else{
            publicData.getOutError().add(message);
            return false;
        }
    }
    public void carHadBeenIn(){
        checkIn.carHadBeenIn();
    }
    public void carHadBeenOut(){
        checkOut.carHadBeenOut();
    }
    /**
     * 用来返回停车场上限的一半这个数
     * @return maxCar/2
     */
    public int getHalfMax(){
        return halfMax;
    }
}
