package org.hwj.FileManager.file.structure;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

/**
 * 全局的FileTree查询类
 * 提供FileTree的set, get操作, 以及其中树的模型、选中节点以及选中文件
 * @author Hwj
 * @version 1.0
 */
public class GlobalFileTreeSelect {
    private FileTree fileTree;
    private DefaultTreeModel model;

    public void setFileTree(FileTree fileTree) {
        this.fileTree = fileTree;
    }

    public FileTree getFileTree() {
        return fileTree;
    }

    public DefaultTreeModel getTreeModel() {
        model = (DefaultTreeModel)fileTree.getTree().getModel();
        return model;
    }

    public File getTreeSelectFile() {
        return fileTree.getSelFile();
    }

    public DefaultMutableTreeNode getTreeSelNode() {
        return fileTree.getSelNode();
    }
}
