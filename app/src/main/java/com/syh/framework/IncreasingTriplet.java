package com.syh.framework;

/**
 * Created by shenyonghe on 2020/6/9.
 * 给定一个未排序的数组，判断这个数组中是否存在长度为 3 的递增子序列。
 * 如果存在这样的 i, j, k,  且满足 0 ≤ i < j < k ≤ n-1，
 * 使得 arr[i] < arr[j] < arr[k] ，返回 true ; 否则返回 false 。
 * 要求算法的时间复杂度为 O(n)，空间复杂度为 O(1) 。
 */
public class IncreasingTriplet {
    public static boolean increasingTriplet(int[] nums) {
        if (nums.length < 3) return false;
        int min = Integer.MAX_VALUE; //最小值
        int second = Integer.MAX_VALUE; //第二元素最小的递增对的第二元素
        for (int num : nums) {
            if (num <= min) { //num比min小或相等，肯定不存在递增三元素。刷新的时候由不可能存在以num结尾的递增对，因此，只需要刷新min
                min = num;
            } else if (num <= second) { //num比second小或相对，肯定不存在递增三元素。由于存在以num结尾的递增对且num不大于second，因此可以更新second
                second = num;
            } else { //num比second大，那就存在递增三元素，因为second是一个递增对的第二元素，加上num后就是递增三元素了
                return true;
            }
        }
        return false;
    }
}
