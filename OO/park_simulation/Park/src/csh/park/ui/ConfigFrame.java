package csh.park.ui;

import csh.park.data.PublicData;
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

    public FrameParts getFrameParts(){
        return frameParts;
    }

    JFrame jFrame;
    JTextField textMaxEmployee, textMaxCar,textRateOfNotEmployee,textRateOfError;
    JButton btn_save,btn_ok;
    SimulationConfig config;
    PublicData publicData;
    FrameParts frameParts;

    public ConfigFrame()
    {
        this.publicData = PublicData.getPublicData();
        config= publicData.getConfig();
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
        textMaxCar.setText(Integer.toString(config.getMaxCar()));
        textRateOfNotEmployee.setText(Double.toString(config.getRateOfNotEmployee()));
        textRateOfError.setText(Double.toString(config.getRateOfError()));
        textMaxEmployee.setText(Integer.toString(config.getMaxEmployee()));

        frameParts=new FrameParts(textMaxEmployee,textMaxCar,textRateOfNotEmployee,textRateOfError,jFrame);

        //初始化两个按钮
        btn_ok=new JButton("确定");
        btn_save=new JButton("保存");
        //初始化两个监听器

        //绑定两个按钮
        btn_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PublicData publicData=PublicData.getPublicData();
                SimulationConfig config=publicData.getConfig();
                config.setSimulationConfig(getFrameParts());
                getjFrame().dispose();
                publicData.initParkFrame();
            }
        });
        btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationConfig config=SimulationConfig.getConfig();
                config.setSimulationConfig(frameParts);
                try {
                    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("/tmp/SimulationOfPark.config"));
                    output.writeObject(config);
                    System.out.println(config);
                    output.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(getjFrame(),"保存失败",null,JOptionPane.QUESTION_MESSAGE);
                }
                JOptionPane.showMessageDialog(getjFrame(),"保存成功!",null,JOptionPane.QUESTION_MESSAGE);
            }
        });

        //添加两个按钮
        jFrame.add(btn_ok);
        jFrame.add(btn_save);
        jFrame.setSize(430,280);
        jFrame.setDefaultCloseOperation(3);// 设置关闭按钮
        setBackground(Color.white);// 设置面板背景为白色
        setSize(new Dimension(430,280));
        jFrame.setVisible(true);// 设置窗体可见
        jFrame.getContentPane().add(this, BorderLayout.CENTER);// 将面板添加到窗
    }
}
