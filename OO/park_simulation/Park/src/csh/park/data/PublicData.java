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
 * 这个类用单例式管理一组数据,这组数据由于其一经配置不再改变,所以改为公共数据,所有类都可以访问,甚至留有对其的引用
 */
public class PublicData extends Thread{
    SimulationConfig config;
    Park park;
    Console console;
    protected final ArrayList<Message> inError,outError;
    ArrayList<Employee> employeesInPark;
    ConfigFrame configFrame;
    ParkFrame parkFrame;
    public static void main(String[] args){
        getPublicData().initConfigFrame();
    }
    /**
     * 停车场的布局通过这个方法进行传递,减少了参数数量
     * @return
     */
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

    public Console getConsole() {
        return console;
    }

    private void initConsole() {
        if(console==null){
            this.console=new Console();
        }
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
        File outputFile= null;
        try {
            //这里windows肯定不是这么写的
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

    public int getN() {
        return park.getHalfMax();
    }
}
