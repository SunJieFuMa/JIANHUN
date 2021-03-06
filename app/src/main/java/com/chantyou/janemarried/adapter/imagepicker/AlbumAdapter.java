package com.chantyou.janemarried.adapter.imagepicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chantyou.janemarried.model.imagepicker.AlbumModel;
import com.chantyou.janemarried.view.imagepicker.AlbumItem;

import java.util.ArrayList;


/**
 * Created by fengyongge on 2016/5/24
 */
public class AlbumAdapter extends MBaseAdapter<AlbumModel> {
	public AlbumAdapter(Context context, ArrayList<AlbumModel> models) {
		super(context, models);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AlbumItem albumItem = null;
		if (convertView == null) {
			albumItem = new AlbumItem(context);
			convertView = albumItem;
		} else
			albumItem = (AlbumItem) convertView;
		albumItem.update(models.get(position));
		return convertView;
	}

}
