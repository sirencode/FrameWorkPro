package com.syh.framework.util.spwithtime

/**
 * Created by shenyonghe on 2020/7/1.
 */
data class SPData(var value: Any? = null, var age: Int? = Int.MAX_VALUE, var type: Int? = SpTimeHelper.DAY, var time: Long? = System.currentTimeMillis()) {}
