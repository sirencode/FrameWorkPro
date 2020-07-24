package com.syh.framework.img

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.request.target.Target
import com.syh.framework.R
import com.syh.framework.expand.dp

/**
 * Created by shenyonghe on 2020/6/17.
 */
class ImageConfig(builder: Builder) {
    val loadResId = -1;
    val failedResId = -1;
    val emptyResId = -1;
    val scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
    val width = 0;
    val height = 0;
    val roundingParams = 0;

    companion object{
        var imageConfig1 = ImageConfig(Builder()
                .loadingRes(R.mipmap.a)
                .failedRes(R.mipmap.a)
                .emptyRes(R.mipmap.a)
                .size(100.dp,200.dp)
                .scaleType(ImageView.ScaleType.FIT_XY)
        )
    }

    class Builder {
        private var loadingResId = 0
        private var failedResId = 0
        private var emptyResId = 0
        private var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
        private var roundingParams = 0
        private var width = Target.SIZE_ORIGINAL
        private var height = Target.SIZE_ORIGINAL

        fun loadingRes(@DrawableRes loadingResId: Int): Builder {
            this.loadingResId = loadingResId
            return this
        }

        fun failedRes(@DrawableRes failedResId: Int): Builder {
            this.failedResId = failedResId
            return this
        }

        fun emptyRes(@DrawableRes emptyResId: Int): Builder {
            this.emptyResId = emptyResId
            return this
        }

        /**
         * 设置图片放缩类型
         *
         * @param scaleType
         * @return
         */
        fun scaleType(scaleType: ImageView.ScaleType): Builder {
            this.scaleType = scaleType
            return this
        }

        fun size(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            return this
        }

        /**
         * 设置圆角/圆环参数 (如果是圆形, 使用 [.asCircle] 方法)
         *
         * @param params
         * @return
         */
        fun roundingParams(params: Int): Builder {
            roundingParams = params
            return this
        }

        fun build(): ImageConfig {
            return ImageConfig(this)
        }
    }
}