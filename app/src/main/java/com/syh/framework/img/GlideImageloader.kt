package com.syh.framework.img

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.Nullable
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * Created by shenyonghe on 2020/6/18.
 */
class GlideImageloader : BaseImageLoader {
    var context: Context? = null;

    override fun init(context: Context) {
        this.context = context
    }

    override fun showImage(target: ImageView, url: String, callback: ImageLoadCallback?, imgConfig: ImageConfig) {
        Glide.with(context!!)
                .asBitmap()
                .load(handleUrl(url))
                .apply(loadOptions(imgConfig))
                .listener(object : RequestListener<Bitmap?> {
                    override fun onLoadFailed(@Nullable e: GlideException?, o: Any, target: Target<Bitmap?>, b: Boolean): Boolean {
                        callback?.onFailed()
                        return false
                    }

                    override fun onResourceReady(bitmap: Bitmap?, o: Any, target: Target<Bitmap?>, dataSource: DataSource, b: Boolean): Boolean {
                        if (bitmap != null) {
                            callback?.onSuccess(bitmap)
                        }
                        return false
                    }
                })
                .into(target)
    }

    override fun loadBitmap(url: String, width: Int, height: Int, callback: ImageLoadCallback) {
        TODO("Not yet implemented")
    }

    private fun handleUrl(uri: String): Any {
        if (TextUtils.isEmpty(uri)) {
            return uri
        }
        return when {
            uri.startsWith("file") -> {
                uri
            }
            uri.startsWith("res") -> {
                uri.replace("res:///", "").toInt()
            }
            else -> {
                GlideUrl(uri)
            }
        }
    }

    private fun loadOptions(config: ImageConfig): RequestOptions {
        var requestOptions = RequestOptions()
        if (config == null) return requestOptions
        if (config.loadResId > 0) requestOptions.placeholder(config.loadResId)
        if (config.failedResId > 0) requestOptions.error(config.failedResId)
        if (config.roundingParams > 0) {

        }

        if (config.width > 0) {
            requestOptions.override(config.width, config.height)
        }

        requestOptions.centerCrop()
        return requestOptions
    }
}