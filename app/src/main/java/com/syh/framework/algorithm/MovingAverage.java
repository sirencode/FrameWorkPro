package com.syh.framework.algorithm;

import java.util.LinkedList;

/**
 * Created by shenyonghe on 2019-11-27.
 * 数据流移动平均值
 */
public class MovingAverage {
    private LinkedList<Integer> dequeue = new LinkedList<>();
    private int size;
    private long sum;

    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        this.size = size;
    }

    public double next(int val) {
        if (dequeue.size() == size) sum -= dequeue.removeFirst();
        dequeue.addLast(val);
        sum += val;
        // System.out.printf("size=%d, val=%d, dequeue=%s, sum=%d\n", size, val, dequeue, sum);
        return (double)sum / dequeue.size();
    }

    public static void main(String[] args) {
        MovingAverage m = new MovingAverage(3);
        System.out.println(m.next(1)); // 1
        System.out.println(m.next(10)); // (1+10) / 2
        System.out.println(m.next(3));  // (1+10+3) / 3
        System.out.println(m.next(5));  // (5+10+3) / 3
    }
}
