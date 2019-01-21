package com.syh.framework.util;

import android.text.TextUtils;

import com.syh.framework.annotions.ChekValue;

import java.lang.reflect.Field;

public class DataCheckUtil {

    public static void checkValue(Object o) {
        Class<?> oClass = o.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        String chekMessage = "";
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(ChekValue.class)) {
                declaredField.setAccessible(true);
                Class<?> paramClass = declaredField.getType();
                Object value = null;
                try {
                    value = declaredField.get(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                String paramName = declaredField.getName();
                if (value == null) {
                    chekMessage += new StringBuilder(TextUtils.isEmpty(chekMessage) ? "" : "\n").append(o.getClass().getName()).append("==>").append(paramName).toString();
                } else if (!paramClass.isPrimitive()) {
                    checkValue(value);
                }
            }
        }
        if (!TextUtils.isEmpty(chekMessage)) {
            LogUtil.d("DataCheckUtil", chekMessage);
        }
    }
}
