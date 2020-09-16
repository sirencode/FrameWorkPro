package com.syh.framework.http.net_check

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.RequiresApi
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.syh.framework.MyApp
import com.syh.framework.R
import com.syh.framework.util.UIParameter

/**
 * Created by shenyonghe on 2020/7/24.
 */
class NetCheckViewService : Service() {

    companion object {
        @kotlin.jvm.JvmField
        var isStarted: Boolean = false
    }

    var errorMsg:String = ""
    var requestMsg:String = ""

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    lateinit var windowManager: WindowManager
    lateinit var layoutParams: WindowManager.LayoutParams
    lateinit var displayView: View
    lateinit var requestView: TextView
    lateinit var errorView: TextView

    override fun onCreate() {
        super.onCreate()
        isStarted = true
        NetCheckViewManager.service = this
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.gravity = Gravity.LEFT or Gravity.TOP
        layoutParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.width = UIParameter.getWidth(MyApp.getApplication()) - 200
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.x = 100
        layoutParams.y = 300
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            requestMsg = it.getStringExtra("request")
            errorMsg = it.getStringExtra("error")
        }
        showWindow()
        return super.onStartCommand(intent, flags, startId)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showWindow() {
        if (Settings.canDrawOverlays(this)) {
            val layoutInflater = LayoutInflater.from(this)
            displayView = layoutInflater.inflate(R.layout.view_net_chect_window, null)
            requestView = displayView.findViewById(R.id.tv_request)
            errorView = displayView.findViewById(R.id.tv_error)
            requestView.text = requestMsg
            errorView.text = errorMsg
            displayView.findViewById<ImageView>(R.id.iv_close).setOnClickListener {
                windowManager.removeView(displayView)
                stopSelf()
            }
            displayView.setOnTouchListener(FloatingOnTouchListener())
            windowManager.addView(displayView, layoutParams)
        }
    }


    fun update(request: String, error: String) {
        requestView.text = request
        errorView.text = error
    }

    override fun onDestroy() {
        isStarted = false
        super.onDestroy()
    }

    inner class FloatingOnTouchListener : View.OnTouchListener {

        var x: Int = 0
        var y: Int = 0

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event!!.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    layoutParams.x = layoutParams.x + movedX
                    layoutParams.y = layoutParams.y + movedY
                    windowManager.updateViewLayout(v, layoutParams)
                }
                else -> {
                }
            }
            return false
        }

    }
}