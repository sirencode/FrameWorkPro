package com.syh.framework.frame_animation

import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Handler
import android.widget.ImageView
import com.syh.framework.MyApp
import com.syh.framework.R
import java.lang.ref.SoftReference

/**
 * Created by shenyonghe on 2020/7/22.
 */
class AnimationsContainerO(var fps: Int = 58, var resId: Int = R.array.loading_anim) {
    companion object {
        val instance: AnimationsContainerO by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AnimationsContainerO()
        }
    }

    fun createProgressDialogAnim(imageView: ImageView):FramesSequenceAnimation{
        return  FramesSequenceAnimation(imageView,getData(),fps)
    }

    var mBitmap: Bitmap? = null
    lateinit var mBitmapOptions: BitmapFactory.Options

    inner class FramesSequenceAnimation(var imageView: ImageView, var frame: IntArray, var fps: Int) {
        var handler = Handler()
        var index = 0
        var softImg = SoftReference<ImageView>(imageView)
        var shouldRun = false
        var running = false
        var loop = false
        var delayMillis = 1000 / fps
        var stoppedListener: OnAnimationStoppedListener? = null

        init {
            imageView.setImageResource(frame[0])

            // 当图片大小类型相同时进行复用，避免频繁GC
            if (Build.VERSION.SDK_INT >= 11) {
                val bmp = (imageView.drawable as BitmapDrawable).bitmap
                val width = bmp.width
                val height = bmp.height
                val config = bmp.config
                mBitmap = Bitmap.createBitmap(width, height, config)
                mBitmapOptions = BitmapFactory.Options()
                //设置Bitmap内存复用
                mBitmapOptions.inBitmap = mBitmap //Bitmap复用内存块，类似对象池，避免不必要的内存分配和回收
                mBitmapOptions.inMutable = true //解码时返回可变Bitmap
                mBitmapOptions.inSampleSize = 1 //缩放比例
            }
        }

        fun getNext(): Int {
            index += 1
            if (index >= frame.size) index = 0
            return frame[index]
        }

        @Synchronized
        fun start() {
            shouldRun = true
            if (!loop) index = -1
            if (running) return

            val runnable: Runnable = object : Runnable {
                override fun run() {
                    var imageView: ImageView? = softImg.get()
                    if (!shouldRun || imageView == null || (!loop && (index == frame.size - 1))) {
                        running = false
                        stoppedListener?.let {
                            it.onAnimationStopped()
                        }
                        return
                    }
                    running = true
                    //新开线程去读下一帧
                    handler.postDelayed(this, delayMillis.toLong())
                    if (imageView.isShown) {
                        val imageRes = getNext()
                        if (mBitmap != null) { // so Build.VERSION.SDK_INT >= 11
                            var bitmap: Bitmap? = null
                            try {
                                bitmap = BitmapFactory.decodeResource(imageView.resources, imageRes, mBitmapOptions)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap)
                            } else {
                                imageView.setImageResource(imageRes)
                                mBitmap?.recycle()
                                mBitmap = null
                            }
                        } else {
                            imageView.setImageResource(imageRes)
                        }
                    }
                }
            }

            handler.post(runnable)
        }

        @Synchronized fun stop() {
            running = false
        }
    }

    interface OnAnimationStoppedListener {
        fun onAnimationStopped()
    }

    private fun getData(): IntArray {
        val array: TypedArray = MyApp.getApplication().resources.obtainTypedArray(resId)
        val len = array.length()
        val intArray = IntArray(array.length())
        for (i in 0 until len) {
            intArray[i] = array.getResourceId(i, 0)
        }
        array.recycle()
        return intArray
    }
}