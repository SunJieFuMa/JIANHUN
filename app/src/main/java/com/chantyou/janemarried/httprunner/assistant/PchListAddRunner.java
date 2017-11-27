package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.PCHLIST_ADD;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_PCHLIST_ADD;

/**
 * Created by j_turn on 2016/2/4 22:19
 * Email：766082577@qq.com
 */
public class PchListAddRunner extends HttpRunner {

    private static final String url = PCHLIST_ADD;

    public PchListAddRunner(Object... params) {
        super(HTTP_PCHLIST_ADD, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("id", (String) ev.getParamsAtIndex(0)));
        pList.add(new NameValuePair("name", (String) ev.getParamsAtIndex(1)));
        pList.add(new NameValuePair("ItemTotalPrice", (String) ev.getParamsAtIndex(2)));
        pList.add(new NameValuePair("brand", (String) ev.getParamsAtIndex(3)));
        pList.add(new NameValuePair("num", (String) ev.getParamsAtIndex(4)));
        pList.add(new NameValuePair("tip", (String) ev.getParamsAtIndex(5)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
