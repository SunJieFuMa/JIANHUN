package com.chantyou.janemarried.ui.left;

import android.os.Bundle;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.utils.SystemUtils;

/**
 * Created by j_turn on 2016/4/26.
 * Email 766082577@qq.com
 */
public class AboutUsActivity extends XBaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_aboutus);

        ((TextView) findViewById(R.id.tvVersion)).setText(String.format("V%1$s %2$d", SystemUtils.getCurVersion(this), SystemUtils.getCurVCode(this)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(AboutUsActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(AboutUsActivity.this);
    }
}
