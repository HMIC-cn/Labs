package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.file.tool.CryptTool;
import org.hwj.FileManager.message.ErrorMessage;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;

/**
 * 解密按钮
 * 响应点击时调用CryptTool用于解密
 * @author Hwj
 * @version 1.0
 */
public class DecryptButton extends Button {

    private CryptTool cryptTool = new CryptTool();

    public DecryptButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("Decrypt Click");
            File file = this.getFile();
            if (file != null) {
                boolean success = false;
                try {
                    success = cryptTool.decryptFile(file);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (success) {
                    StringBuilder builder = new StringBuilder(file.getName());
                    builder.delete(builder.length()-3, builder.length());
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(builder.toString(), false);
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) this.getNode().getParent();

                    parentNode.add(newNode);
                    treeSelect.getFileTree().getTree().setSelectionPath(new TreePath(parentNode.getPath()));
                    treeSelect.getTreeModel().reload(parentNode);
                    tableSelect.getFileTable().update(null);
                } else {
                    new ErrorMessage(FileOperationConstant.FILE_DECRYPTED_FAIL_MESSAGE).showMessage();
                }
            }
        });
    }
}
