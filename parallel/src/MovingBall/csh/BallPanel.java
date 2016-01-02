package csh;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class BallPanel extends JPanel {
    // 实例化一个数组对象
    private Ball[] ball = new Ball[10];
    // 实例化一个随机数对象  
    private Random r = new Random();  
  
    public static void main(String[] args) {  
        // 实例化一个面板对象
        BallPanel bp = new BallPanel();
        bp.setVisible(true);
        JFrame jFrame=new JFrame();
        jFrame.setSize(600,700);
        jFrame.setVisible(true);
        jFrame.setLayout(new FlowLayout());

        JPanel pnl_active=new JPanel();
        pnl_active.setSize(600,600);
        pnl_active.setMaximumSize(new Dimension(600,600));
        pnl_active.setMinimumSize(new Dimension(600,600));
        pnl_active.setVisible(true);
        pnl_active.add(bp);
        jFrame.getContentPane().add(pnl_active);

        JButton btn_start,btn_pause;
        btn_start=new JButton("开始");
        btn_pause=new JButton("暂停");
        btn_start.setSize(100,50);
        btn_pause.setSize(100,50);
        btn_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    pnl_active.setSize(600,600);
                    System.out.println(pnl_active.getHeight()+"+"+pnl_active.getWidth());
                    bp.startBalls();
            }
        });
        btn_pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    bp.pauseAndGoOn();
            }
        });
        btn_start.setVisible(true);
        btn_start.setLocation(50,620);
        btn_pause.setVisible(true);
        btn_pause.setLocation(250,620);
        jFrame.getContentPane().add(btn_start);
        jFrame.getContentPane().add(btn_pause);
        pnl_active.setSize(600,600);
        jFrame.repaint();
        return;

    }

    private void pauseAndGoOn() {
        for (int i=0;i<ball.length;i++){
            ball[i].changeStatus();
        }
    }

    // 界面函数  
    public BallPanel() {
        super();
        Dimension dimension=new Dimension(600,570);
        this.setBackground(Color.white);// 设置面板背景为白色
        this.setSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        for (int i = 0; i < ball.length; i++) {  
            // 实例化每个小球对象  
            ball[i] = new Ball(new Color(r.nextInt(255), r.nextInt(255),  
                    r.nextInt(255)), r.nextInt(550), r.nextInt(550), 50,  
                    r.nextInt(4) + 1, r.nextInt(4) + 1, this, i);  
        }
        System.out.println("ballFrame initialed");
//        startBalls();
    }

    public void startBalls() {
        for (int i = 0; i < ball.length; i++) {
            // 将每个小球线程运行起来
            ball[i].start();
        }
    }

    // 重写paint方法  
    public void paint(Graphics g) {  
        super.paint(g);
        for (int i = 0; i < ball.length; i++) {  
            // 从ball中获取颜色并设置  
            g.setColor(ball[i].getcolor());  
            // 画出小球  
            g.fillOval(ball[i].getX(), ball[i].getY(), ball[i].getRadiu(),  
                    ball[i].getRadiu());  
        }  
        // 调用碰撞函数  
        collision();  
    }  
  
    // 碰撞函数  
    private void collision() {  
        // 距离数组，存储两小球间的距离  
        double[][] dis = new double[ball.length][ball.length];  
        for (int i = 0; i < ball.length; i++) {  
            for (int j = 0; j < ball.length; j++) {  
                // 计算两个小球间的距离  
                dis[i][j] = Math.sqrt(Math.pow(ball[i].getX() - ball[j].getX(),  
                        2) + Math.pow(ball[i].getY() - ball[j].getY(), 2));  
            }  
        }  
        for (int i = 0; i < ball.length; i++) {  
            for (int j = i + 1; j < ball.length; j++) {  
                if (dis[i][j] < (ball[i].getRadiu() + ball[j].getRadiu()) / 2) {  
                    int t;  
                    // 交换小球x方向的速度  
                    t = ball[i].getVx();  
                    ball[i].setVx(ball[j].getVx());  
                    ball[j].setVx(t);  
                    // 交换小球y方向的速度  
                    t = ball[i].getVy();  
                    ball[i].setVy(ball[j].getVy());  
                    ball[j].setVy(t);  
                    // 确定碰撞后第二个小球的位置  
                    int x2 = ball[j].getX() - ball[i].getX(), y2 = ball[j]  
                            .getY() - ball[i].getY();  
                    ball[j].setX(ball[i].getX() + x2);  
                    ball[j].setY(ball[j].getY() + y2);  
                } else {  
                }  
            }  
        }  
    }  
}  