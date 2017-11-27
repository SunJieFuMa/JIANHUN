package com.chantyou.janemarried.httprunner.push;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.chantyou.janemarried.config.UrlConfig.USER_UPDATE_PUSHID;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_USER_UPDATE_PUSH;

/**
 * Created by j_turn on 2016/4/19.
 * Email 766082577@qq.com
 */
public class IgeXinPushRunner extends HttpRunner {

    private static final String url = USER_UPDATE_PUSHID;

    public IgeXinPushRunner(Object... params) {
        super(HTTP_USER_UPDATE_PUSH, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("pushId", (String) ev.getParamsAtIndex(0)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
//        if(ev.isSuccess()) {
//            Constants.setUserInfo((Map<String, Object>) ev.getReturnParamsAtIndex(0));
//        }
    }
}
