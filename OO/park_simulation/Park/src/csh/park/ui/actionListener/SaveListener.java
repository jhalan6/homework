package csh.park.ui.actionListener;

import csh.park.data.SimulationConfig;
import csh.park.ui.ConfigFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * SaveListener实现了保存按钮的事件监听器,将配置文件保存到了/temp/SimulationOfPark.config文件中
 * 该文件夹是OSX下的一个临时文件夹,重新启动将导致当前配置失效
 * Created by Alan on 15/12/10.
 */
public class SaveListener extends ConfigListener {

    public SaveListener(ConfigFrame configFrame) {
        super(configFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
