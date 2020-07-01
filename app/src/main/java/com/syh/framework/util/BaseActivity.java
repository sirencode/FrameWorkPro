package com.syh.framework.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.Choreographer;

import com.syh.framework.ui.MyFrameCallback;

/**
 * Created bg shenyonghe on 2018/6/4.
 */
public class BaseActivity extends Activity {

    private MyFrameCallback callback;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        UIParameter.setWindowStatusBarColor(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res != null) {
            Configuration config = res.getConfiguration();
            if (config != null && config.fontScale != 1.0f) {
                config.fontScale = 1.0f;
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
        }
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (callback == null) {
            callback = new MyFrameCallback(this);
            Choreographer.getInstance().postFrameCallback(callback);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (callback != null) {
            Choreographer.getInstance().removeFrameCallback(callback);
        }
    }
}
