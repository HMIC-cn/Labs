package org.hwj.FileManager.ui.pane;

import org.hwj.FileManager.file.structure.FileTree;

import javax.swing.*;
import java.net.InetAddress;
import java.net.InterfaceAddress;

/**
 * 封装有一个FileTree用于显示
 * @author Hwj
 * @version 1.0
 */
public class FileTreePane extends JScrollPane {
    private FileTree fTree;

    public FileTreePane() {
        fTree = new FileTree();
        try {
            fTree.initDiskRootTree(InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.getViewport().add(fTree.getTree());
        this.setWheelScrollingEnabled(true);
    }

    /**
     * FileTree的get函数
     * @return 返回FileTreePane内的FileTree
     */
    public FileTree getFileTree() {
        return fTree;
    }
}
