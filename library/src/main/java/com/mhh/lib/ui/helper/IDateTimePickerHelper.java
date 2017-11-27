package com.mhh.lib.ui.helper;

import android.content.Context;

import com.mhh.lib.widget.XDatePickerDialog;
import com.mhh.lib.widget.XTimePickerDialog;

import java.util.Calendar;

/**
 * Created by j_turn on 2016/2/21.
 * Email 766082577@qq.com
 */
public interface IDateTimePickerHelper {

    /**
     * 弹出日期选择控件
     * @param cxt
     * @param year
     * @param month
     * @param day
     * @param maxDate
     * @param onDataChooseListener
     */
    void showDatePicker(Context cxt,int year, int month, int day, long maxDate,
                        final XDatePickerDialog.OnDateSetListener onDataChooseListener);


    /**
     * 时间选择控件
     * @param cxt
     * @param hourOfDay
     * @param minute
     * @param timeSetListener
     */
    void showTimePicker(Context cxt,int hourOfDay, int minute,
                               final XTimePickerDialog.OnTimeSetListener timeSetListener);
}
