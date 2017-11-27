package com.chantyou.janemarried.httprunner.user;

import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

/**
 * Created by mhh on 2016/5/18.
 *
 */
public class GetCodeRunner extends HttpRunner {

    private static String url = UrlConfig.GET_SMS_CODE;

    public GetCodeRunner (Object... params) {
        super(XEventCode.SMS_CODE_EVENT, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("phone", (String) ev.getParamsAtIndex(0)));
        pList.add(new NameValuePair("smstype", (String) ev.getParamsAtIndex(1)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
