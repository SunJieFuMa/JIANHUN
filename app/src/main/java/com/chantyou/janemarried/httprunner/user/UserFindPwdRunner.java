package com.chantyou.janemarried.httprunner.user;

import android.text.TextUtils;

import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.mhh.lib.common.MD5Utils;

import org.json.JSONObject;

import java.util.LinkedList;

import static com.chantyou.janemarried.framework.XEventCode.HTTP_USER_FINDPWD;

/**
 * Created by j_turn on 2016/4/15.
 * Email 766082577@qq.com
 */
public class UserFindPwdRunner extends HttpRunner {

    private static final String url = UrlConfig.USER_FINDPWD_URL;

    public UserFindPwdRunner(Object... params) {
        super(HTTP_USER_FINDPWD, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        pList.add(new NameValuePair("phone", String.valueOf(ev.getParamsAtIndex(0))));
        pList.add(new NameValuePair("pswd", MD5Utils.md5((String) ev.getParamsAtIndex(1))));
        pList.add(new NameValuePair("repswd", MD5Utils.md5((String) ev.getParamsAtIndex(1))));
        pList.add(new NameValuePair("smscode",(String) ev.getParamsAtIndex(3)));
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        doDefForm(ev, response.getResult());
    }

//    {"return_code":"0","msg":"注册成功","access_token":"4r343hy5j3q78titor87disnhiu67vs8s62r"}{"return_code":"0","msg":"注册成功","access_token":"4r343hy5j3q78titor87disnhiu67vs8s62r"}
}
