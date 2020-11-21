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
 * 解压按钮
 * 响应点击时调用ZipTool用于解压
 * @author Hwj
 * @version 1.0
 */
public class UnZipButton extends Button {
    private ZipTool zipTool = new ZipTool();
    private FileOperationDialog dialog;

    public UnZipButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            new Thread(() -> {
                System.out.println("Unzip Click");
                File file = this.getFile();
                if (file != null) {
                    DefaultMutableTreeNode node = this.getNode();
                    File outfile = new File(file.getParentFile().getAbsolutePath());

                    long size = FileSizeTool.getFileSize(file) / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
                    FileProgressPane.initProgress(0, (int) size);
                    FileProgressPane.displayPane();
                    Main.mainFrame.lockAll();
                    Main.mainFrame.validate();
                    dialog = new FileOperationDialog(Main.mainFrame, "正在解压 " + file.getName() + " 请等待");
                    dialog.display();

                    boolean success = zipTool.unzipFile(file, outfile.getAbsolutePath());

                    FileProgressPane.closePane();
                    dialog.close();
                    Main.mainFrame.releaseAll();

                    if (success) {
                        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                        StringBuilder builder = new StringBuilder(file.getName());
                        builder.delete(builder.length()-4, builder.length());
                        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(builder.toString());

                        parentNode.add(newNode);
                        treeSelect.getFileTree().getTree().setSelectionPath(new TreePath(parentNode));
                        treeSelect.getTreeModel().reload(parentNode);
                        tableSelect.getFileTable().update(null);

                        new NormalMessage(FileOperationConstant.FILE_UNZIP_SUCCESS_MESSAGE).showMessage();
                    }
                }
            }).start();

        });
    }
}
