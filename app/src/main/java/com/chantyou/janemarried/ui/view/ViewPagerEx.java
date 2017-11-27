package com.chantyou.janemarried.ui.view;



import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import com.chantyou.janemarried.AppAndroid;

public class ViewPagerEx extends ViewPager {

	private boolean mCanScroll = true;

	public ViewPagerEx(Context context) {
		super(context);
	}

	public ViewPagerEx(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		if (mCanScroll) {
			final int action = e.getAction();
			float fx = e.getX();
			if (action == MotionEvent.ACTION_DOWN) {
				PagerAdapter adapter = getAdapter();
				if (adapter != null) {
					final int pos = getCurrentItem();
					if (pos == 0) {
						if (pos != adapter.getCount() - 1) {
							if (fx < AppAndroid.getScreenWidth() / 2) {

							} else if (fx > AppAndroid.getScreenWidth() / 2) {
								ViewParent vp = getParent();
								if (vp != null) {
									vp.requestDisallowInterceptTouchEvent(true);
								}
							}
						}
					} else if (pos == adapter.getCount() - 1) {
						if (fx < AppAndroid.getScreenWidth() / 2) {
							ViewParent vp = getParent();
							if (vp != null) {
								vp.requestDisallowInterceptTouchEvent(true);
							}
						}
					} else {
						ViewParent vp = getParent();
						if (vp != null) {
							vp.requestDisallowInterceptTouchEvent(true);
						}
					}
				}
			}
			return super.onInterceptTouchEvent(e);
		} else {
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (mCanScroll) {
			try{
				return super.onTouchEvent(e);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return false;
		} else {
			return false;
		}
	}

	public void setCanScroll(boolean bScroll) {
		mCanScroll = bScroll;
	}
}
