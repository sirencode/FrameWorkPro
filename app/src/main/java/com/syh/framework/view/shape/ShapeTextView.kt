package com.syh.framework.view.shape

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 *
 * description:
 *
 * Created by ethan on 2019-09-05.
 */
class ShapeTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    var shape: Shape = Shape(context, attrs)

    init {
        shape.setToView(this@ShapeTextView)
    }
}