package com.syh.framework.http.net_check

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.syh.framework.MyApp
import com.syh.framework.util.ToastUtil

/**
 * Created by shenyonghe on 2020/7/24.
 */
object NetCheckViewManager {

    var service: NetCheckViewService? = null

    fun start(request: String, error: String) {
        if (NetCheckViewService.isStarted) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(MyApp.getApplication())) {
            ToastUtil.showToast(MyApp.getApplication(),"当前无权限，请授权")
            var intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + MyApp.getApplication().packageName)
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MyApp.getApplication().startActivity(intent)
        } else {
            var intent = Intent(
                    MyApp.getApplication(),
                NetCheckViewService::class.java
            )
            intent.putExtra("request",request)
            intent.putExtra("error",error)
            MyApp.getApplication().startService(intent)
        }
    }

    fun updateLogMsg(request: String, error: String) {
        if (!NetCheckViewService.isStarted) {
            start(request,error)
        } else if (NetCheckViewService.isStarted && service != null) {
            service!!.update(request, error)
        }
    }
}