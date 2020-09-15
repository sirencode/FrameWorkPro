package com.syh.framework.util

import android.view.View

/**
 * create by shenyonghe at 2018/12/1
 */
abstract class ClickProxy : View.OnClickListener {
    private var lastClickTime: Long = 0

    constructor(time: Int = MIN_CLICK_DELAY_TIME) {
        MIN_CLICK_DELAY_TIME = time
    }

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            checkClick(v)
        }
    }

    abstract fun checkClick(view: View?)

    companion object {
        var MIN_CLICK_DELAY_TIME = 1000
    }
}