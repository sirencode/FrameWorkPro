package com.syh.framework;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.syh.framework.asm.ASMPathManager;
import com.syh.framework.util.AppFrontBackHelper;
import com.syh.framework.util.LogUtil;


/**
 * Created bg shenyonghe on 2018/5/22.
 */
public class MyApp extends Application {

    private static MyApp instance;
    private static final String PROCESS = "com.syh.framework";


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
        initPieWebView();

        AppFrontBackHelper helper = new AppFrontBackHelper();
        helper.register(MyApp.this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
            }

            @Override
            public void onBack() {
                //应用切到后台处理
                LogUtil.e("ASM-TAG", ASMPathManager.list.toString());
            }
        });

    }

    private void initPieWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName(this);
            if (!PROCESS.equals(processName)) {
                WebView.setDataDirectorySuffix(getString(processName, "jiuwu"));
            }
        }
    }

    public String getProcessName(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }

    public String getString(String s, String defValue) {
        return isEmpty(s) ? defValue : s;
    }

    public boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
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
