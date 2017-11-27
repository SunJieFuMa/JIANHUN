package com.chantyou.janemarried.model.Shop;

import java.util.Comparator;

/**
 * Created by sunfusheng on 16/4/25.
 */
public class ShopListBeanComparator implements Comparator<ShopListBean.DataBean> {

    @Override
    public int compare(ShopListBean.DataBean lhs, ShopListBean.DataBean rhs) {
        return rhs.getRank() - lhs.getRank();
    }
}
