package com.chantyou.janemarried.httprunner.my;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import static com.chantyou.janemarried.config.UrlConfig.TOPIC_MYCOLLECT;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_TOPIC_MYCOLLECT;

/**
 * Created by j_turn on 2016/3/31.
 * Email 766082577@qq.com
 */
public class TopicMyFavoriteRunner extends HttpRunner {

    private static final String url = TOPIC_MYCOLLECT;

    public TopicMyFavoriteRunner(Object... params) {
        super(HTTP_TOPIC_MYCOLLECT, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        doDefForm(ev, response.getResult());
    }
}