package com.mhh.lib.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.mhh.lib.R;

import java.util.Calendar;

public class XDatePickerDialog extends AlertDialog implements 
										OnClickListener,
										OnDateChangedListener{

	private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";

    private final DatePicker mDatePicker;
    private final OnDateSetListener mCallBack;
    private final Calendar mCalendar;
    
    private long  mMaxDate;
    private long  mMinDate;

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {

        /**
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param monthOfYear The month that was set (0-11) for compatibility
         *  with {@link Calendar}.
         * @param dayOfMonth The day of the month that was set.
         */
        void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }

    /**
     * @param context The context the dialog is to run in.
     * @param callBack How the parent is notified that the date is set.
     * @param year The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth The initial day of the dialog.
     */
    public XDatePickerDialog(Context context,
            OnDateSetListener callBack,
            int year,
            int monthOfYear,
            int dayOfMonth) {
        this(context, 3, callBack, year, monthOfYear, dayOfMonth);
    }

    /**
     * @param context The context the dialog is to run in.
     * @param theme the theme to apply to this dialog
     * @param callBack How the parent is notified that the date is set.
     * @param year The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth The initial day of the dialog.
     */
    public XDatePickerDialog(Context context,
            int theme,
            OnDateSetListener callBack,
            int year,
            int monthOfYear,
            int dayOfMonth) {
        super(context, theme);

        mCallBack = callBack;

        mCalendar = Calendar.getInstance();

        setButton(BUTTON_POSITIVE,getContext().getText(R.string.ok), this);
        setButton(BUTTON_NEGATIVE, getContext().getText(R.string.cancel), this);
        setIcon(0);
        
        View view = LayoutInflater.from(getContext()).inflate(R.layout.xdatepicker, null);
        setView(view);
        mDatePicker = (DatePicker) view;
        mDatePicker.init(year, monthOfYear, dayOfMonth, this);
        updateTitle(year, monthOfYear, dayOfMonth);
    }

    public void setMaxDate(long max){
    	mMaxDate = max;
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mDatePicker.getYear());
		cal.set(Calendar.MONTH, mDatePicker.getMonth());
		cal.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
		Calendar m = Calendar.getInstance();
		if(cal.after(m)){
			mDatePicker.updateDate(m.get(Calendar.YEAR), 
					m.get(Calendar.MONTH), 
					m.get(Calendar.DAY_OF_MONTH));
		}
    }
    
    public void setMinDate(long min){
    	mMinDate = min;
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mDatePicker.getYear());
		cal.set(Calendar.MONTH, mDatePicker.getMonth());
		cal.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
		Calendar m = Calendar.getInstance();
		if(cal.before(m)){
			mDatePicker.updateDate(m.get(Calendar.YEAR),
                    m.get(Calendar.MONTH),
                    m.get(Calendar.DAY_OF_MONTH));
		}
    }
    
    @Override
    public void onClick(DialogInterface dialog, int which) {
    	if(which == DialogInterface.BUTTON_POSITIVE){
    		tryNotifyDateSet();
    	}
    }

    @Override
	public void onDateChanged(DatePicker view, int year,
            int month, int day) {
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		
		if(mMaxDate != 0){
			Calendar max = Calendar.getInstance();
			max.setTimeInMillis(mMaxDate);
			if(cal.after(max)){
				year = max.get(Calendar.YEAR);
				month = max.get(Calendar.MONTH);
				day = max.get(Calendar.DAY_OF_MONTH);
			}
		}
		if(mMinDate != 0){
			Calendar min = Calendar.getInstance();
			min.setTimeInMillis(mMinDate);
            if(min.after(cal)) {
                year = min.get(Calendar.YEAR);
                month = min.get(Calendar.MONTH);
                day = min.get(Calendar.DAY_OF_MONTH);
            }
		}
        mDatePicker.init(year, month, day, this);
        updateTitle(year, month, day);
    }

    /**
     * Gets the {@link DatePicker} contained in this dialog.
     *
     * @return The calendar view.
     */
    public DatePicker getDatePicker() {
        return mDatePicker;
    }

    /**
     * Sets the current date.
     *
     * @param year The date year.
     * @param monthOfYear The date month.
     * @param dayOfMonth The date day of month.
     */
    public void updateDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker.updateDate(year, monthOfYear, dayOfMonth);
    }

    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            mDatePicker.clearFocus();
            mCallBack.onDateSet(mDatePicker, mDatePicker.getYear(),
                    mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        }
    }

    @Override
    protected void onStop() {
//        tryNotifyDateSet();
        super.onStop();
    }

    private void updateTitle(int year, int month, int day) {
        if (true) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            String title = DateUtils.formatDateTime(getContext(),
                    mCalendar.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_SHOW_WEEKDAY
                    | DateUtils.FORMAT_SHOW_YEAR
                    | DateUtils.FORMAT_ABBREV_MONTH
                    | DateUtils.FORMAT_ABBREV_WEEKDAY);
            setTitle(title);
        }
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(YEAR, mDatePicker.getYear());
        state.putInt(MONTH, mDatePicker.getMonth());
        state.putInt(DAY, mDatePicker.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int year = savedInstanceState.getInt(YEAR);
        int month = savedInstanceState.getInt(MONTH);
        int day = savedInstanceState.getInt(DAY);
        mDatePicker.init(year, month, day, this);
    }

}
