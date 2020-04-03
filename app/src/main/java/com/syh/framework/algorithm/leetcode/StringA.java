package com.syh.framework.algorithm.leetcode;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shenyonghe on 2019-11-11.
 */
public class StringA {

    private final static HashSet<Character> set = new HashSet<>(Arrays.asList('a', 'o', 'e', 'i', 'u', 'A', 'O', 'E', 'I', 'U'));

    /**
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * 双指针字符校验，过滤掉不符合条件的字符
     *
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;
        while (end > start) {
            while (start < end && !Character.isLetterOrDigit(s.charAt(start))) start++;
            while (start < end && !Character.isLetterOrDigit(s.charAt(end))) end--;
            if (Character.toLowerCase(s.charAt(start)) != Character.toLowerCase(s.charAt(end)))
                return false;
            start++;
            end--;
        }
        return true;
    }

    /**
     * 编写一个函数，以字符串作为输入，反转该字符串中的元音字母。
     * 对撞指针：第一个指针记录从左到右的元素，另外一个从又到左记录，不是元音就跳过，如果两个都是就交换位置
     *
     * @param s
     * @return
     */
    public static String reverseVowels(String s) {
        int head = 0;
        int tail = s.length() - 1;
        char[] chars = s.toCharArray();
        while (head < tail) {
            char headY = chars[head];
            char tailY = chars[tail];
            if (!isYuanyin(headY)) head++;
            if (!isYuanyin(tailY)) tail--;
            if (isYuanyin(tailY) && isYuanyin(headY)) {
                chars[head] = s.charAt(tail);
                chars[tail] = s.charAt(head);
                head++;
                tail--;
            }
        }
        return new String(chars);
    }

    public static boolean isYuanyin(char a) {
        return set.contains(a);
    }

    /**
     * 其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
     *
     * @param s
     */
    public static void reverseString(char[] s) {
        if (s == null || s.length <= 1) return;
        int head = 0;
        int tail = s.length - 1;
        while (head < tail) {
            char tmp = s[head];
            s[head] = s[tail];
            s[tail] = tmp;
            head++;
            tail--;
        }
    }

    public static int reverse(int x) {
        int ans = 0;
        while (x != 0) {
            int pop = x % 10;
            //判断溢出
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7))
                return 0;
            if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8))
                return 0;
            ans = ans * 10 + pop;
            x /= 10;
        }
        return ans;
    }

    /**
     * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
     *
     * @param s
     * @return
     */
    public static int firstUniqChar(String s) {
        if (s.length() == 1) return 0;
        HashMap<Character, Integer> count = new HashMap<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            count.put(c, count.get(c) == null ? 1 : count.get(c) + 1);
        }

        for (int i = 0; i < n; i++) {
            if (count.get(s.charAt(i)) == 1)
                return i;
        }
        return -1;
    }

    /**
     * 两个单词如果包含相同的字母，次序不同，则称为字母易位词
     * 你可以假设字符串只包含小写字母。
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] table = new int[26];
        for (int i = 0; i < s.length(); i++) {
            table[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            table[t.charAt(i) - 'a']--;
            if (table[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
     * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
     * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；
     * 假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
     * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
     *
     * @param str
     * @return
     */
    public static int myAtoi(String str) {
        str = str.trim();
        if (str == null || str.length() == 0) return 0;
        char firstChar = str.charAt(0);
        int sign = 1;
        int start = 0;
        long res = 0;
        if (firstChar == '+') {
            sign = 1;
            start++;
        } else if (firstChar == '-') {
            sign = -1;
            start++;
        }
        for (int i = start; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return (int) res * sign;
            }
            res = res * 10 + str.charAt(i) - '0';
            if (sign == 1 && res > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            if (sign == -1 && res > Integer.MAX_VALUE)
                return Integer.MIN_VALUE;
        }
        return (int) res * sign;
    }

    /**
     * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {
        if (!haystack.contains(needle)) return -1;
        if (haystack.equals(needle)) return 0;
        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 报数序列是一个整数序列，按照其中的整数的顺序进行报数.
     * 1 被读作 “one 1” (“一个一”) , 即 11。
     * 11 被读作 “two 1s” (“两个一”）, 即 21。
     * 21 被读作 “one 2”, “one 1” （“一个二” , “一个一”) , 即 1211。
     *
     * @param n 给定一个正整数 n（1 ≤ n ≤ 30），输出报数序列的第 n 项。
     * @return
     */
    public static String countAndSay(int n) {
        StringBuilder ans = new StringBuilder();
        ans.append("1");
        for (int i = 2; i <= n; i++) {
            //遍历前一个字符串
            String currentStr = new String(ans);
            ans.delete(0, ans.length());
            int num = 0;
            char currentChar = currentStr.charAt(0);
            for (char c : currentStr.toCharArray()) {
                if (c == currentChar) {
                    num++;
                } else {
                    ans.append((char) ('0' + num));
                    ans.append(currentChar);
                    currentChar = c;
                    num = 1;
                }
            }
            ans.append((char) ('0' + num));
            ans.append(currentChar);
        }
        return ans.toString();
    }

    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String result = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(result) != 0) {
                result = result.substring(0, result.length() - 1);
                if (result.isEmpty()) return "";
            }
        }
        return result;
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public static String addBinary(String a, String b) {
        StringBuilder ans = new StringBuilder();
        int ca = 0;
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int sum = ca;
            sum += i >= 0 ? a.charAt(i) - '0' : 0;
            sum += j >= 0 ? b.charAt(j) - '0' : 0;
            ans.append(sum % 2);
            ca = sum / 2;
        }
        ans.append(ca == 1 ? ca : "");
        return ans.reverse().toString();
    }

    /**
     * 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int len = s.length();
        if (s == null || len == 0) return 0;
        Set<Character> set = new HashSet<>();
        int max = 0, i = 0, j = 0;
        while (i < len && j < len) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                max = Math.max(max, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return max;
    }



    public static String preProcess(String s) {
        int n = s.length();
        if (n == 0) {
            return "^$";
        }
        String ret = "^";
        for (int i = 0; i < n; i++)
            ret += "#" + s.charAt(i);
        ret += "#$";
        return ret;
    }

    public static void main(String[] args) {
        System.out.println("A man, a plan, a c121canal: Panama isPalindrome " + isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println("race a car isPalindrome " + isPalindrome("race a car"));
        System.out.println("hello reverseVowels " + reverseVowels("hello"));
        System.out.println("leetcode reverseVowels " + reverseVowels("leetcode"));
        System.out.println("aA reverseVowels " + reverseVowels("aA"));
        char[] reverseString = new char[]{'H', 'a', 'n', 'n', 'a', 'h'};
        reverseString(reverseString);
        System.out.println(new String(reverseString));
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(-120));
        System.out.println(reverse(1534236469));
        firstUniqChar("leetcode");
        System.out.println(isAnagram("anagram", "nagaram"));
        System.out.println(isAnagram("rat", "car"));
        System.out.println(myAtoi("42"));
        System.out.println(myAtoi("    -42"));
        System.out.println(myAtoi("4193 with words"));
        System.out.println(myAtoi("-91283472332"));
        System.out.println(strStr("", ""));
        System.out.println(countAndSay(1));
        System.out.println(countAndSay(2));
        System.out.println(countAndSay(3));
        System.out.println(countAndSay(4));
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        System.out.println(longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        System.out.println(addBinary("1", "11"));
        System.out.println(addBinary("1010", "1011"));
    }
}
