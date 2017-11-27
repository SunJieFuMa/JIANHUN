package com.chantyou.janemarried.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.base.BaseListAdapter;
import com.chantyou.janemarried.ui.assistant.FortuneActivity;
import com.chantyou.janemarried.ui.assistant.fragment.FortBean;

import java.util.Calendar;
import java.util.Date;

public class CalendarGridViewAdapter extends BaseListAdapter<FortBean> {

    private onDateSelectListener listener;

    private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
//    private Calendar calSelected = Calendar.getInstance(); // 选择的日历

    private Calendar calToday = Calendar.getInstance(); // 今日
    private int iMonthViewCurrentMonth = 0; // 当前视图月
    private int itemWidth;
//
//    // 根据改变的日期更新日历
//    // 填充日历控件用
//    private void UpdateStartDateForMonth() {
//        calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
//        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月
//
//        // 星期一是2 星期天是1 填充剩余天数
//        int iDay = 0;
//        int iFirstDayOfWeek = Calendar.MONDAY;
//        int iStartDay = iFirstDayOfWeek;
//        if (iStartDay == Calendar.MONDAY) {
//            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
//            if (iDay < 0)
//                iDay = 6;
//        }
//        if (iStartDay == Calendar.SUNDAY) {
//            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
//            if (iDay < 0)
//                iDay = 6;
//        }
//        calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
//
//        calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位
//
//    }
//
//    ArrayList<Date> titles;
//
//    private ArrayList<Date> getDates() {
//
//        UpdateStartDateForMonth();
//
//        ArrayList<Date> alArrayList = new ArrayList<Date>();
//
//        for (int i = 1; i <= 42; i++) {
////            if(i == 36) {
////                if(calStartDate.get(Calendar.MONTH) != iMonthViewCurrentMonth) {
////                    break;
////                }
////            }
//            alArrayList.add(calStartDate.getTime());
//            calStartDate.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        return alArrayList;
//    }

    private Context activity;
    Resources resources;

    // construct
    public CalendarGridViewAdapter(Context a, Calendar cal) {
        calStartDate.setTime(cal.getTime());
        activity = a;
        resources = activity.getResources();
//        titles = getDates();
        //我真是个天才，加上下面这一句就能知道当前显示的月份是哪一个，这样才能在后面实现当前月份的日子显示黑色，其余的显示灰色
        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月
    }

    public CalendarGridViewAdapter(Activity a) {
        activity = a;
        resources = activity.getResources();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_calendar;
    }

    @Override
    protected AbsViewHolder setViewHolder(View view) {
        return new ViewHolder(view);
    }

    private class ViewHolder extends AbsViewHolder {

        ImageView ivBg;
        ImageView ivJi;
        TextView tvDay;
        TextView tvToDay;
        Date date;
        FortBean bean;

        ViewHolder(View v) {
            ivBg = (ImageView) v.findViewById(R.id.ivBg);
            ivJi = (ImageView) v.findViewById(R.id.ivJi);
            tvDay = (TextView) v.findViewById(R.id.tvDay);
            tvToDay = (TextView) v.findViewById(R.id.tvToDay);
            if (itemWidth == 0) {
                itemWidth = AppAndroid.getScreenWidth() / 7;
            }
            ivBg.getLayoutParams().width = ivBg.getLayoutParams().height = itemWidth;
            ivBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    FortuneActivity.mCurCalendar = cal;
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onDateSelect(bean, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                                cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) - iMonthViewCurrentMonth);
                    }
                }
            });
        }

        @Override
        public void setValue(int position, FortBean item) {
            super.setValue(position, item);
            this.bean = item;
            if (bean != null) {
                Calendar calCalendar = Calendar.getInstance();
                calCalendar.setTime(bean.date2);
                date = bean.date2;

                final int iMonth = calCalendar.get(Calendar.MONTH);

                if (equalsDate(calToday.getTime(), date)) {
                    // 当前日期
                    ivBg.setImageResource(R.drawable.ic_gbg);
                    if (TextUtils.isEmpty(bean.lunar2)) {
                        tvToDay.setText(new CalendarUtil(calCalendar).toString());
                    } else {
                        tvToDay.setText(bean.lunar2);
                    }
//            tvToDay.setText(calendarUtil.getChineseDay(calCalendar));
                } else {
                    if (TextUtils.isEmpty(bean.lunar2)) {
                        tvToDay.setText(new CalendarUtil(calCalendar).toString());
                    } else {
                        tvToDay.setText(bean.lunar2);
                    }
//            tvToDay.setText(calendarUtil.getChineseDay(calCalendar));
                }

                // 日期开始

                // 判断是否是当前月
                if (iMonth == iMonthViewCurrentMonth) {
//                    tvDay.setTextColor(resources.getColor(R.color.light_black));
//                    tvToDay.setTextColor(resources.getColor(R.color.c_808080));
                    tvDay.setTextColor(resources.getColor(R.color.black));
                    tvToDay.setTextColor(resources.getColor(R.color.light_black));
                } else {
                    tvToDay.setTextColor(resources.getColor(R.color.c_9a9a9a));
                    tvDay.setTextColor(resources.getColor(R.color.c_9a9a9a));
                }

                // 设置背景颜色
                if (FortuneActivity.mCurCalendar != null && equalsDate(FortuneActivity.mCurCalendar.getTime(), date)) {
                    // 选择的
                    ivBg.setImageResource(R.drawable.ic_rbg);
                    tvDay.setTextColor(resources.getColor(R.color.white));
                    tvToDay.setTextColor(resources.getColor(R.color.white_gray));
                } else {
                    if (equalsDate(calToday.getTime(), date)) {
                        // 当前日期
                        ivBg.setImageResource(R.drawable.ic_gbg);
                        tvDay.setTextColor(resources.getColor(R.color.white));
                        tvToDay.setTextColor(resources.getColor(R.color.white_gray));
                    } else {
                        ivBg.setImageResource(R.color.transparent);
                    }
                }
                // 设置背景颜色结束

                ivJi.setVisibility(bean.flag == 1 ? View.VISIBLE : View.GONE);
                int day = date.getDate(); // 日期
                tvDay.setText(String.valueOf(day));
                tvDay.setId(position + 500);
            }
        }
    }

    public void setOnDateSelectListener(onDateSelectListener listener) {
        this.listener = listener;
    }

    public interface onDateSelectListener {
        void onDateSelect(FortBean bean, int year, int month, int dayOfMonth, int dMonth);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private Boolean equalsDate(Date date1, Date date2) {

        if (date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonth()
                && date1.getDate() == date2.getDate()) {
            return true;
        } else {
            return false;
        }

    }

}
