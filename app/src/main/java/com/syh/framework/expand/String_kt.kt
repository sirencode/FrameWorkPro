package com.syh.framework.expand

import android.text.TextUtils

/**
 * Created by shenyonghe on 2020/6/10.
 */

fun String.equalsExt(str: String): Boolean {
    return TextUtils.equals(this, str)
}