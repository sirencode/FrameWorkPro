package com.syh.framework.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenyonghe on 2019-11-07.
 * 题目描述：
 * 给定一个整数数组nums和一个目标值target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 */
public class TwoSum {
    /**
     * 一遍哈希表方式
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * 插入的同时进行匹配，可以确保到最后的时候最后一个值和之前的所有值都对比一次
     *
     * @param nums   给定数组
     * @param target 和
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * 两遍哈希表方式
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     *
     * @param nums   给定数组
     * @param target 和
     * @return
     */
    public static int[] twoSumTwo(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[]{i, map.get(complement)};
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * 两次迭代的方式
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSumBad(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 15, 11, 7};
        try {
            System.out.println(Arrays.toString(twoSumBad(nums, 9)));
            System.out.println(Arrays.toString(twoSumTwo(nums, 9)));
            System.out.println(Arrays.toString(twoSum(nums, 9)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
