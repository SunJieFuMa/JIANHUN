package com.mhh.lib.ui.base;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by j_turn on 2016/3/2.
 * Email 766082577@qq.com
 */
public class ScalePageTransformer implements ViewPager.PageTransformer {

    private float MAX_SCALE = 1.2f;
    private float MIN_SCALE = 0.8f;

    public ScalePageTransformer(float maxScale, float minScale) {
        if(maxScale > 0 && minScale > 0) {
            MAX_SCALE = maxScale;
            MIN_SCALE = minScale;
        }
    }

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {//看不到的一页
            position = -1;
        } else if (position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1 + position : 1 - position;

        float slope = (MAX_SCALE - MIN_SCALE) / 1;
        //一个公式
        float scaleValue = MIN_SCALE + tempScale * slope;
//        page.setScaleX(scaleValue);
//        page.setScaleY(scaleValue);
        ViewHelper.setScaleX(page, scaleValue);
        ViewHelper.setScaleY(page, scaleValue);
    }
}
