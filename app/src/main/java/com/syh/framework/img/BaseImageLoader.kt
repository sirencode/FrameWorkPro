package com.syh.framework.img

import android.content.Context
import android.widget.ImageView

/**
 * Created by shenyonghe on 2020/6/18.
 */
interface BaseImageLoader {
    fun init(context: Context)

    fun showImage(target: ImageView, url: String, callback: ImageLoadCallback?, imgConfig: ImageConfig)

    fun loadBitmap(url: String, width: Int, height: Int, callback: ImageLoadCallback)
}