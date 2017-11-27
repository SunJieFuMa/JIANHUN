package com.chantyou.janemarried.httprunner.user;

import android.text.TextUtils;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.lib.mark.core.Event;

import org.json.JSONObject;

import java.util.LinkedList;

import static com.chantyou.janemarried.framework.XEventCode.HTTP_USER_SIGNOUT;

/**
 * Created by j_turn on 2016/1/24 23:38
 * Email：766082577@qq.com
 */
public class UserSignoutRunner extends HttpRunner {

    private static final String url = UrlConfig.USER_SIGNOUT_URL;

    public UserSignoutRunner(Object... params) {
        super(HTTP_USER_SIGNOUT, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        String result = response.getResult();
        if(!TextUtils.isEmpty(result)) {
            JSONObject jsonObject = new JSONObject(result);
            if(verifySuccess(jsonObject)) {
                AppAndroid.setAccessToken("", 0);
                AppAndroid.setUserInfo("", "", "");
            }
            doDefParams(ev, result);
        }
    }
}
