package com.chantyou.janemarried.ui.guide;

import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.base.XBaseActivity;

/**
 * Created by j_turn on 2015/12/19 22:37
 * Email：766082577@qq.com
 */

//用户注册协议
//add by zhuwx 20160804
public class RegisterAgreementActivity extends XBaseActivity {


    @Override
    protected void onCreate(Bundle arg0) {
        mSwipeBackFinish = false;
        super.onCreate(arg0);
        setContentView(R.layout.activity_register_agreement);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void init() {
        super.init();

    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(RegisterAgreementActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(RegisterAgreementActivity.this);
    }


}
