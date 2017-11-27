package com.chantyou.janemarried.utils;

import android.content.Context;
import android.text.TextUtils;

import com.mhh.lib.utils.JsonUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by j_turn on 2016/1/14.
 * Email 766082577@qq.com
 */
public class JYConstants {

    public static final boolean DEBUG = true;       // 是否调试

    private static final String HTTP_CODE = "status";
    private static final String HTTP_ERROR_MSG = "msg";
    public  static final int PAGE_SIZE = 10;

    public static final String QQ = "2";
    public static final String WeChat = "3";
    public static final String SINA = "4";

    public static String QQ_APPID = "1105153797";

    public static String SINA_REDIRECT_URL = "http://www.sina.com";

//    public static String TB_APPKEY = "1105153797";
//    public static String TB_SECRET = "1105153797";

    public static String WX_APP_ID = "wx4bb8476b9232acdc";
    public static String WX_APP_SECRET = "4465a0da28b5171aed422726533170f1";
    public static final String WX_SCOPE = "snsapi_userinfo";
    public static final String WX_STATE = "wechat_sdk_demo_test";

    public static boolean verifySuccess(JSONObject jsonObject) {
        return jsonObject.optInt(HTTP_CODE) == 1;
    }

    public static String getErrorMsg(JSONObject jsonObject, String defError) {
        String msg = jsonObject.optString(HTTP_ERROR_MSG);
        if(TextUtils.isEmpty(msg)) {
            msg = defError;
        }
        return msg;
    }

//    public static String[] PRODUCT = {"all", "hunsha", "hunxie", "lifu","peishi","hunfang"};

    public static String buildCardUrl(int uid, int pageId, int cid) {
        return "http://www.easymarrytec.com/MarryTrade/card.html?uid="+ uid+"&page="+pageId+"&cId="+cid;
    }

    public static String buildNewsUrl(int id) {
        return "http://www.easymarrytec.com/MarryTrade/news.html?id=" +id;
    }

    public static String buildShopUrl(int id) {
        return "http://www.easymarrytec.com/MarryTrade/news.html?id=" +id;
    }

    /**   用户信息   **/
    public static final String ACCESSTOKEN = "access_token";
    public static final String USER_ID = "u_id";

    public static final String APPID = "android";
    public static final String APPSECRET = "123";

    public static final String SU_PHOTO = "u_photo";
    public static final String SU_NAME = "u_name";
    public static final String SU_CITY = "u_city";
    public static final String SU_STATE = "u_state";
    public static final String SU_MATCHID = "u_matchid";
    public static final String SU_MARRYDATE = "u_marrydate";
    public static final String SU_ADDRESS = "u_address";
    public static final String SU_INTRODUCE = "u_introduce";

    public static boolean isMobileUser(String type) {
        return TextUtils.isEmpty(type) || "1".equals(type);
    }

    public static void setUserInfo(Map<String, Object> map) {
        if(map != null) {
            SPDBHelper spdbHelper = new SPDBHelper();
            String u_photo = JsonUtil.getItemString(map, "photo");
            String u_name = JsonUtil.getItemString(map, "nickname");
            String u_city = JsonUtil.getItemString(map, "city");
            String u_state = JsonUtil.getItemString(map, "state");
            String u_marrydate = JsonUtil.getItemString(map, "marryDay").split(" ")[0];
            String u_address = JsonUtil.getItemString(map, "address");
            String u_introduce = JsonUtil.getItemString(map, "introduce");
            Map<String , Object> match = (Map<String, Object>) map.get("matchid");
            String matchid = "";
            if(match != null) {
                if(TextUtils.isEmpty(u_photo)) {
                    u_photo = JsonUtil.getItemString(match, "photo");
                }
                if(TextUtils.isEmpty(u_name)) {
                    u_name = JsonUtil.getItemString(match, "nickname");
                }
                if(TextUtils.isEmpty(u_city)) {
                    u_city = JsonUtil.getItemString(match, "city");
                }
                if(TextUtils.isEmpty(u_state)) {
                    u_state = JsonUtil.getItemString(match, "state");
                }
                if(TextUtils.isEmpty(u_marrydate)) {
                    u_marrydate = JsonUtil.getItemString(map, "marryDay").split(" ")[0];
                }
                matchid = JsonUtil.getItemString(match, "phone");
            }
            spdbHelper.putString(SU_PHOTO, u_photo);
            spdbHelper.putString(SU_NAME, u_name);
            spdbHelper.putString(SU_CITY, u_city);
            spdbHelper.putString(SU_STATE, u_state);
            spdbHelper.putString(SU_MARRYDATE, u_marrydate);
            spdbHelper.putString(SU_MATCHID, matchid);
            spdbHelper.putString(SU_ADDRESS, u_address);
            spdbHelper.putString(SU_INTRODUCE, u_introduce);
        }
    }

    public static String getUserInfoByKey(String key) {
        SPDBHelper spdbHelper = new SPDBHelper();
        String result = spdbHelper.getString(key, "");
        return result;
    }

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }



    private static final String KEY_UID           = "uid";
    private static final String KEY_ACCESS_TOKEN  = "access_token";
    private static final String KEY_EXPIRES_IN    = "expires_in";
    private static final String KEY_REFRESH_TOKEN    = "refresh_token";

    /**
     * 保存 Token 对象到 SharedPreferences。
     *
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }
        SPDBHelper spdbHelper = new SPDBHelper();
        spdbHelper.putString(KEY_UID, token.getUid());
        spdbHelper.putString(KEY_ACCESS_TOKEN, token.getToken());
        spdbHelper.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
        spdbHelper.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
    }
}
