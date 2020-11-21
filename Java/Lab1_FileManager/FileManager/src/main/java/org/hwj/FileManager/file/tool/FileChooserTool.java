package org.hwj.FileManager.file.tool;

import javax.swing.*;
import java.io.File;

/**
 * 弹出一个指定的文件选择视图，可以获取选择的文件
 * @author Hwj
 * @version 1.0
 */
public class FileChooserTool extends JFileChooser {
    public FileChooserTool() {
        this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.showDialog(new JLabel(), "选择");
        this.setBounds(400, 200, 100, 100);
        this.setVisible(true);
    }

    /**
     * 获取选择的文件
     * @return 返回文件选择试图调用之后选择的文件
     */
    public File getSelFile() {
        return this.getSelectedFile();
    }
}
