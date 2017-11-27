package com.chantyou.janemarried.adapter.shop;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.model.Shop.ShopCommentListBean;
import com.chantyou.janemarried.utils.DateTimeUtils;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.view.CircleImageView;

import java.util.List;

/**
 * Created by j_turn on 2015/12/19.
 * Email 766082577@qq.com
 */

public class ShopCommentAdapter extends BaseAdapter {

    private Context context;
    private List<ShopCommentListBean.DataBean> dataBeanList;

    public ShopCommentAdapter(Context context, List<ShopCommentListBean.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }


    @Override
    public int getCount() {
        return dataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View contentView, ViewGroup view) {
        ViewHolder viewHolder;
        if (contentView == null) {
            contentView = LayoutInflater.from(context).inflate(R.layout.item_shop_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.ivHead = (CircleImageView) contentView.findViewById(R.id.ivHead);
            viewHolder.tvName = (TextView) contentView.findViewById(R.id.tvName);
            viewHolder.tvTime = (TextView) contentView.findViewById(R.id.tvTime);
            viewHolder.tvIntro = (TextView) contentView.findViewById(R.id.tvIntro);
            contentView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) contentView.getTag();

        if (null != dataBeanList && dataBeanList.size() > 0) {
            if (!TextUtils.isEmpty(dataBeanList.get(position).getPhoto())) {
                HImageLoader.displayImage(dataBeanList.get(position).getPhoto(), viewHolder.ivHead, R.mipmap.push);
            }
            viewHolder.tvName.setText(dataBeanList.get(position).getNickname());
            viewHolder.tvTime.setText(DateTimeUtils.friendly_time(DateTimeUtils.timeStamp2Date(dataBeanList.get(position).getCreateTim())));
            viewHolder.tvIntro.setText(dataBeanList.get(position).getComments());
        }

        return contentView;
    }


    private static class ViewHolder {
        CircleImageView ivHead;
        TextView tvName;
        TextView tvTime;
        TextView tvIntro;
    }


}
