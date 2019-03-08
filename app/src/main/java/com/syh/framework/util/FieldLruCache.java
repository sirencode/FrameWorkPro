package com.syh.framework.util;

import android.util.LruCache;

import com.syh.framework.util.unsafe.UnSafeProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.TreeSet;

public class FieldLruCache {

    private static LruCache<Class<?>, Field[]> classLruCache;
    final static int maxMemory = (int) (Runtime.getRuntime().maxMemory());

    static {
        classLruCache = new LruCache<Class<?>, Field[]>(maxMemory / 32) {
            @Override
            protected int sizeOf(Class<?> key, Field[] value) {
                return getObjectSize(key.getSimpleName(), value);
            }
        };
    }

    public static Field[] get(Class<?> cl) {
        return classLruCache.get(cl);
    }

    public synchronized static void save(Class<?> cl, Field[] fields) {
        classLruCache.put(cl, fields);
    }

    public static int getObjectSize(String className, Field[] fields) {
        int maxSize = 0;
        int offset;
        //为了观察每个field大小，对field的offSet进行了排序，后者-前者=前者大小
        TreeSet<Field> hashSet = new TreeSet(new FieldComparator());
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.STATIC) == 0) {
                offset = (int) UnSafeProxy.objectFieldOffset(field);
                maxSize = maxSize > offset ? maxSize : offset;
                hashSet.add(field);
            }
        }
        maxSize = (maxSize / 8 + 1) * 8;
        LogUtil.d("FieldLruCache", className + "FieldLruCache::" + maxSize);
        for (Field field : hashSet) {
            long offSet = UnSafeProxy.objectFieldOffset(field);
            LogUtil.d("offset", className + "." + field.getName() + "==>" + field.getType() + "::offset::" + offSet);
        }
        return maxSize;
    }

    static class FieldComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            Field f1 = (Field) o1;
            Field f2 = (Field) o2;
            long offSet1 = UnSafeProxy.objectFieldOffset(f1);
            long offSet2 = UnSafeProxy.objectFieldOffset(f2);
            return Integer.parseInt((offSet1 - offSet2) + "");
        }
    }

}
