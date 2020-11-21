package org.hwj.FileManager.file.tool;

import org.apache.commons.io.FileUtils;
import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.message.ErrorMessage;
import org.hwj.FileManager.ui.pane.FileProgressPane;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 * 提供文件或文件夹的压缩和解压功能
 * @author Hwj
 * @version 1.0
 */
public class ZipTool {
    private FileOutputStream fileOutputStream;
    private ZipOutputStream zipOutputStream;
    private long totSize;

    /**
     * 对外的压缩文件接口，需要被压缩路径和压缩目的路径
     * @param infile    被压缩路径
     * @param outfile   压缩目的路径
     * @throws Exception
     */
    public boolean zipFile(File infile, File outfile) {
        try {
            totSize = 0;
            fileOutputStream = new FileOutputStream(outfile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            this.zip(infile.getAbsolutePath(), infile, zipOutputStream);
            zipOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean zipAFile(File file) {
        boolean success = true;
        File parentFile;
        File outputFile;
        FileInputStream fis = null;
        ZipOutputStream out = null;
        ZipEntry entry;
        try {
            parentFile = file.getParentFile();
            outputFile = new File(parentFile.getAbsolutePath() + File.separator + file.getName() + ".zip");
            fis = new FileInputStream(file);
            out = new ZipOutputStream(new FileOutputStream(outputFile));
            entry = new ZipEntry(file.getName());
            out.putNextEntry(entry);

            int len;
            byte[] inData = new byte[FileOperationConstant.BUFFER_LENGTH];
            while ((len = fis.read(inData)) != -1) {
                out.write(inData, 0, len);
            }
            out.closeEntry();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
            try {
                fis.close();
                out.closeEntry();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return success;
    }

    /**
     * 对外的解压文件接口，需要压缩文件路径以及解压路径
     * @param zipFile 压缩文件路径
     * @param outfilePath 解压目的路径
     * @return 返回解压是否成功
     */
    public boolean unzipFile(File zipFile, String outfilePath) {
        totSize = 0;
        if (!zipFile.getName().endsWith(".zip")) {
            new ErrorMessage(FileOperationConstant.FILE_SHOULD_CHOOSE_ZIP_MESSAGE).showMessage();
            return false;
        } else {
            Path destination = Paths.get(outfilePath);
            StringBuilder builder = new StringBuilder(zipFile.getName());
            builder.delete(builder.length() - 4, builder.length());
            File outfile = new File(destination + File.separator + builder.toString());
            if (!outfile.exists()) outfile.mkdirs();
            try {
                return this.unzip(zipFile.getAbsolutePath(), outfile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 递归压缩文件夹
     * @param srcRootDir 压缩文件夹根目录的子路径
     * @param file       当前递归压缩的文件或目录对象
     * @param zos        压缩文件存储对象
     * @throws Exception
     */
    private void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
        if (file == null) {
            return;
        }
        String subPath = file.getAbsolutePath();

        int index = subPath.indexOf(srcRootDir);
        if (index != -1) {
            subPath = subPath.substring(srcRootDir.length());
        }
        //如果是文件，则直接压缩该文件
        if (file.isFile()) {
            int readByteCount = 0;
            byte data[] = new byte[FileOperationConstant.BUFFER_LENGTH];

            totSize += FileUtils.sizeOf(file) / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
            FileProgressPane.setValue((int) totSize);
            FileProgressPane.setText(file.getAbsoluteFile());

            //获取文件相对于压缩文件夹根目录的子路径
            zos.putNextEntry(new ZipEntry(subPath));
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((readByteCount = bis.read(data, 0, FileOperationConstant.BUFFER_LENGTH)) != -1) {
                zos.write(data, 0, readByteCount);
            }
            bis.close();
            zos.closeEntry();
        } else {
            //压缩目录中的文件或子目录
            if (file.listFiles() == null) {
                return;
            } else if (file.listFiles().length == 0) {
                zos.putNextEntry(new ZipEntry(subPath + File.separator));
            } else {
                for (File f : file.listFiles()) {
                    zip(srcRootDir, f, zos);
                    FileProgressPane.setValue((int) totSize);
                    FileProgressPane.setText(file);
                }
            }
        }
    }

    private boolean unzip(String source, String destination) throws IOException {
        boolean success = true;
        File root = new File(destination);
        FileInputStream fis;
        ZipInputStream zis = null;
        try {
            if (!root.exists()) {
                root.mkdir();
            }
            // 创建zip流
            fis = new FileInputStream(new File(source));
            zis = new ZipInputStream(new BufferedInputStream(fis));
            System.out.println(zis);
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                File file = new File(destination + File.separator + fileName);
                if (!entry.isDirectory()) {
                    Path parentPath = Paths.get(file.getParent());
                    if (!parentPath.toFile().exists()) {
                        parentPath.toFile().mkdirs();
                    }
                    unzipFileTo(file, zis);
                    totSize += file.length() / FileOperationConstant.FILE_PROGRESS_SMALL_TIMES;
                } else {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
                FileProgressPane.setValue((int) totSize);
                FileProgressPane.setText(file);
                zis.closeEntry();
            }
            zis.close();
        } catch (Exception e) {
            zis.close();
            success = false;
            e.printStackTrace();
        }
        return success;
    }

    private void unzipFileTo(File file, ZipInputStream zis) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos, FileOperationConstant.BUFFER_LENGTH);
        int len;
        byte data[] = new byte[FileOperationConstant.BUFFER_LENGTH];
        while ((len = zis.read(data, 0, FileOperationConstant.BUFFER_LENGTH)) != -1) {
            bos.write(data, 0, len);
        }
        bos.flush();
        bos.close();
    }
}
