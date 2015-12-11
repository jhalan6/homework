package csh.park.check;

import csh.park.data.Employee;
import csh.park.ui.ParkFrame;
import csh.park.warning.Message;

/**
 * Created by Alan on 15/12/10.
 */
public class CheckIn extends CheckID {
    public CheckIn(int maxEmployee, ParkFrame pf) {
        super(maxEmployee,pf);
    }

    @Override
    public Message checkEmployee(Employee employee) {
       if (employee.inEmployeeList(maxEmployee)){
           // TODO: 15/12/10
       }else{
           // TODO: 15/12/10
       }
        return null;
    }

    @Override
    public void run() {
        super.run();
    }
}
