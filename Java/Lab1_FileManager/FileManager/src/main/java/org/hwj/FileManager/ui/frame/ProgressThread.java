package org.hwj.FileManager.ui.frame;

import javax.swing.*;

@Deprecated
public class ProgressThread extends Thread {

    JProgressBar progressBar;
    JButton button;
    //进度条上的数字
    int[] progressValues={6,18,27,39,51,66,81,100};
    public ProgressThread() {
        this.progressBar = new JProgressBar();
        this.button = new JButton("click");
    }

    public void run() {
        for(int i=0;i<progressValues.length;i++) {
            try {
                Thread.sleep(3000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            //设置进度条的值
            progressBar.setValue(progressValues[i]);
        }
        progressBar.setIndeterminate(false);
        progressBar.setString("升级完成！");
        button.setEnabled(true);
    }

    public static void main(String[] args) {
        new ProgressThread().start();
    }
}
