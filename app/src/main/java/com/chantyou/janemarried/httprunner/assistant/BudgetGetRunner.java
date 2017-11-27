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

import static com.chantyou.janemarried.config.UrlConfig.BUDGET_GET;

/**
 * Created by j_turn on 2016/4/6.
 * Email 766082577@qq.com
 */
public class BudgetGetRunner extends HttpRunner {

    private static final String url = BUDGET_GET;

    public BudgetGetRunner(Object... params) {
        super(XEventCode.HTTP_BUDGET_GET, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }
}
