package csh.park.car;

import csh.park.data.SimulationConfig;
import csh.park.park.Park;

import java.util.Random;

/**
 * Created by Alan on 15/12/9.
 */
public class CarFactory extends Thread{
    Park park;
    SimulationConfig simulationConfig;
    Random random;
    String Message;
    public CarFactory(Park park, SimulationConfig simulationConfig) {
        this.park=park;
        this.simulationConfig=simulationConfig;
        random=new Random();
    }
}
