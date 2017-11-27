package com.chantyou.janemarried.ui.guide;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.user.GetCodeRunner;
import com.chantyou.janemarried.httprunner.user.UserLoginRunner;
import com.chantyou.janemarried.httprunner.user.UserRegRunner;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by j_turn on 2015/12/19 22:37
 * Email：766082577@qq.com
 */
public class RegisterActivity extends XBaseActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @ViewInject(R.id.etPhone)
    private EditText etPhone;
    @ViewInject(R.id.etRecode)
    private EditText etRecode;
    @ViewInject(R.id.etNewPassword)
    private EditText etNewPassword;
    @ViewInject(R.id.etRNewPassword)
    private EditText etRNewPassword;
    @ViewInject(R.id.tvGetRcode)
    private TextView tvSendCode;
    @ViewInject(R.id.tvtk)
    private TextView tvtk;

    // 填写从短信SDK应用后台注册得到的APPKEY
    private static final String APPKEY = "f13402aad97a";

    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static final String APPSECRET = "0acea055959b955cfb1a5107ad88fd07";

    EventHandler eventHandler = null;

    private Timer timer;
    private int recLen = 60;

    @Override
    protected void onCreate(Bundle arg0) {
        mSwipeBackFinish = false;
        super.onCreate(arg0);
        setContentView(R.layout.activity_register);
        addAndroidEventListener(XEventCode.EVENT_USER_LOGIN, this);

        registerEditTextInputManager(etPhone);
        registerEditTextInputManager(etRecode);
        registerEditTextInputManager(etNewPassword);
        registerEditTextInputManager(etRNewPassword);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.EVENT_USER_LOGIN, this);

        unRegisterEditTextInputManager(etPhone);
        unRegisterEditTextInputManager(etRecode);
        unRegisterEditTextInputManager(etNewPassword);
        unRegisterEditTextInputManager(etRNewPassword);
    }

    @Override
    protected void init() {
        super.init();
        initSDK();
    }

    private void initSDK() {
        try{
            // 初始化短信SDK
            SMSSDK.initSDK(this, APPKEY, APPSECRET);
            if (eventHandler == null) {
                eventHandler = new EventHandler() {
                    public void afterEvent(final int event, final int result, final Object data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSmsComplete(event, result, data);
                            }
                        });
                    }
                };
            }
            // 注册回调监听接口
            SMSSDK.registerEventHandler(eventHandler);
        }catch (Exception e){

        }

    }

    private void onSmsComplete(int event, int result, Object data) {
        if (result == SMSSDK.RESULT_COMPLETE) {
            //回调完成
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                doRegister();
            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码成功
                CustomToast.showRightToast(this, getString(R.string.send_code_finish));
//            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表
            }
        } else {
            dismissProgressDialog();
            ((Throwable) data).printStackTrace();
            clearTimer();
            CustomToast.showErrorToast(this, getString(R.string.code_error));
        }
    }

    private void doRegister() {
        String phone = etPhone.getText().toString().trim();
        String pswd = etNewPassword.getText().toString().trim();
        String repswd = etRNewPassword.getText().toString().trim();
        String smscode = etRecode.getText().toString().trim();
        if (TextUtils.isEmpty(smscode)) {
            CustomToast.showWorningToast(this, "请输入验证码");
            return;
        }
        clearTimer();
        showProgressDialog(null, "正在注册...");
        pushEventEx(false, null, new UserRegRunner(phone, pswd, repswd, smscode), this);
    }

    private void clearTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null)
            timer.cancel();
        timer = null;
        recLen = 60;
        tvSendCode.setClickable(true);
        tvSendCode.setEnabled(true);
        tvSendCode.setText(R.string.send_againt);
    }

    private void initTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        if (tvSendCode != null) {
                            tvSendCode.setEnabled(false);
                            recLen--;
                            tvSendCode.setText(String.format("%1$ds", recLen));
                            if (recLen <= 0) {
                                clearTimer();
                            }
                        }
                    }
                });
            }
        };
    }

    TimerTask task;

    @OnClick({R.id.tvSubmit, R.id.tvGetRcode, R.id.tvtk})
    public void onViClick(View v) {
        if (Constants.DEBUG) {
            Log.v(TAG, "onViClick()");
        }

        switch (v.getId()) {
            case R.id.tvGetRcode://获取验证码按钮
                String etPhoneNum = etPhone.getText().toString().replace(" ", "");
                if (etPhoneNum.length() != 11) {
                    CustomToast.showWorningToast(this, getText(R.string.input_phone_right).toString());
                } else {
                    tvSendCode.setClickable(false);
//                    initTimer();
//                    timer.schedule(task, 1000, 1000);
//                    SMSSDK.getVerificationCode("86", etPhoneNum);
                    pushEvent(new GetCodeRunner(etPhoneNum, "1"));
                }
                break;
            case R.id.tvSubmit://点击立即注册按钮
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    CustomToast.showWorningToast(this, getString(R.string.input_phone));
                } else if (etPhone.getText().toString().replace(" ", "").length() != 11) {
                    CustomToast.showWorningToast(this, getString(R.string.input_phone_right));
                } else if (TextUtils.isEmpty(etRecode.getText())) {
                    CustomToast.showWorningToast(this, getString(R.string.input_code));
                } else if (TextUtils.isEmpty(etNewPassword.getText().toString())) {
                    CustomToast.showWorningToast(this, getString(R.string.input_password));
                } else if (etNewPassword.getText().toString().length() < 6 || etNewPassword.getText().toString().length() > 20) {
                    CustomToast.showWorningToast(this, getString(R.string.password_6_20));
                } else if (TextUtils.isEmpty(etRNewPassword.getText().toString())) {
                    CustomToast.showWorningToast(this, getString(R.string.input_rpassword));
                } else if (!etNewPassword.getText().toString().equals(etRNewPassword.getText().toString())) {
                    CustomToast.showWorningToast(this, "两次密码输入不一致");
                } else {
//                    SMSSDK.submitVerificationCode("86", etPhone.getText().toString(), etRecode.getText().toString());
                    doRegister();
                }
                break;

            case R.id.tvtk:
                System.out.println("协议");
                launch(this, RegisterAgreementActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_USER_REG:
                if (event.isSuccess()) {
                    CustomToast.showRightToast(this, "注册成功");
                    showProgressDialog("", "正在登录...");
                    pushEventEx(false, "", new UserLoginRunner(event.getParamsAtIndex(0), event.getParamsAtIndex(1)), this);
                } else {
                    clearTimer();
                    CustomToast.showErrorToast(this, event.getMessage("注册失败"));
                }
                break;
            case XEventCode.HTTP_USER_LOGIN:
                LoginActivity.doLoginAction(event, this, true);
                break;
            case XEventCode.EVENT_USER_LOGIN:
                finish();
                break;
            case XEventCode.SMS_CODE_EVENT:
                if (event.isSuccess()) {
                    initTimer();
                    timer.schedule(task, 1000, 1000);
                    CustomToast.showRightToast(this, event.getMessage("验证码获取成功"));
                } else {
                    tvSendCode.setClickable(true);
                    clearTimer();
                    CustomToast.showWorningToast(this, event.getMessage("验证码获取失败"));
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(RegisterActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(RegisterActivity.this);
    }
}
