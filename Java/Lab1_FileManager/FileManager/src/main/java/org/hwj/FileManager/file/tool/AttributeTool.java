package org.hwj.FileManager.file.tool;

import org.apache.commons.io.FileUtils;
import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.message.ErrorMessage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 属性工具类，提供文件、文件夹的大小、相关时间的功能
 * @author Hwj
 * @version 1.0
 */
public class AttributeTool {

    /**
     * 文件、文件夹大小计算
     * @param file 需要计算大小的文件或文件夹
     * @return 返回字符串形式的文件大小，具体为两位小数加上后缀
     */
    public String getFileSize(File file) {
        long size;
//        if (file.isDirectory()) {
//            size = FileUtils.sizeOfDirectory(file);
//        } else {
//            size = FileUtils.sizeOf(file);
//        }
        size = FileSizeTool.getFileSize(file);
        double sizeOf;
        String sizeOfFile, later;
        if (size < FileUtils.ONE_KB) {
            sizeOf = size;
            later = "Bytes";
        } else if (size >= FileUtils.ONE_KB && size < FileUtils.ONE_MB) {
            sizeOf = (double) size / FileUtils.ONE_KB;
            later = "KB";
        } else if (size >= FileUtils.ONE_MB && size < FileUtils.ONE_GB) {
            sizeOf = (double) size / FileUtils.ONE_MB;
            later = "MB";
        } else if (size >= FileUtils.ONE_GB && size < FileUtils.ONE_TB) {
            sizeOf = (double) size / FileUtils.ONE_GB;
            later = "GB";
        } else if (size >= FileUtils.ONE_TB && size < FileUtils.ONE_PB) {
            sizeOf = (double) size / FileUtils.ONE_TB;
            later = "TB";
        } else if (size >= FileUtils.ONE_PB && size < FileUtils.ONE_EB) {
            sizeOf = (double) size / FileUtils.ONE_PB;
            later = "PB";
        } else {
            sizeOf = (double) size / FileUtils.ONE_EB;
            later = "EB";
        }
        sizeOfFile = String.format("%.2f", sizeOf);

        return (sizeOfFile + " " + later);
    }

    /**
     * 获取创建时间
     * @param file 需要获取创建时间的文件或文件夹
     * @return 返回文件或文件夹的创建时间，采用当地时间
     */
    public String getFileCreationTime(File file) {
        BasicFileAttributes bfa = null;
        try {
            bfa = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (Exception e) {
            new ErrorMessage(FileOperationConstant.FILE_NOT_CLEAR_ERROR_MESSAGE).showMessage();
            e.printStackTrace();
        }
        return formatDateTime(bfa.creationTime());
    }

    /**
     * 获取访问时间
     * @param file 需要获取访问时间的文件或文件夹
     * @return 返回文件或文件夹的最后访问时间，采用当地时间
     */
    public String getFileLastAccessTime(File file) {
        BasicFileAttributes bfa = null;
        try {
            bfa = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (Exception e) {
            new ErrorMessage(FileOperationConstant.FILE_NOT_CLEAR_ERROR_MESSAGE).showMessage();
            e.printStackTrace();
        }
        return formatDateTime(bfa.lastAccessTime());
    }

    /**
     * 获取修改时间
     * @param file 需要获取修改时间的文件或文件夹
     * @return 返回文件或文件夹的修改时间，采用当地时间
     */
    public String getFileModifyTime(File file) {
        BasicFileAttributes bfa = null;
        try {
            bfa = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (Exception e) {
            new ErrorMessage(FileOperationConstant.FILE_NOT_CLEAR_ERROR_MESSAGE).showMessage();
            e.printStackTrace();
        }
        return formatDateTime(bfa.lastModifiedTime());
    }

    /**
     * 文件时间转换为标准时间格式
     * @param fileTime 需要转换的文件时间
     * @return 返回最终显示的时间
     */
    private String formatDateTime(FileTime fileTime) {
        LocalDateTime localDateTime = fileTime
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
