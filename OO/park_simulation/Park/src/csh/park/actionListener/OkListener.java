package csh.park.actionListener;

import csh.park.data.PublicData;
import csh.park.data.SimulationConfig;
import csh.park.ui.ConfigFrame;
import csh.park.ui.ParkFrame;

import javax.swing.*;
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
        SimulationConfig config=configFrame.getConfig();
        try {
            config.setMaxCar(Integer.parseInt(configFrame.getTextMaxCar().getText().trim()));
            config.setMaxEmployee(Integer.parseInt(configFrame.getTextMaxEmployee().getText().trim()));
            config.setRateOfNotEmployee(Float.parseFloat(configFrame.getTextRateOfNotEmployee().getText().trim()));
            config.setRateOfError(Float.parseFloat(configFrame.getTextRateOfError().getText().trim()));
            publicData.setConfig(config);
            configFrame.getjFrame().dispose();
        }catch (Exception ee){
            JOptionPane.showMessageDialog(configFrame.getjFrame(),"数据错误!",null,JOptionPane.QUESTION_MESSAGE);
            throw new RuntimeException("输入数据错误!");
        }
        publicData.initPark();
        publicData.initConsole();
        publicData.initParkFrame();
    }
}
