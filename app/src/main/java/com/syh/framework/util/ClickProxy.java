package com.syh.framework.util;

import android.view.View;

/**
 * create by shenyonghe at 2018/12/1
 */
public abstract class ClickProxy implements View.OnClickListener {

    public static int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public ClickProxy() {

    }

    public ClickProxy(int time) {
        MIN_CLICK_DELAY_TIME = time;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            checkClick(v);
        }
    }

    public abstract void checkClick(View view);
}
