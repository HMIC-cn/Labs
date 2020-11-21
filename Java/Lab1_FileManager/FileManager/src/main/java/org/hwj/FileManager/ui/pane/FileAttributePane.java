package org.hwj.FileManager.ui.pane;

import org.hwj.FileManager.file.tool.AttributeTool;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@Deprecated
public class FileAttributePane extends JFrame {

    private JTextArea nameText = new JTextArea();
    private JTextField textField = new JFormattedTextField();
    private AttributeTool attributeTool = new AttributeTool();

    public FileAttributePane(File file) {
        super(file.getName() + "属性");
        this.setSize(600, 600);
        this.setLayout(new BorderLayout());
        nameText.setEnabled(false);
        init(file);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void init(File file) {
        this.removeAll();
        JPanel panel = new JPanel();
        JTextPane textPane = new JTextPane();

        panel.setSize(500, 300);
        panel.add(new JLabel("类型"), BorderLayout.WEST);
        panel.add(new JLabel(file.isFile() ? "文件" : "文件夹"), BorderLayout.EAST);
        panel.setVisible(true);
        this.add(panel, BorderLayout.CENTER);

//        nameText.append("类型" + "\t\t\t" + (file.isFile() ? "文件" : "文件夹") + System.getProperty("line.separator"));
//        nameText.append("位置" + "\t\t\t" + file.getAbsolutePath() + System.getProperty("line.separator"));
//        nameText.append("大小" + "\t\t\t" + attributeTool.getFileSize(file) + System.getProperty("line.separator"));
//        nameText.append("创建时间" + "\t\t\t" + attributeTool.getFileCreationTime(file) + System.getProperty("line.separator"));
//        nameText.append("修改时间" + "\t\t\t" + attributeTool.getFileModifyTime(file) + System.getProperty("line.separator"));
//        nameText.append("访问时间" + "\t\t\t" + attributeTool.getFileLastAccessTime(file) + System.getProperty("line.separator"));
    }

}
