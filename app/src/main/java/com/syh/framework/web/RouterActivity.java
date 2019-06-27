package com.syh.framework.web;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.syh.framework.util.BaseActivity;
import com.syh.framework.util.LogUtil;

public class RouterActivity extends BaseActivity {

    public static final String WEB_PATH = "/common/webview_activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getIntent() != null && getIntent().getData() != null) {
                jump();
            } else {
                startApp();
            }
            finish();
        } catch (Exception e) {
            Log.e("", e.getMessage(), e);
        }
        finish();
    }

    private void jump() {
        String path = getIntent().getData().getPath();
        if (path.equals(WEB_PATH)) {
            String url = getIntent().getData().getQueryParameter("url");
            WebViewActivity.startWebAct(url, this, true);
        }
    }

    private void startApp() {
        LogUtil.d("zlq", "从Web 启动移动经纪人APP");
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(this.getPackageName());
        startActivity(intent);
    }
}
