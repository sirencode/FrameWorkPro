package com.syh.framework.data.structure;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shenyonghe on 2019-12-24.
 * // 结构如下：(由下往上建立)
 * //            A
 * //       B         C
 * //    D         E     F
 * //  G   H         I
 * 前序：A B D G H C E I F
 * 中序：G D H B A E I C F
 * 后序：G H D B I E F C A
 */
public class TreeTest {

    private Tree<String> tree;

    @Before
    public void init() {
        tree = Tree.init();
    }

    /**
     * 前序递归：A B D G H C E I F
     */
    @Test
    public void preOrder() {
        System.out.println("前序遍历-递归");
        Tree.printTreeList(Tree.preOrder(tree, null));
    }

    /**
     * 前序栈：A B D G H C E I F
     */
    @Test
    public void preOrderStack() {
        System.out.println("前序遍历-栈");
        Tree.printTreeList(Tree.preOrderStack(tree));
    }

    /**
     * 中序递归：G D H B A E I C F
     */
    @Test
    public void inOrder() {
        System.out.println("中序遍历-递归");
        Tree.printTreeList(Tree.inOrder(tree, null));
    }

    /**
     * 中序递归：G D H B A E I C F
     */
    @Test
    public void inOrderStack() {
        System.out.println("中序遍历-栈");
        Tree.printTreeList(Tree.inOrderStack(tree));
    }

    /**
     * 后序递归：G D H B A E I C F
     */
    @Test
    public void postOrder() {
        System.out.println("后序遍历-递归");
        Tree.printTreeList(Tree.postOrder(tree, null));
    }

    /**
     * 后序递归：G D H B A E I C F
     */
    @Test
    public void postOrderStack() {
        System.out.println("后序遍历-栈");
        Tree.printTreeList(Tree.postOrderStack(tree));
    }

    /**
     * 层序遍历: A B C D E F G H I
     */
    @Test
    public void levelTravelBFS() {
        System.out.println("层序遍历-队列");
        Tree.printTreeList(Tree.levelTravelBFS(tree));
    }
}