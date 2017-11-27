package com.chantyou.janemarried.ui.left.favorite;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.base.BaseTabsActivity;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;

/**
 * Created by j_turn on 2015/12/19.
 * Email 766082577@qq.com
 */
public class MyFavoriteActivity extends BaseTabsActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_favorite;
    }

    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        tabs = (TabLayout) findViewById(R.id.tabs);

    }

    @Override
    protected void setupViewPager(BaseFragmentPagerAdapter adapter) {
        adapter.addFragment(ProductFavoriteFragment.class, getResources().getString(R.string.fav_item1));
        adapter.addFragment(TopicFavoriteFragment.class, getResources().getString(R.string.fav_item2));
        adapter.addFragment(ShopFavoriteFragment.class, getResources().getString(R.string.fav_item3));
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MyFavoriteActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MyFavoriteActivity.this);
    }
}
