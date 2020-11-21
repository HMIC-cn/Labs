package org.hwj.FileManager.file.structure;


import org.hwj.FileManager.constants.*;
import org.hwj.FileManager.ui.menu.pop.*;
import org.hwj.FileManager.message.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * FileTree内置了一个JTree
 * 提供在JTree中生成文件树的功能
 * @author Hwj
 * @version 1.0
 */
public class FileTree {
    private JTree tree;
    public DefaultMutableTreeNode treeRoot;
    private PopMenu popMenu = new PopMenu();

    /**
     * 获得FileTree中的JTree
     * @return 返回FileTree中的JTree
     */
    public JTree getTree() {
        return tree;
    }

    /**
     * 以整个磁盘为根节点建立文件树
     * @param rootName 为设置的树的根节点名字
     * TODO 修改为设置树根为电脑名字
     */
    public void initDiskRootTree(String rootName) {
        treeRoot = new DefaultMutableTreeNode(rootName, true);
        DefaultMutableTreeNode root;
        tree = new JTree(treeRoot);
        init();
        for (File file : File.listRoots()) {
            root = new DefaultMutableTreeNode(file.getPath());
            expandTree(root, file.getPath(), 1, 1);
            treeRoot.add(root);
        }
        DefaultTreeModel model = new DefaultTreeModel(treeRoot, true);
        tree.setModel(model);
    }

    public DefaultMutableTreeNode getSelNode() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        return node;
    }

    /**
     * 获取当前选择的节点，并转换为文件路径
     * @return 返回文件路径
     */
    public File getSelFile() {
        DefaultMutableTreeNode root = getSelNode();
        if (root != null) {
            String filePath = toFilePath(root.getPath());
            File file = null;
            if (filePath != null) file = new File(filePath);
            return file;
        } else return null;
    }

    public void setSelFile(File file) {
        if (file == null) {
            System.out.println("FILE NULL");
            DefaultMutableTreeNode node = getSelNode();
            tree.setSelectionPath(null);
            tree.setSelectionPath(new TreePath(node.getPath()));
            ((DefaultTreeModel) tree.getModel()).reload(node);
            return;
        }
        DefaultMutableTreeNode root = getSelNode();
        if (!getSelFile().isDirectory()) root = (DefaultMutableTreeNode) root.getParent();
        tree.expandPath(new TreePath(root.getPath()));
        int len = root.getChildCount();
        for (int i = 0; i < len; ++i) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
            if (node.getUserObject().equals(file.getName())) {
                tree.setSelectionPath(new TreePath(node.getPath()));
                break;
            }
        }
    }

    public void nodeUp() {
        System.out.println("NODE UP");
        DefaultMutableTreeNode node = getSelNode();
        System.out.println("SELECT NODE = " + node.getUserObject());
        System.out.println("SELECT FILE = " + getSelFile());
        if (treeRoot.equals(node.getParent())) return;
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) node.getParent();
        tree.setSelectionPath(new TreePath(root.getPath()));
        ((DefaultTreeModel) tree.getModel()).reload(root);
    }


    /**
     * JTree中紧耦合的更新响应
     * 考虑到性能问题，动态地加载节点
     * 在展开某个文件夹之前，先向下遍历两层
     * 第一层实现动态的加载功能，第二层实现第一层加载的文件夹的正常显示（而不是显示为文件）
     */
    private void initActions() {
        // 选择响应，阻止访问一些拒绝访问的文件夹
        tree.addTreeSelectionListener(e -> {
            System.out.println("IN SELECT " + getSelFile());
            if (canEnter()) selectListenExpand(tree);
            else new ErrorMessage(FileOperationConstant.FILE_DIRECTORY_ENTER_NOT_PERMITTED).showMessage();
        });
        // 展开响应，把展开的节点设置为选择节点，便于处理动态加载节点
        tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) {
                tree.setSelectionPath(event.getPath());
                ((DefaultTreeModel) tree.getModel()).reload(getSelNode());
            }
            @Override
            public void treeWillCollapse(TreeExpansionEvent event) {}
        });
        // 在树上添加鼠标操作
        tree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            // 右键点击释放的弹出菜单
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isMetaDown()) {
                    popMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * 对于JTree的UI进行一些简单的调整
     */
    private void initUI() {
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.putClientProperty("JTree.lineStyle","None");
    }

    /**
     * 完整初始化函数
     */
    private void init() {
        initUI();
        initActions();
    }

    /**
     * 将树节点路径转换为文件路径
     * @param path 需要转换的树节点路径，即以getPath()获取的TreeNode数组
     * @return 返回的是由path转化成的文件路径
     */
    private String toFilePath(TreeNode[] path) {
        // 如果访问到建立的树的根节点则返回空
        if (path.length <= 1) {
            return null;
        }
        StringBuilder filePath = new StringBuilder(path[1] + "");
        for (int i = 2; i < path.length; ++i) {
            filePath.append(path[i]).append(File.separator);
        }
        return filePath.toString();
    }


    /**
     * 判断是否是拒绝访问的文件夹
     * @return 返回判断结果
     */
    private boolean canEnter() {
        File file = getSelFile();
        return file == null || !file.isDirectory() || file.listFiles() != null;
    }

    private void expandNode(DefaultMutableTreeNode root, File file) {
        if (file.isFile()) {
            return;
        } else {
            DefaultMutableTreeNode node;
            if (file.listFiles() != null) {
                for (File file1 : file.listFiles()) {
                    if (file1.isFile()) {
                        node = new DefaultMutableTreeNode(file1.getName(), false);
                        root.add(node);
                    } else if (file1.isDirectory()) {
                        node = new DefaultMutableTreeNode(file1.getName(), true);
                        root.add(node);
                    }
                }
            }
        }
    }

    /**
     * 执行节点的展开响应，向下更新
     * @param tree FileTree包含的JTree
     */
    private void selectListenExpand(JTree tree) {
        DefaultMutableTreeNode root = getSelNode();
        if (root == null || root.getPath().length <= 1) {
            return;
        }
        File file = getSelFile();

        if (file.isFile()) {
            return;
        }

        if (root.isLeaf()) {
            expandNode(root, file);
            ((DefaultTreeModel) tree.getModel()).reload(root);
        } else if (root.getLeafCount() == root.getChildCount()) {
            root.removeAllChildren();
            expandNode(root, file);
            ((DefaultTreeModel) tree.getModel()).reload(root);
        }
    }

    @Deprecated
    public void initAltRootTree(File file, String fileName) {
        treeRoot = new DefaultMutableTreeNode(fileName);
        DefaultMutableTreeNode root;
        tree = new JTree(treeRoot);
        init();
        for (File f : file.listFiles()) {
            root = new DefaultMutableTreeNode(f.getName());
            expandTree(root, f.getPath(), 1, 1);
            treeRoot.add(root);
        }
    }

    /**
     * 以某个根节点代表的文件路径向下展开，并将子树挂在根节点下
     * @param root 需要展开的树节点
     * @param filePath 当前的文件路径
     * @param depth 当前已经展开深度
     * @param Max 最大展开深度限制
     */
    @Deprecated
    private void expandTree(DefaultMutableTreeNode root, String filePath, int depth, int Max) {
        // 控制展开的层数
        if (depth > Max) {
            return;
        }
        File file = new File(filePath);
        DefaultMutableTreeNode node;
        // listFile() == null 表示该文件或文件夹没有访问权限
        // 如果强行遍历会出现NullPoint错误
        if (!file.exists() || (file.listFiles() == null)) {
            return;
        }
        if (file.isFile()) {
            node = new DefaultMutableTreeNode(file.getName());
            root.add(node);
        } else if (file.listFiles() != null) {
            for (File f : file.listFiles()) {
                node = new DefaultMutableTreeNode(f.getName(), f.isDirectory());
                root.add(node);
                if (f.isDirectory()) {
                    expandTree(node, f.getAbsolutePath(), depth + 1, Max);
                }
            }
        }
    }

}