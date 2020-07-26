package com.syh.framework.view.state_layout

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.syh.framework.expand.gone
import com.syh.framework.expand.visible

/**
 * Created by shenyonghe on 2020/7/23.
 */
class StateFrameLayout : FrameLayout {

    companion object {
        const val LAYOUT_LOADING_ID = 1
        const val LAYOUT_CONTENT_ID = 2
        const val LAYOUT_ERROR_ID = 3
        const val LAYOUT_EMPTY_ID = 4
    }

    private var layoutSparseArray = SparseArray<View>()
    private var stateLayoutManager: StateLayoutManager? = null
    private var emptyView: View? = null
    private var errorView: View? = null
    private var loadView: View? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrRes: AttributeSet) : super(context, attrRes)
    constructor(context: Context, attrRes: AttributeSet, defStyleAttr: Int) : super(context, attrRes, defStyleAttr)

    private fun addLayResId(@LayoutRes layoutResId: Int, id: Int) {
        var resView = LayoutInflater.from(context).inflate(layoutResId, this, false)
        if (id == LAYOUT_LOADING_ID) {
            resView.isClickable = false
        }
        layoutSparseArray.put(id, resView)
        addView(resView)
    }

    fun setLayManager(manager: StateLayoutManager) {
        stateLayoutManager = manager
        addAllLayoutToRootLayout()
    }

    private fun addAllLayoutToRootLayout() {
        stateLayoutManager?.let {
            if (it.contendLayId != 0) {
                addLayResId(it.contendLayId, LAYOUT_CONTENT_ID)
            }
            showContent()
        }
    }

    private fun showHideView(id: Int) {
        for (index in 0 until layoutSparseArray.size()) {
            var key = layoutSparseArray.keyAt(index)
            var view = layoutSparseArray.valueAt(index)
            if (key == id) {
                view.visible()
            } else {
                view.gone()
            }
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
            removeView(it)
            layoutSparseArray.remove(id)
        }
    }

    fun isLoading(): Boolean {
        var loadView = layoutSparseArray.get(LAYOUT_LOADING_ID)
        return if (loadView == null) {
            false
        } else {
            loadView.visibility == View.VISIBLE
        }
    }

    fun showLoading() {
        showById(LAYOUT_LOADING_ID)
    }

    fun showContent() {
        layoutSparseArray.get(LAYOUT_CONTENT_ID)?.let { showHideView(LAYOUT_CONTENT_ID) }
    }

    fun showEmpty() {
        showById(LAYOUT_EMPTY_ID)
    }

    fun showError() {
        showById(LAYOUT_ERROR_ID)
    }

    private fun showById(id: Int) {
        if (layoutSparseArray.get(id) == null) {
            when(id) {
                LAYOUT_EMPTY_ID -> {
                    if (emptyView == null) {
                        emptyView = LayoutInflater.from(context).inflate(stateLayoutManager!!.emptyLayId, this, false)
                        emptyView!!.setOnClickListener {
                            stateLayoutManager?.emptyClick?.let {
                                it.onEmptyClick()
                            }
                        }
                    }
                    layoutSparseArray.put(id, emptyView)
                    addView(emptyView)
                }

                LAYOUT_ERROR_ID -> {
                    if (errorView == null) {
                        errorView = LayoutInflater.from(context).inflate(stateLayoutManager!!.errorLayId, this, false)
                        errorView!!.setOnClickListener {
                            stateLayoutManager?.errorClick?.let {
                                it.onErrorClick()
                            }
                        }
                    }
                    layoutSparseArray.put(id, errorView)
                    addView(errorView)
                }

                LAYOUT_LOADING_ID -> {
                    if (loadView == null) {
                        loadView = LayoutInflater.from(context).inflate(stateLayoutManager!!.loadLayId, this, false)
                    }
                    layoutSparseArray.put(id, loadView)
                    addView(loadView)
                }
            }
        }
        showHideView(id)
    }
}