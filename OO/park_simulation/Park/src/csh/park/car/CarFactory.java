package csh.park.car;

import csh.park.data.Employee;
import csh.park.data.PublicData;

import java.util.Random;

/**
 * Created by Alan on 15/12/9.
 */
public class CarFactory extends Thread{
    Random random;
    PublicData publicData;
    public CarFactory(PublicData publicData) {
        this.publicData = publicData;
        random=new Random();
    }


    @Override
    public void run() {
        while (true){
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Car(nextIn(),nextOut(), publicData,random.nextInt(50000)).start();
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
