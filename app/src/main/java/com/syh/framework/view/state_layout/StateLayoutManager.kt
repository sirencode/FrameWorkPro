package com.syh.framework.view.state_layout

import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.syh.framework.R
import com.syh.framework.expand.gone
import com.syh.framework.expand.visible

/**
 * Created by shenyonghe on 2020/7/23.
 */
class StateLayoutManager(private var rootFrameLay: FrameLayout?, var context: Context,
                         @LayoutRes var contentLayId: Int, @LayoutRes var emptyLayId: Int = R.layout.view_common_empty,
                         @LayoutRes var errorLayId: Int = R.layout.view_common_error, @LayoutRes var loadLayId: Int = R.layout.view_base_load) {

    companion object {
        const val LAYOUT_LOADING_ID = 1
        const val LAYOUT_CONTENT_ID = 2
        const val LAYOUT_ERROR_ID = 3
        const val LAYOUT_EMPTY_ID = 4
    }

    private var layoutSparseArray = SparseArray<View>()
    private var emptyView: View? = null
    private var errorView: View? = null
    private var loadView: View? = null

    var showViewListener: OnShowViewListener? = null
    var emptyClick: OnEmptyClick? = null
    var errorClick: OnErrorClick? = null
    var loading: Boolean = false

    init {
        if (rootFrameLay == null) {
            var layParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            rootFrameLay = FrameLayout(context)
            rootFrameLay?.layoutParams = layParams
            rootFrameLay?.setBackgroundColor(Color.WHITE)
        }
        var content = LayoutInflater.from(context).inflate(contentLayId, rootFrameLay, false)
        layoutSparseArray.put(LAYOUT_CONTENT_ID, content)
        rootFrameLay!!.addView(content)
    }

    fun isLoading(): Boolean {
        return loading
    }

    fun showLoading() {
        if (loadLayId != 0 && !isLoading()) showById(LAYOUT_LOADING_ID)
    }

    fun showContent() {
        showById(LAYOUT_CONTENT_ID)
    }

    fun showEmpty() {
        if (emptyLayId != 0) showById(LAYOUT_EMPTY_ID)
    }

    fun showError() {
        if (errorLayId != 0) showById(LAYOUT_ERROR_ID)
    }

    fun getRootView(): View {
        return rootFrameLay!!
    }

    private fun showById(id: Int) {
        if (layoutSparseArray.get(id) == null) {
            when (id) {
                LAYOUT_EMPTY_ID -> {
                    if (emptyView == null) {
                        emptyView = LayoutInflater.from(context).inflate(emptyLayId, rootFrameLay, false)
                        emptyView!!.setOnClickListener {
                            emptyClick?.let {
                                it.onEmptyClick()
                            }
                        }
                    }
                    layoutSparseArray.put(id, emptyView)
                    rootFrameLay?.addView(emptyView)
                }

                LAYOUT_ERROR_ID -> {
                    if (errorView == null) {
                        errorView = LayoutInflater.from(context).inflate(errorLayId, rootFrameLay, false)
                        errorView!!.setOnClickListener {
                            errorClick?.let {
                                it.onErrorClick()
                            }
                        }
                    }
                    layoutSparseArray.put(id, errorView)
                    rootFrameLay?.addView(errorView)
                }

                LAYOUT_LOADING_ID -> {
                    if (loadView == null) {
                        loadView = LayoutInflater.from(context).inflate(loadLayId, rootFrameLay, false)
                    }
                    layoutSparseArray.put(id, loadView)
                    rootFrameLay?.addView(loadView)
                }
            }
        }
        showHideView(id)
    }

    private fun showHideView(id: Int) {
        for (index in 0 until layoutSparseArray.size()) {
            var key = layoutSparseArray.keyAt(index)
            var view = layoutSparseArray.valueAt(index)
            if (key == id || key == LAYOUT_CONTENT_ID) {
                view.visible()
            } else {
                view.gone()
            }
            loading = (id == LAYOUT_LOADING_ID)
            showViewListener?.onShowView(view, id)
        }

        when (id) {
            LAYOUT_LOADING_ID -> {
                removeViewById(LAYOUT_ERROR_ID)
                removeViewById(LAYOUT_EMPTY_ID)
            }
            LAYOUT_CONTENT_ID -> {
                removeViewById(LAYOUT_ERROR_ID)
                removeViewById(LAYOUT_EMPTY_ID)
                removeViewById(LAYOUT_LOADING_ID)
            }

            LAYOUT_ERROR_ID -> {
                removeViewById(LAYOUT_EMPTY_ID)
                removeViewById(LAYOUT_LOADING_ID)
            }

            LAYOUT_EMPTY_ID -> {
                removeViewById(LAYOUT_ERROR_ID)
                removeViewById(LAYOUT_LOADING_ID)
            }
        }
    }

    private fun removeViewById(id: Int) {
        var view = layoutSparseArray.get(id)
        view?.let {
            rootFrameLay?.removeView(it)
            layoutSparseArray.remove(id)
        }
    }

    interface OnShowViewListener {
        fun onShowView(view: View, id: Int)
    }

    interface OnEmptyClick {
        fun onEmptyClick()
    }

    interface OnErrorClick {
        fun onErrorClick()
    }
}