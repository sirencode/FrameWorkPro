package com.syh.framework.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.syh.framework.R

/**
 * Created by shenyonghe on 2020/8/12.
 */
class AnimatorView : RelativeLayout {

    lateinit var path: Path
    lateinit var view: View
    lateinit var backPaint: Paint
    lateinit var layout: LinearLayout
    lateinit var textView: TextView
    var currentHeight: Int = 0
    var layHeight: Int = 0
    var layWidth: Int = 0
    var move: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, default: Int) : super(context, attrs, default) {
        init()
    }

    fun init() {
        path = Path()
        backPaint = Paint()
        backPaint.isAntiAlias = true
        backPaint.style = Paint.Style.FILL
        backPaint.color = Color.parseColor("#f3f3f3")

        view = View.inflate(context, R.layout.animator_hot, null)
        layout = view.findViewById(R.id.animator_ll)
        textView = view.findViewById(R.id.animator_text)
        addView(view)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        currentHeight = height
        layHeight = layout.height
        layWidth = layout.width
    }

    fun setRefresh(width:Int) {
        move += width
        if (move < 0) {
            move = 0
        } else if (move > StickyNavLayout.maxWidth){
            move = StickyNavLayout.maxWidth
        }
        view.layoutParams.width = move
        view.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
        textView.text =  if (move > StickyNavLayout.maxWidth/2) "释放查看" else "查看更多"
        requestLayout()
    }

    fun setRelease() {
        move = 0
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.reset()
        var marginTop = (currentHeight -layHeight) /2
        path.moveTo((move -layWidth).toFloat(), marginTop.toFloat())
        path.quadTo(0f,currentHeight/2.toFloat(), (move-layWidth).toFloat(), (layHeight + marginTop).toFloat())
        canvas?.drawPath(path,backPaint)
    }
}