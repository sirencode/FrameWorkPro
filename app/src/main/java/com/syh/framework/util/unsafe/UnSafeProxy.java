package com.syh.framework.util.unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UnSafeProxy {

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
}
