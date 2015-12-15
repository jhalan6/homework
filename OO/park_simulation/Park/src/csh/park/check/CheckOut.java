package csh.park.check;

import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/10.
 */
public class CheckOut extends CheckID{

    public CheckOut(PublicData publicData) {
        super(publicData);
    }

    @Override
    public Message checkEmployee(Employee employee) {
        if (publicData.getEmployeesInPark().contains(employee)) {
            return new Message(Message.MessageType.ok);
        }
//        else if ()
        // TODO: 15/12/14  
        return null;
    }

    @Override
    public void run() {
        super.run();
    }
}
