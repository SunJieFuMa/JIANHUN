package com.chantyou.janemarried.ui.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.home.WeChartRunner;
import com.chantyou.janemarried.httprunner.user.UserLoginRunner;
import com.chantyou.janemarried.httprunner.user.UserThreadRegRunner;
import com.chantyou.janemarried.ui.base.weibo.User;
import com.chantyou.janemarried.ui.base.weibo.UsersAPI;
import com.lib.mark.core.Event;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by j_turn on 2016/3/4.
 * Email 766082577@qq.com
 */
public class ThreadAuthActivity extends XBaseActivity {

    public static Tencent mTencent;
    private static boolean isServerSideLogin = false;

    /**
     * 微博实例
     */
    private AuthInfo mAuthInfo;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle arg0) {
        mSwipeBackFinish = false;
        super.onCreate(arg0);

        addAndroidEventListener(XEventCode.EVENT_AUTH_CODE, this);
    }

    @Override
    protected void init() {
        super.init();
        mTencent = Tencent.createInstance(com.chantyou.janemarried.utils.Constants.QQ_APPID, this.getApplicationContext());
        // 创建微博实例
        //mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
        mAuthInfo = new AuthInfo(this, getString(R.string.sina_key),
                com.chantyou.janemarried.utils.Constants.SINA_REDIRECT_URL, "");
        mSsoHandler = new SsoHandler(this, mAuthInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.EVENT_AUTH_CODE, this);
    }

    private void loginBy(String openId, String nickName, String headImg, String type) {
        pushEvent(new UserThreadRegRunner(openId, nickName, headImg, type));
    }

    /*********************************** 微博 ***************************************/

    protected void doLoginBySina() {
        mSsoHandler.authorize(new AuthListener());
    }


    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                com.chantyou.janemarried.utils.Constants.writeAccessToken(ThreadAuthActivity.this, mAccessToken);
                UsersAPI mUsersAPI = new UsersAPI(ThreadAuthActivity.this, getString(R.string.sina_key), mAccessToken);
                long uid = Long.parseLong(mAccessToken.getUid());
                mUsersAPI.show(uid, mListener);
            } else {
                dismissProgressDialog();
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "授权失败";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                CustomToast.showWorningToast(ThreadAuthActivity.this, message);
            }
        }

        @Override
        public void onCancel() {
            CustomToast.showWorningToast(ThreadAuthActivity.this, "取消授权");
        }

        @Override
        public void onWeiboException(WeiboException e) {
            CustomToast.showErrorToast(ThreadAuthActivity.this, "Auth exception : " + e.getMessage());
        }
    }

    /**
     * 获取用户信息
     * 微博 OpenAPI 回调接口。
     */
    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            dismissProgressDialog();
            if (!TextUtils.isEmpty(response)) {
                // 调用 User#parse 将JSON串解析成User对象
                User user = User.parse(response);
                if (user != null) {
                    loginBy(user.idstr, user.screen_name, user.profile_image_url, com.chantyou.janemarried.utils.Constants.SINA);
                } else {
                    Toast.makeText(ThreadAuthActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            dismissProgressDialog();
            CustomToast.showErrorToast(ThreadAuthActivity.this, e.getMessage());
        }
    };

    /*********************************** 微博 ***************************************/

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            dismissProgressDialog();
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            final String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
                UserInfo userInfo = new UserInfo(this, mTencent.getQQToken());
                showXProgressDialog();
                userInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        dismissXProgressDialog();
                        JSONObject object = (JSONObject) o;
                        int ret = object.optInt("ret");
                        System.out.println("json=" + String.valueOf(object));
                        String nickName = object.optString("nickname");
                        String gender = object.optString("gender");
                        String figureurl = object.optString("figureurl_qq_2");
                        if (TextUtils.isEmpty(figureurl)) {
                            figureurl = object.optString("figureurl_qq_1");
                        }
                        loginBy(openId, nickName, figureurl, com.chantyou.janemarried.utils.Constants.QQ);
                    }

                    @Override
                    public void onError(UiError uiError) {
                        dismissXProgressDialog();
                    }

                    @Override
                    public void onCancel() {
                        dismissXProgressDialog();
                    }
                });
            }

        } catch (Exception e) {
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
//            {
//                "ret":0,
//                    "pay_token":"xxxxxxxxxxxxxxxx",
//                    "pf":"openmobile_android",
//                    "expires_in":"7776000",
//                    "openid":"xxxxxxxxxxxxxxxxxxx",
//                    "pfkey":"xxxxxxxxxxxxxxxxxxx",
//                    "msg":"sucess",
//                    "access_token":"xxxxxxxxxxxxxxxxxxxxx"
//            }
            initOpenidAndToken(values);
        }
    };

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            dismissProgressDialog();
            if (null == response) {
                Util.showResultDialog(ThreadAuthActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (jsonResponse.length() == 0) {
                Util.showResultDialog(ThreadAuthActivity.this, "返回为空", "登录失败");
                return;
            }
//            Util.showResultDialog(ThreadAuthActivity.this, response.toString(), "登录成功");
            // 有奖分享处理
//            handlePrizeShare();
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

            dismissProgressDialog();
        }

        @Override
        public void onError(UiError e) {
            dismissProgressDialog();
            Util.toastMessage(ThreadAuthActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            dismissProgressDialog();
            Util.toastMessage(ThreadAuthActivity.this, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }

    /**
     * QQ授权登陆
     */
    protected void doLoginQQ() {
        showProgressDialog(null, "正在请求QQ授权");
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
            isServerSideLogin = false;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            dismissProgressDialog();
            mTencent.logout(this);
        }
    }

    /*****************************************  微信 ********************************************
     *
     * @param event
     */


    /**
     * 微信登陆
     */
    protected void doLoginByWeChat() {
        if (isWxInstalled()) {
            showProgressDialog(null, "正在请求微信授权");
            SendAuth.Req req = new SendAuth.Req();
            req.scope = com.chantyou.janemarried.utils.Constants.WX_SCOPE;
            req.state = com.chantyou.janemarried.utils.Constants.WX_STATE;
            IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, com.chantyou.janemarried.utils.Constants.WX_APP_ID, true);
            mWxApi.registerApp(com.chantyou.janemarried.utils.Constants.WX_APP_ID);
            mWxApi.sendReq(req);
            Log.d("WXCHAT", " WXCHAT doLoginByWeChat=" + com.chantyou.janemarried.utils.Constants.WX_APP_ID);
        } else {
            showYesOrNoDialog("微信授权失败", "确定", null, "未安装微信客户端", null);
        }
    }

    private boolean isWxInstalled() {
        try {
            getPackageManager().getApplicationInfo("com.tencent.mm", PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 微信登陆
     *
     * @param resp
     */
    private void disposeWxAuthResp(com.tencent.mm.sdk.modelmsg.SendAuth.Resp resp) {
        if (resp == null || BaseResp.ErrCode.ERR_OK != resp.errCode || TextUtils.isEmpty(resp.code)) {
            String msg = (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) ? "用户取消" :
                    (resp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) ? "用户拒绝授权" : "获取授权信息失败";
            showYesOrNoDialog("微信", "重试", "取消", msg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        doLoginByWeChat();
                    }
                }
            });
            return;
        }
        pushEvent(new WeChartRunner(resp.code));
    }

    /*****************************************
     * 微信
     *********************************************/

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_USER_REG2:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    if(map != null && JsonUtil.getItemInt(map, "code") == 2) {
                        pushEvent(new UserLoginRunner(event.getParamsAtIndex(0),"", event.getParamsAtIndex(3)));
//                        CustomToast.showRightToast(this, event.getMessage("登陆成功"));
//                        MainTabActivity.launch(this);
//                        runEvent(XEventCode.EVENT_USER_LOGIN);
                    }
                    return;
                } else {
                    CustomToast.showWorningToast(this, event.getMessage("登陆失败，请重试"));
                }
                break;
            case XEventCode.EVENT_AUTH_CODE:
                if (event.isSuccess()) {
                    disposeWxAuthResp((SendAuth.Resp) event.getParamsAtIndex(1));
                }
                break;
            case XEventCode.HTTP_AUTH_WECHAT:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    if (map != null) {
                        String openid = JsonUtil.getItemString(map, "openid");
                        String nickname = JsonUtil.getItemString(map, "nickname");
                        String headimgurl = JsonUtil.getItemString(map, "headimgurl");
                        loginBy(openid, nickname, headimgurl, com.chantyou.janemarried.utils.Constants.WeChat);
                    }
                } else {
                    showYesOrNoDialog("微信", "确定", null, "验证失败", null);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ThreadAuthActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ThreadAuthActivity.this);
    }
}
