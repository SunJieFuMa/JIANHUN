package com.chantyou.janemarried.ui.base;

import android.content.Intent;

/**
 * Created by j_turn on 2016/4/13.
 * Email 766082577@qq.com
 */
public class ThreadShareActivity extends XBaseActivity {

    ThreadShareHelper shareHelper;

    @Override
    protected void init() {
        super.init();
        shareHelper = new ThreadShareHelper(this);
    }

    protected void showShareDialog(String url, String title, String content) {
        if(shareHelper != null) {

            shareHelper.showShareDialog(url, title, content);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(shareHelper != null) {
            shareHelper.handleWeiboResponse(intent);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(shareHelper != null) {
            shareHelper.onActivityResult(requestCode, resultCode, data);
        }
    }
}
