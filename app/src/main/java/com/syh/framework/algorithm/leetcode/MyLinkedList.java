package com.syh.framework.algorithm.leetcode;

/**
 * Created by shenyonghe on 2019-12-02.
 * 设计链表的实现。您可以选择使用单链表或双链表。单链表中的节点应该具有两个属性：val 和 next。
 * val 是当前节点的值，next 是指向下一个节点的指针/引用。
 * 如果要使用双向链表，则还需要一个属性 prev 以指示链表中的上一个节点。假设链表中的所有节点都是 0-index 的。
 */
public class MyLinkedList {

    private Node head;
    private int size;

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public MyLinkedList() {
        head = new Node(-1);
        size = 0;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index < 0 || index >= size) return -1;
        Node curr = head.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.value;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index > size)
            return;
        if (index < 0)
            addAtHead(val);
        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node node = new Node(val);
        node.next = prev.next;
        prev.next = node;
        size++;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if(index < 0 || index >= size)
            return;
        Node prev = head;
        for(int i = 0; i < index; i++)
            prev = prev.next;
        Node reNode = prev.next;
        prev.next = reNode.next;
        reNode.next = null;
        size--;
    }

    public static void soutListNode(Node node) {
        if (node == null) return;
        StringBuffer buffer = new StringBuffer("start");
        while (node != null) {
            buffer.append("->");
            buffer.append(node.value);
            node = node.next;
        }
        System.out.println(buffer.toString());
    }


    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(1);
        linkedList.addAtTail(3);
        linkedList.addAtIndex(1, 2);   //链表变为1-> 2-> 3
        System.out.println(linkedList.get(1));  //返回2
        linkedList.deleteAtIndex(1);  //现在链表是1-> 3
        System.out.println(linkedList.get(1));  //返回3
    }
}
