package com.chantyou.janemarried.view.shop;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.shop.ShopHomeChannelAdapter;
import com.chantyou.janemarried.model.Shop.ShopHomeChannelEntity;
import com.chantyou.janemarried.view.FixedGridView;
import com.chantyou.janemarried.view.HeaderViewInterface;

import java.util.List;

//
public class ShopHomeChanelView extends HeaderViewInterface<List<ShopHomeChannelEntity>> {

    private FixedGridView gvChannel;

    public ShopHomeChanelView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(List<ShopHomeChannelEntity> list, ListView listView) {
        View view = mInflate.inflate(R.layout.shop_home_header_channel, listView, false);
        gvChannel = (FixedGridView) view.findViewById(R.id.gv_channel);


        dealWithTheView(list);//适配列数
        listView.addHeaderView(view);
    }

    private void dealWithTheView(List<ShopHomeChannelEntity> list) {
        int size = list.size();

        if (size <= 4) {
            gvChannel.setNumColumns(size);
        } else if (size == 6) {
            gvChannel.setNumColumns(3);
        } else if (size == 8) {
            gvChannel.setNumColumns(4);
        } else {
            gvChannel.setNumColumns(4);
        }

        ShopHomeChannelAdapter adapter = new ShopHomeChannelAdapter(mContext, list);
        gvChannel.setAdapter(adapter);
    }


}
