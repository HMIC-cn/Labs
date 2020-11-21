package org.hwj.FileManager.ui.pane;

import javax.swing.*;

public class FileOperationDialog extends JDialog {

    public FileOperationDialog(JFrame owner, String text) {
        super(owner, text, false);
        init();
    }

    public void init() {
        this.setSize(300, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        JLabel messageLabel = new JLabel();
        JPanel panel = new JPanel();
        panel.add(messageLabel);
        this.setContentPane(panel);
    }

    public void display() {
        this.setVisible(true);
    }

    public void close() {
        this.dispose();
    }
}
