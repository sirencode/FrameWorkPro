package com.syh.framework.http.net_check.gson_adapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Author: shenyonghe
 * Date: 2020/10/25
 * Version: v1.0
 * Description:
 * Modification History:
 * Date Author Version Description
 * ------------------------------------
 * 2020/10/25 shenyonghe v1.0
 * Why & What is modified:
 **/
public class GsonAdapter implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType == String.class)
            return (TypeAdapter<T>) new StringAdapter();
        if (rawType == int.class)
            return (TypeAdapter<T>) new IntegerAdapter();
        if (rawType == boolean.class)
            return (TypeAdapter<T>)new BooleanAdapter();
        if (rawType == double.class)
            return (TypeAdapter<T>)new DoubleAdapter();
        return null;
    }
}
