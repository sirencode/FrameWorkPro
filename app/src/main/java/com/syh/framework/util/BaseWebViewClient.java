package com.syh.framework.util;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created bg shenyonghe on 2018/6/5.
 */
public class BaseWebViewClient extends WebViewClient {

    private static final String TAG = "BaseWebViewClient";

    //截取url请求，在当前视图加载，避免在跳转到自带浏览器
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return true;
    }

    //重写此方法可以让webView处理https请求
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    //重写此方法才能够处理在浏览器中的按键事件
    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        LogUtil.LogD(TAG, "onPageStarted:" + url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtil.LogD(TAG, "onPageFinished:" + url);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        LogUtil.LogE(TAG, "onReceivedError:" + error.getDescription().toString());
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        LogUtil.LogE(TAG, "onLoadResource:" + url);
    }
}
