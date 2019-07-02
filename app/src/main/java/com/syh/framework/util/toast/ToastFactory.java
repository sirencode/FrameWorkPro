package com.syh.framework.util.toast;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

public class ToastFactory {
    private volatile static ToastFactory sToastFactory;

    private IToast mIToast;

    private ToastFactory(Context context) {
        if (isNotificationEnabled(context)) {
            mIToast = new SystemToast(context);
        } else {
            mIToast = new CustomToast(context);
        }
    }

    public static IToast getInstance(Context context) {
        if (sToastFactory == null) {
            synchronized (ToastFactory.class) {
                if (sToastFactory == null) {
                    sToastFactory = new ToastFactory(context);
                }
            }
        }
        return sToastFactory.mIToast;
    }

    private static boolean isNotificationEnabled(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        return manager.areNotificationsEnabled();
    }
}

