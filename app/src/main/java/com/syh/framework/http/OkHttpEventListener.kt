package com.syh.framework.http

import com.syh.framework.util.LogUtil
import okhttp3.*
import okhttp3.EventListener
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by shenyonghe on 2020/9/15.
 */
class OkHttpEventListener : EventListener {
    /**
     * 自定义EventListener工厂
     */
    companion object {
        /**
         * 自定义EventListener工厂
         */
        val FACTORY: Factory = object : Factory {
            val nextCallId = AtomicLong(1L)
            override fun create(call: Call): EventListener {
                val callId = nextCallId.getAndIncrement()
                return OkHttpEventListener(call.request().url(), System.nanoTime())
            }
        }
    }

    /**
     * 每次请求的标识
     */
//    private var callId: Long = 0

    /**
     * 每次请求的开始时间，单位纳秒
     */
    private var callStartNanos: Long = 0

    private var sbLog: StringBuilder? = null

    constructor(url: HttpUrl, callStartNanos: Long) {
//        this.callId = callId
        this.callStartNanos = callStartNanos
        sbLog = StringBuilder(url.toString()).append(":").append("\n")
    }

    private fun recordEventLog(name: String) {
        val elapseNanos = System.nanoTime() - callStartNanos
        sbLog!!.append(String.format(Locale.CHINA, "%.3f-%s", elapseNanos / 1000000000.0, name)).append(";").append("\n")
        if (name.equals("callEnd", ignoreCase = true) || name.equals("callFailed", ignoreCase = true)) {
            //打印出每个步骤的时间点
            LogUtil.i("HttpEventListener", sbLog.toString())
        }
    }

    override fun callStart(call: Call?) {
        super.callStart(call)
        recordEventLog("callStart")
    }

    override fun dnsStart(call: Call?, domainName: String?) {
        super.dnsStart(call, domainName)
        recordEventLog("dnsStart")
    }

    override fun dnsEnd(call: Call?, domainName: String?, inetAddressList: List<InetAddress?>?) {
        super.dnsEnd(call, domainName, inetAddressList)
        recordEventLog("dnsEnd")
    }

    override fun connectStart(call: Call?, inetSocketAddress: InetSocketAddress?, proxy: Proxy?) {
        super.connectStart(call, inetSocketAddress, proxy)
        recordEventLog("connectStart")
    }

    override fun secureConnectStart(call: Call?) {
        super.secureConnectStart(call)
        recordEventLog("secureConnectStart")
    }

    override fun secureConnectEnd(call: Call?, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
        recordEventLog("secureConnectEnd")
    }

    override fun connectEnd(call: Call?, inetSocketAddress: InetSocketAddress?, proxy: Proxy?, protocol: Protocol?) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        recordEventLog("connectEnd")
    }

    override fun connectFailed(call: Call?, inetSocketAddress: InetSocketAddress?, proxy: Proxy?, protocol: Protocol?, ioe: IOException?) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
        recordEventLog("connectFailed")
    }

    override fun connectionAcquired(call: Call?, connection: Connection?) {
        super.connectionAcquired(call, connection)
        recordEventLog("connectionAcquired")
    }

    override fun connectionReleased(call: Call?, connection: Connection?) {
        super.connectionReleased(call, connection)
        recordEventLog("connectionReleased")
    }

    override fun requestHeadersStart(call: Call?) {
        super.requestHeadersStart(call)
        recordEventLog("requestHeadersStart")
    }

    override fun requestHeadersEnd(call: Call?, request: Request?) {
        super.requestHeadersEnd(call, request)
        recordEventLog("requestHeadersEnd")
    }

    override fun requestBodyStart(call: Call?) {
        super.requestBodyStart(call)
        recordEventLog("requestBodyStart")
    }

    override fun requestBodyEnd(call: Call?, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
        recordEventLog("requestBodyEnd")
    }

    override fun responseHeadersStart(call: Call?) {
        super.responseHeadersStart(call)
        recordEventLog("responseHeadersStart")
    }

    override fun responseHeadersEnd(call: Call?, response: Response?) {
        super.responseHeadersEnd(call, response)
        recordEventLog("responseHeadersEnd")
    }

    override fun responseBodyStart(call: Call?) {
        super.responseBodyStart(call)
        recordEventLog("responseBodyStart")
    }

    override fun responseBodyEnd(call: Call?, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        recordEventLog("responseBodyEnd")
    }

    override fun callEnd(call: Call?) {
        super.callEnd(call)
        recordEventLog("callEnd")
    }

    override fun callFailed(call: Call?, ioe: IOException?) {
        super.callFailed(call, ioe)
        recordEventLog("callFailed")
    }
}