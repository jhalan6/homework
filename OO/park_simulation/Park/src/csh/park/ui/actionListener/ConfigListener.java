package csh.park.ui.actionListener;

import csh.park.data.PublicData;
import csh.park.data.SimulationConfig;
import csh.park.ui.ConfigFrame;
import csh.park.ui.FrameParts;

import java.awt.event.ActionListener;

/**
 * ConfigListener实现了一个监听器接口,完成了两个子接口所需的信息初始化
 * Created by Alan on 15/12/11.
 */
public abstract class ConfigListener implements ActionListener{
    ConfigFrame configFrame;
    FrameParts frameParts;
    SimulationConfig config;

    public ConfigListener(ConfigFrame configFrame) {
        this.configFrame = configFrame;
        frameParts=configFrame.getFrameParts();
        config= PublicData.getPublicData().getConfig();
    }
}
