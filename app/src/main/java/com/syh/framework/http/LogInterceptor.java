package com.syh.framework.http;

import android.util.Log;

import com.google.gson.Gson;
import com.syh.framework.http.model.HttpBaseResult;
import com.syh.framework.util.LogUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

public class LogInterceptor implements Interceptor {

    public static String TAG = "LogInterceptor";

    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtil.d(TAG, "\n");
        LogUtil.d(TAG, "----------Start----------------");
        LogUtil.d(TAG, "| " + request.toString());
        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                Log.d(TAG, "| RequestParams:{" + sb.toString() + "}");
            }
        }
        LogUtil.d(TAG, "| Response:" + content);
        LogUtil.d(TAG, "----------End:" + duration + "毫秒----------");
        Gson gson = new Gson();
        HttpBaseResult result = gson.fromJson(content,HttpBaseResult.class);
        result.setRequest(request.toString());
        LogUtil.d(TAG, "| Response:" + result);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, gson.toJson(result)))
                .build();
    }
}

