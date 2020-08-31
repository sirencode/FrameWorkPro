package com.syh.framework.http.net_check

import com.google.gson.Gson
import com.syh.framework.http.model.HttpResultBean
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.nio.charset.Charset

/**
 * Created by shenyonghe on 2020/8/23.
 */
class DataCheckInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)
        response.body()?.let {
            var source = it.source()
            source.request(Long.MAX_VALUE)
            var content = source.buffer().clone().readString(Charset.forName("UTF-8"))
            val mediaType = it.contentType()
            val gson = Gson()
            val result: HttpResultBean?
            try {
                result = gson.fromJson(content, HttpResultBean::class.java)
                result.request = buildRequestString(request)
                return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, gson.toJson(result))).build()
            } catch (e: Exception) {

            }
        }
        return response
    }

    private fun buildRequestString(request: Request):String {
        var result = StringBuilder(request.toString())
        result.delete(result.length - 1, result.length)
        result.append(",headers=").append(request.headers())
        if (request.method() == "POST") {
            result.append(",body=")
            if (request.body() is FormBody) {
                val body = request.body() as FormBody?
                for (i in 0 until body!!.size()) {
                    result.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                }
                result.delete(result.length - 1, result.length)
            }
        }
        result.append("}")
        return result.toString();
    }
}