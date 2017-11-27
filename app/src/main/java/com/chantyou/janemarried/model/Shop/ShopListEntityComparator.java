package com.chantyou.janemarried.model.Shop;

import java.util.Comparator;

/**
 * Created by sunfusheng on 16/4/25.
 */
public class ShopListEntityComparator implements Comparator<ShopListEntity> {

    @Override
    public int compare(ShopListEntity lhs, ShopListEntity rhs) {
        return rhs.getRank() - lhs.getRank();
    }
}
