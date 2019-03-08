package com.syh.framework.util.unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class UnSafeProxy {

    /** 对象头部的大小 */
    private static final int OBJECT_HEADER_SIZE = 8;
    /** 对象占用内存的最小值 */
    private static final int MINIMUM_OBJECT_SIZE = 8;
    /** 对象按多少字节的粒度进行对齐 */
    private static final int OBJECT_ALIGNMENT = 8;

    private static ReflectUtils sUtils;
    private static Class sUnsafeClass;
    private static Object sUnsafe;

    static {
        try {
            sUtils = ReflectUtils.getInstance();
            sUnsafeClass = Class.forName("sun.misc.Unsafe");
            sUnsafe = sUtils.getUnsafe(sUnsafeClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Object allocateInstance(Class<?> params) {
        Method method = sUtils.getMethod(sUnsafeClass, "allocateInstance", Class.class);
        return sUtils.invokeMethod(method, sUnsafe, params);
    }

    public static long objectFieldOffset(Field field) {
        Method method = sUtils.getMethod(sUnsafeClass, "objectFieldOffset", Field.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, field);
        return obj == null ? 0 : (Long) obj;
    }

    public static long objectStaticFieldOffset(Field field) {
        Method method = sUtils.getMethod(sUnsafeClass, "staticFieldOffset", Field.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, field);
        return obj == null ? 0 : (Long) obj;
    }

    public static long putLong(Object object, long offset, long newValue) {
        Method method = sUtils.getMethod(sUnsafeClass, "putLong", Object.class, long.class, long.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, newValue);
        return obj == null ? 0 : (Long) obj;
    }

    public static long putInt(Object object, long offset, int newValue) {
        Method method = sUtils.getMethod(sUnsafeClass, "putInt", Object.class, long.class, int.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, newValue);
        return obj == null ? 0 : (Long) obj;
    }

    public static long putObject(Object object, long offset, Object newValue) {
        Method method = sUtils.getMethod(sUnsafeClass, "putObject", Object.class, long.class, Object.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, newValue);
        return obj == null ? 0 : (Long) obj;
    }

    public static int arrayIndexScale(Class clazz) {
        Method method = sUtils.getMethod(sUnsafeClass, "arrayIndexScale", Class.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, clazz);
        return obj == null ? 0 : (Integer) obj;
    }

    public static int arrayBaseOffset(Class clazz) {
        Method method = sUtils.getMethod(sUnsafeClass, "arrayBaseOffset", Class.class);
        Object obj = sUtils.invokeMethod(method, sUnsafe, clazz);
        return obj == null ? 0 : (Integer) obj;
    }

    public static long getObjectSize(Object obj) {
        if (obj.getClass().isArray()) {
            Class<?> klazz = obj.getClass();
            int base = UnSafeProxy.arrayBaseOffset(klazz);
            int scale = UnSafeProxy.arrayIndexScale(klazz);
            long size = base + (scale * Array.getLength(obj));
            if ((size % OBJECT_ALIGNMENT) != 0) {
                size += OBJECT_ALIGNMENT - (size % OBJECT_ALIGNMENT);
            }
            return Math.max(MINIMUM_OBJECT_SIZE, size);
        } else {
            // 如果数组对象则迭代遍历该对象的父类，找到最后一个非静态字段的偏移量
            for (Class<?> klazz = obj.getClass(); klazz != null; klazz = klazz
                    .getSuperclass()) {
                long lastFieldOffset = -1;
                for (Field f : klazz.getDeclaredFields()) {
                    if (!Modifier.isStatic(f.getModifiers())) {
                        lastFieldOffset = Math.max(lastFieldOffset,
                                UnSafeProxy.objectFieldOffset(f));
                    }
                }
                if (lastFieldOffset > 0) {
                    lastFieldOffset += 1;
                    if ((lastFieldOffset % OBJECT_ALIGNMENT) != 0) {
                        lastFieldOffset += OBJECT_ALIGNMENT
                                - (lastFieldOffset % OBJECT_ALIGNMENT);
                    }
                    return Math.max(MINIMUM_OBJECT_SIZE, lastFieldOffset);
                }
            }
            // 该对象没有任何属性
            long size = OBJECT_HEADER_SIZE;
            if ((size % OBJECT_ALIGNMENT) != 0) {
                size += OBJECT_ALIGNMENT - (size % OBJECT_ALIGNMENT);
            }
            return Math.max(MINIMUM_OBJECT_SIZE, size);
        }
    }
}
