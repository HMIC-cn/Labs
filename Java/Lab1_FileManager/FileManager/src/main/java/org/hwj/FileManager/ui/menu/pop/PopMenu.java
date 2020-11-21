package org.hwj.FileManager.ui.menu.pop;

import org.hwj.FileManager.ui.menu.button.*;

import javax.swing.*;

/**
 * 基于button包的弹出菜单栏
 * 继承于JPopMenu
 * 提供新建、删除、拷贝、重命名、属性、加密、解密、压缩、解压按钮
 * @author Hwj
 * @version 1.0
 */
public class PopMenu extends JPopupMenu {
    private NewFileButton newFileButton;
    private DeleteFileButton deleteFileButton;
    private CopyButton copyButton;
    private RenameButton renameButton;
    private AttributeButton attributeButton;

    private EncryptButton encryptButton;
    private DecryptButton decryptButton;

    private ZipButton zipButton;
    private UnZipButton unZipButton;

    public PopMenu() {
        this.newFileButton = new NewFileButton("新建");
        this.deleteFileButton = new DeleteFileButton("删除");
        this.copyButton = new CopyButton("拷贝");
        this.renameButton = new RenameButton("重命名");
        this.attributeButton = new AttributeButton("属性");
        this.encryptButton = new EncryptButton("加密");
        this.decryptButton = new DecryptButton("解密");
        this.zipButton = new ZipButton("压缩");
        this.unZipButton = new UnZipButton("解压");
        addItems();
    }

    public void addItems() {
        this.add(newFileButton);
        this.add(deleteFileButton);
        this.add(copyButton);
        this.add(renameButton);
        this.addSeparator();
        this.add(encryptButton);
        this.add(decryptButton);
        this.addSeparator();
        this.add(zipButton);
        this.add(unZipButton);
        this.addSeparator();
        this.add(attributeButton);
    }
}
