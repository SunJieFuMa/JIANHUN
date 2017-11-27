package com.mhh.lib.ui.helper;

import android.content.Intent;
import android.view.View;

/**
 * Created by j_turn on 2016/2/5 23:28
 * Email：766082577@qq.com
 */
public interface IChoosePhotoHelper {

    int RequestCode_LaunchCamera = 15000;
    int RequestCode_LaunchChoosePicture = 15001;
    int RequestCode_LaunchCrop = 15002;

    /**
     * 启动图片选择页面
     *
     * @param view
     */
    void launchChoosePhoto(View view);

    /**
     * 启动图片选择页面
     *
     * @param view
     * @param aspectX X比例
     * @param aspectY Y比例
     * @param outX      宽
     * @param outY      高
     */
    void launchChoosePhotoWithCrop(View view, int aspectX, int aspectY, int outX, int outY);

    /**
     * 图片文件保存位置
     *
     * @return
     */
    String getCameraSaveFilePath();

    /**
     * Activity/Fragment
     *
     * @param intent
     * @param requestCode
     */
    void launchActivityForResult(Intent intent, int requestCode);

    /**
     * Activity/Fragment 实现回掉
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @param resulltOk
     */
    void onActivityResult(int requestCode, int resultCode, Intent data, int resulltOk);

    /**
     * 图片结果回掉
     *
     * @param filePath
     * @param displayName
     */
    void onPictureChoosed(String filePath, String displayName);
}
