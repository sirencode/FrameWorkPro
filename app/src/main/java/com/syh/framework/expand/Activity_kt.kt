package com.syh.framework.expand

import android.app.Activity
import android.content.Intent

/**
 * Created by shenyonghe on 2020/5/12.
 */

fun Activity.startForResult(source: Class<out Activity>,requestCode: Int = -1) {
    var intent = Intent(this, source)
    startActivityForResult(intent, -1)
}
