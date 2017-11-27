package com.chantyou.janemarried.adapter.topic;

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
 * Created by j_turn on 2016/3/13.
 * Email 766082577@qq.com
 */
public class TopicCommentAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

    public TopicCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.adapter_topic_comment));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Map<String, Object> map = getValueAt(position);
        vh.map = map;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder instanceof ViewHolder) {
            ((ViewHolder) holder).setData();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.ivHead)
        ImageView ivHead;
        @ViewInject(R.id.tvName)
        TextView tvName;
        @ViewInject(R.id.tvTime)
        TextView tvTime;
        @ViewInject(R.id.tvIntro)
        TextView tvIntro;
        Map<String, Object> map;
        public ViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }

        void setData() {
            if(map != null) {
                Map<String, Object> member = (Map<String, Object>) map.get("member");
                HImageLoader.displayImage(JsonUtil.getItemString(member, "photo"), ivHead, R.drawable.defaulthead);
                tvName.setText(JsonUtil.getItemString(member, "nickname"));
                tvTime.setText(JsonUtil.getItemString(map, "time"));
                tvIntro.setText(JsonUtil.getItemString(map, "content"));
            }
        }
    }
}
