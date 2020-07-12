package com.syh.framework.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

/**
 * created by 申永鹤(shenyonghe@theduapp.com) on 2020/7/7
 **/
class ExpandLayout : RelativeLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private var viewHeight: Int = 0
    private var isExpand: Boolean = true
    private var lock: Boolean = false
    private var animationDuration: Long = 300L

    private fun init() {
        setViewDimensions()
    }

    fun initExpand(expand: Boolean) {
        isExpand = expand
    }

    private fun setViewDimensions() {
        post {
            if (viewHeight <= 0) {
                viewHeight = measuredHeight
            }
            setViewHeight(this, if (isExpand) viewHeight else 0)
        }
    }

    private fun animateToggle() {
        val heightAnimation = if (isExpand) ValueAnimator.ofFloat(0f, viewHeight.toFloat()) else ValueAnimator.ofFloat(viewHeight.toFloat(), 0f)
        heightAnimation.duration = animationDuration / 2
        heightAnimation.startDelay = animationDuration / 2
        heightAnimation.addUpdateListener { animation ->
            val value = (animation.animatedValue as Float).toInt()
            setViewHeight(this, value)
            if (value == viewHeight || value == 0) {
                lock = false
            }
        }

        heightAnimation.start()
        lock = true
    }

    fun getExpand(): Boolean {
        return isExpand
    }

    private fun collapse() {
        isExpand = false
        animateToggle()
    }

    private fun expand() {
        isExpand = true
        animateToggle()
    }

    fun toggleExpand() {
        if (lock) {
            return
        }
        if (isExpand) {
            collapse()
        } else {
            expand()
        }
    }

    companion object {
        fun setViewHeight(view: View, height: Int) {
            val params: ViewGroup.LayoutParams = view.layoutParams
            params.height = height
            view.requestLayout()
        }
    }
}