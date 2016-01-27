package csh.park.check;

import csh.park.car.Car;
import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.warning.Message;


/**
 * Created by Alan on 15/12/10.
 * CheckID是一个门禁类,同时控制绘制铁门
 */
public abstract class CheckID extends Thread{
    public final int maxEmployee;
    protected PublicData publicData;
    protected BarStatus barStatus=BarStatus.close;
    protected DoorStatus doorStatus=DoorStatus.nothing;

    public void carHadBeenLeaveBar() {
        try {
            sleep(PublicData.midTime);
            barStatus=BarStatus.half;
            publicData.getParkFrame().repaint();
            sleep(PublicData.midTime);
            barStatus=BarStatus.close;
            publicData.getParkFrame().repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void openDoor() {
        try {
            doorStatus= DoorStatus.yes;
            sleep(PublicData.midTime);
            barStatus= BarStatus.half;
            publicData.getParkFrame().repaint();
            sleep(PublicData.midTime);
            barStatus= BarStatus.open;
            doorStatus= DoorStatus.nothing;
            publicData.getParkFrame().repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public enum BarStatus {open,half, close}
    public enum DoorStatus{yes,no,nothing}

    public BarStatus getBarStatus() {
        return barStatus;
    }
    public DoorStatus getDoorStatus(){return doorStatus;}
    public CheckID() {
        this.publicData = PublicData.getPublicData();
        maxEmployee= publicData.getConfig().getMaxEmployee();
    }
    protected boolean contains(Employee employee){
        return publicData.getEmployeesInPark().contains(employee);
    }

    public abstract Message checkEmployee(Car car);
}
