package com.chantyou.janemarried.httprunner.assistant;

import android.text.TextUtils;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import org.json.JSONObject;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.MARRY_ADDPROCESS;

/**
 * Created by j_turn on 2016/1/24 13:02
 * Email：766082577@qq.com
 */
public class MarryAddProcessRunner extends HttpRunner {

    private static final String url = MARRY_ADDPROCESS;

    public MarryAddProcessRunner(Object... params) {
        super(XEventCode.HTTP_MARRY_ADDPROCESS, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("processId", String.valueOf(ev.getParamsAtIndex(0))));
        pList.add(new NameValuePair("type", String.valueOf(ev.getParamsAtIndex(1))));
        pList.add(new NameValuePair("dateTime", (String) ev.getParamsAtIndex(2)));
        pList.add(new NameValuePair("thing", (String) ev.getParamsAtIndex(3)));
        pList.add(new NameValuePair("persons", (String) ev.getParamsAtIndex(4)));
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
