package com.syh.framework;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.syh.framework.util.CrashHandler;
import com.syh.framework.util.LogUtil;
import com.syh.framework.util.NativeLoadePathUtil;


/**
 * Created bg shenyonghe on 2018/5/22.
 */
public class MyApp extends Application {

    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setCanLog(BuildConfig.DEBUG);
        FileDownloadLog.NEED_LOG = true;
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                ))
                .commit();
        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LaunchRecord.Companion.startRecord();
    }

    public static MyApp getApplication() {
        return instance;
    }
}
