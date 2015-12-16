package csh.park.ui;

import javax.swing.*;
import java.awt.*;

public class FrameParts{
    JTextField textMaxEmployee;
    JTextField textMaxCar;
    JTextField textRateOfNotEmployee;
    JTextField textRateOfError;
    JFrame jFrame;

    public JTextField getTextRateOfError() {
        return textRateOfError;
    }

    public JTextField getTextMaxEmployee() {
        return textMaxEmployee;
    }

    public JTextField getTextMaxCar() {
        return textMaxCar;
    }

    public JTextField getTextRateOfNotEmployee() {
        return textRateOfNotEmployee;
    }

    public FrameParts(JTextField textMaxEmployee, JTextField textMaxCar, JTextField textRateOfNotEmployee, JTextField textRateOfError,JFrame jFrame) {
        this.textMaxEmployee = textMaxEmployee;
        this.textMaxCar = textMaxCar;
        this.textRateOfNotEmployee = textRateOfNotEmployee;
        this.textRateOfError = textRateOfError;
        this.jFrame=jFrame;
    }

    public JFrame getjFrame() {
        return jFrame;
    }
}
