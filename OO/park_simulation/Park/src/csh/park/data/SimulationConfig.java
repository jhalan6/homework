package csh.park.data;

import csh.park.ui.FrameParts;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * SimulationConfig类,实现了对配置相关数据的集中管理,同时实现了序列化接口,保存配置属性就是通过输出这个类到文件中实现的.
 * 这个类和PublicData一样都是用单例式实现的数据管理方式
 * Created by Alan on 15/12/9.
 */
public class SimulationConfig implements Serializable {
    /**
     * 单例式设计模式中所维持的一个引用
     */
    private static SimulationConfig singleConfig;
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

    /**
     * 默认的私有构造函数
     * @param maxCar 停车场容纳量的上限
     * @param maxEmployee 员工的最大员工编号
     * @param rateOfNotEmployee 随机生成的门夹卡中非本公司员工的比例
     * @param rateOfError 随机生成的进门门卡与出门门卡不同的数量
     */
    private SimulationConfig(int maxCar, int maxEmployee, double rateOfNotEmployee, double rateOfError) {
        this.maxCar = maxCar;
        this.maxEmployee = maxEmployee;
        this.rateOfNotEmployee = rateOfNotEmployee;
        this.rateOfError = rateOfError;
    }

    /**
     * 获取仿真配置信息
     * @return 一个SimulationConfig对象的引用,这个饮用可能是读取自配置文件中,也可能是用默认配置10,10,0.1,0.1初始化的一个配置
     */
    public static SimulationConfig getConfig() {
        if (singleConfig == null) {
            try {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream("/tmp/SimulationOfPark.config"));
                singleConfig = (SimulationConfig) input.readObject();
                System.out.println(singleConfig);
                input.close();
                return singleConfig;
            } catch (Exception e) {
                singleConfig = new SimulationConfig(10, 10, 0.1, 0.1);
                PublicData.print.println("未能找到之前保存的信息,已用系统内置数据进行初始化");
                return singleConfig;
            }
        }
        return singleConfig;
    }

    /**
     * 用当前界面中的数据实例化SimulationConfig对象
     * @param frameParts 含有配置页面控件引用的一个集合
     */
    public void setSimulationConfig(FrameParts frameParts) {
        try {
            setMaxCar(Integer.parseInt(frameParts.getTextMaxCar().getText().trim()));
            singleConfig.maxEmployee = Integer.parseInt(frameParts.getTextMaxEmployee().getText().trim());
            singleConfig.rateOfNotEmployee = Double.valueOf(frameParts.getTextRateOfNotEmployee().getText().trim());
            singleConfig.rateOfError = Double.valueOf(frameParts.getTextRateOfError().getText().trim());
        } catch (NumberFormatException ee) {
            JOptionPane.showMessageDialog(frameParts.getTextMaxCar(), "数据错误!", null, JOptionPane.QUESTION_MESSAGE);
            throw new RuntimeException("输入数据错误!");
        }
    }

    /**
     * 为了保证仿真效果,讲停车场的上限设置为16,如果用大于16的数来初始化配置信息,则用16替代之
     * 可以改变这个方法使仿真软件可以仿真容量大于16的情况
     * @param maxCar 想要让停车场仿真的最大容纳量
     */
    private void setMaxCar(int maxCar) {
        if (maxCar <= 16&&maxCar>=0)
            this.maxCar = maxCar;
        else
            this.maxCar = 16;
    }

    @Override
    public String toString() {
        return "Employee:" + maxEmployee + "\nCar:" + maxCar + "\nRateOfNotEmployee:" + rateOfNotEmployee + "\nRateOfError:" + rateOfError;
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

}
