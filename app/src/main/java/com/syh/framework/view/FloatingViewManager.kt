package com.syh.framework.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.syh.framework.expand.showToast
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by shenyonghe on 2020/7/24.
 */
object FloatingViewManager {

    var service: FloatingLogViewService? = null

    const val REQUEST_CODE = 1


    fun start(activity: Activity) {
        if (FloatingLogViewService.isStarted) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
            activity.showToast("当前无权限，请授权")
            activity.startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.packageName)), REQUEST_CODE)
        } else {
            activity.startService(Intent(activity, FloatingLogViewService::class.java))
        }
    }

    fun updateLogMsg(msg: String) {
        if (FloatingLogViewService.isStarted && msg.isNotEmpty() && msg.trim().isNotEmpty() && service != null) {
            service!!.update(formatUrl(msg))
        }
    }

    private fun formatUrl(url: String): String {
        var array = url.split("?")
        var result = StringBuilder(SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date())).append("\n")
        result.append(array[0]).append("\n")
        if (array.size > 1) {
            var params = array[1].split("&")

            for ((index, item) in params.withIndex()) {
                result.append(item)
                if (index!= params.size-1) {
                    result.append("\n")
                }
            }
        }
        return result.toString()
    }
}