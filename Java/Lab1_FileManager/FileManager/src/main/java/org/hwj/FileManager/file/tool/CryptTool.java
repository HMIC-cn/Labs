package org.hwj.FileManager.file.tool;

import org.hwj.FileManager.constants.FileOperationConstant;
import org.hwj.FileManager.message.ErrorMessage;
import org.hwj.FileManager.message.InputMessage;
import org.hwj.FileManager.message.NormalMessage;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * 加密解密工具类
 * 提供文件的加密和解密功能
 * 采用AES加密方式
 * 密钥文件储存在程序路径的cache缓存目录下
 * @author Hwj
 * @version 1.0
 */
public class CryptTool {

    public CryptTool() {
        File file = new File("cache");
        if (!file.exists()) file.mkdir();
    }

    /**
     * 加密文件工具
     * @param inputFile 需要加密的文件
     * @return 返回加密是否成功
     * @throws Exception
     */
    public boolean encryptFile(File inputFile) throws Exception {
        boolean success = true;
        if (inputFile.isFile()) {
            Path parentPath = Paths.get(inputFile.getParent());
            File outputFile = new File(parentPath + File.separator + inputFile.getName() + "hwj");
            outputFile.createNewFile();
            InputStream inputStream = new FileInputStream(inputFile);
            OutputStream outputStream = new FileOutputStream(outputFile);

            try {
                // 输入密码
                String password = (String) new InputMessage(FileOperationConstant.FILE_INPUT_YOUR_PASSWORD_MESSAGE).showMessage();

                // password, iv and salt should be transferred to the other end
                // in a secure manner

                // salt is used for encoding
                // writing it to a file
                // salt should be transferred to the recipient securely
                // for decryption
                // 生成salt
                byte[] salt = new byte[8];
                SecureRandom secureRandom = new SecureRandom();
                secureRandom.nextBytes(salt);

                FileOutputStream saltOutFile = new FileOutputStream("cache" + File.separator + "QAQ" + parentPath.getFileName().hashCode() + inputFile.getName().hashCode() + "cne.tlas");
                saltOutFile.write(salt);
                saltOutFile.close();

                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
                SecretKey secretKey = factory.generateSecret(keySpec);
                SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secret);
                AlgorithmParameters params = cipher.getParameters();

                // 生成iv
                FileOutputStream ivOutFile = new FileOutputStream("cache" + File.separator + "QAQ" + parentPath.getFileName().hashCode() + inputFile.getName().hashCode() + "cne.vi");
                byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
                ivOutFile.write(iv);
                ivOutFile.close();

                // 加密文件的数据传输
                byte[] input = new byte[FileOperationConstant.BUFFER_LENGTH];
                int bytesRead;

                while ((bytesRead = inputStream.read(input)) != -1) {
                    byte[] output = cipher.update(input, 0, bytesRead);
                    if (output != null) {
                        outputStream.write(output);
                    }
                }

                byte[] output = cipher.doFinal();
                if (output != null) {
                    outputStream.write(output);
                }

                inputStream.close();
                outputStream.flush();
                outputStream.close();

                new NormalMessage(FileOperationConstant.FILE_ENCRYPTED_DONE_MESSAGE).showMessage();
            } catch (Exception e) {
                inputStream.close();
                outputStream.close();
                throw new Exception();
            }
        } else {
            new ErrorMessage(FileOperationConstant.FILE_SHOULD_SELECT_FILE_MESSAGE).showMessage();
            success = false;
        }
        return success;
    }

    /**
     * 解密文件工具
     * @param inputFile 需要解密的文件
     * @return 返回解密文件是否成功
     * @throws Exception
     */
    public boolean decryptFile(File inputFile) throws Exception {
        boolean success = true;
        if (!inputFile.isFile()) {
            success = false;
            new ErrorMessage(FileOperationConstant.FILE_SHOULD_SELECT_FILE_MESSAGE).showMessage();
        } else if (!inputFile.getName().endsWith("hwj")) {
            success = false;
            new ErrorMessage(FileOperationConstant.FILE_SHOULD_SELECT_ENCRYPTED_MESSAGE).showMessage();
        } else {
            Path parentPath = Paths.get(inputFile.getParent());
            StringBuilder builder = new StringBuilder(inputFile.getName());
            builder.delete(builder.length()-3, builder.length());
            File outputFile = new File(parentPath + File.separator + builder.toString());
            outputFile.createNewFile();
            InputStream inputStream = new FileInputStream(inputFile);
            OutputStream outputStream = new FileOutputStream(outputFile);

            try {
                String password = (String) new InputMessage(FileOperationConstant.FILE_INPUT_YOUR_PASSWORD_MESSAGE).showMessage();

                // reading the salt
                // user should have secure mechanism to transfer the
                // salt, iv and password to the recipient
                FileInputStream saltFis = new FileInputStream("cache" + File.separator + "QAQ" + parentPath.getFileName().hashCode() + builder.toString().hashCode() + "cne.tlas");
                byte[] salt = new byte[8];
                saltFis.read(salt);
                saltFis.close();

                // reading the iv
                FileInputStream ivFis = new FileInputStream("cache" + File.separator + "QAQ" + parentPath.getFileName().hashCode() + builder.toString().hashCode() + "cne.vi");
                byte[] iv = new byte[16];
                ivFis.read(iv);
                ivFis.close();

                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
                SecretKey tmp = factory.generateSecret(keySpec);
                SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

                // file decryption
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

                byte[] in = new byte[FileOperationConstant.BUFFER_LENGTH];
                int read;
                while ((read = inputStream.read(in)) != -1) {
                    byte[] output = cipher.update(in, 0, read);
                    if (output != null)
                        outputStream.write(output);
                }
                byte[] output = null;
                try {
                    output = cipher.doFinal();
                } catch (Exception e) {
                    success = false;
                    new ErrorMessage(FileOperationConstant.FILE_GET_WRONG_PASSWORD_MESSAGE).showMessage();
                }
                if (output != null) {
                    outputStream.write(output);
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();
                if (success) {
                    new NormalMessage(FileOperationConstant.FILE_DECRYPTED_DONE_MESSAGE).showMessage();
                }
            } catch (Exception e) {
                inputStream.close();
                outputStream.close();
                throw new Exception();
            }
        }
        return success;
    }

}
