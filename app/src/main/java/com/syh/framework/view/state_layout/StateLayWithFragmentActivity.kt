package com.syh.framework.view.state_layout

import android.os.Bundle
import android.os.Handler
import com.syh.framework.R
import com.syh.framework.util.BaseActivity
import kotlinx.android.synthetic.main.activity_state_layout.*

/**
 * Created by shenyonghe on 2020/7/26.
 */
class StateLayWithFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateLayoutManager = StateLayoutManager(findViewById(android.R.id.content),this, R.layout.activity_state_layout)
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
            showLaterContent()
        }
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.lay_frame, StateFragment())
        ft.commitAllowingStateLoss()
    }

    private fun showLaterContent() {
        Handler().postDelayed({
            stateLayoutManager.showContent()
        }, 2000)
    }
}