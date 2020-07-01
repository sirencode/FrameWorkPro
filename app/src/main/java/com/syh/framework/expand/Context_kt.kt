package com.syh.framework.expand

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

/**
 * Created by shenyonghe on 2020/5/12.
 */
fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.start(source: Class<out Activity>) {
    var intent = Intent(this, source)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
}

fun Context.getScreenWidth(): Int {
    if (ScreenUtil.width == 0) {
        ScreenUtil.width = this.resources.displayMetrics.widthPixels
    }
    return ScreenUtil.width
}

fun Context.getScreenHeight(): Int {
    if (ScreenUtil.height == 0) {
        ScreenUtil.height = this.resources.displayMetrics.heightPixels
    }
    return ScreenUtil.height
}

fun Context.getDensity(): Float {
    if (ScreenUtil.density == 0f) {
        ScreenUtil.density = this.resources.displayMetrics.density
    }
    return ScreenUtil.density
}

fun Context.dp2px(dp: Int): Int {
    return (getDensity() * dp + 0.5f).toInt()
}

fun Context.isWifi(): Boolean {
    var connectivityManager : ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected
}

class ScreenUtil {
    companion object {
        var width = 0;
        var height = 0;
        var density = 0f;
    }
}