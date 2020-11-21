package org.hwj.FileManager.test.perfomance;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

/**
 * 文件遍历等操作采用dfs方式，可能存在栈溢出的风险
 * 故进行不同类型函数的最大递归深度测试
 * @author Hwj
 * @version 1.0
 */
public class StackOverTest {

    public static void main(String[] args) {
        dfs(1, "Hello", new DefaultMutableTreeNode());
    }

    public static void dfs(long x, String s, DefaultMutableTreeNode node) {
        File file = new File("conf");

        for (File f : file.listFiles()) ;
//        String s = "Hello" + x;
//        System.out.println(x);
        try {
            dfs(x + 1, s + x, node);
        } catch (StackOverflowError e) {
            System.out.println(x);
        }
    }

    /**
     * 测试结果如下：
     * 9623 int + File
     * 5263 int + String
     * 8482 long
     * 4419 long, String
     * 4420 long, String + File
     * 3184 long, String, TreeNode + TreeNode, File
     * 4316 long, String, TreeNode + File
     * 2700 long, String, TreeNode + FileList
     * 9692 long, String, TreeNode + FileListList
     */
}
