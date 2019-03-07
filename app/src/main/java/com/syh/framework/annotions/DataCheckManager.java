package com.syh.framework.annotions;

import android.os.Build;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.syh.framework.util.FieldLruCache;
import com.syh.framework.util.LogUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCheckManager {
    /**
     * 开关控制是否需要校验
     */
    private static boolean open = true;

    public static void setOpen(boolean isOpen) {
        open = isOpen;
    }

    public static boolean isOpen() {
        return open;
    }

    private static ExecutorService singleES;

    public static void checkValue(Object o, String path) {
        if (needCheck(o)) {
            if (singleES == null) {
                singleES = newFixedThreadPool(1);
            }
            singleES.execute(() -> {
                try {
                    Map<String, String> map = new HashMap<>();
                    StringBuffer msg = new StringBuffer("");
                    String head = "接口" + path + "返回对象" + o.getClass().getName() + "空数据如下：";
                    if (o.getClass().isArray() && !o.getClass().isPrimitive() && Array.getLength(o) > 0 && Array.get(o, 0) instanceof NeedCheck) {
                        for (int i = 0; i < Array.getLength(o); i++) {
                            String result = check(Array.get(o, i));
                            if (!TextUtils.isEmpty(result)) {
                                map.put(Array.get(o, i).getClass().getSimpleName() + "." + i, result);
                            }
                        }
                    }
                    if (o instanceof Collection && !((Collection) o).isEmpty() && ((Collection) o).toArray()[0] instanceof NeedCheck) {
                        for (int i = 0; i < ((Collection) o).size(); i++) {
                            String result = check(((Collection) o).toArray()[i]);
                            if (!TextUtils.isEmpty(result)) {
                                map.put(((Collection) o).toArray()[i].getClass().getSimpleName() + "." + i, result);
                            }
                        }
                    }
                    if (o instanceof NeedCheck) {
                        String result = check(o);
                        if (!TextUtils.isEmpty(result)) {
                            map.put(o.getClass().getSimpleName(), result);
                        }
                    }
                    if (map.size() > 0) {
                        LogUtil.d("DataCheckUtil", head + "\n" + shortMap(map).toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Error e) {

                }
            });
        }
    }

    private static Map<String, String> shortMap(Map<String, String> baseMap) {
        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());
        sortMap.putAll(baseMap);
        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            int str1Num = getInt(str1);
            int str2Num = getInt(str2);
            if (str1Num > str2Num) {
                return 1;
            } else if (str1Num < str2Num) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private static int getInt(String key) {
        int result = 0;
        try {
            if (!TextUtils.isEmpty(key)) {
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(key);
                String num = m.replaceAll("").trim();
                if (!TextUtils.isEmpty(num)) {
                    result = Integer.parseInt(num);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean needCheck(Object o) {
        return o != null && open && ((o.getClass().isArray() && !o.getClass().isPrimitive() && Array.getLength(o) > 0 && Array.get(o, 0) instanceof NeedCheck)
                || o instanceof Collection && !((Collection) o).isEmpty() && ((Collection) o).toArray()[0] instanceof NeedCheck || o instanceof NeedCheck);
    }

    public static synchronized ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), sThreadFactory);
    }

    /**
     * 创建线程工厂
     */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "dataCheck:" + mCount.getAndIncrement());
            thread.setPriority(Thread.MIN_PRIORITY);
            return thread;
        }
    };

    // 站的处理32 缓存 class
    public static String check(Object o) {
        StringBuilder chekMessage = new StringBuilder("");
        Class<?> oClass = o.getClass();
        Field[] declaredFields = FieldLruCache.get(oClass);
        if (declaredFields == null) {
            declaredFields = oClass.getDeclaredFields();
            FieldLruCache.save(oClass, declaredFields);
        }
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(ChekNull.class)) {
                declaredField.setAccessible(true);
                Class<?> paramClass = declaredField.getType();
                Object value = null;
                try {
                    value = declaredField.get(o);
                    String paramName = declaredField.getName();
                    if (isEmpty(value)) {
                        if (!TextUtils.isEmpty(chekMessage)) {
                            chekMessage.append(",");
                        }
                        chekMessage.append(o.getClass().getSimpleName()).append(".").append(paramName).toString();
                    }
                    if (value != null && !paramClass.isPrimitive() && value instanceof NeedCheck) {
                        if (!TextUtils.isEmpty(chekMessage)) {
                            chekMessage.append(",");
                        }
                        chekMessage.append(check(value));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
//        if (!TextUtils.isEmpty(chekMessage)) {
//            //todo 上传服务器错误数据
//            LogUtil.d("DataCheckUtil", head + "\n" + chekMessage);
//        }
        return chekMessage.toString();
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
