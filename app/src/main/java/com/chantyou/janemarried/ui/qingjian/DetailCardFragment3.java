package com.chantyou.janemarried.ui.qingjian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.mhh.lib.ui.base.BaseFragment;

/**
 * Created by j_turn on 2016/4/12.
 * Email 766082577@qq.com
 */
public class DetailCardFragment3 extends BaseFragment {

    int layId;
    private ImageView iv;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        if(getArguments() != null) {
            layId = getArguments().getInt("layId", 0);
        }
        return layId;
    }

    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);
        String endImage = getArguments().getString("endImage");
        iv= (ImageView) mRootView.findViewById(R.id.iv);
        Glide.with(AppAndroid.getContext()).load(endImage).into(iv);
    }



}
