package com.chantyou.janemarried.ui.pay;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.chantyou.janemarried.utils.SpUtils;

/**
 * Created by Administrator on 2017/1/7.
 */
public class WriteOrderActivity extends MyBaseActivity implements View.OnClickListener {
    private Button titleBack;
    private Button titleSetting;
    private TextView titleTitle;
    private ImageView ivOrder;
    private RadioButton cb_allpay;
    private RadioButton cb_dingpay;
    private ImageView iv_allpay;
    private ImageView iv_dingpay;
    private TextView tv_contacts;
    private Button btn_putOrder;
    private Dialog dialog;
    private LinearLayout ll_edit_info;
    private TextView tv_name;
    private TextView tv_server_time;
    private TextView tv_yes;
    private TextView tv_modify;
    private TextView dialog_tv_name;
    private TextView dialog_tv_phone;
    private TextView tv_server_address;
    private TextView tv_xuxianzhifu;
    private TextView tv_youhui;
    private TextView tv_change_text_youhui;
    private TextView tv_quankuan_or_dingjin;
    private TextView dialog_tv_server_time;
    private TextView tv_ding_money;
    private TextView tv_taocan_price;
    private TextView tv_youhui_price;
    private TextView tv_bencizhifu_price;
    private TextView tv_total_price;
    private TextView tv_ding_name;
    private TextView tv_ding_des;
    private TextView tv_ding_price;

    private String imageUrl;//传递过来的图片
    private String name;
    private String label;
    private String price;//传递过来的价格
    private String totalPrice;
    private String shopPhone;

    private void initViews() {
        titleBack = (Button) findViewById(R.id.title_back);
        titleBack.setOnClickListener(this);
        titleSetting = (Button) findViewById(R.id.title_setting);
        titleTitle = (TextView) findViewById(R.id.title_title);
        ivOrder = (ImageView) findViewById(R.id.iv_order);//商品图片
        cb_allpay = (RadioButton) findViewById(R.id.cb_allpay);//全款支付
        cb_allpay.setChecked(true);
        cb_allpay.setOnClickListener(this);
        iv_allpay = (ImageView) findViewById(R.id.iv_allpay);
        cb_dingpay = (RadioButton) findViewById(R.id.cb_dingpay);//定金支付
        iv_dingpay = (ImageView) findViewById(R.id.iv_dingpay);
        cb_dingpay.setOnClickListener(this);
        tv_contacts = (TextView) findViewById(R.id.tv_contacts);//点击电话号码填写联系人
        //        tv_contacts.setOnClickListener(this);加了这句之后点击这个按钮就不会执行最外层的点击事件
        btn_putOrder = (Button) findViewById(R.id.btn_putOrder);
        btn_putOrder.setOnClickListener(this);
        ll_edit_info = (LinearLayout) findViewById(R.id.ll_edit_info);
        ll_edit_info.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_server_time = (TextView) findViewById(R.id.tv_server_time);
        dialog = new Dialog(this, R.style.ShareDialog);//自己写一个style去掉dialog的头部
        dialog.setContentView(R.layout.mydialog);
        tv_yes = (TextView) dialog.findViewById(R.id.yes);
        tv_yes.setOnClickListener(this);
        tv_modify = (TextView) dialog.findViewById(R.id.modify);
        tv_modify.setOnClickListener(this);
        dialog_tv_name = (TextView) dialog.findViewById(R.id.dialog_tv_name);
        dialog_tv_phone = (TextView) dialog.findViewById(R.id.dialog_tv_phone);
        dialog_tv_server_time = (TextView) dialog.findViewById(R.id.dialog_tv_server_time);
        tv_ding_money = (TextView) findViewById(R.id.tv_ding_money);
        ivOrder = (ImageView) findViewById(R.id.iv_order);
        tv_ding_name = (TextView) findViewById(R.id.tv_ding_name);
        tv_ding_des = (TextView) findViewById(R.id.tv_ding_des);
        tv_ding_price = (TextView) findViewById(R.id.tv_ding_price);
        tv_xuxianzhifu = (TextView) findViewById(R.id.tv_xuxianzhifu);//只是单纯的文字，改变颜色用
        tv_youhui = (TextView) findViewById(R.id.tv_youhui);//只是单纯的文字，改变颜色用
        //当点击定金支付按钮的时候，就改变文字为----下次余额支付
        tv_quankuan_or_dingjin = (TextView) findViewById(R.id.tv_quankuan_or_dingjin);
        //当点击定金支付按钮的时候，就改变文字为----本次定金支付
        tv_change_text_youhui = (TextView) findViewById(R.id.tv_change_text_youhui);
        tv_taocan_price = (TextView) findViewById(R.id.tv_taocan_price);
        tv_youhui_price = (TextView) findViewById(R.id.tv_youhui_price);
        tv_bencizhifu_price = (TextView) findViewById(R.id.tv_bencizhifu_price);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        tv_server_address = (TextView) findViewById(R.id.tv_server_address);
//        System.out.println("得到的令牌：" + AppAndroid.getAccessToken());
        /*String url="http://101.201.209.200/MarryTrade/interfaces/jinyou/shop!info.action";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("token", AppAndroid.getAccessToken())
                .addParams("shopId", "132456")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("cuo了：");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("出来了：" + response);
                    }
                });*/
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writeorder);
        initViews();
        initData();
    }

    private void initData() {
        imageUrl = getIntent().getStringExtra("imageUrl");
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        label = getIntent().getStringExtra("label");
        shopPhone = getIntent().getStringExtra("shopPhone");//商家的电话号码
        Glide.with(this).load(imageUrl).into(ivOrder);
        tv_ding_price.setText("￥" + price);
        tv_taocan_price.setText("￥" + price);
        tv_ding_name.setText(name);
        tv_ding_des.setText(label);
        totalPrice = "￥" + (Integer.parseInt(price)
                //别忘了将价格中的特殊符号置换为空
                - Integer.parseInt(tv_youhui_price.getText().toString().replace("￥", "")));
        tv_bencizhifu_price.setText(totalPrice);
        tv_total_price.setText(totalPrice);
        //从本地取出之前保存过的联系人信息
        tv_name.setText(SpUtils.getString(this, "marry_name", ""));
        tv_contacts.setText(SpUtils.getString(this, "marry_phone", ""));
        tv_server_time.setText(SpUtils.getString(this, "marry_severtime", ""));
        tv_server_address.setText(SpUtils.getString(this, "marry_address", ""));
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean quit_edit = SharedPreferenceUtils.getBoolean(this, "quit_edit", false);
        if (quit_edit) {
            SharedPreferenceUtils.putBoolean(this,"quit_edit",false);//记得还原，要不然出错
            return;
        }
        //从本地取出之前保存过的联系人信息
        tv_name.setText(SpUtils.getString(this, "marry_name",""));
        tv_contacts.setText(SpUtils.getString(this, "marry_phone", ""));
        tv_server_time.setText(SpUtils.getString(this, "marry_severtime", ""));
        tv_server_address.setText(SpUtils.getString(this, "marry_address", ""));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.cb_dingpay:
                cb_dingpay.setChecked(true);
                cb_dingpay.setTextColor(Color.RED);
                cb_allpay.setTextColor(this.getResources().getColor(R.color.home_text));
                tv_youhui.setTextColor(this.getResources().getColor(R.color.home_text));
                tv_ding_money.setTextColor(Color.RED);
                tv_xuxianzhifu.setTextColor(Color.RED);
                tv_youhui_price.setTextColor(Color.RED);
                cb_allpay.setChecked(false);
                iv_allpay.setVisibility(View.GONE);
                iv_dingpay.setVisibility(View.VISIBLE);
                tv_quankuan_or_dingjin.setText("下次余额支付：");
                tv_change_text_youhui.setText("本次定金支付：");
                tv_total_price.setText(tv_youhui_price.getText().toString());
                break;
            case R.id.cb_allpay:
                cb_allpay.setChecked(true);
                cb_allpay.setTextColor(Color.RED);
                tv_youhui.setTextColor(Color.RED);
                cb_dingpay.setTextColor(this.getResources().getColor(R.color.home_text));
                tv_xuxianzhifu.setTextColor(this.getResources().getColor(R.color.home_text));
                tv_ding_money.setTextColor(this.getResources().getColor(R.color.home_text));
                cb_dingpay.setChecked(false);
                iv_allpay.setVisibility(View.VISIBLE);
                iv_dingpay.setVisibility(View.GONE);
                tv_quankuan_or_dingjin.setText("本次全款支付：");
                tv_change_text_youhui.setText("优惠：");
                tv_bencizhifu_price.setText(totalPrice);
                tv_total_price.setText(totalPrice);
                break;
            case R.id.ll_edit_info:
                //进入填写联系人界面
                Intent intent = new Intent(this, WriteContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_putOrder:
                //点击提交订单的时候
                if (TextUtils.isEmpty(tv_name.getText().toString().trim())) {
                    Toast.makeText(this, "请填写您的姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog_tv_name.setText(tv_name.getText().toString());

                if (TextUtils.isEmpty(tv_contacts.getText().toString().trim())) {
                    Toast.makeText(this, "请填写您的电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog_tv_phone.setText(tv_contacts.getText().toString().trim());

                if (TextUtils.isEmpty(tv_server_time.getText().toString().trim())) {
                    Toast.makeText(this, "服务时间不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog_tv_server_time.setText(tv_server_time.getText().toString());
                dialog.show();
                break;
            case R.id.yes:
                Intent intent1 = new Intent(this, OnlinePayActivity.class);
                intent1.putExtra("totalPrice", totalPrice);
                intent1.putExtra("shopPhone", shopPhone);//将商家的电话号码也传递过去
                startActivity(intent1);
                dialog.dismiss();
                finish();//关闭自己
                break;
            case R.id.modify:
                Intent intent2 = new Intent(this, WriteContactsActivity.class);
                startActivity(intent2);
                dialog.dismiss();
                break;
        }
    }

}
