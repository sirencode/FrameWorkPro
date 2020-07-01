package com.syh.framework.img

import android.content.Context
import android.widget.ImageView
import com.syh.framework.MyApp

/**
 * Created by shenyonghe on 2020/6/18.
 */
class ImageLoaderHelp {

    companion object {
        var imageLoader: BaseImageLoader? = null

        fun init(context: Context) {
            imageLoader = GlideImageloader()
            imageLoader!!.init(context)
        }

        fun displayImg(tartget: ImageView, url: String, callback: ImageLoadCallback?, config: ImageConfig) {
            if (imageLoader == null) {
                init(MyApp.getApplication())
            }
            imageLoader?.showImage(tartget, url, callback, config)
        }
    }
}