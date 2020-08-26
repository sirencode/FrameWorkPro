package com.syh.framework.asm.test;


import android.util.Log;

import com.syh.framework.asm.ASMPathManager;
import com.syh.framework.util.LogUtil;

/**
 * Created by shenyonghe on 2020/8/24.
 */
class Test {
//    private void record() {
//        String name = getClass().getSimpleName();
//        long time = System.currentTimeMillis();
//        String result = new StringBuilder("1").append(",")
//                .append(time).append(",")
//                .append(name).append(",")
//                .append(Runtime.getRuntime().maxMemory() / 1024 / 1024).append(",")
//                .append(Runtime.getRuntime().totalMemory() / 1024 / 1024).append(",")
//                .toString();
//        LogUtil.i("ASM-TAG", result);
//        ASMPathManager.add(result);
//    }

    private void markTime(String name) {
        long start = System.currentTimeMillis();
        LogUtil.i("ASM-TAG", "=================start::" + start + "ms==========");
        long end = System.currentTimeMillis();
        String costClassName = new StringBuilder(getClass().getSimpleName()).append("--->").append("name").toString();
        LogUtil.i("ASM-TAG", costClassName);
        LogUtil.i("ASM-TAG", "----> execute cost::" + (end - start) + "ms");
        LogUtil.i("ASM-TAG", "=================end::" + end + "ms==========");
    }
}
