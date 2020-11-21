package org.hwj.FileManager.message;

/**
 * 消息机制顶层类
 * @author Hwj
 * @version 1.0
 */
public abstract class Message {
    protected String message;

    public Message(String messageSet) {
        message = messageSet;
    }

    public abstract Object showMessage();
}
