package org.hwj.FileManager.file.tool;

import org.apache.commons.io.FileUtils;
import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.message.ErrorMessage;
import org.hwj.FileManager.ui.pane.FileProgressPane;

import java.io.File;

/**
 * 新建删除工具类
 * 提供文件或文件夹的删除，和文件夹的新建功能
 * @author Hwj
 * @version 1.0
 */
public class DeleteNewTool {
    private long totSize;

    /**
     * 删除指定路径下的所有文件及文件夹
     * @param delFile 需要删除的路径
     */
    public void deleteFile(File delFile) {
        totSize = 0;
        delete(delFile);
    }

    /**
     * 在指定目录创建新文件夹，并刷新组件
     * @param newFile 需要新建的目录
     */
    public void newFile(File newFile) {
        if (!newFile.exists()) {
            newFile.mkdir();
        } else {
            new ErrorMessage(FileOperationConstant.FILE_EXIST_ALREADY).showMessage();
        }
    }

    private void delete(File delFile) {
        if (delFile.isFile()) {
            totSize += FileUtils.sizeOf(delFile) / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
            FileProgressPane.setValue((int) totSize);
//            System.out.println("totSize = " + totSize);
            FileProgressPane.setText(delFile);
            delFile.delete();
        } else if (delFile.listFiles() != null) {
            for (File file : delFile.listFiles()) {
                delete(file);
                FileProgressPane.setValue((int) totSize);
                FileProgressPane.setText(delFile);
            }
            delFile.delete();
        } else {
            delFile.delete();
        }
    }
}
