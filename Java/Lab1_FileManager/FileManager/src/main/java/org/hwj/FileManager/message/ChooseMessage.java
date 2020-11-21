package org.hwj.FileManager.message;

import javax.swing.*;

/**
 * 选择是否的消息弹窗
 * @author Hwj
 * @version 1.0
 */
public class ChooseMessage extends Message {

    public ChooseMessage(String messageSet) {
        super(messageSet);
    }

    /**
     * 选择是或否的消息
     * @return 返回一个整数，为选择的结果，0表示是，1表示否
     */
    @Override
    public Object showMessage() {
        return JOptionPane.showConfirmDialog(null, this.message, null,JOptionPane.YES_NO_OPTION);
    }
}
