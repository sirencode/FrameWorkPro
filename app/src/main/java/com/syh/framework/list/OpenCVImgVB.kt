package com.syh.framework.list

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.syh.framework.R
import com.syh.framework.expand.showToast
import com.syh.framework.http.model.OpenCvImgBean
import com.syh.framework.img.ImageConfig
import com.syh.framework.img.ImageLoaderHelp
import com.syh.framework.util.OpenCVUtil
import kotlinx.android.synthetic.main.item_opencv_list.view.*

/**
 * 作者：created by Bamboo on 2019-10-14
 * 邮箱：bzy601638015@126.com
 */
class OpenCVImgVB(val activity: Activity,val listener: () -> Unit) : ItemViewBinder<OpenCvImgBean, OpenCVImgVB.OpenCVImg>() {
    override fun onBindViewHolder(holder: OpenCVImg, item: OpenCvImgBean) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): OpenCVImg {
        return OpenCVImg(inflater.inflate(R.layout.item_opencv_list, parent, false))
    }

    inner class OpenCVImg(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: OpenCvImgBean) = with(itemView) {
            ImageLoaderHelp.displayImg(iv_img, "file://${item.img}", null, ImageConfig.imageConfig1)
            tv_result.text = item.result
            itemView.setOnClickListener {
                OpenCVUtil.checkImg(item.img) { tidy, time ->
                    activity.runOnUiThread {
                        var result = if (tidy) "tidy" else "messy"
                        activity.showToast("检测结果：${result} ==> ${time}")
                        item.result = "检测结果：${result}\n耗时：${time}毫秒"
                        listener()
                    }
                }
            }
        }
    }
}