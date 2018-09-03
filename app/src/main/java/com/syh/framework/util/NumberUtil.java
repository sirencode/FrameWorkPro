package com.syh.framework.util;

import android.text.TextUtils;

public class NumberUtil {

    public static int getIntFromString(String value,int defaultValue){
        int result = defaultValue;
        if (!TextUtils.isEmpty(value)) {
            try {
                result = Integer.valueOf(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static float getFloateFromString(String value,float defaultValue){
        float result = defaultValue;
        if (!TextUtils.isEmpty(value)) {
            try {
                result = Integer.valueOf(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
