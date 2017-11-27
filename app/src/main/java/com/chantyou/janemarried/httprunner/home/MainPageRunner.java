package com.chantyou.janemarried.httprunner.home;

import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import java.util.LinkedList;

import static com.chantyou.janemarried.framework.XEventCode.HTTP_HOME_PAGE;

/**
 * Created by j_turn on 2016/3/15.
 * Email 766082577@qq.com
 */
public class MainPageRunner extends HttpRunner {

    private static final String url = UrlConfig.HOME_PAGE;

    public MainPageRunner(Object... params) {
        super(HTTP_HOME_PAGE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.push(new NameValuePair("page", String.valueOf(ev.getParamsAtIndex(0))));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        String result = response.getResult();
        doDefForm(ev,result);
    }
}
