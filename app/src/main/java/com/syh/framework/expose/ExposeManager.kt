package com.syh.framework.expose

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver
import com.google.common.collect.MapMaker
import com.syh.framework.R
import com.syh.framework.util.LogUtil
import java.util.concurrent.ConcurrentMap

object ExposeManager {

    /**
     * 上报间隔时间
     */
    private var diffTime = 60 * 1000L

    /**
     * 缓存的最大条数
     */
    private var tempCount = 100

    @Volatile
    private var lastReportTime = 0L

    @Volatile
    private var isReporting = false

    private val timerHandler = Handler(Looper.getMainLooper())

    private val keysSet: HashSet<String> = HashSet()

    @Synchronized
    fun getCount(): Int {
        return keysSet.size
    }

    @Synchronized
    fun getExposeKeys(): String {
        val builder = StringBuilder()
        for (item in keysSet) {
            builder.append(item).append("#")
        }
        if (builder.isNotEmpty()) {
            builder.deleteCharAt(builder.length - 1)
        }
        return builder.toString()
    }

    @Synchronized
    fun addExposeKey(key: String) {
        keysSet.add(key)

    }

    @Synchronized
    fun clearAll(exposeKeys: String) {
        if (exposeKeys.contains("#")) {
            val keys = exposeKeys.split("#")
            //1. 删除内存中数据
            val iterator = keysSet.iterator()
            iterator.forEach {
                if (keys.contains(it)) {
                    iterator.remove()
                }
            }
        } else {
            keysSet.clear()
        }
    }

    /**
     * 初始化
     * @param context 上下文
     */
    fun init(context: Application) {
        context.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityDestroyed(activity: Activity) {
                detachExpose(activity)
            }

            override fun onActivityPaused(activity: Activity) {
                handlePaused(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                handleResumed(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                attachExpose(activity)
            }
        })
    }

    /**
     * 记录曝光点，超过缓存条数，自动上报
     */
    fun recordExpose(context: Context?, key: String) {
        var exposeKey = key + "@" + System.currentTimeMillis()
        LogUtil.d("Exposed","--->Exposed ${exposeKey}")
        addExposeKey(exposeKey)
        if (getCount() ?: 0 >= tempCount) {
            report(context)
        }
    }


    /**
     * 上报
     */
    @Synchronized
    fun report(context: Context?) {
        //一分钟内，如果在reporting，将会abort内部report；超过一分钟则不判断reporting，直接开始report；
        if (System.currentTimeMillis() - lastReportTime < 60 * 1000
            && isReporting
        ) {
            return
        }
        if (getCount() ?: 0 <= 0) {
            return
        }
        val str = getExposeKeys()
        if (!TextUtils.isEmpty(str)) {
            isReporting = true
            lastReportTime = System.currentTimeMillis()
            // todo 上报数据
        }
    }


    /**
     * 定时检查上报
     */
    private fun triggerLoop() {
        timerHandler.removeCallbacksAndMessages(null)
        timerHandler.postDelayed(Runnable {
            report(null)
            triggerLoop()
        }, diffTime)
    }
}

/**
 * 拓展view 增加attachExposeKey 用于曝光
 * @param key 曝光key
 */
fun View.attachExposeKey(key: String?) {
    if (TextUtils.isEmpty(key))
        return
    val preKey = getTag(R.id.nf_expose_id)
    if (preKey is String) {
        attachListener.onViewDetachedFromWindow(this)
    }
    setTag(R.id.nf_expose_id, key)
    attachListener.onViewAttachedToWindow(this)
    removeOnAttachStateChangeListener(attachListener)
    addOnAttachStateChangeListener(attachListener)
}

fun RecyclerView.collectExpose() {
    scrollListener.triggerDellayedCollect(false)
}

/**
 * 内部View scroll监听对象
 */
private val scrollListener = DefaultViewScrollChangeListener()

/**
 * 内部ViewDetached监听对象
 */
private val attachListener = DefaultViewAttachStateChangeListener()

/**
 * 记录resumed状态下activity里面的所有的曝光view
 */
private val activeViews: MutableList<ConcurrentMap<String, View>> = ArrayList()

/**
 * 内部对象池 value的key是exposureKey,value的value是曝光的view
 */
private val viewMap: ConcurrentMap<Activity, ConcurrentMap<String, View>> = MapMaker()
    .weakKeys()
    .makeMap()

private val activityMap: ConcurrentMap<Activity, Int> = MapMaker()
    .weakKeys()
    .makeMap()

/**
 * 给decor view 设置监听
 */
private fun attachExpose(activity: Activity) {
    activity.window.decorView.viewTreeObserver.addOnScrollChangedListener(scrollListener)
    viewMap[activity] = MapMaker().weakValues().makeMap()
    activityMap[activity] = 0
}

/**
 * 给decor view 注销监听
 */
private fun detachExpose(activity: Activity) {
    activity.window.decorView.viewTreeObserver.removeOnScrollChangedListener(scrollListener)
    viewMap.remove(activity)
}

/**
 * 处理resumed
 */
fun handleResumed(activity: Activity?) {
    if (activity == null) return
    val temp = viewMap[activity]
    if (temp != null) {
        activeViews.add(temp)
    }
    if (activityMap.containsKey(activity)) {
        var count = activityMap[activity] ?: 0
        activityMap[activity] = ++count
        if (count > 1) {
            scrollListener.triggerCollect()
        }
    }
}

/**
 * 处理paused
 */
fun handlePaused(activity: Activity?) {
    if (activity == null) return
    val temp = viewMap[activity]
    if (temp != null) {
        activeViews.remove(temp)
    }
}

/**
 * 监听滑动
 */
private class DefaultViewScrollChangeListener : ViewTreeObserver.OnScrollChangedListener {
    /**
     * 主线程handler
     */
    private val handler = Handler(Looper.getMainLooper())

    /**
     * 检测view runnable
     */
    private val collectRunnable = object : Runnable {
        override fun run() {
            collect(false)
        }

        //滑动列表里面的view ignoreLastVisible为false
        fun collect(ignoreLastVisible: Boolean) {
            if (activeViews.isEmpty()) {
                return
            }
            val itr = activeViews.flatMap { it.entries }.iterator()
            while (itr.hasNext()) {
                val item = itr.next()
                val tag = item.key
                val view = item.value
                if (view != null) {
                    if (view.isAttachedToWindow) {
                        var context = view.context
                        if (context !is Activity) {
                            context = (context as? ContextWrapper)?.baseContext
                        }
                        //可见面积超过view的一半上报
                        val visible = !ViewVisibleUtils.isHalfShade(view)
                        if (ignoreLastVisible) {
                            if (visible) {
                                ExposeManager.recordExpose(context, tag)
                            }
                        } else {
                            val lastVisible = view.getTag(R.bool.nf_last_visiable)
                            if (visible != lastVisible && visible) {
                                ExposeManager.recordExpose(context, tag)
                            }
                        }
                        view.setTag(R.bool.nf_last_visiable, visible)
                    } else {
                        view.setTag(R.bool.nf_last_visiable, false)
                    }
                }
            }
        }
    }

    override fun onScrollChanged() {
        handler.removeCallbacks(collectRunnable)
        handler.postDelayed(collectRunnable, 300)
    }

    //TODO 后期需要优化不可滑动页面曝光收集 2020/09/10 由于现在的所有曝光都是可滑动的列表故注释一下代码可以暂时解决问题
    // 1. 由于滑动的界面会执行-onScrollChanged - onResume/triggerCollect
    // 2. 改造方案： 后期可以暴露一个静态界面上报的方法
    //不可滑动页面曝光收集
    fun triggerCollect() {
        collectRunnable.collect(true)
//        handler.postDelayed(Runnable {
//            collectRunnable.collect(true)
//        }, 1000)
    }

    fun triggerDellayedCollect(ignoreLastVisible: Boolean) {
        handler.postDelayed(Runnable {
            collectRunnable.collect(ignoreLastVisible)
        }, 1000)
    }
}

/**
 * 内部ViewDetached监听类
 */
private class DefaultViewAttachStateChangeListener : View.OnAttachStateChangeListener {

    override fun onViewDetachedFromWindow(v: View) {
        v.setTag(R.bool.nf_last_visiable, false)
        val context = v.context
        if (context !is Activity) {
            return
        }
        val iterator = viewMap[context]?.iterator() ?: return
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.value == v) {
                iterator.remove()
            }
        }
    }

    override fun onViewAttachedToWindow(v: View) {
        v.setTag(R.bool.nf_last_visiable, false)
        val tag = v.getTag(R.id.nf_expose_id)
        val context = v.context
        if (tag !is String || TextUtils.isEmpty(tag) || context !is Activity) {
            return
        }
        viewMap[context]?.set(tag, v)
    }
}
