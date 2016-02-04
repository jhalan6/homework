package csh.park.check;

import csh.park.car.Car;
import csh.park.data.Employee;
import csh.park.data.PublicData;

import static csh.park.data.PublicData.*;

import csh.park.warning.Message;


/**
 * Created by Alan on 15/12/10.
 * CheckID是一个门禁类,提供对门的开闭属性的设置
 */
public abstract class CheckID extends Thread {
    protected PublicData publicData;
    /**
     * 门的栏杆状态,默认为关
     */
    protected BarStatus barStatus = BarStatus.close;
    /**
     * 门的属性状态,默认为空
     */
    protected DoorStatus doorStatus = DoorStatus.nothing;

    /**
     * 门的属性状态:开\闭\半开
     */
    public enum BarStatus {
        /**
         * 开
         */
        open,
        /**
         * 半开
         */
        half,
        /**
         * 关
         */
        close
    }

    /**
     * 门的属性状态:通过,禁止通过
     */
    public enum DoorStatus {
        /**
         * 允许通过
         */
        yes,
        /**
         * 禁止通行
         */
        no,
        /**
         * 默认状态
         */
        nothing
    }

    public CheckID() {
        this.publicData = PublicData.getPublicData();
    }

    /**
     * 检测到车辆符合开门状态后的栏杆以及门禁状态顺序
     */
    protected void openDoor() {
        try {
            doorStatus = DoorStatus.yes;
            sleep(MID_TIME);
            barStatus = BarStatus.half;
            publicData.getParkFrame().repaint();
            sleep(MID_TIME);
            barStatus = BarStatus.open;
            doorStatus = DoorStatus.nothing;
            publicData.getParkFrame().repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测到车辆的尾灯离开栏杆后的栏杆状态顺序
     */
    public void carHadBeenLeaveBar() {
        try {
            sleep(MID_TIME);
            barStatus = BarStatus.half;
            publicData.getParkFrame().repaint();
            sleep(MID_TIME);
            barStatus = BarStatus.close;
            publicData.getParkFrame().repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验员工是否是本公司员工
     *
     * @param employee 员工信息
     * @return 是本公司员工返回true, 否则返回false
     */
    protected boolean contains(Employee employee) {
        return publicData.getEmployeesInPark().contains(employee);
    }

    /**
     * barStatus的get方法,用来读取当前栏杆状态
     *
     * @return 开:BarStatus.open\闭:BarStatus.close\半开:Barstatus.half
     */
    public BarStatus getBarStatus() {
        return barStatus;
    }

    /**
     * doorStatus的get方法,用来读取门禁状态
     *
     * @return 允许通过:DoorStatus.yes\禁止通行:DoorStatus.no\无状态信息:DoorStatus.nothing
     */
    public DoorStatus getDoorStatus() {
        return doorStatus;
    }

    public abstract Message checkEmployee(Car car);
}
