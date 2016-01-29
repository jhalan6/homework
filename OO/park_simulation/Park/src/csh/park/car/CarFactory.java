package csh.park.car;

import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.parkWorker.FrontWorker;
import csh.park.parkWorker.RearWorker;

import java.util.Random;

/**
 * Created by Alan on 15/12/9.
 */
public class CarFactory extends Thread{
    Random random;
    PublicData publicData;
    FrontWorker frontWorker;
    RearWorker rearWorker;
    public CarFactory() {
        this.publicData = PublicData.getPublicData();
        frontWorker=new FrontWorker(publicData.getInError());
        rearWorker=new RearWorker(publicData.getOutError());
        frontWorker.start();
        rearWorker.start();
        random=new Random();
    }

    @Override
    public void run() {
        while (true){
            try {
                sleep(10*PublicData.midTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (publicData.getPark().getStatus(0,0)&&publicData.getPark().getStatus(1,0))
                new Car(nextIn(),nextOut(),random.nextInt(300*PublicData.midTime)).start();
        }
    }
    private Employee temp;
    private Employee nextIn(){
        temp=new Employee(random.nextInt((int)(publicData.getConfig().getMaxEmployee()*(1+ publicData.getConfig().getRateOfNotEmployee()))));
        return temp;
    }
    private Employee nextOut(){
        int forRandom=random.nextInt((int)((publicData.getConfig().getRateOfError()+1)*100));
        if (forRandom>100){
            return  new Employee(temp.getId()+1);
        } else
            return temp;
    }
}
