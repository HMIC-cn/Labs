package org.hwj.FileManager.ui.frame;

import org.apache.commons.io.FileUtils;
import org.hwj.FileManager.app.Main;
import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.file.tool.FileSizeTool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;

@Deprecated
/**
 * 进度显示
 * @author Hwj
 * @version 1.0
 */
public class ProgressFrame extends JFrame {
    private JProgressBar progressBar;
    private JPanel panel;
    private long size;
    private JTextArea textArea;

    ProgressMonitor progressMonitor = new ProgressMonitor(Main.mainFrame, "", "", 0, 100);

    public ProgressFrame() {
        progressBar = new JProgressBar();
        init();
    }

    public void setTitle(String title) {
        this.setTitle(title);
    }

    public void displayFrame() {

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        textArea.setEditable(false);
        panel.add(textArea, BorderLayout.SOUTH);

        this.setContentPane(panel);

        this.setSize(600, 100);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void closeFrame() {
        this.dispose();
    }

    public void setMinMax(int min, int max) {
        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
    }

    public void setValue(int x) {
        progressBar.setValue(x);
    }

    public void setText(File file) {
        textArea.setText(file.getPath());
    }

    private void init() {
        size = 0;
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
    }

    public void dfs(File file) {
        if (file == null) return;
//        textArea.setText(file.getParentFile().getName() + File.separator + file.getName());
        if (file.isFile()) {
            size += FileUtils.sizeOf(file) / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
            progressBar.setValue((int) size);
        } else {
            if (file.listFiles() != null) {
                for (File file1 : file.listFiles()) {
                    dfs(file1);
                    progressBar.setValue((int) size);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ProgressFrame progressFrame = new ProgressFrame();
        progressFrame.displayFrame();
        File file = new File("D:\\Software");
        long size = FileSizeTool.getFileSize(file) / 100;
        System.out.println("size = " + size);
        progressFrame.setMinMax(0, (int) size);
        progressFrame.dfs(file);
        progressFrame.closeFrame();
    }
}
