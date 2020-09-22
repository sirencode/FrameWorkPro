package com.syh.framework.util

import android.app.ListActivity
import com.syh.asm.PathRecord
import com.syh.framework.MainActivity
import com.syh.framework.test.SPActivity

/**
 * Created by shenyonghe on 2020/9/22.
 */
object PageConfig {
    val map: MutableMap<String, String> = mutableMapOf()

    init {
        map[MainActivity::class.java.simpleName] = "首页"
        map[SPActivity::class.java.simpleName] = "SP页面"
        map[ListActivity::class.java.simpleName] = "列表页面"
    }

    fun matchRecord(list: List<PathRecord>): MutableList<PathRecord> {
        var resultList = mutableListOf<PathRecord>()
        list.forEach { record ->
            map[record.name]?.let {
                record.postName = it
            }
            resultList.add(record)
        }
        return resultList
    }
}