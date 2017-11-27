package com.chantyou.janemarried.httprunner.assistant;

import android.text.TextUtils;
import android.util.Log;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.ui.assistant.fragment.FortBean;
import com.chantyou.janemarried.ui.view.CalendarUtil;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.FortuneHelper;
import com.lib.mark.core.AsynEvent;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.mhh.lib.utils.JsonUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chantyou.janemarried.config.UrlConfig.FORTUNE_FIND;

/**
 * Created by j_turn on 2016/4/6.
 * Email 766082577@qq.com
 */
public class FortuneDataRunner extends HttpRunner {

    public FortuneDataRunner(Object... params) {
        super(XEventCode.ASYN_CALENAR, null, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        Calendar cal = (Calendar) ev.getParamsAtIndex(1);

        try {
            int count = FortuneHelper.getInstance().queryCount(cal.getTime());
            if(count < 20) {
                LinkedList<NameValuePair> pList = postPublicPair();
                pList.add(new NameValuePair("yearMonth", FortuneHelper.mFormat.format(cal.getTime())));

                Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(FORTUNE_FIND)
                        .setMethod(HttpMethods.Post)
                        .setHttpBody(new UrlEncodedFormBody(pList)));

                String result = response.getResult();
                if(Constants.DEBUG) {
                    Log.v("HttpRunner", "url=" + FORTUNE_FIND);
                    Log.d("HttpRunner", "result=" + result);
                }
                if(!TextUtils.isEmpty(result)) {
                    Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(result);
                    ev.setMessage(JsonUtil.getItemString(map, "msg"));
                    List<Map<String, Object>> jx = (List<Map<String, Object>>) map.get("jx");
                    if(jx != null) {
//                    StringBuilder builder = new StringBuilder("insert into `tb_fortune` (`yearMonth`, `date`, `lunar`, `lunarYear`, `weekday`, `animalsYear`, `avoid`, `suit`) values('2019-12','2019-12-2','十一月初七','己亥年','星期一','猪','诸事不宜.','诸事不宜.'),");
                        StringBuilder builder = new StringBuilder("insert into `tb_fortune` (`yearMonth`, `date`, `lunar`, `lunarYear`, `weekday`, `animalsYear`, `avoid`, `suit`) values");
                        int num=0;
                        for(Map<String, Object> item : jx) {
                            if(TextUtils.isEmpty(JsonUtil.getItemString(item, "suit"))) {
                                break;
                            }
                            builder.append("(")
                                    .append(JsonUtil.getItemString(item, "yearMonth")).append(",")
                                    .append(JsonUtil.getItemString(item, "date")).append(",")
                                    .append(JsonUtil.getItemString(item, "lunar")).append(",")
                                    .append(JsonUtil.getItemString(item, "lunarYear")).append(",")
                                    .append(JsonUtil.getItemString(item, "weekday")).append(",")
                                    .append(JsonUtil.getItemString(item, "animalsYear")).append(",")
                                    .append(JsonUtil.getItemString(item, "avoid")).append(",")
                                    .append(JsonUtil.getItemString(item, "suit"));

                            builder.append(")");
                            num++;
                        }
                        builder.append("");
                        if(num > count) {
                            FortuneHelper.getInstance().delAll(cal.getTime());
                            FortuneHelper.getInstance().insertSql(builder.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<FortBean> list = getDates(cal);
        ev.addReturnParams(list);
        ev.setSuccess(true);
    }

    private ArrayList<FortBean> getDates(Calendar cal) {

        UpdateStartDateForMonth(cal);

        ArrayList<FortBean> alArrayList = new ArrayList<>();

        for (int i = 1; i <= 42; i++) {
//            if(i == 36) {
//                if(calStartDate.get(Calendar.MONTH) != iMonthViewCurrentMonth) {
//                    break;
//                }
//            }
            FortBean bean = FortuneHelper.getInstance().queryBean(cal.getTime());
            alArrayList.add(bean);
            try {
                bean.lunar2 = new CalendarUtil(cal).toString();
            }catch (Exception e) {
                e.printStackTrace();
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return alArrayList;
    }
    private void UpdateStartDateForMonth(Calendar cal) {
        cal.set(Calendar.DATE, 1); // 设置成当月第一天
//        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

        // 星期一是2 星期天是1 填充剩余天数
        int iDay = 0;
        int iFirstDayOfWeek = Calendar.MONDAY;
        int iStartDay = iFirstDayOfWeek;
        if (iStartDay == Calendar.MONDAY) {
            iDay = cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
            if (iDay < 0)
                iDay = 6;
        }
        if (iStartDay == Calendar.SUNDAY) {
            iDay = cal.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (iDay < 0)
                iDay = 6;
        }
        cal.add(Calendar.DAY_OF_WEEK, -iDay);

        cal.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

    }
}
