package com.chantyou.janemarried.ui.guide;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.user.UserLoginRunner;
import com.chantyou.janemarried.service.CoreService;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.ui.main.MainTabActivity;
import com.chantyou.janemarried.utils.APKUtil2;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.chantyou.janemarried.utils.SPDBHelper;
import com.igexin.sdk.PushManager;
import com.lib.mark.core.Event;
import com.mhh.lib.utils.FilePaths;
import com.mhh.lib.utils.SystemUtils;

import java.io.File;

/**
 * Created by j_turn on 2015/12/8 00:31
 * Email：766082577@qq.com
 */
public class LauncherActivity extends XBaseActivity {

    // 欢迎页 显示时间
    private final static long LTIME = 3000;
    private long sTime;
    private Runnable runnable;
    private static final int REQUEST_PERMISSION_LOCATION_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSwipeBackFinish = false;
        super.onCreate(savedInstanceState);
        sTime = System.currentTimeMillis();
        setContentView(R.layout.activity_launcher);
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext());
    }

    @Override
    protected void init() {
        super.init();
        initAct();
        //        initAnim();

    }

    //动态适配权限
    private void requestSomePermissions() {
        //判断当前系统是否高于或等于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                    ) {
                initAnim();
            } else {
                /*
                *
                * 一开始就让用户给予三个权限。定位、读SD卡和读取电话，
                * 出现了一个很奇葩的bug，就是当你删除了APP的数据之后，再登录就登录不上了，获取验证码也不行，微信登录也不行，
                * 如果给了电话权限的话就可以了，为什么需要这个权限我也是一脸懵逼，所以我就强制让用户给予这三个权限，
                * 如果拒绝了其中的权限的话就弹出对话框让用户选择是否跳转到应用的设置界面给予权限，如果用户不同意跳转那就直接关闭应用
                * 如果用户同意跳转并且给予了权限但是给予的权限不全的话，再回到应用就会再次请求还没给到的权限，这样就很好地解决了权限引发的bug
                *
                * */
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE
                        },
                        REQUEST_PERMISSION_LOCATION_CODE);
            }
        } else {
            //当前系统小于6.0
            initAnim();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener,
                                     DialogInterface.OnClickListener onListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", onListener)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //获取位置
        if (requestCode == REQUEST_PERMISSION_LOCATION_CODE) {
            if (grantResults.length >= 1) {
                if (isPermissionGranted(grantResults)) {
                    //具有位置权限
                    initAnim();
                } else {
                    //不具有相关权限，给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限开启
                    //                    tishi("请去系统设置-应用管理里面把读sd卡权限开启");
                    //                    tishi("可以到系统设置-应用管理里打开被禁止的权限");
                    showMessageOKCancel("是否跳转到设置界面给定APP权限？APP无权限将不能启动", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            APKUtil2.startAppSettingsResult(LauncherActivity.this);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            requestSomePermissions();
        }
    }

    private boolean isPermissionGranted(int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                //只要有一个没有被允许就不能进入APP
                return false;
            }
        }
        return true;
    }


    private void initAct() {
        String pic_welcome = new SPDBHelper().getString("pic_welcome", "");
        String path = null;
        if (!TextUtils.isEmpty(pic_welcome)) {
            path = FilePaths.getUrlFileCachePath(this, pic_welcome);
            if (!new File(path).exists()) {
                path = "";
            }
        }
        if (null == path || TextUtils.isEmpty(path)) {
            HImageLoader.setBitmapDrawable(R.drawable.ic_launch, (ImageView) findViewById(R.id.ivBg),
                    R.drawable.ic_launch);
        } else {
            HImageLoader.setBitmapFile(path, (ImageView) findViewById(R.id.ivBg),
                    HImageLoader.createOptions(R.drawable.ic_launch, 0));
        }

        //后台开启一个服务---关于黄道吉日的
        startService(new Intent(this, CoreService.class));

        //进行一些权限的申请
        requestSomePermissions();

    }

    protected void autoLogin() {
        String[] user = AppAndroid.getUserInfo();
        if (user != null && !TextUtils.isEmpty(user[1])) {
            if (Constants.isMobileUser(user[0])) {
                pushEventEx(false, null, new UserLoginRunner(user[1], user[2], "1"), this);
            } else {
                pushEventEx(false, null, new UserLoginRunner(user[1], user[2], user[0]), this);
            }
        } else {
            launch(LoginActivity.class);
        }
    }

    protected void launch(final Class cls) {
        if (runnable != null) {
            AppAndroid.getMainThreadHandler().removeCallbacks(runnable);
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                launch(LauncherActivity.this, cls);
                finish();
            }
        };

        long nowTime = System.currentTimeMillis();
        long time = nowTime - sTime > LTIME ? 0 : nowTime - sTime;
        if (time < 0) {
            time = 10;
        }
        AppAndroid.getMainThreadHandler().postDelayed(runnable, time);
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        toolbarAttribute.setHasToolbar(false);
        super.onInitToolbarAttribute(toolbarAttribute);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_USER_LOGIN:
                launch(LoginActivity.doLoginAction(event, this, false) ? MainTabActivity.class : LoginActivity.class);
                break;
        }
    }

    int cum;
    private long lime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lime > LTIME) {
            cum = 1;
            lime = System.currentTimeMillis();
            return;
        }
        if (cum < 6) {
            cum++;
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(LauncherActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(LauncherActivity.this);
    }


    /**
     * 设置主界面动画
     */
    private void initAnim() {
        //        myHandler.postDelayed(new Runnable() {
        //
        //            @Override
        //            public void run() {
        //                autoLogin();
        ////
        ////                myIntent = new Intent(context, MainActivity.class);
        ////                startActivityAndFinish(myIntent);
        //            }
        //        }, 3000);


        //        myHandler.postDelayed(new Runnable() {
        //
        //            @Override
        //            public void run() {
        //                autoLogin();
        //            }
        //        }, 3000);
        SPDBHelper spdbHelper = new SPDBHelper();
        try {
            String version = spdbHelper.getString("guidversion", "");
            final String vName = SystemUtils.getCurVersion(this);
            if (vName != null && !vName.equals(version)) {
                //                launch(GuidePageActivity.class);
                launch(LauncherActivity.this, GuidePageActivity.class);
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        myHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                autoLogin();
            }
        }, 3000);


        //        //检测版本更新
        //        new CheckVersion(LauncherActivity.this).getNewVersion(new CallBackInterface() {
        //            @Override
        //            public void doSome() {
        //
        //            }
        //
        //            @Override
        //            public void onFail() {
        //
        //            }
        //
        //            @Override
        //            public void onSuccess() {
        ////        myHandler.postDelayed(new Runnable() {
        //
        //            @Override
        //            public void run() {
        //                autoLogin();
        //            }
        //        }, 3000);
        //
        //            }
        //        });
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
        }
    };
}
