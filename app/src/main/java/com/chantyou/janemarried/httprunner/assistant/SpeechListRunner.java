package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import static com.chantyou.janemarried.config.UrlConfig.SPEECH_LIST;

/**
 * Created by j_turn on 2016/4/1.
 * Email 766082577@qq.com
 */
public class SpeechListRunner extends HttpRunner {

    private static final String url = SPEECH_LIST;

    public SpeechListRunner(Object... params) {
        super(XEventCode.HTTP_SPEECH_LIST, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        doDefForm(ev, response.getResult());
    }
}
