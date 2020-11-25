package com.syh.framework.view.shape

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import com.syh.framework.R

/**
 *
 * description:
 *
 * Created by ethan on 2019-09-05.
 */
class Shape constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int = 0) {
    /**
     * 填充颜色
     */
    var solidColor = 0
    /**
     * 圆角
     */
    var corner = 0f

    /**
     * 描边
     */
    var strokeColor = 0

    /**
     * 描边宽度
     */
    var strokeWidth = 0f

    /**
     * 间隔宽度
     */
    var dashWidth = 0f

    /**
     * 间隔多少
     */
    var dashGap = 0f

    /**
     * 渐变开始颜色
     */
    var startColor = 0

    /**
     * 渐变结束颜色
     */
    var endColor = 0
    /**
     * 渐变方向
     */
    var orientation = 0

    init {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.ShapeView, defStyleAttr, 0)
        solidColor = a.getColor(R.styleable.ShapeView_baseSolidColor, 0)
        corner = a.getDimension(R.styleable.ShapeView_baseCorner, 0f)
        strokeColor = a.getColor(R.styleable.ShapeView_baseStrokeColor, 0)
        strokeWidth = a.getDimension(R.styleable.ShapeView_baseStrokeWidth, 0f)
        dashWidth = a.getDimension(R.styleable.ShapeView_baseStrokeDashWidth, 0f)
        dashGap = a.getDimension(R.styleable.ShapeView_baseStrokeDashGap, 0f)
        startColor = a.getColor(R.styleable.ShapeView_baseGradientStartColor, 0)
        endColor = a.getColor(R.styleable.ShapeView_baseGradientEndColor, 0)
        orientation = a.getInt(R.styleable.ShapeView_baseGradientOrientation, 0)
        a.recycle()
    }

    fun setToView(target: View) {
        lateinit var drawable: GradientDrawable
        if (startColor != 0 && endColor != 0) {
            //渐变效果
            val colors = IntArray(2)
            colors[0] = startColor
            colors[1] = endColor
            var direction: GradientDrawable.Orientation
            when (orientation) {
                0 -> direction = GradientDrawable.Orientation.TOP_BOTTOM
                1 -> direction = GradientDrawable.Orientation.TR_BL
                2 -> direction = GradientDrawable.Orientation.RIGHT_LEFT
                3 -> direction = GradientDrawable.Orientation.BR_TL
                4 -> direction = GradientDrawable.Orientation.BOTTOM_TOP
                5 -> direction = GradientDrawable.Orientation.BL_TR
                6 -> direction = GradientDrawable.Orientation.LEFT_RIGHT
                7 -> direction = GradientDrawable.Orientation.TL_BR
                else -> direction = GradientDrawable.Orientation.TOP_BOTTOM
            }
            drawable = GradientDrawable(direction, colors)
        } else {
            //非渐变
            drawable = GradientDrawable()
            drawable.setColor(solidColor)
        }
        drawable.cornerRadius = corner
        drawable.setStroke(strokeWidth.toInt(), strokeColor, dashWidth, dashGap)
        target.background = drawable
    }

}