package com.syh.framework.data.structure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by shenyonghe on 2019-12-24.
 * 遍历：(前中后是指根间节点的次序，左右的次序始终是先左后右)
 * 1 前序遍历（深度优先遍历-DFS） 根 -> 左子树 -> 右子树
 * 2 中序遍历 左 -> 根 -> 右
 * 3 后序遍历 左 -> 右 -> 根
 * 4 层序遍历（广度优先遍历-BFS） 按层次 上->下 左->右
 */
public class Tree<T> {
    T val; // 二叉树的结点数据
    Tree<T> leftNode; // 二叉树的左子树（左孩子）
    Tree<T> rightNode; // 二叉树的右子树（右孩子）

    public Tree(T data, Tree<T> left, Tree<T> right) {
        this.val = data;
        this.leftNode = left;
        this.rightNode = right;
    }


    // 获得 & 设置二叉树的结点数据
    public T getData() {
        return val;
    }

    public void setData(T data) {
        this.val = data;
    }

    // 获得 & 设置二叉树的左子树（左孩子）
    public Tree getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Tree leftNode) {
        this.leftNode = leftNode;
    }

    // 获得 & 设置二叉树的右子树（右孩子）
    public Tree getRightNode() {
        return rightNode;
    }

    public void setRightNode(Tree rightNode) {
        this.rightNode = rightNode;
    }

    public static Tree init() {
        // 结构如下：(由下往上建立)
        //            A
        //       B         C
        //    D         E     F
        //  G   H         I
        Tree I = new Tree("I", null, null);
        Tree H = new Tree("H", null, null);
        Tree G = new Tree("G", null, null);
        Tree F = new Tree("F", null, null);
        Tree E = new Tree("E", null, I);
        Tree D = new Tree("D", G, H);
        Tree C = new Tree("C", E, F);
        Tree B = new Tree("B", D, null);
        Tree A = new Tree("A", B, C);
        return A;  // 返回根节点
    }

    public static void printTreeList(List<Tree<String>> list) {
        StringBuilder builder = new StringBuilder();
        if (!list.isEmpty()) {
            for (Tree<String> tree : list) {
                builder.append(tree.getData()).append(" ");
            }
        }
        System.out.println(builder.toString());
    }

    /**
     * 前序遍历也称深度优先遍历 中 -> 左 -> 右
     * 递归实现
     */
    public static List<Tree<String>> preOrder(Tree<String> root, List<Tree<String>> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (root == null) return list;
        list.add(root);
        // 遍历左子树
        preOrder(root.getLeftNode(), list);
        // 遍历右子树
        preOrder(root.getRightNode(), list);
        return list;
    }

    /**
     * 前序遍历也称深度优先遍历 中 -> 左 -> 右
     * 栈实现
     */
    public static List<Tree<String>> preOrderStack(Tree<String> root) {
        List<Tree<String>> list = new ArrayList<>();
        if (root == null) return list;
        Stack<Tree<String>> stack = new Stack<>();
        while (root != null || stack.size() > 0) {
            if (root != null) {
                list.add(root);
                stack.push(root);
                root = root.getLeftNode();
            } else {
                root = stack.pop().getRightNode();
            }
        }
        return list;
    }

    /**
     * 中序遍历 左 -> 中 -> 右
     * 遍历
     *
     * @param root
     */
    public static List<Tree<String>> inOrder(Tree<String> root, List<Tree<String>> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (root == null) return list;
        // 遍历左子树
        inOrder(root.getLeftNode(), list);
        list.add(root);
        // 遍历右子树
        inOrder(root.getRightNode(), list);
        return list;
    }

    /**
     * 中序遍历 左 -> 中 -> 右
     * 栈
     *
     * @param root
     */
    public static List<Tree<String>> inOrderStack(Tree<String> root) {
        List<Tree<String>> list = new ArrayList<>();
        if (root == null) return list;
        Stack<Tree<String>> stack = new Stack<>();
        while (root != null || stack.size() > 0) {
            if (root != null) {
                stack.push(root);
                root = root.getLeftNode();
            } else {
                root = stack.pop();
                list.add(root);
                System.out.println(root.getData());
                root = root.getRightNode();
            }
        }
        return list;
    }

    /**
     * 中序遍历 左 -> 右 -> 中
     *
     * @param root
     * @return
     */
    public static List<Tree<String>> postOrder(Tree<String> root, List<Tree<String>> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (root == null) return list;
        postOrder(root.getLeftNode(), list);
        postOrder(root.getRightNode(), list);
        list.add(root);
        return list;
    }

    /**
     * 后序遍历 左 -> 右 -> 中
     * 栈
     *
     * @param root
     * @return
     */
    public static List<Tree<String>> postOrderStack(Tree<String> root) {
        List<Tree<String>> list = new ArrayList<>();
        if (root == null) return list;
        Stack<Tree<String>> stack = new Stack<>();
        Stack<Tree<String>> out = new Stack<>();
        while (root != null || stack.size() > 0) {
            if (root != null) {
                stack.push(root);
                out.push(root);
                root = root.getRightNode();
            } else {
                root = stack.pop().getLeftNode();
            }
        }
        while (out.size() > 0) {
            list.add(out.pop());
        }
        return list;
    }

    public static List<Tree<String>> levelTravelBFS(Tree<String> root) {
        List<Tree<String>> list = new ArrayList<>();
        if (root == null) return list;
        Queue<Tree<String>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            list.add(root);
            if (root.getLeftNode() != null) queue.add(root.getLeftNode());
            if (root.getRightNode() != null) queue.add(root.getRightNode());
        }
        return list;
    }
}
