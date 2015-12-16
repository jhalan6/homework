package csh.park.actionListener;

import csh.park.ui.ConfigFrame;
import csh.park.ui.FrameParts;

import java.awt.event.ActionListener;

/**
 * Created by Alan on 15/12/11.
 */
public abstract class ConfigListener implements ActionListener{
    ConfigFrame configFrame;
    FrameParts frameParts;

    public ConfigListener(ConfigFrame configFrame) {
        this.configFrame = configFrame;
        frameParts=configFrame.getFrameParts();
    }
}
