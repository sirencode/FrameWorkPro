package com.syh.framework.annotions;

import android.os.Build;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.syh.framework.util.LogUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 用来控制对象是否需要动态检查
 */
public class NeedCheck {

    public void check(String path) {
        if (DataCheckManager.isOpen()) {
            String chekMessage = "";
            String head = "接口" + path + "返回对象"+ this.getClass().getName() + "空数据如下：";
            Class<?> oClass = this.getClass();
            Field[] declaredFields = oClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(ChekValue.class)) {
                    declaredField.setAccessible(true);
                    Class<?> paramClass = declaredField.getType();
                    Object value = null;
                    try {
                        value = declaredField.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    String paramName = declaredField.getName();
                    if (isEmpty(value)) {
                        chekMessage += new StringBuilder(TextUtils.isEmpty(chekMessage) ? "" : "\n").append(this.getClass().getName()).append(paramClass).append(".").append(paramName).toString();
                    } else if (!paramClass.isPrimitive() && value instanceof NeedCheck && value != null) {
                        ((NeedCheck) value).check(path);
                    }
                }
            }
            if (!TextUtils.isEmpty(chekMessage)) {
                //todo 上传服务器错误数据
                LogUtil.d("DataCheckUtil", head + "\n" + chekMessage);
            }
        }
    }

    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
                return true;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof LongSparseArray
                    && ((LongSparseArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

}
