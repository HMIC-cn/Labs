package org.hwj.FileManager.message;

import javax.swing.*;

/**
 * 错误信息的弹窗
 * @author Hwj
 * @version 1.0
 */
public class ErrorMessage extends Message {

    public ErrorMessage(String messageSet) {
        super(messageSet);
    }

    /**
     * 显示错误信息
     * @return 可忽略的返回值
     */
    @Override
    public Object showMessage() {
        JOptionPane.showMessageDialog(null, this.message, "Error",JOptionPane.QUESTION_MESSAGE);
        System.out.println(this.message);
        return 0;
    }
}