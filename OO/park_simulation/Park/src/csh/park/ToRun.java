package csh.park;

import csh.park.data.PublicData;

/**
 * 这个类只有一个方法,仅仅是为了启动程序,仅包含main方法
 * Created by Alan on 15/12/9.
 */
public class ToRun {
    public static void main(String[] args) throws InterruptedException {
        PublicData.getPublicData().initConfigFrame();
    }
}
