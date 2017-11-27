package com.mhh.lib.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.mhh.lib.R;

import java.util.Calendar;

public class XTimePickerDialog extends AlertDialog implements
		OnClickListener, OnTimeChangedListener {

	private static final String HOUR = "year";
	private static final String MINUTE = "month";

	private final TimePicker mTimePicker;
	private final OnTimeSetListener mCallBack;
	private final Calendar mCalendar;

	/**
	 * The callback used to indicate the user is done filling in the date.
	 */
	public interface OnTimeSetListener {

		void onTimeSet(TimePicker view, int hourOfDay, int minute);
	}

	public XTimePickerDialog(Context context, OnTimeSetListener callBack,
							 int hourOfDay, int minute) {
		this(context, 3, callBack, hourOfDay, minute);
	}
	public XTimePickerDialog(Context context, int theme,
							 OnTimeSetListener callBack, int hourOfDay, int minute) {
		super(context, theme);

		mCallBack = callBack;

		mCalendar = Calendar.getInstance();

		setButton(BUTTON_POSITIVE, getContext().getText(R.string.ok), this);
		setButton(BUTTON_NEGATIVE, getContext().getText(R.string.cancel), this);
		setIcon(0);

		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.xtimepicker, null);
		setView(view);
		mTimePicker = (TimePicker) view;
		mTimePicker.setOnTimeChangedListener(this);
		setTitle("请选择时间");
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_POSITIVE) {
			tryNotifyDateSet();
		}
	}

	/**
	 * Gets the {@link TimePicker} contained in this dialog.
	 * 
	 * @return The calendar view.
	 */
	public TimePicker getTimePicker() {
		return mTimePicker;
	}

	private void tryNotifyDateSet() {
		if (mCallBack != null) {
			mTimePicker.clearFocus();

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				mCalendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getHour());
				mCalendar.set(Calendar.MINUTE, mTimePicker.getMinute());
			}
			mCallBack.onTimeSet(mTimePicker, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
		}
	}

	@Override
	protected void onStop() {
		// tryNotifyDateSet();
		super.onStop();
	}

	private void updateTitle(int hourOfDay, int minute) {
		if (true) {
			mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mCalendar.set(Calendar.MINUTE, minute);
			String title = DateUtils.formatDateTime(getContext(),
					mCalendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_24HOUR);
			setTitle(title);
		}
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		updateTitle(hourOfDay, minute);
	}
}
