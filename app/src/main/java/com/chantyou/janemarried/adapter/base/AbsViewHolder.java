package com.chantyou.janemarried.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by j_turn on 2016/4/19.
 * Email 766082577@qq.com
 */
public class AbsViewHolder extends RecyclerView.ViewHolder {

    View rootView;

    public AbsViewHolder(View itemView) {
        super(itemView);
        this.rootView = itemView;
    }

    public void setData() {

    }
}
