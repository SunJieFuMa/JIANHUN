package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.CARD_INFO;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_CARD_INFO;

/**
 * Created by j_turn on 2016/4/12.
 * Email 766082577@qq.com
 */
public class InviCardInfoRunner extends HttpRunner {

    private static final String url = CARD_INFO;

    public InviCardInfoRunner(Object... params) {
        super(HTTP_CARD_INFO, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));
        doDefForm(ev, response.getResult());
        System.out.println("微信请柬"+response.getResult());
    }
}
