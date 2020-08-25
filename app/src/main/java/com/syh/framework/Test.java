package com.syh.framework;


import com.syh.framework.util.LogUtil;

/**
 * Created by shenyonghe on 2020/8/24.
 */
class Test {
    private void record() {
        String name = getClass().getSimpleName();
        long time = System.currentTimeMillis();
        String result = new StringBuilder("-------> onStart : ").append(name)
                .append(",time=").append(time)
                .append(",maxMemory=").append(Runtime.getRuntime().maxMemory() / 1024 / 1024).append("M")
                .append(",totalMemory=").append(Runtime.getRuntime().totalMemory() / 1024 / 1024).append("M")
                .toString();
        LogUtil.i("TAG", result);
    }
}
