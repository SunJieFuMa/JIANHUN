package com.chantyou.janemarried.httprunner.topic;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.TOPIC_COLLECT;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_TOPIC_COLLECT;

/**
 * Created by j_turn on 2016/3/30.
 * Email 766082577@qq.com
 */
public class TopicCollectRunner extends HttpRunner {

    private static final String url = TOPIC_COLLECT;

    public TopicCollectRunner(Object... params) {
        super(HTTP_TOPIC_COLLECT, url, params);
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
