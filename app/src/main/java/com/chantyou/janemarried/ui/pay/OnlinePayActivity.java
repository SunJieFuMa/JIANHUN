package com.chantyou.janemarried.ui.pay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.ui.topic.TopicDetailsActivity;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by Administrator on 2017/1/9.
 */

public class OnlinePayActivity extends MyBaseActivity implements View.OnClickListener {
    private RadioButton rb_alipay;
    private ImageView iv_alipay;
    private RadioButton rb_weixinpay;
    private ImageView iv_weixinpay;
    private RelativeLayout rl_alipay;
    private RelativeLayout rl_weixinpay;
    private CountdownView mCvCountdownView;
    private Button title_back;
    private String totalPrice;
    private TextView tv_pay_price;
    private AlertDialog dialog;
    private String shopPhone;
    private Button btn_pay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pay);
        rb_alipay = (RadioButton) findViewById(R.id.rb_alipay);
        rb_alipay.setChecked(true);//默认选中支付宝支付
        rb_alipay.setOnClickListener(this);
        rb_weixinpay = (RadioButton) findViewById(R.id.rb_weixinpay);
        rb_weixinpay.setOnClickListener(this);
        iv_alipay = (ImageView) findViewById(R.id.iv_alipay);
        iv_weixinpay = (ImageView) findViewById(R.id.iv_weixinpay);
        rl_alipay = (RelativeLayout) findViewById(R.id.rl_alipay);
        rl_alipay.setOnClickListener(this);
        rl_weixinpay = (RelativeLayout) findViewById(R.id.rl_weixinpay);
        rl_weixinpay.setOnClickListener(this);
        mCvCountdownView = (CountdownView) findViewById(R.id.cv_countdownView);
        mCvCountdownView.start(995550); // 毫秒
        title_back = (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        totalPrice = getIntent().getStringExtra("totalPrice");
        shopPhone = getIntent().getStringExtra("shopPhone");//商家的电话号码
        tv_pay_price = (TextView) findViewById(R.id.tv_pay_price);
        tv_pay_price.setText(totalPrice);
        btn_pay = (Button) findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_alipay:
            case R.id.rl_alipay:
                rb_alipay.setChecked(true);
                rb_weixinpay.setChecked(false);
                iv_alipay.setImageResource(R.drawable.belong_check_down);
                iv_weixinpay.setImageResource(R.drawable.belong_check_on);
                break;
            case R.id.rb_weixinpay:
            case R.id.rl_weixinpay:
                rb_weixinpay.setChecked(true);
                rb_alipay.setChecked(false);
                iv_weixinpay.setImageResource(R.drawable.belong_check_down);
                iv_alipay.setImageResource(R.drawable.belong_check_on);
                break;
            case R.id.title_back:
                goBack();
                break;
            case R.id.btn_pay:
                //确认支付
                TopicDetailsActivity.launch(OnlinePayActivity.this,103);

                break;
        }
    }

    //弹出对话框进行判断是否退出这个页面
    private void goBack() {
        dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("确定要取消支付吗？")
                .setPositiveButton("取消支付", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialog.dismiss();
                        //                        Toast.makeText(OnlinePayActivity.this, "您可以在“我的订单”中查看您未支付的订单~", Toast.LENGTH_SHORT).show();
                        //                        shopPhone这个商家的电话号码该怎么传递呢
                        Intent intent = new Intent(OnlinePayActivity.this, MyDingActivity.class);
                        intent.putExtra("shopPhone", shopPhone);//将商家电话号码传递过去
                        intent.putExtra("totalPrice", totalPrice);//将订单的总金额传递过去
                        intent.putExtra("ding_status", "waitpay");//将订单的状态传递过去
                        //其他的应该是要从服务器获取
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("我点错了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}
