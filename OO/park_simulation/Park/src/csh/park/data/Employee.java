package csh.park.data;

/**
 * Employee类包含了员工的相关信息,目前仅包含id信息1项
 * Created by Alan on 15/12/9.
 */
public class Employee {
    /**
     * 员工工号
     */
    protected int id;
    /**
     * 公司员工编号的范围
     */
    protected static final int maxEmployee=PublicData.getPublicData().getConfig().getMaxEmployee();

    /**
     * 通过员工工号初始化Employee
     * @param id 员工编号
     */
    public Employee(int id) {
        this.id = id;
    }

    /**
     * 返回员工的工号
     * @return 工号
     */
    public int getId() {
        return id;
    }

    /**
     * 检验当前Employee对象是否是公司员工
     * @return 是公司员工返回true,反之则返回false
     */
    public boolean inEmployeeList(){
        return (id<=maxEmployee&&id>=1);
    }
    @Override
    public boolean equals(Object obj) {
        return ((Employee)obj).getId()==id;
    }
}
