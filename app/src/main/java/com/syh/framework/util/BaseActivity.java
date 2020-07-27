package com.syh.framework.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.view.Choreographer;

import com.syh.framework.ui.MyFrameCallback;
import com.syh.framework.util.net.NetChangeListener;
import com.syh.framework.view.state_layout.StateLayoutManager;

/**
 * Created bg shenyonghe on 2018/6/4.
 */
public class BaseActivity extends FragmentActivity {

    private MyFrameCallback callback;

    ConnectivityManager networkManager;
    ConnectivityManager.NetworkCallback networkCallback;
    NetChangeListener netChangeListener;
    public StateLayoutManager stateLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        UIParameter.setWindowStatusBarColor(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                register();
            }
        } else {
            register();
        }
    }

    public void showLoading() {
        if (stateLayoutManager != null) {
            stateLayoutManager.showLoading();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void register() {
        networkManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
            }
        };
        networkManager.requestNetwork(new NetworkRequest.Builder().build(), networkCallback);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void unRegister() {
        try {
            if (networkManager != null && networkCallback != null) {
                networkManager.unregisterNetworkCallback(networkCallback);
            }
        } catch (Exception e) {
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
        super.onDestroy();
        unRegister();
    }
}
