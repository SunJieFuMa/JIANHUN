package com.chantyou.janemarried.httprunner.local;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.ui.base.CityBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lib.mark.core.AsynEvent;
import com.lib.mark.core.Event;
import com.mhh.lib.utils.SystemUtils;

import java.util.List;

import static com.chantyou.janemarried.framework.XEventCode.EVENT_CITY_ALL;

/**
 * Created by j_turn on 2016/2/21.
 * Email 766082577@qq.com
 */
public class CityAllRunner extends AsynEvent {

    public CityAllRunner(Object... params) {
        super(EVENT_CITY_ALL, null, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {//没有去访问网络，从assets文件中读取的json数据
        String citys = SystemUtils.getFromAssets(AppAndroid.getApp(), "city.txt");
        List<CityBean> list = /*(List<CityBean>) JsonUtil.jsonToList(citys);*/
        new Gson().fromJson(citys, new TypeToken<List<CityBean>>(){}.getType());
        if(list != null && list.size() > 10) {
            ev.setSuccess(true);
            ev.addReturnParams(list);
        }
    }
}
