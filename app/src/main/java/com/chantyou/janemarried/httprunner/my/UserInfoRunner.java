package com.chantyou.janemarried.httprunner.my;

import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.Map;

import static com.chantyou.janemarried.config.UrlConfig.USER_INFO;
import static com.chantyou.janemarried.framework.XEventCode.HTTP_USER_INFO;

/**
 * Created by j_turn on 2016/4/3.
 * Email 766082577@qq.com
 */
public class UserInfoRunner extends HttpRunner {

    private static final String url = USER_INFO;

    public UserInfoRunner(Object... params) {
        super(HTTP_USER_INFO, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(postPublicPair())));

        doDefForm(ev, response.getResult());
        if(ev.isSuccess()) {
            Constants.setUserInfo((Map<String, Object>) ev.getReturnParamsAtIndex(0));
        }
    }
}
