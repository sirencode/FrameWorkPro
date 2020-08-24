package com.syh.framework.expand

import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.EditText

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
    if (flag) visible() else gone()
}

inline fun EditText.hintSize(hintText: String, dpSize: Int) {
    val ss = SpannableString(hintText)
    val ass = AbsoluteSizeSpan(dpSize, true)
    ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    hint = SpannedString(ss)
}