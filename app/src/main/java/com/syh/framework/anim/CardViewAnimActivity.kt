package com.syh.framework.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.syh.framework.R
import com.syh.framework.expand.dp
import com.syh.framework.util.BaseActivity
import com.syh.framework.util.UIParameter
import kotlinx.android.synthetic.main.activity_card_anim.*
import kotlin.math.abs

/**
 * Author: shenyonghe
 * Date: 12/8/20
 * Version: v1.0
 * Description:
 * Modification History:
 * Date Author Version Description
 * ------------------------------------
 * 12/8/20 shenyonghe v1.0
 * Why & What is modified:
 **/
class CardViewAnimActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_anim)
        var list = mutableListOf<String>()
        list.add("#808080")
        list.add("#997A3D")
        list.add("#ffffff")
        list.add("#F23D3D")
        list.add("#000000")
        tv_start.setOnClickListener {
            addView(list)
        }
    }

    private fun addView(list: List<String>) {
        ll_card.removeAllViews()
        ll_card.translationY = 200.dp.toFloat()
        var w = UIParameter.getWidth(this) - 80.dp
        for (s in list) {
            var view = LayoutInflater.from(this).inflate(R.layout.item_card_anim, ll_card, false)
            var w = w
            view.layoutParams.width = w
            view.setBackgroundColor(Color.parseColor(s))
            ll_card.addView(view)
        }
        (ll_card.getChildAt(0).layoutParams as LinearLayout.LayoutParams).leftMargin = 20.dp
        ll_card.post {
            for ((index, item) in list.withIndex()) {
                var child = ll_card.getChildAt(index)
                child.translationX = -child.x -200.dp
            }
            startAnimation(list)
        }
    }

    private fun startAnimation(list: List<String>) {
        var anima = ObjectAnimator.ofFloat(ll_card, "translationY", 200.dp.toFloat(), 0f)
        anima.duration = 200
        anima.start()
        anima.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                for ((index, item) in list.withIndex()) {
                    var child = ll_card.getChildAt(index)
                    var anima = ObjectAnimator.ofFloat(child, "translationX", child.translationX, 0f)
                    anima.duration = abs(child.translationX/5).toLong()
                    anima.interpolator = LinearInterpolator()
                    anima.start()
                }
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
    }
}