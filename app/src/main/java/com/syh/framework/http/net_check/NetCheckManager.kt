package com.syh.framework.http.net_check

import android.os.Build
import androidx.collection.SimpleArrayMap
import android.text.TextUtils
import android.util.*
import com.syh.framework.BuildConfig
import com.syh.framework.http.net_check.annotions.CheckNull
import com.syh.framework.http.net_check.annotions.NeedCheck
import java.lang.reflect.Array
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by shenyonghe on 2020/8/24.
 */
object NetCheckManager {
    private const val THREAD_COUNT = 2
    const val TAG = "NetCheckManager"
    private var singleES: ExecutorService? = null
    var open = true

    private fun needCheck(o: Any): Boolean {
        return o != null && open && (o.javaClass.isArray && !o.javaClass.isPrimitive
                && Array.getLength(o) > 0 && Array.get(o, 0) is NeedCheck
                || o is Collection<*> && !o.isEmpty() && o.toTypedArray()[0] is NeedCheck || o is NeedCheck)
    }

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
        if (obj is androidx.collection.SimpleArrayMap<*, *> && obj.isEmpty) {
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

    private fun check(o: Any): String {
        val checkMessage = StringBuilder()
        val oClass: Class<*> = o.javaClass
        val declaredFields = oClass.declaredFields
        for (declaredField in declaredFields) {
            if (declaredField.isAnnotationPresent(CheckNull::class.java)) {
                declaredField.isAccessible = true
                val paramClass = declaredField.type
                var value: Any?
                try {
                    value = declaredField[o]
                    val paramName = declaredField.name
                    if (isEmpty(value)) {
                        if (!TextUtils.isEmpty(checkMessage)) {
                            checkMessage.append(",")
                        }
                        checkMessage.append(paramName).append("->空").toString()
                    }
                    if (value != null && !paramClass.isPrimitive && value is NeedCheck) {
                        if (!TextUtils.isEmpty(checkMessage)) {
                            checkMessage.append(",")
                        }
                        checkMessage.append(check(value))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return checkMessage.toString()
    }

    fun checkValue(o: Any, request: String) {
        if (needCheck(o)) {
            if (singleES == null) {
                singleES = newFixedThreadPool(THREAD_COUNT)
            }
            singleES!!.execute {
                try {
                    val map: MutableMap<String, String> = HashMap()
                    if (o.javaClass.isArray && !o.javaClass.isPrimitive && Array.getLength(o) > 0
                        && Array.get(o, 0) is NeedCheck
                    ) {
                        for (i in 0 until Array.getLength(o)) {
                            val result = check(Array.get(o, i))
                            if (!TextUtils.isEmpty(result)) {
                                map[Array.get(o, i).javaClass.simpleName + "." + (i + 1)] = result
                            }
                        }
                    }
                    if (o is Collection<*> && !o.isEmpty() && o.toTypedArray()[0] is NeedCheck) {
                        for (i in o.indices) {
                            val result = check(o.toTypedArray()[i]!!)
                            if (!TextUtils.isEmpty(result)) {
                                map[o.toTypedArray()[i]!!.javaClass.simpleName + "." + (i + 1)] =
                                    result
                            }
                        }
                    }
                    if (o is NeedCheck) {
                        val result = check(o)
                        if (!TextUtils.isEmpty(result)) {
                            map[o.javaClass.simpleName] = result
                        }
                    }
                    if (map.isNotEmpty()) {
                        if (BuildConfig.DEBUG) {
                            NetCheckViewManager.updateLogMsg(request, map.toString())
                        }
                        // todo 上报
                        map["request"] = request
//                        NFLog.postCheckDataLog(map.toString())

//                        Timber.tag(TAG).d(map.toString())
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                } catch (e: Error) {
                }
            }
        }
    }
}