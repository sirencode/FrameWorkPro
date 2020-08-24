package com.syh.framework.http.model


/**
 * Created by shenyonghe on 2020/8/23.
 */
data class HttpResultBean(
    var request: String = "",
    var status: String ,
    var msg: String ,
    val data: Any
)