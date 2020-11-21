package org.hwj.FileManager.test.layout;

import org.apache.commons.io.FileUtils;
import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.file.tool.FileSizeTool;
import org.hwj.FileManager.ui.frame.ProgressFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class FlowLayoutTest {

    private static long totSize;
    private static long maxSize;
    private static ProgressFrame progressFrame;
    private static ProgressMonitor progressMonitor;

    public static void dfs(File file) {
        if (file == null) return;
        if (file.isFile()) {
            totSize += FileUtils.sizeOf(file) / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
        } else {
            if (file.listFiles() != null) {
                System.out.println("FILE = " + file);
                for (File file1 : file.listFiles()) {
                    dfs(file1);

                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("H1");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton button = new JButton("test");
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.getComponent().setFocusable(false);
                    File file = new File("D:\\Work");
                    maxSize = FileSizeTool.getFileSize(file) / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
                    System.out.println("size = " + maxSize);
                    totSize = 0;

                    dfs(file);
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        frame.add(button);

        frame.setVisible(true);

    }

}
