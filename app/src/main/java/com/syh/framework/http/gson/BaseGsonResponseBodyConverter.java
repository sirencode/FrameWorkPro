package com.syh.framework.http.gson;

import com.google.gson.TypeAdapter;
import com.syh.framework.http.net_check.NetCheckManager;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 作者：created by Bamboo on 2019/3/17
 * 邮箱：bzy601638015@126.com
 */
public class BaseGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    BaseGsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonString = value.string();
        try {
            JSONObject object = new JSONObject(jsonString);
            T t = adapter.fromJson(object.getString("data"));
            String request = object.getString("request");
            NetCheckManager.INSTANCE.checkValue(t, request);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        } finally {
            value.close();
        }
    }
}
