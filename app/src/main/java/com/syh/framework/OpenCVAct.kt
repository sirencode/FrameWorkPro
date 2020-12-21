package com.syh.framework


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.syh.framework.http.model.OpenCvImgBean
import com.syh.framework.list.OpenCVImgVB
import com.syh.framework.util.BaseActivity
import com.syh.framework.util.OpenCVUtil
import kotlinx.android.synthetic.main.activity_opencv.*
import org.opencv.osgi.OpenCVNativeLoader
import java.io.File


/**
 * Created by shenyonghe on 2020/4/9.
 */
class OpenCVAct : BaseActivity() {

    val items = ArrayList<Any>()
    var adapter = MultiTypeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opencv)
        OpenCVNativeLoader().init()
        init()
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun init() {
        tv_start_check_img.setOnClickListener {
            checkPermission()
        }
        adapter.register(OpenCVImgVB(this){
            adapter.notifyDataSetChanged()
        })
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter.items = items
    }

    private fun checkPermission() {
        var list = buildMessyIms()
        items.clear()
        var imgs = mutableListOf<OpenCvImgBean>()
        for (s in list) {
            imgs.add(OpenCvImgBean(s, ""))
        }
        items.addAll(imgs)
        adapter.notifyDataSetChanged()
        for (s in list) {
            OpenCVUtil.checkImg(s) { tidy, time->
                runOnUiThread {
                    var result = if (tidy) "tidy" else "messy"
                    imgs[list.indexOf(s)].result = "检测结果：${result}\n耗时：${time}毫秒"
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun buildMessyIms():List<String>{
        var list = mutableListOf<String>()
        var file = File("/storage/emulated/0/Pictures/WeiXin/")
        var files = file.listFiles()
        for (file in files) {
            list.add(file.absolutePath)
        }
        return list
    }

}