package com.syh.framework.view.state_layout

import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import com.syh.framework.R

/**
 * Created by shenyonghe on 2020/7/23.
 */
class StateLayoutManager(var context: Context, @LayoutRes var contendLayId: Int, @LayoutRes var emptyLayId: Int, @LayoutRes var errorLayId: Int,  @LayoutRes var loadLayId: Int = R.layout.view_base_load) {
    var showHideViewListener: OnShowHideViewListener? = null
    var emptyClick: OnEmptyClick? = null
    var errorClick: OnErrorClick? = null
    var rootFrameLayout: StateFrameLayout = StateFrameLayout(context)

    init {
        var layParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rootFrameLayout.layoutParams = layParams
        rootFrameLayout.setBackgroundColor(Color.WHITE)
    }

    fun getRootView(): View {
        rootFrameLayout.setLayManager(this)
        return rootFrameLayout
    }

    fun showLoading() {
        rootFrameLayout.showLoading()
    }

    fun showContent() {
        rootFrameLayout.showContent()
    }

    fun showEmpty() {
        if (emptyLayId != 0) {
            rootFrameLayout.showEmpty()
        }
    }

    fun showError() {
        if (errorLayId != 0) {
            rootFrameLayout.showError()
        }
    }

    interface OnShowHideViewListener {
        fun onShowView(view: View, id: Int)
        fun onHideView(view: View, id: Int)
    }

    interface OnEmptyClick {
        fun onEmptyClick()
    }

    interface OnErrorClick {
        fun onErrorClick()
    }
}