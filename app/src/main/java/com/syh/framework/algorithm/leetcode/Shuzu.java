package com.syh.framework.algorithm.leetcode;

import com.syh.framework.algorithm.sort.SortUtil;

import java.util.Arrays;

/**
 * Created by shenyonghe on 2019-11-07.
 */
public class Shuzu {

    /**
     * 题目描述：
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 思路：
     * 1.nums中，i指针用于存放非零元素
     * 2.j指针用于遍历寻找非零元素
     * 3.j指针遍历完后，最后nums数组还有空位置，存放0即可
     *
     * @param nums
     */
    public static void moveZeroes(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                if (i != j) {
                    nums[i] = nums[j];
                }
                i++;
            }
        }
        for (; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    /**
     * 给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * 时间复杂度：O(n)，空间复杂度：O(1)
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[result] = nums[i];
                result++;
            }
        }
        return result;
    }

    /**
     * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int p = 0;
        int q = 1;
        while (q < nums.length) {
            if (nums[p] != nums[q]) {
                nums[p + 1] = nums[q];
                p++;
            }
            q++;
        }
        return p + 1;
    }

    public static int removeDuplicatesTwo(int[] nums) {
        int m = 0;
        for (int i = 0; i < nums.length; ) {
            if (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                int val = nums[i];
                nums[m++] = nums[i++];
                nums[m++] = nums[i++];
                while (i < nums.length && nums[i] == val)
                    i++;
            } else {
                nums[m++] = nums[i++];
            }
        }
        return m;
    }

    /**
     * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * 三指针
     *
     * @param nums
     */
    public static void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int len = nums.length;
        int r = 0, curr = 0, b = len - 1;
        int tmp;
        while (curr <= b) {
            if (nums[curr] == 0) {
                swap(r, curr, nums);
                r++;
                curr++;
            } else if (nums[curr] == 2) {
                swap(curr, b, nums);
                b--;
            } else {
                curr++;
            }
        }
    }

    public static void swap(int i, int j, int[] arr) {
        if (arr == null || arr.length == 0 || i > arr.length - 1 || j > arr.length - 1) return;
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     *
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest(int[] nums, int k) {
        int preIndex, current;
        for (int i = 1; i < nums.length; i++) {
            preIndex = i - 1;
            current = nums[i];
            while (preIndex >= 0 && nums[preIndex] > current) {
                nums[preIndex + 1] = nums[preIndex];
                preIndex--;
            }
            nums[preIndex + 1] = current;
        }
        return nums[nums.length - k];
    }

    /**
     * 给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int len1 = m - 1;
        int len2 = n - 1;
        int len = m + n - 1;
        while (len1 >= 0 && len2 >= 0) {
            // 注意--符号在后面，表示先进行计算再减1，这种缩写缩短了代码
            nums1[len--] = nums1[len1] > nums2[len2] ? nums1[len1--] : nums2[len2--];
        }
        // 表示将nums2数组从下标0位置开始，拷贝到nums1数组中，从下标0位置开始，长度为len2+1
        System.arraycopy(nums2, 0, nums1, 0, len2 + 1);
    }

    /**
     * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * 说明：你不能倾斜容器，且 n 的值至少为 2。
     * 计算：下标差 * min(value) 的值
     *
     * @param height
     * @return
     */
    public static int maxAreabad(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int tmp = (j - i) * (Math.min(height[i], height[j]));
                max = max > tmp ? max : tmp;
            }
        }
        return max;
    }

    /**
     * 双指针实现：将两端较长的向内侧移动，移动较短线段的指针会得到一条相对较长的线段，这可以克服由宽度减小而引起的面积减小。
     * H[i]与H[j]中至少有一个是在(0,i](0,i]和[j,n-1)[j,n−1)中，H最大的。
     *
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
        int maxarea = 0, l = 0, r = height.length - 1;
        while (l < r) {
            maxarea = Math.max(maxarea, Math.min(height[l], height[r]) * (r - l));
            if (height[l] < height[r])
                l++;
            else
                r--;
        }
        return maxarea;
    }

    /**
     * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
     * 思路：要求是连续子数组，所以我们必须定义 i，j两个指针，i 向前遍历，j 向后遍历，相当与一个滑块，
     * 这样所有的子数组都会在 [i...j] 中出现，如果 nums[i..j] 的和小于目标值 s，那么j向后移一位，再次比较，
     * 直到大于目标值 s 之后，i 向前移动一位，缩小数组的长度。遍历到i到数组的最末端，就算结束了，如果不存在符合条件的就返回 0。
     *
     * @param s
     * @param nums
     * @return
     */
    public static int minSubArrayLen(int s, int[] nums) {
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        int left = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            while (sum >= s) {
                ans = Math.min(ans, i + 1 - left);
                sum -= nums[left++];
            }
        }
        return (ans != Integer.MAX_VALUE) ? ans : 0;
    }

    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
        }
        return profit;
    }

    /**
     * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     *
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    // 反转思想
    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(start++, end--, nums);
        }
    }

    /**
     * 环状替换
     *
     * @param nums
     * @param k
     */
    public static void rotateG(int[] nums, int k) {
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }

    /**
     * 给定一个整数数组，判断是否存在重复元素。
     *
     * @param nums
     * @return
     */
    public static boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; ++i) {
            if (nums[i] == nums[i + 1]) return true;
        }
        return false;
    }

    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            result = result ^ nums[i];
        }
        return result;
    }

    /**
     * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     *
     * @param digits
     * @return
     */
    public static int[] plusOne(int[] digits) {
        int carry = 1;
        int len = digits.length - 1;
        while (carry > 0 && len >= 0) {
            int tmp = digits[len] + carry;
            digits[len] = tmp % 10;
            carry = tmp / 10;
            len--;
        }
        // 当前情况只有第一位是1，其它位都是0，没必要拷贝数组
        if (carry > 0) {
            digits = new int[digits.length + 1];
            digits[0] = 1;
        }
        return digits;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0, 0, 1};
        moveZeroes(nums);
        int[] remove = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        removeElement(remove, 2);
        System.out.println(Arrays.toString(remove));

        int[] removeDup = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(removeDuplicatesTwo(removeDup));
        System.out.println(Arrays.toString(removeDup));

        int[] color = new int[]{2, 0, 2, 1, 1, 0};
        sortColors(color);
        System.out.println(Arrays.toString(color));
        int[] findk = new int[]{3, 2, 1, 5, 6, 4};
        System.out.println(findKthLargest(findk, 2));
        System.out.println(Arrays.toString(findk));
        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxArea(height));
        int[] minsub = new int[]{2, 3, 1, 2, 4, 3};
        System.out.println(minSubArrayLen(7, minsub));

        int[] prices = new int[]{2, 1, 2, 0, 2};
        System.out.println(maxProfit(prices));

        int[] rotates = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotateG(rotates, 3);
        System.out.println(Arrays.toString(rotates));

        int[] repet = new int[]{1, 2, 3, 1};
        System.out.println(containsDuplicate(repet));

        int[] single = new int[]{4, 1, 2, 1, 2};
        System.out.println(singleNumber(single));

        int[] plusOne = new int[]{9, 9, 9, 9};
        System.out.println(Arrays.toString(plusOne(plusOne)));
    }
}
