package csh.park.check;

import csh.park.car.Car;
import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.park.Park;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/10.
 */
public class CheckOut extends CheckID{

    @Override
    public Message checkEmployee(Car car) {
        Employee employee=car.getOutData();
        if (contains(employee)||car.isHandled()) {
            publicData.getEmployeesInPark().remove(employee);
            openDoor();
            return new Message(Message.MessageType.ok,car);
        }else
        return new Message(Message.MessageType.not_found_in_system,car);
    }

    @Override
    public void run() {
        super.run();
    }
}
