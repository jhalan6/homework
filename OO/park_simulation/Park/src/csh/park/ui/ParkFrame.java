package csh.park.ui;

import csh.park.car.CarFactory;
import csh.park.data.SimulationConfig;
import csh.park.park.Park;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alan on 15/12/9.
 */
public class ParkFrame extends JPanel {
    Park park;

    public Start getStart() {
        return start;
    }

    protected Start start;
    int width,height,factor;
    int startX,startY;
    public ParkFrame(Start start) {
        this.start=start;
        startX=15;
        startY=10;
        factor=80;
        width=(start.getConfig().getMaxCar()/2+6)*80;
        height=factor*6;
        JFrame jFrame=new JFrame();
        jFrame.setSize(width+30,height+45);
        jFrame.setResizable(false);// 设置不可调节大小
        jFrame.setDefaultCloseOperation(3);// 设置关闭按钮
        jFrame.setLocationRelativeTo(null);// 设置窗体居中
        jFrame.setTitle("停车场仿真");
        this.setBackground(Color.white);// 设置面板背景为白色
        this.setSize(new Dimension(width,height));
        jFrame.setVisible(true);// 设置窗体可见
        jFrame.getContentPane().add(this, BorderLayout.CENTER);// 将面板添加到窗
        park=new Park(start.getConfig().getMaxCar(),this);
        CarFactory carFactory=new CarFactory(park, start.getConfig());
        carFactory.run();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(startX,startY,width,height);
        g.drawLine(startX,startY+factor,startX+width,startY+factor);
        for(int i=2;i<start.getConfig().getMaxCar()/2+5;i++){
            g.drawLine(startX+factor*i,startY+factor,startX+factor*i,startY+factor*3);
            g.drawLine(startX+factor*i,startY+factor*4,startX+factor*i,startY+factor*6);
        }
        g.drawLine(startX+2*factor,startY+3*factor,startX+width-2*factor,startY+3*factor);
        g.drawLine(startX+2*factor,startY+4*factor,startX+width-2*factor,startY+4*factor);
        // TODO: 15/12/10 画停车场
        // TODO: 15/12/10 画所有车辆
    }

}
