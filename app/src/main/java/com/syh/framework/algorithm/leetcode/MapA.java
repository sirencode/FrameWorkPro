package com.syh.framework.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shenyonghe on 2019-12-07.
 */
public class MapA {

    /**
     * 两个数组的交集
     * 给定两个数组，编写一个函数来计算它们的交集。
     * 说明：
     * 输出结果中的每个元素一定是唯一的。
     * 我们可以不考虑输出结果的顺序。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set1 = new HashSet<>();
        for (Integer n : nums1) set1.add(n);
        HashSet<Integer> set2 = new HashSet<>();
        for (Integer n : nums2) set2.add(n);
        set1.retainAll(set2);
        int[] output = new int[set1.size()];
        int idx = 0;
        for (int s : set1) output[idx++] = s;
        return output;
    }

    /**
     * 快乐数
     * 快乐数有以下的特性：在给定的进位制下，该数字所有数字（英语：digits）的平方和，得到的新数再次求所有数字的平方和，如此重复进行，最终结果必为1。
     * 28 → 2^2+8^2=68 → 6^2+8^2=100 → 1^2+0^2+0^2=1
     *
     * @param n
     * @return
     */
    public static boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        int m = 0;
        while (true) {
            while (n != 0) {
                m += Math.pow(n % 10, 2);
                n /= 10;
            }
            if (m == 1) {
                return true;
            }

            if (set.contains(m)) {
                return false;
            } else {
                set.add(m);
                n = m;
                m = 0;
            }
        }
    }

    /**
     * 同构字符串
     * 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) return false;
        Map<Character, Character> map = new HashMap<>();
        Map<Character, Character> map1 = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) != null && map.get(s.charAt(i)) != t.charAt(i)) {
                return false;
            }
            if (map1.get(t.charAt(i)) != null && map1.get(t.charAt(i)) != s.charAt(i)) {
                return false;
            }
            map1.put(t.charAt(i), s.charAt(i));
            map.put(s.charAt(i), t.charAt(i));
        }
        return true;
    }

    /**
     * 两个列表的最小索引总和
     * 输入:
     * ["Shogun", "Tapioca Express", "Burger King", "KFC"]
     * ["KFC", "Shogun", "Burger King"]
     * 输出: ["Shogun"]
     * 解释: 他们共同喜爱且具有最小索引和的餐厅是“Shogun”，它有最小的索引和1(0+1)。
     * 提示
     * 1 两个列表的长度范围都在 [1, 1000]内。
     * 2 两个列表中的字符串的长度将在[1，30]的范围内。
     * 3 下标从0开始，到列表的长度减1。
     * 4 两个列表都没有重复的元素。
     *
     * @param list1
     * @param list2
     * @return
     */
    public static String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list1.length; i++) map.put(list1[i], i);
        List<String> result = new ArrayList<>();
        int sum, minSum = Integer.MAX_VALUE;
        for (int i = 0; i < list2.length && i <= minSum; i++) {
            if (map.containsKey(list2[i])) {
                sum = i + map.get(list2[i]);
                if (sum < minSum) {
                    result.clear();
                    result.add(list2[i]);
                    minSum = sum;
                } else if (sum == minSum) {
                    result.add(list2[i]);
                }
            }
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * 存在重复元素 II
     * 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，并且 i 和 j 的差的绝对值最大为 k。
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

    /**
     * 字母异位词分组
     * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
     * 说明：
     * 1 所有输入均为小写字母。
     * 2 不考虑答案输出的顺序。
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> hash = new HashMap<>();
        for (String str : strs) {
            int[] num = new int[26];
            for (int i = 0; i < str.length(); i++) {
                num[str.charAt(i) - 'a']++;
            }
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < num.length; i++) {
                buffer.append(num[i]);
            }
            String key = buffer.toString();
            if (hash.containsKey(key)) {
                hash.get(key).add(str);
            } else {
                List<String> list = new ArrayList<>();
                list.add(str);
                hash.put(key,list);
            }
        }
        System.out.println(hash);
        return new ArrayList<>(hash.values());
    }

    public static boolean isIsomorphic_(String s, String t) {
        char[] ch1 = s.toCharArray();
        char[] ch2 = t.toCharArray();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.indexOf(ch1[i]) != t.indexOf(ch2[i])) {
                return false;
            }
        }
        return true;
    }
}
