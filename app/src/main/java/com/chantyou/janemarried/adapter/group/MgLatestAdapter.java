package com.chantyou.janemarried.adapter.group;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.base.PhotoAdapter;
import com.chantyou.janemarried.ui.topic.TopicDetailsActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.ViewUtils;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2015/12/16 15:26
 * Email：766082577@qq.com
 */
public class MgLatestAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

    public MgLatestAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.adapter_mglatest));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;

        final Map<String, Object> map = getValueAt(position);
        vh.map = map;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder != null && holder instanceof ViewHolder) {
            ((ViewHolder) holder).setValue();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.ivHead)
        ImageView ivHead;
        @ViewInject(R.id.tvPopular)
        TextView tvPopular;
        @ViewInject(R.id.tvTitle)
        TextView tvTitle;
        @ViewInject(R.id.tvSubTitle)
        TextView tvSubTitle;
        @ViewInject(R.id.tvTag)
        TextView tvTag;
        @ViewInject(R.id.tvContent)
        TextView tvContent;
        @ViewInject(R.id.tvVisitor)
        TextView tvVisitor;
        @ViewInject(R.id.tvTime)
        TextView tvTime;
        @ViewInject(R.id.tvComment)
        TextView tvComment;
        @ViewInject(R.id.list)
        RecyclerView list;

        View view;

        PhotoAdapter adapter;
        Map<String, Object> map;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ViewUtils.inject(this, view);

            adapter = new PhotoAdapter(view.getContext(), SystemUtils.dipToPixel(view.getContext(), 110));
            list.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
            list.setAdapter(adapter);
        }

        void setValue() {
            if (map != null) {
                HImageLoader.displayImage(JsonUtil.getItemString(map, "headPic"), ivHead, R.drawable.defaulthead);
                String popular = JsonUtil.getItemString(map, "popular");
                tvPopular.setText(popular);
                if (TextUtils.isEmpty(popular)) {
                    tvPopular.setVisibility(View.GONE);
                } else {
                    tvPopular.setVisibility(View.VISIBLE);
                }
                String state = JsonUtil.getItemString(map, "phase");
                if (TextUtils.isEmpty(state)) {
                    tvTag.setVisibility(View.INVISIBLE);
                } else {
                    tvTag.setVisibility(View.VISIBLE);
                    tvTag.setText(state);
                }
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
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TopicDetailsActivity.launch(v.getContext(), JsonUtil.getItemInt(map, "id"));
                    }
                });
                list.setAdapter(adapter);
                List<Map<String, Object>> images = (List<Map<String, Object>>) map.get("images");
                List<String> imgs = new ArrayList<>();
                if (images != null) {
                    for (Map<String, Object> it : images) {
                        imgs.add(JsonUtil.getItemString(it, "source"));
                    }
                }
                list.setVisibility(imgs != null && imgs.size() > 0 ? View.VISIBLE : View.GONE);
                adapter.setData(imgs);
            }
        }
    }

}
