package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.file.structure.FileTable;
import org.hwj.FileManager.file.structure.FileTree;
import org.hwj.FileManager.file.structure.GlobalFileTableSelect;
import org.hwj.FileManager.file.structure.GlobalFileTreeSelect;
import org.hwj.FileManager.message.ErrorMessage;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

/**
 * 按钮的顶层类，内有GlobalFileTreeSelect和GlobalFileTableSelect，
 * 用于具体按钮的相关实现
 * @author Hwj
 * @version 1.0
 */
public class Button extends JMenuItem {

    protected static GlobalFileTreeSelect treeSelect = new GlobalFileTreeSelect();
    protected static GlobalFileTableSelect tableSelect = new GlobalFileTableSelect();

    public Button(String text) {
        super(text);
    }

    /**
     * FileTree的set函数
     * @param tree 需要放置的对所有Button生效的FileTree
     */
    public static void setFileTree(FileTree tree) {
        treeSelect.setFileTree(tree);
    }

    /**
     * FileTable的set函数
     * @param fileTable 需要放置的对所有Button生效的FileTable
     */
    public static void setFileTable(FileTable fileTable) {
        tableSelect.setFileTable(fileTable);
    }

    /**
     * 获取当前Button内嵌的FileTree的已选中节点
     * @return 返回FileTree的选择节点
     */
    protected DefaultMutableTreeNode getNode() {
        DefaultMutableTreeNode node = treeSelect.getTreeSelNode();
        if (node == null) {
            new ErrorMessage(FileOperationConstant.FILE_NOT_SELECT_MESSAGE).showMessage();
        }
        return node;
    }

    /**
     * 获取当前Button内嵌的FileTree的已选中文件
     * @return 返回FileTree的选择文件
     */
    protected File getFile() {
        File file = treeSelect.getTreeSelectFile();
        if (file == null) {
            new ErrorMessage(FileOperationConstant.FILE_NOT_SELECT_MESSAGE).showMessage();
        }
        return file;
    }

}
