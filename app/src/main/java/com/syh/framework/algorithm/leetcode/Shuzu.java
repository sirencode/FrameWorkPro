package com.syh.framework.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * Created by shenyonghe on 2019-11-07.
 */
public class Shuzu {

    private static Random rand = new Random();

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

    public static int[] shuffle(int[] array) {
        for (int i = 0; i < array.length; i++) {
            swap(i, rand.nextInt(array.length - i) + i, array);
        }
        return array;
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

    /**
     * 给定两个数组，编写一个函数来计算它们的交集。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> list = new ArrayList<>();
        int p1 = 0, p2 = 0;
        while (p1 < nums1.length && p2 < nums2.length) {
            if (nums1[p1] < nums2[p2]) p1++;
            else if (nums1[p1] > nums2[p2]) p2++;
            else {
                list.add(nums1[p1]);
                p1++;
                p2++;
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++) res[i] = list.get(i);
        return res;
    }

    /**
     * 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
     * 1 数字 1-9 在每一行只能出现一次。
     * 2 数字 1-9 在每一列只能出现一次。
     * 3 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     *
     * @param board
     * @return
     */
    public static boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            // hori, veti, sqre分别表示行、列、小宫
            int hori = 0, veti = 0, sqre = 0;
            for (int j = 0; j < 9; j++) {
                // 由于传入为char，需要转换为int，减48
                int h = board[i][j] - 48;
                int v = board[j][i] - 48;
                int s = board[3 * (i / 3) + j / 3][3 * (i % 3) + j % 3] - 48;
                // "."的ASCII码为46，故小于0代表着当前符号位"."，不用讨论
                if (h > 0) {
                    hori = sodokuer(h, hori);
                }
                if (v > 0) {
                    veti = sodokuer(v, veti);
                }
                if (s > 0) {
                    sqre = sodokuer(s, sqre);
                }
                if (hori == -1 || veti == -1 || sqre == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidSudoku_(char[][] board) {
        int[] row = new int[9];
        int[] col = new int[9];
        int[] sub = new int[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //当前字符是'.'则跳过, 直接进入下一轮循环
                if (board[i][j] == '.')
                    continue;
                //处理行
                if (!valid(row, i, board[i][j] - 48))
                    return false;
                //处理列
                if (!valid(col, j, board[i][j] - 48))
                    return false;
                //处理子数独
                int index = i / 3 * 3 + j / 3; // 分成9块数据{{0,3,6},{1,4,7},{2,5,8}}
                if (!valid(sub, index, board[i][j] - 48))
                    return false;
            }
        }
        return true;
    }

    private static int sodokuer(int n, int val) {
        return ((val >> n) & 1) == 1 ? -1 : val ^ (1 << n);
    }

    public static boolean valid(int[] arr, int i, int cur) {
        //cur出现过, 返回false
        if (((arr[i] >> cur) & 1) == 1)
            return false;
        //cur没出现过, 标记为出现过对应位标识对应数字，8= *1*******
        arr[i] = arr[i] | (1 << cur);
        return true;
    }

    /**
     * 给定一个 n × n 的二维矩阵表示一个图像。将图像顺时针旋转 90 度。
     *
     * @param matrix
     */
    public static void rotate(int[][] matrix) {
        if (matrix.length == 0 || matrix.length != matrix[0].length) {
            return;
        }
        int nums = matrix.length;
        for (int i = 0; i < nums; i++) {
            for (int j = 0; j < nums - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[nums - 1 - j][nums - 1 - i];
                matrix[nums - 1 - j][nums - 1 - i] = temp;
            }
        }
        for (int i = 0; i < (nums >> 1); i++) {
            for (int j = 0; j < nums; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[nums - 1 - i][j];
                matrix[nums - 1 - i][j] = temp;
            }
        }
    }

    /**
     * 寻找数组的中心索引
     * 给定一个整数类型的数组 nums，请编写一个能够返回数组“中心索引”的方法。
     * 我们是这样定义数组中心索引的：数组中心索引的左侧所有元素相加的和等于右侧所有元素相加的和。
     *
     * @param nums
     * @return
     */
    public static int pivotIndex(int[] nums) {
        int leftsum = 0;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        for (int i = 0; i < nums.length; i++) {
            if (leftsum == sum - leftsum - nums[i]) return i;
            leftsum += nums[i];
        }
        return -1;
    }

    /**
     * 在一个给定的数组nums中，总是存在一个最大元素。
     * 查找数组中的最大元素是否至少是数组中每个其他数字的两倍。
     * 如果是，则返回最大元素的索引，否则返回-1。
     *
     * @param nums
     * @return
     */
    public static int dominantIndex(int[] nums) {
        int secondMax = 0;
        int max = nums[0];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max) {
                secondMax = max;
                max = nums[i];
                index = i;
            }
        }

        for (int i = index + 1; i < nums.length; i++) {
            if (nums[i] > secondMax) {
                secondMax = nums[i];
            }
        }
        return max >= 2 * secondMax ? index : -1;
    }

    /**
     * 对角线遍历
     * 给定一个含有 M x N 个元素的矩阵（M 行，N 列），请以对角线遍历的顺序返回这个矩阵中的所有元素。
     * 输入：
     * [
     * [ 1, 2, 3 ],
     * [ 4, 5, 6 ],
     * [ 7, 8, 9 ]
     * ]
     * <p>
     * 输出:  [1,2,4,7,5,3,6,8,9] - 00 01 10 20 11 02 12 21 22
     *
     * @param matrix
     * @return
     */
    public static int[] findDiagonalOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }
        int iNum = matrix.length;
        int jNum = matrix[0].length;
        int[] result = new int[iNum * jNum];
        boolean isUp = true;
        int resultIndex = 0;
        int iIndex = 0, jIndex = 0;
        while (iIndex < iNum && jIndex < jNum) { //边界控制
            if (isUp) {
                while (iIndex >= 0 && jIndex >= 0 && iIndex < iNum && jIndex < jNum) { //遍历上升的所有数
                    result[resultIndex++] = matrix[iIndex--][jIndex++];
                }
                iIndex++;
                jIndex--; //revert，上面退出循环后多改变了一次，要先还原到up的最后一个元素的index状态
                if (jIndex + 1 < jNum) { //先j+1；
                    jIndex++;
                } else { //j会超过边界，此时i+1;
                    iIndex++;
                }
                isUp = false;
            } else {
                while (iIndex >= 0 && jIndex >= 0 && iIndex < iNum && jIndex < jNum) {//遍历下降的所有数
                    result[resultIndex++] = matrix[iIndex++][jIndex--];
                }
                iIndex--;
                jIndex++;//revert，和up的解释相同
                if (iIndex + 1 < iNum) {//先i+1
                    iIndex++;
                } else {//i会超出边界，此时j+1
                    jIndex++;
                }
                isUp = true;
            }
        }
        return result;
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return list;
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;
        //统计矩阵从外向内的层数，如果矩阵非空，那么它的层数至少为1层
        int count = (Math.min(m, n) + 1) / 2;
        //从外部向内部遍历，逐层打印数据
        while (i < count) {
            for (int j = i; j < n - i; j++) {
                list.add(matrix[i][j]);
            }
            for (int j = i + 1; j < m - i; j++) {
                list.add(matrix[j][(n - 1) - i]);
            }

            for (int j = (n - 1) - (i + 1); j >= i && (m - 1 - i != i); j--) {
                list.add(matrix[(m - 1) - i][j]);
            }
            for (int j = (m - 1) - (i + 1); j >= i + 1 && (n - 1 - i) != i; j--) {
                list.add(matrix[j][i]);
            }
            i++;
        }
        return list;
    }

    /**
     * 数组拆分 I
     * 给定长度为 2n 的数组, 你的任务是将这些数分成 n 对, 例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从1 到 n 的 min(ai, bi) 总和最大。
     *
     * @param nums
     * @return
     */
    public static int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length; i += 2) {
            sum += nums[i];
        }
        return sum;
    }

    /**
     * 最大连续1的个数
     * 给定一个二进制数组， 计算其中最大连续1的个数。
     *
     * @param nums
     * @return
     */
    public static int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        int currentmax = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (currentmax == -1) {
                    max++;
                } else {
                    currentmax++;
                }
            } else {
                max = Math.max(max, currentmax);
                currentmax = 0;
            }
        }
        max = Math.max(max, currentmax);
        return max;
    }

    public static void main(String[] args) {
//        int[] nums = new int[]{0, 0, 1};
//        moveZeroes(nums);
//        int[] remove = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
//        removeElement(remove, 2);
//        System.out.println(Arrays.toString(remove));
//
//        int[] removeDup = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
//        System.out.println(removeDuplicatesTwo(removeDup));
//        System.out.println(Arrays.toString(removeDup));
//
//        int[] color = new int[]{2, 0, 2, 1, 1, 0};
//        sortColors(color);
//        System.out.println(Arrays.toString(color));
//        int[] findk = new int[]{3, 2, 1, 5, 6, 4};
//        System.out.println(findKthLargest(findk, 2));
//        System.out.println(Arrays.toString(findk));
//        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
//        System.out.println(maxArea(height));
//        int[] minsub = new int[]{2, 3, 1, 2, 4, 3};
//        System.out.println(minSubArrayLen(7, minsub));
//
//        int[] prices = new int[]{2, 1, 2, 0, 2};
//        System.out.println(maxProfit(prices));
//
//        int[] rotates = new int[]{1, 2, 3, 4, 5, 6, 7};
//        rotateG(rotates, 3);
//        System.out.println(Arrays.toString(rotates));
//
//        int[] repet = new int[]{1, 2, 3, 1};
//        System.out.println(containsDuplicate(repet));
//
//        int[] single = new int[]{4, 1, 2, 1, 2};
//        System.out.println(singleNumber(single));
//
//        int[] plusOne = new int[]{9, 9, 9, 9};
//        System.out.println(Arrays.toString(plusOne(plusOne)));
//
        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        rotate(matrix);
//        for (int i = 0; i < matrix.length; i++) {
//            System.out.println(Arrays.toString(matrix[i]));
//        }
//        System.out.println(Arrays.toString(shuffle(single)));
//        int[] pov1 = new int[]{1, 7, 3, 6, 5, 6};
//        int[] pov2 = new int[]{1, 2, 3};
//        System.out.println(pivotIndex(pov1));
//        System.out.println(pivotIndex(pov2));
        System.out.println(Arrays.toString(findDiagonalOrder(matrix)));
        MathA.soutList(spiralOrder(matrix));
        int[] maxlx = new int[]{1, 1, 1, 1, 0, 1};
        System.out.println(findMaxConsecutiveOnes(maxlx));
    }
}
