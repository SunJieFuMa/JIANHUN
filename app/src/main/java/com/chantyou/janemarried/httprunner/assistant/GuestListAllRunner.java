package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.GUESTLIST_ALL;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_GUESTLIST_ALL;

/**
 * Created by j_turn on 2016/2/6 22:38
 * Email：766082577@qq.com
 */
public class GuestListAllRunner extends HttpRunner {

    private static final String url = GUESTLIST_ALL;

    public GuestListAllRunner(Object... params) {
        super(HTTP_GUESTLIST_ALL, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("name", (String) ev.getParamsAtIndex(0)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
