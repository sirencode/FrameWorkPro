package com.syh.framework.view

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller
import android.view.View

/**
 * Created by shenyonghe on 2020/8/12.
 */
class TopSmoothScroller constructor(context: Context) :
    androidx.recyclerview.widget.LinearSmoothScroller(context) {
    var positionOffset = 0

    override fun getHorizontalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START // 将子view与父view顶部对齐
    }

    override fun calculateDyToMakeVisible(view: View?, snapPreference: Int): Int {
        return super.calculateDyToMakeVisible(view, snapPreference) + positionOffset
    }
}