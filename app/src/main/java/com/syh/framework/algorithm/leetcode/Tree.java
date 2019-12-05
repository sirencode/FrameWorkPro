package com.syh.framework.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by shenyonghe on 2019-11-18.
 */
public class Tree {
    public static class TreeNode {
        int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static TreeNode buildDeep() {
        TreeNode root = new TreeNode(3);
        TreeNode rl = new TreeNode(9);
        TreeNode rr = new TreeNode(20);
        TreeNode rrl = new TreeNode(15);
        TreeNode rrr = new TreeNode(7);
        root.left = rl;
        root.right = rr;
        rr.left = rrl;
        rr.right = rrr;
        return root;
    }

    public static TreeNode buildOrderTree() {
        TreeNode root = new TreeNode(1);
        TreeNode rl = new TreeNode(2);
        TreeNode rr = new TreeNode(2);
        TreeNode rrl = new TreeNode(3);
        TreeNode rrr = new TreeNode(4);
        TreeNode rll = new TreeNode(4);
        TreeNode rlr = new TreeNode(3);
        root.left = rl;
        root.right = rr;
        rr.left = rrl;
        rr.right = rrr;
        rl.left = rll;
        rl.right = rlr;
        return root;
    }

    public static TreeNode buildSymTree() {
        TreeNode root = new TreeNode(2);
        TreeNode rl = new TreeNode(1);
        TreeNode rr = new TreeNode(4);
        TreeNode rrl = new TreeNode(3);
        TreeNode rrr = new TreeNode(5);
        root.left = rl;
        root.right = rr;
        rr.left = rrl;
        rr.right = rrr;
        return root;
    }

    public static class Record {
        TreeNode node;
        int level;

        Record(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    /**
     * 深度优先搜索(DFS)
     * 栈，压栈，出栈
     *
     * @param root
     * @return
     */
    public static int maxDepthDFS(TreeNode root) {
        if (root == null) return 0;
        Stack<Record> stack = new Stack<>();
        int depth = 0;
        stack.add(new Record(root, 1));
        Record curr;
        while (!stack.isEmpty()) {
            curr = stack.pop();
            depth = Math.max(curr.level, depth);
            if (curr.node.left != null) stack.add(new Record(curr.node.left, curr.level + 1));
            if (curr.node.right != null) stack.add(new Record(curr.node.right, curr.level + 1));
        }
        return depth;
    }

    /**
     * 广度优先搜索(BFS)
     * 队列，入队列，出队列
     *
     * @param root
     * @return
     */
    public static int maxDepthBFS(TreeNode root) {
        if (root == null) return 0;
        LinkedList<Record> queue = new LinkedList<>();
        queue.add(new Record(root, 1));
        int depth = 0;
        Record curr;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            depth = Math.max(depth, curr.level);
            if (curr.node.left != null) queue.add(new Record(curr.node.left, curr.level + 1));
            if (curr.node.right != null) queue.add(new Record(curr.node.right, curr.level + 1));
        }
        return depth;
    }

    /**
     * 给定一个二叉树，找出其最大深度。
     * 深度优先遍历, 递归版
     *
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * 节点的左子树只包含小于当前节点的数。
     * 节点的右子树只包含大于当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     *
     * @param root
     * @return
     */
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    /**
     * 对当前节点做大小的上下限。
     *
     * @param node
     * @param lower
     * @param upper
     * @return
     */
    public static boolean isValidBST(TreeNode node, Integer lower, Integer upper) {
        if (node == null) return true;
        int val = node.val;
        if (lower != null && val <= lower) return false;
        if (upper != null && val >= upper) return false;
        if (!isValidBST(node.right, val, upper)) return false;
        if (!isValidBST(node.left, lower, val)) return false;
        return true;
    }

    /**
     * 给定一个二叉树，检查它是否是镜像对称的。
     * 递归：如果一个树的左子树与右子树镜像对称，那么这个树是对称的。l=r r=l;
     *
     * @param root
     * @return
     */
    public static boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    public static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val)
                && isMirror(t1.right, t2.left)
                && isMirror(t1.left, t2.right);
    }

    /**
     * 迭代
     *
     * @param root
     * @return
     */
    public static boolean isSymmetricDD(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode t1 = q.poll();
            TreeNode t2 = q.poll();
            if (t1 == null && t2 == null) continue;
            if (t1 == null || t2 == null) return false;
            if (t1.val != t2.val) return false;
            q.add(t1.left);
            q.add(t2.right);
            q.add(t1.right);
            q.add(t2.left);
        }
        return true;
    }

    /**
     * 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) return levels;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            levels.add(new ArrayList<>());
            int level_length = queue.size();
            for (int i = 0; i < level_length; ++i) {
                TreeNode node = queue.remove();
                levels.get(level).add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            level++;
        }
        return levels;
    }

    public static void sout(List<TreeNode> list) {
        if (list == null || list.size() == 0) return;
        StringBuffer stringBuffer = new StringBuffer("");
        for (TreeNode treeNode : list) {
            stringBuffer.append(treeNode.val + "-");
        }
        System.out.println(stringBuffer.toString());
    }

    /**
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     * 二分
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return toBST(nums, 0, nums.length - 1);
    }

    public static TreeNode toBST(int nums[], int left, int right) {
        if (left > right) return null;
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = toBST(nums, left, mid - 1);
        root.right = toBST(nums, mid + 1, right);
        return root;
    }

    public static LinkedList<TreeNode> generate_trees(int start,int end) {
        LinkedList<TreeNode> all_trees = new LinkedList<TreeNode>();
        if (start > end) {
            all_trees.add(null);
            return all_trees;
        }
        for (int i = start; i <= end; i++) {
            LinkedList<TreeNode> left_trees = generate_trees(start, i - 1);
            LinkedList<TreeNode> right_trees = generate_trees(i + 1, end);
            for (TreeNode l : left_trees) {
                for (TreeNode r : right_trees) {
                    TreeNode current_tree = new TreeNode(i);
                    current_tree.left = l;
                    current_tree.right = r;
                    all_trees.add(current_tree);
                }
            }
        }
        return all_trees;
    }

    /**
     * 不同的二叉搜索树 II
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树。
     * 输入: 3
     * 输出:
     * [
     *   [1,null,3,2],
     *   [3,2,null,1],
     *   [3,1,null,null,2],
     *   [2,1,3],
     *   [1,null,2,null,3]
     * ]
     * @param n
     * @return
     */
    public static List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generate_trees(1, n);
    }

    public static void main(String[] args) {
        System.out.println(maxDepthDFS(buildDeep()));
        System.out.println(isValidBST(buildOrderTree()));
        System.out.println(isSymmetric(buildOrderTree()));
        System.out.println(isSymmetricDD(buildOrderTree()));
        levelOrder(buildOrderTree());
        sout(generateTrees(3));
    }
}
