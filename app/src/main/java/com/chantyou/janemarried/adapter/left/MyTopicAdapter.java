package com.chantyou.janemarried.adapter.left;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.topic.TopicDetailsActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.ViewUtils;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2015/12/19.
 */
public class MyTopicAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

    public MyTopicAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.adapter_mytopic));
    }

    @Override
    protected boolean isDefBg() {
        return false;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh  = (ViewHolder) holder;
        vh.map = getValueAt(position);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder != null && holder instanceof ViewHolder) {
            ((ViewHolder) holder).setValue();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.ivHead)
        ImageView ivHead;
        @ViewInject(R.id.tvTitle)
        TextView tvTitle;
        @ViewInject(R.id.tvSubTitle)
        TextView tvSubTitle;
        @ViewInject(R.id.tvContent)
        TextView tvContent;
        @ViewInject(R.id.tvVisitor)
        TextView tvVisitor;
        @ViewInject(R.id.tvComment)
        TextView tvComment;
        @ViewInject(R.id.tvTime)
        TextView tvTime;
        Map<String, Object> map;

        public ViewHolder(View view) {
            super(view);
            ViewUtils.inject(this, view);

            ivHead.setImageResource(R.drawable.defaulthead);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TopicDetailsActivity.launch(v.getContext(), JsonUtil.getItemInt(map, "id"));
                }
            });
        }

        void setValue() {
            if(map != null) {
                HImageLoader.displayImage(JsonUtil.getItemString(map, "headPic"), ivHead, R.drawable.defaulthead);
                tvTitle.setText(JsonUtil.getItemString(map, "title"));
                tvContent.setText(JsonUtil.getItemString(map, "content"));
                List<Map<String, Object>> tags = (List<Map<String, Object>>) JsonUtil.jsonToList(JsonUtil.getItemString(map, "tags"));
                String stag = "";
                int size = tags == null ? 0 : tags.size();
                for (int i = 0; i < size; i++) {
                    Map<String, Object> tMap = tags.get(i);
                    stag += JsonUtil.getItemString(tMap, "name");
                    if (i != size - 1) {
                        stag += "/";
                    }
                }

                tvSubTitle.setText(stag);
                tvVisitor.setText(String.valueOf(JsonUtil.getItemInt(map, "isreads")));
                tvComment.setText(String.valueOf(JsonUtil.getItemInt(map, "comments")));
                tvTime.setText(JsonUtil.getItemString(map, "datetime"));
            }
        }
    }
}
