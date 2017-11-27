package com.chantyou.janemarried.ui.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.CheckVersion.CallBackInterface;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.shop.ShopCommentAdapter;
import com.chantyou.janemarried.adapter.shop.ShopDemoAdapter;
import com.chantyou.janemarried.adapter.shop.ShopPackageAdapter;
import com.chantyou.janemarried.adapter.sticky.TravelingAdapter;
import com.chantyou.janemarried.data.ShopInfoDetailKey;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.AppointmentRunner;
import com.chantyou.janemarried.httprunner.shop.ShopCommentAddRunner;
import com.chantyou.janemarried.httprunner.shop.ShopCommentListRunner;
import com.chantyou.janemarried.httprunner.shop.ShopDemoListRunner;
import com.chantyou.janemarried.httprunner.shop.ShopDisFollowRunner;
import com.chantyou.janemarried.httprunner.shop.ShopFollowRunner;
import com.chantyou.janemarried.httprunner.shop.ShopInfoRunner;
import com.chantyou.janemarried.httprunner.shop.ShopPackageListRunner;
import com.chantyou.janemarried.model.Shop.ShopCommentListBean;
import com.chantyou.janemarried.model.Shop.ShopDemoListBean;
import com.chantyou.janemarried.model.Shop.ShopHomeChannelEntity;
import com.chantyou.janemarried.model.Shop.ShopInfoBean;
import com.chantyou.janemarried.model.Shop.ShopPackageListBean;
import com.chantyou.janemarried.model.TravelingEntity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.chantyou.janemarried.utils.MyDialog;
import com.chantyou.janemarried.utils.SysCommon;
import com.chantyou.janemarried.utils.sticky.DensityUtil;
import com.chantyou.janemarried.utils.sticky.ModelUtil;
import com.chantyou.janemarried.view.HeaderDividerViewView;
import com.chantyou.janemarried.view.MyVideoView;
import com.chantyou.janemarried.view.SmoothListView.SmoothListView;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.lib.mark.ui.BaseActivity;
import com.mhh.lib.ToastManager;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.view.CircleImageView3;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class ShopHomeActivity extends BaseActivity implements SmoothListView.ISmoothListViewListener {

    private SmoothListView smoothListView;//主容器
    private TextView title_back;//返回

    private View fl_shop_home;//频道-主页
    private TextView tv_follow_add;//关注
    private CircleImageView3 iv_shop_image;//头像

    private LinearLayout ll_connect_us;//联系我们
    private TextView tv_appointment;//联系我们

    private ImageView iv_shop_bg;//背景图

    private TextView tv_tip_company;//商家类型
    private TextView tv_desc;//简介
    private TextView tv_address;//地址


    //评论
    private View vComment;
    private ImageView ivBack;
    private TextView btnComment;
    private EditText et;

    private TextView tv_shop_name;
    private TextView tv_range;
    private TextView tv_follow;
    private ImageView iv_follow;
    private TextView tv_collect;
    private ImageView iv_bz;

    private RadioButton tv_shop_home;
    private RadioButton tv_shop_demo;
    private RadioButton tv_shop_package;
    private RadioButton tv_shop_rate;
    LinearLayout ll;

    private Context mContext;
    private Activity mActivity;
    private int mScreenHeight; // 屏幕高度

    private List<String> adList = new ArrayList<>(); // 广告数据
    private List<ShopHomeChannelEntity> channelList = new ArrayList<>(); // 频道数据

    private List<TravelingEntity> travelingList = new ArrayList<>(); // ListView数据

    private HeaderDividerViewView headerDividerViewView; // 分割线占位图

    private TravelingAdapter mAdapter; // 主页数据

    private View itemHeaderAdView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isScrollIdle = true; // ListView是否在滑动


    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 4; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    private String shopId;
    private int shopLevel;//一般、认证、优质

    private int shopHomeType = ShopInfoDetailKey.home;//店铺目前显示的类型  套餐、案例、评价

    private int pageDemoNum = 1;
    private int pagePacketNum = 1;
    private int pageCommentNum = 1;

    private boolean isFollowed = false;//是否已收藏
    private String shopPhone;//店铺电话

    //套餐
    private ShopPackageAdapter shopPackageAdapter;
    private List<ShopPackageListBean.DataBean> shopPackagesList = new ArrayList<ShopPackageListBean.DataBean>();

    //案例
    private ShopDemoAdapter shopDemoAdapter;
    private List<ShopDemoListBean.DataBean> shopDemosList = new ArrayList<ShopDemoListBean.DataBean>();

    //评论
    private ShopCommentAdapter shopCommentAdapter;
    private List<ShopCommentListBean.DataBean> shopCommentsList = new ArrayList<ShopCommentListBean.DataBean>();
    private String videoUrl;
    private ViewHolder holder;
    private TextView tv_do;
    private EditText et_name;
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home2);

        ll = (LinearLayout) findViewById(R.id.ll);
        smoothListView = (SmoothListView) findViewById(R.id.listView);
        title_back = (TextView) findViewById(R.id.title_back);//返回
        tv_collect = (TextView) findViewById(R.id.tv_collect);//收藏
        tv_follow_add = (TextView) findViewById(R.id.tv_follow_add);//加关注
        ll_connect_us = (LinearLayout) findViewById(R.id.ll_connect_us);//联系我们
        tv_appointment = (TextView) findViewById(R.id.tv_appointment);//联系我们

        vComment = (View) findViewById(R.id.vComment);//
        ivBack = (ImageView) findViewById(R.id.ivBack);//
        btnComment = (TextView) findViewById(R.id.btnComment);//
        et = (EditText) findViewById(R.id.et);//

        initSomething();


        shopId = getIntent().getExtras().get("id").toString();
        shopLevel = getIntent().getExtras().getInt("shopLevel");//商铺等级
        if (shopLevel == 2 || shopLevel == 3) {
            tv_appointment.setText("预约到店");
        } else {
            tv_appointment.setText("联系我们");
        }
        initData();
        initView();
        initListener();
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute baseToolbarAttribute) {

    }

    private void initData() {
        mContext = this;
        mActivity = this;
        mScreenHeight = DensityUtil.getWindowHeight(this);

        // 广告数据
        adList = ModelUtil.getAdData();

        // 频道数据
        channelList.add(new ShopHomeChannelEntity(1, "主页", R.drawable.icon_zhuye_selected, true));
        channelList.add(new ShopHomeChannelEntity(2, "套餐", R.drawable.icon_taocan_normal, false));
        channelList.add(new ShopHomeChannelEntity(3, "案例", R.drawable.icon_anli_normal, false));
        channelList.add(new ShopHomeChannelEntity(4, "评论", R.drawable.icon_pingjia, false));

        // ListView数据
        travelingList = new ArrayList<>();
    }

    private AlertDialog dialog;

    ////////我改的一个
    public void show1(String title, boolean showKeyBoard, final CallBackInterface callBackInterface) {
        LayoutInflater factory = LayoutInflater.from(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        View convertView = factory.inflate(R.layout.pop_window2, null);
        holder = new ViewHolder();
        holder.title = (TextView) convertView.findViewById(R.id.title);
        tv_do = (TextView) convertView.findViewById(R.id.tv_do);
        et_name = (EditText) convertView.findViewById(R.id.et_name);
        et_phone = (EditText) convertView.findViewById(R.id.et_phone);

        holder.tv_close = (TextView) convertView.findViewById(R.id.tv_close);
        holder.title.setText(title);
        dialog = builder.create();
        dialog.show();

        if (showKeyBoard) {
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        Window window = dialog.getWindow();
        window.setContentView(convertView);
        dialog.setCanceledOnTouchOutside(true);
        //透明
        window.setBackgroundDrawable(new ColorDrawable());

        //////////////////////////////////////////////////
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (AppAndroid.getScreenWidth() * 0.8); // 宽度
        //        lp.height = (int) (AppAndroid.getScreenHeight()*0.6); // 高度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        window.setAttributes(lp);

        ////////////////////////////////////////////////////

        holder.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.doSome();
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                callBackInterface.doSome();
            }
        });

        //        /////////////////////
        tv_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_name.getText().toString()) || TextUtils.isEmpty(et_phone.getText().toString())) {
                    ToastManager.getInstance(AppAndroid.getContext()).show("请先输入您的姓名和手机号");
                    return;
                }
                if (et_phone.getText().length() != 11) {
                    ToastManager.getInstance(AppAndroid.getContext()).show("请输入正确的手机号");
                    return;
                }
                pushEventEx(false, "",
                        new AppointmentRunner(shopId + "", et_name.getText().toString(), et_phone.getText().toString()),
                        ShopHomeActivity.this);
//                pushEvent(new AppointmentRunner(shopId + "", et_name.getText().toString(), et_phone.getText().toString()));
            }
        });
    }

    static class ViewHolder {
        private TextView title;
        private TextView txt_content;
        private TextView tv_close;
        private LinearLayout ll_btn;
        private TextView tv_cancel;
        private TextView tv_sure;
    }

    private void initSomething() {
        //收藏
        tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowed) {//已关注，取消关注
                    //boolean bShowProgress, String progressMsg, AsynEvent ev, OnEventListener listener
                    pushEventEx(false, "", new ShopDisFollowRunner(shopId), ShopHomeActivity.this);
                } else {//未关注，加关注
                    pushEventEx(false, "", new ShopFollowRunner(shopId), ShopHomeActivity.this);
                }
            }
        });


        //联系我们
        tv_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //V1
                //暂时不开启
//                                AppointmentActivity.launch(ShopHomeActivity.this, Integer.parseInt(shopId));
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
                    if (shopLevel == 2 || shopLevel == 3) {
                        show1("请填写预约信息", true, new CallBackInterface() {
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

                        /*
                        自己写一个style去掉dialog的头部，不知道怎么回事，
                        加上这个style之后dialog的显示就会出问题，所以先不加了
                        加上的话得把布局文件的宽高设置为固定值才行，真是邪了门了
                         */
                        /*final Dialog builder = new Dialog(ShopHomeActivity.this,R.style.NoTitleDialog);
                        builder.setContentView(R.layout.dialog_shop_appointment);
//                        builder.setTitle(null);
                        TextView tv_do = (TextView) builder.findViewById(R.id.tv_do);
                        TextView tv_cancel = (TextView) builder.findViewById(R.id.tv_cancel);
                        final EditText et_name = (EditText) builder.findViewById(R.id.et_name);
                        final EditText et_phone = (EditText) builder.findViewById(R.id.et_phone);
                        tv_do.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(et_name.getText().toString()) || TextUtils.isEmpty(et_phone.getText().toString())) {
                                    ToastManager.getInstance(ShopHomeActivity.this).show("请先输入您的姓名和手机号");
                                    return;
                                }
                                if (et_phone.getText().length() != 11) {
                                    ToastManager.getInstance(ShopHomeActivity.this).show("请输入正确的手机号");
                                    return;
                                }
                                builder.dismiss();
                                pushEvent(new AppointmentRunner(shopId + "", et_name.getText().toString(), et_phone.getText().toString()));
                            }
                        });
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder.dismiss();
                            }
                        });
                        builder.show();*/

                    } else {
                        LayoutInflater factory = LayoutInflater.from(ShopHomeActivity.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShopHomeActivity.this);
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

            }


        });


        //评论返回
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vComment.setVisibility(View.GONE);
            }
        });

        //发表评论
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = et.getText().toString().trim();
                if (TextUtils.isEmpty(txt)) {
                    ToastManager.getInstance(ShopHomeActivity.this).show("请输入评论");
                } else {
                    pushEventEx(false, "", new ShopCommentAddRunner(shopId, txt), ShopHomeActivity.this);
                }

            }
        });

    }

    private void initView() {
        //初始化头部
        initShopHomeTop();
        //初始化频道
        initChannel();

        //初始化主页
        initHome();


        // 设置分割线
        headerDividerViewView = new HeaderDividerViewView(this);
        headerDividerViewView.fillView("", smoothListView);

        // 设置ListView数据
        mAdapter = new TravelingAdapter(this, travelingList);
        smoothListView.setAdapter(mAdapter);

        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;
    }

    private FrameLayout video_layout;

    //初始化频道-主页
    private void initHome() {
        View headerView = getLayoutInflater().inflate(R.layout.shop_home_home, null);
        fl_shop_home = (View) headerView.findViewById(R.id.fl_shop_home);

        //        rl_vv = (RelativeLayout) headerView.findViewById(R.id.rl_vv);//
        video_layout = (FrameLayout) headerView.findViewById(R.id.video_layout);//
        //        vv_shop_video = (MyVideoView) headerView.findViewById(R.id.vv_shop_video);//
        //        iv_shop_video = (ImageView) headerView.findViewById(R.id.iv_shop_video);//

        tv_desc = (TextView) headerView.findViewById(R.id.tv_desc);//简介
        tv_address = (TextView) headerView.findViewById(R.id.tv_address);//地址
        jcVideoPlayerStandard = (JCVideoPlayerStandard) headerView.findViewById(R.id.videoplayer);

        // 添加头部view：必须放在adapter前面不然会报错
        smoothListView.addHeaderView(headerView);

        if (!TextUtils.isEmpty(shopId)) {
            pushEventEx(false, "", new ShopInfoRunner(shopId), this);
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
//        tv_shop_rate.setText("评价(" + "0" + ")");
        System.out.println("评价：：：：："+shopInfo.getJudgeCount());
        tv_shop_rate.setText("评价(" + shopInfo.getJudgeCount() + ")");
        tv_follow.setText("粉丝" + shopInfo.getCollectCount());


        String shopp = shopInfo.getDescs();
        if (!TextUtils.isEmpty(shopp)) {
            tv_desc.setText(Html.fromHtml(shopInfo.getDescs()));//店铺信息
        }

        tv_address.setText(shopInfo.getAddress());//店铺地址
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShopHomeActivity.this,PoiAroundSearchActivity.class);
                intent.putExtra("address",shopInfo.getAddress());
                startActivity(intent);
            }
        });

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


        videoUrl = shopInfo.getVideoUrl();
        initVideo();

    }

    private View headerView_top;

    //初始化头部
    @SuppressLint("NewApi")
    private void initShopHomeTop() {
        // 头部view：设置view的点击时间，用于屏蔽listview的item的点击事件
        headerView_top = getLayoutInflater().inflate(R.layout.shop_home_header_top, null);
        tv_follow_add = (TextView) headerView_top.findViewById(R.id.tv_follow_add);
        iv_shop_image = (CircleImageView3) headerView_top.findViewById(R.id.iv_shop_image);
        iv_shop_bg = (ImageView) headerView_top.findViewById(R.id.iv_shop_bg);//背景图
        tv_tip_company = (TextView) headerView_top.findViewById(R.id.tv_tip_company);//商家类型

        tv_shop_name = (TextView) headerView_top.findViewById(R.id.tv_shop_name);
        tv_range = (TextView) headerView_top.findViewById(R.id.tv_range);
        tv_follow = (TextView) headerView_top.findViewById(R.id.tv_follow);
        iv_bz = (ImageView) headerView_top.findViewById(R.id.iv_bz);


        //加关注
        tv_follow_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowed) {//已关注，取消关注
                    pushEventEx(false, "", new ShopDisFollowRunner(shopId), ShopHomeActivity.this);
                } else {//未关注，加关注
                    pushEventEx(false, "", new ShopFollowRunner(shopId), ShopHomeActivity.this);
                }
            }
        });

        // 添加头部view：必须放在adapter前面不然会报错
        smoothListView.addHeaderView(headerView_top);


        if (shopLevel > 0) {
            switch (shopLevel) {
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
                    tv_tip_company.setBackground(getResources().getDrawable(R.drawable.btn_tips_blue));
                }
                break;
                case 3: {//认证商家
                    //                        tv_tip_company_great.setVisibility(View.VISIBLE);
                    tv_tip_company.setText("认证");
                    tv_tip_company.setTextColor(getResources().getColor(R.color.white));
                    tv_tip_company.setBackground(getResources().getDrawable(R.drawable.btn_tips_yellow));
                }
                break;
            }

        }

    }

    private View headerView_Channel;

    //初始化频道
    private void initChannel() {
        // 头部view：设置view的点击时间，用于屏蔽listview的item的点击事件
        headerView_Channel = getLayoutInflater().inflate(R.layout.shop_home_header_channel, null);
        tv_shop_home = (RadioButton) headerView_Channel.findViewById(R.id.tv_shop_home);
        tv_shop_demo = (RadioButton) headerView_Channel.findViewById(R.id.tv_shop_demo);
        tv_shop_package = (RadioButton) headerView_Channel.findViewById(R.id.tv_shop_package);
        tv_shop_rate = (RadioButton) headerView_Channel.findViewById(R.id.tv_shop_rate);

        tv_shop_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//首页
                // 设置ListView数据
                shopHomeType = ShopInfoDetailKey.home;
                smoothListView.setAdapter(mAdapter);
                fl_shop_home.setVisibility(View.VISIBLE);
                vComment.setVisibility(View.GONE);
                ll_connect_us.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        tv_shop_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//套餐
                shopHomeType = ShopInfoDetailKey.packet;
                smoothListView.setVisibility(View.VISIBLE);
                fl_shop_home.setVisibility(View.GONE);
                vComment.setVisibility(View.GONE);
                ll_connect_us.setVisibility(View.VISIBLE);
                shopPackageAdapter = null;
                initPackage();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        tv_shop_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//案例
                shopHomeType = ShopInfoDetailKey.demo;
                smoothListView.setVisibility(View.VISIBLE);
                fl_shop_home.setVisibility(View.GONE);
                vComment.setVisibility(View.GONE);
                ll_connect_us.setVisibility(View.VISIBLE);
                shopDemoAdapter = null;
                initDemo();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
            }


        });
        tv_shop_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//评价
                shopHomeType = ShopInfoDetailKey.rate;
                smoothListView.setVisibility(View.VISIBLE);
                fl_shop_home.setVisibility(View.GONE);
                vComment.setVisibility(View.VISIBLE);
                ll_connect_us.setVisibility(View.GONE);
                shopCommentAdapter = null;
                initComment();
            }
        });

        // 添加头部view：必须放在adapter前面不然会报错
        smoothListView.addHeaderView(headerView_Channel);
    }

    private void initListener() {
        //返回
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        smoothListView.setRefreshEnable(true);
        smoothListView.setLoadMoreEnable(true);
        smoothListView.setSmoothListViewListener(this);
        smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && adViewTopSpace < 0)
                    return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = smoothListView.getChildAt(1 - firstVisibleItem);
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(mContext, itemHeaderAdView.getHeight());
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderFilterView.getTop());
                }


            }
        });
    }

    // 填充数据
    private void fillAdapter(List<TravelingEntity> list) {
        if (list == null || list.size() == 0) {
            smoothListView.setLoadMoreEnable(false);
            int height = mScreenHeight - DensityUtil.dip2px(mContext, 95); // 95 = 标题栏高度 ＋ FilterView的高度
            mAdapter.setData(ModelUtil.getNoDataEntity(height));
        } else {
            smoothListView.setLoadMoreEnable(list.size() > TravelingAdapter.ONE_REQUEST_COUNT);
            mAdapter.setData(list);
        }
    }

    //______________________________

    private MyVideoView vv_shop_video;
    private ImageView iv_shop_video;
    private RelativeLayout rl_vv;
    private boolean videoIsError = false;

    @SuppressLint("NewApi")
    private void initVideo(String videoUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            return;
        }

        if (SysCommon.isNotWifi(ShopHomeActivity.this)) {
            ToastManager.getInstance(ShopHomeActivity.this).show("由于视频文件较大，请切换至无线网络后重新打开");
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

        vv_shop_video.setVideoURI(mVideoUri);
        vv_shop_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                videoIsError = true;
                return true;
            }
        });

        rl_vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoIsError) {
                    ToastManager.getInstance(ShopHomeActivity.this).show("该视频无法播放");
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

    //~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        if (listViewAdHeaderView != null) {
        //            listViewAdHeaderView.stopADRotate();
        //        }
    }

    //    @Override
    //    public void onBackPressed() {
    //        super.onBackPressed();
    //
    //    }

    @Override
    public void onRefresh() {
        //        smoothListView.stopRefresh();
        //        smoothListView.setRefreshTime("刚刚");
        switch (shopHomeType) {
            case ShopInfoDetailKey.packet: {//套餐
                pagePacketNum = 1;
                initPackage();
            }
            break;
            case ShopInfoDetailKey.demo: {//案例
                pageDemoNum = 1;
                initDemo();
            }
            break;
            case ShopInfoDetailKey.rate: {//评论
                pageCommentNum = 1;
                initComment();
            }
            break;
            default:
                smoothListView.stopRefresh();
                smoothListView.setRefreshTime("刚刚");
                break;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothListView.stopRefresh();
                smoothListView.setRefreshTime("刚刚");
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        //        smoothListView.stopLoadMore();
        switch (shopHomeType) {
            case ShopInfoDetailKey.packet: {//套餐
                pagePacketNum++;
                initPackage();
            }
            break;
            case ShopInfoDetailKey.demo: {//案例
                pageDemoNum++;
                initDemo();
            }
            break;
            case ShopInfoDetailKey.rate: {//评论
                pageCommentNum++;
                initComment();
            }
            break;
            default:
                smoothListView.stopLoadMore();
                break;
        }

        //        mHandler.postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //                smoothListView.stopLoadMore();
        //            }
        //        }, 2000);
    }

    //==========================================================
    //后台返回数据
    @Override
    public void onEventRunEnd(Event event) {
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SHOP_INFO://店铺详情
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopInfoBean shopListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopInfoBean.class);
//                    System.out.println("sssssssssssssss:" + shopListBean.getData().get(0).toString());
                    showShopInfo(shopListBean.getData().get(0));
                }
                break;
            case XEventCode.HTTP_SHOP_PACKAGE://套餐
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopPackageListBean shopPackageListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopPackageListBean.class);
                    showPackage(shopPackageListBean.getData());
                    smoothListView.stopLoadMore();
                }
                break;
            case XEventCode.HTTP_SHOP_FOLLOW://加收藏
                if (event.isSuccess()) {
                    ToastManager.getInstance(ShopHomeActivity.this).show(event.getMessage("收藏成功"));
                    //根本就不会弹出收藏成功，而是会弹出店铺已经收藏过了，而且弹出的文字都是从服务器端获取的
                    isFollowed = true;
                    tv_collect.setText("已收藏");
                    tv_follow_add.setText("√已关注");
                } else {
                    System.out.println("zhuwx:" + event.getMessage("失败"));
                    ToastManager.getInstance(ShopHomeActivity.this).show(event.getMessage("收藏失败，请重试"));
                }
                break;
            case XEventCode.HTTP_SHOP_DIS_FOLLOW://取消收藏
                if (event.isSuccess()) {
                    ToastManager.getInstance(ShopHomeActivity.this).show(event.getMessage("取消收藏成功"));
                    isFollowed = false;
                    tv_collect.setText("收藏");
                    tv_follow_add.setText("+关注");
                } else {
                    System.out.println("zhuwx:" + event.getMessage("失败"));
                    ToastManager.getInstance(ShopHomeActivity.this).show(event.getMessage("取消收藏失败，请重试"));
                }
                break;
            case XEventCode.HTTP_SHOP_DEMO://案例
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopDemoListBean shopPackageListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopDemoListBean.class);
                    showDemo(shopPackageListBean.getData());
                    smoothListView.stopLoadMore();
                }
                break;
            case XEventCode.HTTP_SHOP_COMMENT_ADD://添加评论成功
                if (event.isSuccess()) {
                    ToastManager.getInstance(ShopHomeActivity.this).show(event.getMessage("评论成功"));

                    initComment();//初始化评论

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0); //强制隐藏键盘
                } else {
                    ToastManager.getInstance(ShopHomeActivity.this).show(event.getMessage("评论失败"));
                }
                break;
            case XEventCode.HTTP_SHOP_COMMENT_LIST://获取评论列表
                if (event.isSuccess()) {
                    System.out.println("zhuwx" + event.getParamsAtIndex(0));
                    Gson gson = new Gson();
                    ShopCommentListBean listBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopCommentListBean.class);
                    showComment(listBean.getData());
                    smoothListView.stopLoadMore();
                } else {

                }
                break;
            case XEventCode.HTTP_APPOINTMENT://预约到店
                if (event.isSuccess()) {
                    //                    CustomToast.showRightToast(this, "预约成功");
                    if (null!=dialog && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    MyDialog dialog = new MyDialog(ShopHomeActivity.this);
                    dialog.show("预约成功", "请耐心等待，稍后该商家将与您联系");
                }else{
                    CustomToast.showWorningToast(this, event.getMessage("操作失败,请检查网络或重试"));
                }
                break;
        }
    }

    //---------------------------------------------------------
    //获取套餐
    private void initPackage() {
        if (!TextUtils.isEmpty(shopId)) {
            System.out.println("zhuwx:id:" + shopId + ",page:" + pagePacketNum);
            pushEventEx(false, "", new ShopPackageListRunner(shopId, pagePacketNum + ""), this);
        }
    }

    //显示套餐列表
    private void showPackage(List<ShopPackageListBean.DataBean> shopPackages) {

        /*if (null == shopPackages || shopPackages.size() <= 0) {
            if (shopPackagesList==null && shopPackagesList.size()<=0){
                smoothListView.setAdapter(null);//我加了这个判断，不然当数据为空的时候listView还是显示之前的视图
            }
            return;
        }*/
        if (null == shopPackagesList) {
            shopPackagesList = new ArrayList<ShopPackageListBean.DataBean>();
        } else {
            if (pagePacketNum <= 1) {
                if (shopPackagesList.size() > 0) {
                    shopPackagesList.clear();
                }
            }
        }
        shopPackagesList.addAll(shopPackages);
        if (null == shopPackageAdapter) {//我在参数里加了一个shopPhone,传递商家电话过去
            shopPackageAdapter = new ShopPackageAdapter(ShopHomeActivity.this, shopPackagesList, shopPhone);
            smoothListView.setAdapter(shopPackageAdapter);
        } else {
            shopPackageAdapter.notifyDataSetChanged();
        }

        //        setListViewHeight(lv_shop_package);
    }


    //获取案例
    private void initDemo() {
        if (!TextUtils.isEmpty(shopId)) {
            System.out.println("zhuwx:id:" + shopId + ",page:" + pageDemoNum);
            pushEventEx(false, "", new ShopDemoListRunner(shopId, pageDemoNum + ""), this);
        }
    }

    //显示案例列表
    private void showDemo(List<ShopDemoListBean.DataBean> shopDemos) {

        /*if (null == shopDemos || shopDemos.size() <= 0) {
            if (shopDemosList==null && shopDemosList.size()<=0){
                smoothListView.setAdapter(null);//我加了这个判断，不然当数据为空的时候listView还是显示之前的视图
            }
            return;
        }*/
        if (null == shopDemosList) {
            shopDemosList = new ArrayList<ShopDemoListBean.DataBean>();
        } else {
            if (pageDemoNum <= 1) {
                if (shopDemosList.size() > 0) {
                    shopDemosList.clear();
                }
            }
        }
        shopDemosList.addAll(shopDemos);
        if (null == shopDemoAdapter) {
            shopDemoAdapter = new ShopDemoAdapter(ShopHomeActivity.this, shopDemosList);
            smoothListView.setAdapter(shopDemoAdapter);
        } else {
            shopDemoAdapter.notifyDataSetChanged();
        }
    }


    //获取评论
    private void initComment() {
        if (!TextUtils.isEmpty(shopId)) {
            System.out.println("zhuwx:id:" + shopId + ",page:" + pageCommentNum);
            pushEventEx(false, "", new ShopCommentListRunner(shopId, pageCommentNum + "", 20 + ""), this);
        }
    }

    //显示评论列表
    private void showComment(List<ShopCommentListBean.DataBean> dataBeans) {

        /*if (null == dataBeans || dataBeans.size() <= 0) {
            if (shopCommentsList==null && shopCommentsList.size()<=0){
                smoothListView.setAdapter(null);//我加了这个判断，不然当数据为空的时候listView还是显示之前的视图
            }
            return;
        }*/
        if (null == shopCommentsList) {
            shopCommentsList = new ArrayList<ShopCommentListBean.DataBean>();
        } else {
            if (pageCommentNum <= 1) {
                if (shopCommentsList.size() > 0) {
                    shopCommentsList.clear();
                }
            }
        }
        shopCommentsList.addAll(dataBeans);
        if (null == shopCommentAdapter) {
            shopCommentAdapter = new ShopCommentAdapter(ShopHomeActivity.this, shopCommentsList);
            smoothListView.setAdapter(shopCommentAdapter);
        } else {
            shopCommentAdapter.notifyDataSetChanged();
        }

    }

    private JCVideoPlayerStandard jcVideoPlayerStandard;

    public void initVideo() {
        if (TextUtils.isEmpty(videoUrl)) {
            return;
        }
        video_layout.setVisibility(View.VISIBLE);

        if (!videoUrl.matches("[^\\s]+(\\.(?i)(mp4|rmvb|flv|mpeg|3gp|avi))$")) {
            System.out.println("zhuwx：不是视频" + videoUrl);
            return;
        }

        if (SysCommon.isNotWifi(ShopHomeActivity.this)) {
            ToastManager.getInstance(ShopHomeActivity.this).show("由于视频文件较大，请切换至无线网络后重新打开");
            return;
        }

        //        String url = "http://vfx.mtime.cn/Video/2016/08/09/mp4/160809150901758001.mp4";
        //        String url = "http://jh-video.oss-cn-shanghai.aliyuncs.com/video/摄影师顺子婚礼.mp4";
        //        String url = "http://jh-video.oss-cn-shanghai.aliyuncs.com/video/%E6%91%84%E5%BD%B1%E5%B8%88%E9%A1%BA%E5%AD%90%E5%A9%9A%E7%A4%BC.mp4";
        jcVideoPlayerStandard.setUp(videoUrl
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频");
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
