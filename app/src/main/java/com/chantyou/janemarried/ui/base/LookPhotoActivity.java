package com.chantyou.janemarried.ui.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.base.CommonPagerAdapter;
import com.chantyou.janemarried.utils.HImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;


public class LookPhotoActivity extends XBaseActivity implements
													ViewPager.OnPageChangeListener{

	
	private ViewPager	mViewPager;
	private MyAdapter	mAdapter;
	private TextView tvIndex;

	public static final void launch(Context context, String pic, ArrayList<String> pics) {
		Intent intent = new Intent(context, LookPhotoActivity.class);
		intent.putExtra("picurl", pic);
		intent.putStringArrayListExtra("urls", pics);
		context.startActivity(intent);
	}

	@Override
	protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
		toolbarAttribute.mHasToolbar = false;
		super.onInitToolbarAttribute(toolbarAttribute);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lookphoto);

		tvIndex = (TextView) findViewById(R.id.tvIndex);

		final String picurl=getIntent().getStringExtra("picurl");
		
		List<String> urls = 
//				(ArrayList<String>)getIntent().getSerializableExtra("urls");
				getIntent().getStringArrayListExtra("urls");
		if(urls == null){
			urls = new ArrayList<String>();
			if(!TextUtils.isEmpty(picurl)){
				urls.add(picurl);
			}
		}
		
		mViewPager = (ViewPager)findViewById(R.id.vp);
		mAdapter = new MyAdapter();
		mAdapter.mUrls.addAll(urls);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setAdapter(mAdapter);
		if(!TextUtils.isEmpty(picurl)){
			mViewPager.setCurrentItem(urls.indexOf(picurl));
		}
		
		setTitle((mViewPager.getCurrentItem() + 1) + "/" + mAdapter.getCount());
	}

	@Override
	public void setTitle(CharSequence title) {
		tvIndex.setText(title);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mViewPager.post(new Runnable() {
			@Override
			public void run() {
//				mEventManager.runEvent(XHEventCode.Set_SlidingTouchModeMargin,
//						SystemUtils.dipToPixel(LookPhotoActivity.this, 50));
			}
		});
		
		//FinalBitmap.create(this).configDisplayer(new XBitmapSimpleDisplayer());
	}

	@Override
	protected void onPause() {
		super.onPause();
		//FinalBitmap.create(this).configDisplayer(new XBitmapDisplayer());
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		setTitle((arg0 + 1) + "/" + mAdapter.getCount());
	}
	
	private class MyAdapter extends CommonPagerAdapter implements OnClickListener {
		
		private final List<String> mUrls = new ArrayList<String>();
		
		@Override
		public void onClick(View v) {
			finish();
		}
		
		@Override
		protected View getView(View v, int nPos) {
			ViewHolder holder;
//			if(v == null) {
				v = LayoutInflater.from(LookPhotoActivity.this).inflate(R.layout.adapter_lookphoto, null);
				holder = new ViewHolder();
				holder.photoView = (PhotoView) v.findViewById(R.id.photo);
				holder.photoView.setOnClickListener(this);
				holder.bar = (ProgressBar) v.findViewById(R.id.pBar);
				v.setTag(R.id.tag_id, holder);
//			} else {
//				holder = (ViewHolder) v.getTag(R.id.tag_id);
//			}

			final String url = mUrls.get(nPos);
//			HImageLoader.displayImage(url, holder.photoView, HImageLoader.createOptions(0, R.color.gray), holder);
			HImageLoader.displayImage(url, holder.photoView, HImageLoader.createOptions(R.color.gray, 0), holder);

			return v;
		}

		@Override
		public int getCount() {
			return mUrls.size();
		}
		
		
		
		class ViewHolder implements ImageLoadingListener {
			 PhotoView photoView;
			 ProgressBar bar;

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					bar .setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					bar .setVisibility(View.GONE);
				}

				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					bar .setVisibility(View.GONE);
				}

				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					bar .setVisibility(View.VISIBLE);
				}
		}


	}
}
