package org.hwj.FileManager.ui.pane;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileProgressPane {
    private static JPanel panel;
    private static JProgressBar progressBar;
    private static JTextArea textArea;

    public static void initFileProgressPane() {
        panel = new JPanel();
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        panel.setSize(600, 50);
        panel.setLayout(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        textArea.setEditable(false);
        panel.add(textArea, BorderLayout.SOUTH);
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static void displayPane() {
        panel.setVisible(true);
    }

    public static void closePane() {
        panel.setVisible(false);
    }

    public static void initProgress(int min, int max) {
        setMinMax(min, max);
        setValue(min);
    }

    private static void setMinMax(int min, int max) {
        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
    }

    public static void setValue(int x) {
        progressBar.setValue(x);
    }

    public static void setText(File file) {
        textArea.setText(file.getPath());
    }

}
