package csh;

import javax.swing.*;
import java.awt.event.*;

public class BallDialog extends JDialog {
    private JPanel contentPane;
    private JButton button1;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel activePanel;
    private BallPanel ballPanel;

    public BallDialog() {
        ballPanel =new BallPanel();
        System.out.println(ballPanel);
        setContentPane(contentPane);
        setModal(true);
        setSize(800,700);
        activePanel=new JPanel();
        activePanel.setSize(600,600);
        activePanel.setVisible(true);
        activePanel.add(ballPanel);
        ballPanel.setVisible(true);
        getContentPane().add(activePanel);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setVisible(true);
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        BallDialog dialog = new BallDialog();
//        dialog.activePanel.add(dialog.ballPanel);
    }
}
