package com.syh.framework


/**
 * Created by shenyonghe on 2020/9/20.
 */
data class ExposeBean(var exposedKey: String, var extra: String = "") {
    override fun toString(): String {
        return "{'exposedKey':'${exposedKey}','extra':'${extra}'}"
    }
}