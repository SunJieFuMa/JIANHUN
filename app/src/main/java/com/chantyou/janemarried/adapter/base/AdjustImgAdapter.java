package com.chantyou.janemarried.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;

/**
 * Created by j_turn on 2015/12/20 10:39
 * Email：766082577@qq.com
 */
public class AdjustImgAdapter extends BaseRecyclerViewAdapter<String> {

//    public int count = 3;
    static int maxW, maxH;
//    int [] ivs = new int[]{R.drawable.ic_marinfo_pic1,
//            R.drawable.ic_marinfo_pic2,R.drawable.ic_marinfo_pic3};

    public AdjustImgAdapter(Context context) {
        super(context);
        maxW = AppAndroid.getScreenWidth() + 10;
        maxH = maxW * 5;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoler(inflateView(parent, R.layout.adapter_adjustimg));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHoler vh = (ViewHoler) holder;
        HImageLoader.displayImage(getValueAt(position), vh.iv, R.color.white_gray);
    }

    private static class ViewHoler extends RecyclerView.ViewHolder {

        ImageView iv;

        public ViewHoler(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            iv.setAdjustViewBounds(true);
            iv.setMaxWidth(maxW);
            iv.setMaxHeight(maxH);
        }
    }
}
