package com.syh.framework.view.shape

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * Created by zhouying18 on 2019-09-12.
 */

class ShapeRelativeLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    var shape: Shape = Shape(context, attrs)

    init {
        shape.setToView(this@ShapeRelativeLayout)
    }
}