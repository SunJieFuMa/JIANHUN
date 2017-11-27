package com.chantyou.janemarried.adapter.shop;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.sticky.BaseListAdapter;
import com.chantyou.janemarried.model.Shop.ShopHomeChannelEntity;

import java.util.List;

 //店铺主页
public class ShopHomeChannelAdapter extends BaseListAdapter<ShopHomeChannelEntity> {

    public ShopHomeChannelAdapter(Context context, List<ShopHomeChannelEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_home_channel, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ShopHomeChannelEntity entity = getItem(position);

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

//                System.out.println("zhuwx:shophomechannel is " + entity.getId());
//                Intent intent = new Intent(mContext, ShopListActivity.class);
//                intent.putExtra("merryType", entity.getId() + "");
//                mContext.startActivity(intent);
//                ShopListActivity.launch(mContext, entity.getId());

                switch (entity.getId()) {
                    case 1: {//主页

                    }
                    break;
                    case 2: {//套餐

                    }
                    break;
                    case 3: {//案例

                    }
                    break;
                    case 4: {//评论

                    }
                    break;
                }


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
