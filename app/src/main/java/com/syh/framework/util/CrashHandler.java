package com.syh.framework.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;

/**
 * Created by shenyonghe on 2019-12-30.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashCatchHandler";
    private static CrashHandler crashHandler = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultCaughtExceptionHandler;

    /**
     * 饿汉单例模式(静态）
     *
     * @return
     */
    public static CrashHandler getInstance() {
        return crashHandler;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取默认的系统异常捕获器
        mDefaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //把当前的crash捕获器设置成默认的crash捕获器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && mDefaultCaughtExceptionHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultCaughtExceptionHandler.uncaughtException(thread, throwable);
        }else {
            LogUtil.d(TAG, "error : "+ throwable.getMessage());
            //退出程序
//            ActivityManager activityMgr = (ActivityManager) mContext
//                    .getSystemService(Context.ACTIVITY_SERVICE);
//            activityMgr.killBackgroundProcesses(mContext.getPackageName());
//            System.exit(0);
        }
    }
    /**
     * 自定义错误处理
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        final String msg = ex.getLocalizedMessage();
        if (msg == null) {
            return false;
        }

        if (ex instanceof  UnsatisfiedLinkError) {
            return true;
        }
        return false;
    }

}
