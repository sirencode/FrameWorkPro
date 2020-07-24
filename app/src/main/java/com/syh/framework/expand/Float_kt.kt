package com.syh.framework.expand

import android.content.res.Resources

/**
 * Created by shenyonghe on 2020/7/24.
 */
val Float.dp: Float
    get() = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )