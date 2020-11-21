package org.hwj.FileManager.message;

import javax.swing.*;

/**
 * 普通消息弹窗
 * @author Hwj
 * @version 1.0
 */
public class NormalMessage extends Message {

    public NormalMessage(String messageSet) {
        super(messageSet);
    }

    /**
     * 显示普通的提示消息
     * @return 可忽略的返回值
     */
    @Override
    public Object showMessage() {
        JOptionPane.showMessageDialog(null, this.message, null,JOptionPane.PLAIN_MESSAGE);
        return 0;
    }
}
