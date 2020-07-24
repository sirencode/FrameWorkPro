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


val Context.screenWidth: Int
    get() = if (ScreenUtil.width == 0) this.resources.displayMetrics.widthPixels else ScreenUtil.width

val Context.screenHeight: Int
    get() = if (ScreenUtil.height == 0) this.resources.displayMetrics.heightPixels else ScreenUtil.height

val Context.density: Float
    get() = if (ScreenUtil.density == 0f) this.resources.displayMetrics.density else ScreenUtil.density

fun Context.isWifi(): Boolean {
    var connectivityManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected
}

class ScreenUtil {
    companion object {
        var width = 0
        var height = 0
        var density = 0f
    }
}