package com.chantyou.janemarried.httprunner.topic;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.TOPIC_DELCOLLECT;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_TOPIC_DELCOLLECT;

/**
 * Created by j_turn on 2016/3/31.
 * Email 766082577@qq.com
 */
public class TopicDelCollectRunner extends HttpRunner {

    private static final String url = TOPIC_DELCOLLECT;

    public TopicDelCollectRunner(Object... params) {
        super(HTTP_TOPIC_DELCOLLECT, url, params);
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
