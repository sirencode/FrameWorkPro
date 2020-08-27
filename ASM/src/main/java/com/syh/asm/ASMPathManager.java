package com.syh.asm;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyonghe on 2020/8/25.
 */
public class ASMPathManager {
    public static List<PathRecord> list = new ArrayList<>();

    public static void add(String result) {
        String[] strings = result.split(",");
        if (strings.length == 5) {
            list.add(new PathRecord(strings[0], Long.valueOf(strings[1]), strings[2], Long.valueOf(strings[3]), Long.valueOf(strings[4])));
        }
    }

    public static void init(Application application,boolean open) {
        if (!open) return;
        AppFrontBackHelper helper = new AppFrontBackHelper();
        helper.register(application, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
            }

            @Override
            public void onBack() {
                //应用切到后台处理
                Log.e("ASM-TAG", ASMPathManager.list.toString());
            }
        });
    }
}
