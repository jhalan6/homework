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
 * 这个类用单例式管理一组数据,这组数据由于与整个停车场仿真的大多数类相关,所以改为公共数据,所有类都可以访问,甚至留有对其的引用
 */
public class PublicData {
    /**
     * 调试的关键参数,MID_TIME是单位时间,标准时间的话将其设置为1000,即为1000ms一个间隔,调试时可以设的小一些方便观察实验结果
     */
    public static final int MID_TIME =50;
    /**
     * 因将默认的print设置为输出到日志文件中,所以在PublicData 中维持这个引用,用来输出到控制台
     */
    public static PrintStream print;
    /**
     * 单例式设计模式中所保留的对对象的引用
     */
    private static PublicData singlePublicData;
    /**
     * 用来存放进场或出场错误列表的引用.
     */
    private final ArrayList<Message> inError,outError;
    private Park park;
    private Console console;
    /**
     * 进入停车场的员工列表
     */
    private ArrayList<Employee> employeesInPark;
    private ConfigFrame configFrame;
    private ParkFrame parkFrame;

    /**
     * 默认的私有空参数构造函数,对基本数据进行了初始化
     */
    private PublicData() {
        inError=new ArrayList<>();
        outError=new ArrayList<>();
        employeesInPark =new ArrayList<>();
        print=System.out;
        File outputFile;
        try {
            outputFile = new File("//tmp//Simulation.log");
            System.setOut(new PrintStream(outputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化停车场界面
     */
    public void initParkFrame() {
        initPark();
        initConsole();
        if(parkFrame==null){
            parkFrame=new ParkFrame();
        }
    }

    /**
     * 初始化停车场界面中控制台的部分
     */
    private void initConsole() {
        if(console==null){
            this.console=new Console();
        }
    }

    /**
     * 初始化停车场界面中停车场的部分
     */
    private void initPark() {
        if(this.park==null)
            this.park = new Park();
    }

    /**
     * 初始化配置界面
     */
    public void initConfigFrame(){
        initConfig();
        if (configFrame==null){
            configFrame=new ConfigFrame();
        }
    }

    /**
     * 初始化配置界面里的配置数据
     */
    private void initConfig() {
        SimulationConfig.getConfig();
    }

    public Console getConsole() {
        return console;
    }

    public Park getPark() {
        return park;
    }

    public static PublicData getPublicData() {
       if (singlePublicData==null){
               singlePublicData=new PublicData();
           return singlePublicData;
       }
        else return singlePublicData;
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

    public SimulationConfig getConfig() {
        return SimulationConfig.getConfig();
    }

    /**
     * 通过公共数据访问停车场界面,主要用于重新绘制停车场界面
     * @return 停车场界面的引用
     */
    public ParkFrame getParkFrame() {
        return parkFrame;
    }

    public int getN() {
        return park.getHalfMax();
    }
}
