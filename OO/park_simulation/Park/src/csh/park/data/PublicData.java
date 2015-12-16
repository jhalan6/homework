package csh.park.data;

import csh.park.console.Console;
import csh.park.park.Park;
import csh.park.ui.ConfigFrame;
import csh.park.ui.ParkFrame;
import csh.park.warning.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by Alan on 15/12/9.
 */
public class PublicData extends Thread{
    SimulationConfig config;
    Park park;
    Console console;
    protected final ArrayList<Message> inError,outError;
    ArrayList<Employee> employeesInPark;
    ConfigFrame configFrame;
    ParkFrame parkFrame;

    public ConfigFrame getConfigFrame() {
        return configFrame;
    }


    public ParkFrame getParkFrame() {
        return parkFrame;
    }

    public void initParkFrame() {
        initPark();
        initConsole();
        if(parkFrame==null){
            parkFrame=new ParkFrame();
        }else return;
    }

    public static PrintStream print;//系统控制台输出

    public Console getConsole() {
        return console;
    }

    private void initConsole() {
        if(console==null){
            this.console=new Console();
        }else
            return;
    }

    public SimulationConfig getConfig() {
        return SimulationConfig.getConfig();
    }

    public Park getPark() {
        return park;
    }

    private void initConfig() {
        config=SimulationConfig.getConfig();
    }

    private void initPark() {
        if(this.park==null)
            this.park = new Park();
        else
            return;
    }
    public void initConfigFrame(){
        initConfig();
        if (configFrame==null){
            configFrame=new ConfigFrame();
        } else return;
    }
    private static PublicData singlePublicData;
    public static PublicData getPublicData() {
       if (singlePublicData==null){
               singlePublicData=new PublicData();
           return singlePublicData;
       }
        else return singlePublicData;
    }

    private PublicData() {
        inError=new ArrayList<Message>();
        outError=new ArrayList<Message>();
        employeesInPark =new ArrayList<Employee>();
        print=System.out;
        File outputFile= null;
        try {
            outputFile = new File("//tmp//Simulation.log");
            System.setOut(new PrintStream(outputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Employee> getEmployeesInPark() {
        return employeesInPark;
    }

    public ArrayList<Message> getOutError() {
        return outError;
    }

    public ArrayList<Message> getInError() {
        return inError;
    }
}
