package com.chantyou.janemarried.base;

import android.content.Intent;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.base.XBaseActivity;

/**
 * Created by Administrator on 2017/1/10.
 */
public class MyBaseActivity extends XBaseActivity {
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.start_activity_in,R.anim.start_activity_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.finish_activity_in,R.anim.finish_activity_out);
    }

}
