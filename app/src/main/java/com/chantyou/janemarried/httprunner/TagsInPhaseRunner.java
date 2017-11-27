package com.chantyou.janemarried.httprunner;

import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.TAGS_INPHASE;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_TAGS_INPHASE;

/**
 * Created by j_turn on 2016/2/20.
 * Email 766082577@qq.com
 */
public class TagsInPhaseRunner  extends HttpRunner {

    private static final String url = TAGS_INPHASE;

    public TagsInPhaseRunner(Object... params) {
        super(HTTP_TAGS_INPHASE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("phaseId", String.valueOf(ev.getParamsAtIndex(0))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefParams(ev, response.getResult());
    }

//    {"tags":"[{\"id\":1,\"name\":\"近两年\",\"phase\":\"婚礼筹备\"},{\"id\":2,\"name\":\"简婚\",\"phase\":\"婚礼筹备\"}]","return_code":"0","msg":"查询成功"}
}
