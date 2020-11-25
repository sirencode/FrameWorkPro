package com.syh.framework.view.shape

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Created by zhouying18 on 2019-09-12.
 */

class ShapeLinearLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var shape: Shape = Shape(context, attrs)

    init {
        shape.setToView(this@ShapeLinearLayout)
    }
}