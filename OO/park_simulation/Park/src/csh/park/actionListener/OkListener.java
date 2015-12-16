package csh.park.actionListener;

import csh.park.data.PublicData;
import csh.park.data.SimulationConfig;
import csh.park.ui.ConfigFrame;

import java.awt.event.ActionEvent;

/**
 * Created by Alan on 15/12/11.
 */
public class OkListener extends ConfigListener{

    public OkListener(ConfigFrame configFrame) {
        super(configFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PublicData publicData=PublicData.getPublicData();
        SimulationConfig config=publicData.getConfig();
        config.setSimulationConfig(configFrame.getFrameParts());
        configFrame.getjFrame().dispose();
        publicData.initParkFrame();
    }
}
