package com.chantyou.janemarried.adapter.sticky;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chantyou.janemarried.model.Shop.ShopADListBean;
import com.chantyou.janemarried.ui.shop.ShopADWebViewActivity;

import java.util.List;

//顶部轮播图  广告 adapter
public class HeaderAdAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> ivList; // ImageView的集合
    private int count = 1; // 广告的数量

    private List<ShopADListBean.DataBean> dataBeanList;


    public HeaderAdAdapter(Context context, List<ImageView> ivList) {
        super();
        this.mContext = context;
        this.ivList = ivList;
        if (ivList != null && ivList.size() > 0) {
            count = ivList.size();
        }
    }

    @Override
    public int getCount() {
        if (count == 1) {
            return 1;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final int newPosition = position % count;
        if (null == ivList || ivList.size() < 1) {
            return null;
        }
        // 先移除在添加，更新图片在container中的位置（把iv放至container末尾）
        ImageView iv = ivList.get(newPosition);
        container.removeView(iv);
        container.addView(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != dataBeanList && dataBeanList.size() > 0) {
                    if (1 == dataBeanList.get(newPosition).getIsHref()) {
                        Intent intent = new Intent(mContext, ShopADWebViewActivity.class);
                        intent.putExtra("url", dataBeanList.get(newPosition).getHrefUrl());
                        mContext.startActivity(intent);
                    }

                }
            }
        });


        return iv;
    }

    public List<ShopADListBean.DataBean> getDataBeanList() {
        return dataBeanList;
    }

    public void setDataBeanList(List<ShopADListBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }
}
