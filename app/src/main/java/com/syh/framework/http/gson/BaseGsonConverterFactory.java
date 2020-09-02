package com.syh.framework.http.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 作者：created by Bamboo on 2019/3/17
 * 邮箱：bzy601638015@126.com
 */
public class BaseGsonConverterFactory<T> extends Converter.Factory {

    private final Gson gson;
    public static BaseGsonConverterFactory create() {
        return create(new Gson());
    }

    public static BaseGsonConverterFactory create(Gson gson) {
        if(gson == null) {
            throw new NullPointerException("gson == null");
        } else {
            return new BaseGsonConverterFactory(gson);
        }
    }

    private BaseGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new BaseGsonResponseBodyConverter<>(adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }
}
