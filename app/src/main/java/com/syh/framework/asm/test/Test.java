package com.syh.framework.asm.test;


import com.syh.asm.ASMPathManager;

/**
 * Created by shenyonghe on 2020/8/24.
 */
class Test {
    private void record(boolean isVisible) {
        String result = new StringBuilder("1").append(",")
                .append(getClass().getSimpleName()).append(",")
                .toString();
        ASMPathManager.add(result);
    }

//    private void markTime(String name) {
//        long start = System.currentTimeMillis();
//        Log.i("ASM-TAG", "=================start::" + start + "ms==========");
//        long end = System.currentTimeMillis();
//        String costClassName = new StringBuilder(getClass().getSimpleName()).append("--->").append("name").toString();
//        Log.i("ASM-TAG", costClassName);
//        Log.i("ASM-TAG", "----> execute cost::" + (end - start) + "ms");
//        Log.i("ASM-TAG", "=================end::" + end + "ms==========");
//    }
}
