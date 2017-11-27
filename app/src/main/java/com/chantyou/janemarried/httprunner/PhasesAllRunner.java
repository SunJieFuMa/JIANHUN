package com.chantyou.janemarried.httprunner;

import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import static com.chantyou.janemarried.config.UrlConfig.PHASES_ALL;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_PHASES_ALL;

/**
 * Created by j_turn on 2016/2/20.
 * Email 766082577@qq.com
 */
public class PhasesAllRunner extends HttpRunner {

    private static final String url = PHASES_ALL;

    public PhasesAllRunner(Object... params) {
        super(HTTP_PHASES_ALL, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        doDefParams(ev, response.getResult());
    }

//    {"phases":"[{\"attentionThings\":\"借钱\",\"name\":\"婚礼筹备\",\"phaseId\":1}]","return_code":"0","msg":"查询成功"}
}
