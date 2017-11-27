package com.chantyou.janemarried.ui.qingjian;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.bean.AllCard;
import com.chantyou.janemarried.bean.CardSaveBean;
import com.chantyou.janemarried.bean.MyCard;
import com.chantyou.janemarried.bean.UserUseCard;
import com.chantyou.janemarried.config.UrlConfig;
import com.google.gson.Gson;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.helper.AbsDateTimerPickerHelper;
import com.mhh.lib.widget.XDatePickerDialog;
import com.mhh.lib.widget.XTimePickerDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/8.
 */
public class UseCardActivity extends MyBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.etName)
    private EditText etName;
    @ViewInject(R.id.etName2)
    private EditText etName2;
    @ViewInject(R.id.tvTime)
    private TextView tvTime;
    @ViewInject(R.id.etHotal)
    private EditText etHotal;
    @ViewInject(R.id.tvAddress)
    private EditText tvAddress;
    private AbsDateTimerPickerHelper dateTimerPickerHelper;
    Calendar tCalendar;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    private Button title_back;
    private Button title_setting;
    private TextView tvMusic;
    private long userTempleteId;
    private AllCard.DataEntity dataEntity;
    private int musicId;
    private String musicName;
    private MyCard.DataEntity data;
    private int templateId;
    private String str_userTempleteId;
    private String man_name;
    private String woman_name;
    private String marry_time;
    private String marry_address;
    private String marry_music;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        AppAndroid.addActivity(this);
        setContentView(R.layout.activity_usecard);
        initData();
        title_back = (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        title_setting = (Button) findViewById(R.id.title_setting);
        title_setting.setOnClickListener(this);
        tvMusic = (TextView) findViewById(R.id.tvMusic);
        tvMusic.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppAndroid.removeActivity(this);
    }

    private void initData() {
        //        userTempleteId = getIntent().getIntExtra("userTempleteId", 0);
        //        userTempleteId = Long.parseLong(getIntent().getStringExtra("userTempleteId"));
        //        Toast.makeText(this, "userTempleteId22:: "+userTempleteId, Toast.LENGTH_SHORT).show();

        templateId = getIntent().getIntExtra("templateId", 0);
        dataEntity = (AllCard.DataEntity) getIntent().getSerializableExtra("dataEntity");
    }


    @Override
    protected void init() {
        super.init();
        dateTimerPickerHelper = new AbsDateTimerPickerHelper();

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                tCalendar = Calendar.getInstance();
                dateTimerPickerHelper.showDatePicker(UseCardActivity.this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, new XDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        tCalendar.set(Calendar.YEAR, year);
                        tCalendar.set(Calendar.MONTH, monthOfYear);
                        tCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Calendar cal = Calendar.getInstance();
                        dateTimerPickerHelper.showTimePicker(UseCardActivity.this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), new XTimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                tCalendar.set(Calendar.MINUTE, minute);
                                tvTime.setText(format.format(new Date(tCalendar.getTimeInMillis())));
                            }
                        });
                    }
                });
            }
        });
    }

    /*private void setData(Map<String, Object> map) {
        if (map != null) {
            etName.setText(JsonUtil.getItemString(map, "newMan"));
            etName2.setText(JsonUtil.getItemString(map, "newWoman"));
            tvTime.setText(JsonUtil.getItemString(map, "marryDay") + " " + JsonUtil.getItemString(map, "marryTime"));
            etHotal.setText(JsonUtil.getItemString(map, "hotelName"));
            tvAddress.setText(JsonUtil.getItemString(map, "hotelAddress"));
        }
    }*/

    private void doSave() {
        man_name = etName.getText().toString();
        if (TextUtils.isEmpty(man_name)) {
            CustomToast.showWorningToast(this, "请输入新郎姓名");
            return;
        }
        woman_name = etName2.getText().toString();
        if (TextUtils.isEmpty(woman_name)) {
            CustomToast.showWorningToast(this, "请输入新娘姓名");
            return;
        }
        marry_time = tvTime.getText().toString();
        if (TextUtils.isEmpty(marry_time)) {
            CustomToast.showWorningToast(this, "请选择婚宴时间");
            return;
        }
        //        String s4 = etHotal.getText().toString();
        //        if (TextUtils.isEmpty(s4)) {
        //            CustomToast.showWorningToast(this, "请输入酒店名称");
        //            return;
        //        }
        marry_address = tvAddress.getText().toString();
        if (TextUtils.isEmpty(marry_address)) {
            CustomToast.showWorningToast(this, "请选择酒店地址");
            return;
        }
        marry_music = tvMusic.getText().toString();
        //        if (TextUtils.isEmpty(s6)) {
        //            CustomToast.showWorningToast(this, "请选择音乐");
        //            return;
        //        }

        //        showProgressDialog(null, "正在保存...");
        //点击了保存之后才算是用户真正使用了这个请柬模板
        String url1 = UrlConfig.QingjianHost+"/qingjian/user/template/use";
//        System.out.println("用户id为：：" + AppAndroid.getUid());
        OkHttpUtils
                .post()
                .url(url1)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("templateId", templateId + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(UseCardActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserUseCard userUseCard = new Gson().fromJson(response, UserUseCard.class);
                        str_userTempleteId = userUseCard.getUserTempleteId();
                        System.out.println("response::" + response);
                        //保存填写的新郎新娘的信息
                        saveMarryInfo();//一定要在这里面调用，否则得到的str_userTempleteId会为null
                    }
                });

    }

    private void saveMarryInfo() {
        List<AllCard.DataEntity.FirstPageEntity> firstPage = dataEntity.getFirstPage();
        System.out.println("firstPage::::::::::::" + firstPage.get(0).getId());
        int man_id = firstPage.get(0).getId();
        int women_id = firstPage.get(1).getId();
        int time_id = firstPage.get(2).getId();
        int address_id = firstPage.get(3).getId();
        String url = UrlConfig.QingjianHost+"/qingjian/user/template/savetext";
        //        CardSaveBean cardSaveBean = new CardSaveBean();
        //        cardSaveBean.setUserId(AppAndroid.getUid()+"");
        //        cardSaveBean.setType(3);
        //        cardSaveBean.setUserTempleteId(userTempleteId);
        //        cardSaveBean.setUserPageId(0);
        ArrayList<CardSaveBean.DatasEntity> datasEntities = new ArrayList<>();
        datasEntities.add(new CardSaveBean.DatasEntity(man_id, man_name));
        datasEntities.add(new CardSaveBean.DatasEntity(women_id, woman_name));
        datasEntities.add(new CardSaveBean.DatasEntity(time_id, marry_time));
        datasEntities.add(new CardSaveBean.DatasEntity(address_id, marry_address));
        //        cardSaveBean.setDatas(datasEntities);
        //        System.out.println("new Gson().toJson(datasEntities):::"+new Gson().toJson(datasEntities));
        //保存填写的新郎新娘的信息
        System.out.println("-----------------------------"+new Gson().toJson(datasEntities));
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("type", 3 + "")//3表示模板，1表示页面
                .addParams("userTempleteId", Long.parseLong(str_userTempleteId) + "")
                .addParams("userPageId", 0 + "")//默认填写0就行
                .addParams("datas", new Gson().toJson(datasEntities))//上传json字符串
                //                .postString()
                //                .url(url)
                //                .content(new Gson().toJson(cardSaveBean))
                //                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(UseCardActivity.this, "操作失败，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson();
                        System.out.println("response::" + response);
                    }
                });
    }

    private void parseJson() {
        CustomToast.showRightToast(this, "已保存");
        //保存完就跳转到请柬具体的页面
        goQingjianPage();
        saveMusic();//保存音乐
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(UseCardActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(UseCardActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                doSave();
                break;
            case R.id.tvMusic:
                Intent intent = new Intent(this, QingJianMusicActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            musicId = data.getIntExtra("musicId", 0);
            musicName = data.getStringExtra("name");
            tvMusic.setText(musicName);
        }
    }

    private void saveMusic() {
        if (musicId == 0 || "无音乐".equals(musicName)) {//说明用户并没有去选择音乐，那就不必保存
            return;
        }
        String url = UrlConfig.QingjianHost+"/qingjian/user/music/use";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                //                .addParams("templateId", getIntent().getIntExtra("templateId", 0) + "")
                .addParams("templateId", Long.parseLong(str_userTempleteId) + "")
                .addParams("musicId", musicId + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(UseCardActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(UseCardActivity.this, "保存音乐成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UseCardActivity.this, "保存音乐失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("response::" + response);
                    }
                });
    }

    private void goQingjianPage() {
        //然后跳转到请柬页面
        String url = UrlConfig.QingjianHost+"/qingjian/user/template/list";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("timestamped", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(UseCardActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        goCardDetail(response);
                        System.out.println("response::" + response);
                    }
                });

    }

    private void goCardDetail(String response) {
        MyCard myCard = new Gson().fromJson(response, MyCard.class);
        data = myCard.getData().get(myCard.getData().size() - 1);//新添加的模板肯定是最后一个
        Intent intent = new Intent(UseCardActivity.this, CardDetailActivity.class);
        /*
        不传的话会出问题，这里为什么要传这个position值呢，因为在请柬详情界面是要用到这个值进行界面刷新的
         */
        intent.putExtra("position", myCard.getData().size()-1);
        intent.putExtra("detail", data);
        this.startActivity(intent);
        finish();
        AppAndroid.deleteAllActivity();
    }

}
