package csh.park.car;

import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.parkWorker.Worker;

import java.util.Random;

/**
 * Created by Alan on 15/12/9.
 */
public class CarFactory extends Thread{
    Random random;
    PublicData publicData;
    public CarFactory() {
        this.publicData = PublicData.getPublicData();
        Worker worker=new Worker(publicData.getOutError(),publicData.getInError());
        worker.start();
        random=new Random();
    }

    @Override
    public void run() {
        while (true){
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Car(nextIn(),nextOut(),random.nextInt(300000)).start();
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
