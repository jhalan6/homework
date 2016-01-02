package csh;

import java.awt.Color;

public class Ball extends Thread {
    // 初始化一些对象名  
    private Color color;
    private int x, y, radiu, vx, vy;
    private BallPanel bf;
    private int id;
    boolean status;

    /**
     * 构造函数
     *
     * @param color 小球颜色
     * @param x 小球横坐标
     * @param y 小球纵坐标
     * @param radiu 小球直径
     * @param vx    小球横向速度
     * @param vy    小球纵向速度
     * @param bf    面板
     * @param id    标志
     */
    public Ball(Color color, int x, int y, int radiu, int vx, int vy,
                BallPanel bf, int id) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.radiu = radiu;
        this.vx = vx;
        this.vy = vy;
        this.bf = bf;
        this.id = id;
    }

    // 重写run方法  
    public void run() {
        super.run();// 调用父类run方法  
        // 执行无限循环
        status=true;
        while (true) {
            while (status) {
                // System.out.println("第"+id+"个球的x:"+x +"   y:"+y);
                x += vx;// 改变x的位置
                y += vy;// 改变y的位置
                // 如果x越界
                if (x <= 0 || x + radiu >= bf.getWidth()) {
                    vx = -vx;// x速度反向
                    if (x < 0)
                        x = 0;
                    else if (x > bf.getWidth() - radiu)
                        x = bf.getWidth() - radiu;
                    else {
                    }
                }
                // 如果y越界
                else if (y <= 0 || y + radiu >= bf.getHeight()) {
                    vy = -vy;// y速度反向
                    if (y < 0)
                        y = 0;
                    else if (y > bf.getHeight() - radiu)
                        y = bf.getHeight() - radiu;
                    else {
                    }
                } else {
                }

                try {
                    Thread.sleep(10);// 设置睡眠时间为10ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 重绘
                bf.repaint();
            }
            bf.repaint();
        }
    }
    public Color getcolor() {
        return color;
    }

    public void setcolor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadiu() {
        return radiu;
    }

    public void setRadiu(int radiu) {
        this.radiu = radiu;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    public void changeStatus(){
        status^=true;
        System.out.println(status);
    }

}