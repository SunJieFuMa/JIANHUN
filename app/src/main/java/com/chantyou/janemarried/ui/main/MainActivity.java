package com.chantyou.janemarried.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.main.MainPageAdapter;
import com.chantyou.janemarried.bean.MarryState;
import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.home.MainPageRunner;
import com.chantyou.janemarried.httprunner.my.UserEditRunner;
import com.chantyou.janemarried.httprunner.my.UserInfoRunner;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.chantyou.janemarried.utils.APKUtil2;
import com.chantyou.janemarried.utils.HImageLoader;
import com.chantyou.janemarried.utils.SPDBHelper;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.base.ScalePageTransformer;
import com.mhh.lib.utils.FileHelper;
import com.mhh.lib.utils.FilePaths;
import com.mhh.lib.utils.JsonUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import space.sye.z.library.manager.RecyclerMode;

//首页
public class MainActivity extends PullrefreshBottomloadActivity {

    @ViewInject(R.id.vMarryStep)
    private View vMarryStep;
    @ViewInject(R.id.tvMarryStep)
    private TextView tvMarryStep;
    @ViewInject(R.id.backdrop)
    private ImageView backdrop;
    private MainPageAdapter mAdapter;
    private ViewPager viewpager;
    private int[] imgs={R.drawable.icon_state_ready,R.drawable.icon_state_food_appointment,
            R.drawable.icon_state_takephoto, R.drawable.icon_state_wedding_appointment,
            R.drawable.icon_state_procuctbuy,R.drawable.icon_state_zhuchi,
            R.drawable.icon_state_travel};
//    private List<ImageView> mImageViews=new ArrayList<>();
    private LinearLayout ll_container;
//    private String[] strs={"前期准备","婚宴预约","婚纱摄影","婚礼策划","婚品购置","化妆、主持、摄像","蜜月旅行"};
    private List<String> strState=new ArrayList<>();
    private String state;//结婚阶段状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        super.onCreate(savedInstanceState);
        /*
        * 使用CollapsingToolbarLayout时必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上不会显示
        * */
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);

        addAndroidEventListener(XEventCode.HTTP_USER_EDIT, this);
        addAndroidEventListener(XEventCode.HTTP_MARRY_DAY_EDIT, this);
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        ll_container= (LinearLayout) findViewById(R.id.ll_container);
        requestMarryState();//先从服务器获取所有的婚礼状态
    }

    private void setViewpager() {
        //高度设置成填充父窗体，这样图片下面的文字才能显示出来
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                AppAndroid.getScreenWidth() / 4, ViewGroup.LayoutParams.MATCH_PARENT);
        //        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        //                AppAndroid.getScreenWidth() / 4, APKUtil2.dip2px(this,100));
        params.setMargins(0,APKUtil2.dip2px(this,45),0,0);
        viewpager.setLayoutParams(params);
        viewpager.setPageMargin(15); // 设置各页面的间距，好像没用
        // 将父节点的触摸事件分发给viewpager，否则只能滑动中间的一个view对象
        ll_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewpager.dispatchTouchEvent(event);
            }
        });
        viewpager.setPageTransformer(true, new ScalePageTransformer(0.90f, 0.65f));//用于ViewPager的滑动动画
        viewpager.setOffscreenPageLimit(7);
        viewpager.setAdapter(new MyViewPagerAdapter());
        initListener();
    }

    private void requestMarryState() {
        OkHttpUtils
                .post()
                .url(UrlConfig.PHASES_ALL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AppAndroid.getContext(), "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                        System.out.println("response::" + response);
                    }
                });
    }

    private void parseJson(String response) {
        MarryState marryState = new Gson().fromJson(response, MarryState.class);
        List<MarryState.PhasesEntity> phases = marryState.getPhases();
        if (null!=phases && phases.size()>0){
            for (int i=0;i<phases.size();i++){
                String name = phases.get(i).getName();
                strState.add(name);
            }
        }
        setViewpager();
    }

    private void initListener() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //                Toast.makeText(MainActivity.this, " ::"+position, Toast.LENGTH_SHORT).show();
                //                MainTabActivity.launchPhasesSel();
                //                showProgressDialog(null, "正在修改...");
                pushEventEx(false, null, new UserEditRunner("", "", strState.get(position), "", "", "", ""), MainActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view=View.inflate(MainActivity.this,R.layout.item_main_viewpager,null);
            ImageView iv= (ImageView) view.findViewById(R.id.iv);
            iv.setImageResource(imgs[position]);
            TextView tv= (TextView) view.findViewById(R.id.tv);
            tv.setText(strState.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.HTTP_USER_EDIT, this);
        removeEventListener(XEventCode.HTTP_MARRY_DAY_EDIT, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        toolbarAttribute.setNavigation(true, getResources().getDrawable(R.drawable.icon_user),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runEvent(XEventCode.EVENT_MAINMENU, true);
                    }
                });
    }

    @Override
    protected void init() {
        super.init();
        vMarryStep.setVisibility(View.GONE);
        onPullDown();
    }

    @Override
    public void onPullDown() {
        super.onPullDown();
        pageCur = 1;
        pushEvent(new MainPageRunner(pageCur));
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        pageCur += 1;
        pushEvent(new MainPageRunner(pageCur));
    }

    @Override
    protected void setupRecyclerView() {
        mAdapter = new MainPageAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), mAdapter, RecyclerMode.BOTTOM);
    }

    public void checkin(View view) {
        Snackbar.make(view, "checkin success!", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem item = menu.findItem(R.id.main_menu);
//        item.setIcon(R.drawable.icon_mian_right);
        return true;
    }

    /**
     * Toolbar 菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private static void loadUrlPic(final Context cxt, final String url, final String key) {
        if (url != null) {
            HImageLoader.loadImge(url, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    if (bitmap != null) {
                        String path = FilePaths.getUrlFileCachePath(cxt, url);
                        FileHelper.saveBitmapToFile(path, bitmap);
                        new SPDBHelper().putString(key, url);
                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_HOME_PAGE:
                try {
                    onRefreshCompleted();
                    if (event.isSuccess()) {
                        Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("mainpages");
                        if ("1".equals(String.valueOf(event.getParamsAtIndex(0)))) {
                            mAdapter.clear();
                            String welcome = JsonUtil.getItemString(map, "welcome");
                            String pic_welcome = new SPDBHelper().getString("pic_welcome", "");
                            if (welcome != null && !welcome.equalsIgnoreCase(pic_welcome)) {
                                loadUrlPic(this, welcome, "pic_welcome");
                            }
                            vMarryStep.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    PersonInfoActivity.launch(v.getContext(), PersonInfoActivity.class);
                                    MainTabActivity.launchPhasesSel();
                                }
                            });
                            if (list != null && list.size() > 0) {
                                Map<String, Object> map1 = list.get(0);
                                if (JsonUtil.getItemInt(map1, "type") == 3) {
                                    vMarryStep.setVisibility(View.GONE);
                                    vMarryStep.setClickable(true);
                                    state = JsonUtil.getItemString(map1, "state");
                                    if (TextUtils.isEmpty(state)) {
                                        state = "未设置婚礼阶段";
                                    }
                                    HImageLoader.displayImage(JsonUtil.getItemString(map1, "bgpic"), backdrop, HImageLoader.createOptions(R.drawable.state1, 100));
                                    tvMarryStep.setText(state);
                                    setViewPagerShow();//根据用户的婚礼状态设置viewpager选中哪个item
                                } else {
                                    vMarryStep.setVisibility(View.VISIBLE);
                                    vMarryStep.setClickable(true);
                                    tvMarryStep.setText("完善信息");
                                }
                            } else {
                                vMarryStep.setVisibility(View.VISIBLE);
                                tvMarryStep.setText("未设置婚礼阶段");
                            }
                        }
                        hasMore(list != null && list.size() >= 10);
                        mAdapter.addData(list);
                    } else {
                        CustomToast.showWorningToast(this, event.getMessage("查询失败"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
                break;
            case XEventCode.HTTP_MARRY_DAY_EDIT:
            case XEventCode.HTTP_USER_EDIT:
                if (event.isSuccess()) {
                    pushEventEx(false, null, new UserInfoRunner(), this);
                }
                break;
            case XEventCode.HTTP_USER_INFO:
                if (event.isSuccess()) {
                    onPullDown();
                }
                break;
        }
    }

    private void setViewPagerShow() {
        for (int i=0;i<strState.size();i++){
            if (strState.get(i).equals(state)){
                viewpager.setCurrentItem(i);
                return;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MainActivity.this);
        /*
        * 我发现APP在后台一段时间的话，头部的viewpager有可能会失效，所以在这处理一下
        * 好像是token失效的原因，对，应该是token失效的问题，A设备上登录了我的账号以后，
        * B设备上再登录，然后再回到A设备的话，只有当你再此请求api时，而且有时候请求一次还不行，得2次，
        * 才会提示你token过期要重新登录
        * 请求api的时候滑动就失效了，我也不明白为什么只是滑动失效，很奇葩的bug，能滑动但是不会切换数据了
        * 而别的请求api没有被影响
        * */
        initListener();
        addAndroidEventListener(XEventCode.HTTP_USER_EDIT, this);
        addAndroidEventListener(XEventCode.HTTP_MARRY_DAY_EDIT, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MainActivity.this);
    }


}
