package com.chantyou.janemarried.httprunner.assistant;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.SPEECH_ADD;

/**
 * Created by j_turn on 2016/4/1.
 * Email 766082577@qq.com
 */
public class SpeechAddRunner extends HttpRunner {

    private static final String url = SPEECH_ADD;

    public SpeechAddRunner(Object... params) {
        super(XEventCode.HTTP_SPEECH_ADD, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("id", String.valueOf(ev.getParamsAtIndex(0))));
        pList.add(new NameValuePair("title", String.valueOf(ev.getParamsAtIndex(1))));
        pList.add(new NameValuePair("content", String.valueOf(ev.getParamsAtIndex(2))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}