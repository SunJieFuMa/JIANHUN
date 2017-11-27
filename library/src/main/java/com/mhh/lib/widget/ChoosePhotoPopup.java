package com.mhh.lib.widget;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.mhh.lib.R;


public class ChoosePhotoPopup extends XPopupWindow {

	private final View mView;

	public ChoosePhotoPopup(Context context) {
		super(context);
		mView = LayoutInflater.from(context).inflate(
				R.layout.xpopup_choosephoto, null);
		setContentView(mView);
	}

	public void setOnItemClickListener(OnClickListener listener) {
		mView.findViewById(R.id.tvCamera).setOnClickListener(listener);
		mView.findViewById(R.id.tvAlbumChoose).setOnClickListener(listener);
		mView.findViewById(R.id.tvCancel).setOnClickListener(listener);
		mView.findViewById(R.id.popup_topview).setOnClickListener(listener);
	}
}
