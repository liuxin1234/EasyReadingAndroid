package com.liux.easyreading.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.liux.easyreading.R;
import com.liux.easyreading.base.BaseSwipeBackActivity;
import com.liux.easyreading.view.ToolbarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity
 * 项目日期：2017/11/3
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class WebViewActivity extends BaseSwipeBackActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String url;
    private String title;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        toolbarHelper.setTitle("轻松阅");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setSwipeBackEnable(false); //默认为true：使用侧滑返回，false：关闭侧滑返回
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            //setWebClient就是帮助WebView处理各种通知、请求事件
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
//                dissMissNormalDialog();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //设定加载资源的操作
            }

        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            //setWebChromeClient辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //网页加载完毕
//                    dissMissNormalDialog();
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE) {
                        progressBar.setVisibility(View.GONE);
                    }else
                    {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                    //网页正在加载
//                    showNormalDialog("正在加载数据.....");
                }
            }
        });

    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }


}
