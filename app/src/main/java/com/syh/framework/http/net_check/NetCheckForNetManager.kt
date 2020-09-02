package com.syh.framework.http.net_check

import android.os.Build
import android.support.v4.util.SimpleArrayMap
import android.util.*
import com.syh.framework.http.net_check.model.NetCheckBean
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder
import java.lang.reflect.Array
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by shenyonghe on 2020/8/24.
 */
object NetCheckForNetManager {
    private const val THREAD_COUNT = 2
    const val TAG = "NetCheckForNetManager"
    private var singleES: ExecutorService? = null
    var open = true

    private val mCount = AtomicInteger(1)

    private val sThreadFactory = ThreadFactory { r ->
        val thread = Thread(r, "dataCheck:" + mCount.getAndIncrement())
        thread.priority = Thread.MIN_PRIORITY
        thread
    }

    @Synchronized
    fun newFixedThreadPool(nThreads: Int): ExecutorService {
        return ThreadPoolExecutor(
                nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(), sThreadFactory
        )
    }

    fun isEmpty(obj: Any): Boolean {
        if (obj == null) {
            return true
        }
        if (obj is CharSequence && obj.toString().trim().isEmpty()) {
            return true
        }
        if (obj.javaClass.isArray && Array.getLength(obj) == 0) {
            return true
        }
        if (obj is Collection<*> && obj.isEmpty()) {
            return true
        }
        if (obj is Map<*, *> && obj.isEmpty()) {
            return true
        }
        if (obj is SimpleArrayMap<*, *> && obj.isEmpty) {
            return true
        }
        if (obj is SparseArray<*> && obj.size() == 0) {
            return true
        }
        if (obj is SparseBooleanArray && obj.size() == 0) {
            return true
        }
        if (obj is SparseIntArray && obj.size() == 0) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj is SparseLongArray && obj.size() == 0) {
                return true
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj is LongSparseArray<*> && obj.size() == 0) {
                return true
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj is LongSparseArray<*>
                    && (obj as LongSparseArray<*>).size() == 0
            ) {
                return true
            }
        }
        return false
    }

    private fun checkParamNull(o: JSONObject, list: List<String>): Boolean {
        var obj = o
        try {
            for ((index, item) in list.withIndex()) {
                var value = obj.get(item)
                when {
                    isEmpty(o) -> {
                        return true
                    }
                    index == list.size - 1 -> {
                        return isEmpty(value)
                    }
                    value is JSONObject -> {
                        obj = value
                    }
                    value is JSONArray -> {
                        for (i in 0 until value.length()) {
                            var ob: JSONObject = value.get(i) as JSONObject
                            if (checkParamNull(ob, list.subList(index, list.size))) return true
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("$TAG====>${e.message}")
            return true
        }
        return false
    }

    fun checkValue(o: JSONObject, request: String, checkBean: NetCheckBean) {
        var result = StringBuilder()
        if (request.contains(checkBean.requestUrl) && checkBean.params.isNotEmpty()) {
            if (singleES == null) {
                singleES = newFixedThreadPool(THREAD_COUNT)
            }
            singleES!!.execute {
                try {
                    checkBean.params.forEach { param ->
                        if (param != null && param.trim().isNotEmpty()) {
                            if (checkParamNull(o, param.split("."))) result.append(param).append(",")
                        }
                    }
                    if (result.toString().isNotEmpty()) {
                        result.delete(result.length - 1, result.length)
                        println("$TAG====> 问题数据$result")
                        //todo 上报
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                } catch (e: Error) {
                }
            }
        }
    }
}