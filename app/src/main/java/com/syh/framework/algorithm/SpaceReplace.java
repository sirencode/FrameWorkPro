package com.syh.framework.algorithm;

import android.text.TextUtils;

/**
 * Created by shenyonghe on 2020/5/19.
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 */
public class SpaceReplace {

    public static String replaceSpace(String str) {
        if (str == null) return "";
        return str.replace(" ", "%20");
    }

    public static String replaceSpaceByArray(String str) {
        if (str == null) return "";
        StringBuilder builder =  new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            builder.append(c == ' ' ? "%20" : c);
        }
        return builder.toString();
    }
}
