package csh.park.check;

import csh.park.data.Employee;
import csh.park.ui.ParkFrame;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/10.
 */
public class CheckOut extends CheckID{
    public CheckOut(int maxEmployee, ParkFrame pf) {
        super(maxEmployee,pf);
    }

    @Override
    public Message checkEmployee(Employee employee) {
        // TODO: 15/12/10  
        return null;
    }

    @Override
    public void run() {
        super.run();
    }
}
