package com.syh.framework.algorithm.leetcode;

import java.util.Stack;

/**
 * Created by shenyonghe on 2019-11-19.
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 */
public class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int x) {
        stack.push(x);
        minStack.push(minStack.isEmpty() || minStack.peek() >= x ? x : minStack.peek());
    }

    public void pop() {
        if (!stack.isEmpty()) {
            stack.pop();
            minStack.pop();
        }
    }

    public int top() {
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        throw new RuntimeException("栈中元素为空，此操作非法");
    }

    public int getMin() {
        if (!minStack.isEmpty()) {
            return minStack.peek();
        }
        throw new RuntimeException("栈中元素为空，此操作非法");
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin()); //  --> 返回 -3.
        minStack.pop();
        System.out.println(minStack.top()); // --> 返回 0.
        System.out.println(minStack.getMin()); // --> 返回 -2.
    }
}
