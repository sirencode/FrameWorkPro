package com.syh.framework.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyonghe on 2019-11-25.
 */
public class MyCircularQueue {

    private int size;
    private List<Integer> list;

    /**
     * Initialize your data structure here. Set the size of the queue to be k.
     */
    public MyCircularQueue(int k) {
        this.size = k;
        list = new ArrayList<>();
    }

    /**
     * Insert an element into the circular queue. Return true if the operation is successful.
     */
    public boolean enQueue(int value) {
        if (!isFull()) {
            list.add(value);
            return true;
        }
        return false;
    }

    /**
     * Delete an element from the circular queue. Return true if the operation is successful.
     */
    public boolean deQueue() {
        if (!isEmpty()) {
            list.remove(0);
            return true;
        }
        return false;
    }

    /**
     * Get the front item from the queue.
     */
    public int Front() {
        if (list.size() > 0) {
            return list.get(0);
        }
        return -1;
    }

    /**
     * Get the last item from the queue.
     */
    public int Rear() {
        if (list.size() > 0) {
            return list.get(list.size() - 1);
        }
        return -1;
    }

    /**
     * Checks whether the circular queue is empty or not.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Checks whether the circular queue is full or not.
     */
    public boolean isFull() {
        return list.size() == size;
    }

    public static void main(String[] args) {
        MyCircularQueue circularQueue = new MyCircularQueue(3); // 设置长度为 3
        System.out.println(circularQueue.enQueue(1));// 返回 true
        System.out.println(circularQueue.enQueue(2));// 返回 true
        System.out.println(circularQueue.enQueue(3));// 返回 true
        System.out.println(circularQueue.enQueue(4));// 返回 false，队列已满
        System.out.println(circularQueue.Rear());// 返回 3
        System.out.println(circularQueue.isFull());// 返回 true
        System.out.println(circularQueue.deQueue());// 返回 true
        System.out.println(circularQueue.enQueue(4));// 返回 true
        System.out.println(circularQueue.Rear());// 返回 4
    }
}
