package org.hwj.FileManager.file.structure;

import org.hwj.FileManager.file.tool.AttributeTool;
import org.hwj.FileManager.ui.menu.pop.PopMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * FileTable内置了一个JTable
 * @author Hwj
 * @version 1.0
 */
public class FileTable {
    private PopMenu popMenu = new PopMenu();
    private AttributeTool attributeTool = new AttributeTool();
    private File currFile;
    private JTable fileTable;
    private DefaultTableModel model;
    public boolean canUpdate = true;

    String[] columnNames = {"名称", "修改时间", "类型", "大小"};

    public JTable getTable() {
        return fileTable;
    }

    public FileTable() {
        super();
        model = new DefaultTableModel(columnNames, 0);
        fileTable = new JTable(model);
        init();
    }

    public File getSelFile() {
        return currFile;
    }

    /**
     * 响应外界的文件列表更新调用
     * @param changeFile 需要更新显示在JTable中的目录
     */
    public void update(File changeFile) {
        if (changeFile != null) {
            currFile = changeFile;
        }
        int row = 0;
        if (currFile.listFiles() == null) return;
        model.setRowCount(currFile.listFiles().length);
        for (File file : currFile.listFiles()) {
            fileTable.setValueAt(file.getName(), row, 0);
            fileTable.setValueAt(attributeTool.getFileModifyTime(file), row, 1);
            fileTable.setValueAt(file.isDirectory() ? "文件夹" : "文件", row, 2);
            fileTable.setValueAt(file.isFile() ? attributeTool.getFileSize(file) : null, row, 3);
            row++;
        }
    }

    private void init() {
        fileTable.setRowHeight(28);
        fileTable.setDefaultEditor(Object.class, null);
        fileTable.setFillsViewportHeight(true);
        fileTable.getTableHeader().setReorderingAllowed(false);
        fileTable.setAutoCreateRowSorter(true);
        fileTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 向fileTable添加右键的弹出菜单
        fileTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isMetaDown()) {
                    popMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

    }
}
