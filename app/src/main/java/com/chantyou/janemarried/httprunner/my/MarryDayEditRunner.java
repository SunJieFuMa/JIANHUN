package com.chantyou.janemarried.httprunner.my;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.multi.MultipartBody;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import static com.chantyou.janemarried.config.UrlConfig.USER_EDIT;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_MARRY_DAY_EDIT;

//修改婚期
public class MarryDayEditRunner extends HttpRunner {

    private static final String url = USER_EDIT;

    public MarryDayEditRunner(Object... params) {
        super(HTTP_MARRY_DAY_EDIT, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        MultipartBody pList = postPublicMultiPair();
        System.out.println("zhuwx:" + ev.getParamsAtIndex(0));
        String marryDay = (String) ev.getParamsAtIndex(0);
        pList.addPart(new StringPart("marryDay", marryDay));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(pList));

        doDefParams(ev, response.getResult());
    }
}
