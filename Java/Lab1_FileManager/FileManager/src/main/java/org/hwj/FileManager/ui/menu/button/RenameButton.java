package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.message.InputMessage;
import org.hwj.FileManager.message.NormalMessage;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

/**
 * 解密按钮
 * 响应点击时对文件、文件夹进行重命名
 * @author Hwj
 * @version 1.0
 */
public class RenameButton extends Button {
    public RenameButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("Rename Click");
            String getInput = (String) new InputMessage(FileOperationConstant.FILE_INPUT_NEW_DIRECTORY_NAME_MESSAGE).showMessage();
            if (getInput == null) {
                new NormalMessage(FileOperationConstant.FILE_RENAME_CANCEL_MESSAGE).showMessage();
                return;
            }
            File file = this.getFile();
            if (file != null) {
                DefaultMutableTreeNode node = this.getNode();
                String getNewName = rename(file, getInput);
                file.renameTo(new File(getNewName));
                node.setUserObject(getInput);
                treeSelect.getTreeModel().reload(node);
                tableSelect.getFileTable().update(file.getParentFile());
            }
        });
    }

    private String rename(File inputFile, String changeName) {
        StringBuilder builder = new StringBuilder(inputFile.getAbsolutePath());
        int deleteLength = inputFile.getName().length();
        builder.replace(builder.length()-deleteLength, builder.length(), changeName);
        System.out.println("TO " + builder.toString());
        return builder.toString();
    }
}
