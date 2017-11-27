package com.chantyou.janemarried.ui.pay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chantyou.janemarried.base.MyBaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public class MyAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;
    private List<MyBaseFragment> mFragments;

    public MyAdapter(FragmentManager fragmentManager, String[] titles, List<MyBaseFragment> fragments) {
        super(fragmentManager);
        this.mTitles = titles;
        this.mFragments = fragments;
    }


    //别忘了这个方法一定要有
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("getItem看看每次都会被调用吗" + (position + 1));
//        mFragments.get(position).setNewsId(position+1);
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

}
