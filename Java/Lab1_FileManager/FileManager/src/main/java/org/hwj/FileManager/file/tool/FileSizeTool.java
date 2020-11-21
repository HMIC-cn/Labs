package org.hwj.FileManager.file.tool;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

/**
 * 获取文件或文件夹大小
 * @author Hwj
 * @version 1.0
 */
public class FileSizeTool {
    private final static ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static long getFileSize(File file) {
        long size = forkJoinPool.invoke(new FileSizeFinder(file));
        return size;
    }
}
