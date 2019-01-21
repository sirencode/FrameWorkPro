package com.syh.framework.util;

import android.util.Log;

/**
 * Created bg shenyonghe on 2018/5/22.
 */
public class LogUtil {
    private static boolean canLog;

    public static void setCanLog(boolean canLog) {
        LogUtil.canLog = canLog;
    }

    public static boolean isCanLog() {
        return LogUtil.canLog;
    }

    public static void LogA(String tag, String msg) {
        log(LogType.LogA, tag, msg);
    }

    public static void d(String tag, String msg) {
        log(LogType.LogD, tag, msg);
    }

    public static void LogE(String tag, String msg) {
        log(LogType.LogE, tag, msg);
    }

    public static void LogI(String tag, String msg) {
        log(LogType.LogI, tag, msg);
    }

    public static void LogW(String tag, String msg) {
        log(LogType.LogW, tag, msg);
    }

    public static void SystemO(String msg) {
        log(LogType.SystemO, null, msg);
    }

    private static void log(LogType type, String tag, String msg) {
        if (LogUtil.isCanLog()) {
            switch (type) {
                case LogA:
                    Log.wtf(tag, msg);
                    break;
                case LogD:
                    Log.d(tag, msg);
                    break;
                case LogE:
                    Log.e(tag, msg);
                    break;
                case LogI:
                    Log.i(tag, msg);
                    break;
                case LogV:
                    Log.v(tag, msg);
                    break;
                case LogW:
                    Log.w(tag, msg);
                    break;
                case SystemO:
                    System.out.println(msg);
                    break;
                default:
                    break;
            }
        }
    }


    private enum LogType {
        LogD,
        LogE,
        LogI,
        LogV,
        LogA,
        LogW,
        SystemO
    }

}
