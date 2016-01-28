package csh.park.check;

import csh.park.car.Car;
import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/10.
 */
public class CheckIn extends CheckID {

    @Override
    public Message checkEmployee(Car car) {
        Employee employee=car.getInData();
       if (employee.inEmployeeList(maxEmployee)&&!contains(employee)){
           publicData.getEmployeesInPark().add(employee);
           openDoor();
           return new Message(Message.MessageType.ok,car);
       }else{
           try {
               doorStatus=DoorStatus.no;
               publicData.getParkFrame().repaint();
               sleep(PublicData.midTime);
               doorStatus=DoorStatus.nothing;
               publicData.getParkFrame().repaint();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           publicData.getInError().add(new Message(Message.MessageType.not_found_in_register,car));
           return new Message(Message.MessageType.not_found_in_system,car);
       }
    }

    @Override
    public void run() {
        super.run();
    }
}
