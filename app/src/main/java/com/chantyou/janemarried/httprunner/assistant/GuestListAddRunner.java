package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.GUESTLIST_ADD;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_GUESTLIST_ADD;

/**
 * Created by j_turn on 2016/2/6 22:43
 * Email：766082577@qq.com
 */
public class GuestListAddRunner extends HttpRunner {

    private static final String url = GUESTLIST_ADD;

    public GuestListAddRunner(Object... params) {
        super(HTTP_GUESTLIST_ADD, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("deskName", (String) ev.getParamsAtIndex(0)));
        pList.add(new NameValuePair("Persons", (String) ev.getParamsAtIndex(1)));
        pList.add(new NameValuePair("id", (String) ev.getParamsAtIndex(2)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefParams(ev, response.getResult());
    }
}