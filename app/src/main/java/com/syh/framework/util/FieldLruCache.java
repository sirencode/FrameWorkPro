package com.syh.framework.util;

import android.util.LruCache;

import com.syh.framework.util.unsafe.UnSafeProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldLruCache {

    private static LruCache<Class<?>, Field[]> classLruCache;
    final static int maxMemory = (int) (Runtime.getRuntime().maxMemory());

    static {
        classLruCache = new LruCache<Class<?>, Field[]>(maxMemory / 32) {
            @Override
            protected int sizeOf(Class<?> key, Field[] value) {
                return getObjectSize(value);
            }
        };
    }

    public static Field[] get(Class<?> cl) {
        return classLruCache.get(cl);
    }

    public synchronized static void save(Class<?> cl, Field[] fields) {
        classLruCache.put(cl, fields);
    }

    public static int getObjectSize(Field[] fields) {
        int maxSize = 0;
        int offset;
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.STATIC) == 0) {
                offset = (int) UnSafeProxy.objectFieldOffset(field);
                LogUtil.d("offset", "offset::" + offset);
                maxSize = maxSize > offset ? maxSize : offset;
            }
        }
        maxSize = (maxSize / 8 + 1) * 8;
        LogUtil.d("FieldLruCache", "FieldLruCache::" + maxSize);
        return maxSize;
    }

}
