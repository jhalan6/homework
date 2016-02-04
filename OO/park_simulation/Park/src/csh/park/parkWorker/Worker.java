package csh.park.parkWorker;

import csh.park.data.PublicData;

import static csh.park.data.PublicData.*;

import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * Worker 类是一个前后门员工的父类,继承了Thread类,每几秒完成一次工作任务完成了Worker类通用的构造方法,
 * 定义了类中的数据元素:错误列表\公共数据
 * Created by Alan on 15/12/14.
 */
public abstract class Worker extends Thread {
    /**
     * 工人负责管理的那个错误列表
     */
    protected ArrayList<Message> errorList;
    /**
     * 内部维护的一个公共数据
     */
    protected PublicData publicData;

    /**
     * 用错误列表初始化一个工人
     * @param errorList 让工人处理的错误信息列表
     */
    public Worker(ArrayList<Message> errorList) {
        this.errorList = errorList;
        publicData = PublicData.getPublicData();
    }

    /**
     * 每5秒执行一次work中的任务,所有工人都有相同的工作间隔
     */
    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                sleep(5 * MID_TIME);
                work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void work();
}
