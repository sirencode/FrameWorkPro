package com.syh.framework.asm;

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
}
