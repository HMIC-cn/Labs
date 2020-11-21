package org.hwj.FileManager.file.tool;

import com.alee.utils.FileUtils;
import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.message.ErrorMessage;
import org.hwj.FileManager.message.NormalMessage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 拷贝工具类
 * @author Hwj
 * @version 1.0
 */
public class CopyTool {

    /**
     * 输入需要拷贝的路径和目标路径，进行拷贝
     * @param inputFile 需要拷贝的路径
     * @param outputFile 需要拷贝到的目标路径
     * @throws IOException
     */
    public void copyFile(File inputFile, File outputFile) throws IOException {
        Path inputPath = Paths.get(inputFile.getAbsolutePath());
        Path outputPath = Paths.get(outputFile.getAbsolutePath());
        if (outputFile.isFile()) {
            new ErrorMessage(FileOperationConstant.FILE_DIRECTORY_SHOULD_CHOOSE_MESSAGE).showMessage();
        } else if (outputPath.startsWith(inputPath)) {
            new ErrorMessage(FileOperationConstant.FILE_COPY_TO_SUB_BANNED_MESSAGE).showMessage();
        } else if (outputFile.exists()) {
            new ErrorMessage(FileOperationConstant.FILE_EXIST_ALREADY).showMessage();
        } else {
            boolean success = FileUtils.copyDirectory(inputFile, outputFile, true);
            if (success) new NormalMessage(FileOperationConstant.FILE_COPY_SUCCESSES_MESSAGE).showMessage();
            else new NormalMessage(FileOperationConstant.FILE_COPY_FAIL_MESSAGE).showMessage();
        }
    }
}
