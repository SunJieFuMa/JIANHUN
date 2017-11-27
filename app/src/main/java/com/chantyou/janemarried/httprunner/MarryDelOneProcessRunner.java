package com.chantyou.janemarried.httprunner;

import android.text.TextUtils;

import com.chantyou.janemarried.framework.XEventCode;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import org.json.JSONObject;

import java.util.LinkedList;

import static com.chantyou.janemarried.config.UrlConfig.MARRY_DELONEPROCESS;

/**
 * Created by j_turn on 2016/1/24 18:03
 * Email：766082577@qq.com
 */
public class MarryDelOneProcessRunner extends HttpRunner {

    private static final String url = MARRY_DELONEPROCESS;

    public MarryDelOneProcessRunner(Object... params) {
        super(XEventCode.HTTP_MARRY_DELONEPRO, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("processId", ev.getParamsAtIndex(0).toString()));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        String result = response.getResult();
        doDefForm(ev, result);
    }
}
