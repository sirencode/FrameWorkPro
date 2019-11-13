package com.syh.framework.algorithm.leetcode;

/**
 * Created by shenyonghe on 2019-11-07.
 * 题目描述：给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 */
public class AddTwoNum {

    /**
     * 时间复杂度：O(max(m, n))，假设 m 和 n 分别表示 l1 和 l2 的长度，上面的算法最多重复 max(m, n) 次。
     * 空间复杂度：O(max(m, n))，新列表的长度最多为 max(m,n)+1。
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(2);
        ListNode node1 = new ListNode(4);
        ListNode node2 = new ListNode(3);
        node.next = node1;
        node1.next = node2;

        ListNode nodeTwo = new ListNode(5);
        ListNode nodeTwo1 = new ListNode(6);
        ListNode nodeTwo2 = new ListNode(7);
        nodeTwo.next = nodeTwo1;
        nodeTwo1.next = nodeTwo2;

        ListNode result = addTwoNumbers(node, nodeTwo);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }
}
