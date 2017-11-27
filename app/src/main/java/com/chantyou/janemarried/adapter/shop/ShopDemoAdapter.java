package com.chantyou.janemarried.adapter.shop;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.model.Shop.ShopDemoListBean;
import com.chantyou.janemarried.ui.shop.ShopDemoInfoActivity;
import com.chantyou.janemarried.utils.HImageLoader;

import java.util.List;

//店铺案例列表
public class ShopDemoAdapter extends BaseAdapter {

    private Context context;
    private List<ShopDemoListBean.DataBean> dataBeanList;

    public ShopDemoAdapter(Context context, List<ShopDemoListBean.DataBean> dataBeanList) {
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
            contentView = LayoutInflater.from(context).inflate(R.layout.item_shop_demo, null);
            viewHolder = new ViewHolder();
//            viewHolder.tv_title = (TextView) contentView.findViewById(R.id.tv_title);
//            viewHolder.tv_desc = (TextView) contentView.findViewById(R.id.tv_desc);
            viewHolder.tv_hotel = (TextView) contentView.findViewById(R.id.tv_hotel);
            viewHolder.tv_price = (TextView) contentView.findViewById(R.id.tv_price);
            viewHolder.iv_image1 = (ImageView) contentView.findViewById(R.id.iv_image1);
            viewHolder.iv_image2 = (ImageView) contentView.findViewById(R.id.iv_image2);
            viewHolder.iv_image3 = (ImageView) contentView.findViewById(R.id.iv_image3);
//            viewHolder.iv_image4 = (ImageView) contentView.findViewById(R.id.iv_image4);
            viewHolder.tv_address = (TextView) contentView.findViewById(R.id.tv_address);
            viewHolder.tv_demo_name = (TextView) contentView.findViewById(R.id.tv_demo_name);
            viewHolder.ln_demo_item = (LinearLayout) contentView.findViewById(R.id.ln_demo_item);
            viewHolder.tv_label = (TextView) contentView.findViewById(R.id.tv_label);
            contentView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) contentView.getTag();

        if (null != dataBeanList && dataBeanList.size() > 0) {
            if (!TextUtils.isEmpty(dataBeanList.get(position).getImageUrl1())) {
//                HImageLoader.displayImage(dataBeanList.get(position).getImageUrl1(), viewHolder.iv_image1, R.mipmap.push);
                Glide.with(context).load(dataBeanList.get(position).getImageUrl1()).fitCenter().into(viewHolder.iv_image1);
            }
            if (!TextUtils.isEmpty(dataBeanList.get(position).getImageUrl2())) {
//                HImageLoader.displayImage(dataBeanList.get(position).getImageUrl2(), viewHolder.iv_image2, R.mipmap.push);
                Glide.with(context).load(dataBeanList.get(position).getImageUrl2()).fitCenter().into(viewHolder.iv_image2);
            } else {
                viewHolder.iv_image2.setVisibility(View.INVISIBLE);
            }
            if (!TextUtils.isEmpty(dataBeanList.get(position).getImageUrl3())) {
                HImageLoader.displayImage(dataBeanList.get(position).getImageUrl3(), viewHolder.iv_image3, R.mipmap.push);
                Glide.with(context).load(dataBeanList.get(position).getImageUrl3()).fitCenter().into(viewHolder.iv_image3);

            } else {
                viewHolder.iv_image3.setVisibility(View.INVISIBLE);
            }
//            if (!TextUtils.isEmpty(dataBeanList.get(position).getImageUrl4())) {
////                HImageLoader.displayImage(dataBeanList.get(position).getImageUrl4(), viewHolder.iv_image4, R.mipmap.push);
//                Glide.with(context).load(dataBeanList.get(position).getImageUrl4()).fitCenter().placeholder(R.mipmap.push).into(viewHolder.iv_image4);
//            } else {
//                viewHolder.iv_image4.setVisibility(View.GONE);
//            }

            if (null != dataBeanList.get(position).getLabel() && !TextUtils.isEmpty(dataBeanList.get(position).getLabel())) {
                viewHolder.tv_label.setVisibility(View.VISIBLE);
                viewHolder.tv_label.setText(dataBeanList.get(position).getLabel());
            }
//            viewHolder.tv_address.setText(dataBeanList.get(position));

            viewHolder.tv_hotel.setText(dataBeanList.get(position).getHotel());

            viewHolder.tv_demo_name.setText(dataBeanList.get(position).getProductName());

//            viewHolder.tv_title.setText(dataBeanList.get(position).getHotelName());
//            viewHolder.tv_desc.setText(dataBeanList.get(position).getDescs());

            //案例价格
//            if(!TextUtils.isEmpty(dataBeanList.get(position).getPrice())){
//                viewHolder.tv_price.setVisibility(View.VISIBLE);
//                viewHolder.tv_price.setText("￥" + dataBeanList.get(position).getPrice());
//            }
        }

        viewHolder.ln_demo_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("zhuwx: demoDetail " + dataBeanList.get(position).getShopId() + " id:" + dataBeanList.get(position).getId());
                ShopDemoInfoActivity.launch(context, "案例详情", dataBeanList.get(position).getShopId(), dataBeanList.get(position).getId(), dataBeanList.get(position).getImageUrl1(), dataBeanList.get(position).getDescs());
            }
        });

        return contentView;
    }


    private static class ViewHolder {
        ImageView iv_image1;
        ImageView iv_image2;
        ImageView iv_image3;
        //        ImageView iv_image4;
        TextView tv_address;
        TextView tv_hotel;
        TextView tv_demo_name;
        TextView tv_price;

        TextView tv_label;

        //
//        TextView tv_title;
//        TextView tv_desc;
        LinearLayout ln_demo_item;
    }


}
