package csh.park.parkWorker;

import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * FrontWorker类继承了Worker类,实现了前门工作人员的任务,可以增加新的任务
 * Created by Alan on 16/1/27.
 */
public class FrontWorker extends Worker {

    public FrontWorker(ArrayList<Message> arrayList) {
        super(arrayList);
    }

    /**
     * 在没有新的工作任务的情况下,将进门异常移除列表就是唯一的任务
     */
    @Override
    protected void work() {
        if (!errorList.isEmpty()) {
            Message message = errorList.get(0);
            errorList.remove(message);
        }
    }
}
