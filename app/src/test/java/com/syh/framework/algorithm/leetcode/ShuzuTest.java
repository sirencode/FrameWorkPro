package com.syh.framework.algorithm.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shenyonghe on 2019-12-18.
 */
public class ShuzuTest {

    @Test
    public void majorityElement() {
        int[] num1 = new int[]{3, 2, 3};
        int[] num2 = new int[]{2, 2, 1, 1, 1, 2, 2};
        assertEquals(Shuzu.majorityElement(num1), 3);
        assertEquals(Shuzu.majorityElement(num2), 2);
    }

    @Test
    public void searchMatrix() {
        int[][] matrix = new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        assertEquals(Shuzu.searchMatrix(matrix, 5), true);
        assertEquals(Shuzu.searchMatrix(matrix, 20), false);
    }

    @Test
    public void superEggDrop() {
        assertEquals(Shuzu.superEggDrop(1, 2), 2);
        assertEquals(Shuzu.superEggDrop(2, 6), 3);
        assertEquals(Shuzu.superEggDrop(3, 14), 4);
        assertEquals(Shuzu.superEggDrop(2, 2), 2);
    }

    @Test
    public void threeSum() {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        assertEquals(Shuzu.threeSum(nums).size(), 2);
    }
}