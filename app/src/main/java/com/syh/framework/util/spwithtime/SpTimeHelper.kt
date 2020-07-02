package com.syh.framework.util.spwithtime

import android.content.Context
import android.content.SharedPreferences
import com.alibaba.fastjson.JSONObject
import java.util.*

/**
 * Created by shenyonghe on 2020/7/1.
 */
class SpTimeHelper(var context: Context, fileName: String = context.packageName) {

    companion object{
        const val MONTH = 1
        const val DAY = 2
        const val HOUR = 3
        const val MINUTE = 4
    }

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    init {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }

    fun put(key: String, data: SPData) {
        data?.let {
            editor!!.putString(key, JSONObject.toJSONString(data)).commit()
        }
    }

    fun getValue(key: String,defaultObject: Any?): Any? {
        var jsonStr = sharedPreferences!!.getString(key, "")
        jsonStr.isNotEmpty().let {
            var o: SPData? = JSONObject.parseObject(jsonStr,SPData::class.java)
            o?.let {
                var date = Date(o.time!!)
                var now = Date(System.currentTimeMillis())
                if (o.type == MONTH && date.year == now.year  && (now.month - date.month) < o.age!!) {
                    return o.value
                } else if (o.type == DAY && date.year == now.year && date.month == now.month && (now.day - date.day) < o.age!!) {
                    return o.value
                } else if (o.type == HOUR && date.year == now.year && date.month == now.month && now.day == date.day && (now.hours - date.hours) < o.age!!) {
                    return o.value
                } else if (o.type == MINUTE && date.year == now.year && date.month == now.month && now.day == date.day && now.hours == date.hours && (now.minutes - date.minutes) < o.age!!) {
                    return o.value
                }
            }
        }
        return defaultObject;
    }
    /**
     * 移除某个key值已经对应的值
     */
    fun remove(key: String?) {
        editor!!.remove(key)
        editor!!.commit()
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        editor!!.clear()
        editor!!.commit()
    }
}