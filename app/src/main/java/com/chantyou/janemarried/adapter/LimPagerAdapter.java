package com.chantyou.janemarried.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by j_turn on 2016/1/11 11:51
 * Email：766082577@qq.com
 */
public abstract class LimPagerAdapter<T> extends PagerAdapter {

    private final SparseArray<View> mSparseViews;

    public LimPagerAdapter() {
        mSparseViews = new SparseArray<View>();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
        mSparseViews.remove(position);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        View v = mSparseViews.get(position);
        if (v == null) {
            v = getView(container, position);
            mSparseViews.put(position, v);
        }
        setView(v, position);
        ((ViewPager) container).addView(v);
        return v;
    }

    protected abstract View getView(View container, int position);

    protected abstract void setView(View view, int position);
}
