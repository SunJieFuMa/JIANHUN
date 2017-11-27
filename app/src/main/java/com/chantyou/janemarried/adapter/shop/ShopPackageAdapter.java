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

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.model.Shop.ShopPackageListBean;
import com.chantyou.janemarried.ui.shop.ShopPackageInfoActivity;
import com.chantyou.janemarried.utils.HImageLoader;

import java.util.List;

/**
 * Created by j_turn on 2015/12/19.
 * Email 766082577@qq.com
 */

public class ShopPackageAdapter extends BaseAdapter {

    private Context context;
    private List<ShopPackageListBean.DataBean> dataBeanList;
    private String shopPhone;

    public ShopPackageAdapter(Context context, List<ShopPackageListBean.DataBean> dataBeanList, String shopPhone) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.shopPhone=shopPhone;//传递进来的商家电话
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
            contentView = LayoutInflater.from(context).inflate(R.layout.item_shop_package, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (TextView) contentView.findViewById(R.id.tv_title);
            viewHolder.tv_desc = (TextView) contentView.findViewById(R.id.tv_desc);
            viewHolder.tv_price = (TextView) contentView.findViewById(R.id.tv_price);
            viewHolder.iv_head = (ImageView) contentView.findViewById(R.id.iv_head);
            viewHolder.ln_package_item = (LinearLayout) contentView.findViewById(R.id.ln_package_item);
            contentView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) contentView.getTag();

        if (null != dataBeanList && dataBeanList.size() > 0) {
            if (!TextUtils.isEmpty(dataBeanList.get(position).getImageUrl())) {
                HImageLoader.displayImage(dataBeanList.get(position).getImageUrl(), viewHolder.iv_head, R.mipmap.push);
            }
            viewHolder.tv_title.setText(dataBeanList.get(position).getName());
            viewHolder.tv_desc.setText(dataBeanList.get(position).getLabel());
            viewHolder.tv_price.setText("￥" + dataBeanList.get(position).getPrice());
        }

        viewHolder.ln_package_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("zhuwx:package " + dataBeanList.get(position).getShopId() + " id:" + dataBeanList.get(position).getId());
                ShopPackageInfoActivity.launch(context,
                        dataBeanList.get(position).getShopId(),
                        dataBeanList.get(position).getId(),
                        dataBeanList.get(position).getImageUrl(),
                        dataBeanList.get(position).getHotelName(),
                        dataBeanList.get(position).getName(),
                        dataBeanList.get(position).getPrice() + "",
                        dataBeanList.get(position).getLabel(),
                        shopPhone);//我在这里加了一个参数---商家电话
            }
        });

        return contentView;
    }


    private static class ViewHolder {
        ImageView iv_head;
        TextView tv_title;
        TextView tv_desc;
        TextView tv_price;
        LinearLayout ln_package_item;
    }


}
