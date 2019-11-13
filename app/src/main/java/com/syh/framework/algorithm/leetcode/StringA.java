package com.syh.framework.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashSet;

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


    public static void main(String[] args) {
        System.out.println("A man, a plan, a c121canal: Panama isPalindrome " + isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println("race a car isPalindrome " + isPalindrome("race a car"));
        System.out.println("hello reverseVowels " + reverseVowels("hello"));
        System.out.println("leetcode reverseVowels " + reverseVowels("leetcode"));
        System.out.println("aA reverseVowels " + reverseVowels("aA"));
    }
}
