package com.mhh.lib.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.mhh.lib.R;


public class XPopupWindow extends PopupWindow {

	private FrameLayout mContentView;
	protected View bg;

	public XPopupWindow(Context context) {
		super(context);
		init(context);
	}

	@SuppressWarnings("deprecation")
	private void init(Context context) {
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setOutsideTouchable(true);
		setAnimationStyle(R.style.PopupAnimation);

		mContentView = new FrameLayout(context);
		bg = new View(context);
		bg.setBackgroundColor(mContentView.getResources().getColor(
				R.color.trans_bg_black));
	}
	
	@Override
	public void dismiss() {
		try {
			mContentView.removeView(bg);
			if (mContentView != null && action != null) {
				mContentView.removeCallbacks(action);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mContentView.postDelayed(new Runnable() {

			@Override
			public void run() {
				supDissmiss();
			}
		}, 40);
	}
	
	private void supDissmiss() {
		super.dismiss();
	}

	private Runnable action;
	private void showAction() {
		try {
			mContentView.setBackgroundColor(Color.TRANSPARENT);
			if(bg != null && bg.getParent() != null && bg.getParent() instanceof ViewGroup) {
				((ViewGroup) bg.getParent()).removeView(bg);
			}
			if(action == null) {
				action = new Runnable() {
					
					@Override
					public void run() {
						if(bg != null && bg.getParent() != null && bg.getParent() instanceof ViewGroup) {
							((ViewGroup) bg.getParent()).removeView(bg);
						}
						if(mContentView != null && bg != null) {
							mContentView.addView(bg, 0, new FrameLayout.LayoutParams(FrameLayout
									.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
						}
					}
				};
			}
			mContentView.removeCallbacks(action);
			mContentView.postDelayed(action, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		showAction();
		super.showAtLocation(parent, gravity, x, y);
	}
	
	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		showAction();
		super.showAsDropDown(anchor, xoff, yoff);
	}
	
	@Override
	public void setContentView(View contentView) {
		if (contentView != null) {
			mContentView.removeAllViews();
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout
					.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.BOTTOM;
			mContentView.addView(contentView, params);
			super.setContentView(mContentView);
		}
	}
}
