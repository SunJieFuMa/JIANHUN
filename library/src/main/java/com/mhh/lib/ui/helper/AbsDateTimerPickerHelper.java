package com.mhh.lib.ui.helper;

import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mhh.lib.widget.XDatePickerDialog;
import com.mhh.lib.widget.XTimePickerDialog;

import java.util.Calendar;

/**
 * Created by j_turn on 2016/2/21.
 * Email 766082577@qq.com
 */
public class AbsDateTimerPickerHelper implements  IDateTimePickerHelper {

    public void showDatePicker(Context cxt,int year, int month, int day, long maxDate,
                               final XDatePickerDialog.OnDateSetListener onDateSetListener) {
        XDatePickerDialog dialog = new XDatePickerDialog(cxt, onDateSetListener, year,
                month, day);
        if (maxDate > 0) {
            dialog.setMaxDate(maxDate);
        }
        dialog.show();
    }

    public void showDatePickerWithMinMax(Context cxt,int year, int month, int day, long minDate,long maxDate,
                               final XDatePickerDialog.OnDateSetListener onDateSetListener) {
        XDatePickerDialog dialog = new XDatePickerDialog(cxt, onDateSetListener, year,
                month, day);
        if (minDate > 0) {
            dialog.setMinDate(minDate);
        }
        if (maxDate > 0) {
            dialog.setMaxDate(maxDate);
        }
        dialog.show();
    }

    public void showTimePicker(Context cxt,int hourOfDay, int minute,
                               final XTimePickerDialog.OnTimeSetListener timeSetListener) {
        XTimePickerDialog dialog = new XTimePickerDialog(cxt, timeSetListener, hourOfDay, minute);
        dialog.show();
    }
}
