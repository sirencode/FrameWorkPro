package com.syh.framework.view

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.syh.framework.R
import com.syh.framework.expand.screenWidth
import com.syh.framework.util.UIThreadUtil

/**
 * Created by shenyonghe on 2020/7/24.
 */
class FloatingLogViewService : Service() {

    companion object {
        @kotlin.jvm.JvmField
        var isStarted: Boolean = false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    lateinit var windowManager: WindowManager
    lateinit var layoutParams: WindowManager.LayoutParams
    lateinit var displayView: View
    lateinit var recycler: RecyclerView
    lateinit var adapter: LogViewAdapter

    override fun onCreate() {
        super.onCreate()
        isStarted = true
        FloatingViewManager.service = this
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.gravity = Gravity.LEFT or Gravity.TOP
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.width = screenWidth - 200
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.x = 100
        layoutParams.y = 300
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showWindow()
        return super.onStartCommand(intent, flags, startId)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showWindow() {
        if (Settings.canDrawOverlays(this)) {
            val layoutInflater = LayoutInflater.from(this)
            displayView = layoutInflater.inflate(R.layout.view_log_window, null)
            recycler = displayView.findViewById(R.id.recycler)
            displayView.findViewById<View>(R.id.btn_window_close).setOnClickListener {
                windowManager.removeView(displayView)
                stopSelf()
            }

            recycler.layoutManager = LinearLayoutManager(this)
            adapter = LogViewAdapter(mutableListOf())
            recycler.adapter = adapter
            displayView.setOnTouchListener(FloatingOnTouchListener())
            windowManager.addView(displayView, layoutParams)
        }
    }

    inner class LogViewAdapter(var list: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var item = LayoutInflater.from(parent.context).inflate(R.layout.item_log_view, parent, false) as TextView
            return TextViewHolder(item)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var textView: TextView = holder.itemView as TextView
            textView.text = list[position]
        }

    }

    inner class TextViewHolder(itemView: TextView) : RecyclerView.ViewHolder(itemView)

    fun update(msg: String) {
        UIThreadUtil.runOnUiThread(Runnable {
            adapter?.let {
                it.list.add(0, msg)
                it.notifyDataSetChanged()
            }
        })
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