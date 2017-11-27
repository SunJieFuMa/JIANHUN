package com.chantyou.janemarried.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.utils.JYConstants;
import com.lib.mark.core.AsynEvent;
import com.lib.mark.core.Event;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.content.multi.MultipartBody;
import com.litesuits.http.request.content.multi.StringPart;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.utils.SystemUtils;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.chantyou.janemarried.utils.Constants.DEBUG;


/**
 * Created by j_turn on 2016/1/18 22:04
 * Email：766082577@qq.com
 */
public abstract class JYHttpRunner extends AsynEvent {

    public static final String TAG = JYHttpRunner.class.getSimpleName();

    private static LiteHttp liteHttp;
    private String httpUrl;

    public static LiteHttp getLiteHttp() {
        if (liteHttp == null) {
            HttpConfig config = new HttpConfig(AppAndroid.getApp()) // configuration quickly
                    .setDebugged(DEBUG)                   // log output when debugged
                    .setDetectNetwork(true)              // detect network before connect
                    .setDoStatistics(true)               // statistics of time and traffic
                    .setUserAgent("Mozilla/5.0 (...)")   // set custom User-Agent
                    .setForRetry(50000, false)
                    .setDefaultMaxRetryTimes(0)
                    .setTimeOut(10000, 10000);
//                    .setTimeOut(10000, 10000);
            liteHttp = LiteHttp.newApacheHttpClient(config);
        }
        return liteHttp;
    }

    public JYHttpRunner(int eventCode, String url, Object... params) {
        super(eventCode, url, params);
        httpUrl = url;
    }

    /**
     * 验证 接口返回
     *
     * @param jsonObject 返回的JSON 数据
     * @return 返回验证结果
     */
    protected boolean verifySuccess(JSONObject jsonObject) {
        return JYConstants.verifySuccess(jsonObject);
    }

    /**
     * 验证 接口返回
     *
     * @param map 返回的JSON 数据
     * @return 返回验证结果
     */
    protected boolean verifySuccess(Map<String, Object> map) {
        return map != null && map.containsKey("status") && JsonUtil.getItemInt(map, "status") ==1;
    }

    /**
     * 2016/03/06 修改默认解析方式返回值为Map
     *
     * @param ev
     * @param result
     * @throws Exception
     */
    protected void doDefForm(Event ev, String result) throws Exception {
        if (JYConstants.DEBUG) {
            Log.v("HttpRunner", "url=" + httpUrl);
            Log.d("HttpRunner", "zhuwx:" + httpUrl + "； result= " + result);
        }
        if (!TextUtils.isEmpty(result)) {
            Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(result);
            ev.setMessage(JsonUtil.getItemString(map, "msg"));
            ev.addReturnParams(map);
            if (verifySuccess(map)) {
                ev.setSuccess(true);
            }
        }
    }

    protected void doDefParams(Event ev, String result) throws Exception {
        if (JYConstants.DEBUG) {
            Log.v("HttpRunner", "url=" + httpUrl);
            Log.d("HttpRunner", "zhuwx:" + httpUrl + " ；result= " + result);
        }
        if (!TextUtils.isEmpty(result)) {
            JSONObject jsonObject = new JSONObject(result);
            ev.setMessage(jsonObject.optString("msg"));
            if (verifySuccess(jsonObject)) {
                ev.setSuccess(true);
                ev.addReturnParams(jsonObject);
            }
        }
    }

    protected void log(String log) {
        if (DEBUG) {
            Log.v("HttpRunner", log);
        }
    }

    protected LinkedHashMap<String, String> getPublicPair() {
        LinkedHashMap<String, String> pList = new LinkedHashMap<String, String>();
        String accessToken = AppAndroid.getAccessToken();
        if (!TextUtils.isEmpty(accessToken)) {
            pList.put("token", accessToken);
            log("token=" + accessToken);
        }
        pList.put("timestamp", System.currentTimeMillis() / 1000 + "");
        pList.put("locale", "zh-CN");
        pList.put("imei", SystemUtils.encryptBySHA1(SystemUtils.getDeviceUUID(AppAndroid.getApp())));
        pList.put("version", SystemUtils.getCurVersion(AppAndroid.getApp()));
        return pList;
    }

    protected LinkedList<NameValuePair> postPublicPair() {
        LinkedList<NameValuePair> pList = new LinkedList<NameValuePair>();
        String accessToken = AppAndroid.getAccessToken();
        if (!TextUtils.isEmpty(accessToken)) {
            pList.add(new NameValuePair("token", accessToken));
            log("token=" + accessToken);
        }
        pList.add(new NameValuePair("timestamp", System.currentTimeMillis() / 1000 + ""));
        pList.add(new NameValuePair("locale", "zh-CN"));
        pList.add(new NameValuePair("imei", SystemUtils.encryptBySHA1(SystemUtils.getDeviceUUID(AppAndroid.getApp()))));
        pList.add(new NameValuePair("version", SystemUtils.getCurVersion(AppAndroid.getApp())));
        return pList;
    }

    protected MultipartBody postPublicMultiPair() {
        MultipartBody body = new MultipartBody();
        String accessToken = AppAndroid.getAccessToken();
        if (!TextUtils.isEmpty(accessToken)) {
            body.addPart(new StringPart("token", accessToken));
            log("token=" + accessToken);
        }
        body.addPart(new StringPart("timestamp", System.currentTimeMillis() / 1000 + ""));
        body.addPart(new StringPart("locale", "zh-CN"));
        body.addPart(new StringPart("imei", SystemUtils.encryptBySHA1(SystemUtils.getDeviceUUID(AppAndroid.getApp()))));
        body.addPart(new StringPart("version", SystemUtils.getCurVersion(AppAndroid.getApp())));
        return body;
    }

    protected void doResult(Event ev, String result) throws Exception {
        if (JYConstants.DEBUG) {
            Log.v("HttpRunner", "url=" + httpUrl);
            Log.d("HttpRunner", "zhuwx:" + httpUrl + " ；result= " + result);
        }
        if (!TextUtils.isEmpty(result)) {
            JSONObject jsonObject = new JSONObject(result);
            ev.setMessage(jsonObject.optString("msg"));
            if (verifySuccess(jsonObject)) {
                ev.setSuccess(true);
                ev.addReturnParams(jsonObject);
            }
        }
    }
}
