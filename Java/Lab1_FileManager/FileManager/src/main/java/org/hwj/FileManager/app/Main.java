package org.hwj.FileManager.app;

import com.alee.laf.*;
import org.hwj.FileManager.ui.frame.MainFrame;

import javax.swing.*;

/**
 * 程序入口
 * @author Hwj
 * @version 1.0
 */
public class Main {
    public static MainFrame mainFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WebLookAndFeel.install();
            mainFrame = new MainFrame("文件管理器");
            mainFrame.start();
        });
    }
}
