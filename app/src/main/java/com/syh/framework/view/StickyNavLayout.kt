package com.syh.framework.view

import android.content.Context
import android.graphics.Color
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import com.syh.framework.expand.dp

/**
 * Created by shenyonghe on 2020/8/12.
 */
class StickyNavLayout : LinearLayout, NestedScrollingParent {

    lateinit var headerView: View
    lateinit var footerView: AnimatorView
    lateinit var childView: androidx.recyclerview.widget.RecyclerView
    var isRunAnim: Boolean = false
    lateinit var parentHelper: NestedScrollingParentHelper
    var mLinster: OnStartActivityListener? = null

    companion object {
        var maxWidth = 0
        val DRAG = 2
    }

    fun setOnStartActivity(listener: OnStartActivityListener) {
        mLinster = listener
    }

    interface OnStartActivityListener {
        fun onStart()
    }

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
        headerView = View(context)
        headerView.setBackgroundColor(Color.parseColor("#ffffff"))
        footerView = AnimatorView(context)
        footerView.setBackgroundColor(Color.parseColor("#ffffff"))
        maxWidth = 120.dp
        parentHelper = NestedScrollingParentHelper(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        orientation = HORIZONTAL
        if (getChildAt(0) is androidx.recyclerview.widget.RecyclerView) {
            childView = getChildAt(0) as androidx.recyclerview.widget.RecyclerView
            val layoutParams = LayoutParams(maxWidth, LayoutParams.MATCH_PARENT)
            addView(headerView, 0, layoutParams)
            addView(footerView, childCount, layoutParams)
            // 左移
            scrollBy(maxWidth, 0)
            childView.setOnTouchListener(OnTouchListener { v, event -> // 保证动画状态中 子view不能滑动
                isRunAnim
            })
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        childView?.let {
            childView.layoutParams.width = measuredWidth
        }
    }


    inner class ProgressAnimation() : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            scrollBy(((maxWidth - scrollX) * interpolatedTime).toInt(), 0)
            if (interpolatedTime == 1f) {
                isRunAnim = false
                footerView.setRelease()
            }
        }

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            duration = 300
            interpolator = AccelerateInterpolator()
        }

        init {
            isRunAnim = true
        }
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        parentHelper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return target is androidx.recyclerview.widget.RecyclerView && !isRunAnim
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return false
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        parent.requestDisallowInterceptTouchEvent(true)
        // dx>0 往左滑动 dx<0往右滑动
        //System.out.println("dx:" + dx + "=======getScrollX:" + getScrollX() + "==========canScrollHorizontally:" + !ViewCompat.canScrollHorizontally(target, -1));
        // dx>0 往左滑动 dx<0往右滑动
        //System.out.println("dx:" + dx + "=======getScrollX:" + getScrollX() + "==========canScrollHorizontally:" + !ViewCompat.canScrollHorizontally(target, -1));
        val hiddenLeft = dx > 0 && scrollX < maxWidth && !ViewCompat.canScrollHorizontally(target, -1)
        val showLeft = dx < 0 && !ViewCompat.canScrollHorizontally(target, -1)
        val hiddenRight = dx < 0 && scrollX > maxWidth && !ViewCompat.canScrollHorizontally(target, 1)
        val showRight = dx > 0 && !ViewCompat.canScrollHorizontally(target, 1)
        if (hiddenLeft || showLeft || hiddenRight || showRight) {
            scrollBy(dx / DRAG, 0)
            consumed[0] = dx
        }

        if (hiddenRight || showRight) {
            footerView.setRefresh(dx / DRAG)
        }

        // 限制错位问题

        // 限制错位问题
        if (dx > 0 && scrollX > maxWidth && !ViewCompat.canScrollHorizontally(target, -1)) {
            scrollTo(maxWidth, 0)
        }
        if (dx < 0 && scrollX < maxWidth && !ViewCompat.canScrollHorizontally(target, 1)) {
            scrollTo(maxWidth, 0)
        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        // 当RecyclerView在界面之内交给它自己惯性滑动
        return scrollX != maxWidth
    }

    override fun onStopNestedScroll(target: View) {
        parentHelper.onStopNestedScroll(target)
        // 如果不在RecyclerView滑动范围内
        // 如果不在RecyclerView滑动范围内
        if (maxWidth != scrollX) {
            startAnimation(ProgressAnimation())
        }

        if (scrollX > maxWidth + maxWidth / 2 && mLinster != null) {
            mLinster!!.onStart()
        }
    }

    override fun getNestedScrollAxes(): Int {
        return 0
    }

    override fun scrollTo(x: Int, y: Int) {
        var currentX = x
        if (currentX < 0) {
            currentX = 0
        }
        if (currentX > maxWidth * 2) {
            currentX = maxWidth * 2
        }
        super.scrollTo(currentX, y)
    }

}