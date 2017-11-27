package com.mhh.lib.ui.helper;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;

import com.mhh.lib.R;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.FileHelper;
import com.mhh.lib.utils.FilePaths;
import com.mhh.lib.utils.SystemUtils;
import com.mhh.lib.widget.ChoosePhotoPopup;
import com.mhh.lib.widget.XPopupWindow;

import java.io.File;

/**
 * Created by mhh on 2016/5/19.
 * Email:766082577@qq.com
 */
public abstract class AbsChoosePhotoHelper2 implements IChoosePhotoHelper {

    private XPopupWindow mPopupWindow;
    private boolean mIsChoosePhotoCrop;
    private int outputX = 800, outputY = 800;
    private int aspectX, aspectY;

    private int mIndex;

    private Context cxt;

    protected AbsChoosePhotoHelper2(Context cxt) {
        this.cxt = cxt;
    }

    private void dissmissPopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    @Override
    public void launchChoosePhoto(View view) {
        mIsChoosePhotoCrop = false;
        showChoose(view);
    }

    @Override
    public void launchChoosePhotoWithCrop(View view, int aspectX, int aspectY, int outX, int outY) {
        mIsChoosePhotoCrop = true;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        this.outputX = outX;
        this.outputY = outY;

        showChoose(view);
    }

    private void showChoose(View view) {
        dissmissPopup();
        mPopupWindow = new ChoosePhotoPopup(view.getContext());

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.tvCamera) {
                    launchCamera(/* , recId */);
                    dissmissPopup();
                } else if (id == R.id.tvAlbumChoose) {
                    launchPictureChoose();
                    dissmissPopup();
                } else if (id == R.id.tvCancel) {
                    dissmissPopup();
                } else if (id == R.id.popup_topview) {
                    dissmissPopup();
                }
            }
        };
        ((ChoosePhotoPopup) mPopupWindow).setOnItemClickListener(listener);
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
    }

    protected void launchCamera(/* , int recId */) {

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        FileHelper.checkOrCreateDirectory(getCameraSaveFilePath());

        final File file = new File(getCameraSaveFilePath());
        final Uri outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        try {
            launchActivityForResult(intent, RequestCode_LaunchCamera);

        } catch (final ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void launchPictureChoose() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            intent.setAction("android.intent.action.OPEN_DOCUMENT");
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        launchActivityForResult(Intent.createChooser(intent, "选择图片"), RequestCode_LaunchChoosePicture);
    }

    @Override
    public String getCameraSaveFilePath() {
        return FilePaths.getCameraSaveFilePath(cxt);
    }

    private String getPictureCropFilePath() {
        return FilePaths.getPictureCropPath(cxt);
    }

    /**
     * 多图图片保存路径
     * @param cxt
     * @return
     */
	protected String getPictureSaveFilePath2(Context cxt) {
		return SystemUtils.getExternalCachePath(cxt) +
                File.separator + "cameras" + File.separator +
                "temp" + String.valueOf(mIndex++) + ".jpg";
	}

    @Override
    public void onPictureChoosed(String filePath, String displayName) {

    }

    private void onChooseFinished(String filePath) {
        if(filePath != null && new File(filePath).exists()
                && mIsChoosePhotoCrop && outputX > 0 && outputY > 0) {
            SystemUtils.compressBitmapFile(filePath, filePath, outputX, outputY, false);
        }
        // 从相册选择图片时copy一个副本，防止删除系统图片
        if(filePath != null && !filePath.equalsIgnoreCase(getCameraSaveFilePath())
                && !filePath.equalsIgnoreCase(getPictureCropFilePath())) {
            FileHelper.copyFile(getCameraSaveFilePath(), filePath);
            filePath = getCameraSaveFilePath();
        }
        if(filePath != null && new File(filePath).exists()) {
            onPictureChoosed(filePath, "");
        } else {
            CustomToast.showErrorToast(cxt, "图片获取失败");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, int resulltOk) {
        if (requestCode == RequestCode_LaunchCamera) {
            String path = CameraUtil.getResultPhotoPath(cxt, data, getCameraSaveFilePath());
            if(mIsChoosePhotoCrop && path != null) {
                cropImageUri(Uri.fromFile(new File(path)));
            } else {
                onChooseFinished(path);
            }
        } else if (requestCode == RequestCode_LaunchCrop) {
//            Bitmap bitmap = null;
            try {
//                bitmap = BitmapFactory.decodeStream(cxt.getContentResolver().openInputStream(Uri.fromFile(new File(getPictureCropFilePath()))));
                onChooseFinished(getPictureCropFilePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == RequestCode_LaunchChoosePicture) {
            if (resultCode == resulltOk) {
                if (data != null && data.getData() != null) {
                    // 根据返回的URI获取对应的SQLite信息
                    Uri uri = data.getData();
                    String path = CameraUtil.getPath(cxt, uri);
                    if (mIsChoosePhotoCrop && path != null) {
                        cropImageUri(Uri.fromFile(new File(path)));
                    } else {
                        onChooseFinished(path);
                    }

//                    cropImageUri(Uri.parse("file://"+path), 500, 600, 1002);
                }
            }
        }
    }

    private void cropImageUri(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        if(aspectX >= 1 && aspectY >= 1) {
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
        }

        if(mIsChoosePhotoCrop && outputX > 0 && outputY > 0) {
            intent.putExtra("outputX", outputX);
            intent.putExtra("outputY", outputY);
        }

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(getPictureCropFilePath())));

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection

        launchActivityForResult(intent, RequestCode_LaunchCrop);
    }
}
