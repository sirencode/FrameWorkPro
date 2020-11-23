package com.syh.framework.util.net

import com.syh.framework.util.net.enums.NetworkState
import java.lang.reflect.Method

internal class NetworkStateReceiverMethod(
    var any: Any? = null,
    var method: Method? = null,
    var monitorFilter: Array<NetworkState>? = null
)