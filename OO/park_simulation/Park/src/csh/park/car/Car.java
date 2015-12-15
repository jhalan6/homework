package csh.park.car;

import csh.park.console.Console;
import csh.park.data.Employee;
import csh.park.park.Park;
import csh.park.data.PublicData;

/**
 * Created by Alan on 15/12/9.
 */
public class Car extends Thread{
    private static int count =0;
    private  final Employee inData,outData;
    private  final Park park;
    private  final PublicData publicData;
    private  final Console console;
    private  final int time;
    private  final int number;

    public Car(Employee outData, Employee inData, PublicData publicData,int time) {
        this.outData = outData;
        this.inData = inData;
        this.publicData=publicData;
        this.time=time;
        console=publicData.getConsole();
        park=publicData.getPark();
        number= count++;
    }

    @Override
    public void run() {
        super.run();
        // TODO: 15/12/10 画车
        if (console.getLeft()>0&&park.in(inData)){
            // TODO: 15/12/10 停车
            publicData.print.println("汽车"+number+"进入停车场停车");
            try {
                publicData.print.println("汽车"+number+"在停车场内,将要停"+time+"毫秒");
                sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publicData.print.println("汽车"+number+"将要驶出停车场");
            // TODO: 15/12/10 出停车场
            park.out(outData);
        } else {
            publicData.print.println("汽车"+number+"无法进入停车场而离开");
            // TODO: 15/12/10 离开
        }

    }
}
