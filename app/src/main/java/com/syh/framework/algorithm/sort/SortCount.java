package com.syh.framework.algorithm.sort;

import java.util.Arrays;

/**
 * Created by shenyonghe on 2019-11-01.
 * 时间复杂度：O(n+k)
 * 空间复杂度：O(n+k)
 * k = max - min + 1
 * 稳定性：稳定
 * 计数排序适用于有明确范围的数组，比如给定一个数组，且知道所有值得范围是[m,n](非负数)。
 * 这个时候可以使用一个n-m+1长度的数组，待排序的数组就可以散在这个数组上，数组的值就是当前值的个数，再经过一次遍历展开，得到的数组就有序了。
 */
public class SortCount {
    public static void main(String[] args) {
        //测试
        int[] arr = {1, 4, 6, 7, 5, 4, 3, 2, 1, 4, 5, 10, 9, 10, 3};
        sortCount(arr, 1, 10);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1 新建一个长度为n-m+1的临时数组
     * 2 遍历待排序数组，它的值-m作为临时数组下角标，这个位置的值加1
     * 3 遍历结束，临时数组就存储了每个值得个数
     * 4 最后将它展开赋值给原数组
     *
     * @param arr
     * @param m
     * @param n
     */
    public static void sortCount(int[] arr, int m, int n) {
        int len = arr.length;
        int[] tem = new int[n - m + 1];
        //计算每个位置上的值得个数=>[2, 1, 2, 3, 2, 1, 1, 0, 1, 2] => 从左到右依次是最小到最大的值的个数
        for (int i = 0; i < len; i++) {
            tem[arr[i] - m] += 1;
            System.out.println(Arrays.toString(tem));
        }
        //根据每个位置的值的个数向数组里面添加值，对应位置的值=i+m
        for (int i = 0, index = 0; i < tem.length; i++) {
            int item = tem[i];
            while (item-- != 0) {
                arr[index++] = i + m;
            }
        }
    }
}
