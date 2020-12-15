package com.syh.framework.util

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

/**
 * Author: shenyonghe
 * Date: 12/15/20
 * Version: v1.0
 * Description:
 * Modification History:
 * Date Author Version Description
 * ------------------------------------
 * 12/15/20 shenyonghe v1.0
 * Why & What is modified:
 **/
object OpenCVUtil {

    val image_shape = 200
    val median_kernel = 5
    val template_scale = 0.2
    val template_shape = (image_shape * template_scale).toInt()
    val method = Imgproc.TM_SQDIFF_NORMED
    val threshold = 0.1
    val delay_weight = 0.5
    val dist_threshold = 0.15
    val count_threshold = 30000

    fun checkImg(path: String, listener: (tidy: Boolean, time: Long) -> Unit) {
        Thread(Runnable {
            var start = System.currentTimeMillis()
            // 暂时取三色道 效率 灰度：IMREAD_GRAYSCALE
            var originalImg = Imgcodecs.imread(path)
            if (originalImg.empty()) {
                println("checkImg==>> 检测结果：读取图片异常")
                return@Runnable
            }
            var resizeImg = Mat()
            var sz = Size(image_shape.toDouble(), image_shape.toDouble())
            //resize
            Imgproc.resize(originalImg, resizeImg, sz)
            // 设置中值滤波
            Imgproc.medianBlur(resizeImg, Mat(), median_kernel)
            var rect = Rect(0, 0, template_shape, template_shape)
            var rectRT = Rect(160, 0, template_shape, template_shape)
            var left_top = Mat(resizeImg, rect)
            var right_top = Mat(resizeImg, rectRT)
            var dist = Mat()
            var res1 = Mat()
            var res2 = Mat()
            Imgproc.matchTemplate(left_top, right_top, dist, method)
            Imgproc.matchTemplate(resizeImg, left_top, res1, method)
            Imgproc.matchTemplate(resizeImg, right_top, res2, method)
            var tmp1 = Mat()
            var tmp2 = Mat()
            Imgproc.threshold(res1, tmp1, threshold, 1.toDouble(), Imgproc.THRESH_BINARY_INV)
            Imgproc.threshold(res2, tmp2, threshold, 1.toDouble(), Imgproc.THRESH_BINARY_INV)
            var res1Count = Core.countNonZero(tmp1)
            var res2Count = Core.countNonZero(tmp2)
            var count = res1Count + res2Count
            var distValue = dist.get(0, 0)[0]
            if (count > count_threshold) {
                distValue *= delay_weight
            }
            var result = if (distValue < dist_threshold) "tidy" else "messy"
            println("检测结果：${result} ==> ${System.currentTimeMillis() - start}")
            listener(distValue < dist_threshold, System.currentTimeMillis() - start)
        }).start()
    }
}