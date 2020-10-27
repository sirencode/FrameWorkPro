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
public class StringAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter out, String value) throws IOException {

    }

    @Override
    public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";
        }
        if (reader.peek() == JsonToken.BOOLEAN) {
            reader.nextBoolean();
            return "";
        }
        return reader.nextString();
    }
}
