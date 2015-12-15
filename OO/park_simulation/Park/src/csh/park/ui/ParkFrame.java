package csh.park.ui;

import csh.park.data.PublicData;
import csh.park.park.Park;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alan on 15/12/9.
 */
public class ParkFrame extends JPanel {

    protected PublicData publicData;
    int width,height,factor;
    int startX,startY;
    Dimension frameSize;
    public ParkFrame(PublicData publicData) {
        this.publicData = publicData;
        initFrameSize();
        initFrame();
    }

    private void initFrame() {
        //配置及初始化Frame
        JFrame jFrame=new JFrame();
        jFrame.setSize(width+30,height+45);
        jFrame.setResizable(false);// 设置不可调节大小
        jFrame.setDefaultCloseOperation(3);// 设置关闭按钮
        jFrame.setLocationRelativeTo(null);// 设置窗体居中
        jFrame.setTitle("停车场仿真");
        this.setBackground(Color.white);// 设置面板背景为白
        this.setSize(frameSize);
        jFrame.setVisible(true);// 设置窗体可见
        jFrame.getContentPane().add(this, BorderLayout.CENTER);// 将面板添加到窗
    }

    private void initFrameSize() {
        startX=15;
        startY=10;
        //根据屏幕大小动态设置窗体大小
        Toolkit toolkit=getToolkit();
        Dimension screenSize=toolkit.getScreenSize();
        height=(int)(screenSize.height*0.65);
        factor=height/6;
        width=(publicData.getConfig().getMaxCar()/2+6)*factor;
        frameSize=new Dimension(width,height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //绘制停车场边界
        g.drawRect(startX,startY,width,height);
        //绘制控制台区域
        g.drawLine(startX,startY+factor,startX+width,startY+factor);
        //绘制停车场车位间隔线
        for(int i = 2; i< publicData.getConfig().getMaxCar()/2+5; i++){
            g.drawLine(startX+factor*i,startY+factor,startX+factor*i,startY+factor*3);
            g.drawLine(startX+factor*i,startY+factor*4,startX+factor*i,startY+factor*6);
        }
        g.drawLine(startX+2*factor,startY+3*factor,startX+width-2*factor,startY+3*factor);
        g.drawLine(startX+2*factor,startY+4*factor,startX+width-2*factor,startY+4*factor);
        // TODO: 15/12/10 画所有车辆
    }

}
