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

import static com.chantyou.janemarried.config.UrlConfig.GIFTS_DELONE;

/**
 * Created by j_turn on 2016/1/25 22:51
 * Email：766082577@qq.com
 */
public class GiftsDelOneRunner extends HttpRunner {

    private static final String url = GIFTS_DELONE;

    public GiftsDelOneRunner(Object... params) {
        super(XEventCode.HTTP_GIFTS_DELONE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("id", (String) ev.getParamsAtIndex(0)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        String result = response.getResult();
        doDefForm(ev, result);
    }
}
