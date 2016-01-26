package csh.park.check;

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

    public CheckID() {
        this.publicData = PublicData.getPublicData();
        maxEmployee= publicData.getConfig().getMaxEmployee();
    }
    protected boolean contains(Employee employee){
        return publicData.getEmployeesInPark().contains(employee);
    }

    abstract Message checkEmployee(Employee employee);
}
