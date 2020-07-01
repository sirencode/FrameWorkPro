package com.syh.framework.ui;

import android.app.Activity;
import android.view.Choreographer;

import com.syh.framework.util.LogUtil;

/**
 * Created by shenyonghe on 2020/6/5.
 */
public class MyFrameCallback implements Choreographer.FrameCallback {

    private long lastTime = 0;
    private Activity activity;
    private String TAG = "FrameCallback";

    public MyFrameCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        if (lastTime == 0) {
            lastTime = frameTimeNanos;
        } else {
            long times = (frameTimeNanos - lastTime) / 1000000;
            int frames = (int) (times / 16);

            if (times > 16) {
                LogUtil.w(TAG, activity.getClass().getName() + "FrameCallback UI线程超时(超过16ms):" + times + "ms" + " , 丢帧:" + frames);
            }
            lastTime = frameTimeNanos;
        }
        Choreographer.getInstance().postFrameCallback(this);
    }
}
