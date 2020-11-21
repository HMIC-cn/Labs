package org.hwj.FileManager.ui.frame;


import org.hwj.FileManager.ui.menu.button.Button;
import org.hwj.FileManager.ui.pane.FileBackBar;
import org.hwj.FileManager.ui.pane.FileTablePane;
import org.hwj.FileManager.ui.pane.FileTreePane;
import org.hwj.FileManager.ui.menu.bar.MenuBar;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Enumeration;

/**
 * 主视图
 * 组织以下组件:
 * JSplitPane装载的，分列左右的FileTreePane, FileTablePane
 * 顶部的MenuBar和FileBackBar
 * @author Hwj
 * @version 1.0
 */
public class MainFrame extends JFrame {
    private final int FRAME_WIDTH = 1080, FRAME_HEIGHT = 640;

    private FileTreePane leftFileTreePane;
    private MenuBar menuBar;
    private JSplitPane splitPane;
    private FileTablePane fileTablePane;
    private FileBackBar fileBackBar;

    public MainFrame(String title) throws HeadlessException {
        super(title);
        initGlobalFontSetting();
        leftFileTreePane = new FileTreePane();
        menuBar = new MenuBar();
        splitPane = new JSplitPane();
        fileTablePane = new FileTablePane();
        fileBackBar = new FileBackBar();
        initFrame();
    }

    /**
     * MainFrame的显示函数
     */
    public void start() {
        this.setVisible(true);
    }

    /**
     * 初始化所有设置
     * 包括FileTree, FileTable的加载
     * JFrame的常规设置
     * FileTablePane, FileTreePane, FileBackBar的核心交互监听
     */
    private void initFrame() {
        Button.setFileTree(leftFileTreePane.getFileTree());
        Button.setFileTable(fileTablePane.getFileTable());

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        splitPane.setLeftComponent(leftFileTreePane);
        splitPane.setRightComponent(fileTablePane);
        splitPane.setDividerSize(4);

        this.setJMenuBar(menuBar);
        this.add(fileBackBar, BorderLayout.NORTH);
        this.add(splitPane);

        leftFileTreePane
                .getFileTree()
                .getTree()
                .addTreeSelectionListener(e -> {
                    fileBackBar.update(leftFileTreePane.getFileTree().getSelFile());
                    if (fileTablePane.getFileTable().canUpdate) {
                        fileTablePane.getFileTable().update(leftFileTreePane.getFileTree().getSelFile());
                    }
                });

        fileBackBar
                .backButton
                .addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        leftFileTreePane.getFileTree().nodeUp();
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });

        // 年轻人不讲码德，他们说我是乱写的，我可不是乱写的啊
        // 让右侧的控制能够与左侧的JTree同步，方便功能操作
        // 1 若单击，则将左侧树节点选中为当前文件，但不进入
        // 若之前选过该文件夹下其它文件，则将节点提升之后再选择
        // 2 若双击，则进入改文件夹，即将选中节点设置为表格选中文件并进入
        // 提升节点操作与单击同理，同时阻止进入文件
        fileTablePane.getFileTable().getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!e.isMetaDown()) {
                    int row = fileTablePane.getFileTable().getTable().getSelectedRow();
                    if (e.getClickCount() <= 1) {
                        // 单击操作
                        fileTablePane.getFileTable().canUpdate = false;
                        if (row >= 0) {
                            File file1 = leftFileTreePane.getFileTree().getSelFile();
                            File file2 = fileTablePane.getFileTable().getSelFile();

                            if (file1 != null && file1.getParent() != null && file2 != null && !file1.getParent().equals(file2.getParent())) {
                                leftFileTreePane.getFileTree().nodeUp();
                            }

                            File file = leftFileTreePane.getFileTree().getSelFile();
                            String filePath = file.getAbsolutePath()
                                    + File.separator
                                    + fileTablePane
                                        .getFileTable()
                                        .getTable()
                                        .getValueAt(fileTablePane.getFileTable().getTable().getSelectedRow(), 0);
                            leftFileTreePane.getFileTree().setSelFile(new File(filePath));
                        }
                    } else {
                        // 双击操作
                        fileTablePane.getFileTable().canUpdate = true;
                        if (row >= 0) {
                            File file = leftFileTreePane.getFileTree().getSelFile();
                            if (file == null || file.isFile()) return;
                            String filePath = (String) fileTablePane
                                    .getFileTable()
                                    .getTable()
                                    .getValueAt(fileTablePane.getFileTable().getTable().getSelectedRow(), 0);
                            if (file.getName().equals(filePath)) {
                                leftFileTreePane.getFileTree().setSelFile(null);
                            } else {
                                leftFileTreePane.getFileTree().setSelFile(new File(file.getAbsolutePath() + File.separator + filePath));
                            }
                        }
                    }
                }
                fileTablePane.getFileTable().canUpdate = true;
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * 全局字体设置
     * 将所有组件的字体设为微软雅黑，普通，12号
     */
    private void initGlobalFontSetting() {
        System.out.println("DONE");
        FontUIResource fontRes = new FontUIResource("微软雅黑",Font.PLAIN,12);
        for(Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }

    @Deprecated
    private void uiSettings() {
        initGlobalFontSetting();
        ImageIcon icon1 = new ImageIcon("src/main/resources/icons/treeCollapsed.png");
        ImageIcon icon2 = new ImageIcon("src/main/resources/icons/treeExpanded.png");
        UIManager.put("Tree.collapsedIcon", icon1);
        UIManager.put("Tree.expandedIcon", new ImageIcon(icon2
                .getImage()
                .getScaledInstance(12,6,Image.SCALE_FAST)));
    }
}
