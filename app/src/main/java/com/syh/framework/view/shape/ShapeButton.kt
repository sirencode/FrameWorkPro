package com.syh.framework.view.shape

import android.content.Context
import android.util.AttributeSet
import android.widget.Button

/**
 *
 * description:
 *
 * Created by ethan on 2019-09-05.
 */
class ShapeButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {

    var shape: Shape = Shape(context, attrs)

    init {
        shape.setToView(this@ShapeButton)
    }
}