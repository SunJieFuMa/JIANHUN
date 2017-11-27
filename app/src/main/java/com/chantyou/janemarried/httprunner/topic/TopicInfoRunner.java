package com.chantyou.janemarried.httprunner.topic;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.TOPIC_INFO;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_TOPIC_INFO;

/**
 * Created by j_turn on 2016/2/6 15:51
 * Email：766082577@qq.com
 */
public class TopicInfoRunner extends HttpRunner {

    private static final String url = TOPIC_INFO;

    public TopicInfoRunner(Object... params) {
        super(HTTP_TOPIC_INFO, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("topicId", String.valueOf(ev.getParamsAtIndex(0))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
