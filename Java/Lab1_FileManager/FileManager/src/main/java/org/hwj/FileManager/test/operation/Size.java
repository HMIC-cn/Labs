package org.hwj.FileManager.test.operation;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 * 文件属性的相关测试
 * @author Hwj
 * @version 1.0
 */
public class Size {
    public static void main(String[] args) throws IOException {
        File file = new File("src");
//        System.out.println(file.length());
        long size;
        if (file.isDirectory()) {
            size = FileUtils.sizeOfDirectory(file);
        } else {
            size = FileUtils.sizeOf(file);
        }
        double sizeOf;
        String sizeOfFile = "", later = "";
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

        System.out.println(sizeOfFile + " " + later);

        BasicFileAttributes bfa = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        StringBuilder time1, time2, time3;
        time1 = new StringBuilder(bfa.creationTime().toString());
        time2 = new StringBuilder(bfa.lastModifiedTime().toString());
        time3 = new StringBuilder(bfa.lastAccessTime().toString());

        System.out.println(bfa.creationTime());
        System.out.println(formatDateTime(bfa.creationTime()));
        System.out.println(bfa.lastAccessTime());
        System.out.println(bfa.lastModifiedTime());

    }

    public static String formatDateTime(FileTime fileTime) {
        LocalDateTime localDateTime = fileTime
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime.format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
