package com.syh.framework.view.state_layout

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syh.framework.BaseLazyFragment
import com.syh.framework.R

/**
 * Created by shenyonghe on 2020/7/27.
 */
class StateFragment : BaseLazyFragment() {
    lateinit var rootView: View
    lateinit var stateLayoutManager: StateLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        stateLayoutManager = StateLayoutManager(context!!, R.layout.fragment_state_layout)
        with(stateLayoutManager, {
            emptyClick = object : StateLayoutManager.OnEmptyClick {
                override fun onEmptyClick() {
                    showContent()
                }
            }
            errorClick = object : StateLayoutManager.OnErrorClick {
                override fun onErrorClick() {
                    showContent()
                }
            }
        })
        stateLayoutManager.init(null)
        rootView = stateLayoutManager.getRootView()
        initView()
        return rootView
    }

    private fun initView() {
        rootView.findViewById<View>(R.id.btn_show_empty).setOnClickListener {
            stateLayoutManager.showEmpty()
        }
        rootView.findViewById<View>(R.id.btn_show_error).setOnClickListener {
            stateLayoutManager.showError()
        }
        rootView.findViewById<View>(R.id.btn_show_load).setOnClickListener {
            stateLayoutManager.showLoading()
            showLateContent()
        }
    }

    private fun showLateContent() {
        Handler().postDelayed({
            stateLayoutManager.showContent()
        }, 2000)
    }
}