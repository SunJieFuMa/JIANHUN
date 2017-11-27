package com.chantyou.janemarried.httprunner.home;

import android.util.Log;

import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.mhh.lib.utils.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by j_turn on 2016/3/13.
 * Email 766082577@qq.com
 */
public class WeChartRunner extends HttpRunner {

    private static String getToken = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static String getInfo = "https://api.weixin.qq.com/sns/userinfo";

    public WeChartRunner(Object... params) {
        super(XEventCode.HTTP_AUTH_WECHAT, getToken, params);
    }

    @Override
    public void onEventRun(Event ev) throws Exception {
        String code = (String) ev.getParamsAtIndex(0);
        LinkedHashMap<String, String> pList = new LinkedHashMap<>();
        pList.put("code", code);
        pList.put("appid", Constants.WX_APP_ID);
        pList.put("secret", Constants.WX_APP_SECRET);
        pList.put("grant_type", "authorization_code");

        Response<String> response = getLiteHttp().executeOrThrow(new StringRequest(getToken)
                .setMethod(HttpMethods.Get)
                .setParamMap(pList));
        String result = response.getResult();
        Log.d("WXCHAT", "WXCHAT 111=" + result);
        Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(result);
        if(map.containsKey("errcode")) {
            ev.setSuccess(false);
            ev.setMessage(JsonUtil.getItemString(map, "errmsg"));
        } else if(map.containsKey("openid") && map.containsKey("access_token")) {
            final String openid = JsonUtil.getItemString(map, "openid");
            final String access_token = JsonUtil.getItemString(map, "access_token");
            pList = new LinkedHashMap<>();
            pList.put("openid", openid);
            pList.put("access_token", access_token);
            response = getLiteHttp().executeOrThrow(new StringRequest(getInfo)
                    .setMethod(HttpMethods.Get)
                    .setParamMap(pList));
            result = response.getResult();
            Log.d("WXCHAT", "WXCHAT 222=" + result);
            map = (Map<String, Object>) JsonUtil.jsonToMap(result);
            if(map.containsKey("errcode")) {
                ev.setSuccess(false);
                ev.setMessage(JsonUtil.getItemString(map, "errmsg"));
            } else if(map.containsKey("openid")) {
                ev.setSuccess(true);
                ev.addReturnParams(map);
            }
        }
    }
//    {"access_token":"OezXcEiiBSKSxW0eoylIeFWYAU38zppYf3ZBZCiysOtqBqObtNmKeyAM8b5puZcfrwulhUHETciJ9e-t1twNJm8Uxd52G9VxiIpTRtrif7ixNFJzScNoE9DZ756-lGXD46cutFazdfv_H_54CKTbig","expires_in":7200,"refresh_token":"OezXcEiiBSKSxW0eoylIeFWYAU38zppYf3ZBZCiysOtqBqObtNmKeyAM8b5puZcfR8kW0KRazNX5gaT1I4MDwWnThFCssXFpN_F5j6y4-m5QHiTQlAzZ_4C5UQ6z4CkgiDzUnOekdmXYAk5ytnOVVQ","openid":"o2BvtvnIuNCIrubjBpwSrxIfujuQ","scope":"snsapi_userinfo","unionid":"oT4KzuFpptffsG30HrTMJuGCbcFQ"}
//{"openid":"o2BvtvnIuNCIrubjBpwSrxIfujuQ","nickname":"五芒星","sex":1,"language":"zh_CN","city":"Suzhou","province":"Jiangsu","country":"CN","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/PiajxSqBRaEJ9QVOYtEQUicvd6gHUDQwOFdKM4ibabygQIicE4Ejh7iaVn5IP03vptIZa0jYqCrjAAIibUNkuicSfLZhA\/0","privilege":[],"unionid":"oT4KzuFpptffsG30HrTMJuGCbcFQ"}

}
