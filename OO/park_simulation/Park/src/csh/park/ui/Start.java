package csh.park.ui;

import csh.park.console.Console;
import csh.park.data.SimulationConfig;
import csh.park.park.Park;

/**
 * Created by Alan on 15/12/9.
 */
public class Start extends Thread{
    SimulationConfig config;
    Park park;
    Console console;

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public SimulationConfig getConfig() {
        return config;
    }

    public Park getPark() {
        return park;
    }

    public void setConfig(SimulationConfig config) {
        this.config = config;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    public Start() throws InterruptedException {
        config=new SimulationConfig();
        new ConfigFrame(this);
    }
}
