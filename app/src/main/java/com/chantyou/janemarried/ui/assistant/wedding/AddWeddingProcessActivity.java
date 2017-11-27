package com.chantyou.janemarried.ui.assistant.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.dialog.PopupWheelSelector;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.MarryAddProcessRunner;
import com.chantyou.janemarried.ui.base.MarryPhasesAllActivity;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.helper.AbsDateTimerPickerHelper;
import com.mhh.lib.ui.helper.IDateTimePickerHelper;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.widget.XTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by j_turn on 2016/1/23 23:45
 * Email：766082577@qq.com
 */
public class AddWeddingProcessActivity extends XBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.etThing)
    protected EditText etThing;
    @ViewInject(R.id.etPerson)
    protected EditText etPerson;
    @ViewInject(R.id.tvProcess)
    protected EditText tvProcess;
    @ViewInject(R.id.tvTime)
    protected TextView tvTime;

    private int phaseId = -1;
    protected int processId = -1;

    private PopupWheelSelector wheelSelector;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    private Calendar tCalendar;

    /**
     * 日期选择 类
     */
    private IDateTimePickerHelper dateTimePickerHelper;

    protected int getLayoutId() {
        return R.layout.activity_addweddingprocess;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(getLayoutId());

        registerEditTextInputManager(etPerson);
        registerEditTextInputManager(etThing);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEditTextInputManager(etPerson);
        unRegisterEditTextInputManager(etThing);
    }

    protected void setView() {

        tvTime.setOnClickListener(this);
    }

    @Override
    protected void init() {
        super.init();
        etThing = (EditText) findViewById(R.id.etThing);
        etPerson = (EditText) findViewById(R.id.etPerson);
        tvProcess = (EditText) findViewById(R.id.tvProcess);
        tvTime = (TextView) findViewById(R.id.tvTime);

        dateTimePickerHelper = new AbsDateTimerPickerHelper();
        setView();
    }

    private void dismissPopup() {
        if(wheelSelector != null && wheelSelector.isShowing()) {
            wheelSelector.dismiss();
            wheelSelector = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTime:
                Calendar cal = Calendar.getInstance();
                tCalendar = Calendar.getInstance();
                dateTimePickerHelper.showTimePicker(AddWeddingProcessActivity.this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), new XTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        tCalendar.set(Calendar.MINUTE, minute);
                        tvTime.setText(format.format(new Date(tCalendar.getTimeInMillis())));
                    }
                });
//                dateTimePickerHelper.showDatePicker(this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, new XDatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                        tCalendar.set(Calendar.YEAR, year);
//                        tCalendar.set(Calendar.MONTH, monthOfYear);
//                        tCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    }
//                });
                break;
        }
    }

    private void addProcess() {
        String datetime = tvTime.getText().toString();
        String process = tvProcess.getText().toString();
        if(TextUtils.isEmpty(process)) {
            CustomToast.showWorningToast(this, "请输入婚礼流程类型");
            return;
        }
        try {
            format.parse(datetime);
        } catch (Exception e) {
            e.printStackTrace();
            datetime = "";
//            CustomToast.showErrorToast(this, "日期时间格式错误");
//            return;
        }
        String thing = etThing.getText().toString();
        String  persons = etPerson.getText().toString();
        if(TextUtils.isEmpty(thing)) {
            CustomToast.showWorningToast(this, "请输入具体事宜");
            return;
        }
//        if(TextUtils.isEmpty(persons)) {
//            CustomToast.showWorningToast(this, "请输入重要参与人员");
//            return;
//        }
        showProgressDialog("添加新流程","正在提交...");
        pushEvent(new MarryAddProcessRunner(processId == -1 ? "" : processId+"", process, datetime, thing, persons));
    }

    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.font_save);
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
        if (item.getItemId() == R.id.main_menu) {
            addProcess();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_MARRY_ADDPROCESS:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, "添加成功");
                    finish();
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("添加失败"));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == MarryPhasesAllActivity.REQCODE) {
                String phasesinfo = data.getStringExtra("phasesinfo");
                Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(phasesinfo);
                tvProcess.setText(JsonUtil.getItemString(map, "name"));
                phaseId = JsonUtil.getItemInt(map, "phaseId");
            }
        }
    }
}
