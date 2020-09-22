package com.syh.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyonghe on 2020/8/25.
 */
public class ASMPathManager {
    public static List<PathRecord> list = new ArrayList<>();
    private static LifecycleListener listener;

    public static void add(String result) {
        String[] strings = result.split(",");
        if (strings.length >= 2) {
            PathRecord record = new PathRecord(strings[0], System.currentTimeMillis(), strings[1], "");
            list.add(record);
            if (listener != null) {
                listener.add(record);
            }
        }
    }

    public static void init(LifecycleListener lifecycleListener) {
        listener = lifecycleListener;
    }

    public void clear() {
        list.clear();
    }
}
