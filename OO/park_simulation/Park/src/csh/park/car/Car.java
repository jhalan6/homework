package csh.park.car;

import csh.park.console.Console;
import csh.park.data.Employee;
import csh.park.park.Park;
import csh.park.ui.ParkFrame;

/**
 * Created by Alan on 15/12/9.
 */
public class Car extends Thread{
    Employee indata,outdata;
    Park park;
    ParkFrame pf;
    Console console;

    public Car(Employee outdata, Employee indata, Park park, ParkFrame pf, Console console) {
        this.outdata = outdata;
        this.indata = indata;
        this.park = park;
        this.pf=pf;
        this.console=console;
    }

    @Override
    public void run() {
        super.run();
        // TODO: 15/12/10 画车
        if (console.getLeft()&&park.in(indata)){
                // TODO: 15/12/10 停车
                ;
        } else {
            // TODO: 15/12/10 离开
        }
        // TODO: 15/12/10 出停车场
        park.out(outdata);
    }
}
