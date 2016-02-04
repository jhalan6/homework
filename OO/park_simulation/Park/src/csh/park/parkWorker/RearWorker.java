package csh.park.parkWorker;

import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * RearWorker类继承了Worker类,完成了后门工作人员的任务,实现了对后门出错车辆的处理
 * Created by Alan on 16/1/27.
 */
public class RearWorker extends Worker {
    public RearWorker(ArrayList<Message> arrayList) {
        super(arrayList);
    }

    /**
     * 后门工人的工作任务是,把未从后门出去,并将已经回到了停车位的车辆处理(可以理解为罚款,也可以增加新的处理方式)
     */
    @Override
    protected void work() {
        //检验是否有出错不能出门的车辆
        if (!errorList.isEmpty()) {
            Message message = errorList.get(0);
            //并且如果车已经回到了车位的话,可以进行处理
            if (message.getCar().atParkLocation()) {
                message.getCar().handle();
                //通知车辆,已经处理完毕,要进行线程同步,否则会出现数组角标越界异常
                try {
                    synchronized (message.getCar()) {
                        message.getCar().notify();
                        errorList.remove(message);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }
}
