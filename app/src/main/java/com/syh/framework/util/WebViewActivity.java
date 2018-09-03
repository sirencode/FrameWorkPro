package com.syh.framework.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.syh.framework.R;


/**
 * Created bg shenyonghe on 2018/6/4.
 */
public class WebViewActivity extends BaseActivity {
    private static final String URL_KEY = "URL_KEY";
    private WebView webView;
    private String url;

    public static void startWebAct(String url, Activity activity) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(URL_KEY, url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initParameter();
        initWebView();
    }

    private void initParameter() {
        if (getIntent() != null) {
            this.url = getIntent().getStringExtra(URL_KEY);
        }
    }

    private void initWebView() {
        webView = findViewById(R.id.web);
        WebViewBuild.buildWebView(webView);
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        WebViewBuild.releaseWebView(webView);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
