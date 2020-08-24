package com.syh.framework.http.net_check

import com.google.gson.Gson
import com.syh.framework.http.model.HttpResultBean
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by shenyonghe on 2020/8/23.
 */
class DataCheckInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)
        response.body()?.let {
            var content = it.string()
            val mediaType = it.contentType()
            val gson = Gson()
            val result: HttpResultBean?
            try {
                result = gson.fromJson(content, HttpResultBean::class.java)
                result.request = request.toString()
                return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, gson.toJson(result))).build()
            } catch (e: Exception) {

            }
        }
        return response
    }
}