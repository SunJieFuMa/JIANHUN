package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import static com.chantyou.janemarried.config.UrlConfig.PCHLIST_ALL;

/**
 * Created by j_turn on 2016/2/2 00:49
 * Email：766082577@qq.com
 */
public class PchListAllRunner extends HttpRunner {

    private static final String url = PCHLIST_ALL;

    public PchListAllRunner(Object... params) {
        super(XEventCode.HTTP_PCHLIST_ALL, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        doDefParams(ev, response.getResult());
    }
}
