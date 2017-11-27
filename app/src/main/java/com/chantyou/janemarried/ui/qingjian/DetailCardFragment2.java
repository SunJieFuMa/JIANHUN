package com.chantyou.janemarried.ui.qingjian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.bean.MyCard;
import com.mhh.lib.ui.base.BaseFragment;

/**
 * Created by j_turn on 2016/4/12.
 * Email 766082577@qq.com
 */
public class DetailCardFragment2 extends BaseFragment {

    int layId;
    private MyCard.DataEntity dataEntity;
    private ImageView iv;
    private long userTempleteId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        if (getArguments() != null) {
            layId = getArguments().getInt("layId", 0);
        }
        return layId;
    }

    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);
        dataEntity = (MyCard.DataEntity) getArguments().getSerializable("dataEntity");
        userTempleteId=dataEntity.getId();
        iv = (ImageView) mRootView.findViewById(R.id.iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppAndroid.getContext(),PageModelActivity.class);
                intent.putExtra("userTempleteId",userTempleteId);
                startActivityForResult(intent,0);
            }
        });
    }
}
