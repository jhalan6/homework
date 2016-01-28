package csh.park.parkWorker;

import csh.park.data.PublicData;
import csh.park.warning.Message;

import java.util.ArrayList;

/**
 * Created by Alan on 16/1/27.
 */
public class RearWorker extends Worker {
    public RearWorker(ArrayList<Message> arrayList) {
        super(arrayList);
    }

    @Override
    protected void work() {
        while (true) {
            try {
                sleep(5 * publicData.midTime);
                if (!arrayList.isEmpty()) {
                    Message message = arrayList.get(0);
                    if (message.getCar().atParkLocation()) {
                        message.getCar().handle();
//                    PublicData.print.println("干活了干活了!");
                        arrayList.remove(message);
                    }
                }
// else PublicData.print.println("没活干没活干!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
