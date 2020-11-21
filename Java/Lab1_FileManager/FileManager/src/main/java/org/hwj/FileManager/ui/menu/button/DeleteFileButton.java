package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.app.Main;
import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.file.tool.DeleteNewTool;
import org.hwj.FileManager.file.tool.FileSizeTool;
import org.hwj.FileManager.message.ChooseMessage;
import org.hwj.FileManager.message.NormalMessage;
import org.hwj.FileManager.ui.pane.FileOperationDialog;
import org.hwj.FileManager.ui.pane.FileProgressPane;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;

/**
 * 删除按钮
 * 响应点击时调用DeleteNewTool用于删除
 * @author Hwj
 * @version 1.0
 */
public class DeleteFileButton extends Button {

    private DeleteNewTool deleteNewTool = new DeleteNewTool();
    private FileOperationDialog dialog;

    public DeleteFileButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> new Thread(() -> {
            System.out.println("Delete Click");
            File file = this.getFile();
            if (file != null) {
                File parentFile = file.getParentFile();
                DefaultMutableTreeNode node = this.getNode();
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                System.out.println(file.getAbsolutePath());
                Integer chooseAns = (Integer) new ChooseMessage("你确定要删除 " + file.getAbsolutePath() + " 吗？").showMessage();
                if (chooseAns.equals(JOptionPane.YES_OPTION)) {
                    long size = FileSizeTool.getFileSize(file) / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
                    FileProgressPane.initProgress(0, (int) size);
                    FileProgressPane.displayPane();
                    Main.mainFrame.lockAll();
                    Main.mainFrame.validate();
                    dialog = new FileOperationDialog(Main.mainFrame, "正在删除 " + file.getName() + " 请等待");
                    dialog.display();

                    deleteNewTool.deleteFile(file);

                    FileProgressPane.closePane();
                    dialog.close();
                    Main.mainFrame.releaseAll();

                    treeSelect.getTreeModel().removeNodeFromParent(node);
                    treeSelect.getFileTree().getTree().setSelectionPath(new TreePath(parentNode));
                    treeSelect.getTreeModel().reload(parentNode);
                    tableSelect.getFileTable().update(parentFile);

                    new NormalMessage("已删除 " + file.getAbsolutePath()).showMessage();
                } else {
                    new NormalMessage(FileOperationConstant.FILE_DELETE_CANCEL_MESSAGE).showMessage();
                }
            }
        }).start());
    }
}
