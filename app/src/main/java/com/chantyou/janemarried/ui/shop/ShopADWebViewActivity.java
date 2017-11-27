package com.chantyou.janemarried.ui.shop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.base.XBaseActivity;


public class ShopADWebViewActivity extends XBaseActivity {

    private String url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_adweb_view);
        url = getIntent().getStringExtra("url");
        initWebView();
    }

    private void initWebView() {
        final WebView webView = (WebView) findViewById(R.id.wv_ad);


//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setWebViewClient(mWebViewClient);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setSaveFormData(false);
//        webView.getSettings().setSavePassword(false);
//        webView.loadUrl(url);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.loadUrl("javascript:function setTop(){document.getElementsByClassName(\"header\")[0].style.display = \"none\";}setTop();");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //在加载完成之后对网页中内容进行隐藏
//                webView.loadUrl("javascript:document.getElementsByClassName(\"header\")[0].style.display = \"none\";");
                webView.loadUrl("javascript:function setTop(){document.getElementsByClassName(\"header\")[0].style.display = \"none\";}setTop();");
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (url == null)
                    return;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });

//        webView.setDownloadListener(new DownloadListener() {
//
//            @Override
//            public void onDownloadStart(String arg0, String arg1, String arg2,
//                                        String arg3, long arg4) {
//
//            }
//        });


        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        webView.loadUrl(url);


//        webView.loadUrl("http://www.easymarrytec.com/MarryTrade/news.html?id=16&from=singlemessage&isappinstalled=0");
        System.out.println("zhuwx：url:" + url);

//        覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//        });
    }


    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };


    private WebChromeClient mWebChromeClient = new WebChromeClient() {

    };
}
