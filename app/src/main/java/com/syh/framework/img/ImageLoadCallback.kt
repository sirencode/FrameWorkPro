package com.syh.framework.img

import android.graphics.Bitmap

/**
 * Created by shenyonghe on 2020/6/18.
 */
interface ImageLoadCallback {
    fun onSuccess(bitmap: Bitmap)

    fun onFailed()
}