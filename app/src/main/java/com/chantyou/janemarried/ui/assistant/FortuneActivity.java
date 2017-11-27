package com.chantyou.janemarried.ui.assistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.FortuneFindMonthRunner;
import com.chantyou.janemarried.ui.assistant.fragment.FortBean;
import com.chantyou.janemarried.ui.assistant.fragment.FortuneFramgent;
import com.chantyou.janemarried.ui.view.CalendarGridView;
import com.chantyou.janemarried.ui.view.CalendarGridViewAdapter;
import com.chantyou.janemarried.utils.FortuneHelper;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.ui.helper.AbsDateTimerPickerHelper;
import com.mhh.lib.utils.SystemUtils;
import com.mhh.lib.widget.XDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by j_turn on 2016/4/5.
 * Email 766082577@qq.com
 */
public class FortuneActivity extends XBaseActivity {

    @ViewInject(R.id.tvMonth)
    private TextView tvMonth;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.tvSelDay)
    private TextView tvSelDay;
    @ViewInject(R.id.tvSelDaySuit)
    private TextView tvSelDaySuit;
    @ViewInject(R.id.tvDayInfo)
    private TextView tvDayInfo;
    @ViewInject(R.id.tvSuit)
    private TextView tvSuit;
    @ViewInject(R.id.tvAvoid)
    private TextView tvAvoid;

    CalendarAdapter adapter;

    public static Calendar mCurCalendar;
    private AbsDateTimerPickerHelper dateTimerPickerHelper;

    private SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy年MM月");

    private Map<String, Map<String, Object>> mSuitMap;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_fortune);

        addAndroidEventListener(XEventCode.EVENT_RUNCODE, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        removeEventListener(XEventCode.EVENT_RUNCODE, this);
    }

    @Override
    protected void init() {
        super.init();
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.MONTH, 5);
//        CalendarGridViewAdapter currentGridAdapter = new CalendarGridViewAdapter(this, cal);
//        calendarGridView.setAdapter(currentGridAdapter);// 设置菜单Adapter
        dateTimerPickerHelper = new AbsDateTimerPickerHelper();
        tvMonth.setText(monthFormat.format(new Date()));
        tvSelDaySuit.setVisibility(View.INVISIBLE);
        tvSelDay.setText(new SimpleDateFormat("yyyy年MM月dd").format(new Date()));
        FortBean bean = FortuneHelper.getInstance().queryBean(new Date());
        setFortInfo(bean);
        adapter = new CalendarAdapter(getSupportFragmentManager());
        viewPager.getLayoutParams().height = (AppAndroid.getScreenWidth() / 7) * 6
                + SystemUtils.dipToPixel(this, CalendarGridView.VERSPACE * 6);
        viewPager.setAdapter(adapter);
//        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                runEvent(XEventCode.EVENT_SWIP_CALENAR, position);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, position);//通过position的变化来改变Calendar的值
                tvMonth.setText(monthFormat.format(cal.getTime()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.tvQYM})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.tvQYM:
                final Calendar cal = Calendar.getInstance();
                Calendar maxCal = Calendar.getInstance();
                maxCal.add(Calendar.YEAR, 10);
                dateTimerPickerHelper.showDatePickerWithMinMax(v.getContext(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH), cal.getTimeInMillis(), maxCal.getTimeInMillis(),
                        new XDatePickerDialog.OnDateSetListener() {//getTimeInMillis()方法返回此Calendar以毫秒为单位的时间
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvSelDay.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth);
                                int num = (year - cal.get(Calendar.YEAR)) * 12;
                                num += monthOfYear - cal.get(Calendar.MONTH);
                                if (num >= 0) {
                                    viewPager.setCurrentItem(num);
                                }
                            }
                        });
                break;
        }
    }

    private void setFortInfo(FortBean bean) {
        try {
            if (bean != null) {
                if (bean.flag == 1) {
                    tvSelDaySuit.setVisibility(View.VISIBLE);
                    tvSelDaySuit.setText("宜嫁娶");
                } else {
                    tvSelDaySuit.setVisibility(View.INVISIBLE);
                }
                tvDayInfo.setText("农历" + bean.lunar + " " + bean.weekday);
                tvSuit.setText(bean.suit);
                tvAvoid.setText(bean.avoid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CalendarGridViewAdapter.onDateSelectListener listener = new CalendarGridViewAdapter.onDateSelectListener() {
        @Override
        public void onDateSelect(FortBean bean, int year, int month, int dayOfMonth, int dMonth) {
            try {
//                tvMonth.setText(String.format("%1$d年%2$d月", year, (month + 1)));
                tvSelDay.setText(String.format("%1$d年%2$d月%3$d日", year, (month + 1), dayOfMonth));
                if (dMonth != 0) {
                    if (dMonth == 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else if (dMonth == -1 && viewPager.getCurrentItem() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
                setFortInfo(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.EVENT_RUNCODE:
                int code = (int) event.getParamsAtIndex(0);
                if (code == XEventCode.HTTP_FORTUNE_FIND) {
                    String date = (String) event.getParamsAtIndex(1);
                    pushEvent(new FortuneFindMonthRunner(date));
                }
                break;
            case XEventCode.HTTP_FORTUNE_FIND:
                if (event.isSuccess()) {
                    String date = (String) event.getParamsAtIndex(1);
                }
                break;
        }
    }

    private class CalendarAdapter extends FragmentPagerAdapter {

        public CalendarAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("posi", position);
            return Fragment.instantiate(FortuneActivity.this, FortuneFramgent.class.getName(), bundle);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(FortuneActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(FortuneActivity.this);
    }
}
