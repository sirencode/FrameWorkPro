package com.syh.framework.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by shenyonghe on 2019-11-19.
 */
public class MathA {

    private static Map<Character, Integer> map = new HashMap<>();
    private static Map<Character, Character> khMap = new HashMap<>();

    static {
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        khMap.put(')', '(');
        khMap.put(']', '[');
        khMap.put('}', '{');
    }

    /**
     * 素数的倍数都不是素数
     *
     * @param n
     * @return
     */
    public static boolean[] sieveOfEratosthenes(int n) {
        boolean[] isPrime = new boolean[n + 1];
        //初始化，默认所有都是质数
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }
        //筛选，将所有质数的倍数都标记为非质数(最初只知道2是质数)
        for (int i = 2; i <= n / i; i++) {
            if (isPrime[i]) {
                for (int j = 2; j <= n / i; j++) {
                    isPrime[i * j] = false;
                }
            }
        }
        return isPrime;
    }

    /**
     * 统计所有小于非负整数 n 的质数的数量。
     *
     * @param n
     * @return
     */
    public static int countPrimes(int n) {
        boolean[] isPrim = new boolean[n];
        Arrays.fill(isPrim, true);
        for (int i = 2; i * i < n; i++) {
            if (isPrim[i]) {
                for (int j = i * i; j < n; j += i) isPrim[j] = false;
            }
        }
        int count = 0;
        for (int i = 2; i < n; i++)
            if (isPrim[i]) count++;
        return count;
    }

    /**
     * 给定一个整数，写一个函数来判断它是否是 3 的幂次方。
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfThree(int n) {
        if (n < 1) return false;
        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }

    public static int romanToInt(String s) {
        if (s == null || s.equals("")) return 0;
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if ((i + 1 < s.length()) && (map.get(cur) < map.get(s.charAt(i + 1)))) {
                char pre = s.charAt(i + 1);
                result += map.get(pre);
                if (pre == 'M' || pre == 'D') {
                    result -= 100;
                }
                if (pre == 'L' || pre == 'C') {
                    result -= 10;
                }
                if (pre == 'V' || pre == 'X') {
                    result -= 1;
                }
                i++;
            } else {
                result += map.get(s.charAt(i));
            }
        }
        return result;
    }

    /**
     * 编写一个函数，输入是一个无符号整数，返回其二进制表达式中数字位数为 ‘1’ 的个数（也被称为汉明重量）。
     * 注意符号位 0 负数，1 正数
     * 无符号整数：最左边这一位不用来表示正负，只能是正数。
     *
     * @param n
     * @return
     */
    public static int hammingWeight(int n) {
        int count = 0;
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) count++;
            mask = mask << 1;
        }
        return count;
    }

    /**
     * 两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。
     * 给出两个整数 x 和 y，计算它们之间的汉明距离。0 ≤ x, y < 2^31.
     *
     * @param x
     * @param y
     * @return
     */
    public static int hammingDistance(int x, int y) {
        int diff = 0;
        int mask = 1;
        int m = x ^ y;
        for (int i = 1; i < 32; i++) {
            if ((m & mask) != 0) diff++;
            mask <<= 1;
        }
        return diff;
    }

    /**
     * 颠倒给定的 32 位无符号整数的二进制位。
     *
     * @param n
     * @return
     */
    public static int reverseBits(int n) {
        int revNum = 0;
        int mask = 1;
        for (int i = 1; i <= 32; i++) {
            if ((n & mask) != 0) {
                revNum |= 1 << (32 - i);
            }
            mask <<= 1;
        }
        return revNum;
    }

    /**
     * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
     * 在杨辉三角中，每个数是它左上方和右上方的数的和。
     *
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> lists = new ArrayList<>();
        if (numRows <= 0) return lists;
        List<Integer> first = new ArrayList<>();
        first.add(1);
        lists.add(first);
        for (int i = 1; i < numRows; i++) {
            List<Integer> curr = new ArrayList<>();
            List<Integer> pre = lists.get(lists.size() - 1);
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    curr.add(1);
                } else {
                    curr.add(pre.get(j - 1) + pre.get(j));
                }
            }
            soutList(curr);
            lists.add(curr);
        }
        return lists;
    }

    /**
     * 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     * 左括号必须用相同类型的右括号闭合。左括号必须以正确的顺序闭合。
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        if (s == null || s.length() == 0) return true;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (khMap.containsKey(s.charAt(i))) {
                if (stack.isEmpty() || khMap.get(s.charAt(i)) != stack.pop()) return false;
            } else {
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }

    /**
     * 杨辉三角 II
     * 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
     *
     * @param rowIndex
     * @return
     */
    public static List<Integer> getRow(int rowIndex) {
        List<Integer> pre = new ArrayList<>();
        pre.add(1);
        if (rowIndex <= 0) return pre;
        List<Integer> curr = new ArrayList<>();
        for (int i = 1; i <= rowIndex; i++) {
            curr = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    curr.add(1);
                } else {
                    curr.add(pre.get(j - 1) + pre.get(j));
                }
            }
            pre = curr;
        }
        return curr;
    }

    public static void soutList(List<Integer> list) {
        if (list == null || list.size() == 0) return;
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(list.get(i));
        }
        System.out.println(stringBuffer.toString());
    }

    /**
     * 缺失数字
     * 给定一个包含 0, 1, 2, ..., n 中 n 个数的序列，找出 0 .. n 中没有出现在序列中的那个数。
     *
     * @param nums
     * @return
     */
    public static int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        double sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        double realSum = (nums.length + 1) * (nums.length) / 2;
        return (int) (realSum - sum);
    }

    /**
     * 翻转字符串里的单词
     * 给定一个字符串，逐个翻转字符串中的每个单词。
     *
     * @param s
     * @return
     */
    public static String reverseWords(String s) {
        String emptyStr = " ";
        final String[] s1 = s.trim().split(emptyStr);
        StringBuilder sb = new StringBuilder();
        for (int i = s1.length - 1; i >= 0; i--) {
            String str = s1[i];
            while (str.contains(emptyStr)) {
                str = str.replaceAll(emptyStr, "");
            }
            if ("".equals(str)) {
                continue;
            }
            sb.append(str);
            if (i != 0) {
                sb.append(emptyStr);
            }
        }
        return sb.toString();
    }

    /**
     * 反转字符串中的单词 III
     * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     *
     * @param s
     * @return
     */
    public static String reverseWordsIII(String s) {
        String[] strings = s.split(" ");
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < strings.length; i++) {
            char[] chars = strings[i].toCharArray();
            int start = 0;
            int tail = chars.length - 1;
            while (start < tail) {
                char tmp = chars[start];
                chars[start] = chars[tail];
                chars[tail] = tmp;
                start++;
                tail--;
            }
            stringBuilder.append(chars);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().trim();
    }

    /**
     * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
     * 说明
     * 1 -100.0 < x < 100.0
     * 2 n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1] 。
     *
     * @param x
     * @param n
     * @return
     */
    public static double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        return fastPow(x, N);
    }

    private static double fastPow(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        double half = fastPow(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }

    /**
     * 第K个语法符号
     * 在第一行我们写上一个0。接下来的每一行，将前一行中的0替换为01，1替换为10。
     * 给定行数 N 和序数 K，返回第 N 行中第 K个字符。（K从1开始）
     *
     * @param N
     * @param K 第一行: 0
     *          第二行: 01
     *          第三行: 0110
     *          第四行: 01101001
     *          注意
     *          1 N 的范围 [1, 30].
     *          2 K 的范围 [1, 2^(N-1)].
     * @return
     */
    public static int kthGrammar(int N, int K) {
        return Integer.bitCount(K - 1) % 2;
    }

    public static void main(String[] args) {
//        System.out.println(countPrimes(10));
//        System.out.println(isPowerOfThree(10));
//        System.out.println(isPowerOfThree(1));
//        System.out.println(romanToInt("III"));
//        System.out.println(romanToInt("IV"));
//        System.out.println(romanToInt("IX"));
//        System.out.println(hammingWeight(Integer.MIN_VALUE));
//        System.out.println(Integer.MIN_VALUE);
//        System.out.println(hammingDistance(93, 73));
//        System.out.println(reverseBits(43261596));
//        generate(5);
//        System.out.println(isValid("()"));
//        int[] miss1 = new int[]{3, 0, 1};
//        int[] miss3 = new int[]{0};
//        int[] miss2 = new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1};
//        System.out.println(missingNumber(miss1));
//        System.out.println(missingNumber(miss2));
//        System.out.println(missingNumber(miss3));
//        soutList(getRow(3));
//        System.out.println(reverseWords("the sky is blue"));
//        System.out.println(reverseWords("  hello world!  "));
//        System.out.println(reverseWords(" a good   example"));
//        System.out.println(myPow(2.0, 10));
//        System.out.println(myPow(2.0, -3));
//        System.out.println(myPow(2.0, 0));
//        System.out.println(myPow(0, 0));
//        System.out.println(myPow(0, 2));
        System.out.println(kthGrammar(1, 1));
        System.out.println(kthGrammar(2, 1));
    }
}
