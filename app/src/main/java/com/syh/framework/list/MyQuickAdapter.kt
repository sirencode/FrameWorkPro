package com.syh.framework.list

import android.app.Activity
import android.content.Intent
import android.widget.TextView
import com.syh.framework.R
import com.syh.framework.expose.attachExposeKey
import com.syh.framework.test.SPActivity
import java.util.*

/**
 * Created bg shenyonghe on 2018/6/8.
 */
class MyQuickAdapter(data: List<ItemDemo?>?) : BaseQuickAdapter<ItemDemo?>(map, data) {
    companion object {
        private var map: HashMap<Int, Int>? = null

        init {
            map = HashMap()
            map!![0] = R.layout.item_test_list
            map!![1] = R.layout.item_test2_list
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun convert(holder: BaseViewHolder?, s: ItemDemo?) {
        var context =  holder!!.itemView.context as Activity
        holder!!.itemView.attachExposeKey(s!!.name)
        val title = holder!!.getView<TextView>(R.id.tv_title)
        title.text = s!!.name
        holder!!.itemView.setOnClickListener {
            context.startActivity(Intent(context, SPActivity::class.java))
        }
    }

}