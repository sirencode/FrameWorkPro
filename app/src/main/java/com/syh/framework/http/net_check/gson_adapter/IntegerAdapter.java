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
public class IntegerAdapter extends TypeAdapter<Integer> {

    private static final int DEFAULT_VALUE = -1;

    @Override
    public void write(JsonWriter out, Integer value) throws IOException {

    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return DEFAULT_VALUE;
        }
        //默认的gson其实可以对string转换int，不过仅限string内容为数字，
        //为了保证安全，这里统一处理
        if (in.peek() == JsonToken.STRING){
            in.nextString();
            return DEFAULT_VALUE;
        }
        if (in.peek() == JsonToken.BOOLEAN) {
            in.nextBoolean();
            return DEFAULT_VALUE;
        }
        return in.nextInt();

    }
}
