package com.syh.framework.algorithm.leetcode;

import java.util.Random;

/**
 * Created by shenyonghe on 2019-11-19.
 */
public class ShuffleArry {
    private int[] array;
    private int[] original;
    Random rand = new Random();

    public ShuffleArry(int[] nums) {
        array = nums;
        original = nums.clone();
    }

    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        array = original;
        original = original.clone();
        return original;
    }

    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        for (int i = 0;i<array.length;i++) {
            swap(i,rand.nextInt(array.length-i)+i,array);
        }
        return array;
    }
    public void swap(int i, int j, int[] arr) {
        if (arr == null || arr.length == 0 || i > arr.length - 1 || j > arr.length - 1) return;
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
