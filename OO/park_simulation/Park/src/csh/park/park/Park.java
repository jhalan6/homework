package csh.park.park;

import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.data.Employee;
import csh.park.ui.ParkFrame;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/9.
 */
public class Park {
    protected int maxCar;//停车场的最大容量
    protected boolean[][] park;//用二维数组存储整个停车场,而真正的停车场,就是这个二维数组放大的结果
    protected CheckIn checkIn;
    protected CheckOut checkOut;
    protected ParkFrame parkFrame;
    public Park(int maxCar, ParkFrame parkFrame) {
        this.maxCar = maxCar;
        this.parkFrame=parkFrame;
        int n=maxCar/2;
        park=new boolean[5][n+2];//初始化二维数组
        for (boolean[] temp:park)
            for (int i=n+1;i>=0;--i){
                temp[i]=true;
            }
        //将不可停车的部分置为false;
        park[0][0]=park[1][0]=park[3][0]=park[4][0]=false;
        park[0][n+1]=park[1][n+1]=park[3][n+1]=park[4][n+1]=false;
        checkIn=new CheckIn(parkFrame.getStart().getConfig().getMaxEmployee(),parkFrame);
        checkOut=new CheckOut(parkFrame.getStart().getConfig().getMaxEmployee(),parkFrame);
    }
    public boolean getStatus(int x,int y){
        return park[x][y];
    }
    public void setStatusTrue(int x,int y){
       park[x][y]=true;
    }
    public void setStatusFalse(int x,int y){
        park[x][y]=false;
    }
    public boolean in(Employee employee){
        Message message=checkIn.checkEmployee(employee);
        if(message.getMessageType()==Message.MessageType.ok)
           return true;
        else
           return false;
    }
    public boolean out(Employee employee){
        Message message=checkOut.checkEmployee(employee);
        if(message.getMessageType()==Message.MessageType.ok)
            return true;
        else
            return false;
    }
}
