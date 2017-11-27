package com.chantyou.janemarried.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.FileHelper;
import com.mhh.lib.utils.FilePaths;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * Created by j_turn on 2016/4/20.
 * Email 766082577@qq.com
 */
public class ThreadShareHelper implements View.OnClickListener, IWeiboHandler.Response {

    public static final int SIZE = (int) (32 * 1024 * 1.5 * 1.5);

    private static final int THUMB_SIZE = 150;

    //    private IWXAPI api;
    private Tencent mTencent;

    /**
     * 微博微博分享接口实例
     */
    private IWeiboShareAPI mWeiboShareAPI = null;

    protected AlertDialog dialog;

    private String SHARE_TITLE = "简婚-结婚助手";
    private String SHARE_SUMMARY = "";
    private String SHARE_TARGET_URL = "http://connect.qq.com/";
    private String imageUrl;

    Activity cxt;

    public ThreadShareHelper(Activity activity) {
        this.cxt = activity;
        init();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void init() {
//        api = WXAPIFactory.createWXAPI(cxt.getApplicationContext(), Constants.WX_APP_ID);
//        api.registerApp(Constants.WX_APP_ID);
        mTencent = Tencent.createInstance(Constants.QQ_APPID, cxt);

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(cxt, cxt.getString(R.string.sina_key));

        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void showShareDialog(String url, String title, String content) {
        this.SHARE_TARGET_URL = url;
        if (!TextUtils.isEmpty(title)) {
            SHARE_TITLE = title;
        }
        SHARE_SUMMARY = content;
        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(cxt)
                .setNegativeButton("取消", null);
        builder.setTitle("分享到");
        builder.setView(R.layout.view_share);
        dialog = builder.create();
        dialog.show();
        dialog.findViewById(R.id.shareWechat).setOnClickListener(this);
        dialog.findViewById(R.id.shareWechatmoments).setOnClickListener(this);
        dialog.findViewById(R.id.shareSina).setOnClickListener(this);
        dialog.findViewById(R.id.shareQQ).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismissDialog();
        switch (v.getId()) {
            case R.id.shareWechat:
                doWechatShare(false);
                break;
            case R.id.shareWechatmoments:
                doWechatShare(true);
                break;
            case R.id.shareSina:
                doSinaShare();
                break;
            case R.id.shareQQ:
                doQQShare(v);
                break;
        }
    }

    boolean checkIfWXExists(Context c) {
        String packageName = "com.tencent.mm";
        try {
            c.getPackageManager().getApplicationInfo(packageName,
                    android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 微信分享
     *
     * @param isTimeline 是否是朋友圈
     */
    protected void doWechatShare(boolean isTimeline) {
        if (!checkIfWXExists(cxt)) {
            CustomToast.showErrorToast(cxt, "微信尚未安装");
            return;
        }
        final WXMediaMessage msg = new WXMediaMessage();
        msg.title = SHARE_TITLE;
        msg.description = SHARE_SUMMARY;

        final SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
//        发送到回话或发到朋友圈 SendMessageToWX.Req.WXSceneTimeline
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        final IWXAPI api = WXAPIFactory.createWXAPI(cxt.getApplicationContext(), Constants.WX_APP_ID);

        //是否是网页分享
        if (TextUtils.isEmpty(SHARE_TARGET_URL)) {
            if(TextUtils.isEmpty(SHARE_SUMMARY)) {
                msg.description = SHARE_TITLE;
            }
            // 没有图片分享文字
            if (TextUtils.isEmpty(imageUrl)) {
                // 初始化一个WXTextObject对象
                WXTextObject textObj = new WXTextObject();
                textObj.text = SHARE_TITLE+"\n"+SHARE_SUMMARY;
                msg.mediaObject = textObj;
                // 发送文本类型的消息时，title字段不起作用
                // msg.title = "Will be ignored";
//            msg.description = SHARE_SUMMARY;

                req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求

//                Bitmap bmp = BitmapFactory.decodeResource(cxt.getResources(), R.mipmap.push);
//                Bitmap thumb = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
//                bmp.recycle();
//                msg.thumbData = Util.bmpToByteArray(thumb, true);

                api.sendReq(req);
            } else {
                //分享图片
                final WXImageObject imgObj = new WXImageObject();
                imgObj.imageUrl = imageUrl;
                msg.mediaObject = imgObj;

                try {
                    req.transaction = buildTransaction("img");

//                    Bitmap bmp = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
//                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
//                    bmp.recycle();
//                    msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

//                    api.sendReq(req);

                    HImageLoader.loadImge(imageUrl, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            Bitmap bmp = BitmapFactory.decodeResource(cxt.getResources(), R.mipmap.push);
                            Bitmap thumb = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                            bmp.recycle();
                            msg.thumbData = Util.bmpToByteArray(thumb, true);

                            api.sendReq(req);
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            try {
                                if (bitmap != null) {
                                    String path = FilePaths.getShareFilePath(cxt);
                                    FileHelper.saveBitmapToFile(path, bitmap, 100);
                                    imgObj.setImagePath(path);

//                                    Bitmap bmp = BitmapFactory.decodeFile(path);
//                                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
//                                    bmp.recycle();
//                                    msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

//                                    Bitmap thumb = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
//                                    bitmap.recycle();
//                                    imgObj.imageData = Util.bmpToByteArray(thumb, true);
//                                    msg.thumbData = Util.bmpToByteArray(thumb, true);

                                    api.sendReq(req);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                            Bitmap bmp = BitmapFactory.decodeResource(cxt.getResources(), R.mipmap.push);
                            Bitmap thumb = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                            bmp.recycle();
                            msg.thumbData = Util.bmpToByteArray(thumb, true);

                            api.sendReq(req);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //分享网页
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = SHARE_TARGET_URL;
            msg.mediaObject = webpage;

            req.transaction = buildTransaction("webpage");

            if (TextUtils.isEmpty(imageUrl)) {//网页没有图片
//                Bitmap bmp = BitmapFactory.decodeResource(cxt.getResources(), R.mipmap.push);
//                Bitmap thumb = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
//                bmp.recycle();
//                msg.thumbData = Util.bmpToByteArray(thumb, true);

                api.sendReq(req);
            } else {//网页有图片


                HImageLoader.loadImge(imageUrl, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        Bitmap bmp = BitmapFactory.decodeResource(cxt.getResources(), R.mipmap.push);
                        Bitmap thumb = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                        bmp.recycle();
                        msg.thumbData = Util.bmpToByteArray(thumb, true);

                        api.sendReq(req);
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        if (bitmap != null) {
                            try{
                                bitmap=  comp(bitmap);
                            }catch (Exception e){

                            }

                            Bitmap thumb = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
                            bitmap.recycle();
                            msg.thumbData = Util.bmpToByteArray(thumb, true);

                            api.sendReq(req);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        Bitmap bmp = BitmapFactory.decodeResource(cxt.getResources(), R.mipmap.push);
                        Bitmap thumb = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                        bmp.recycle();
                        msg.thumbData = Util.bmpToByteArray(thumb, true);

                        api.sendReq(req);
                    }
                });
            }
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void doQQShare(View view) {
        Bundle bundle = new Bundle();
//这条分享消息被好友点击后的跳转URL。
        if (!TextUtils.isEmpty(SHARE_TARGET_URL)) {
            bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, SHARE_TARGET_URL);
        } else {

            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        }
//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, SHARE_TITLE);
//分享的图片URL
//        bundle.putString(QQShare.PARAM_IMAGE_URL,
//                "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
//分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, SHARE_SUMMARY);
//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, cxt.getString(R.string.app_name));
//标识该消息的来源应用，值为应用名称+AppId。
//        bundle.putString(QQShare.PARAM_APP_SOURCE, "星期几" + AppId);

        mTencent.shareToQQ(cxt, bundle, qqShareListener);
    }


    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
//            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
//                Util.toastMessage(ThreadShareActivity.this, "onCancel: ");
//            }
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Util.toastMessage(cxt, "onComplete: " + response.toString());
        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(cxt, "onError: " + e.errorMessage, "e");
        }
    };


    public void handleWeiboResponse(Intent intent) {

        if (mWeiboShareAPI != null) {
            // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
            // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
            // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
            mWeiboShareAPI.handleWeiboResponse(intent, this);
        }
    }


    private void doSinaShare() {
        if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
            int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
            if (supportApi >= 10351) {
                sendMultiMessage();
            } else {
                sendSingleMessage();
            }

        } else {
            Toast.makeText(cxt, "新浪不支持该分享！", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。 注意：当
     * {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     */
    private void sendMultiMessage() {
        // 1. 初始化微博的分享消息
        final WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

        final SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        weiboMessage.textObject = getTextObj();// 文本
        if (!TextUtils.isEmpty(SHARE_TARGET_URL)) {
            weiboMessage.mediaObject = getWebpageObj();
        }
        if (TextUtils.isEmpty(imageUrl)) {

            weiboMessage.imageObject = getImageObj(null);
            // 3. 发送请求消息到微博，唤起微博分享界面
            mWeiboShareAPI.sendRequest(cxt, request);
        } else {

            HImageLoader.loadImge(imageUrl, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    weiboMessage.imageObject = getImageObj(null);

                    // 3. 发送请求消息到微博，唤起微博分享界面
                    mWeiboShareAPI.sendRequest(cxt, request);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    weiboMessage.imageObject = getImageObj(bitmap);

                    // 3. 发送请求消息到微博，唤起微博分享界面
                    mWeiboShareAPI.sendRequest(cxt, request);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    weiboMessage.imageObject = getImageObj(null);

                    // 3. 发送请求消息到微博，唤起微博分享界面
                    mWeiboShareAPI.sendRequest(cxt, request);
                }
            });
        }
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()}
     * < 10351 时，只支持分享单条消息，即 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     */
    private void sendSingleMessage() {
        WeiboMessage weiboMessage = new WeiboMessage();
        weiboMessage.mediaObject = getTextObj();// 文本
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        mWeiboShareAPI.sendRequest(cxt, request);
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = SHARE_TITLE;
        mediaObject.description = SHARE_SUMMARY;

        Bitmap bitmap = BitmapFactory.decodeResource(cxt.getResources(), R.drawable.ic_logo);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = SHARE_TARGET_URL;
        mediaObject.defaultText = SHARE_TITLE;
        return mediaObject;
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = SHARE_TITLE + "\n" + SHARE_SUMMARY;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(cxt.getResources(), R.mipmap.push);
        }
        if (bitmap != null) {
            bitmap = resizeImage(bitmap);
        }
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     *
     * @param baseResp 微博请求数据对象
     * @see {@link IWeiboShareAPI#handleWeiboRequest}
     */
    @Override
    public void onResponse(BaseResponse baseResp) {
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(cxt, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(cxt, "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(cxt, "分享失败 Error Message: " + baseResp.errMsg,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    // 通过传入位图,新的宽.高比进行位图的缩放操作
    public static Bitmap resizeImage(Bitmap bitmap) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = width;
        int newHeight = height;
        while (newWidth * newHeight > SIZE) {
            newWidth = (int) (newWidth * 0.9);
            newHeight = (int) (newHeight * 0.9);
        }
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
    }




    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 10;
        while (baos.toByteArray().length / 1024 > 60) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }



    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        if( baos.toByteArray().length / 1024 > 60) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
}
