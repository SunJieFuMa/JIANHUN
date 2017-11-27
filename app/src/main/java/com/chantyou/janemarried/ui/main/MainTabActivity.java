package com.chantyou.janemarried.ui.main;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.CheckVersion.CallBackInterface;
import com.chantyou.janemarried.CheckVersion.CheckVersion;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.data.SharedPreferenceKey;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.ui.assistant.MyTaskActivity;
import com.chantyou.janemarried.ui.base.CitySelectActivity;
import com.chantyou.janemarried.ui.base.MarryPhasesAllActivity;
import com.chantyou.janemarried.ui.base.ThreadShareHelper;
import com.chantyou.janemarried.ui.excellent.ExcellentMarriageShopActivity;
import com.chantyou.janemarried.ui.left.AboutUsActivity;
import com.chantyou.janemarried.ui.left.PersonInfoActivity;
import com.chantyou.janemarried.ui.left.favorite.MyFavoriteActivity;
import com.chantyou.janemarried.ui.left.topic.MyTopicActivity;
import com.chantyou.janemarried.ui.pay.MyDingActivity;
import com.chantyou.janemarried.ui.topic.MarryGroupActivity;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.lib.mark.core.AndroidEventManager;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.ViewUtils;
import com.mhh.lib.utils.ExitUtil;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.widget.RippleView;
import com.nineoldandroids.view.ViewHelper;

import java.util.Map;

/**
 * Created by j_turn on 2015/12/8.
 * Email 766082577@qq.com
 */
public class MainTabActivity extends TabActivity implements EventManager.OnEventListener, View.OnClickListener, RippleView.OnRippleCompleteListener {


    private static final int REQUEST_PERMISSION_SDCARD_CODE = 1;
    //    @ViewInject(R.id.main_bg)
    //    private View mMainBg;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;
    @ViewInject(R.id.nav_view)
    private NavigationView mNavigationView;
    private TextView tvLCity;
    private TextView tvMarryState;
    private TextView tvMarryDate;
    @ViewInject(R.id.main_bg)
    private FrameLayout root;

    private static MainTabActivity activity;
    private ThreadShareHelper threadShareHelper;

    public static final void launch(Context context) {
        Intent intent = new Intent(context, MainTabActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_maintab);
        activity = this;

        ViewUtils.inject(this);

        addTab(MainActivity.class, R.string.main_0, R.drawable.xml_main_tab1_selector);
        //        addTab(ExcellentMarriageGoodsActivity.class, R.string.main_1, R.drawable.xml_main_tab2_selector);
        addTab(ExcellentMarriageShopActivity.class, R.string.main_1, R.drawable.xml_main_tab2_selector);
        addTab(MyTaskActivity.class, R.string.main_add, R.drawable.xml_main_tab5_add);
        addTab(MarriedAssistantActivity.class, R.string.main_2, R.drawable.xml_main_tab3_selector);
        addTab(MarryGroupActivity.class, R.string.main_3, R.drawable.xml_main_tab4_selector);
        mDrawerLayout.setScrimColor(0x00ffffff);//// 设置抽屉空余处颜色
        //        mDrawerLayout.setDrawerShadow(null, Gravity.LEFT);
        //        mMainBg.setBackgroundColor(getResources().getColor(R.color.c_blue));
        //        getTabHost().setOnTabChangedListener(new TabHost.OnTabChangeListener() {
        //            @Override
        //            public void onTabChanged(String tabId) {
        //                if (getResources().getString(R.string.main_0).equals(tabId)) {
        //                    mMainBg.setBackgroundColor(getResources().getColor(R.color.background));
        //                } else {
        //                    mMainBg.setBackgroundColor(getResources().getColor(R.color.background));
        //                }
        //            }
        //        });
        initEvents();

        setupNavigationView();

        AndroidEventManager.getInstance().addEventListener(XEventCode.EVENT_MAINMENU, this);
        AndroidEventManager.getInstance().addEventListener(XEventCode.HTTP_USER_INFO, this);
        /*final SPDBHelper spdbHelper = new SPDBHelper();
        try {
            String version = spdbHelper.getString("sp_icg", "");
            final String vName = SystemUtils.getCurVersion(this);
            if (vName != null && !vName.equals(version)) {
                final ImageView view = new ImageView(this);
                view.setImageResource(R.drawable.ic_g);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            root.removeView(view);
                            spdbHelper.putString("sp_icg", vName);
                        }
                        return true;
                    }
                });

                view.setBackgroundColor(Color.argb(155, 0, 0, 0));
                root.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        checkVerion();
        threadShareHelper = new ThreadShareHelper(this);
    }

    private void checkVerion() {
        //检测版本更新
        new CheckVersion(this).getNewVersion(new CallBackInterface() {
            @Override
            public void doSome() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess() {

            }
        });
    }


    private void setupNavigationView() {
        if (mNavigationView != null) {
            View header = mNavigationView.getHeaderView(0);
            ImageView ivHead = (ImageView) header.findViewById(R.id.ivHead);
            TextView tvName = (TextView) header.findViewById(R.id.tvName);
            tvLCity = (TextView) header.findViewById(R.id.tvLCity);
            tvMarryState = (TextView) header.findViewById(R.id.tvMarryState);
            tvMarryDate = (TextView) header.findViewById(R.id.tvMarryDate);
            ivHead.setOnClickListener(this);
            tvName.setOnClickListener(this);
            tvMarryDate.setOnClickListener(this);
            ((RippleView) header.findViewById(R.id.vMarryState)).setOnRippleCompleteListener(this);
            ((RippleView) header.findViewById(R.id.vitem1)).setOnRippleCompleteListener(this);
            ((RippleView) header.findViewById(R.id.vitem2)).setOnRippleCompleteListener(this);
            ((RippleView) header.findViewById(R.id.vitem3)).setOnRippleCompleteListener(this);
            ((RippleView) header.findViewById(R.id.vitem4)).setOnRippleCompleteListener(this);
            ((RippleView) header.findViewById(R.id.vitem5)).setOnRippleCompleteListener(this);
            //在这里声明后才会触发点击事件
            ((RippleView) header.findViewById(R.id.vitem_ding)).setOnRippleCompleteListener(this);
            ((RippleView) header.findViewById(R.id.vitem_share)).setOnRippleCompleteListener(this);

            setUserInfo();
        }
    }

    private void setUserInfo() {
        if (mNavigationView != null) {
            View header = mNavigationView.getHeaderView(0);
            ImageView ivHead = (ImageView) header.findViewById(R.id.ivHead);
            TextView tvName = (TextView) header.findViewById(R.id.tvName);

            HImageLoader.displayImage(Constants.getUserInfoByKey(Constants.SU_PHOTO), ivHead, R.drawable.defaulthead);
            tvName.setText(Constants.getUserInfoByKey(Constants.SU_NAME));
            tvMarryState.setText(Constants.getUserInfoByKey(Constants.SU_STATE));
            tvLCity.setText(Constants.getUserInfoByKey(Constants.SU_CITY));
            tvMarryDate.setText(Constants.getUserInfoByKey(Constants.SU_MARRYDATE));

            String userCity = SharedPreferenceUtils.getString(MainTabActivity.this, SharedPreferenceKey.userCity, "");
            if ("".equalsIgnoreCase(userCity)) {
                SharedPreferenceUtils.putString(MainTabActivity.this, SharedPreferenceKey.userCity, Constants.getUserInfoByKey(Constants.SU_CITY));
            }
        }
    }

    @Override
    public void onComplete(RippleView rv) {
        switch (rv.getId()) {
            case R.id.vMarryState:
                //                MarryPhasesAllActivity.launchForResult(this, true);
                //                launchPhasesSel();
                break;
            case R.id.vitem1:
                MyTaskActivity.launch(this, MyTaskActivity.class);
                break;
            case R.id.vitem2:
                CitySelectActivity.launchForResult(this, true);
                break;
            case R.id.vitem3:
                MyFavoriteActivity.launch(this, MyFavoriteActivity.class);
                break;
            case R.id.vitem4:
                MyTopicActivity.launch(this, MyTopicActivity.class);
                break;
            case R.id.vitem5:
                AboutUsActivity.launch(this, AboutUsActivity.class);
                break;
            case R.id.vitem_ding://我的订单
                Intent intent = new Intent(this, MyDingActivity.class);
                startActivity(intent);
                break;
            case R.id.vitem_share://分享APP
                if (threadShareHelper != null) {
                    threadShareHelper.setImageUrl(R.drawable.ic_logo + "");
                    String title = "简婚APP--备婚助手！结婚请柬、结婚工具";
                    String subtitle = "\n" +
                            "简婚是为准备结婚的新人提供结婚资讯以结婚工具的结婚服务移动应用，" +
                            "为简婚（北京）科技有限公司旗下主推服务。我们为新人提供全面的结婚消费服务信息，" +
                            "推荐合理化专业性的结婚一站式服务方案和便捷时尚的结婚服务体验，" +
                            "让新人摆脱为各项结婚事宜跑断腿、磨破嘴的状况。通过权威的第三方监管介入，" +
                            "实现结婚过程零缝隙无差错，让新人享受结婚的过程而不是为了结婚而操心受累。" +
                            "使用婚礼小当家，规划结婚流程，预估结婚预算，让新人实实在在的掌控自己的婚礼。" +
                            "同时提出“不要裸婚，要简婚！”，回归爱情本身，体现幸福实质。";
//                    threadShareHelper.showShareDialog("http://www.ijianhun.com", title, subtitle);
                    threadShareHelper.showShareDialog("\n" +
                            "http://android.myapp.com/myapp/detail.htm?apkName=com.chantyou.janemarried", title, subtitle);
                }
                break;
        }
    }

    public static void launchPhasesSel() {
        if (activity != null) {
            MarryPhasesAllActivity.launchForResult(activity, true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHead:
            case R.id.tvName:
            case R.id.tvMarryDate:
                PersonInfoActivity.launch(this, PersonInfoActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        AndroidEventManager.getInstance().removeEventListener(XEventCode.EVENT_MAINMENU, this);
        AndroidEventManager.getInstance().removeEventListener(XEventCode.HTTP_USER_INFO, this);
    }

    protected void addTab(Class<?> cls, int textId, int iconId) {
        final TabHost tabHost = getTabHost();
        final TabHost.TabSpec tabSpec = tabHost.newTabSpec(getResources().getString(textId));

        View tab = LayoutInflater.from(this).inflate(R.layout.tabview, null);
        if (R.string.main_add == textId) {
            ((TextView) tab.findViewById(R.id.tab_text)).setVisibility(View.GONE);//让第三个只显示图片，不显示文字
        }
        ((TextView) tab.findViewById(R.id.tab_text)).setText(textId);
        ((ImageView) tab.findViewById(R.id.tab_image)).setImageResource(iconId);
        Intent intent = new Intent(this, cls);
        final Intent data = getIntent();
        final Bundle b = data == null ? null : data.getExtras();
        if (b != null) {
            intent.putExtras(b);
        }
        tabSpec.setIndicator(tab).setContent(intent);
        tabHost.addTab(tabSpec);
    }

    public void OpenRightMenu(View view) {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);
    }

    private void initEvents() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView.findViewById(R.id.nav_headerview);
                float scale = 1 - slideOffset;
                float rightScale = 0.75f + scale * 0.25f;

                //                if (drawerView.getTag().equals("LEFT"))
                //                {

                float leftScale = 1 - 0.25f * scale;

                ViewHelper.setScaleX(mMenu, leftScale);
                ViewHelper.setScaleY(mMenu, leftScale);
                ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
                ViewHelper.setPivotX(mContent, 0);
                ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                mContent.invalidate();
                ViewHelper.setScaleX(mContent, rightScale);
                ViewHelper.setScaleY(mContent, rightScale);


                //                } else
                //                {
                //                    ViewHelper.setTranslationX(mContent,
                //                            -mMenu.getMeasuredWidth() * slideOffset);
                //                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                //                    ViewHelper.setPivotY(mContent,
                //                            mContent.getMeasuredHeight() / 2);
                //                    mContent.invalidate();
                //                    ViewHelper.setScaleX(mContent, rightScale);
                //                    ViewHelper.setScaleY(mContent, rightScale);
                //                }


            }

            @Override
            public void onDrawerOpened(View drawerView) {
                System.out.println("打开的");
                //                ab_adds.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
                System.out.println("关闭的");
                //                ab_adds.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
    }

    @Override
    public void onEventRunEnd(Event event) {
        switch (event.getEventCode()) {
            case XEventCode.EVENT_MAINMENU:
                if ((Boolean) event.getParamsAtIndex(0)) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.closeDrawers();
                }
                break;
            case XEventCode.HTTP_USER_INFO:
                if (event.isSuccess()) {
                    setUserInfo();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CitySelectActivity.RCODE) {
                if (tvLCity != null) {
                    tvLCity.setText(data.getStringExtra("returncity"));
                }
            } else if (requestCode == MarryPhasesAllActivity.REQCODE) {
                String phasesinfo = data.getStringExtra("phasesinfo");
                Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(phasesinfo);
                //                tvProcess.setText(JsonUtil.getItemString(map, "name"));
                //                phaseId = JsonUtil.getItemInt(map, "phaseId");
            }
        }

        if (threadShareHelper != null) {
            threadShareHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    private long cTime;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mDrawerLayout != null) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    if (System.currentTimeMillis() - cTime > 1500) {
                        cTime = System.currentTimeMillis();
                        Toast.makeText(this, "再点击一次退出应用", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    ExitUtil.getInstance().exit();
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MainTabActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MainTabActivity.this);
    }
}
