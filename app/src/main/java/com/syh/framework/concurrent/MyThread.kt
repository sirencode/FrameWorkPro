package com.syh.framework.concurrent

import android.os.Looper

/**
 * Created by shenyonghe on 2020/5/19.
 */
class MyThread : Thread() {

    override fun run() {
        Looper.prepare()
        Looper.loop()
        super.run()
    }
}