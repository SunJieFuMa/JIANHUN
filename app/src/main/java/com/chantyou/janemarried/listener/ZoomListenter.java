package com.chantyou.janemarried.listener;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;

/**
 * Created by Administrator on 2017/1/6.
 */
public class ZoomListenter implements View.OnTouchListener {
    private int mode = 0;
    float oldDist;
    /*
    将这个文字大小保存起来，到SP中，实现别的item也显示同等大小
     */
    float textSize = 0;

    TextView textView = null;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        textView = (TextView) v;
        if (textSize == 0) {
            textSize = textView.getTextSize();
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //第一个手指按下
                mode = 1;
                break;
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode -= 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //第二个手指按下
                /*
                * 由于在项目中textview的父控件的父控件的父控件是一个NestedScrollView，
                * 为了不和textview的双指缩放事件冲突，就要在第二个手指按下的时候请求父控件不拦截事件，
                * 交给textview来处理，实现双指缩放文字大小
                * */
                textView.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                oldDist = spacing(event);
                mode += 1;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode >= 2) {
                    float newDist = spacing(event);
                    if (newDist > oldDist + 1) {
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                    }
                    if (newDist < oldDist - 1) {
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                    }
                }
                break;
        }
        return true;
    }

    private void zoom(float f) {
        textSize *= f;
        if (textSize<10||textSize>40){
            return;
        }
        textView.setTextSize(textSize);
        /*
    将这个文字大小保存起来，到SP中，实现别的item也显示同等大小
     */
        SharedPreferenceUtils.putFloat(AppAndroid.getContext(),"speech_textsize",textSize);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
