package com.syh.framework.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenyonghe on 2019-11-07.
 * 罗马数字转整数
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * I 1
 * V 5
 * X 10
 * L 50
 * C 100
 * D 500
 * M 1000
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900
 */
public class RomanToInt {

    public static Map<String, Integer> map = new HashMap<>();

    static {
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);
    }

    public static int romanToInt(String s) {
        int tmp = 0;
        for (int i = 0; i < s.length(); ) {
            if (i + 1 < s.length() && map.containsKey(s.substring(i, i + 2))) {
                tmp += map.get(s.substring(i, i + 2));
                i += 2;
            } else {
                tmp += map.get(s.substring(i, i + 1));
                i++;
            }
        }
        return tmp;
    }

    public static int romanToIntC(String s) {
        int sum = 0;
        int preNum = getValue(s.charAt(0));
        for(int i = 1;i < s.length(); i ++) {
            int num = getValue(s.charAt(i));
            if(preNum < num) {
                sum -= preNum;
            } else {
                sum += preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;
    }

    private static int getValue(char ch) {
        switch(ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }


    public static void main(String[] args) {
        System.out.println("MCMXCIV is => " + romanToInt("MCMXCIV"));
        System.out.println("III is => " + romanToInt("III"));
        System.out.println("LVIII is => " + romanToInt("LVIII"));
        System.out.println("IX is => " + romanToInt("IX"));
    }
}
