package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.app.Main;
import org.hwj.FileManager.file.tool.CopyTool;
import org.hwj.FileManager.file.tool.FileChooserTool;
import org.hwj.FileManager.ui.pane.FileOperationDialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 拷贝按钮
 * 响应点击时调用FileChooserTool获取路径，调用CopyTool用于拷贝
 * @author Hwj
 * @version 1.0
 */
public class CopyButton extends Button {

    private CopyTool copyTool = new CopyTool();
    private FileOperationDialog dialog;

    public CopyButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("Copy Click");
            File inputFile = this.getFile();
            if (inputFile != null) {
                File outputFile = new FileChooserTool().getSelFile();
                Path inputPath = Paths.get(inputFile.getAbsolutePath());
                Path outputPath = Paths.get(outputFile.getAbsolutePath() + File.separator + inputPath.getFileName());
                try {
                    dialog = new FileOperationDialog(Main.mainFrame, "正在拷贝 " + inputFile.getName() + " 到 " + outputFile.getName() + " 请等待");
                    dialog.display();
                    copyTool.copyFile(inputPath.toFile(), outputPath.toFile());
                    dialog.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

}
