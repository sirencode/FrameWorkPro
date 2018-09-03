package com.syh.framework.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;

/**
 * Created bg shenyonghe on 2018/6/6.
 */
public class SPHelper {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static String DEFAULT_FILE_NAME = "";

    public SPHelper(Context context, String fileName) {
        if (TextUtils.isEmpty(DEFAULT_FILE_NAME)) {
            DEFAULT_FILE_NAME = context.getPackageName();
        }
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public SPHelper(Context context) {
        this(context, DEFAULT_FILE_NAME);
    }

    /**
     * 存储
     * 如果数据量很大，请使用editor.apply()
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 获取保存的数据
     * 如果数据量很大，请使用editor.apply()
     */
    public Object getValue(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }
}
