package csh.park.ui;

import csh.park.car.Car;
import csh.park.check.CheckIn;
import csh.park.check.CheckOut;
import csh.park.console.Console;
import csh.park.data.PublicData;

import static csh.park.data.PublicData.*;

import javax.swing.*;
import java.awt.*;

/**
 * ParkFrame类实现了仿真界面,包括了:停车场的绘制,停车场中所有车辆的绘制,前后门禁动画的实现,控制台文字显示等功能
 * Created by Alan on 15/12/9.
 */
public class ParkFrame extends JPanel {
    /**
     * 内部维护的一个PublicData引用
     */
    private PublicData publicData;
    /**
     * 界面的宽度(横向)
     */
    private int width;
    /**
     * 界面的高度(纵向)
     */
    private int height;
    /**
     * 每一个方格的单位宽度
     */
    private int factor;
    /**
     * 在Frame中,停车场的方框的起始坐标的X值
     */
    private int startX;
    /**
     * 在Frame中,停车场的方框的起始坐标的Y值
     */
    private int startY;
    /**
     * Frame的大小
     */
    private Dimension frameSize;
    /**
     * 停车场部分左上角像素点的X坐标
     */
    private int startXofParkArea ;
    /**
     * 停车场部分左上角像素点的Y坐标
     */
    private int startYofParkArea ;

    /**
     * 默认构造方法,初始化了界面的大小和界面中的元素
     */
    public ParkFrame() {
        this.publicData = PublicData.getPublicData();
        initFrameSize();
        initFrame();
    }

    /**
     * 对界面的大小进行初始化设置
     */
    private void initFrameSize() {
        //在界面的坐上角空出横向15个像素点,纵向10个像素点
        startX = 15;
        startY = 10;
        //根据屏幕大小动态设置窗体大小
        Toolkit toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        //将宽度设置为屏幕高度的65%
        height = (int) (screenSize.height * 0.65);
        //根据这个高度设置单位宽度,因为纵向上总共包括1个单位宽度的控制台,4个宽度的停车位,1个宽度的停车场过道,
        // 所以宽度因数用除法进行设置
        factor = height / 6;
        //横向上包括4个单位宽度的停车场外的画面,停车场内2个不能停车位置的单位宽度,还有n个单位宽度的车位
        width = (publicData.getN() + 6) * factor;
        //对界面大小进行初始化,为了使用时方便
        frameSize = new Dimension(width, height);
        //初始化停车场区域(除控制台区域的部分)的左上角像素点位置
        startXofParkArea = startX;
        startYofParkArea = startY + factor;
    }

    /**
     * 初始化界面内部
     */
    private void initFrame() {
        //配置及初始化Frame
        JFrame jFrame = new JFrame();
        jFrame.setSize(width + 30, height + 45);
        // 设置不可调节大小
        jFrame.setResizable(false);
        // 设置关闭按钮
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置窗体居中
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("停车场仿真");
        // 设置面板背景为白
        this.setBackground(Color.white);
        this.setSize(frameSize);
        // 设置窗体可见
        jFrame.setVisible(true);
        // 将面板添加到窗
        jFrame.getContentPane().add(this, BorderLayout.CENTER);
    }

    /**
     * 绘制停车场仿真界面内的所有对象
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawParkLines(g);
        drawCarInParkFromCopy(g);
        drawBar(g);
        drawConsole(g);
        drawCarNumber(g);
    }

    /**
     * 绘制停车场界面中的所有线
     */
    private void drawParkLines(Graphics g) {
        //绘制停车场边界
        g.drawRect(startX, startY, width, height);
        //绘制控制台区域
        g.drawLine(startX, startY + factor, startX + width, startY + factor);
        //绘制停车场车位间隔线
        for (int i = 2; i < publicData.getN() + 5; i++) {
            g.drawLine(startX + factor * i, startY + factor, startX + factor * i, startY + factor * 3);
            g.drawLine(startX + factor * i, startY + factor * 4, startX + factor * i, startY + factor * 6);
        }
        g.drawLine(startX + 3 * factor, startY + 3 * factor, startX + width - 3 * factor, startY + 3 * factor);
        g.drawLine(startX + 3 * factor, startY + 4 * factor, startX + width - 3 * factor, startY + 4 * factor);
    }

    /**
     * 绘制停车场中车辆的车牌号
     */
    private void drawCarNumber(Graphics g) {
        //遍历所有车辆,找到车辆的相对位置,在车辆的中间位置画出车牌号
        for (Car car : publicData.getPark().getCarsInPark()) {
            int relativeX = (int) (((float) (car.getCarHeadX() + car.getCarTailX() + 1) / 2) * factor);
            int relativeY = (int) (((float) (car.getCarHeadY() + car.getCarTailY() + 1) / 2) * factor);
            g.drawString(Integer.toString(car.getNumber()), startXofParkArea + relativeY, startYofParkArea + relativeX);
        }
    }

    /**
     * 绘制控制台上的文字信息,包括当前时间,当前剩余车位数量,前后门状态等信息.
     */
    private void drawConsole(Graphics g) {
        Console console = publicData.getConsole();
        int consoleStartX = startX + 2 * factor, consoleStartY = (int) (startY + 0.5 * factor);
        //显示相对时间信息
        g.drawString(console.relativeTimeToString(), consoleStartX - 2 * factor, consoleStartY);
        //显示前门信息
        g.drawString(console.frontDoorToString(), consoleStartX, consoleStartY);
        //显示容纳量信息
        g.drawString(console.capabilityToString(), consoleStartX + factor * (publicData.getN() / 2 + 1), consoleStartY);
        //显示后门信息
        g.drawString(console.rearDoorToString(), consoleStartX + factor * (publicData.getN() + 2), consoleStartY);
    }

    /**
     * 绘制前后两个门禁,总共包括四条短线\两个可伸缩的门
     */
    private void drawBar(Graphics g) {
        //半条短线的长度
        int smallLineHalfLength = 10;
        //绘制门禁的四条短线
        g.drawLine(startX + 2 * factor - smallLineHalfLength, startY + 3 * factor, startX + 2 * factor + smallLineHalfLength, startY + 3 * factor);
        g.drawLine(startX + 2 * factor - smallLineHalfLength, startY + 4 * factor, startX + 2 * factor + smallLineHalfLength, startY + 4 * factor);
        g.drawLine(startX + 2 * factor - smallLineHalfLength + (2 + publicData.getN()) * factor, startY + 3 * factor, startX + 2 * factor + smallLineHalfLength + (2 + publicData.getN()) * factor, startY + 3 * factor);
        g.drawLine(startX + 2 * factor - smallLineHalfLength + (2 + publicData.getN()) * factor, startY + 4 * factor, startX + 2 * factor + smallLineHalfLength + (2 + publicData.getN()) * factor, startY + 4 * factor);
        //抽取前后两个门禁的数据信息
        CheckIn checkIn = publicData.getPark().getCheckIn();
        CheckOut checkOut = publicData.getPark().getCheckOut();
        //把刚刚拿到的数据信息根据门的状态进行显示
        //根据前门开闭状态更新前门GUI表示
        switch (checkIn.getBarStatus()) {
            case close:
                g.drawLine(startX + factor * 2, startY + factor * 3, startX + factor * 2, startY + factor * 4);
                break;
            case half:
                g.drawLine(startX + factor * 2, startY + factor * 3, startX + factor * 2, (int) (startY + factor * 3.5));
                break;
            case open:
                break;
        }
        //根据后门开闭状态更新后门GUI表示
        switch (checkOut.getBarStatus()) {
            case close:
                g.drawLine(startX + factor * (publicData.getN() + 4), startY + factor * 3, startX + factor * (publicData.getN() + 4), startY + factor * 4);
                break;
            case half:
                g.drawLine(startX + factor * (publicData.getN() + 4), startY + factor * 3, startX + factor * (publicData.getN() + 4), (int) (startY + factor * 3.5));
                break;
            case open:
                break;
        }

    }

    /**
     * 从一个停车场数组存储方式的副本中抽取出信息经分析后画出结果
     */
    private void drawCarInParkFromCopy(Graphics g) {
        boolean[][] parkCopy = publicData.getPark().getParkCopy();
        int lengthOfCopy = publicData.getN()+ 6;
        //showCopy(parkCopy);//调试用
        for (int i = 0; i < lengthOfCopy; ++i) {
            if (i != 2 && i != publicData.getN() + 3) {//两列不能停车的位置设置不在扫描范围内
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
    }

    /**
     * 调试用的,缩小的停车场表示方式
     *
     * @param parkCopy 一个停车场小模型二维数组(深度复制)
     */
    private void showCopy(boolean[][] parkCopy) {
        for (boolean[] b : parkCopy) {
            for (boolean c : b) {
                print.print(c + "\t");
            }
            print.println();
        }
    }
}
