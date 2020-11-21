package org.hwj.FileManager.ui.menu.bar;

import org.hwj.FileManager.ui.menu.button.*;
import javax.swing.*;


/**
 * 基于button包的顶部菜单栏
 * 继承于JMenuBar
 * 提供新建、删除、拷贝、重命名、属性、加密、解密、压缩、解压按钮
 * @author Hwj
 * @version 1.0
 */
public class MenuBar extends JMenuBar {
    private NewFileButton newFileButton;
    private DeleteFileButton deleteFileButton;
    private CopyButton copyButton;
    private RenameButton renameButton;
    private AttributeButton attributeButton;

    private EncryptButton encryptButton;
    private DecryptButton decryptButton;

    private ZipButton zipButton;
    private UnZipButton unZipButton;


    private JMenu fileDic, crypt, zip;

    public MenuBar() {
        // 一级菜单
        fileDic = new JMenu("文件夹...");
        crypt = new JMenu("加密...");
        zip = new JMenu("压缩...");
        // 二级菜单
        newFileButton = new NewFileButton("新建");
        deleteFileButton = new DeleteFileButton("删除");
        copyButton = new CopyButton("拷贝");
        renameButton = new RenameButton("重命名");
        attributeButton = new AttributeButton("属性");

        encryptButton = new EncryptButton("加密");
        decryptButton = new DecryptButton("解密");
        zipButton = new ZipButton("压缩");
        unZipButton = new UnZipButton("解压");

        this.addItems();
    }

    private void addItems() {
        fileDic.add(newFileButton);
        fileDic.add(deleteFileButton);
        fileDic.add(copyButton);
        fileDic.add(renameButton);
        fileDic.add(attributeButton);
        this.add(fileDic);

        crypt.add(encryptButton);
        crypt.add(decryptButton);
        this.add(crypt);

        zip.add(zipButton);
        zip.add(unZipButton);
        this.add(zip);
    }

}