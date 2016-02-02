package csh.park.ui.actionListener;

import csh.park.data.PublicData;
import csh.park.data.SimulationConfig;
import csh.park.ui.ConfigFrame;

import java.awt.event.ActionEvent;

/**
 * OkListener实现了确定监听器,配置页面点击确定以后调用该类中的方法进行处理
 * Created by Alan on 15/12/11.
 */
public class OkListener extends ConfigListener {

    public OkListener(ConfigFrame configFrame) {
        super(configFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        config.setSimulationConfig(configFrame.getFrameParts());
        configFrame.getjFrame().dispose();
        PublicData.getPublicData().initParkFrame();
    }
}
