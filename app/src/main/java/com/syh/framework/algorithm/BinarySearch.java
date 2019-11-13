package com.syh.framework.algorithm;

import java.util.Arrays;

/**
 * Created by shenyonghe on 2019-10-21.
 * 二分查找，数组必须是有序的。
 * 时间复杂度，O(log2n)-(n在此处是数组的元素数量,是以2为底，n的对数)
 */
public class BinarySearch {
    private static final int[] source = new int[]{7, 1, 3, 9, 6, 8, 11, 30, 100};

    public static int rank(int key) {
        int start = 0;
        int end = source.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (key < source[mid]) end = mid - 1;
            else if (key > source[mid]) start = mid + 1;
            else return mid;
        }
        System.out.println("未找到");
        return -1;
    }

    public static void main(String[] args) {
        Arrays.sort(source);
        int index = rank(8);
        if (index < 0) {
            System.out.println("未找到");
        } else {
            System.out.println(8 + "在第" + index);
        }
    }
}
