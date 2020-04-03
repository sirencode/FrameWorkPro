package com.syh.framework.algorithm.leetcode;

/**
 * Created by shenyonghe on 2020-01-15.
 * 最长回文子串
 * 所谓回文串，简单来说就是正着读和反着读都是一样的字符串，比如abba，noon等等
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 */
public class LongestPalindrome {
    /**
     * 马拉车算法-manacher：
     * 预处理：bob => #b#0#b# (这样做的好处是不论原字符串是奇数还是偶数个，处理之后得到的字符串的个数都是奇数个，这样就不用分情况讨论了，而可以一起搞定。)
     * r[i]记录i点为中心点的回文半径
     * mx是回文串能延伸到的最右端的位置
     * id为最大回文子串中心的位置
     * r[i] = mx > i ? Math.min(r[2 * id - i], mx - i) : 1;
     *
     * str = abaaba
     * index : 0 1 2 3 4 5 6 7 8 9 10 11 12
     * T     : # a # b # a # a # b #  a  #
     * r[]   : 1 2 1 4 1 2 1 2 1 4 1  2  1
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if (s == null || s.length() <= 1)
            return s;
        StringBuilder sb = new StringBuilder("$#");
        int i;
        for (i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            sb.append("#");
        }
        String str = sb.toString();
        int[] r = new int[str.length()];
        int mx = 0, id = 0, ansR = 0, ansCenter = 0;
        for (i = 1; i < str.length(); i++) {
            r[i] = mx - i > r[i] ? Math.min(r[2 * id - i], mx - i) : 1;
            while (((i - r[i]) >= 0) && ((i + r[i]) < str.length())
                    && str.charAt(i - r[i]) == str.charAt(i + r[i]))
                r[i]++;
            if (i + r[i] > mx) {
                mx = i + r[i];
                id = i;
            }
            if (ansR < r[i]) {
                ansR = r[i];
                ansCenter = i;
            }
        }
        int maxStart = (ansCenter - ansR + 1) / 2;
        if (maxStart >= 0)
            return s.substring(maxStart, maxStart + ansR - 1);
        return "";
    }

    public static void main(String[] args) {
        System.out.printf(longestPalindrome("asdsd"));
    }
}
