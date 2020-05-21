package com.syh.framework.algorithm;

/**
 * Created by shenyonghe on 2020/5/19.
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class TwoDimenArrayFind {
    /**
     * 暴力法
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     *
     * @param target
     * @param array
     * @return
     */
    public static boolean find(int target, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 左下
     * 时间复杂度：O(row + col)
     * 空间复杂度：O(1)
     *
     * @param target
     * @param array
     * @return
     */
    public static boolean leftFind(int target, int[][] array) {
        int rows = array.length; //行数
        if (rows == 0) return false;
        int cols = array[0].length; // 列
        if (cols == 0) return false;
        // 左下
        int row = rows - 1;
        int col = 0;
        while (row >= 0 && col < cols) {
            if (array[row][col] < target) {
                col++;
            } else if (array[row][col] > target) {
                row--;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 右上
     * 时间复杂度：O(row + col)
     * 空间复杂度：O(1)
     *
     * @param target
     * @param array
     * @return
     */
    public static boolean rightFind(int target, int[][] array) {
        int rows = array.length; //行数
        if (rows == 0) return false;
        int cols = array[0].length; // 列
        if (cols == 0) return false;
        // 右上
        int row = 0;
        int col = cols - 1;
        while (row < rows && col >= 0) {
            if (array[row][col] < target) {
                row++;
            } else if (array[row][col] > target) {
                col--;
            } else {
                return true;
            }
        }
        return false;
    }
}
