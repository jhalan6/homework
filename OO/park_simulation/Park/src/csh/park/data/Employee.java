package csh.park.data;

/**
 * Created by Alan on 15/12/9.
 */
public class Employee {
    protected int id;

    public Employee(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public boolean inEmployeeList(int maxEmployee){
        if (id<=maxEmployee&&id>=1){
            return true;
        }
        else return false;
    }
}
