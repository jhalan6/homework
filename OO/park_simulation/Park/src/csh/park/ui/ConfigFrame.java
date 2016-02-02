package csh.park.ui;

import csh.park.ui.actionListener.OkListener;
import csh.park.ui.actionListener.SaveListener;
import csh.park.data.PublicData;
import csh.park.data.SimulationConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * ConfigFrame类,实现了配置页面,用来实现初始化数据信息
 * Created by Alan on 15/12/9.
 */
public class ConfigFrame extends JPanel{
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
        initUI();//初始化UI
    }
   public JFrame getjFrame() {
        return jFrame;
    }

    public FrameParts getFrameParts(){
        return frameParts;
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

        frameParts=new FrameParts(textMaxEmployee,textMaxCar,textRateOfNotEmployee,textRateOfError);

        //初始化两个按钮
        btn_ok=new JButton("确定");
        btn_save=new JButton("保存");

        //绑定两个按钮
        btn_ok.addActionListener(new OkListener(this));
        btn_save.addActionListener(new SaveListener(this));

        //添加两个按钮
        jFrame.add(btn_ok);
        jFrame.add(btn_save);

        jFrame.setSize(430,280);
        jFrame.setResizable(false);// 设置不可调节大小
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// 设置关闭按钮
        jFrame.setLocationRelativeTo(null);// 设置窗体居中
        setBackground(Color.white);// 设置面板背景为白色
        setSize(new Dimension(430,280));
        jFrame.setVisible(true);// 设置窗体可见
        jFrame.getContentPane().add(this, BorderLayout.CENTER);// 将面板添加到窗

    }

    private void initText() {
        textMaxCar.setText(Integer.toString(config.getMaxCar()));
        textRateOfNotEmployee.setText(Double.toString(config.getRateOfNotEmployee()));
        textRateOfError.setText(Double.toString(config.getRateOfError()));
        textMaxEmployee.setText(Integer.toString(config.getMaxEmployee()));
    }
}