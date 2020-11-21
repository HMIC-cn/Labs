package org.hwj.FileManager.ui.pane;

import org.hwj.FileManager.file.structure.FileTable;
import javax.swing.*;

/**
 * 封装有一个FileTable用于显示
 * @author Hwj
 * @version 1.0
 */
public class FileTablePane extends JScrollPane {
    private FileTable fTable;

    public FileTablePane() {
        super();
        fTable = new FileTable();
        this.getViewport().add(fTable.getTable());
    }

    /**
     * FileTable的get函数
     * @return 返回FileTablePane内的FileTable
     */
    public FileTable getFileTable() {
        return fTable;
    }

}
