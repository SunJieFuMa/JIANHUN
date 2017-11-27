package com.chantyou.janemarried.httprunner.user;

import android.text.TextUtils;
import android.util.Log;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.mhh.lib.common.MD5Utils;
import com.mhh.lib.utils.JsonUtil;

import java.util.LinkedList;
import java.util.Map;

import static com.chantyou.janemarried.framework.XEventCode.HTTP_USER_LOGIN;

/**
 * Created by j_turn on 2016/1/18 22:39
 * Email：766082577@qq.com
 */
public class UserLoginRunner extends HttpRunner {

    private static final String url = UrlConfig.USER_LOGIN_URL;

    public UserLoginRunner(Object... params) {
        super(HTTP_USER_LOGIN, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        String phone = (String) ev.getParamsAtIndex(0);
        String pwd = (String) ev.getParamsAtIndex(1);
        String type = (String) ev.getParamsAtIndex(2);
        pList.add(new NameValuePair("phone", phone));
        if ("1".equals(type) || TextUtils.isEmpty(type)) {
            pList.add(new NameValuePair("type", "1"));
            pList.add(new NameValuePair("pswd", MD5Utils.md5(pwd)));
        } else {
            pList.add(new NameValuePair("type", type));
        }
        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        String result = response.getResult();
        if (Constants.DEBUG) {
            Log.v(TAG, "result = " + result);
        }

        doDefForm(ev, result);
        if (ev.isSuccess()) {
            Map<String, Object> map = (Map<String, Object>) ev.getReturnParamsAtIndex(0);
            AppAndroid.setAccessToken(JsonUtil.getItemString(map, "access_token"), JsonUtil.getItemInt(map, "uid"));
            AppAndroid.setUserInfo(type, phone, pwd);
            Constants.setUserInfo(map);
        }
    }

//    {"matchid":"","pwdExpire":"2062-03-04 16:12:09.0","logDate":"2016-02-02 00:06:04.0","return_code":"0","msg":"登录成功","access_token":"y3m2ks9olmdvwzmhuyo4lvdebkal0rakw37o"}
}
