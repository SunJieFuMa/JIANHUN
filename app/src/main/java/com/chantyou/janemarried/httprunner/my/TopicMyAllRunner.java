package com.chantyou.janemarried.httprunner.my;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import static com.chantyou.janemarried.config.UrlConfig.TOPIC_MYALL;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_TOPIC_MYALL;

/**
 * Created by j_turn on 2016/2/6 14:51
 * Email：766082577@qq.com
 */
public class TopicMyAllRunner extends HttpRunner {

    private static final String url = TOPIC_MYALL;

    public TopicMyAllRunner(Object... params) {
        super(HTTP_TOPIC_MYALL, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        doDefForm(ev, response.getResult());
    }
}
