package org.hwj.FileManager.test.operation;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@Deprecated
public class JProgressBarDemo extends JFrame
{
    //static JProgressBarDemo frame;
    public JProgressBarDemo()
    {
        setTitle("使用进度条");
        JLabel label=new JLabel("欢迎使用在线升级功能！");
        //创建一个进度条
        JProgressBar progressBar=new JProgressBar();
        JButton button=new JButton("完成");
        button.setEnabled(false);
        Container container=getContentPane();
        container.setLayout(new GridLayout(3,1));
        JPanel panel1=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel3=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel1.add(label);    //添加标签
        panel2.add(progressBar);    //添加进度条
        panel3.add(button);    //添加按钮
        container.add(panel1);
        container.add(panel2);
        container.add(panel3);
        progressBar.setStringPainted(true);
        //如果不需要进度上显示“升级进行中...”，可注释此行
        progressBar.setString("升级进行中...");
        //如果需要使用不确定模式，可使用此行
        //progressBar.setIndeterminate(true);
        //开启一个线程处理进度
        new Progress(progressBar, button).start();
        //单机“完成”按钮结束程序
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                System.exit(0);
            }
        });
    }
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        JProgressBarDemo frame=new JProgressBarDemo();
        //frame.setBounds(300,200,300,150);    //设置容器的大小
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    private class Progress extends Thread
    {
        JProgressBar progressBar;
        JButton button;
        //进度条上的数字
        int[] progressValues={6,18,27,39,51,66,81,100};
        Progress(JProgressBar progressBar,JButton button)
        {
            this.progressBar=progressBar;
            this.button=button;
        }
        public void run()
        {
            for(int i=0;i<progressValues.length;i++)
            {
                try
                {
                    Thread.sleep(3000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                //设置进度条的值
                progressBar.setValue(progressValues[i]);
            }
            progressBar.setIndeterminate(false);
            progressBar.setString("升级完成！");
            button.setEnabled(true);
        }
    }
}