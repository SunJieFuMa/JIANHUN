//package com.mhh.lib.ui.helper;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//
//import com.huili.readingclub.MyApplication;
//import com.huili.readingclub.NameObject;
//import com.huili.readingclub.R;
//import com.huili.readingclub.utils.Const;
//import com.huili.readingclub.utils.HImageLoader;
//import com.huili.readingclub.widget.CustomToast;
//
//
///**
// * 手机上图片选择页面
// * @author mhh
// *
// */
//public class ChoosePictureActivity extends AbsChoosePhotoHelper implements
//												AdapterView.OnItemClickListener{
//
//	public static final String EXTRA_RETURN_PICS = "pics";
//
//	protected GridView				mGridView;
//	protected ChoosePictureAdapter 	mAdapter;
//
//	protected int					mMaxChooseCount;
//
//	@SuppressWarnings("deprecation")
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_choosepicture);
//		setTitle("选择照片");
//
//		mMaxChooseCount = getIntent().getIntExtra("maxchoosecount", -1);
//
//		mGridView = (GridView)findViewById(R.id.gv);
//		mAdapter = new ChoosePictureAdapter();
//		mGridView.setOnItemClickListener(this);
//		mGridView.setAdapter(mAdapter);
//		final Dialog dialog = Const.createAnimationDailog(this);
//		dialog.show();
// 		new Thread(){
//			@Override
//			public void run() {
//				List<NameObject> datas = null;
//				Cursor c = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//						new String[]{MediaStore.Images.ImageColumns.DATA,MediaStore.Images.ImageColumns.DISPLAY_NAME},
//						null, null, MediaStore.Images.ImageColumns.DATE_ADDED + " DESC");
//				if(c != null && c.moveToFirst()){
//					datas = new ArrayList<NameObject>();
//					do{
//						NameObject no = new NameObject(c.getString(
//								c.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
//						no.setName(c.getString(
//								c.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)));
//						datas.add(no);
//					}while(c.moveToNext());
//				}
//
//				final List<NameObject> result = datas;
//
//				MyApplication.getMainThreadHandler().post(new Runnable() {
//					@Override
//					public void run() {
//						dialog.dismiss();
//						if(result != null){
//							mAdapter.replaceAll(result);
//						}
//					}
//				});
//			}
//		}.start();
//		showRightSave(false);
//		findViewById(R.id.title_left_tv).setOnClickListener(this);
//		findViewById(R.id.title_right_tv).setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.title_left_tv:
//			finish();
//			break;
//		case R.id.title_right_tv:
//			onTitleRightButtonClicked(v);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	private void showRightSave(boolean show) {
//		try {
//			findViewById(R.id.title_right_tv).setVisibility(show ? View.VISIBLE : View.INVISIBLE);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//	}
//
//	public static void launchForResult(Activity activity,int maxChooseCount,int requestCode){
//		Intent i = new Intent(activity, ChoosePictureActivity.class);
//		i.putExtra("maxchoosecount", maxChooseCount);
//		activity.startActivityForResult(i, requestCode);
//	}
//
//	private void onTitleRightButtonClicked(View v) {
//		if(mAdapter.getChooseCount() > 0){
//			Intent data = new Intent();
//			data.putExtra(EXTRA_RETURN_PICS,
//					new ArrayList<NameObject>(mAdapter.getChoosePictures()));
//			setResult(RESULT_OK, data);
//			finish();
//		}
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		final Object item = parent.getItemAtPosition(position);
//		if(item != null && item instanceof NameObject){
//			final NameObject no = (NameObject)item;
//			if(mAdapter.isChoosed(no)){
//				mAdapter.removeChoose(no);
//				view.findViewById(R.id.ivChoose).setVisibility(View.GONE);
//			}else{
//				if(mMaxChooseCount == -1 || mAdapter.getChooseCount() < mMaxChooseCount){
//					mAdapter.addChoose(no);
//					view.findViewById(R.id.ivChoose).setVisibility(View.VISIBLE);
//				}else{
//					CustomToast.showWorningToast(this, String.format("最多选取%1$d张图片", mMaxChooseCount));
//				}
//			}
//			showRightSave(mAdapter.getChooseCount() > 0);
//		}
//	}
//
//	protected static class ChoosePictureAdapter extends BaseAdapter {
//
//		protected List<NameObject> mListObject;
//
//		public ChoosePictureAdapter(){
//			mListObject = new ArrayList<NameObject>();
//		}
//
//		@Override
//		public int getCount() {
//			return mListObject.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			if(position < mListObject.size())
//				return mListObject.get(position);
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return 0;
//		}
//
//		@Override
//		public int getViewTypeCount() {
//			return 1;
//		}
//
//		public void replaceAll(Collection<NameObject> collection){
//			mListObject.clear();
//			if(collection != null){
//				mListObject.addAll(collection);
//			}
//			notifyDataSetChanged();
//		}
//
//		public void addAll(Collection<NameObject> collection){
//			if(collection != null){
//				mListObject.addAll(collection);
//			}
//			notifyDataSetChanged();
//		}
//
//		public void addItem(NameObject e){
//			mListObject.add(e);
//			notifyDataSetChanged();
//		}
//
//		public void addAllItem(List<NameObject> list){
//			mListObject.addAll(list);
//			notifyDataSetChanged();
//		}
//
//		public void removeItem(NameObject e){
//			mListObject.remove(e);
//			notifyDataSetChanged();
//		}
//
//		public void removeItemById(String id){
//			if(mListObject.size() > 0){
//				Object item = getItem(0);
//				if(item instanceof NameObject){
//					int index = 0;
//					for(Object o : mListObject){
//						NameObject ido = (NameObject)o;
//						if(ido.getId().equals(id)){
//							mListObject.remove(index);
//							notifyDataSetChanged();
//							break;
//						}
//						++index;
//					}
//				}
//			}
//		}
//
//		public Object getItemById(String id){
//			if(mListObject.size() > 0){
//				Object item = getItem(0);
//				if(item instanceof NameObject){
//					for(Object o : mListObject){
//						NameObject ido = (NameObject)o;
//						if(ido.getId().equals(id)){
//							return o;
//						}
//					}
//				}
//			}
//			return null;
//		}
//
//		public void removeAllItem(List<NameObject> list){
//			mListObject.removeAll(list);
//			notifyDataSetChanged();
//		}
//
//		public List<NameObject>	getAllItem(){
//			return mListObject;
//		}
//
//		public void clear(){
//			mListObject.clear();
//			notifyDataSetChanged();
//		}
//
//		public HashMap<NameObject, NameObject> mMapChoosePic = new LinkedHashMap<NameObject, NameObject>();
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder;
//			if(convertView == null){
//				convertView = LayoutInflater.from(parent.getContext()).inflate(
//						R.layout.adapter_choosepicture, null);
//				holder = new ViewHolder(convertView);
//				convertView.setTag(R.id.tag_id, holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag(R.id.tag_id);
//			}
//
//			final NameObject no = (NameObject)getItem(position);
//			final String uri = no.getId();
////			XApplication.setBitmapEx(ivPic, uri,width,width, 0);
//			HImageLoader.displayImage("file://"+uri, holder.iv, HImageLoader.createOptions(0, 0), 90, 90);
//			if(mMapChoosePic.containsKey(no)){
//				holder.vChoose.setVisibility(View.VISIBLE);
//			}else{
//				holder.vChoose.setVisibility(View.GONE);
//			}
//
//			return convertView;
//		}
//
//		private class ViewHolder {
//			ImageView iv;
//			View vChoose;
//			public ViewHolder(View v) {
//				iv = (ImageView) v.findViewById(R.id.ivPic);
//				vChoose = v.findViewById(R.id.ivChoose);
//			}
//		}
//
//		public int getChooseCount(){
//			return mMapChoosePic.size();
//		}
//
//		public boolean isChoosed(NameObject no){
//			return mMapChoosePic.containsKey(no);
//		}
//
//		public Collection<NameObject> getChoosePictures(){
//			return mMapChoosePic.keySet();
//		}
//
//		public void addChoose(NameObject no){
//			mMapChoosePic.put(no,no);
////			notifyDataSetChanged();
//		}
//
//		public void removeChoose(NameObject no){
//			mMapChoosePic.remove(no);
////			notifyDataSetChanged();
//		}
//	}
//}
