package csh.park.ui;

import csh.park.car.Car;
import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.console.Console;
import csh.park.data.PublicData;

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
    public ParkFrame() {
        this.publicData = PublicData.getPublicData();
        startX=15;
        startY=10;
        height=420;
        factor=height/6;
        width=(publicData.getConfig().getMaxCar()/2+6)*factor;
        frameSize=new Dimension(width,height);
        //配置及初始化Frame
        JFrame jFrame=new JFrame();
        jFrame.setSize(width+30,height+45);
        jFrame.setDefaultCloseOperation(3);// 设置关闭按钮
        jFrame.setTitle("停车场仿真");
        this.setBackground(Color.white);// 设置面板背景为白
        this.setSize(frameSize);
        jFrame.setVisible(true);// 设置窗体可见
        jFrame.getContentPane().add(this, BorderLayout.CENTER);// 将面板添加到窗
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //绘制停车场边界
        g.drawRect(startX,startY,width,height);
        //绘制控制台区域
        g.drawLine(startX,startY+factor,startX+width,startY+factor);
        //绘制停车场车位间隔线
        for(int i = 2; i< publicData.getN()+5; i++){
            g.drawLine(startX+factor*i,startY+factor,startX+factor*i,startY+factor*3);
            g.drawLine(startX+factor*i,startY+factor*4,startX+factor*i,startY+factor*6);
        }
        g.drawLine(startX+3*factor,startY+3*factor,startX+width-3*factor,startY+3*factor);
        g.drawLine(startX+3*factor,startY+4*factor,startX+width-3*factor,startY+4*factor);
        boolean[][] parkCopy=publicData.getPark().getParkCopy();
        int lengthOfCopy = publicData.getPark().getHalfMax() + 6;
        int startXofParkArea = startX;
        int startYofParkArea = startY + factor;
//        for (boolean[] b:parkCopy){
//            for (boolean c:b){
//                System.err.print(c +"\t");
//            }
//            System.err.println();
//        }
        for (int i = 0; i < lengthOfCopy; ++i) {
            if (i != 2 && i != publicData.getN() + 3) {
                for (int j = 0; j < 2; ++j) {
                    if (!parkCopy[j][i]) {//遍历前两行,每行如果有车的话,这辆车一定是竖着的,并且以当前这个点为起点
                        g.drawRect(startXofParkArea + 3 + factor * i, startYofParkArea + 4 + factor * j, factor - 6, factor * 2 - 8);
                        parkCopy[j][i] = true;
                        parkCopy[j + 1][i] = true;
                    }
                    if (!parkCopy[4 - j][i]) {//遍历后两行,每行如果有车的话,这辆车一定是竖着的,并且以当前这个点为起点
                        g.drawRect(startXofParkArea + 3 + factor * i, startYofParkArea + 4 + factor * (3 - j), factor - 6, factor * 2 - 8);
                        parkCopy[4 - j][i] = true;
                        parkCopy[4 - j - 1][i] = true;
                    }
                }
            }
            if (!parkCopy[2][i]) {//遍历中间一行如果有车就画出来
                g.drawRect(startXofParkArea + factor * i + 4, startYofParkArea + factor * 2 + 3, factor * 2 - 8, factor - 6);
                parkCopy[2][i] = true;
                parkCopy[2][i + 1] = true;
            }
        }
        //抽取前后两个门禁的数据信息
        CheckIn checkIn=publicData.getPark().getCheckIn();
        CheckOut checkOut=publicData.getPark().getCheckOut();
        //把刚刚拿到的数据信息根据门的状态进行显示
        switch (checkIn.getBarStatus()){
            case close:
                g.drawLine(startX+factor*2,startY+factor*3,startX+factor*2,startY+factor*4);
                break;
            case half:
                g.drawLine(startX+factor*2,startY+factor*3,startX+factor*2,(int) (startY+factor*3.5));
                break;
            case open:
                break;
        }
        switch (checkOut.getBarStatus()){
            case close:
                g.drawLine(startX+factor*(publicData.getN()+4),startY+factor*3,startX+factor*(publicData.getN()+4),startY+factor*4);
                break;
            case half:
                g.drawLine(startX+factor*(publicData.getN()+4),startY+factor*3,startX+factor*(publicData.getN()+4),(int) (startY+factor*3.5));
                break;
            case open:
                break;
        }
        Console console=publicData.getConsole();
        int consoleStartX=startX+2*factor,consoleStartY=(int) (startY+0.5*factor);
        g.drawString(console.toString(),consoleStartX,consoleStartY);
        startXofParkArea = startX;
        startYofParkArea = startY + factor;
        for (Car car:publicData.getPark().getCarsInPark()){
            int relativeX=(int) (((float)(car.getCarHeadX()+car.getCarTailX()+1)/2)*factor);
            int relativeY=(int) (((float)(car.getCarHeadY()+car.getCarTailY()+1)/2)*factor);
            g.drawString(Integer.toString(car.getNumber()),startXofParkArea+relativeY,startYofParkArea+relativeX);
        }
    }
}
