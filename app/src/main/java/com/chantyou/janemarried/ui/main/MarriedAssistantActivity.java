package com.chantyou.janemarried.ui.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.assistant.MarriedassistantAdapter;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.chantyou.janemarried.ui.qingjian.CustomGridLayoutManager;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2015/12/14 23:38
 * Email：766082577@qq.com
 */

//备婚助手
public class MarriedAssistantActivity extends PullrefreshBottomloadActivity {

    private MarriedassistantAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_marriedassistant;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

    }

    @Override
    protected void setupRecyclerView() {
        mAdapter = new MarriedassistantAdapter(this);
//        GridLayoutManager manager = new GridLayoutManager(this, 3);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return position == 0 || position == 7 ? 3 : 1;
//            }
//        });
        /*
        使用RecycleView 时，如果数据量很少只有几个，需求就不需要它上下左右滑动，
        在xml配置中加上 Android:scrollbars=”none”，这只是去掉了滑动bar。
        但是RecycleView 上下还是能滑动，且有阴影。
        下面这几行代码就能禁止RecycleView滑动，并且能够去掉滑动阴影
         */
        CustomGridLayoutManager manager=new CustomGridLayoutManager(this,3);
        manager.setScrollEnabled(false);//自定义的方法，设置不能滑动
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 3 : 1;
            }
        });
        setupRecyclerView(manager, mAdapter, RecyclerMode.NONE);
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        toolbarAttribute.setNavigation(false, null, null);
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MarriedAssistantActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MarriedAssistantActivity.this);
    }
}
