package com.syh.framework

/**
 * created by 申永鹤(shenyonghe@theduapp.com) on 2020/7/14
 **/
class LaunchRecord {
    companion object {
        private var startTime:Long = 0

        private val TAG = "LaunchRecord"

        fun startRecord() {
            startTime = System.currentTimeMillis()
        }

        fun endRecord(point:String) {
            val cost = System.currentTimeMillis() - startTime
            println("$TAG:===$point===>$cost")
        }
    }
}