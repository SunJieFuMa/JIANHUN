package com.mhh.lib.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by j_turn on 2015/12/16.
 * Email 766082577@qq.com
 */
public class BaseFragment extends Fragment {

    protected View mRootView;

    protected void onInitView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState, View root) {

    }

    protected int getLayoutId() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            if (mRootView == null) {
                mRootView = inflater.inflate(getLayoutId(), container, false);
                if(mRootView != null) {
                    interceptTouchEvent(mRootView, true);
                    onInitView(inflater, container, savedInstanceState, mRootView);
                }
            } else {
                if (mRootView.getParent() != null && mRootView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) mRootView.getParent()).removeView(mRootView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRootView;
    }

    /**
     * Fragment布局是否拦截事件
     *
     * @param view
     * @param interceptEvent true拦截|false不拦截
     */
    private void interceptTouchEvent(View view, boolean interceptEvent) {
        if (interceptEvent) {
            if (view != null) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
        } else {
            if (view != null) {
                view.setOnTouchListener(null);
            }
        }
    }
}
