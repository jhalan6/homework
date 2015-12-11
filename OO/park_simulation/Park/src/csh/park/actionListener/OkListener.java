package csh.park.actionListener;

import csh.park.data.SimulationConfig;
import csh.park.ui.ConfigFrame;
import csh.park.ui.ParkFrame;
import csh.park.ui.Start;
import sun.tools.tree.ConvertExpression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alan on 15/12/11.
 */
public class OkListener extends ConfigListener{
    public OkListener(ConfigFrame configFrame) {
        super(configFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            configFrame.getConfig().setMaxCar(Integer.parseInt(configFrame.getTextMaxCar().getText()));
            configFrame.getConfig().setMaxEmployee(Integer.parseInt(configFrame.getTextMaxEmployee().getText()));
            configFrame.getConfig().setRateOfNotEmployee(Float.parseFloat(configFrame.getTextRateOfNotEmployee().getText()));
            configFrame.getConfig().setRateOfError(Float.parseFloat(configFrame.getTextRateOfError().getText()));
            configFrame.getStart().setConfig(configFrame.getConfig());
            configFrame.getjFrame().dispose();
            new ParkFrame(configFrame.getStart());
        }catch (Exception ee){
            JOptionPane.showMessageDialog(configFrame.getjFrame(),"数据错误!",null,JOptionPane.QUESTION_MESSAGE);
            throw new RuntimeException("输入数据错误!");
        }
    }
}
