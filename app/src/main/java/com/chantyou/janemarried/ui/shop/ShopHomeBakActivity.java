package com.chantyou.janemarried.ui.shop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.shop.ShopCommentAdapter;
import com.chantyou.janemarried.adapter.shop.ShopDemoAdapter;
import com.chantyou.janemarried.adapter.shop.ShopPackageAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.ShopCommentAddRunner;
import com.chantyou.janemarried.httprunner.shop.ShopCommentListRunner;
import com.chantyou.janemarried.httprunner.shop.ShopDemoListRunner;
import com.chantyou.janemarried.httprunner.shop.ShopDisFollowRunner;
import com.chantyou.janemarried.httprunner.shop.ShopFollowRunner;
import com.chantyou.janemarried.httprunner.shop.ShopInfoRunner;
import com.chantyou.janemarried.httprunner.shop.ShopPackageListRunner;
import com.chantyou.janemarried.model.Shop.ShopCommentListBean;
import com.chantyou.janemarried.model.Shop.ShopDemoListBean;
import com.chantyou.janemarried.model.Shop.ShopInfoBean;
import com.chantyou.janemarried.model.Shop.ShopPackageListBean;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.chantyou.janemarried.utils.SysCommon;
import com.chantyou.janemarried.view.MyVideoView;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.mhh.lib.ToastManager;
import com.mhh.lib.annotations.ViewInject;

import java.util.HashMap;
import java.util.List;

//商铺首页展示

/**
 * Created by j_turn on 2015/12/12 14:24
 * Email：766082577@qq.com
 */

public class ShopHomeBakActivity extends PullrefreshBottomloadActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener {

    @ViewInject(R.id.fl_shop_home)
    private FrameLayout fl_shop_home;
    @ViewInject(R.id.fl_shop_demo)
    private FrameLayout fl_shop_demo;
    @ViewInject(R.id.fl_shop_package)
    private FrameLayout fl_shop_package;
    @ViewInject(R.id.frame_shop_rate)
    private FrameLayout frame_shop_rate;

    @ViewInject(R.id.tv_shop_home)
    private TextView tv_shop_home;
    @ViewInject(R.id.tv_shop_demo)
    private TextView tv_shop_demo;
    @ViewInject(R.id.tv_shop_package)
    private TextView tv_shop_package;
    @ViewInject(R.id.tv_shop_rate)
    private TextView tv_shop_rate;

    @ViewInject(R.id.ln_package_item)
    private LinearLayout ln_package_item;
//    @ViewInject(R.id.lin_shop_demo_item)
//    private LinearLayout lin_shop_demo_item;


    @ViewInject(R.id.tv_shop_name)
    private TextView tv_shop_name;
    @ViewInject(R.id.iv_shop_image)
    private ImageView iv_shop_image;
    @ViewInject(R.id.tv_range)
    private TextView tv_range;
    @ViewInject(R.id.tv_follow)
    private TextView tv_follow;
    @ViewInject(R.id.iv_follow)
    private ImageView iv_follow;
    @ViewInject(R.id.tv_follow_add)
    private TextView tv_follow_add;
    @ViewInject(R.id.tv_desc)
    private TextView tv_desc;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.tv_collect)
    private TextView tv_collect;
    @ViewInject(R.id.tv_tip_company)
    private TextView tv_tip_company;
    @ViewInject(R.id.iv_shop_bg)
    private ImageView iv_shop_bg;
    @ViewInject(R.id.iv_bz)
    private ImageView iv_bz;

    private String shopId;
    private int shopLevel;

    @ViewInject(R.id.vv_shop_video)
    private MyVideoView vv_shop_video;
    MediaController mediaco;
    private MediaController mediaController;


    @ViewInject(R.id.iv_shop_video)
    private ImageView iv_shop_video;

    //套餐
    @ViewInject(R.id.lv_shop_package)
    private ListView lv_shop_package;
    //案例
    @ViewInject(R.id.lv_shop_demo)
    private ListView lv_shop_demo;
    //评论
    @ViewInject(R.id.lv_shop_comment)
    private ListView lv_shop_comment;


    @ViewInject(R.id.tv_appointment)
    private TextView tv_appointment;
    @ViewInject(R.id.rl_vv)
    private RelativeLayout rl_vv;

    @ViewInject(R.id.sv_shop)
    private ScrollView sv_shop;


    @ViewInject(R.id.title_back)
    private Button title_back;
    @ViewInject(R.id.title_setting)
    private Button title_setting;
    @ViewInject(R.id.title_title)
    private TextView title_title;


    //评论
    @ViewInject(R.id.vComment)
    private View vComment;
    @ViewInject(R.id.ivBack)
    private ImageView ivBack;
    @ViewInject(R.id.btnComment)
    private TextView btnComment;
    @ViewInject(R.id.et)
    private EditText et;
    @ViewInject(R.id.vomm)
    private View vomm;

    private boolean isFollowed = false;//是否已收藏

    private String shopPhone;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shop_home;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        setContentView(R.layout.activity_shop_home);

        initListener();

        shopId = getIntent().getExtras().get("id").toString();
        shopLevel = getIntent().getExtras().getInt("shopLevel");

        initHome();
        title_title.setText("商户");
    }


    @Override
    protected void setupRecyclerView() {
//        ShopDemoAdapter shopDemoAdapter = new ShopDemoAdapter(ShopHomeActivity.this, shopDemos);
//        setupRecyclerView(new LinearLayoutManager(this), shopDemoAdapter, RecyclerMode.BOTH);
    }


    private void initListener() {
        //切换项目
        tv_shop_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//首页
                fl_shop_home.setVisibility(View.VISIBLE);
                fl_shop_demo.setVisibility(View.GONE);
                frame_shop_rate.setVisibility(View.GONE);
                fl_shop_package.setVisibility(View.GONE);

                //评论
                title_setting.setVisibility(View.INVISIBLE);
                vComment.setVisibility(View.GONE);
                vomm.setVisibility(View.GONE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘


            }
        });
        tv_shop_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//套餐
                fl_shop_home.setVisibility(View.GONE);
                fl_shop_demo.setVisibility(View.GONE);
                frame_shop_rate.setVisibility(View.GONE);
                fl_shop_package.setVisibility(View.VISIBLE);
                //评论
                title_setting.setVisibility(View.INVISIBLE);
                vComment.setVisibility(View.GONE);
                vomm.setVisibility(View.GONE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘


                if (null != vv_shop_video && vv_shop_video.isPlaying()) {
                    vv_shop_video.pause();
                    iv_shop_video.setVisibility(View.VISIBLE);
                }

                initPackage();
            }
        });
        tv_shop_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//案例
                fl_shop_home.setVisibility(View.GONE);
                fl_shop_demo.setVisibility(View.VISIBLE);
                fl_shop_package.setVisibility(View.GONE);
                frame_shop_rate.setVisibility(View.GONE);
                //评论
                title_setting.setVisibility(View.INVISIBLE);
                vComment.setVisibility(View.GONE);
                vomm.setVisibility(View.GONE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘

                if (null != vv_shop_video && vv_shop_video.isPlaying()) {
                    vv_shop_video.pause();
                    iv_shop_video.setVisibility(View.VISIBLE);
                }

                initDemo();

            }


        });
        tv_shop_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//评价
                fl_shop_home.setVisibility(View.GONE);
                fl_shop_demo.setVisibility(View.GONE);
                fl_shop_package.setVisibility(View.GONE);
                frame_shop_rate.setVisibility(View.VISIBLE);
                //评论
//                title_setting.setVisibility(View.VISIBLE);
                vComment.setVisibility(View.VISIBLE);
                vomm.setVisibility(View.VISIBLE);

                if (null != vv_shop_video && vv_shop_video.isPlaying()) {
                    vv_shop_video.pause();
                    iv_shop_video.setVisibility(View.VISIBLE);
                }

                initComment();//初始化评论
            }
        });


        //收藏
        tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowed) {//已关注，取消关注
                    pushEventEx(false, "", new ShopDisFollowRunner(shopId), ShopHomeBakActivity.this);
                } else {//未关注，加关注
                    pushEventEx(false, "", new ShopFollowRunner(shopId), ShopHomeBakActivity.this);
                }
            }
        });


        //加关注
        tv_follow_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowed) {//已关注，取消关注
                    pushEventEx(false, "", new ShopDisFollowRunner(shopId), ShopHomeBakActivity.this);
                } else {//未关注，加关注
                    pushEventEx(false, "", new ShopFollowRunner(shopId), ShopHomeBakActivity.this);
                }
            }
        });

        //预约到店
        tv_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //V1
                //暂时不开启
//                AppointmentActivity.launch(ShopHomeActivity.this, Integer.parseInt(shopId));

                //V2
//                if (!TextUtils.isEmpty(shopPhone)) {
//                    Intent intent = new Intent(Intent.ACTION_DIAL);
//                    Uri data = Uri.parse("tel:" + shopPhone);
//                    intent.setData(data);
//                    startActivity(intent);
//                }
//

                //V3
//                if (!TextUtils.isEmpty(shopPhone)) {
//                    LayoutInflater factory = LayoutInflater.from(ShopHomeActivity.this);
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ShopHomeActivity.this);
//                    final View textEntryView = factory.inflate(R.layout.alertdialog, null);
//                    ((TextView) textEntryView.findViewById(R.id.title)).setText("联系我们");
//                    ((TextView) textEntryView.findViewById(R.id.txt_tel)).setText(shopPhone);
//
//                    ((TextView) textEntryView.findViewById(R.id.txt_tel)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    Window window = dialog.getWindow();
//                    window.setContentView(textEntryView);
//
//                    ((TextView) textEntryView.findViewById(R.id.txt_tel)).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(Intent.ACTION_DIAL);
//                            Uri data = Uri.parse("tel:" + shopPhone);
//                            intent.setData(data);
//                            startActivity(intent);
//                        }
//                    });
//                }


                //V4
                if (!TextUtils.isEmpty(shopPhone)) {
                    LayoutInflater factory = LayoutInflater.from(ShopHomeBakActivity.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShopHomeBakActivity.this);
                    final View textEntryView = factory.inflate(R.layout.alertdialog, null);
//                    ((TextView) textEntryView.findViewById(R.id.title)).setText("联系我们");
                    ((TextView) textEntryView.findViewById(R.id.txt_tel)).setText(shopPhone);

//                    ((TextView) textEntryView.findViewById(R.id.txt_tel)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    Window window = dialog.getWindow();
                    window.setContentView(textEntryView);
                    dialog.setCanceledOnTouchOutside(true);
                    ((TextView) textEntryView.findViewById(R.id.txt_tel)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + shopPhone);
                            intent.setData(data);
                            startActivity(intent);
                        }
                    });

                    ((ImageView) textEntryView.findViewById(R.id.iv_tel)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + shopPhone);
                            intent.setData(data);
                            startActivity(intent);
                        }
                    });
                }

            }


        });

        //评论
        title_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (View.VISIBLE == vComment.getVisibility()) {//评论显示  点击隐藏
                    vComment.setVisibility(View.GONE);
                    vomm.setVisibility(View.GONE);
                } else {//评论隐藏状态  点击显示
                    vComment.setVisibility(View.VISIBLE);
                    vomm.setVisibility(View.VISIBLE);
                }
            }
        });
        //评论返回
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vComment.setVisibility(View.GONE);
                vomm.setVisibility(View.GONE);
            }
        });
        //发表评论
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = et.getText().toString().trim();
                if (TextUtils.isEmpty(txt)) {
                    ToastManager.getInstance(ShopHomeBakActivity.this).show("请输入评论");
                } else {
                    pushEventEx(false, "", new ShopCommentAddRunner(shopId, txt), ShopHomeBakActivity.this);
                }

            }
        });

        //返回
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    //获取主页
    @SuppressLint("NewApi")
    private void initHome() {


        if (!TextUtils.isEmpty(shopId)) {
            pushEventEx(false, "", new ShopInfoRunner(shopId), this);
        }


        if (shopLevel > 0) {
            switch (shopLevel) {
//                case 1: {//一般商家
//                    tv_tip_company.setText("一般商家");
//                }
//                break;
//                case 2: {//优质商家
//                    tv_tip_company.setText("优质商家");
//                }
//                break;
//                case 3: {//认证商家
//                    tv_tip_company.setText("认证商家");
//                }
//                break;
                case 1: {//一般商家
//                        holder.tv_tip_company.setVisibility(View.VISIBLE);
                    tv_tip_company.setText("商家");
                    tv_tip_company.setBackground(getResources().getDrawable(R.drawable.btn_tips_grey));
                }
                break;
                case 2: {//优质商家
//                        tv_tip_company_good.setVisibility(View.VISIBLE);
                    tv_tip_company.setText("优质");
                    tv_tip_company.setTextColor(getResources().getColor(R.color.white));
                    tv_tip_company.setBackground(getResources().getDrawable(R.drawable.btn_tips_yellow));
                }
                break;
                case 3: {//认证商家
//                        tv_tip_company_great.setVisibility(View.VISIBLE);
                    tv_tip_company.setText("认证");
                    tv_tip_company.setTextColor(getResources().getColor(R.color.white));
                    tv_tip_company.setBackground(getResources().getDrawable(R.drawable.btn_tips_blue));
                }
                break;
            }

        }


//        initVideo("http://jinyou.oss-cn-qingdao.aliyuncs.com/LG/lg.mp4");
//        initVideo("http://jinyouapp.com/badriver/mm.mp4");
    }

    private boolean isPlay = false;


    private boolean videoIsError = false;

    @SuppressLint("NewApi")
    private void initVideo(String videoUrl) {

//        //设置视频源播放res/raw中的文件,文件名小写字母,格式: 3gp,mp4等,flv的不一定支持;
//        Uri rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.shuai_dan_ge);
//        vv_shop_video.setVideoURI(rawUri);

        if (TextUtils.isEmpty(videoUrl)) {

            return;
        }


        if (SysCommon.isNotWifi(ShopHomeBakActivity.this)) {
            ToastManager.getInstance(ShopHomeBakActivity.this).show("由于视频文件较大，请切换至无线网络后重新打开");
            return;
        }


        if (TextUtils.isEmpty(videoUrl)) {
            return;
        } else {
            if (!videoUrl.matches("[^\\s]+(\\.(?i)(mp4|rmvb|flv|mpeg|3gp|avi))$")) {
                System.out.println("zhuwx：不是视频" + videoUrl);
                return;
            }
        }
        rl_vv.setVisibility(View.VISIBLE);
        final Uri mVideoUri = Uri.parse(videoUrl);
//        final MyVideoView vv_shop_video = new MyVideoView(ShopHomeActivity.this);
        // 播放在线视频


        vv_shop_video.setVideoURI(mVideoUri);
        vv_shop_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
//                ToastManager.getInstance(ShopHomeActivity.this).show("该视频无法播放");
                videoIsError = true;
                return true;
            }
        });
//        vv_shop_video.setVideoPath(mVideoUri.toString());

        //添加播放控制条,还是自定义好点
//        vv_shop_video.setMediaController(new MediaController(this));
//        mediaController = new MediaController(this);
//        vv_shop_video.setMediaController(mediaController);
//        vv_shop_video.requestFocus();

//        vv_shop_video.pause();


        rl_vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoIsError) {
                    ToastManager.getInstance(ShopHomeBakActivity.this).show("该视频无法播放");
                    return;
                }
                if (!vv_shop_video.isPlaying()) {
//                    ToastManager.getInstance(ShopHomeActivity.this).show("开始播放");

                    vv_shop_video.start();
                    iv_shop_video.setVisibility(View.GONE);
                } else {
//                    ToastManager.getInstance(ShopHomeActivity.this).show("暂停播放");
                    vv_shop_video.pause();
                    iv_shop_video.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }


    //获取套餐
    private void initPackage() {
        if (!TextUtils.isEmpty(shopId)) {
            System.out.println("zhuwx:id:" + shopId);
            pushEventEx(false, "", new ShopPackageListRunner(shopId), this);
        }
    }

    //获取案例
    private void initDemo() {
        if (!TextUtils.isEmpty(shopId)) {
            System.out.println("zhuwx:id:" + shopId);
            pushEventEx(false, "", new ShopDemoListRunner(shopId), this);
        }
    }

    //获取评论
    private void initComment() {
        if (!TextUtils.isEmpty(shopId)) {
            System.out.println("zhuwx:id:" + shopId);
            pushEventEx(false, "", new ShopCommentListRunner(shopId, 1 + "", 1000 + ""), this);
        }
    }


    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        //返回
        toolbarAttribute.setNavigation(true, getResources().getDrawable(R.drawable.navbar_icon_back), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbarAttribute.setTitleAttr(true, Gravity.CENTER, "商家信息");
    }


    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置右侧按钮
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem item = menu.findItem(R.id.main_menu);
//        item.setIcon(R.drawable.navbar_icon_share);//设置右侧按钮
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ShopHomeBakActivity.this);

    }

    @Override
    protected void onPause() {

        super.onPause();
        StatService.onPause(ShopHomeBakActivity.this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //后台返回数据
    @Override
    public void onEventRunEnd(Event event) {
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SHOP_INFO://店铺详情
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopInfoBean shopListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopInfoBean.class);
                    System.out.println("zhuwx:" + shopListBean.getMsg());

                    showShopInfo(shopListBean.getData().get(0));
                }
                break;
            case XEventCode.HTTP_SHOP_PACKAGE://套餐
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopPackageListBean shopPackageListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopPackageListBean.class);
//                    System.out.println("zhuwx:" + shopPackageListBean.getMsg());

                    showPackage(shopPackageListBean.getData());
//                    ShopInfoBean shopListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopInfoBean.class);
//                    System.out.println("zhuwx:" + shopListBean.getMsg());

//                    showShopInfo(shopListBean.getData().get(0));
                }
                break;

            case XEventCode.HTTP_SHOP_FOLLOW://加收藏
                if (event.isSuccess()) {
                    ToastManager.getInstance(ShopHomeBakActivity.this).show(event.getMessage("收藏成功"));
                    isFollowed = true;
                    tv_collect.setText("已收藏");
                    tv_follow_add.setText("√ 已关注");
                } else {
                    System.out.println("zhuwx:" + event.getMessage("失败"));
                    ToastManager.getInstance(ShopHomeBakActivity.this).show(event.getMessage("收藏失败，请重试"));
                }
                break;
            case XEventCode.HTTP_SHOP_DIS_FOLLOW://取消收藏
                if (event.isSuccess()) {
                    ToastManager.getInstance(ShopHomeBakActivity.this).show(event.getMessage("取消收藏成功"));
                    isFollowed = false;
                    tv_collect.setText("收藏");
                    tv_follow_add.setText("+关注");
                } else {
                    System.out.println("zhuwx:" + event.getMessage("失败"));
                    ToastManager.getInstance(ShopHomeBakActivity.this).show(event.getMessage("取消收藏失败，请重试"));
                }
                break;
            case XEventCode.HTTP_SHOP_DEMO://案例
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopDemoListBean shopPackageListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopDemoListBean.class);
                    showDemo(shopPackageListBean.getData());
//                    ShopInfoBean shopListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopInfoBean.class);
//                    System.out.println("zhuwx:" + shopListBean.getMsg());

//                    showShopInfo(shopListBean.getData().get(0));
                }
                break;

            case XEventCode.HTTP_SHOP_COMMENT_ADD://添加评论成功
                if (event.isSuccess()) {
                    ToastManager.getInstance(ShopHomeBakActivity.this).show(event.getMessage("评论成功"));
//                    vComment.setVisibility(View.GONE);

                    initComment();//初始化评论

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0); //强制隐藏键盘
                } else {
                    ToastManager.getInstance(ShopHomeBakActivity.this).show(event.getMessage("评论失败"));
                }
                break;
            case XEventCode.HTTP_SHOP_COMMENT_LIST://获取评论列表
                if (event.isSuccess()) {
                    System.out.println("zhuwx" + event.getParamsAtIndex(0));
                    Gson gson = new Gson();
                    ShopCommentListBean listBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopCommentListBean.class);
                    showComment(listBean.getData());
                } else {

                }
                break;
        }
    }

    //展示首页详情
    private void showShopInfo(final ShopInfoBean.DataBean shopInfo) {
        if (!TextUtils.isEmpty(shopInfo.getImageUrl())) {
            HImageLoader.displayImage(shopInfo.getImageUrl(), iv_shop_image, R.mipmap.push);
            HImageLoader.displayImage(shopInfo.getBackGroundUrl(), iv_shop_bg, R.drawable.state4);
        }
        shopPhone = shopInfo.getPhone();

        tv_shop_name.setText(shopInfo.getName());//店铺名称
        tv_range.setText(shopInfo.getLabel());
        tv_shop_package.setText("套餐(" + shopInfo.getProductCount() + ")");
        tv_shop_demo.setText("案例(" + shopInfo.getEgCount() + ")");
        tv_shop_rate.setText("评价(" + "0" + ")");

        tv_follow.setText("粉丝" + shopInfo.getCollectCount());


        String shopp = shopInfo.getDescs();
        if (!TextUtils.isEmpty(shopp)) {
            tv_desc.setText(Html.fromHtml(shopInfo.getDescs()));//店铺信息
        }

        tv_address.setText(shopInfo.getAddress());//店铺地址

        //保证金
        if (1 == shopInfo.getIsBill()) {
            iv_bz.setVisibility(View.VISIBLE);
        }

        //收藏
        int isCollect = shopInfo.getIsCollect();
        if (1 == isCollect) {
            isFollowed = true;
            tv_collect.setText("已收藏");
            tv_follow_add.setText("√ 已关注");
        } else {
            isFollowed = false;
            tv_collect.setText("收藏");
            tv_follow_add.setText("+关注");
        }


        final String videoUrl = shopInfo.getVideoUrl();
        initVideo(videoUrl);
        //点击播放视频
//        initVideo(shopInfo.getVideoUrl());
//        iv_shop_video.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("NewApi")
//            @Override
//            public void onClick(View v) {
//
//                System.out.println("zhuwx：视频地址" + videoUrl);
//                if (TextUtils.isEmpty(videoUrl)) {
//                    return;
//                } else {
//                    if (!videoUrl.matches("[^\\s]+(\\.(?i)(mp4|rmvb|flv|mpeg|3gp|avi))$")) {
//                        System.out.println("zhuwx：不是视频" + videoUrl);
//                        return;
//                    }
//                }
//
//                if (SysCommon.isNotWifi(ShopHomeActivity.this)) {
////                    ToastManager.getInstance(ShopHomeActivity.this).show("由于视频文件较大，请切换至无线网络后重新打开");
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ShopHomeActivity.this);
//                    builder.setTitle("友情提示")//设置对话框标题
//                            .setMessage("检查到您未开启无线网络，是否继续打开此视频")//设置显示的内容
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//                                    Uri uri = Uri.parse(videoUrl);
//                                    //调用系统自带的播放器
//                                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                                    intent.setDataAndType(uri, "video/*");
//                                    startActivity(intent);
//                                }
//
//                            })
//
//                            .setCancelable(true)//不可取消
//                            .show();//在按键响应事件中显示此对话框
//                } else {
//                    Uri uri = Uri.parse(videoUrl);
//                    //调用系统自带的播放器
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(uri, "video/*");
//                    startActivity(intent);
//                }
//
//
//            }
//        });

    }

    //显示套餐列表
    private void showPackage(List<ShopPackageListBean.DataBean> shopPackages) {
        ShopPackageAdapter shopPackageAdapter = new ShopPackageAdapter(ShopHomeBakActivity.this, shopPackages, shopPhone);
        lv_shop_package.setAdapter(shopPackageAdapter);
        setListViewHeight(lv_shop_package);
    }


    //显示案例列表
    private void showDemo(List<ShopDemoListBean.DataBean> shopDemos) {
        ShopDemoAdapter shopDemoAdapter = new ShopDemoAdapter(ShopHomeBakActivity.this, shopDemos);
        lv_shop_demo.setAdapter(shopDemoAdapter);

        setListViewHeight(lv_shop_demo);
    }

    //显示评论列表
    private void showComment(List<ShopCommentListBean.DataBean> dataBeans) {
        if (null != dataBeans && dataBeans.size() > 0) {
            ShopCommentAdapter shopCommentAdapter = new ShopCommentAdapter(ShopHomeBakActivity.this, dataBeans);
            lv_shop_comment.setAdapter(shopCommentAdapter);

            setListViewHeight(lv_shop_comment);
        }


//        onRefreshCompleted();
//        if (event.isSuccess()) {
//            Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
//            List<Map<String, Object>> reviews = (List<Map<String, Object>>) map.get("reviews");
//            hasMore(reviews != null && reviews.size() >= 10);
//            if(reviews == null || reviews.size() == 0) {
//                pageCur -= 1;
//            }
//            mAdapter.addData(reviews);
//        } else {
//            pageCur -= 1;
//        }
    }

    /**
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
     *
     * @param listView
     */
    public void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0); // 计算子项View 的宽高
            listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
//            System.out.println(i + ":" + listItem.getMeasuredHeight() + "");
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }


    @Override
    public void onPullDown() {
        super.onPullDown();
        print("onPullDown");

//        pageCur = 0;
//        mAdapter.clear();
//        pushEvent(new TopicInfoRunner(topicId));
//        pushEvent(new TopicCommentListRunner(topicId, pageCur));
    }

    private void print(String msg) {
        System.out.println("zhuwx:"+msg);
    }

    @Override
    public void onLoadMore() {
        print("onLoadMore");

//        pageCur += 1;
//        pushEvent(new TopicCommentListRunner(topicId, pageCur));
    }

}

