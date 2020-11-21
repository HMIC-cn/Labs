package org.hwj.FileManager.ui.pane;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 提供返回上一级文件夹的功能
 * 包含一个按钮，用于返回；一个文本区域，用于显示当前所在的文件夹
 * @author Hwj
 * @version 1.0
 */
public class FileBackBar extends JPanel {

    public JButton backButton = new JButton("<");
    private JTextArea pathTextArea = new JTextArea();

    public FileBackBar() {
        pathTextArea.setEnabled(false);
        this.setLayout(new BorderLayout());
        this.setSize(200, 12);

        this.add(backButton, BorderLayout.WEST);
        this.add(pathTextArea, BorderLayout.CENTER);

        this.setVisible(true);
    }

    /**
     * 可供外界调用的更新函数，用于更新文本框内显示的路径
     * @param file 需要显示的文件路径
     */
    public void update(File file) {
        if (file == null) return;
        pathTextArea.setText(file.getAbsolutePath());
    }

}
