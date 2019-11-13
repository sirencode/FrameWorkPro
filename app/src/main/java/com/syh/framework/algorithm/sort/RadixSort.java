package com.syh.framework.algorithm.sort;

import java.util.Arrays;

/**
 * Created by shenyonghe on 2019-11-02.
 * 基数排序：
 * 按照个位，十位，百位等基数位进行排序
 * 时间复杂度：O(n*k)
 * 空间复杂度：O(n+k)
 * 稳定性：稳定
 */
public class RadixSort {
    public static int findMax(int[] arrays, int L, int R) {
        //如果该数组只有一个数，那么最大的就是该数组第一个值了
        if (L == R) {
            return arrays[L];
        } else {
            int a = arrays[L];
            int b = findMax(arrays, L + 1, R);//找出整体的最大值
            if (a > b) {
                return a;
            } else {
                return b;
            }
        }
    }


    public static void radixSort(int[] arrays) {
        int max = findMax(arrays, 0, arrays.length - 1);
        //需要遍历的次数由数组最大值的位数来决定
        for (int i = 1; max / i > 0; i = i * 10) {
            int[][] buckets = new int[arrays.length][10];
            //获取每一位数字(个、十、百、千位...分配到桶子里)
            for (int j = 0; j < arrays.length; j++) {
                int num = (arrays[j] / i) % 10;
                //将其放入桶子里
                buckets[j][num] = arrays[j];
            }
            //回收桶子里的元素
            int k = 0;
            //有10个桶子
            for (int j = 0; j < 10; j++) {
                //对每个桶子里的元素进行回收
                for (int l = 0; l < arrays.length; l++) {
                    //如果桶子里面有元素就回收(数据初始化会为0)
                    if (buckets[l][j] != 0) {
                        arrays[k++] = buckets[l][j];
                    }
                }

            }
            //每增加一个基数的位置变化
            System.out.println(Arrays.toString(arrays));
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 22, 14, 28, 66, 1065, 281};
        radixSort(arr);
    }
}
