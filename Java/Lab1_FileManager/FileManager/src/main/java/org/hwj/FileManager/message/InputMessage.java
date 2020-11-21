package org.hwj.FileManager.message;

import javax.swing.*;

/**
 * 获取输入的消息弹窗
 * @author Hwj
 * @version 1.0
 */
public class InputMessage extends Message {
    public InputMessage(String messageSet) {
        super(messageSet);
    }

    /**
     * 获取用户输入的字符串
     * @return 返回一个用户输入的字符串
     */
    public Object showMessage() {
        String getInput = JOptionPane.showInputDialog(null, this.message);
        return getInput;
    }
}
