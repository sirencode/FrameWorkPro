package com.syh.framework


import android.Manifest
import android.os.Bundle
import com.syh.framework.expand.showToast
import com.syh.framework.rxpermission.RxPermissions
import com.syh.framework.util.BaseActivity
import kotlinx.android.synthetic.main.activity_opencv.*
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.osgi.OpenCVNativeLoader


/**
 * Created by shenyonghe on 2020/4/9.
 */
class OpenCVAct : BaseActivity() {
//
    val image_shape = 200
    val median_kernel = 5
    val template_scale = 0.2
    val template_shape = (image_shape * template_scale).toInt()
    val method = Imgproc.TM_SQDIFF_NORMED
    val threshold = 0.1
    val delay_weight = 0.5
    var dist_threshold = 0.15
    val count_threshold = 30000
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opencv)
        OpenCVNativeLoader().init()
        init()
    }

    private fun init() {
        var list = mutableListOf<String>()
        list.add("/storage/emulated/0/Pictures/WeiXin/mmexport1607917525958.jpg")
        list.add("/storage/emulated/0/Pictures/WeiXin/mmexport1607917542075.jpg")
        list.add("/storage/emulated/0/Pictures/WeiXin/mmexport1607917521142.jpg")
        tv_start_check_img.setOnClickListener {
            RxPermissions(this).request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ).subscribe { granted ->
                if (granted) {
                    checkImg(list[2])
                }
            }
        }
    }

    private fun checkImg(path: String) {
        Thread(Runnable {
            var start = System.currentTimeMillis()
            var originalImg = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE)
            if (originalImg.empty()) {
                runOnUiThread {
                    showToast("检测结果：读取图片异常")
                }
                return@Runnable
            }
            var resizeImg = Mat()
            var sz = Size(image_shape.toDouble(), image_shape.toDouble())
            //resize
            Imgproc.resize(originalImg, resizeImg, sz)
            println("checkImg==>> resize ${System.currentTimeMillis() - start}")
            // 设置中值滤波
            Imgproc.medianBlur(originalImg, resizeImg, median_kernel)
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
            println("checkImg==>> matchTemplate ${System.currentTimeMillis() - start}")
            var res1Count = 0
            var res2Count = 0
            for (i in 0 until res1.rows()) {
                for (j in 0 until res1.cols()) {
//                    if (res1.get(i, j)[0] <= threshold) res1Count++
                }
            }

            println("checkImg==>> res1Count ${System.currentTimeMillis() - start}")

//            for (i in 0 until res2.rows()) {
//                for (j in 0 until res2.cols()) {
//                    if (res2.get(i, j)[0] <= threshold) res2Count++
//                }
//            }
//            var tmp1 = Mat()
//            Imgproc.threshold(res1, tmp1, threshold, 255.toDouble(), Imgproc.THRESH_BINARY or Imgproc.THRESH_OTSU)
//            Core.countNonZero(tmp1)

            println("checkImg==>> res2Count ${System.currentTimeMillis() - start}")

            var count = res1Count + res2Count
            var distValue = dist.get(0, 0)[0]
            if (count > count_threshold) {
                distValue *= delay_weight
            }
            var result = if (distValue < dist_threshold) "tidy" else "messy"
            println("checkImg==>> all ${System.currentTimeMillis() - start}")
            runOnUiThread {
                showToast("检测结果：${result} ==> ${System.currentTimeMillis() - start}")
            }
        }).start()
    }

}