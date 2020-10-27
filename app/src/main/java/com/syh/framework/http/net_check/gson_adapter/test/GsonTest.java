package com.syh.framework.http.net_check.gson_adapter.test;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.syh.framework.http.net_check.gson_adapter.GsonAdapter;

/**
 * Author: shenyonghe
 * Date: 2020/10/26
 * Version: v1.0
 * Description:
 * Modification History:
 * Date Author Version Description
 * ------------------------------------
 * 2020/10/26 shenyonghe v1.0
 * Why & What is modified:
 **/
public class GsonTest {

    public static void main(String[] args) {
        gsonTest();
    }

    public static void gsonTest() {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.registerTypeAdapterFactory(new GsonAdapter()).create();
            UserBean bean = gson.fromJson("{\"address\":false,\"age\":\"sss\",\"email\":null,\"gender\":null,\"hasMarry\":1.5,\"name\":null,\"weight\":false,\"height\":\"188\"}", UserBean.class);
            Log.i("kkk", "错误类型：\n");
            Log.i("kkk", bean.toString());
            UserBean bean1 = gson.fromJson("{\"address\":\"北京\",\"age\":18,\"email\":\"126.com\",\"gender\":\"男\",\"hasMarry\":true,\"name\":\"kjt\",\"weight\":150.0,\"height\":188}", UserBean.class);
            Log.i("kkk", "正常类型：\n");
            Log.i("kkk", bean1.toString());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

}
