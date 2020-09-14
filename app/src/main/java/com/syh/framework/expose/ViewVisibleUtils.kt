package com.syh.framework.expose

import android.graphics.Rect
import android.view.View

/**
 * Author: baozhiyuan
 * Time: 2020/9/3
 * Version:
 * Description: view可见性工具
 **/
object ViewVisibleUtils {

    /**
     * 是否可见
     */
    fun isVisible(view: View): Boolean {
        if (view.visibility != View.VISIBLE) {
            return false
        }
        val currentViewRect = Rect()
        val isCovered = view.getGlobalVisibleRect(currentViewRect)
        if (!isCovered) {
            return false
        }
        return currentViewRect.width() * currentViewRect.height() >= 1
    }

    /**
     * view是否遮挡/不可见面积超过一半
     *
     * @param view
     */
    fun isHalfShade(view: View): Boolean {
        if (view.visibility != View.VISIBLE) {
            return true
        }
        val currentViewRect = Rect()
        val isCovered = view.getGlobalVisibleRect(currentViewRect)
        if (!isCovered) {
            return true
        }
        return (currentViewRect.width() * currentViewRect.height() < view.measuredHeight * view.measuredWidth / 2)
    }

    /**
     * view是否遮挡
     *
     * @param view
     */
    fun isShade(view: View): Boolean {
        if (view.visibility != View.VISIBLE) {
            return true
        }
        val currentViewRect = Rect()
        val isCovered = view.getGlobalVisibleRect(currentViewRect)
        if (!isCovered) {
            return true
        }
        return (currentViewRect.width() * currentViewRect.height() <= view.measuredHeight * view.measuredWidth)
    }
}