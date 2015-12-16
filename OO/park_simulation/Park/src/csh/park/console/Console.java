package csh.park.console;

import csh.park.data.PublicData;

/**
 * Created by Alan on 15/12/9.
 */
public class Console {

    private final PublicData publicData;
    private final int maxCar;
    private int remainCapability;

    public Console() {
        this.publicData = PublicData.getPublicData();
        maxCar=publicData.getConfig().getMaxCar();
        remainCapability=maxCar;
    }
    public void carIn(){
        remainCapability--;
    }
    public void carOut(){
        remainCapability++;
    }


    public int getLeft(){
        return remainCapability;
    }
}
