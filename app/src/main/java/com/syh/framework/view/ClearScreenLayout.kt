package com.syh.framework.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import java.util.*
import kotlin.math.abs

/**
 * Created by shenyonghe on 2020/8/18.
 */
class ClearScreenLayout : FrameLayout {

    private var mDownX // 手指按下的x轴位置
            = 0
    private var mDownY // 手指按下的y轴位置
            = 0
    private var startX // 滑动开始时x轴偏移量
            = 0
    private var translateX // 当前x轴偏移量
            = 0
    private var endX // 动画结束时x轴偏移量
            = 0
    private var ifCleared // 是否已清屏
            = false
    private var mVelocityTracker // 计算滑动速度
            : VelocityTracker? = null
    private lateinit var mAnimator // 动画
            : ValueAnimator

    private var leftSlide = true // true-左滑清屏 false-右滑清屏

    private val slideClearListener // 清屏监听器
            : OnSlideClearListener? = null
    private val listClearViews = ArrayList<View>() // 需要清除的View


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    fun init() {
        val view = View(context)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view.isClickable = true
        addView(view, 0)

        mVelocityTracker = VelocityTracker.obtain()
        mAnimator = ValueAnimator.ofFloat(0f, 1.0f).setDuration(200)
        mAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            translateChild((translateX + value * (endX - translateX)).toInt())
        })
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (ifCleared && translateX == 0) {
                    slideClearListener?.onRestored()
                    ifCleared = !ifCleared
                } else if (!ifCleared && Math.abs(translateX) == width) {
                    slideClearListener?.onCleared()
                    ifCleared = !ifCleared
                }
            }
        })
    }

    fun setSlideDirection(direction:SlideDirection) {
        leftSlide = direction === SlideDirection.LEFT
    }

    fun addClearViews(vararg views: View?) {
        for (cell in views) {
            if (!listClearViews.contains(cell)) {
                listClearViews.add(cell!!)
            }
        }
    }

    /**
     * 移除需要清屏的view
     */
    fun removeClearViews(vararg views: View?) {
        for (cell in views) {
            listClearViews.remove(cell)
        }
    }

    /**
     * 移除所有需要清屏的view
     */
    fun removeAllClearViews() {
        listClearViews.clear()
    }

    /**
     * 清屏（有动画）
     */
    fun clearWithAnim() {
        endX = if (ifCleared) {
            return
        } else if (leftSlide) {
            -width
        } else {
            width
        }
        mAnimator.start()
    }

    /**
     * 清屏（无动画）
     */
    fun clearWithoutAnim() {
        endX = if (ifCleared) {
            return
        } else if (leftSlide) {
            -width
        } else {
            width
        }
        translateChild(endX)
        ifCleared = true
    }

    /**
     * 还原（有动画）
     */
    fun restoreWithAnim() {
        if (!ifCleared) {
            return
        }
        endX = 0
        mAnimator.start()
    }

    /**
     * 还原（无动画）
     */
    fun restoreWithoutAnim() {
        if (!ifCleared) {
            return
        }
        endX = 0
        translateChild(endX)
        ifCleared = false
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    mDownX = it.x.toInt()
                    mDownY = it.y.toInt()
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_MOVE -> {
                    val endX = it.x.toInt()
                    val endY = it.y.toInt()
                    val distanceX = abs(endX - mDownX)
                    val distanceY = abs(endY - mDownY)
                    if (distanceX >= distanceY) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val x = it.x.toInt()
            val y = it.y.toInt()
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    mDownX = x
                    mDownY = y
                }
                MotionEvent.ACTION_MOVE -> if (!mAnimator.isRunning && Math.abs(x - mDownX) > Math.abs(y - mDownY)) {
                    startX = translateX
                    if (x - mDownX < -10) {
                        if (leftSlide && !ifCleared || !leftSlide && ifCleared) {
                            return true
                        }
                    } else if (x - mDownX > 10) {
                        if (leftSlide && ifCleared || !leftSlide && !ifCleared) {
                            return true
                        }
                    }
                }
            }
        }

        return super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mVelocityTracker!!.addMovement(event)
        val x = event!!.x.toInt()
        val offsetX = x - mDownX
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                translateChild(startX + offsetX)
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (translateX != 0) {
                    mVelocityTracker!!.computeCurrentVelocity(10)
                    endX = if (Math.abs(offsetX) > width / 3 ||
                            mVelocityTracker!!.xVelocity > 20 && !leftSlide && !ifCleared ||
                            mVelocityTracker!!.xVelocity > 20 && leftSlide && ifCleared ||
                            mVelocityTracker!!.xVelocity < -20 && !leftSlide && ifCleared ||
                            mVelocityTracker!!.xVelocity < -20 && leftSlide && !ifCleared) {
                        if (ifCleared) {
                            0
                        } else if (leftSlide) {
                            -width
                        } else {
                            width
                        }
                    } else {
                        startX
                    }
                }
                mAnimator.start()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun translateChild(translate:Int) {
        if (leftSlide && translate > 0 || !leftSlide && translate < 0) {
            return
        }
        translateX = translate
        for (i in listClearViews.indices) {
            listClearViews[i].translationX = translate.toFloat()
        }
    }

    internal interface OnSlideClearListener {
        fun onCleared()
        fun onRestored()
    }

    enum class SlideDirection {
        LEFT, RIGHT
    }
}