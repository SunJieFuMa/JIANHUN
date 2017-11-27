package com.chantyou.janemarried.httprunner.home;

import android.text.TextUtils;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import org.json.JSONObject;

import java.util.LinkedList;


/**
 * Created by j_turn on 2016/3/12.
 * Email 766082577@qq.com
 */
public class ThreadAouthRunner extends HttpRunner {

    private static final String url = UrlConfig.USER_OAUTH_URL;

    public ThreadAouthRunner(Object... params) {
        super(XEventCode.HTTP_USER_AOUTH, url, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        LinkedList<NameValuePair> pList = postPublicPair();
        String  openid = (String) ev.getParamsAtIndex(0);
        String NickName = (String) ev.getParamsAtIndex(1);
        String Avatar = (String) ev.getParamsAtIndex(2);
        String openType = (String) ev.getParamsAtIndex(3);
        pList.add(new NameValuePair("openid", openid));
        pList.add(new NameValuePair("NickName", NickName));
        pList.add(new NameValuePair("Avatar", Avatar/*URLEncoder.encode(Avatar, "UTF-8")*/));
        pList.add(new NameValuePair("openType", openType));
//        pList.add(new NameValuePair("UserType", Constants.getUserType()));

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(url)
                .setMethod(HttpMethods.Post)
                .setHttpBody(new UrlEncodedFormBody(pList)));

        String result = response.getResult();
        if(!TextUtils.isEmpty(result)) {
            JSONObject jsonObject = new JSONObject(result);
            if (verifySuccess(jsonObject)) {
                JSONObject user = jsonObject.optJSONObject("data");
//                Constants.setUserInfo(user, "", Constants.getUserType());
            }
            doDefForm(ev, result);
        }
    }


//    {"success":true,"message":"登录成功","message_detail":null,"code":0,"data":{"UserId":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VySWQiOiI0NWNmODg5Ny1lMjE2LTQwNTMtODVhMC1hZDk2OTdiODI1NmUifQ.cmusgQrOKYYcQCUlEBPrzcR2b-tSdB4sWst4tjn70ZU","UserName":"mhh1","UserType":"Teacher","NickName":"mhh1","Mobile":"18505127387","Avatar":"","RegionCode":"d83ac73e-875c-4347-aef2-b165f2806039","TodaySigned":false}}
}
