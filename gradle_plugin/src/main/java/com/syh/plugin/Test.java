package com.syh.plugin;


import java.lang.reflect.Method;

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
        Log.i("TAG", result);
        try {
            Class cls = Class.forName("com.syh.framework.util.LogUtil");
            Method staticMethod = cls.getDeclaredMethod("i",String.class,String.class);
            staticMethod.invoke(cls,"Tag",result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
