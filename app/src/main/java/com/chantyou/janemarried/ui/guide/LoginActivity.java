package com.chantyou.janemarried.ui.guide;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.user.UserLoginRunner;
import com.chantyou.janemarried.ui.base.ThreadAuthActivity;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.ui.main.MainTabActivity;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;

/**
 * Created by j_turn on 2015/12/19 22:56
 * Email：766082577@qq.com
 */
public class LoginActivity extends ThreadAuthActivity {

    @ViewInject(R.id.etPhone)
    private EditText etPhone;
    @ViewInject(R.id.etPassword)
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle arg0) {
        mSwipeBackFinish = false;
        super.onCreate(arg0);
        setContentView(R.layout.activity_login);
        addAndroidEventListener(XEventCode.EVENT_USER_LOGIN, this);

        registerEditTextInputManager(etPhone);
        registerEditTextInputManager(etPassword);
    }

    @Override
    protected void init() {
        super.init();

        autoLogin();
    }

    protected void autoLogin() {

        String[] user = AppAndroid.getUserInfo();
        if(user != null && !TextUtils.isEmpty(user[1])) {
            showProgressDialog("", "登陆...");
            if(Constants.isMobileUser(user[0])) {
                pushEventEx(false, null, new UserLoginRunner(user[1], user[2], "1"), this);
            } else {
                pushEventEx(false, null, new UserLoginRunner(user[1], user[2], user[0]), this);
            }
        }
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        toolbarAttribute.mHasNavigationIcon = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.EVENT_USER_LOGIN, this);

        unRegisterEditTextInputManager(etPhone);
        unRegisterEditTextInputManager(etPassword);
    }

    @OnClick({R.id.tvLogin, R.id.tvFindpwd, R.id.tvRegister, R.id.tlIcon1, R.id.tlIcon2, R.id.tlIcon3})
    public void onViClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogin:
                if(TextUtils.isEmpty(etPhone.getText().toString())) {
                    CustomToast.showWorningToast(this, "请输入您的手机号");
                } else if(etPhone.getText().toString().length() != 11) {
                    CustomToast.showWorningToast(this, "请输入正确的手机号");
                } else if(TextUtils.isEmpty(etPassword.getText().toString())) {
                    CustomToast.showWorningToast(this, "请输入登录密码");
                } else {
                    showProgressDialog("","正在登录...");
                    pushEventEx(false, "", new UserLoginRunner(etPhone.getText().toString(), etPassword.getText().toString()), this);
                }
                break;
            case R.id.tvFindpwd:
                launch(this, FindpasswordActivity.class);
                break;
            case R.id.tvRegister:
                launch(this, RegisterActivity.class);
                break;
            case R.id.tlIcon1:
                doLoginQQ();
                break;
            case R.id.tlIcon2:
                doLoginByWeChat();
                break;
            case R.id.tlIcon3:
                doLoginBySina();
                break;
            default:
                break;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_USER_LOGIN:
                doLoginAction(event, this, true);
                break;
            case XEventCode.EVENT_USER_LOGIN:
                finish();
                break;
        }
    }

    /**
     *
     * @param event    接口回掉
     * @param activity  发起页面
     * @param toMain  是否跳转到主页面
     * @return 验证通过（true） 否（false）
     */
    public static boolean doLoginAction(Event event, XBaseActivity activity, boolean toMain) {
        if(event.isSuccess()) {
            if(toMain) {
                MainTabActivity.launch(activity);
            }
            activity.runEvent(XEventCode.EVENT_USER_LOGIN);
            return  true;
        } else {
            CustomToast.showErrorToast(activity, event.getMessage("登录失败"));
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(LoginActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(LoginActivity.this);
    }

}
