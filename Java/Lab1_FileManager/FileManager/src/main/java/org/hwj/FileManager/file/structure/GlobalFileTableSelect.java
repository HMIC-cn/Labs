package org.hwj.FileManager.file.structure;

/**
 * 全局的FileTable查询类
 * 只提供FileTable的get和set函数用于更新JTable的视图
 * @author Hwj
 * @version 1.0
 */
public class GlobalFileTableSelect {
    private FileTable fileTable;

    public void setFileTable(FileTable fileTable) {
        this.fileTable = fileTable;
    }

    public FileTable getFileTable() {
        return fileTable;
    }
}
