package com.chantyou.janemarried.ui.pay;

import android.content.Intent;
import android.view.View;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseFragment;

/**
 * Created by Administrator on 2017/1/12.
 */
public class MyDingFragment extends MyBaseFragment {

    private View view;

    @Override
    protected View initView() {
        //        TextView tv = new TextView(getContext());
        //        tv.setText(getArguments().getString("title"));
        view = View.inflate(mContext, R.layout.fr_myding, null);
        return view;
    }

    @Override
    protected void initData() {
        view.findViewById(R.id.ding_item_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DingInfoActivity.class);
                intent.putExtra("shopPhone",getArguments().getString("shopPhone"));//将商家的电话传递过去
                startActivity(intent);
            }
        });
    }
}
