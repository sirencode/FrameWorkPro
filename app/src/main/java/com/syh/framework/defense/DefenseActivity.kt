package com.syh.framework.defense

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.syh.framework.R
import com.syh.framework.util.BaseActivity
import kotlinx.android.synthetic.main.activity_defense.*
import om.syh.framework.accessibilitydefense.DefenseAccessibilityDelegate

class DefenseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defense)
        initView()
    }

    fun initView() {
        val delegate = DefenseAccessibilityDelegate()
        btn_cannot_listen.setAccessibilityDelegate(delegate)
        btn_can_listen.setOnClickListener { Toast.makeText(this, "btn_can_listen", Toast.LENGTH_LONG).show() }
        btn_cannot_listen.setOnClickListener { Toast.makeText(this, "btn_cannot_listen", Toast.LENGTH_LONG).show() }
        btn_cannot_sim_listen.setOnClickListener { Toast.makeText(this, "btn_cannot_sim_listen", Toast.LENGTH_LONG).show() }
        btn_ui_long_click.setOnClickListener { longTimeClick() }
    }

    fun longTimeClick() {
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
