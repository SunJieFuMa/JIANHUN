//package com.mhh.lib.ui.helper;
//
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//
//import com.mhh.lib.R;
//import com.mhh.lib.utils.FileHelper;
//import com.mhh.lib.utils.FilePaths;
//import com.mhh.lib.utils.SystemUtils;
//import com.mhh.lib.widget.ChoosePhotoPopup;
//import com.mhh.lib.widget.XPopupWindow;
//
//import java.io.File;
//
///**
// * Created by j_turn on 2016/2/5 20:45
// * Email：766082577@qq.com
// */
//public abstract class AbsChoosePhotoHelper implements IChoosePhotoHelper {
//
//	private XPopupWindow mPopupWindow;
//	private boolean mIsChoosePhotoCrop;
//	protected boolean mIsChoosePhotoCompression = true;
//	protected int mChoosePhotoReqWidth = 800;
//	protected int mChoosePhotoReqHeight = 800;
//
//	private String mCameraPath;
//	private int					mIndex;
//
//	private Context cxt;
//
//	protected AbsChoosePhotoHelper(Context cxt) {
//		this.cxt = cxt;
//	}
//
//	private void dissmissPopup() {
//		if (mPopupWindow != null && mPopupWindow.isShowing())
//			mPopupWindow.dismiss();
//	}
//
//	public void launchChoosePhoto(View view, final boolean bCrop/* , final int recId */) {
//		if (mPopupWindow == null || !(mPopupWindow instanceof ChoosePhotoPopup))
//			mPopupWindow = new ChoosePhotoPopup(view.getContext());
//
//		OnClickListener listener = new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				int id = v.getId();
//				if(id == R.id.tvCamera) {
//					launchCamera(false, bCrop/* , recId */);
//					dissmissPopup();
//				} else if(id == R.id.tvAlbumChoose) {
//					launchPictureChoose(bCrop);
//					dissmissPopup();
//				} else if(id ==  R.id.tvCancel) {
//					dissmissPopup();
//				} else if(id == R.id.popup_topview) {
//					dissmissPopup();
//				}
//			}
//		};
//		((ChoosePhotoPopup) mPopupWindow).setOnItemClickListener(listener);
//		if (!mPopupWindow.isShowing())
//			mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
//
//	}
//
//	@Override
//	public void launchChoosePhotoWithCrop(View view, int aspectX, int aspectY, int outX, int outY) {
//
//	}
//
//	@Override
//	public void launchChoosePhoto(View view) {
//
//	}
//
//	protected void launchCamera(boolean bVideo, boolean bCrop/* , int recId */) {
//		mIsChoosePhotoCrop = bCrop;
//		try {
////			FileHelper.checkOrCreateDirectory(getCameraSaveFilePath());
////			Intent intent = new Intent(this, CameraTakeActivity.class);
////			intent.putExtra(CameraTakeActivity.EXTRA_OUTPUT,
////					getCameraSaveFilePath());
////			intent.putExtra("card_id", recId);
////			startActivityForResult(intent, RequestCode_LaunchCamera);
//			 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//			 FileHelper.checkOrCreateDirectory(getCameraSaveFilePath());
//			 intent.putExtra(MediaStore.EXTRA_OUTPUT,
//			 Uri.fromFile(new File(getCameraSaveFilePath())));
//			 launchActivityForResult(intent, RequestCode_LaunchCamera);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	protected void launchPictureChoose(boolean bCrop) {
//		mIsChoosePhotoCrop = bCrop;
//		try {
//			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//
//			if (android.os.Build.VERSION.SDK_INT >= 19) {
//				intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//			} else {
//				intent.setAction(Intent.ACTION_GET_CONTENT);
//			}
//			intent.addCategory(Intent.CATEGORY_OPENABLE);
//			intent.setType("image/*");
//			Intent intent1 = Intent.createChooser(intent, "本地图片");
////			activity.startActivityForResult(
////					Intent.createChooser(intent, "本地图片"), 0);
//
////			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////			intent.setType("image/*");
//			FileHelper.checkOrCreateDirectory(FilePaths.getPictureChooseFilePath(cxt));
//			FileHelper.deleteFile(FilePaths.getPictureChooseFilePath(cxt));
//			if (bCrop) {
//				onSetCropExtra(intent);
//			}
//			// 部分手机不兼容 裁剪无效
//			mIsChoosePhotoCrop = false;
//			launchActivityForResult(intent1, RequestCode_LaunchChoosePicture);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public String getCameraSaveFilePath() {
//		return FilePaths.getCameraSaveFilePath(cxt);
//	}
//
//	protected String getCameraSaveFilePath2(Context cxt) {
//		if(mCameraPath == null) {
//			mCameraPath = SystemUtils.getExternalCachePath(cxt) +
//					File.separator + "cameras" + File.separator +
//					"temp" + String.valueOf(mIndex++) + ".jpg";
////			HImageLoader.init().removeItemFileCache(mCameraPath);
//		}
//		return mCameraPath;
//	}
//
//	public String compressBitmapFile(String srcPath,int reqWidth,int reqHeight, boolean deletesrc){
//		String path = getCameraSaveFilePath2(cxt);
//		SystemUtils.compressBitmapFile(path, srcPath, reqWidth, reqHeight, deletesrc);
//		return path;
//	}
//
//	@Override
//	public void onPictureChoosed(String filePath, String displayName) {
//		mCameraPath = null;
//	}
//
//	protected void onCameraResult(Intent data) {
//		if (mIsChoosePhotoCrop) {
//			try {
//				Intent intent = new Intent("com.android.camera.action.CROP");
//				intent.setDataAndType(
//						Uri.fromFile(new File(getCameraSaveFilePath())),
//						"image/*");
//				FileHelper.deleteFile(FilePaths.getPictureChooseFilePath(cxt));
//				onSetCropExtra(intent);
//				launchActivityForResult(intent, RequestCode_LaunchChoosePicture);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			final String path = getCameraSaveFilePath();
//			if (mIsChoosePhotoCompression) {
//				SystemUtils.compressBitmapFile(path, path,
//						mChoosePhotoReqWidth, mChoosePhotoReqHeight, true);
//			}
//			onPictureChoosed(path, null);
//		}
//	}
//
//	protected void onSetCropExtra(Intent intent) {
//		intent.putExtra("crop", "true");
//		intent.putExtra("return-data", false);
//		intent.putExtra("noFaceDetection", true);
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT,
//				Uri.fromFile(new File(FilePaths.getPictureChooseFilePath(cxt))));
//		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//	}
//
//	protected void onPictureChooseResult(Intent data) {
//		if (data != null) {
//			if (mIsChoosePhotoCrop) {
//				File file = new File(FilePaths.getPictureChooseFilePath(cxt));
//				if (file.exists()) {
//					onPictureChoosed(FilePaths.getPictureChooseFilePath(cxt), null);
//				} else {
//					Object obj = data.getParcelableExtra("data");
//					if (obj != null && obj instanceof Bitmap) {
//						final Bitmap bmp = (Bitmap) obj;
//						FileHelper.saveBitmapToFile(
//								FilePaths.getPictureChooseFilePath(cxt), bmp);
//						onPictureChoosed(FilePaths.getPictureChooseFilePath(cxt),
//								null);
//					}
//				}
//			} else {
//				Uri uri = data.getData();
//				if (uri != null) {
//					Cursor cursor = cxt.getContentResolver()
//							.query(uri,
//									new String[] {
//											MediaStore.Images.ImageColumns.DISPLAY_NAME,
//											MediaStore.Images.ImageColumns.DATA,
//											MediaStore.Images.Media.DATA },
//									null, null, null);
//					if (cursor != null && cursor.moveToFirst()) {
//						String displayName = cursor
//								.getString(cursor
//										.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
//						String picPath = cursor
//								.getString(cursor
//										.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
//						if (picPath == null)
//							picPath = cursor
//									.getString(cursor
//											.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//						if (picPath == null) {
//							try {
//								picPath = FilePaths.getPictureChooseFilePath(cxt);
//								Bitmap bitmap = MediaStore.Images.Media
//										.getBitmap(cxt.getContentResolver(), uri);
//								FileHelper
//										.saveBitmapToFile(picPath, bitmap, 90);
//								SystemUtils.compressBitmapFile(picPath,
//										picPath, mChoosePhotoReqWidth,
//										mChoosePhotoReqHeight, false);
//								onPictureChoosed(picPath, displayName);
//							} catch (Exception e) {
//								e.printStackTrace();
//								throw new NullPointerException("从相册获取图片失败");
//							}
//						} else if (mIsChoosePhotoCompression) {
//							SystemUtils.compressBitmapFile(
//									FilePaths.getPictureChooseFilePath(cxt),
//									picPath, mChoosePhotoReqWidth,
//									mChoosePhotoReqHeight, false);
//							onPictureChoosed(
//									FilePaths.getPictureChooseFilePath(cxt),
//									displayName);
//						} else {
//							onPictureChoosed(picPath, displayName);
//						}
//						return;
//					}
//				}
//					File file = new File(FilePaths.getPictureChooseFilePath(cxt));
//					if (file.exists()) {
//						if (mIsChoosePhotoCompression) {
//							SystemUtils.compressBitmapFile(
//									FilePaths.getPictureChooseFilePath(cxt),
//									FilePaths.getPictureChooseFilePath(cxt),
//									mChoosePhotoReqWidth,
//									mChoosePhotoReqHeight, true);
//						}
//						onPictureChoosed(FilePaths.getPictureChooseFilePath(cxt),
//								null);
//					} else {
//						Object obj = data.getParcelableExtra("data");
//						if (obj != null && obj instanceof Bitmap) {
//							final Bitmap bmp = (Bitmap) obj;
//							FileHelper.saveBitmapToFile(
//									FilePaths.getPictureChooseFilePath(cxt), bmp);
//							onPictureChoosed(
//									FilePaths.getPictureChooseFilePath(cxt), null);
//						}
//					}
//			}
//		}
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data, int resulltOk) {
//		if (resultCode == resulltOk) {
//			if (requestCode == RequestCode_LaunchCamera) {
//				onCameraResult(data);
//			} else if (requestCode == RequestCode_LaunchChoosePicture) {
//				onPictureChooseResult(data);
//			}
//		}
//	}
//
//}
