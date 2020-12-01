package com.syh.framework.util.timer

import android.os.Handler
import android.os.Message
import android.os.SystemClock

/**
 * created by 申永鹤(shenyonghe@theduapp.com) on 12/1/20
 **/
object TimerWheelUtil {

    // 目前只是用到了秒级的倒计时，可根据刻度自定义一些其他数据量级的
    private val onSecondTicks: MutableList<OnSecondTick> = mutableListOf()

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            startTimer()
        }
    }

    private val mTicker: Runnable by lazy {
        object : Runnable {
            override fun run() {
                // 误差补偿算法,确保每次发出的消息都是一千的整数倍,如now取出来是1200，那么next = 1200 + （1000 - 1200 % 1000）也就是next= 2000
                val now = SystemClock.uptimeMillis()
                val next = now + (1000 - now % 1000)
                handler.postAtTime(this, next)
                println("startTimer====>$now")
                for (tick in onSecondTicks) {
                    tick.onSecondTick()
                }
            }
        }
    }

    fun startTimer() {
        val now = SystemClock.uptimeMillis()
        val next = now + (1000 - now % 1000)
        handler.postAtTime(mTicker, next)
    }

    fun addSecondListener(lis: OnSecondTick) {
        onSecondTicks.add(lis)
    }

    fun removeSecondListener(lis: OnSecondTick) {
        if (onSecondTicks.contains(lis)) {
            onSecondTicks.remove(lis)
        }
    }

}