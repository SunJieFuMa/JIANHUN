package com.chantyou.janemarried.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.sticky.HeaderChannelAdapter;
import com.chantyou.janemarried.model.ChannelEntity;

import java.util.List;

/**
 * 频道数据
 */
public class HeaderChannelViewView extends HeaderViewInterface<List<ChannelEntity>> {

    private FixedGridView gvChannel;

    public HeaderChannelViewView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(List<ChannelEntity> list, ListView listView) {
        View view = mInflate.inflate(R.layout.header_channel_layout, listView, false);
        gvChannel = (FixedGridView) view.findViewById(R.id.gv_channel);


        dealWithTheView(list);//适配列数
        listView.addHeaderView(view);
    }

    private void dealWithTheView(List<ChannelEntity> list) {
        int size = list.size();

        if (size <= 4) {
            gvChannel.setNumColumns(size);//设置GridView的总列数
        } else if (size == 6) {
            gvChannel.setNumColumns(3);
        } else if (size == 8) {
            gvChannel.setNumColumns(4);
        } else {
            gvChannel.setNumColumns(4);
        }

        HeaderChannelAdapter adapter = new HeaderChannelAdapter(mContext, list);
        gvChannel.setAdapter(adapter);
    }


}
