package com.syh.framework.util;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * Created by shenyonghe on 2019-12-31.
 */
public class NativeLoadePathUtil {
    private static final String TAG = NativeLoadePathUtil.class.getSimpleName();

    /**
     * 将 so 所在的目录放入PathClassLoader里的nativeLibraryDirectories中
     *
     * @param context
     */
    public static void installSoDir(Context context) {
        LogUtil.i(TAG, "installSoDir");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            LogUtil.e(TAG, "installSoDir sdk_int = " + Build.VERSION.SDK_INT);
            return;
        }
        File soDirFile = context.getDir("lib", Context.MODE_PRIVATE);
        if (!soDirFile.exists()) {
            soDirFile.mkdirs();
        }
        LogUtil.d("installSoDir", "installSoDir==>" + soDirFile.getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // private final NativeLibraryElement[] nativeLibraryPathElements;
            v26Install(soDirFile, context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // private final Element[] nativeLibraryPathElements;
            v23Install(soDirFile, context);
        } else {
            // private final File[] nativeLibraryDirectories;
            v19Install(soDirFile, context);
        }
    }

    public static String getZipPath(Context context) {
        return context.getDir("lib", Context.MODE_PRIVATE).getPath();
    }

    /**
     * private final File[] nativeLibraryDirectories;
     *
     * @param soDirFile
     * @param context
     */
    private static void v19Install(File soDirFile, Context context) {
        LogUtil.i(TAG, "v19Install");
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
        Object pathList = getPathList(pathClassLoader);
        if (pathList != null) {
            //获取当前类的属性
            try {
                Field nativeLibraryDirectoriesField = pathList.getClass().getDeclaredField("nativeLibraryDirectories");
                nativeLibraryDirectoriesField.setAccessible(true);
                Object list = nativeLibraryDirectoriesField.get(pathList);
                if (list instanceof List) {
                    ((List) list).add(soDirFile);
                } else if (list instanceof File[]) {
                    File[] newList = new File[((File[]) list).length + 1];
                    System.arraycopy(list, 0, newList, 0, ((File[]) list).length);
                    newList[((File[]) list).length] = soDirFile;
                    nativeLibraryDirectoriesField.set(pathList, newList);
                }
            } catch (NoSuchFieldException e) {
                LogUtil.e(TAG, "v19Install - " + e.toString());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                LogUtil.e(TAG, "v19Install - " + e.toString());
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "v23Install pathList = null");
        }
    }

    /**
     * private final Element[] nativeLibraryPathElements;
     *
     * @param soDirFile
     * @param context
     */
    private static void v23Install(File soDirFile, Context context) {
        LogUtil.i(TAG, "v23Install");
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
        Object pathList = getPathList(pathClassLoader);
        if (pathList != null) {
            //获取当前类的属性
            try {
                Field nativeLibraryPathField = pathList.getClass().getDeclaredField("nativeLibraryPathElements");
                nativeLibraryPathField.setAccessible(true);
                Object list = nativeLibraryPathField.get(pathList);
                Class<?> elementType = nativeLibraryPathField.getType().getComponentType();
                Constructor<?> constructor = elementType.getConstructor(File.class, boolean.class, File.class, DexFile.class);
                constructor.setAccessible(true);
                Object element = constructor.newInstance(soDirFile);
                if (list instanceof List) {
                    ((List) list).add(element);
                } else if (list instanceof Object[]) {
                    Object[] newList = (Object[]) Array.newInstance(elementType, ((Object[]) list).length + 1);
                    System.arraycopy(list, 0, newList, 0, ((Object[]) list).length);
                    newList[((Object[]) list).length] = element;
                    nativeLibraryPathField.set(pathList, newList);
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "v23Install - " + e.toString());
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "v23Install pathList = null");
        }
    }

    /**
     * private final NativeLibraryElement[] nativeLibraryPathElements;
     *
     * @param soDirFile
     * @param context
     */
    private static void v26Install(File soDirFile, Context context) {
        LogUtil.i(TAG, "v26Install");
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
        Object pathList = getPathList(pathClassLoader);
        if (pathList != null) {
            try {
                Field nativeLibraryPathField = pathList.getClass().getDeclaredField("nativeLibraryPathElements");
                nativeLibraryPathField.setAccessible(true);
                Object list = nativeLibraryPathField.get(pathList);
                Class<?> elementType = nativeLibraryPathField.getType().getComponentType();
                Constructor<?> constructor = elementType.getConstructor(File.class);
                constructor.setAccessible(true);
                Object element = constructor.newInstance(soDirFile);
                if (list instanceof List) {
                    ((List) list).add(element);
                } else if (list instanceof Object[]) {
                    Object[] newList = (Object[]) Array.newInstance(elementType, ((Object[]) list).length + 1);
                    System.arraycopy(list, 0, newList, 0, ((Object[]) list).length);
                    newList[((Object[]) list).length] = element;
                    nativeLibraryPathField.set(pathList, newList);
                    LogUtil.e(TAG, "v26Install - ");
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "v26Install - " + e.toString());
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "v26Install pathList = null");
        }
    }

    private static Object getPathList(Object classLoader) {
        LogUtil.i(TAG, "getPathList");
        try {
            Class cls = Class.forName("dalvik.system.BaseDexClassLoader");
            Field declaredField = cls.getDeclaredField("pathList");
            declaredField.setAccessible(true);
            return declaredField.get(classLoader);
        } catch (Exception e) {
            LogUtil.e(TAG, "getPathList - " + e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
