package com.syh.framework

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.TextView
import com.syh.framework.expand.dp
import com.syh.framework.util.BaseActivity
import kotlinx.android.synthetic.main.activity_shape.*

/**
 * Created by shenyonghe on 2020/4/9.
 */
class ShapeAct : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape)
        val tvAct = TextView(this).apply {
            text = "tag.text"
            textSize = 10f
            setTextColor(Color.parseColor("#000000"))
        }
        var gd = GradientDrawable()
        var fillColor = Color.parseColor("#ffffff")
        gd.setColor(fillColor)
        gd.cornerRadius = 2.dp.toFloat()
        tvAct.background = gd
        fl_shape.addView(tvAct)
    }

}