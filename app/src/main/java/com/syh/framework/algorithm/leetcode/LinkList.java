package com.syh.framework.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shenyonghe on 2019-11-16.
 */
public class LinkList {

    public static class ListNode {
        public int val;
        public ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    //{4,5,1,9}
    public static ListNode buildDeleteNode() {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        return node;
    }

    public static ListNode buildcircleNode() {
        ListNode node = new ListNode(1);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(3);
        ListNode node3 = new ListNode(4);
        node.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node2;
        return node;
    }

    public static List<ListNode> buildIntersetionNode() {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        node1.next = node2;
        node2.next = node5;
        node5.next = node6;

        node3.next = node4;
        node4.next = node5;
        List<ListNode> listNodes = new ArrayList<>();
        listNodes.add(node1);
        listNodes.add(node3);
        return listNodes;
    }

    public static void soutListNode(ListNode node) {
        if (node == null) return;
        StringBuffer buffer = new StringBuffer("start");
        while (node != null) {
            buffer.append("->");
            buffer.append(node.val);
            node = node.next;
        }
        System.out.println(buffer.toString());
    }

    /**
     * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。
     * 链表至少包含两个节点。
     * 链表中所有节点的值都是唯一的。
     * 给定的节点为非末尾节点并且一定是链表中的一个有效节点。
     * 不要从你的函数中返回任何结果。
     *
     * @param node
     */
    public static void deleteNode(ListNode head, ListNode node) {
        while (head != null && head.next != null) {
            if (head.val == node.val) {
                head.val = head.next.val;
                head.next = head.next.next;
                return;
            }
            head = head.next;
        }
    }

    /**
     * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
     * 采用两个间隔为n的指针，同时向前移动。当快指针的下一个节点为最后一个节点时，要删除的节点就是慢指针的下一个节点。
     *
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode start = pre, end = pre;
        while (n != 0) {
            start = start.next;
            n--;
        }
        while (start.next != null) {
            start = start.next;
            end = end.next;
        }
        //跳过第n个元素
        end.next = end.next.next;
        return pre.next;
    }

    /**
     * 就是从头结点找到尾节点，然后从尾节点往回走，再将当节点的下一个节点的next设置为当前节点
     *
     * @param head reverseList(1,2,3,4)(2,3,4)(3,4)(4) -> 2.next.next == null head {3->4} 3->4->3 3->null 4->3->null;
     *             2.n.n = null
     *             <p>
     *             reverseList
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        // head看作是前一结点，head.next 是当前结点
        if (head == null || head.next == null) return head;
        // 反转后新链表的头结点，最后一个元素
        ListNode p = reverseList(head.next);
        // 是当前结点的下一个节点指向当前节点的前一个节点
        head.next.next = head;
        // 前一个节点的指针null
        head.next = null;
        return p;
    }

    /**
     * 迭代方式实现：记录每一个指针的当前指针，前一个以及下一个指针。当前的下一个指向前一个，前一个等于当前，当前等于下一个
     *
     * @param head
     * @return
     */
    public static ListNode reverseListDD(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }


    /**
     * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * 迭代实现
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode node = result;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                result.next = l1;
                l1 = l1.next;
            } else {
                result.next = l2;
                l2 = l2.next;
            }
            result = result.next;
        }
        result.next = l1 == null ? l2 : l1;
        return node.next;
    }

    /**
     * 递归实现
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoListsDG(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    /**
     * 请判断一个链表是否为回文链表。
     *
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        //快慢指针找到链表的中点
        ListNode fast = head.next.next;
        ListNode slow = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        //翻转链表前半部分
        ListNode pre = null;
        ListNode next = null;
        while (head != slow) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        //如果是奇数个节点，去掉后半部分的第一个节点。

        if (fast != null) {
            slow = slow.next;
        }
        //回文校验
        while (pre != null) {
            if (pre.val != slow.val) {
                return false;
            }
            pre = pre.next;
            slow = slow.next;
        }
        return true;
    }

    /**
     * 给定一个链表，判断链表中是否有环。
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
     * 哈希表添加元素的形式。
     *
     * @param head
     * @return
     */
    public static boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            } else {
                set.add(head);
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 快慢指针，
     *
     * @param head
     * @return
     */
    public static boolean hasCyclePoint(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 两两交换链表中的节点
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 示例：
     * 给定 1->2->3->4, 你应该返回 2->1->4->3.
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    /**
     * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 说明：不允许修改给定的链表。
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode intersect = getIntersect(head);
        if (intersect == null) return null;
        ListNode head1 = head;
        ListNode point = intersect;
        while (head1 != point) {
            head1 = head1.next;
            point = point.next;
        }
        return head1;
    }

    /**
     * 找到环中的
     *
     * @param head
     * @return
     */
    private static ListNode getIntersect(ListNode head) {
        ListNode slow = head;
        ListNode quick = head;
        while (quick != null && quick.next != null) {
            slow = slow.next;
            quick = quick.next.next;
            if (slow == quick) {
                return slow;
            }
        }
        return null;
    }

    /**
     * 相交链表
     * 编写一个程序，找到两个单链表相交的起始节点。
     * 注意：
     * 如果两个链表没有交点，返回 null.
     * 在返回结果后，两个链表仍须保持原有的结构。
     * 可假定整个链表结构中没有循环。
     * 程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        return null;
    }

    public static void main(String[] args) {
        ListNode head = buildDeleteNode();
        ListNode head2 = buildDeleteNode();
        deleteNode(head, new ListNode(0));
        soutListNode(head);
        reverseList(head);
        soutListNode(mergeTwoLists(head, head2));
        System.out.println(hasCyclePoint(buildcircleNode()));
        soutListNode(swapPairs(head2));
        System.out.println(detectCycle(buildcircleNode()).val);
    }
}
