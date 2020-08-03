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
class StateLayoutManager(var context: Context, @LayoutRes var contentLayId: Int) {

    @LayoutRes
    var emptyLayId: Int = R.layout.view_common_empty

    @LayoutRes
    var errorLayId: Int = R.layout.view_common_error

    @LayoutRes
    var loadLayId: Int = R.layout.view_base_load

    @LayoutRes
    var noNetLayId: Int = R.layout.view_no_net_error

    @LayoutRes
    var defaultRequestLayId: Int = R.layout.view_base_load
    private var rootFrameLay: FrameLayout? = null
    private var layoutSparseArray = SparseArray<View>()
    private var emptyView: View? = null
    private var errorView: View? = null
    private var loadView: View? = null
    private var noNetView: View? = null
    private var defaultRequestView: View? = null

    private val contentLayKey = 1
    private val loadLayKey = 2
    private val errorLayKey = 3
    private val emptyLayKey = 4
    private val noNetWorkLayKey = 5
    private val defaultRequestLayKey = 6

    var showViewListener: OnShowViewListener? = null
    var emptyClick: OnEmptyClick? = null
    var errorClick: OnErrorClick? = null
    var loading: Boolean = false

    fun init(layout: FrameLayout?) {
        rootFrameLay = layout
        if (rootFrameLay == null) {
            var layParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            rootFrameLay = FrameLayout(context)
            rootFrameLay?.layoutParams = layParams
            rootFrameLay?.setBackgroundColor(Color.WHITE)
        }
        var content = LayoutInflater.from(context).inflate(contentLayId, rootFrameLay, false)
        layoutSparseArray.put(contentLayKey, content)
        rootFrameLay!!.addView(content)
    }

    fun isLoading(): Boolean {
        return loading
    }

    fun showLoading() {
        if (loadLayId != 0 && !isLoading()) showById(loadLayKey)
    }

    fun showContent() {
        showById(contentLayKey)
    }

    fun showEmpty() {
        if (emptyLayId != 0) showById(emptyLayKey)
    }

    fun showError() {
        if (errorLayId != 0) showById(errorLayKey)
    }

    fun showNoNetView() {
        if (noNetLayId != 0) showById(noNetWorkLayKey)
    }

    fun showDefaultRequestView() {
        if (defaultRequestLayId != 0) showById(defaultRequestLayKey)
    }

    fun getRootView(): View {
        return rootFrameLay!!
    }

    private fun showById(id: Int) {
        if (layoutSparseArray.get(id) == null) {
            when (id) {
                emptyLayKey -> {
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

                errorLayKey -> {
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

                noNetWorkLayKey -> {
                    if (noNetView == null) {
                        noNetView = LayoutInflater.from(context).inflate(noNetLayId, rootFrameLay, false)
                        noNetView!!.setOnClickListener {
                            errorClick?.let {
                                it.onErrorClick()
                            }
                        }
                    }
                    layoutSparseArray.put(id, noNetView)
                    rootFrameLay?.addView(noNetView)
                }

                loadLayKey -> {
                    if (loadView == null) {
                        loadView = LayoutInflater.from(context).inflate(loadLayId, rootFrameLay, false)
                    }
                    layoutSparseArray.put(id, loadView)
                    rootFrameLay?.addView(loadView)
                }

                defaultRequestLayKey -> {
                    if (defaultRequestView == null) {
                        defaultRequestView = LayoutInflater.from(context).inflate(defaultRequestLayId, rootFrameLay, false)
                        defaultRequestView!!.setOnClickListener {
                            emptyClick?.let {
                                it.onEmptyClick()
                            }
                        }
                    }
                    layoutSparseArray.put(id, defaultRequestView)
                    rootFrameLay?.addView(defaultRequestView)
                }
            }
        }
        showHideView(id)
    }

    private fun showHideView(id: Int) {
        for (index in 0 until layoutSparseArray.size()) {
            var key = layoutSparseArray.keyAt(index)
            var view = layoutSparseArray.valueAt(index)
            if (key == id || key == contentLayKey) {
                view.visible()
            } else {
                view.gone()
            }
            loading = (id == loadLayKey || id == defaultRequestLayKey)
            showViewListener?.onShowView(view, id)
        }

        when (id) {
            contentLayKey -> {
                removeViewById(loadLayKey)
                removeViewById(errorLayKey)
                removeViewById(emptyLayKey)
                removeViewById(noNetWorkLayKey)
                removeViewById(defaultRequestLayKey)
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