package csh.park.check;

import csh.park.car.Car;
import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/10.
 */
public class CheckIn extends Thread{

    public int maxEmployee;
    protected PublicData publicData;
    protected BarStatus barStatus= BarStatus.close;
    protected DoorStatus doorStatus= DoorStatus.nothing;

    public Message checkEmployee(Car car) {
        this.publicData = PublicData.getPublicData();
        maxEmployee= publicData.getConfig().getMaxEmployee();
        Employee employee=car.getInData();
       if (employee.inEmployeeList(maxEmployee)&&!contains(employee)){
           publicData.getEmployeesInPark().add(employee);
           openDoor();
           return new Message(Message.MessageType.ok,car);
       }else{
           try {
               doorStatus=DoorStatus.no;
               publicData.getParkFrame().repaint();
               sleep(1000);
               doorStatus=DoorStatus.nothing;
               publicData.getParkFrame().repaint();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           publicData.getInError().add(new Message(Message.MessageType.not_found_in_register,car));
           return new Message(Message.MessageType.not_found_in_system,car);
       }
    }

    public void carHadBeenIn() {
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
