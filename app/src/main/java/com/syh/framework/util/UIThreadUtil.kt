package com.syh.framework.util

import android.os.Handler
import android.os.Looper

/**
 * Created by shenyonghe on 2020/9/15.
 */
object UIThreadUtil {
    private var sMainHandler: Handler? = null


    fun isOnUiThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }


    /**
     * Runs the given `Runnable` on the UI thread.
     */
    fun runOnUiThread(runnable: Runnable?) {
        synchronized(UIThreadUtil::class.java) {
            if (sMainHandler == null) {
                sMainHandler = Handler(Looper.getMainLooper())
            }
        }
        sMainHandler!!.post(runnable)
    }

    fun cancel() {
        sMainHandler?.let {
            it.removeCallbacksAndMessages(null)
        }
    }
}