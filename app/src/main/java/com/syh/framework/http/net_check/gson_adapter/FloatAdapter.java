package com.syh.framework.http.net_check.gson_adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

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
public class FloatAdapter extends TypeAdapter<Float> {

    private static final Float DEFAULT_VALUE = 0f;

    @Override
    public void write(JsonWriter out, Float value) throws IOException {

    }

    @Override
    public Float read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return DEFAULT_VALUE;
        }
        if (in.peek() == JsonToken.STRING){
            in.nextString();
            return DEFAULT_VALUE;
        }
        if (in.peek() == JsonToken.BOOLEAN){
            in.nextBoolean();
            return DEFAULT_VALUE;
        }
        return (float)in.nextDouble();

    }
}
