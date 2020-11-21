package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.file.tool.DeleteNewTool;
import org.hwj.FileManager.message.InputMessage;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

/**
 * 新建文件夹按钮
 * 响应点击时调用DeleteNewTool用于新建
 * @author Hwj
 * @version 1.0
 */
public class NewFileButton extends Button {

    private DeleteNewTool deleteNewTool = new DeleteNewTool();

    public NewFileButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("New Click");
            File file = this.getFile();
            if (file != null) {
                DefaultMutableTreeNode node = this.getNode();
                DefaultMutableTreeNode newNode;
                System.out.println(file.getAbsolutePath());

                String inputName = (String) new InputMessage(FileOperationConstant.FILE_INPUT_NEW_DIRECTORY_NAME_MESSAGE).showMessage();
                StringBuilder builder = new StringBuilder(file.getAbsolutePath());
                builder.append(File.separator + inputName);
                deleteNewTool.newFile(new File(builder.toString()));

                newNode = new DefaultMutableTreeNode(inputName);
                node.add(newNode);
                treeSelect.getTreeModel().reload(node);
                tableSelect.getFileTable().update(file);
            }
        });
    }
}
