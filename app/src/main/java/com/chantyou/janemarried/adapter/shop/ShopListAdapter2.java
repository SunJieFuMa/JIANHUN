package com.chantyou.janemarried.adapter.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.sticky.BaseListAdapter;
import com.chantyou.janemarried.model.Shop.ShopListBean;
import com.chantyou.janemarried.ui.shop.ShopHomeActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sunfusheng on 16/4/20.
 */
//商铺列表
public class ShopListAdapter2 extends BaseListAdapter<ShopListBean.DataBean> {

    public boolean isNoData;
    private int mHeight;
    public static final int ONE_SCREEN_COUNT = 5; // 一屏能显示的个数，这个根据屏幕高度和各自的需求定
    public static final int ONE_REQUEST_COUNT = 10; // 一次请求的个数

    private List<ShopListBean.DataBean> shopListEntityList;


    public ShopListAdapter2(Context context) {
        super(context);
    }

    public ShopListAdapter2(Context context, List<ShopListBean.DataBean> list) {
        super(context, list);
        shopListEntityList = list;
    }

    // 设置数据
    public void setData(List<ShopListBean.DataBean> list) {
//        clearAll();
//        addALL(list);

        isNoData = false;
        if (list.size() == 1 && list.get(0).isNoData()) {
            // 暂无数据布局
            isNoData = list.get(0).isNoData();
            mHeight = list.get(0).getHeight();
        } else {
            // 添加空数据
            if (list.size() < ONE_SCREEN_COUNT) {
                shopListEntityList.addAll(createEmptyList(ONE_SCREEN_COUNT - list.size()));
            }
        }
        notifyDataSetChanged();
    }

    // 创建不满一屏的空数据
    public List<ShopListBean.DataBean> createEmptyList(int size) {
        List<ShopListBean.DataBean> emptyList = new ArrayList<>();
        if (size <= 0) return emptyList;
        for (int i = 0; i < size; i++) {
            emptyList.add(new ShopListBean.DataBean(true));
        }
        return emptyList;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 暂无数据
        if (isNoData) {
            convertView = mInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
            RelativeLayout rootView = (RelativeLayout) convertView.findViewById(R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }

//        // 正常数据
//        final ViewHolder holder;
//        if (convertView != null && convertView instanceof LinearLayout) {
//            holder = (ViewHolder) convertView.getTag();
//        } else {
//            convertView = mInflater.inflate(R.layout.item_shop, null);
//            holder = new ViewHolder();
//            convertView.setTag(holder);
//        }

        convertView = mInflater.inflate(R.layout.item_shop, null);
        ViewHolder holder = new ViewHolder();
        convertView.setTag(holder);


        ShopListBean.DataBean entity = shopListEntityList.get(position);
//        ShopListBean.DataBean entity = getItem(position);


        if (!entity.isVisualData()) {
            holder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.llRootView = (LinearLayout) convertView.findViewById(R.id.ll_root_view);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_tip_company = (TextView) convertView.findViewById(R.id.tv_tip_company);//一般商家
            holder.tv_tip_company_good = (TextView) convertView.findViewById(R.id.tv_tip_company_good);//认证
            holder.tv_tip_company_great = (TextView) convertView.findViewById(R.id.tv_tip_company_great);//优质

            holder.iv_bz = (ImageView) convertView.findViewById(R.id.iv_bz);//保证金

            holder.tv_collection = (TextView) convertView.findViewById(R.id.tv_collection);//收藏
            holder.tv_demo = (TextView) convertView.findViewById(R.id.tv_demo);//案例
            holder.tv_package = (TextView) convertView.findViewById(R.id.tv_package);//套餐
            holder.tv_rang = (TextView) convertView.findViewById(R.id.tv_rang);//业务范围
            holder.iv_rang = (ImageView) convertView.findViewById(R.id.iv_rang);//业务范围
            holder.tv_rang_tips = (TextView) convertView.findViewById(R.id.tv_rang_tips);//业务范围

            holder.tv_collection.setText("收藏 " + entity.getCollectCount());
            holder.tv_demo.setText("案例 " + entity.getEgCount());
            holder.tv_package.setText("套餐 " + entity.getProductCount());
            holder.tv_rang.setText(entity.getBisDescs());//业务范围

            if (1 == entity.getIsBill()) {
                holder.iv_bz.setVisibility(View.VISIBLE);
            }

            final int shopLevel = entity.getShopLevel();
            if (shopLevel > 0) {
                holder.tv_tip_company.setVisibility(View.VISIBLE);
                switch (shopLevel) {
                    case 1: {//一般商家
//                        holder.tv_tip_company.setVisibility(View.VISIBLE);
                        holder.tv_tip_company.setText("商家");
                        holder.tv_tip_company.setBackground(mContext.getResources().getDrawable(R.drawable.btn_tips_grey));
                    }
                    break;
                    case 2: {//优质商家
//                        holder.tv_tip_company_good.setVisibility(View.VISIBLE);
                        holder.tv_tip_company.setText("优质");
                        holder.tv_tip_company.setTextColor(mContext.getResources().getColor(R.color.white));
                        holder.tv_tip_company.setBackground(mContext.getResources().getDrawable(R.drawable.btn_tips_blue));
                    }
                    break;
                    case 3: {//认证商家
//                        holder.tv_tip_company_great.setVisibility(View.VISIBLE);
                        holder.tv_tip_company.setText("认证");
                        holder.tv_tip_company.setTextColor(mContext.getResources().getColor(R.color.white));
                        holder.tv_tip_company.setBackground(mContext.getResources().getDrawable(R.drawable.btn_tips_yellow));
                    }
                    break;
                }
                holder.tvTitle.setText(entity.getName());
                mImageManager.loadUrlImage(entity.getImageUrl(), holder.ivImage);
            }

//            holder.iv_rang.setVisibility(View.VISIBLE);
            holder.tv_rang_tips.setVisibility(View.VISIBLE);//业务范围
            holder.iv_extend = (ImageView) convertView.findViewById(R.id.iv_extend);
            if (1 == entity.getIsSpread()) {
                holder.iv_extend.setVisibility(View.VISIBLE);
            }
            holder.llRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("zhuwx:id:" + shopListEntityList.get(position).getId());
                    System.out.println("zhuwx:shopId:" + shopListEntityList.get(position).getShopId());
                    Intent intent = new Intent(mContext, ShopHomeActivity.class);
                    intent.putExtra("id", shopListEntityList.get(position).getId());
                    intent.putExtra("shopLevel", shopLevel > 0 ? shopLevel : -1);
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.line = (View) convertView.findViewById(R.id.line);
            holder.ln_shop = (LinearLayout) convertView.findViewById(R.id.ln_shop);
            holder.line.setVisibility(View.GONE);
            holder.ln_shop.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }

    public class ViewHolder {
        private LinearLayout llRootView;
        private ImageView ivImage;
        private TextView tvTitle;
        private TextView tv_collection;//收藏
        private TextView tv_demo;//案例
        private TextView tv_package;//套餐
        private ImageView iv_extend;//推广

        private ImageView iv_bz;//保证金

        private TextView tv_rang;//业务范围
        private ImageView iv_rang;//业务范围
        private TextView tv_rang_tips;//业务范围

        private TextView tv_tip_company;//一般
        private TextView tv_tip_company_good;//认证
        private TextView tv_tip_company_great;//优质

        private View line;
        private LinearLayout ln_shop;
    }
}
