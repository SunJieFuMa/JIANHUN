package com.chantyou.janemarried.ui.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.chantyou.janemarried.R;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;

/**
 * Created by j_turn on 2015/12/19 14:24
 * Email：766082577@qq.com
 */
public abstract class BaseTabsActivity extends XBaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        super.setContentView(getLayoutResId());
    }

    @Override
    protected void init() {
        super.init();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (viewPager != null) {
            BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
            setupViewPager(adapter);
            viewPager.setAdapter(adapter);
            if(tabLayout != null) {
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                tabLayout.setupWithViewPager(viewPager);
            }
        }
    }

    /**
     *
     * @return
     */
    protected int getLayoutResId() {
        return R.layout.activity_basetabs;
    }

    /**
     * 禁用
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        throw new RuntimeException("请用getLayoutResId()方法设置布局文件Id");
    }

    protected abstract void setupViewPager(BaseFragmentPagerAdapter adapter);
}
