package com.syh.framework.util;

public class StringUtil {
    public static boolean isChineseCharacter(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isChineseWord(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    public static int getCharLength(char c) {
        return isChineseCharacter(c) || isChineseWord(c) ? 2 : 1;
    }

    public static int getStringLength(String s) {
        int length = 0;
        if (s != null && !s.equals("")) {
            char[] chars = s.toCharArray();
            for (char c : chars) {
                length = length + getCharLength(c);
            }
        }
        return length;
    }

    public static String getLengthSub(String text, int length) {
        String tmp = text;
        if (text != null && !text.equals("") && length > 0) {
            int current = 0;
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (current < length) {
                    current = current + StringUtil.getCharLength(chars[i]);
                } else {
                    tmp = text.substring(0, current == length ? i : i - 1) + "...";
                    break;
                }
            }
        }
        return tmp;
    }

}
