package org.hwj.FileManager.ui.menu.button;

import org.hwj.FileManager.ui.frame.FilePropertiesFrame;
import java.io.File;

/**
 * 属性按钮，响应点击时调用FilePropertiesFrame用于显示文件属性
 * @author Hwj
 * @version 1.0
 */
public class AttributeButton extends Button {

    public AttributeButton(String text) {
        super(text);
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("Attribute Click");
            File file = this.getFile();
            if (file != null) {
                new FilePropertiesFrame(file);
            }
        });
    }
}
