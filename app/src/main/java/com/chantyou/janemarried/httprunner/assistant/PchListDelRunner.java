package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.PCHLIST_DEL;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_PCHLIST_DEL;

/**
 * Created by j_turn on 2016/2/4 22:26
 * Email：766082577@qq.com
 */
public class PchListDelRunner extends HttpRunner {

    private static final String url = PCHLIST_DEL;

    public PchListDelRunner(Object... params) {
        super(HTTP_PCHLIST_DEL, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("id", String.valueOf(ev.getParamsAtIndex(0))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefParams(ev, response.getResult());
    }
}
