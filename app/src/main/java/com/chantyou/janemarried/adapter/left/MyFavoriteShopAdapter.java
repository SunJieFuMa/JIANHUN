package com.chantyou.janemarried.adapter.left;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.manager.ImageManager;
import com.chantyou.janemarried.model.Shop.ShopFavoriteListBean;
import com.chantyou.janemarried.ui.shop.ShopHomeActivity;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.ViewUtils;

/**
 * Created by j_turn on 2015/12/19.
 */
public class MyFavoriteShopAdapter extends BaseRecyclerViewAdapter<ShopFavoriteListBean.DataBean> {

    private Context context;

    public MyFavoriteShopAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.adapter_my_favorite_shop), context);
    }

    @Override
    protected boolean isDefBg() {
        return false;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.dataBean = getValueAt(position);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder != null && holder instanceof ViewHolder) {
            ((ViewHolder) holder).setValue();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv_image)
        ImageView iv_image;
        @ViewInject(R.id.tv_title)
        TextView tv_title;
        @ViewInject(R.id.iv_extend)
        ImageView iv_extend;
        @ViewInject(R.id.iv_bz)
        ImageView iv_bz;
        @ViewInject(R.id.tv_tip_company)
        TextView tv_tip_company;
        @ViewInject(R.id.tv_collection)
        TextView tv_collection;
        @ViewInject(R.id.tv_demo)
        TextView tv_demo;
        @ViewInject(R.id.tv_package)
        TextView tv_package;
        @ViewInject(R.id.tv_rang_tips)
        TextView tv_rang_tips;
        @ViewInject(R.id.tv_rang)
        TextView tv_rang;
        @ViewInject(R.id.ll_root_view)
        LinearLayout ll_root_view;

        ShopFavoriteListBean.DataBean dataBean;
        //        Map<String, Object> map;
        protected ImageManager mImageManager;
        private Context context;

        public ViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            mImageManager = new ImageManager(context);
            ViewUtils.inject(this, view);

            iv_image.setImageResource(R.drawable.defaulthead);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), ShopHomeActivity.class);
//                    intent.putExtra("id", dataBean.getId());
//                    intent.putExtra("shopLevel", shopLevel > 0 ? shopLevel : -1);
//                    v.getContext().startActivity(intent);
////                    TopicDetailsActivity.launch(v.getContext(), JsonUtil.getItemInt(map, "id"));
//                }
//            });
        }

        @SuppressLint("NewApi")
        void setValue() {
            if (dataBean != null) {
                tv_collection.setText("收藏 " + dataBean.getCollectCount());
                tv_demo.setText("案例 " + dataBean.getEgCount());
                tv_package.setText("套餐 " + dataBean.getProductCount());
                tv_rang.setText(dataBean.getBisDescs());//业务范围

//
//                if (1 == dataBean.getIsBill()) {
//                    iv_bz.setVisibility(View.VISIBLE);
//                }

                final int shopLevel = dataBean.getShopLevel();
                if (shopLevel > 0) {
                    tv_tip_company.setVisibility(View.VISIBLE);
                    switch (shopLevel) {
                        case 1: {//一般商家
//                        holder.tv_tip_company.setVisibility(View.VISIBLE);
                            tv_tip_company.setText("商家");
                            tv_tip_company.setBackground(context.getResources().getDrawable(R.drawable.btn_tips_grey));
                        }
                        break;
                        case 2: {//优质商家
//                        holder.tv_tip_company_good.setVisibility(View.VISIBLE);
                            tv_tip_company.setText("优质");
                            tv_tip_company.setTextColor(context.getResources().getColor(R.color.white));
                            tv_tip_company.setBackground(context.getResources().getDrawable(R.drawable.btn_tips_blue));
                        }
                        break;
                        case 3: {//认证商家
//                        holder.tv_tip_company_great.setVisibility(View.VISIBLE);
                            tv_tip_company.setText("认证");
                            tv_tip_company.setTextColor(context.getResources().getColor(R.color.white));
                            tv_tip_company.setBackground(context.getResources().getDrawable(R.drawable.btn_tips_yellow));
                        }
                        break;
                    }
                    tv_title.setText(dataBean.getName());
                    mImageManager.loadUrlImage(dataBean.getImageUrl(), iv_image);
                }

                ll_root_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ShopHomeActivity.class);
                        intent.putExtra("id", dataBean.getId());
                        intent.putExtra("shopLevel", shopLevel > 0 ? shopLevel : -1);
                        System.out.println("zhuwx: 查看店铺："+dataBean.getId());
                        v.getContext().startActivity(intent);
                    }
                });
            }

        }
    }
}
