package csh.park.parkWorker;

import csh.park.data.PublicData;
import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * Created by Alan on 15/12/14.
 */
public abstract class Worker extends Thread{
    protected ArrayList<Message> arrayList;
    protected PublicData publicData;
    public Worker(ArrayList<Message> arrayList){
        this.arrayList=arrayList;
        publicData=PublicData.getPublicData();
    }
    @Override
    public void run() {
        super.run();
        work();
    }

    protected abstract void work();
}
