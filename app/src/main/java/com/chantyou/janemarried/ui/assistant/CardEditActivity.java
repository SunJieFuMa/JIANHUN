package com.chantyou.janemarried.ui.assistant;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.InviCardSaveRunner;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.helper.AbsDateTimerPickerHelper;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.widget.XDatePickerDialog;
import com.mhh.lib.widget.XTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by j_turn on 2016/4/10.
 * Email 766082577@qq.com
 */
public class CardEditActivity extends XBaseActivity {

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

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_card_edit);

        addAndroidEventListener(XEventCode.CARD_GETINFO, this);

        runEvent(XEventCode.EVENT_RUNCODE, XEventCode.CARD_GETINFO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.CARD_GETINFO, this);
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
                dateTimerPickerHelper.showDatePicker(CardEditActivity.this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, new XDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        tCalendar.set(Calendar.YEAR, year);
                        tCalendar.set(Calendar.MONTH, monthOfYear);
                        tCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Calendar cal = Calendar.getInstance();
                        dateTimerPickerHelper.showTimePicker(CardEditActivity.this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), new XTimePickerDialog.OnTimeSetListener() {
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

    private void setData(Map<String, Object> map) {
        if(map != null) {
            etName.setText(JsonUtil.getItemString(map, "newMan"));
            etName2.setText(JsonUtil.getItemString(map, "newWoman"));
            tvTime.setText(JsonUtil.getItemString(map, "marryDay") + " " + JsonUtil.getItemString(map, "marryTime"));
            etHotal.setText(JsonUtil.getItemString(map, "hotelName"));
            tvAddress.setText(JsonUtil.getItemString(map, "hotelAddress"));
        }
    }

    private void doSave() {
        String s1 = etName.getText().toString();
        if(TextUtils.isEmpty(s1)) {
            CustomToast.showWorningToast(this, "请输入新郎姓名");
            return;
        }
        String s2 = etName2.getText().toString();
        if(TextUtils.isEmpty(s2)) {
            CustomToast.showWorningToast(this, "请输入新娘姓名");
            return;
        }
        String s3 = tvTime.getText().toString();
        if(TextUtils.isEmpty(s3)) {
            CustomToast.showWorningToast(this, "请选择婚宴时间");
            return;
        }
        String s4 = etHotal.getText().toString();
        if(TextUtils.isEmpty(s4)) {
            CustomToast.showWorningToast(this, "请输入酒店名称");
            return;
        }
        String s5 = tvAddress.getText().toString();
        if(TextUtils.isEmpty(s5)) {
            CustomToast.showWorningToast(this, "请选择酒店地址");
            return;
        }

        showProgressDialog(null, "正在保存...");
        //传递的第一个参数是为了区别是文字还是图片还是音乐
        pushEventEx(false, null, new InviCardSaveRunner(1, s1, s2, s3, s4, s5), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.font_save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main_menu) {
            doSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_CARD_SAVE:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, "已保存");
                    finish();
                } else {
                    CustomToast.showWorningToast(this, event.getMessage("操作失败"));
                }
                break;
            case XEventCode.CARD_GETINFO:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getParamsAtIndex(0);
                    map = (Map<String, Object>) map.get("data");
                    setData(map);
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(CardEditActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(CardEditActivity.this);
    }
}
