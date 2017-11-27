package com.chantyou.janemarried.adapter.excellent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.ViewUtils;
import com.mhh.lib.utils.JsonUtil;

import java.util.Map;

/**
 * Created by j_turn on 2015/12/19.
 * Email 766082577@qq.com
 */

public class MgCommentAdapter extends BaseRecyclerViewAdapter<Map<String , Object>> {

    public MgCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.adapter_comment_item));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Map<String, Object> map = getValueAt(position);
        if(map != null) {
//            Map<String, Object> member = (Map<String, Object>) map.get("member");
            HImageLoader.displayImage(JsonUtil.getItemString(map, "photo"), vh.ivHead, R.drawable.defaulthead);
            vh.tvTitle.setText(JsonUtil.getItemString(map, "username"));
            vh.tvTime.setText(JsonUtil.getItemString(map, "time"));
            vh.tvContent.setText(JsonUtil.getItemString(map, "content"));
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.ivHead)
        ImageView ivHead;
        @ViewInject(R.id.tvTitle)
        TextView tvTitle;
        @ViewInject(R.id.tvContent)
        TextView tvContent;
        @ViewInject(R.id.tvTime)
        TextView tvTime;
        public ViewHolder(View view) {
            super(view);
            ViewUtils.inject(this, view);
        }
    }
}
