package com.syh.framework.view.shape

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 *
 * description:
 *
 * Created by ethan on 2019-09-06.
 */

class ShapeFrameLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var shape: Shape = Shape(context, attrs)

    init {
        shape.setToView(this@ShapeFrameLayout)
    }
}