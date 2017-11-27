package com.chantyou.janemarried.ui.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.lib.mark.ui.BaseActivity;

/**
 * Created by j_turn on 2016/3/6.
 * Email 766082577@qq.com
 */
public class WebPageActivity extends XBaseActivity {

    private TextView tvError;
    protected WebView webView;
    protected String title;
    protected String url;


    public static void launch(Context cxt, boolean hasappbar, String title, String url) {
        Intent intent = new Intent();
        intent.putExtra("hasappbar", hasappbar);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.setClass(cxt, WebPageActivity.class);
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        if (url == null || "".equals(url)) {
            finish();
            return;
        }
        tvError = (TextView) findViewById(R.id.tvError);
        tvError.setVisibility(View.GONE);
        webView = (WebView) findViewById(R.id.webview);

        webView.setVisibility(View.VISIBLE);
//        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 不使用缓存，如果没有网络，即使以前打开过此网页也不会使用以前的网页。
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 缓存模式 
        String cacheDirPath = getFilesDir().getAbsolutePath() + "/webcache";
        webView.getSettings().setDatabasePath(cacheDirPath);// 设置数据库缓存路径
        webView.getSettings().setAppCachePath(cacheDirPath);// 设置Application caches缓存目录
        webView.getSettings().setAppCacheEnabled(true);// 开启Application Cache功能
        webView.getSettings().setGeolocationEnabled(true);//启用地理定位
        // ruleWeb.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDatabaseEnabled(true);//启用数据库
        webView.getSettings().setDomStorageEnabled(true);
//        showXProgressDialog();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                onPageFinish(url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    Uri uri = Uri.parse(url);
                    if (uri.getScheme() != null && !"http".equals(uri.getScheme()) && !"https".equals(uri.getScheme())) {
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 80) {
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, android.webkit.GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        webView.loadUrl(url);//加载url
    }

    protected void onPageFinish(String url) {
        dismissXProgressDialog();
        if(TextUtils.isEmpty(url) || "about:blank".equalsIgnoreCase(url)) {
            if(tvError != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("嗯，我们无法访问该页面\n\n" + WebPageActivity.this.url);
            }
        }
    }

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        super.setupToolbar(toolbar);
        if(!getIntent().getBooleanExtra("hasappbar", false)) {
            toolbar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onInitToolbarAttribute(BaseActivity.BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        title = getIntent().getStringExtra("title");
        toolbarAttribute.setTitleSequence(title);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.clearCache(true);
            webView.destroy();
        }
        super.onDestroy();
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    // 设置??????
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                finish();
                return false;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(WebPageActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(WebPageActivity.this);
    }
}
