package com.chantyou.janemarried.ui.guide;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.utils.SPDBHelper;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.utils.SystemUtils;

public class GuidePageActivity extends XBaseActivity implements OnPageChangeListener {

	@ViewInject(R.id.viewpager)
	private ViewPager mViewPager;
//	private GuidePageAdapter mAdapter;
	private float tX;

	private boolean islogin = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mSwipeBackFinish = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guidepage);
		
//		mViewPager = (ViewPager) findViewById(R.id.viewpager);
//		mAdapter = new GuidePageAdapter();
//		mViewPager.setAdapter(mAdapter);

		mViewPager.setAdapter(new GuidePageAdapter());
		mViewPager.setOnPageChangeListener(this);
			mViewPager.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
						switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								tX = event.getX();
								break;
							case MotionEvent.ACTION_MOVE:

								break;
							case MotionEvent.ACTION_UP:
								if (event.getX() - tX < -50) {
									reLoad();
									return true;
								}
						}
					}
					return false;
				}
			});
	}

	private void reLoad(){
		SPDBHelper spdbHelper = new SPDBHelper();
		spdbHelper.putString("guidversion", SystemUtils.getCurVersion(this));
		launch(this, LoginActivity.class);
		finish();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	private final int[] mGuideIds = {R.drawable.ic_guide_one, R.drawable.ic_guide_two,
			R.drawable.ic_guide_three };
//	private final int[] mGuideIds = {R.drawable.guide1, R.drawable.guide2,
//			R.drawable.guide3 };
	private class GuidePageAdapter extends PagerAdapter {
		
		private final SparseArray<ImageView> mPageViews;
		
		public GuidePageAdapter() {
			mPageViews = new SparseArray<ImageView>();
		}

		@Override
		public int getCount() {
			return mGuideIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			ImageView v = mPageViews.get(position);
			if(v == null) {
				v = new ImageView(GuidePageActivity.this);
				v.setScaleType(ScaleType.FIT_XY);
				mPageViews.put(position, v);
				v.setImageResource(mGuideIds[position]);
			}
			
			((ViewPager) container).addView(v);
			return v;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			mPageViews.remove(position);
			((ViewPager) container).removeView((View) object);
		}
	}

	@Override
	public void onPageSelected(int position) {
	}
	
	long time;
	int num;
	@Override
	public void onBackPressed() {
		if(System.currentTimeMillis() - time < 3000) {
			num++;
			if(num > 4) {
				super.onBackPressed();
			}
		} else {
			time = System.currentTimeMillis();
			num = 0;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(GuidePageActivity.this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(GuidePageActivity.this);
	}
}
