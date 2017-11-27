package com.chantyou.janemarried.CheckVersion;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.CheckVersionRunner;
import com.chantyou.janemarried.model.CheckVersionBean;
import com.chantyou.janemarried.utils.MyDialog;
import com.chantyou.janemarried.utils.SysCommon;
import com.google.gson.Gson;
import com.lib.mark.core.AndroidEventManager;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.mhh.lib.ToastManager;

import java.io.File;
import java.sql.Timestamp;


//检查版本并更新
public class CheckVersion {
    private Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
    final String filePath = "jianhun";
    final String fileName = "jianhun";
    final String fileNameTime = "(" + now + ")";
    final String fileType = ".apk";
    final String appName = "简婚";


    protected Context context;
//    protected MygetWebInfo mygetWebInfo;

    //    private MyProgressDialog myProgressDialog;
    //版本更新
    private long downloadId = 0;
    private CompleteReceiver completeReceiver;

    private DownloadManager downloadManager;
    private DownloadManagerPro downloadManagerPro;


    private boolean isShowAlter = true;//是否提示用户
    private boolean isShowWaiting = true;//是否显示进度条

    private CallBackInterface callBackInterface;
    private String versionname;

    public CheckVersion(Context context) {
        this.context = context;
        this.versionname = PackageUtils.getAppVersionName(context);
    }

    public CheckVersion(Context context, boolean isShowAlter) {
        this.context = context;
        this.isShowAlter = isShowAlter;
        this.versionname = PackageUtils.getAppVersionName(context);
    }

    public CheckVersion(Context context, boolean isShowAlter, boolean isShowWaiting) {
        this.context = context;
        this.isShowAlter = isShowAlter;
        this.isShowWaiting = isShowWaiting;

        this.versionname = PackageUtils.getAppVersionName(context);
    }

    public void getNewVersion(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
        handler.postDelayed(runnable, 0);
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            getNewVerionAPP();//获取最新版本
        }
    };

    //获取最新版本
    private void getNewVerionAPP() {
        completeReceiver = new CompleteReceiver();
        context.registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        AndroidEventManager.getInstance().pushEventEx(new CheckVersionRunner(this.versionname), new EventManager.OnEventListener() {
            @Override
            public void onEventRunEnd(Event event) {
                switch (event.getEventCode()) {
                    case XEventCode.HTTP_CHECK_VERSION:
                        if (event.isSuccess()) {
                            Gson gson = new Gson();
                            CheckVersionBean checkVersionBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), CheckVersionBean.class);
                            boolean isUpdate = false;
                            if (1 == checkVersionBean.getIsUpdate()) {
                                isUpdate = true;
                            }
                            doBack(isUpdate, checkVersionBean.getUpdateUrl());

                        } else {
                            callBackInterface.onFail();
                        }
                        break;
                }
            }
        });
    }


    //处理返回
    private void doBack(boolean isUpdate, final String url) {

        if (isUpdate) {
            if (null == url || "".equalsIgnoreCase(url)) {
                callBackInterface.onSuccess();
                return;
            }

            downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
            downloadManagerPro = new DownloadManagerPro(downloadManager);

            final String downFileName = fileName + fileNameTime + fileType;


            File folder = Environment.getExternalStoragePublicDirectory(filePath);
            if (!folder.exists() || !folder.isDirectory()) {
                folder.mkdirs();
            }

            if (isShowAlter) {
                String versionCode = "";
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("系统提示")//设置对话框标题
//                        .setMessage("系统检测到最新版本" + versionCode + "，建议更新至最新版本")//设置显示的内容
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//                                callBackInterface.onSuccess();
//
//                                try {
//                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                                    request.setDestinationInExternalPublicDir(filePath, downFileName);
//                                    request.setTitle(appName + "下载更新");
//                                    request.setDescription("新版本新气象");
//                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                                    request.setVisibleInDownloadsUi(false);
//                                    downloadId = downloadManager.enqueue(request);
//                                    ToastManager.getInstance(context).show("正在更新，请稍后...");
//                                } catch (Exception e) {
//                                    ToastManager.getInstance(context).show("更新失败,请致电管理员");
//                                }
//                                //finish();
//                                //StartActivity.this.finish();
//
//
//                            }
//
//                        }).setCancelable(false)//不可取消
//                        .show();//在按键响应事件中显示此对话框

                MyDialog popWindow = new MyDialog(context);

                if (SysCommon.isNotWifi(context)) {
                    popWindow.show("版本升级", "发现最新版本，但您未打开无线连接，是否现在更新？", false, true, new VersionCallBackInterface() {
                        @Override
                        public void doSome() {

                        }

                        @Override
                        public void doSure() {
                            callBackInterface.onSuccess();
                            try {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                request.setDestinationInExternalPublicDir(filePath, downFileName);
                                request.setTitle(appName + "下载更新");
                                request.setDescription("新版本新气象");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setVisibleInDownloadsUi(false);
                                downloadId = downloadManager.enqueue(request);
                                ToastManager.getInstance(context).show("正在更新，请稍后...");
                            } catch (Exception e) {
                                ToastManager.getInstance(context).show("更新失败,请联系客服");
                            }
                        }

                        @Override
                        public void doCancel() {

                        }


                    });


                } else {
                    popWindow.show("版本升级", "发现最新版本，是否现在更新？", false, true, new VersionCallBackInterface() {
                        @Override
                        public void doSome() {

                        }

                        @Override
                        public void doSure() {
                            callBackInterface.onSuccess();

                            try {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                request.setDestinationInExternalPublicDir(filePath, downFileName);
                                request.setTitle(appName + "下载更新");
                                request.setDescription("新版本新气象");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setVisibleInDownloadsUi(false);
                                downloadId = downloadManager.enqueue(request);
                                ToastManager.getInstance(context).show("正在更新，请稍后...");
                            } catch (Exception e) {
                                ToastManager.getInstance(context).show("更新失败,请联系客服");
                            }
                        }

                        @Override
                        public void doCancel() {

                        }


                    });
                }


            } else {
                try {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.setDestinationInExternalPublicDir(filePath, downFileName);
                    request.setTitle(appName + "下载更新");
                    request.setDescription("新版本新气象");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setVisibleInDownloadsUi(false);
                    downloadId = downloadManager.enqueue(request);
                    ToastManager.getInstance(context).show("正在更新，请稍后...");
                } catch (Exception e) {
                    ToastManager.getInstance(context).show("更新失败,请联系客服");
                }
                callBackInterface.onSuccess();
            }

        } else {//没有新版本进入系统
            System.out.println("no 新");
            context.unregisterReceiver(completeReceiver);
            callBackInterface.onSuccess();
        }

    }

    class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * get the id of download which have download success, if the id is
             * my id and it's status is successful, then install it
             **/
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (completeDownloadId == downloadId) {
                // if download successful, install apk

                final String downFileName = fileName + fileNameTime + fileType;
                if (downloadManagerPro.getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
                    String apkFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                            .append(File.separator).append(filePath)
                            .append(File.separator).append(downFileName).toString();
                    install(context, apkFilePath);

                    context.unregisterReceiver(completeReceiver);
                }

            }


        }

    }


    /**
     * 安装
     *
     * @param context
     * @param filePath
     * @return whether apk exist
     */

    private static boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }


}




