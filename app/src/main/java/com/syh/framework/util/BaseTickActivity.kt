package com.syh.framework.util

import android.os.Bundle
import com.syh.framework.MyApp
import com.syh.framework.onSecondTick

/**
 * Author: shenyonghe
 * Date: 11/26/20
 * Version: v1.0
 * Description:
 * Modification History:
 * Date Author Version Description
 * ------------------------------------
 * 11/26/20 shenyonghe v1.0
 * Why & What is modified:
 **/
open abstract class BaseTickActivity : BaseActivity() {

    var tick: onSecondTick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tick = object : onSecondTick {
            override fun onSecondTick() {
                onTick()
            }

        }
        MyApp.getApplication().addListener(tick)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tick != null) {
            MyApp.getApplication().removeListener(tick)
        }
    }

    abstract fun onTick()
}