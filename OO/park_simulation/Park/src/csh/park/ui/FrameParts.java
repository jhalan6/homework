package csh.park.ui;

import javax.swing.*;

/**
 * FameParts类,仅仅是为了初始化SimulationConfig对象时可以更加方便而设置的,并不具有实际作用,仅保存一些Config页面控件的引用
 */
public class FrameParts{
    JTextField textMaxEmployee;
    JTextField textMaxCar;
    JTextField textRateOfNotEmployee;
    JTextField textRateOfError;

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

    public FrameParts(JTextField textMaxEmployee, JTextField textMaxCar, JTextField textRateOfNotEmployee, JTextField textRateOfError) {
        this.textMaxEmployee = textMaxEmployee;
        this.textMaxCar = textMaxCar;
        this.textRateOfNotEmployee = textRateOfNotEmployee;
        this.textRateOfError = textRateOfError;
    }
}
