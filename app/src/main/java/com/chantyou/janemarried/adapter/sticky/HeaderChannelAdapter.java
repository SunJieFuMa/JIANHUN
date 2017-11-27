package com.chantyou.janemarried.adapter.sticky;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.model.ChannelEntity;
import com.chantyou.janemarried.ui.shop.ShopListActivity;

import java.util.List;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderChannelAdapter extends BaseListAdapter<ChannelEntity> {

    public HeaderChannelAdapter(Context context, List<ChannelEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_channel, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ChannelEntity entity = getItem(position);

        holder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
        holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        holder.tvTips = (TextView) convertView.findViewById(R.id.tv_tips);
        holder.rv_channel = (RelativeLayout) convertView.findViewById(R.id.rv_channel);

        holder.tvTitle.setText(entity.getTitle());
        if (TextUtils.isEmpty(entity.getImage_url())) {
            mImageManager.loadResImage(entity.getImage_id(), holder.ivImage);
        } else {
            mImageManager.loadCircleImage(entity.getImage_url(), holder.ivImage);//圆角网络图片
        }
        if (TextUtils.isEmpty(entity.getTips())) {
            holder.tvTips.setVisibility(View.INVISIBLE);
        } else {
            holder.tvTips.setVisibility(View.VISIBLE);
            holder.tvTips.setText(entity.getTips());
        }

        holder.rv_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("zhuwx:切换类型,id:" + entity.getId());
//                //获取商铺列表
//                ((XBaseActivity) mContext).pushEvent(new ShopListRunner("山东", "济南", "历下", entity.getId() + "", "", ""), (XBaseActivity) mContext);
//                System.out.println("zhuwx:请求了");
                System.out.println("zhuwx:merryType is " + entity.getId());
                Intent intent = new Intent(mContext, ShopListActivity.class);
                intent.putExtra("merryType", entity.getId() + "");
                mContext.startActivity(intent);
//                ShopListActivity.launch(mContext, entity.getId());
            }
        });

        return convertView;
    }


    public class ViewHolder {
        private ImageView ivImage;
        private TextView tvTitle;
        private TextView tvTips;
        private RelativeLayout rv_channel;

    }
}
