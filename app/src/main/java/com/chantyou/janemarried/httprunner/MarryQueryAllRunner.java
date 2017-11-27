package com.chantyou.janemarried.httprunner;

import com.chantyou.janemarried.framework.XEventCode;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import static com.chantyou.janemarried.config.UrlConfig.MARRY_ALLPROCESS;

/**
 * Created by j_turn on 2016/1/24 17:41
 * Email：766082577@qq.com
 */
public class MarryQueryAllRunner extends HttpRunner {

    private static final String url = MARRY_ALLPROCESS;

    public MarryQueryAllRunner(Object... params) {
        super(XEventCode.HTTP_MARRY_QUERYALLPRO, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        String result = response.getResult();

        doDefParams(ev,result);
    }

//    {"allmyprocess":[{"dateTime":"2016-01-25 16:20:32.0","processId":6,"type":"xinrenzhunbei","persons":"相聚阿萨莫阿","thing":"流程事宜\n"}],"return_code":"0","msg":"查询成功"}
}
