package com.chantyou.janemarried.ui.qingjian;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.bean.AllCard;
import com.chantyou.janemarried.bean.UserUseCard;
import com.google.gson.Gson;

/**
 * Created by j_turn on 2016/3/6.
 * Email 766082577@qq.com
 */
public class QingJianWebPageActivity extends MyBaseActivity implements View.OnClickListener{

    private TextView tvError;
    protected WebView webView;
    protected String title;
    protected String url;
    private int templateId;
    private Button title_back;
    private Button title_setting;
    private ProgressBar bar;
    private AllCard.DataEntity dataEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppAndroid.addActivity(this);
        setContentView(R.layout.activity_webpage2);
        title_back= (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        title_setting= (Button) findViewById(R.id.title_setting);
        title_setting.setOnClickListener(this);
        bar= (ProgressBar) findViewById(R.id.bar);

        Intent intent = getIntent();
        dataEntity= (AllCard.DataEntity) intent.getSerializableExtra("dataEntity");
        templateId=dataEntity.getId();
//        Toast.makeText(this, " templateId::"+templateId, Toast.LENGTH_SHORT).show();
        url="http://www.easymarrytec.com:1661/qingjian/v/vdemo.html?" +
                "userId=0&templateId=" + templateId;

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
                bar.setVisibility(View.GONE);
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
                tvError.setText("嗯，我们无法访问该页面\n\n" + QingJianWebPageActivity.this.url);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.clearCache(true);
            webView.destroy();
        }
        AppAndroid.removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_setting:
                //点击了立即使用
                /*String url="http://101.201.209.200:1661/qingjian/action/qingjian/user/template/use";
                System.out.println("用户id为：："+AppAndroid.getUid());
                OkHttpUtils
                        .post()
                        .url(url)
                        .addParams("userId", AppAndroid.getUid() + "")
                        .addParams("templateId", templateId+"")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(QingJianWebPageActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                parseJson(response);
                                System.out.println("response::" + response);
                            }
                        });
*/
                Intent intent=new Intent(this,UseCardActivity.class);
                intent.putExtra("templateId",templateId);
//                intent.putExtra("userTempleteId",userTempleteId);
                intent.putExtra("dataEntity",dataEntity);
                startActivity(intent);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    private void parseJson(String response) {
        UserUseCard userUseCard = new Gson().fromJson(response, UserUseCard.class);
        String userTempleteId = userUseCard.getUserTempleteId();
//        Toast.makeText(this, "userTempleteId11:: "+userTempleteId, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,UseCardActivity.class);
        intent.putExtra("userTempleteId",userTempleteId);
        intent.putExtra("templateId",templateId);
        intent.putExtra("dataEntity",dataEntity);
        startActivity(intent);
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
        StatService.onResume(QingJianWebPageActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(QingJianWebPageActivity.this);
    }
}
