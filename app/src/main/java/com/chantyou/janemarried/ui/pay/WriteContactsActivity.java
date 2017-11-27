package com.chantyou.janemarried.ui.pay;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.chantyou.janemarried.utils.SpUtils;
import com.yby.areaselector.SelectAreaDialog;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/1/9.
 */
public class WriteContactsActivity extends MyBaseActivity implements View.OnClickListener {

    private TextView server_time;
    private TextView tv_provice_city;
    private Button title_back;
    private Button title_setting;
    private EditText edt_name;
    private EditText edt_phone;
    private EditText edt_detail_address;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private AlertDialog dialog;
    private SelectAreaDialog selectAreaDialog;
    private String provice_city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecontacts);
        server_time = (TextView) findViewById(R.id.server_time);
        server_time.setOnClickListener(this);
        tv_provice_city = (TextView) findViewById(R.id.tv_provice_city);
        tv_provice_city.setOnClickListener(this);
        title_back = (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_detail_address = (EditText) findViewById(R.id.edt_detail_address);
        title_setting = (Button) findViewById(R.id.title_setting);
        title_setting.setOnClickListener(this);
        selectAreaDialog = new SelectAreaDialog(this);
        initData();
    }

    private void initData() {
        //初始化日期
        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                WriteContactsActivity.this.server_time.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        //从本地取出之前保存过的联系人信息
        initEdittext();
    }

    private void initEdittext() {
        String marry_name = SpUtils.getString(this, "marry_name", "");
        String marry_phone = SpUtils.getString(this, "marry_phone", "");
        String marry_severtime = SpUtils.getString(this, "marry_severtime", "");
        String marry_provice = SpUtils.getString(this, "marry_provice", "");
        String marry_detailaddress = SpUtils.getString(this, "marry_detailaddress", "");
        if (!TextUtils.isEmpty(marry_name)) {
            edt_name.setText(marry_name);

        }
        if (!TextUtils.isEmpty(marry_phone)) {
            edt_phone.setText(marry_phone);

        }
        if (!TextUtils.isEmpty(marry_severtime)) {
            server_time.setText(marry_severtime);

        }
        if (!TextUtils.isEmpty(marry_provice)) {
            tv_provice_city.setText(marry_provice);

        }
        if (!TextUtils.isEmpty(marry_detailaddress)) {
            edt_detail_address.setText(marry_detailaddress);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.server_time:
                datePickerDialog.show();
                break;

            case R.id.title_back:
                goBack();
                break;

            case R.id.tv_provice_city:
                //弹出选择省市区的对话框

                selectAreaDialog.setOnConfirmListener(new SelectAreaDialog.OnConfirmListener() {
                    @Override
                    public void getData(String provice, String city, String district) {
//                        Toast.makeText(getApplicationContext(), provice + city + district, Toast.LENGTH_SHORT).show();
                        provice_city=provice + city + district;
                        tv_provice_city.setText(provice + city + district);
                    }
                });
                selectAreaDialog.show();
                break;

            case R.id.title_setting://点击了保存按钮
                if (TextUtils.isEmpty(edt_name.getText().toString().trim())) {
                    Toast.makeText(this, "请填写您的姓名~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_phone.getText().toString().trim())) {
                    Toast.makeText(this, "请填写您的手机号~", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phone = edt_phone.getText().toString().trim();
                if (phone.length() < 11 || !isMobileNO(phone)) {
                    Toast.makeText(this, "手机号格式不正确~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(server_time.getText().toString().trim())) {
                    Toast.makeText(this, "服务时间不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tv_provice_city.getText().toString().trim())) {
                    Toast.makeText(this, "省市区选择不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_detail_address.getText().toString().trim())) {
                    Toast.makeText(this, "详细地址不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                }
                //进行联系人的保存
                SpUtils.putString(this, "marry_name", edt_name.getText().toString().trim());
                SpUtils.putString(this, "marry_phone", phone);
                SpUtils.putString(this, "marry_severtime", server_time.getText().toString().trim());
                SpUtils.putString(this, "marry_address", tv_provice_city.getText().toString()
                        +edt_detail_address.getText().toString().trim());
                SpUtils.putString(this, "marry_provice", tv_provice_city.getText().toString());
                SpUtils.putString(this, "marry_detailaddress", edt_detail_address.getText().toString().trim());

                finish();
                break;
        }
    }

    //弹出对话框进行判断是否退出这个页面
    private void goBack() {
        dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("放弃已编辑的内容？")
                .setPositiveButton("放弃编辑", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        SharedPreferenceUtils.putBoolean(WriteContactsActivity.this,"quit_edit",true);
                        dialog.dismiss();
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

    //手机号判断逻辑
    public boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}
