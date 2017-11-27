package com.chantyou.janemarried.utils;

import android.widget.ImageView;

import com.chantyou.janemarried.AppAndroid;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

public class SimpleImageAware extends ImageViewAware {
	
	private int width = AppAndroid.getScreenWidth();
	private int height = width;

	public SimpleImageAware(ImageView imageView, int width, int height) {
		super(imageView, true);
		if(width > 10) {
			this.width = width;
		}
		if(height > 10) {
			this.height = height;
		}
	}
	
	public SimpleImageAware(ImageView imageView, boolean checkActualViewSize) {
		super(imageView, checkActualViewSize);
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
}
