package com.chantyou.janemarried.httprunner.user;

import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.framework.XEventCode.HTTP_USER_REG2;

/**
 * Created by j_turn on 2016/4/18.
 * Email 766082577@qq.com
 */
public class UserThreadRegRunner extends HttpRunner {

    private static final String url = UrlConfig.USER_REG_URL;

    public UserThreadRegRunner(Object... params) {
        super(HTTP_USER_REG2, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        String  openid = (String) ev.getParamsAtIndex(0);
        String NickName = (String) ev.getParamsAtIndex(1);
        String Avatar = (String) ev.getParamsAtIndex(2);
        String type = (String) ev.getParamsAtIndex(3);
        pList.add(new NameValuePair("type", type));
        pList.add(new NameValuePair("phone", openid));
        pList.add(new NameValuePair("Avatar", Avatar));
        pList.add(new NameValuePair("NickName", NickName));
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        String result = response.getResult();
        doDefForm(ev, result);
    }
}
