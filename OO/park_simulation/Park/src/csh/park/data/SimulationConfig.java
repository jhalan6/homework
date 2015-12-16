package csh.park.data;

import csh.park.ui.FrameParts;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Created by Alan on 15/12/9.
 */
public class SimulationConfig implements Serializable{
    private static SimulationConfig singleConfig;
    public static SimulationConfig getConfig(){
        if (singleConfig==null){
            try {
                ObjectInputStream input=new ObjectInputStream(new FileInputStream("/tmp/SimulationOfPark.config"));
                singleConfig=(SimulationConfig) input.readObject();
                System.out.println(singleConfig);
                input.close();
                return singleConfig;
            } catch (Exception e) {
                singleConfig=new SimulationConfig(10,10,0.1,0.1);
                PublicData.print.println("未能找到之前保存的信息,已用系统内置数据进行初始化");
                return singleConfig;
            }
        }else return singleConfig;
    }

    private SimulationConfig(int maxCar, int maxEmployee, double rateOfNotEmployee, double rateOfError) {
        this.maxCar = maxCar;
        this.maxEmployee = maxEmployee;
        this.rateOfNotEmployee = rateOfNotEmployee;
        this.rateOfError = rateOfError;
    }

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
    protected double rateOfNotEmployee;
    /**
     * 入场员工号与出场员工号不一致的比例
     */
    protected double rateOfError;
    public void setSimulationConfig(FrameParts frameParts){
        try {
            setMaxCar(Integer.parseInt(frameParts.getTextMaxCar().getText().trim()));
            singleConfig.maxEmployee=Integer.parseInt(frameParts.getTextMaxEmployee().getText().trim());
            singleConfig.rateOfNotEmployee=Double.valueOf(frameParts.getTextRateOfNotEmployee().getText().trim());
            singleConfig.rateOfError=Double.valueOf(frameParts.getTextRateOfError().getText().trim());
        }catch (NumberFormatException ee){
            JOptionPane.showMessageDialog(frameParts.getTextMaxCar(),"数据错误!",null,JOptionPane.QUESTION_MESSAGE);
            throw new RuntimeException("输入数据错误!");
        }
    }
    public double getRateOfNotEmployee() {
        return rateOfNotEmployee;
    }

    public double getRateOfError() {
        return rateOfError;
    }

    public int getMaxCar() {
        return maxCar;
    }
    public int getMaxEmployee() {
        return maxEmployee;
    }

    @Override
    public String toString() {
        return "Employee:"+maxEmployee+"\nCar:"+maxCar+"\nRateOfNotEmployee:"+rateOfNotEmployee+"\nRateOfError:"+rateOfError;
    }

    private void setMaxCar(int maxCar) {
        if (maxCar<=16)
            this.maxCar = maxCar;
        else
            this.maxCar=16;
    }
}
