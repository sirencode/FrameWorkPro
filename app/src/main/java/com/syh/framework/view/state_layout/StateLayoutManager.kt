package com.syh.framework.view.state_layout

import android.view.ViewStub
import com.syh.framework.R

/**
 * Created by shenyonghe on 2020/7/23.
 */
class StateLayoutManager(var contendLayId:Int) {
    var loadLayId = R.layout.view_base_load
    var netWorkErrorVs: ViewStub? = null
    var emptyDataVs: ViewStub? = null
    var errorVs: ViewStub? = null


}