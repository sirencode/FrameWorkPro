package com.syh.framework.view.state_layout

import android.os.Bundle
import android.os.Handler
import com.syh.framework.R
import com.syh.framework.util.BaseActivity
import kotlinx.android.synthetic.main.activity_state_layout.*

/**
 * Created by shenyonghe on 2020/7/26.
 */
open class StateLayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateLayoutManager = StateLayoutManager(this, R.layout.activity_state_layout).apply {
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
        }
        stateLayoutManager.init(findViewById(android.R.id.content))
        initView()
    }

    private fun initView() {
        btn_show_empty.setOnClickListener {
            showEmpty()
        }
        btn_show_error.setOnClickListener {
            showError()
        }
        btn_show_load.setOnClickListener {
            showLoading()
            showLateContent()
        }

        btn_show_default.setOnClickListener {
            showDefault()
            showLateContent()
        }
        btn_show_no_net.setOnClickListener {
            showNoNet()
            showLateContent()
        }
    }

    private fun showLateContent() {
        Handler().postDelayed({
            showContent()
        }, 2000)
    }
}