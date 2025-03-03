package com.lcy.base.core.widgets.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;


/**
 * Created by lcy on 2018/8/22.
 * email:lcyzxin@gmail.com
 * version 1.0
 */
public class ProgressWebView extends WebView {

    //  进度条的矩形（进度线）
    private WebViewProgressBar progressBar;
    private Handler handler;

    private WebClient webClientListener;
    private WebChrome webChromeListener;

    public WebClient getWebClientListener() {
        return webClientListener;
    }

    public void setWebClientListener(WebClient webClientListener) {
        this.webClientListener = webClientListener;
    }

    public WebChrome getWebChromeListener() {
        return webChromeListener;
    }

    public void setWebChromeListener(WebChrome webChromeListener) {
        this.webChromeListener = webChromeListener;
    }

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //实例化进度条
        progressBar = new WebViewProgressBar(context);
        //设置进度条的size
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //刚开始时候进度条不可见
        progressBar.setVisibility(GONE);
        //把进度条添加到webView里面
        addView(progressBar);
        //初始化handle
        handler = new Handler();
        initSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSettings() {
        // 初始化设置
        WebSettings settings = this.getSettings();
        setWebChromeClient(new MyWebChromeClient());
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setDefaultTextEncodingName("utf-8");   //  设置字符编码
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);              //  设置支持文件流
        settings.setSupportZoom(true);                  //  支持缩放
        settings.setDatabaseEnabled(true);
        //  页面加载好以后，再放开图片
        settings.setBlockNetworkImage(false);
        settings.setLoadWithOverviewMode(true);
        setHorizontalScrollBarEnabled(false);//水平不显示
        setVerticalScrollBarEnabled(false); //垂直不显示
        // 缩放比例 1
        setInitialScale(1);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        // WebView是否支持多个窗口。
        settings.setSupportMultipleWindows(false);
        // 设置此属性，可任意比例缩放
        settings.setUseWideViewPort(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        setDrawingCacheEnabled(true);
        // 保存表单数据
        settings.setSaveFormData(true);
        // 设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        // 排版适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //点击链接继续在当前browser中响应
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                sslErrorHandler.cancel();
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (getWebClientListener() != null) {
                    getWebClientListener().onPageFinished(webView, s);
                }
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                if (getWebClientListener() != null) {
                    getWebClientListener().onPageStarted(webView, s, bitmap);
                }
            }
        });

    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            showProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {
            if (getWebChromeListener() != null) {
                return getWebChromeListener().onJsAlert(webView, url, message, jsResult);
            }
            jsResult.cancel();
            return super.onJsAlert(webView, url, message, jsResult);
        }
    }

    public void showProgress(int newProgress) {
        if (newProgress == 100) {
            progressBar.setProgress(100);
            handler.postDelayed(runnable, 200);//0.2秒后隐藏进度条
        } else if (progressBar.getVisibility() == GONE) {
            progressBar.setVisibility(VISIBLE);
        }
        //设置初始进度10，这样会显得效果真一点，总不能从1开始吧
        if (newProgress < 10) {
            newProgress = 10;
        }
        //不断更新进度
        progressBar.setProgress(newProgress);
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.GONE);
        }
    };

    public interface WebClient {

        void onPageFinished(WebView webView, String s);

        void onPageStarted(WebView webView, String s, Bitmap bitmap);

    }

    public interface WebChrome {

        boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult);

    }
}
