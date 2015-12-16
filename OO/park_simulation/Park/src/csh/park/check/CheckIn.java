package csh.park.check;

import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/10.
 */
public class CheckIn extends CheckID {


    @Override
    public Message checkEmployee(Employee employee) {
       if (employee.inEmployeeList(maxEmployee)&&!publicData.getEmployeesInPark().contains(employee)){
           publicData.getEmployeesInPark().add(employee);
           return new Message(Message.MessageType.ok);
       }else{
           publicData.getInError().add(new Message(Message.MessageType.not_found_in_system));
           return new Message(Message.MessageType.not_found_in_system);
       }
    }

    @Override
    public void run() {
        super.run();
    }
}
