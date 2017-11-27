package com.chantyou.janemarried.ui.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2016/4/2.
 * Email 766082577@qq.com
 */
public class AutoScrollViewAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private final SparseArray<View> mSparseViews;

    private List<Map<String, Object>> mImages = new ArrayList<>();
    ViewPagerFocusView autoPoints;

    public AutoScrollViewAdapter(ViewPagerFocusView autoPoints) {
        mSparseViews = new SparseArray<View>();
        this.autoPoints = autoPoints;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        autoPoints.setCount(mImages.size());
    }

    public void setData(List<Map<String, Object>> images) {
        mImages.clear();
        if(images != null) {
            mImages.addAll(images);
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
        mSparseViews.remove(getRealPos(position));
    }

    private int getRealPos(int position) {
        return mImages.size() == 0 ? 0 : position % mImages.size();
    }

    @Override
    public Object instantiateItem(View container, int position) {
        int pos = getRealPos(position);
        View v = mSparseViews.get(pos);
        if (v == null) {
            v = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_autoimg, null);
            mSparseViews.put(pos, v);
        }
        if(v.getParent() != null) {
            ((ViewGroup) v.getParent()).removeView(v);
        }
        Map<String, Object> map =  pos < mImages.size() ? mImages.get(pos) : null;
        if(map != null) {
            HImageLoader.displayImage(JsonUtil.getItemString(map, "source"), ((ImageView) v.findViewById(R.id.iv)), R.color.white_gray);
        }
        ((ViewPager) container).addView(v);
        return v;
    }

    @Override
    public int getCount() {
        return  mImages.size() <= 1 ? mImages.size() : Integer.MAX_VALUE;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        try {
            autoPoints.setCurrentIndex(getRealPos(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
