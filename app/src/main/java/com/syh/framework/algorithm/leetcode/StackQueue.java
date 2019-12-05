package com.syh.framework.algorithm.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by shenyonghe on 2019-11-27.
 */
public class StackQueue {
    /**
     * 每日温度
     * 根据每日气温列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。
     * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
     * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
     * stack push,stack中只保存比当前元素大的元素，比当前元素小与当前元素对比也没必要
     *
     * @param T
     * @return
     */
    public static int[] dailyTemperatures(int[] T) {
        int[] ans = new int[T.length];
        Stack<Integer> stack = new Stack();
        for (int i = T.length - 1; i >= 0; --i) {
            // 确保只保存栈顶比当前元素大的元素
            while (!stack.isEmpty() && T[i] >= T[stack.peek()]) stack.pop();
            ans[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }
        return ans;
    }

    /**
     * 岛屿数量
     * 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
     *
     * @param grid
     * @return
     */
    public static int numIslands(char[][] grid) {
        int row = grid.length;
        int column = grid[0].length;
        boolean[][] mark = new boolean[row][column];
        for (int i = 0; i < row; i++) {
            for (int i1 = 0; i1 < column; i1++) {

            }
        }
        return 0;
    }

    /**
     * 逆波兰表达式求值
     * 有效的运算符包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
     * 1 整数除法只保留整数部分。
     * 2 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
     * 就是运算符所在操作数的位置，在前面就是前缀，后面就是后缀，中间就是中缀了。
     * 中缀表达式：2+3 5+(4-2) (1+2)*3-4
     * 后缀表达式：23+ 542-+ 12+3*4-
     * 前缀表达式：+23 +5-42 -*+1234
     * <p>
     * 输入: ["4", "13", "5", "/", "+"]
     * 输出: 6
     * 解释: (4 + (13 / 5)) = 6
     *
     * @param tokens
     * @return
     */
    public static int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) return 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            String tmp = tokens[i];
            if ("+".equals(tmp)) {
                if (stack.size() >= 2) {
                    stack.push(stack.pop() + stack.pop());
                }
            } else if ("-".equals(tmp)) {
                if (stack.size() >= 2) {
                    stack.push(-stack.pop() + stack.pop());
                }
            } else if ("*".equals(tmp)) {
                if (stack.size() >= 2) {
                    stack.push(stack.pop() * stack.pop());
                }
            } else if ("/".equals(tmp)) {
                if (stack.size() >= 2) {
                    int first = stack.pop();
                    stack.push(stack.pop() / first);
                }
            } else {
                stack.push(Integer.valueOf(tokens[i]));
            }
        }
        return stack.isEmpty() ? 0 : stack.pop();
    }

    public static void main(String[] args) {
        int[] tmp = new int[]{73, 74, 75, 76, 77, 78, 79, 80};
        System.out.println(Arrays.toString(dailyTemperatures(tmp)));
        String[] token = new String[]{"4", "13", "5", "/", "+"};
        String[] token1 = new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println(evalRPN(token));
        System.out.println(evalRPN(token1));
    }
}
