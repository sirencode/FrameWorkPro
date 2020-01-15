package com.syh.framework.util;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * so 动态加载，扩展 so 的加载目录
 */
public class SoLibraryInstaller {
    private static final String TAG = SoLibraryInstaller.class.getSimpleName();

    /**
     * 将 so 所在的目录放入PathClassLoader里的nativeLibraryDirectories中
     *
     * @param context
     */
    public static boolean installSoDir(Context context, String dir) {
        LogUtil.i(TAG, "installSoDir");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            LogUtil.e(TAG, "installSoDir sdk_int = " + Build.VERSION.SDK_INT);
            return false;
        }

        File soDirFile = new File(dir);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // private final NativeLibraryElement[] nativeLibraryPathElements;
            return v26Install(soDirFile, context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // private final Element[] nativeLibraryPathElements;
            return v23Install(soDirFile, context);
        } else {
            // private final File[] nativeLibraryDirectories;
            return v19Install(soDirFile, context);
        }
    }

    /**
     *
     * private final File[] nativeLibraryDirectories;
     * @param soDirFile
     * @param context
     */
    private static boolean v19Install(File soDirFile, Context context) {
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
                    ((List) list).add(0, soDirFile);
                } else if(list instanceof File[]) {
                    File[] newList = new File[((File[]) list).length + 1];
                    System.arraycopy(list, 0 , newList, 1, ((File[]) list).length);
                    newList[0] = soDirFile;
                    nativeLibraryDirectoriesField.set(pathList, newList);
                }
                LogUtil.i(TAG, "v19Install successfully");
                return true;
            } catch (NoSuchFieldException e) {
                LogUtil.e(TAG, "v19Install - " + e.toString());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                LogUtil.e(TAG, "v19Install - " + e.toString());
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "v19Install pathList = null");
        }

        LogUtil.i(TAG, "v19Install failure");
        return false;
    }

    /**
     * private final Element[] nativeLibraryPathElements;
     * @param soDirFile
     * @param context
     */
    private static boolean v23Install(File soDirFile, Context context) {
        LogUtil.i(TAG, "v23Install");
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
        Object pathList = getPathList(pathClassLoader);
        if(pathList != null) {
            //获取当前类的属性
            try {
                Field nativeLibraryPathField = pathList.getClass().getDeclaredField("nativeLibraryPathElements");
                nativeLibraryPathField.setAccessible(true);
                Object list = nativeLibraryPathField.get(pathList);
                Class<?> elementType = nativeLibraryPathField.getType().getComponentType();
                Constructor<?> constructor = elementType.getConstructor(File.class, boolean.class, File.class, DexFile.class);
                constructor.setAccessible(true);
                Object element = constructor.newInstance(soDirFile, true, null, null);
                // 放到系统目录之前
                if (list instanceof List) {
                    ((List) list).add(0, element);
                } else if(list instanceof Object[]) {
                    Object[] newList = (Object[]) Array.newInstance(elementType, ((Object[]) list).length + 1);
                    System.arraycopy(list, 0 , newList, 1, ((Object[]) list).length);
                    newList[0] = element;
                    nativeLibraryPathField.set(pathList, newList);
                }
                LogUtil.i(TAG, "v23Install successfully");
                return true;
            } catch (NoSuchFieldException e) {
                LogUtil.e(TAG, "v23Install - " + e.toString());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                LogUtil.e(TAG, "v23Install - " + e.toString());
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                LogUtil.e(TAG, "v23Install - " + e.toString());
                e.printStackTrace();
            } catch (InstantiationException e) {
                LogUtil.e(TAG, "v23Install - " + e.toString());
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                LogUtil.e(TAG, "v23Install - " + e.toString());
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "v23Install pathList = null");
        }

        LogUtil.i(TAG, "v23Install failure");
        return false;
    }

    /**
     * private final NativeLibraryElement[] nativeLibraryPathElements;
     * @param soDirFile
     * @param context
     */
    private static boolean v26Install(File soDirFile, Context context) {
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
                    ((List) list).add(0, element);
                } else if (list instanceof Object[]) {
                    Object[] newList = (Object[]) Array.newInstance(elementType, ((Object[]) list).length + 1);
                    System.arraycopy(list, 0, newList, 1, ((Object[]) list).length);
                    newList[0] = element;
                    nativeLibraryPathField.set(pathList, newList);
                }

                LogUtil.i(TAG, "v26Install successfully");
                return true;
            } catch (NoSuchFieldException e) {
                LogUtil.e(TAG, "v26Install - " + e.toString());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                LogUtil.e(TAG, "v26Install - " + e.toString());
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                LogUtil.e(TAG, "v26Install - " + e.toString());
                e.printStackTrace();
            } catch (InstantiationException e) {
                LogUtil.e(TAG, "v26Install - " + e.toString());
                e.printStackTrace();
            }  catch (InvocationTargetException e) {
                LogUtil.e(TAG, "v26Install - " + e.toString());
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "v26Install pathList = null");
        }

        LogUtil.i(TAG, "v26Install failure");
        return false;
    }

    private static Object getPathList(Object classLoader) {
        LogUtil.i(TAG, "getPathList");
        try {
            Class cls = Class.forName("dalvik.system.BaseDexClassLoader");
            Field declaredField = cls.getDeclaredField("pathList");
            declaredField.setAccessible(true);
            return declaredField.get(classLoader);
        } catch (ClassNotFoundException e) {
            LogUtil.e(TAG, "getPathList - " + e.toString());
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            LogUtil.e(TAG, "getPathList - " + e.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LogUtil.e(TAG, "getPathList - " + e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
