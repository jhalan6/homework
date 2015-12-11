package csh.park.data;

import java.io.Serializable;

/**
 * Created by Alan on 15/12/9.
 */
public class SimulationConfig implements Serializable{
    /**
     * 停车场的最大容纳量
     */
    protected int maxCar;
    /**
     * 员工数量
     */
    protected int maxEmployee;
    /**
     * 车辆非本公司员工比例
     */
    protected float rateOfNotEmployee;
    /**
     * 入场员工号与出场员工号不一致的比例
     */
    protected float rateOfError;

    public float getRateOfNotEmployee() {
        return rateOfNotEmployee;
    }

    public void setRateOfNotEmployee(float rateOfNotEmployee) {
        this.rateOfNotEmployee = rateOfNotEmployee;
    }

    public float getRateOfError() {
        return rateOfError;
    }

    public void setRateOfError(float rateOfError) {
        this.rateOfError = rateOfError;
    }

    public int getMaxCar() {
        return maxCar;
    }
    public void setMaxCar(int maxCar) {
        if (maxCar<=16)
            this.maxCar = maxCar;
        else
            this.maxCar=16;
    }
    public int getMaxEmployee() {
        return maxEmployee;
    }
    public void setMaxEmployee(int maxEmployee) {
        this.maxEmployee = maxEmployee;
    }

    @Override
    public String toString() {
        return "Employee:"+maxEmployee+"\nCar:"+maxCar+"\nRateOfNotEmployee:"+rateOfNotEmployee+"\nRateOfError:"+rateOfError;
    }
}
