package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.chantyou.janemarried.httprunner.assistant.TaskEditRunner;
import com.chantyou.janemarried.utils.MyCalendar;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
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
 * Created by j_turn on 2016/4/2.
 * Email 766082577@qq.com
 */
public class TaskEditActivity extends XBaseActivity {

    @ViewInject(R.id.etName)
    private EditText etName;
    @ViewInject(R.id.tvCtime)
    private TextView tvCtime;
    @ViewInject(R.id.tvTtime)
    private TextView tvTtime;
    @ViewInject(R.id.etTips)
    private EditText etTips;
    @ViewInject(R.id.edt_yusuan)
    private EditText edt_yusuan;

    private int id;
    private String title;
    Map<String, Object> nodeInfo;

    Calendar tCalendar;
    AbsDateTimerPickerHelper dateTimerPickerHelper;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void launch(Context cxt, Map<String, Object> map) {
        Intent intent = new Intent(cxt, TaskEditActivity.class);
        intent.putExtra("nodeInfo", JsonUtil.objectToJson(map));
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        String nodes = getIntent().getStringExtra("nodeInfo");
        nodeInfo = (Map<String, Object>) JsonUtil.jsonToMap(nodes);
        id = JsonUtil.getItemInt(nodeInfo, "id");
        if (nodeInfo == null || id == 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_taskedit);
    }

    @Override
    protected void init() {
        super.init();
        title = JsonUtil.getItemString(nodeInfo, "title");
        etName.setText(title);
        etTips.setText(JsonUtil.getItemString(nodeInfo, "remark"));
        tvCtime.setText(JsonUtil.getItemString(nodeInfo, "completeTime"));
        tvTtime.setText(JsonUtil.getItemString(nodeInfo, "tipTime"));
        if (title != null) {
            etName.setSelection(title.length());
        }

        dateTimerPickerHelper = new AbsDateTimerPickerHelper();
        edt_yusuan.setText(JsonUtil.getItemString(nodeInfo, "budget"));
    }

    private void doSave() {
        String title = etName.getText().toString();
        String comTime = tvCtime.getText().toString();
        String tipTime = tvTtime.getText().toString();
        String remark = etTips.getText().toString();
        String yusuan=edt_yusuan.getText().toString();
        if (title.equals(this.title)) {
            title = "";
        }

        pushEvent(new TaskEditRunner(id, title, comTime, tipTime, remark,yusuan));
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
        if (item.getItemId() == R.id.main_menu) {
            doSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.vCtime, R.id.vTtime})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.vCtime:
                Calendar cal = Calendar.getInstance();
                tCalendar = Calendar.getInstance();
                dateTimerPickerHelper.showDatePicker(this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, new XDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        tCalendar.set(Calendar.YEAR, year);
                        tCalendar.set(Calendar.MONTH, monthOfYear);
                        tCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        tvCtime.setText(format.format(new Date(tCalendar.getTimeInMillis())).split(" ")[0]);
                    }
                });
                break;
            case R.id.vTtime:
                cal = Calendar.getInstance();
                tCalendar = Calendar.getInstance();
                dateTimerPickerHelper.showDatePicker(this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, new XDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        tCalendar.set(Calendar.YEAR, year);
                        tCalendar.set(Calendar.MONTH, monthOfYear);
                        tCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Calendar cal = Calendar.getInstance();
                        dateTimerPickerHelper.showTimePicker(TaskEditActivity.this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), new XTimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                tCalendar.set(Calendar.MINUTE, minute);
                                tvTtime.setText(format.format(new Date(tCalendar.getTimeInMillis())));
                            }
                        });
                    }
                });
                break;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TASK_USEREDIT:
                if (event.isSuccess()) {
                    try {
                        String title = etName.getText().toString();
                        String tipTime = (String) event.getParamsAtIndex(3);
                        String desc = (String) event.getParamsAtIndex(4);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(format.parse(tipTime));
                        new MyCalendar().addEvent(this, title, desc, calendar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                    CustomToast.showRightToast(this, "保存成功");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(TaskEditActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(TaskEditActivity.this);
    }
}
