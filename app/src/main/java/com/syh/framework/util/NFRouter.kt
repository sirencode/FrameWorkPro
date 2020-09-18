package com.syh.framework.util

import android.app.Activity
import android.net.Uri
import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import java.io.Serializable

/**
 * 作者：created by Bamboo on 2019-11-04
 * 邮箱：bzy601638015@126.com
 */
object NFRouter {

    fun jump(href: String) {
        RouterItem.Builder().setHref(href).jump()
    }

    class RouterItem(
        val href: String,
        val map: Map<String, Any?>?,
        val activity: Activity? = null,
        val code: Int = 0
    ) {
        class Builder() {
            lateinit var href: String
            var map: MutableMap<String, Any?>? = null
            var activity: Activity? = null
            var code: Int = 0

            fun setHref(href: String): Builder {
                if (href.startsWith("/")) {
                    this.href = buildPath(href)
                } else {
                    this.href = href
                }
                return this
            }

            fun with(key: String, value: Any?): Builder {
                value?.let {
                    if (map == null) {
                        map = mutableMapOf()
                    }
                    map!![key] = it
                }
                return this
            }

            fun withString(key: String, value: String?): Builder {
                return with(key, value)
            }

            fun withInt(key: String, value: Int?): Builder {
                return with(key, value)
            }

            fun withFloat(key: String, value: Float?): Builder {
                return with(key, value)
            }

            fun withDouble(key: String, value: Double?): Builder {
                return with(key, value)
            }

            fun withBoolean(key: String, value: Boolean?): Builder {
                return with(key, value)
            }

            fun withSerializable(key: String, value: Serializable?): Builder {
                return with(key, value)
            }

            fun withLong(key: String, value: Long?): Builder {
                return with(key, value)
            }


            fun setActivity(activity: Activity): Builder {
                this.activity = activity
                return this
            }

            fun setCode(code: Int): Builder {
                this.code = code
                return this
            }

            fun jump() {
                RouterItem(href, map, activity, code).jump()
            }
        }

        fun jump() {
            if (TextUtils.isEmpty(href)) {
                return
            }
            var uri = Uri.parse(href)
            var builder = uri.buildUpon()
            map?.let { map ->
                map.forEach {
                    builder.appendQueryParameter(it.key, it.value.toString())
                }
            }
            uri = Uri.parse(builder.toString())
            var postcard = ARouter.getInstance().build(uri)
            map?.let { map ->
                map.forEach {
                    if (it.value == null) {
                        return@forEach
                    }
                    when (it.value) {
                        is String -> {
                            postcard.withString(it.key, it.value as String)
                        }
                        is Boolean -> {
                            postcard.withBoolean(it.key, it.value as Boolean)
                        }
                        is Int -> {
                            postcard.withInt(it.key, it.value as Int)
                        }
                        is Float -> {
                            postcard.withFloat(it.key, it.value as Float)
                        }
                        is Double -> {
                            postcard.withDouble(it.key, it.value as Double)
                        }
                        is Char -> {
                            postcard.withChar(it.key, it.value as Char)
                        }
                        is Byte -> {
                            postcard.withByte(it.key, it.value as Byte)
                        }
                        is Long -> {
                            postcard.withLong(it.key, it.value as Long)
                        }
                        is Serializable -> {
                            postcard.withSerializable(it.key, it.value as Serializable)
                        }
                        is ArrayList<*> -> {
                            var array = it.value as ArrayList<Any>
                            if (array != null && array.size > 0) {
                                var item = array[0]
                                when (item) {
                                    is String -> {
                                        postcard.withStringArrayList(
                                            it.key,
                                            it.value as ArrayList<String>
                                        )
                                    }
                                    is Int -> {
                                        postcard.withIntegerArrayList(
                                            it.key,
                                            it.value as ArrayList<Int>
                                        )
                                    }
                                    is Serializable -> {
                                        postcard.withSerializable(it.key, it.value as Serializable)
                                    }
                                }
                            }
                        }
                    }
                }

                if (activity == null) {
                    postcard.navigation()
                } else {
                    postcard.navigation(activity, code)
                }
            }
        }
    }

    fun buildPath(path: String): String {
        return "fen95://95fenapp.com${path}"
    }
}