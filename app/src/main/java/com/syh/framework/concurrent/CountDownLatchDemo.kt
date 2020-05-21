package com.syh.framework.concurrent

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by shenyonghe on 2020/5/19.
 */
class CountDownLatchDemo {

    val latch: CountDownLatch = CountDownLatch(1);

    fun method1(name: String) {
        try {
            latch.await(10L, TimeUnit.SECONDS)
        } catch (e: Exception) {
        }
        println(System.currentTimeMillis().toString() + ":" +Thread.currentThread().getName() + " - " + name + " 被唤醒")
    }

    fun method2(name: String) {
        println(System.currentTimeMillis().toString() + ":" +Thread.currentThread().getName() + " - " + name)
        try {
            TimeUnit.SECONDS.sleep(5);
            latch.countDown();
            println(System.currentTimeMillis().toString() + ":" +Thread.currentThread().getName() + " - " + name + "countDown")
        } catch (e: Exception) {
        }
    }

    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {
            var demo = CountDownLatchDemo()
            Thread(Runnable {
                kotlin.run {
                    demo.method1("method1")
                }
            }).start()

            Thread(Runnable {
                kotlin.run {
                    demo.method2("method2")
                }
            }).start()
        }
    }
}

