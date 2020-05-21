package com.syh.framework.defense

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.syh.framework.R
import kotlinx.android.synthetic.main.activity_defense.*
import om.syh.framework.accessibilitydefense.DefenseAccessibilityDelegate

class DefenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defense)
        initView()
    }

    fun initView() {
        val delegate = DefenseAccessibilityDelegate()
        btn_cannot_listen.setAccessibilityDelegate(delegate)
        btn_can_listen.setOnClickListener {Toast.makeText(this,"btn_can_listen",Toast.LENGTH_LONG).show()}
        btn_cannot_listen.setOnClickListener {Toast.makeText(this,"btn_cannot_listen",Toast.LENGTH_LONG).show()}
        btn_cannot_sim_listen.setOnClickListener {Toast.makeText(this,"btn_cannot_sim_listen",Toast.LENGTH_LONG).show()}
    }

}
