package com.chantyou.janemarried.ui.pay;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;

/**
 * Created by Administrator on 2017/1/10.
 */
public class DingInfoActivity extends MyBaseActivity implements View.OnClickListener {

    private RadioButton rb_msg;
    private RadioButton rb_phone;
    private TextView tv_ding_info_tishi;
    private TextView tv_server_address;
    private Button title_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_info);
        title_back= (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        rb_msg = (RadioButton) findViewById(R.id.rb_msg);
        rb_phone = (RadioButton) findViewById(R.id.rb_phone);
        rb_msg.setOnClickListener(this);
        rb_phone.setOnClickListener(this);
        rb_msg.setChecked(true);//默认选中  私信商家
        tv_ding_info_tishi = (TextView) findViewById(R.id.tv_ding_info_tishi);
        tv_server_address = (TextView) findViewById(R.id.tv_server_address);
        String text = "请在2017-1-1 16:30前完成支付，超时订单将自动关闭";
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.RED), 2, 16, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        //        tv_ding_info_tishi.setText(style);
        tv_ding_info_tishi.setText(Html.fromHtml("请在<font color=\"#ff0000\">2017-1-1 16:30</font>前完成支付，<br>超时订单将自动关闭"));
        tv_server_address.setText(SharedPreferenceUtils.getString(this, "marry_address", ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_msg://点击了私信商家
                /*
                * 直接这样写是不行的
                  tv.setTextColor(R.color.textColor_black);
                  要从resources中获取
                  tv.setTextColor(this.getResources().getColor(R.color.textColor_black));
                  颜色设置必须通过Color类来操作 不然设置无效
                * */
//                rb_msg.setTextColor(this.getResources().getColor(R.color.orange));
//                rb_phone.setTextColor(this.getResources().getColor(R.color.home_text));

                break;
            case R.id.rb_phone://点击了电话商家
//                rb_phone.setTextColor(this.getResources().getColor(R.color.orange));
//                rb_msg.setTextColor(this.getResources().getColor(R.color.home_text));
                celling(getIntent().getStringExtra("shopPhone"));
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    private void celling(final String shopPhone) {
        if (!TextUtils.isEmpty(shopPhone)) {
            LayoutInflater factory = LayoutInflater.from(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
