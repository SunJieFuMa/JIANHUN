package com.chantyou.janemarried.httprunner.shop;

import com.chantyou.janemarried.httprunner.JYHttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.SHOP_APPOINTMENT;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_APPOINTMENT;

//根据城市获取区县
public class AppointmentRunner extends JYHttpRunner {

    private static final String url = SHOP_APPOINTMENT;

    public AppointmentRunner(Object... params) {
        super(HTTP_APPOINTMENT, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("shopId", (String) ev.getParamsAtIndex(0)));
        pList.add(new NameValuePair("name", (String) ev.getParamsAtIndex(1)));
        pList.add(new NameValuePair("phone", (String) ev.getParamsAtIndex(2)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doResult(ev, response.getResult());
        System.out.println("预约到店返回数据："+response.getResult());
    }
}
