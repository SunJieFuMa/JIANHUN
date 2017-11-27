package com.chantyou.janemarried.adapter.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.model.Shop.ShopDemoListBean;
import com.chantyou.janemarried.ui.shop.ShopDemoInfoActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;

//店铺案例列表
public class ShopDemoPullRefreshAdapter extends BaseRecyclerViewAdapter<ShopDemoListBean.DataBean> {

    private Context context;

    public ShopDemoPullRefreshAdapter(Context context) {
        super(context);
        this.context =context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.item_shop_demo));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ShopDemoListBean.DataBean dataBean = getValueAt(position);
//        List<ShopDemoListBean.DataBean> map = getValueAt(position);
//        vh.map = map;
//        //vh.ivDel.setImageResource(JsonUtil.getItemBoolean(map, "candel") ? R.drawable.icon_del : R.drawable.icon_mb);
//        if (JsonUtil.getItemBoolean(map, "candel")) {
//            vh.ivDel.setImageResource(R.drawable.icon_del);
//        }
//
//        if (map != null) {
//            vh.tvName.setText(JsonUtil.getItemString(map, "name"));
//            vh.tvIntro.setText(JsonUtil.getItemString(map, "Brand") + ":"
//                    + JsonUtil.getItemInt(map, "num") + "|"
//                    + JsonUtil.getItemString(map, "tip"));
//            vh.tvMoney.setText(String.format(vh.tvMoney.getResources().getString(R.string.fmt_money), JsonUtil.getItemString(map, "itemTotalPrice")));
//        }



        if (null != dataBean  ) {
            if (!TextUtils.isEmpty(dataBean.getImageUrl1())) {
//                HImageLoader.displayImage(dataBean.getImageUrl1(), viewHolder.iv_image1, R.mipmap.push);
                Glide.with(context).load(dataBean.getImageUrl1()).fitCenter().into(viewHolder.iv_image1);
            }
            if (!TextUtils.isEmpty(dataBean.getImageUrl2())) {
//                HImageLoader.displayImage(dataBean.getImageUrl2(), viewHolder.iv_image2, R.mipmap.push);
                Glide.with(context).load(dataBean.getImageUrl2()).fitCenter().into(viewHolder.iv_image2);
            } else {
                viewHolder.iv_image4.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(dataBean.getImageUrl3())) {
                HImageLoader.displayImage(dataBean.getImageUrl3(), viewHolder.iv_image3, R.mipmap.push);
                Glide.with(context).load(dataBean.getImageUrl3()).fitCenter().into(viewHolder.iv_image3);

            } else {
                viewHolder.iv_image4.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(dataBean.getImageUrl4())) {
//                HImageLoader.displayImage(dataBean.getImageUrl4(), viewHolder.iv_image4, R.mipmap.push);
                Glide.with(context).load(dataBean.getImageUrl4()).fitCenter().placeholder(R.mipmap.push).into(viewHolder.iv_image4);
            } else {
                viewHolder.iv_image4.setVisibility(View.GONE);
            }

            if (null != dataBean.getLabel() && !TextUtils.isEmpty(dataBean.getLabel())) {
                viewHolder.tv_label.setVisibility(View.VISIBLE);
                viewHolder.tv_label.setText(dataBean.getLabel());
            }
//            viewHolder.tv_address.setText(dataBean);

            viewHolder.tv_hotel.setText(dataBean.getHotel());

            viewHolder.tv_demo_name.setText(dataBean.getProductName());


        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_image1;
        ImageView iv_image2;
        ImageView iv_image3;
        ImageView iv_image4;
        TextView tv_address;
        TextView tv_hotel;
        TextView tv_demo_name;
        TextView tv_price;

        TextView tv_label;

        LinearLayout ln_demo_item;

        ShopDemoListBean.DataBean bean;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_hotel = get(itemView, R.id.tv_hotel);
            tv_price = get(itemView, R.id.tv_price);
            iv_image1 = get(itemView, R.id.iv_image1);
            iv_image2 = get(itemView, R.id.iv_image2);
            iv_image3 = get(itemView, R.id.iv_image3);
            iv_image4 = get(itemView, R.id.iv_image4);
            tv_address = get(itemView, R.id.tv_address);
            tv_demo_name = get(itemView, R.id.tv_demo_name);
            ln_demo_item = get(itemView, R.id.ln_demo_item);
            tv_label = get(itemView, R.id.tv_label);

//            tvName = get(itemView, R.id.tvName);
//            tvIntro = get(itemView, R.id.tvIntro);
//            tvMoney = get(itemView, R.id.tvMoney);
//            ivDel = get(itemView, R.id.ivDel);
//            ivEdit = get(itemView, R.id.ivEdit);

            get(itemView, R.id.view_item).setOnClickListener(this);
//            ivDel.setOnClickListener(this);
//            ivEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.view_item:
                    if (bean != null) {
//                        PchListInfoActivity.launch(v.getContext(), JsonUtil.objectToJson(map));
                        System.out.println("zhuwx: demoDetail " +bean.getShopId() + " id:" + bean.getId());
                        ShopDemoInfoActivity.launch(v.getContext(), "案例详情", bean.getShopId(), bean.getId(), bean.getImageUrl1(), bean.getDescs());
                    }
                    break;
//                case R.id.ivEdit:
//                    if (map != null) {
//                        PchListAddActivity.launch(v.getContext(), map);
//                    }
//                    break;
//                case R.id.ivDel:
//                    if (map != null && JsonUtil.getItemBoolean(map, "candel") && map.containsKey("id")) {
//                        showYesOrNoDialog("提示", "删除", "取消", "确定删除该清单", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (which == DialogInterface.BUTTON_POSITIVE) {
//                                    showProgressDialog(null, "正在删除...");
//                                    pushEventEx(false, null, new PchListDelRunner(JsonUtil.getItemInt(map, "id")), PchListActivity.this);
//                                }
//                            }
//                        });
//                    }
//                    break;
            }
        }
    }


}
