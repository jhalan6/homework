package csh.park.parkWorker;

import csh.park.data.PublicData;
import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * Created by Alan on 15/12/14.
 */
public class Worker extends Thread{
    protected ArrayList<Message> arrayList,arrayList1;
    protected PublicData publicData;
    public Worker(ArrayList<Message> arrayList,ArrayList<Message> arrayList1){
        this.arrayList=arrayList;
        publicData=PublicData.getPublicData();
        this.arrayList1=arrayList1;
    }
    @Override
    public void run() {
        super.run();
        while (true){
            try {
                sleep(5000);
                if (!arrayList.isEmpty()) {
                    Message message = arrayList.get(0);
                    message.getCar().handle();
                    arrayList.remove(message);
                }

                if (!arrayList1.isEmpty()){
                    Message message=arrayList1.get(0);
                    arrayList1.remove(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
