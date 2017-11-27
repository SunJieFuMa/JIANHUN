package com.chantyou.janemarried.httprunner.topic;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.TOPIC_BEST;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_TOPIC_BEST;

/**
 * Created by j_turn on 2016/2/5 17:44
 * Email：766082577@qq.com
 */
public class TopicBestRunner extends HttpRunner {

    private static final String url = TOPIC_BEST;

    public TopicBestRunner(Object... params) {
        super(HTTP_TOPIC_BEST, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        int page = (int) ev.getParamsAtIndex(0);
        pList.add(new NameValuePair("pageCur", page + ""));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefParams(ev, response.getResult());
    }
}
