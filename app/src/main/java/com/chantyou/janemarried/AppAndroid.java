package com.chantyou.janemarried;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.SPDBHelper;
import com.mhh.lib.framework.CrashHandler;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.util.ArrayList;
import java.util.List;

public class AppAndroid extends Application {

    private static final String TAG = AppAndroid.class.getSimpleName();

    private static AppAndroid instance;
    private Handler sMainThreadHandler;
    private static int mScreenWidth, mScreenHeight;
    private static int sDensity;

    public SharedPreferences sp;

    private static String accessToken;

    public static AppAndroid getApp() {
        return instance;
    }

    //得到全局变量
    public static Context getContext() {
        return instance.getApplicationContext();
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);
        //        AlibabaSDK.asyncInit(this);

        sMainThreadHandler = new Handler();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;

        sp = getSharedPreferences("sp_readingclub", Context.MODE_PRIVATE);

        //		HImageLoader.init();

        //开启百度统计
        startBaidu();

        //        initWebView();

    }

    private void initWebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Log.e("apptbs", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("apptbs", "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("apptbs", "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("apptbs", "onDownloadProgress:" + i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void startBaidu() {

        //百度统计
        // 测试时，可以使用1秒钟session过期，这样不断的间隔1S启动退出会产生大量日志。
        //        StatService.setSessionTimeOut(1);
        // setOn也可以在AndroidManifest.xml文件中填写，BaiduMobAd_EXCEPTION_LOG，打开崩溃错误收集，默认是关闭的
        StatService.setOn(this, StatService.EXCEPTION_LOG);
         /*
         * 设置启动时日志发送延时的秒数<br/> 单位为秒，大小为0s到30s之间<br/> 注：请在StatService.setSendLogStrategy之前调用，否则设置不起作用
         *
         * 如果设置的是发送策略是启动时发送，那么这个参数就会在发送前检查您设置的这个参数，表示延迟多少S发送。<br/> 这个参数的设置暂时只支持代码加入，
         * 在您的首个启动的Activity中的onCreate函数中使用就可以。<br/>
         */
        StatService.setLogSenderDelayed(0);


           /*
         * 用于设置日志发送策略<br /> 嵌入位置：Activity的onCreate()函数中 <br />
         *
         * 调用方式：StatService.setSendLogStrategy(this,SendStrategyEnum. SET_TIME_INTERVAL, 1, false); 第二个参数可选：
         * SendStrategyEnum.APP_START SendStrategyEnum.ONCE_A_DAY SendStrategyEnum.SET_TIME_INTERVAL 第三个参数：
         * 这个参数在第二个参数选择SendStrategyEnum.SET_TIME_INTERVAL时生效、 取值。为1-24之间的整数,即1<=rtime_interval<=24，以小时为单位 第四个参数：
         * 表示是否仅支持wifi下日志发送，若为true，表示仅在wifi环境下发送日志；若为false，表示可以在任何联网环境下发送日志
         */
        // 如果没有页面和自定义事件统计埋点，此代码一定要设置，否则无法完成统计
        // 设置发送策略，建议使用 APP_START
        // 由于多进程等可能造成Application多次执行，建议此代码不要埋点在Application中，否则可能造成启动次数偏高
        // 建议此代码埋点在统计路径触发的第一个页面中，若可能存在多个则建议都埋点
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        // 调试百度统计SDK的Log开关，可以在Eclipse中看到sdk打印的日志，发布时去除调用，或者设置为false
        StatService.setDebugOn(true);
        String sdkVersion = StatService.getSdkVersion();
        System.out.println("百度统计已启动:" + "sdk version is: " + sdkVersion);

    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    //	public void setAccount(String account, String pwd) {
    //		if(!TextUtils.isEmpty(pwd)) {
    //			sp.edit().putString("account", account).putString("pwd", pwd).commit();
    //		} else {
    //			sp.edit().remove("pwd").remove(DataStruct.USER.USERID).commit();
    //		}
    //	}

    /**
     * 返回主线程Handler实例
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return instance.sMainThreadHandler;
    }

    /**
     * 返回手机屏幕宽
     *
     * @return
     */
    public static int getScreenWidth() {
        return mScreenWidth;
    }

    /**
     * 返回手机屏幕高
     *
     * @return
     */
    public static int getScreenHeight() {
        return mScreenHeight;
    }


    public static int getStatusBarHeight(Activity activity) {
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    public static int dipToPixel(int nDip) {
        if (sDensity == 0) {
            sDensity = instance.getResources().getDimensionPixelSize(R.dimen.per_dp);
        }
        return (int) (sDensity * nDip);
    }

    /**
     * 获取accessToken
     *
     * @return
     */
    public static String getAccessToken() {
        if (TextUtils.isEmpty(accessToken)) {
            accessToken = new SPDBHelper().getString(Constants.ACCESSTOKEN, null);
        }
        return accessToken;
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public static int getUid() {
        return new SPDBHelper().getInteger(Constants.USER_ID, 0);
    }

    /**
     * 设置accessToken 信息
     *
     * @param access_token
     */
    public static void setAccessToken(String access_token, int uid) {
        accessToken = access_token;
        SPDBHelper spdbHelper = new SPDBHelper();
        spdbHelper.putString(Constants.ACCESSTOKEN, access_token);
        spdbHelper.putInteger(Constants.USER_ID, uid);
    }

    public static void setUserInfo(String type, String phone, String pwd) {
        SPDBHelper spdbHelper = new SPDBHelper();
        spdbHelper.putString("user_phone", phone);
        spdbHelper.putString("user_type", type);
        if (Constants.isMobileUser(type)) {
            spdbHelper.putString("user_pwd", pwd);
        }
    }

    public static String[] getUserInfo() {
        String[] user = new String[3];
        SPDBHelper spdbHelper = new SPDBHelper();
        user[0] = spdbHelper.getString("user_type", null);
        user[1] = spdbHelper.getString("user_phone", null);
        user[2] = spdbHelper.getString("user_pwd", null);
        return user;
    }

    //////下面是我自己加的
    private static List<Activity> activityList = new ArrayList<>();

    public static void deleteAllActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        if (activityList.size() == 0) {
            activityList.clear();
            activityList=null;
        }
    }

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }
}
