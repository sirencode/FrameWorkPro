package com.syh.framework.list

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.LinearLayoutManager
import com.syh.framework.R
import com.syh.framework.expose.ExposeManager
import com.syh.framework.util.BaseActivity
import com.syh.plugin.annotation.Cost
import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

/**
 * Created bg shenyonghe on 2018/6/7.
 */
class ListActivity : BaseActivity() {
    private var adapter: MyQuickAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initRV()
        ExposeManager.report(this)
    }

    @Cost
    private fun initRV() {
        refreshLay.setOnRefreshListener(OnRefreshListener { refreshList() })
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = MyQuickAdapter(data)
        recycler_view.adapter = adapter
    }

    private val data: MutableList<ItemDemo?>
        private get() {
            val list: MutableList<ItemDemo?> = ArrayList()
            for (i in 0..19) {
                val itemDemo = ItemDemo()
                itemDemo.name = "shen$i"
                itemDemo.keyNum = i.toString() + "key"
                list.add(itemDemo)
            }
            return list
        }
    private val refreshData: MutableList<ItemDemo?>
        private get() {
            val list = data
            list.removeAt(4)
            list.removeAt(5)
            list.removeAt(6)
            return list
        }

    private fun refreshList() {
        adapter!!.setDatas(refreshData)
        refreshLay!!.isRefreshing = false
    }
}