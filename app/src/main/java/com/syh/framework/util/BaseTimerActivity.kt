package com.syh.framework.util

import android.os.Bundle
import com.syh.framework.util.timer.OnSecondTick
import com.syh.framework.util.timer.TimerWheelUtil

/**
 * created by 申永鹤(shenyonghe@theduapp.com) on 12/1/20
 **/
open abstract class BaseTimerActivity : BaseActivity() {

    var tick: OnSecondTick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tick = object : OnSecondTick {
            override fun onSecondTick() {
                onTimerSecond()
            }
        }
        TimerWheelUtil.addSecondListener(tick!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        tick?.let {
            TimerWheelUtil.removeSecondListener(it)
        }
    }

    abstract fun onTimerSecond()
}