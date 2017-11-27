package com.chantyou.janemarried.adapter.group;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.topic.TopicDetailsActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.chantyou.janemarried.view.GlideRoundTransform;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.ViewUtils;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2015/12/16.
 * Email 766082577@qq.com
 */
public class MgChoiceAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

    public MgChoiceAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.adapter_mgchoice));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        final Map<String, Object> map = getValueAt(position);
//        vh.ivbg.setImageResource(R.drawable.main_banner);
        vh.map = map;
//        vh.setValue();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setValue();
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.ivbg)
        ImageView ivbg;
        @ViewInject(R.id.ivHead)
        ImageView ivHead;
        @ViewInject(R.id.tvTitle)
        TextView tvTitle;
        @ViewInject(R.id.tvSubTitle)
        TextView tvSubTitle;
        @ViewInject(R.id.tvVisitor)
        TextView tvVisitor;
        @ViewInject(R.id.tvComment)
        TextView tvComment;
        @ViewInject(R.id.tv_auth)
        TextView tv_auth;
        Map<String, Object> map;

        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ViewUtils.inject(this, view);
        }

        public void setValue() {
            if (map != null) {
                List<Map<String, Object>> images = (List<Map<String, Object>>) map.get("images");
                String image = null;
                if (images != null && images.size() > 0) {
                    image = JsonUtil.getItemString(images.get(0), "source");
                }
                /*if (image==null || TextUtils.isEmpty(image)){
                    ivbg.setImageResource(R.drawable.huati);
                }else {
                    HImageLoader.displayImage(image, ivbg, R.drawable.huati);
                }*/

//                HImageLoader.displayImage(image, ivbg, R.drawable.huati);

                if (image==null || TextUtils.isEmpty(image)){
                    Glide.with(AppAndroid.getContext()).load(R.drawable.huati)
                            .transform(new GlideRoundTransform(AppAndroid.getContext(), 15)).into(ivbg);
                }else {
                    Glide.with(AppAndroid.getContext()).load(image)
                        .transform(new GlideRoundTransform(AppAndroid.getContext(), 15)).into(ivbg);
                }
                HImageLoader.displayImage(JsonUtil.getItemString(map, "headPic"), ivHead, R.drawable.defaulthead);
                String title = JsonUtil.getItemString(map, "title");
                if (TextUtils.isEmpty(title)) {
                    title = JsonUtil.getItemString(map, "content");
                }
                tvTitle.setText(title);
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
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TopicDetailsActivity.launch(v.getContext(), JsonUtil.getItemInt(map, "id"));
                    }
                });

                tv_auth.setText(JsonUtil.getItemString(map, "nickname"));
            }
        }
    }
}
