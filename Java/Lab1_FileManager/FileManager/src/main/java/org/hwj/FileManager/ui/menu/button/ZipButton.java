package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.app.Main;
import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.file.tool.FileSizeTool;
import org.hwj.FileManager.file.tool.ZipTool;
import org.hwj.FileManager.message.NormalMessage;
import org.hwj.FileManager.ui.pane.FileOperationDialog;
import org.hwj.FileManager.ui.pane.FileProgressPane;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;

/**
 * 压缩按钮
 * 响应点击时调用ZipTool用于压缩
 * @author Hwj
 * @version 1.0
 */
public class ZipButton extends Button {
    private ZipTool zipTool = new ZipTool();
    private FileOperationDialog dialog;

    public ZipButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            new Thread(() -> {
                System.out.println("Zip Click");
                File file = this.getFile();
                if (file != null) {
                    DefaultMutableTreeNode node = this.getNode();
                    File outfile = new File(file.getParentFile().getAbsolutePath() + File.separator + file.getName() + ".zip");
                    boolean success;

                    long size = FileSizeTool.getFileSize(file) / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
                    FileProgressPane.initProgress(0, (int) size);
                    FileProgressPane.displayPane();
                    Main.mainFrame.lockAll();
                    Main.mainFrame.validate();
                    dialog = new FileOperationDialog(Main.mainFrame, "正在压缩 " + file.getName() + " 请等待");
                    dialog.display();

                    if (file.isFile()) {
                        success = zipTool.zipAFile(file);
                    } else {
                        success = zipTool.zipFile(file, outfile);
                    }

                    FileProgressPane.closePane();
                    dialog.close();
                    Main.mainFrame.releaseAll();

                    if (success) {
                        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file.getName() + ".zip", false);

                        parentNode.add(newNode);
                        treeSelect.getTreeModel().reload(parentNode);
                        treeSelect.getFileTree().getTree().setSelectionPath(new TreePath(parentNode.getPath()));
                        tableSelect.getFileTable().update(null);
                        new NormalMessage(FileOperationConstant.FILE_ZIP_SUCCESS_MESSAGE).showMessage();
                    }
                }
            }).start();
        });
    }
}
