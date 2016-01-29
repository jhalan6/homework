package csh.park.check;

import csh.park.car.Car;
import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/10.
 */
public class CheckOut extends Thread{

    public int maxEmployee;
    protected PublicData publicData;
    protected BarStatus barStatus= BarStatus.close;
    protected DoorStatus doorStatus= DoorStatus.nothing;

    public Message checkEmployee(Car car) {
        this.publicData = PublicData.getPublicData();
        maxEmployee= publicData.getConfig().getMaxEmployee();
        Employee employee=car.getOutData();
        if (contains(employee)||car.isHandled()) {
            publicData.getEmployeesInPark().remove(employee);
            openDoor();
            return new Message(Message.MessageType.ok,car);
        }else
        return new Message(Message.MessageType.not_found_in_system,car);
    }

    public void carHadBeenOut() {
        try {
            sleep(1000);
            barStatus= BarStatus.half;
            publicData.getParkFrame().repaint();
            sleep(1000);
            barStatus= BarStatus.close;
            publicData.getParkFrame().repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void openDoor() {
        try {
            doorStatus= DoorStatus.yes;
            sleep(1000);
            barStatus= BarStatus.half;
            publicData.getParkFrame().repaint();
            sleep(1000);
            barStatus= BarStatus.open;
            doorStatus= DoorStatus.nothing;
            publicData.getParkFrame().repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BarStatus getBarStatus() {
        return barStatus;
    }

    public DoorStatus getDoorStatus(){return doorStatus;}

    protected boolean contains(Employee employee){
        return publicData.getEmployeesInPark().contains(employee);
    }

    public enum BarStatus {open,half, close}

    public enum DoorStatus{yes,no,nothing}
}
