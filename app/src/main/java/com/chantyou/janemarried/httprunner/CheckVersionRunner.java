package com.chantyou.janemarried.httprunner;

import com.chantyou.janemarried.framework.XEventCode;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.VERSION_CHECK;

/**
 * Created by j_turn on 2016/4/26.
 * Email 766082577@qq.com
 */
//获取商铺列表
public class CheckVersionRunner extends JYHttpRunner {

    private static final String url = VERSION_CHECK;

    public CheckVersionRunner(Object... params) {
        super(XEventCode.HTTP_CHECK_VERSION, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("typeId", "1"));//1为Android 2为ios
        pList.add(new NameValuePair("currVersion", (String) ev.getParamsAtIndex(0)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doResult(ev, response.getResult());
    }
}
