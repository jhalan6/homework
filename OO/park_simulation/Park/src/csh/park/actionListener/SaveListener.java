package csh.park.actionListener;

import csh.park.data.SimulationConfig;
import csh.park.ui.ConfigFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Alan on 15/12/10.
 */
public class SaveListener extends ConfigListener {

    public SaveListener(ConfigFrame configFrame) {
        super(configFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SimulationConfig config=SimulationConfig.getConfig();
            config.setSimulationConfig(frameParts);
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("/tmp/SimulationOfPark.config"));
            output.writeObject(config);
            System.out.println(config);
            output.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(configFrame.getjFrame(),"保存失败",null,JOptionPane.QUESTION_MESSAGE);
        }
        JOptionPane.showMessageDialog(configFrame.getjFrame(),"保存成功!",null,JOptionPane.QUESTION_MESSAGE);
    }
}
