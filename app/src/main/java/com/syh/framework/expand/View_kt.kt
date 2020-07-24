package com.syh.framework.expand

import android.view.View

/**
 * Created by shenyonghe on 2020/7/24.
 */

inline fun View.gone() {
    visibility = View.GONE
}

inline fun View.visible() {
    visibility = View.VISIBLE
}

inline fun View.isVisible(flag: Boolean) {
    visibility = if (flag) View.VISIBLE else View.GONE
}