package com.syh.framework.view.state_layout

import android.os.Bundle
import android.os.Handler
import com.syh.framework.R
import com.syh.framework.expand.showToast
import com.syh.framework.util.BaseActivity
import kotlinx.android.synthetic.main.activity_state_layout.*

/**
 * Created by shenyonghe on 2020/7/26.
 */
class StateLayActivity : BaseActivity() {
    lateinit var stateLayoutManager: StateLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateLayoutManager = StateLayoutManager(this, R.layout.activity_state_layout,R.layout.view_common_empty, R.layout.view_common_error)
        with(stateLayoutManager, {
            emptyClick = object : StateLayoutManager.OnEmptyClick {
                override fun onEmptyClick() {
                    showToast("onEmptyClick")
                }
            }
            errorClick = object : StateLayoutManager.OnErrorClick {
                override fun onErrorClick() {
                    showToast("OnErrorClick")
                }
            }
        })
        setContentView(stateLayoutManager.getRootView())
        initView()
    }

    private fun initView() {
        btn_show_content.setOnClickListener { stateLayoutManager.showContent() }
        btn_show_empty.setOnClickListener {
            stateLayoutManager.showEmpty()
            showContent()
        }
        btn_show_error.setOnClickListener {
            stateLayoutManager.showError()
        }
        btn_show_load.setOnClickListener {
            stateLayoutManager.showLoading()
            showContent()
        }
    }

    private fun showContent() {
        Handler().postDelayed({
            stateLayoutManager.showContent()
        }, 3000)
    }
}