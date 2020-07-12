package com.syh.framework.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.text.style.ReplacementSpan

/**
 * created by 申永鹤(shenyonghe@theduapp.com) on 2020/7/12
 **/
class VerticalSpan(var fontSize:Int, var textColor:Int, var bgColor:Int,var bgR:Int) : ReplacementSpan() {

    var mSize:Int = 0

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        var text = text!!.subSequence(start, end)
        val p: Paint = getCustomTextPaint(paint)
        mSize = paint.measureText(text, start, end).toInt()
        return p.measureText(text.toString()).toInt()
    }

    private fun getCustomTextPaint(srcPaint: Paint): Paint {
        val paint = TextPaint(srcPaint)
        paint.textSize = fontSize.toFloat() //设定字体大小, sp转换为px

        paint.color = textColor
        paint.isAntiAlias = true
        return paint
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        var text = text!!.subSequence(start, end)
        val p = getCustomTextPaint(paint)
        val fm = p.fontMetricsInt
        val oval =
            RectF(x, y + paint.ascent(), (x + mSize * 0.833).toFloat(), y + paint.descent())
        paint.color = bgColor
        canvas.drawRoundRect(oval, bgR.toFloat(), bgR.toFloat(), paint) //绘制圆角矩形，第二个参数是x半径，第三个参数是y半径

        canvas.drawText(
            text.toString(),
            x,
            y - ((y + fm.descent + y + fm.ascent) / 2 - (bottom + top) / 2).toFloat(),
            p
        )
    }
}