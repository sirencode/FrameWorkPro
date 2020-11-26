package com.syh.framework.util;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.syh.framework.ui.MyFrameCallback;
import com.syh.framework.util.net.NetworkMonitorManager;
import com.syh.framework.util.net.enums.NetworkState;
import com.syh.framework.util.net.interfaces.NetworkMonitor;
import com.syh.framework.view.state_layout.StateLayoutManager;

/**
 * Created bg shenyonghe on 2018/6/4.
 */
public class BaseActivity extends FragmentActivity {

    private MyFrameCallback callback;

    public StateLayoutManager stateLayoutManager;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIParameter.setWindowStatusBarColor(this);
        NetworkMonitorManager.getInstance().register(this);
    }

    public void showLoading() {
        if (stateLayoutManager != null) {
            stateLayoutManager.showLoading();
        }
    }

    public void showDefault() {
        if (stateLayoutManager != null) {
            stateLayoutManager.showDefaultRequestView();
        }
    }

    public void showContent() {
        if (stateLayoutManager != null) {
            stateLayoutManager.showContent();
        }
    }

    public void showEmpty() {
        if (stateLayoutManager != null) {
            stateLayoutManager.showEmpty();
        }
    }

    public void showError() {
        if (stateLayoutManager != null) {
            stateLayoutManager.showError();
        }
    }

    public void showNoNet() {
        if (stateLayoutManager != null) {
            stateLayoutManager.showNoNetView();
        }
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

    @Override
    protected void onDestroy() {
        NetworkMonitorManager.getInstance().unregister(this);
        super.onDestroy();
    }

    @NetworkMonitor
    public void onNetWorkStateChange(NetworkState networkState) {
        Log.i("onNetWorkStateChange", "onNetWorkStateChange  networkState = $networkState");
        if (networkState ==  NetworkState.NONE) {
            Toast.makeText(this, "暂无网络", Toast.LENGTH_SHORT).show();
        } else if (networkState ==  NetworkState.WIFI) {
            Toast.makeText(this, "WIFI网络", Toast.LENGTH_SHORT).show();
        } else if (networkState ==  NetworkState.CELLULAR) {
            Toast.makeText(this, "蜂窝网络", Toast.LENGTH_SHORT).show();
        }
    }

}
