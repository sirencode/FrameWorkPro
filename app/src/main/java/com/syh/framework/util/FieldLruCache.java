package com.syh.framework.util;

import android.util.LruCache;

import com.syh.framework.util.unsafe.UnSafeProxy;

import java.lang.reflect.Field;

public class FieldLruCache {

    private static LruCache<Class<?>, Field[]> classLruCache;
    final static int maxMemory = (int) (Runtime.getRuntime().maxMemory());

    static {
        classLruCache = new LruCache<Class<?>, Field[]>(maxMemory / 32){
            @Override
            protected int sizeOf(Class<?> key, Field[] value) {
                return getObjectSize(value);
            }
        };
    }

    public static Field[] get(Class<?> cl) {
        Field[] fields = null;
        fields = classLruCache.get(cl);
        return fields;
    }

    public synchronized static void save(Class<?> cl,Field[] fields) {
        classLruCache.put(cl,fields);
    }

    public static int getObjectSize(Field[] fields) {
        int size = 0;
        for (Field field : fields) {
            size += UnSafeProxy.objectFieldOffset(field);
        }
        LogUtil.d("FieldLruCache","FieldLruCache::"+size);
        return size;
    }

}
