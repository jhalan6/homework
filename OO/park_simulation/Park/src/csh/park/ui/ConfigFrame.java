package csh.park.ui;

import csh.park.actionListener.OkListener;
import csh.park.actionListener.SaveListener;
import csh.park.data.SimulationConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Alan on 15/12/9.
 */
public class ConfigFrame extends JPanel{
    public JFrame getjFrame() {
        return jFrame;
    }

    public JTextField getTextMaxEmployee() {
        return textMaxEmployee;
    }

    public JTextField getTextMaxCar() {
        return textMaxCar;
    }

    public JTextField getTextRateOfNotEmployee() {
        return textRateOfNotEmployee;
    }

    public JTextField getTextRateOfError() {
        return textRateOfError;
    }

    public SimulationConfig getConfig() {
        return config;
    }

    public Start getStart() {
        return start;
    }

    JFrame jFrame;
    JTextField textMaxEmployee, textMaxCar,textRateOfNotEmployee,textRateOfError;
    JButton btn_save,btn_ok;
    ActionListener okListener,saveListener;
    SimulationConfig config;
    Start start;
    public ConfigFrame(Start start) {
        this.start=start;
        this.config=start.config;
        try {
            ObjectInputStream input=new ObjectInputStream(new FileInputStream("/tmp/SimulationOfPark.config"));
            config=(SimulationConfig) input.readObject();
            System.out.println(this.config);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI();

        //添加四个配置属性的内容
        //配置员工上限(用已保存的数据进行初始化)
        jFrame.add(new JLabel("员工上限(请输入一个整数):"));
        jFrame.add(textMaxEmployee);
        textMaxEmployee.setText(Integer.toString(this.config.getMaxEmployee()));
        //配置车辆上限(用已保存的数据进行初始化)
        jFrame.add(new JLabel("车位上限(请输入一个整数):"));
        jFrame.add(textMaxCar);
        textMaxCar.setText(Integer.toString(this.config.getMaxCar()));
        //配置可能发生的错误信息(用已保存的数据进行初始化)
        jFrame.add(new JLabel("非员工概率(请输入一个浮点数):"));
        jFrame.add(textRateOfNotEmployee);
        textRateOfNotEmployee.setText(Float.toString(this.config.getRateOfNotEmployee()));
        //配置可能进出数据不一致的信息(用已保存的数据进行初始化)
        jFrame.add(new JLabel("进出不一致概率(请输入一个浮点数):"));
        jFrame.add(textRateOfError);
        textRateOfError.setText(Float.toString(this.config.getRateOfError()));
        //初始化两个监听器
        okListener=new OkListener(this);
        saveListener=new SaveListener(this);

        //配置两个按钮及其功能
        jFrame.add(btn_ok);
        jFrame.add(btn_save);
        btn_ok.addActionListener(okListener);
        btn_save.addActionListener(saveListener);


        jFrame.setSize(430,280);
        jFrame.setResizable(false);// 设置不可调节大小
        jFrame.setDefaultCloseOperation(3);// 设置关闭按钮
        jFrame.setLocationRelativeTo(null);// 设置窗体居中
        this.setBackground(Color.white);// 设置面板背景为白色
        this.setSize(new Dimension(430,280));
        jFrame.setVisible(true);// 设置窗体可见
        jFrame.getContentPane().add(this, BorderLayout.CENTER);// 将面板添加到窗
    }

    private void initUI() {
        //初始化配置页面
        jFrame=new JFrame("仿真配置");
        jFrame.setLayout(new GridLayout(5,2));
        //初始化文本框
        textMaxEmployee =new JTextField(4);
        textMaxCar =new JTextField(2);
        textRateOfNotEmployee=new JTextField(6);
        textRateOfError =new JTextField(6);
        //初始化两个按钮
        btn_ok=new JButton("确定");
        btn_save=new JButton("保存");
    }


}
