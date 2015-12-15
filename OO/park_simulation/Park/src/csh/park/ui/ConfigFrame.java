package csh.park.ui;

import csh.park.actionListener.OkListener;
import csh.park.actionListener.SaveListener;
import csh.park.data.PublicData;
import csh.park.data.SimulationConfig;

import javax.swing.*;
import java.awt.*;
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

    public PublicData getPublicData() {
        return publicData;
    }

    JFrame jFrame;
    JTextField textMaxEmployee, textMaxCar,textRateOfNotEmployee,textRateOfError;
    JButton btn_save,btn_ok;
    ActionListener okListener,saveListener;
    SimulationConfig config;
    PublicData publicData;
    public ConfigFrame(PublicData publicData) {
        this.publicData = publicData;
        this.config= publicData.getConfig();
        initUI();//初始化UI
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

        //添加四个配置属性的内容
        jFrame.add(new JLabel("员工上限(请输入一个整数):"));
        jFrame.add(textMaxEmployee);
        jFrame.add(new JLabel("车位上限(请输入一个整数):"));
        jFrame.add(textMaxCar);
        jFrame.add(new JLabel("非员工概率(请输入一个浮点数):"));
        jFrame.add(textRateOfNotEmployee);
        jFrame.add(new JLabel("进出不一致概率(请输入一个浮点数):"));
        jFrame.add(textRateOfError);
        initText();

        //初始化两个按钮
        btn_ok=new JButton("确定");
        btn_save=new JButton("保存");
        //初始化两个监听器
        okListener=new OkListener(this);
        saveListener=new SaveListener(this);

        //绑定两个按钮
        btn_ok.addActionListener(okListener);
        btn_save.addActionListener(saveListener);

        //添加两个按钮
        jFrame.add(btn_ok);
        jFrame.add(btn_save);

        jFrame.setSize(430,280);
        jFrame.setResizable(false);// 设置不可调节大小
        jFrame.setDefaultCloseOperation(3);// 设置关闭按钮
        jFrame.setLocationRelativeTo(null);// 设置窗体居中
        setBackground(Color.white);// 设置面板背景为白色
        setSize(new Dimension(430,280));
        jFrame.setVisible(true);// 设置窗体可见
        jFrame.getContentPane().add(this, BorderLayout.CENTER);// 将面板添加到窗

    }

    private void initText() {
        try {
            ObjectInputStream input=new ObjectInputStream(new FileInputStream("/tmp/SimulationOfPark.config"));
            config=(SimulationConfig) input.readObject();
            System.out.println(config);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        textMaxCar.setText(Integer.toString(config.getMaxCar()));
        textRateOfNotEmployee.setText(Float.toString(config.getRateOfNotEmployee()));
        textRateOfError.setText(Float.toString(config.getRateOfError()));
        textMaxEmployee.setText(Integer.toString(config.getMaxEmployee()));
    }
}
