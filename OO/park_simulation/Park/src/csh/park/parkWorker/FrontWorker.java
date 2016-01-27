package csh.park.parkWorker;

import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * Created by Alan on 16/1/27.
 */
public class FrontWorker extends Worker{

    public FrontWorker(ArrayList<Message> arrayList) {
        super(arrayList);
    }

    @Override
    protected void work() {
        while (true){
            try {
                if (!arrayList.isEmpty()){
                    Message message=arrayList.get(0);
                    arrayList.remove(message);
                }
                sleep(5*publicData.midTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
