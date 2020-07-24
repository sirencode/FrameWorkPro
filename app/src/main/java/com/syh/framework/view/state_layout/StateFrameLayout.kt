package com.syh.framework.view.state_layout

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.widget.FrameLayout

/**
 * Created by shenyonghe on 2020/7/23.
 */
class StateFrameLayout : FrameLayout {

    companion object {
        const val LAYOUT_LOADING_ID = 1
        const val LAYOUT_CONTENT_ID = 2
        const val LAYOUT_ERROR_ID = 3
        const val LAYOUT_NETWORK_ERROR_ID = 4
        const val LAYOUT_EMPTY_DATA_ID = 5
    }

    private var layoutSparseArray = SparseArray<View>()
    private var stateLayoutManager:StateLayoutManager? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrRes: AttributeSet) : super(context, attrRes)
    constructor(context: Context, attrRes: AttributeSet, defStyleAttr: Int) : super(context, attrRes, defStyleAttr)


}