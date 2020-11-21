package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.file.tool.CryptTool;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;

/**
 * 加密按钮
 * 响应点击时调用CryptTool用于加密
 * @author Hwj
 * @version 1.0
 */
public class EncryptButton extends Button {

    private CryptTool cryptTool = new CryptTool();

    public EncryptButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("Encrypt Click");
            File file = this.getFile();
            if (file != null) {
                boolean success = false;
                try {
                    success = cryptTool.encryptFile(file);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (success) {
                    System.out.println("DEL " + file.getAbsolutePath());
                    file.delete();
                    DefaultMutableTreeNode node = this.getNode();
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file.getName() + "hwj", false);
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) this.getNode().getParent();

                    treeSelect.getTreeModel().removeNodeFromParent(node);
                    parentNode.add(newNode);
                    treeSelect.getFileTree().getTree().setSelectionPath(new TreePath(parentNode.getPath()));

                    treeSelect.getTreeModel().reload(parentNode);
                    tableSelect.getFileTable().update(null);
                }
            }
        });
    }
}
