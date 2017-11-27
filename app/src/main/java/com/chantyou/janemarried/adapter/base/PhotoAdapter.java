package com.chantyou.janemarried.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.base.LookPhotoActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.utils.SystemUtils;
import com.mhh.lib.view.SquareImageView;

import java.util.ArrayList;

/**
 * Created by j_turn on 2016/2/20.
 * Email 766082577@qq.com
 */
public class PhotoAdapter extends BaseRecyclerViewAdapter<String> {

    int width;

    public PhotoAdapter(Context context, int width) {
        super(context);
        this.width = width;
        if(width <= 0) {
            this.width = SystemUtils.dipToPixel(context, 60);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.adapter_lib_squareimg));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        String pic =getValueAt(position);
        vh.pic = pic;
        HImageLoader.displayImage(pic, vh.iv, R.color.white_gray);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        SquareImageView iv;
        String pic;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (SquareImageView) itemView.findViewById(R.id.iv);
            iv.getLayoutParams().width = width;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        LookPhotoActivity.launch(v.getContext(), pic, (ArrayList<String>) getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
