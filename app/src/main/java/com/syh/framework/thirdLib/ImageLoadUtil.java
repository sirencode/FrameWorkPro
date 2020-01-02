package com.syh.framework.thirdLib;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.syh.framework.util.LogUtil;
import com.syh.framework.util.UIParameter;

/**
 * Created bg shenyonghe on 2018/5/21.
 */
public class ImageLoadUtil {

    private static final String TAG = "ImageLoadUtil";

    /**
     * 简单加载图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadImageView(Context context, String path, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(path) || imageView == null) {
            LogUtil.e(TAG,TAG + "参数错误");
            return;
        }
        Glide.with(context).load(path).into(imageView);
    }

    /**
     * 指定宽高
     *
     * @param context
     * @param path
     * @param widthDp
     * @param heightDp
     * @param imageView
     */
    public static void loadFixSizeIV(Context context, String path, int widthDp, int heightDp, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions()
                        .fitCenter()
                        .override(UIParameter.dp2px(context, widthDp), UIParameter.dp2px(context, heightDp)))
                .into(imageView);
    }

    /**
     * 占位符与错误图片
     *
     * @param context
     * @param path
     * @param widthDp
     * @param heightDp
     * @param imageView
     * @param holder
     * @param error
     */
    public static void loadIVWithErrorAndHolder(Context context, String path, int widthDp, int heightDp, int holder, int error, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions()
                        .placeholder(holder)
                        .error(error).fitCenter()
                        .override(UIParameter.dp2px(context, widthDp), UIParameter.dp2px(context, heightDp)))
                .into(imageView);
    }

}
