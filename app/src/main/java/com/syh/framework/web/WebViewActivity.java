package com.syh.framework.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.syh.framework.R;
import com.syh.framework.util.BaseActivity;
import com.syh.framework.util.LogUtil;

import java.util.List;
import java.util.Map;


/**
 * Created bg shenyonghe on 2018/6/4.
 */
public class WebViewActivity extends BaseActivity {

    public static final String SCHEME = "openshen";
    public static final String JSOBJECT_NAME = "android";
    private static final String URL_KEY = "URL_KEY";
    private static final String SHOWTITLE_KEY = "SHOW_TITLE_KEY";
    private WebView webView;
    private ProgressBar progressBar;
    private String url;
    private boolean showTitle;
    private LinearLayout titleLay;
    private CookieManager cookieManager;

    public static void startWebAct(String url, Activity activity, boolean showTitle) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(URL_KEY, url);
        intent.putExtra(SHOWTITLE_KEY, showTitle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initParameter();
        initWebView();
        getCookieManager();
    }

    private void initParameter() {
        if (getIntent() != null) {
            this.url = getIntent().getStringExtra(URL_KEY);
            this.showTitle = getIntent().getBooleanExtra(SHOWTITLE_KEY, false);
        }
    }

    private CookieManager getCookieManager() {
        if (cookieManager == null) {
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.createInstance(this);
            }
            cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
        }
        return cookieManager;
    }

    private void setCookie(String url,String cookie) {
        if (!TextUtils.isEmpty(cookie)) {
           cookieManager.setCookie(url,cookie);
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }
        }
    }

    private void initWebView() {
        titleLay = findViewById(R.id.title_lay);
        progressBar = findViewById(R.id.progressBar);
        titleLay.setVisibility(showTitle ? View.VISIBLE : View.GONE);
        webView = findViewById(R.id.web);
        buildWebView(webView, this);
        webView.loadUrl(url);
        findViewById(R.id.iv_title_back).setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        releaseWebView(webView);
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

    public void buildWebView(WebView webView, WebViewActivity activity) {
        WebSettings webSettings = webView.getSettings();
        //各种屏幕网页适配
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //支持混合加载（https网页加载http的内容）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSObject(activity), JSOBJECT_NAME);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //setCookie
//            Uri uri = Uri.parse(url);
//            String host = uri.getHost();
//            String schema = uri.getScheme();
//            // 白名单
//            if (host == null || (!host.contains("xxx.com"))) {
//                LogUtil.d("WebViewActivity", "Do Not set Cookie because of Domain");
//                return;
//            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(SCHEME)) {
                Activity activity = (Activity) view.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(intent);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        }
    }

    public void releaseWebView(WebView webView) {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
    }

    public class JSObject {
        private WebViewActivity webViewActivity;

        public JSObject(WebViewActivity activity) {
            this.webViewActivity = activity;
        }

        @JavascriptInterface
        public void closeCurPage() {
            webViewActivity.finish();
        }
    }
}
