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

import static com.chantyou.janemarried.config.UrlConfig.GIFTS_ADDMARK;

/**
 * Created by j_turn on 2016/1/24 20:21
 * Email：766082577@qq.com
 */
public class GiftsAddOneRunner extends HttpRunner {

    private static final String url = GIFTS_ADDMARK;

    public GiftsAddOneRunner(Object... params) {
        super(XEventCode.HTTP_GIFTS_ADDONE, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("Name", (String) ev.getParamsAtIndex(0)));
        pList.add(new NameValuePair("amountOfMoney", (String) ev.getParamsAtIndex(1)));
        pList.add(new NameValuePair("tip", (String) ev.getParamsAtIndex(2)));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        String result = response.getResult();
        if(!TextUtils.isEmpty(result)) {
            JSONObject jsonObject = new JSONObject(result);
            ev.setSuccess(true);
            ev.addReturnParams(jsonObject);
            if(verifySuccess(jsonObject)) {
            }
        }
    }
}
