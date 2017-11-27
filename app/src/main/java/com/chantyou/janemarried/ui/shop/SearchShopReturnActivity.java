package com.chantyou.janemarried.ui.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;

/**
 * Created by Administrator on 2017/1/23.
 */
public class SearchShopReturnActivity extends MyBaseActivity {//假数据。没用
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_searchshop);
        tv= (TextView) findViewById(R.id.tv);
        tv.setText(getIntent().getStringExtra("data"));
    }
}
