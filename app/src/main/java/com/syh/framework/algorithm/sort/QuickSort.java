package com.syh.framework.algorithm.sort;

import java.util.Arrays;

/**
 * Created by shenyonghe on 2019-11-01.
 *
 */
public class QuickSort {

    private static int partition(int[] arr, int left, int right) {
        int temp = arr[left];
        while (right > left) {
            // 先判断基准数和后面的数依次比较,只要大于基准点不动，直到第一个不大于基准点或者l=r
            while (temp <= arr[right] && left < right) {
                --right;
            }
            // 当基准数大于了 arr[right]，则填坑
            if (left < right) {
                arr[left] = arr[right];
                ++left;
            }
            // 判断基准数和前面的数依次比较，只要小于基准点不动，直到第一个不小于基准数或者l=r
            while (temp >= arr[left] && left < right) {
                ++left;
            }
            //  // 当基准数小于了 arr[left]，则填坑
            if (left < right) {
                arr[right] = arr[left];
                --right;
            }
        }
        arr[left] = temp;
        return left;
    }

    /**
     * 快速排序-固定基准数
     * 时间复杂度：Ο(nlogn)-O(n^2)
     * 快速排序的基本思想：
     * 1 找到该数组的基准点(中间数)
     * 2 遍历数组，拿出数组中的每个数和基准点进行比较，如果比基准点小就放到left数组中，如果比基准点大就放到right数组中；
     * 3 对数组left和right进行递归调用。
     *
     */
    private static void quickSort(int[] arr, int left, int right) {
        if (arr == null || left >= right || arr.length <= 1)
            return;
        int mid = partition(arr, left, right);
        quickSort(arr, left, mid);
        quickSort(arr, mid + 1, right);
    }

    public static void qSort(int[] src, int begin, int end) {
        if (begin < end) {
            int key = src[begin];
            int i = begin;
            int j = end;
            while (i < j) {
                while (i < j && src[j] > key) {
                    j--;
                }
                if (i < j) {
                    src[i] = src[j];
                    i++;
                }
                while (i < j && src[i] < key) {
                    i++;
                }
                if (i < j) {
                    src[j] = src[i];
                    j--;
                }
            }
            src[i] = key;
            qSort(src, begin, i - 1);
            qSort(src, i + 1, end);
        }
    }

    public static void main(String[] args) {
        int[] arr = {6, 4, 3, 2, 7, 9, 1, 8, 5};
        /**
         * 6 4 3 2 7 9 1 8 5  =>  1 4 3 2 7 9 5 8 6 =>
         * 1已排序 => 4 3 2 7 9 5 8 6 => 2 3 4 7 9 5 8 6
         * => 2 已排序 => 3 4 7 9 5 8 6 => 3 4 已排序 =>7 9 5 8 6=> 6 9 5 8 7
         * =>5 9 6 8 7=> 5已排序 => 9 6 8 7 => 7 6 8 9 => 6 7 8 9 =>done
         */
        qSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
