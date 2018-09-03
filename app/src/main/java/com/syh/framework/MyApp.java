package com.syh.framework;

import android.app.Application;

import com.syh.framework.util.LogUtil;


/**
 * Created bg shenyonghe on 2018/5/22.
 */
public class MyApp extends Application {

    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setCanLog(BuildConfig.DEBUG);
        instance = this;
    }

    public static MyApp getApplication() {
        return instance;
    }
}
