package com.chantyou.janemarried.ui.guide;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.user.GetCodeRunner;
import com.chantyou.janemarried.httprunner.user.UserFindPwdRunner;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by j_turn on 2015/12/19.
 * Email 766082577@qq.com
 */
public class FindpasswordActivity extends XBaseActivity {

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
        setContentView(R.layout.activity_findpassword);

        registerEditTextInputManager(etPhone);
        registerEditTextInputManager(etRecode);
        registerEditTextInputManager(etNewPassword);
        registerEditTextInputManager(etRNewPassword);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            if(eventHandler == null) {
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
                clearTimer();
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
        if(TextUtils.isEmpty(smscode)) {
            CustomToast.showWorningToast(this, "请输入验证码");
            return;
        }
        clearTimer();
        showProgressDialog(null, "正在找回...");
        pushEventEx(false, null, new UserFindPwdRunner(phone, pswd, repswd, smscode), this);
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
                        if(tvSendCode != null) {
                            tvSendCode.setEnabled(false);
                            recLen--;
                            tvSendCode.setText(String.format("%1$ds",recLen));
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

    @OnClick({R.id.tvSubmit, R.id.tvGetRcode})
    public void onViClick(View v) {
        switch (v.getId()) {
            case R.id.tvGetRcode:
                String etPhoneNum = etPhone.getText().toString().replace(" ", "");
                if (etPhoneNum.length() != 11) {
                    CustomToast.showWorningToast(this, getText(R.string.input_phone_right).toString());
                } else {
//                    tvSendCode.setClickable(false);
//                    initTimer();
//                    timer.schedule(task, 1000, 1000);
//                    SMSSDK.getVerificationCode("86", etPhoneNum);

                    tvSendCode.setClickable(false);
                    pushEvent(new GetCodeRunner(etPhoneNum, "2"));
                }
                break;
            case R.id.tvSubmit:
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
            default:
                break;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_USER_FINDPWD:
                if(event.isSuccess()) {
                    showYesOrNoDialog("友情提示", "确定", "取消", "操作成功，请返回并重新登陆", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {

                                finish();
                            }
                        }
                    });
                } else {
                    clearTimer();
                    CustomToast.showErrorToast(this, event.getMessage("操作失败"));
                }
                break;
            case XEventCode.EVENT_USER_LOGIN:
                finish();
                break;
            case XEventCode.SMS_CODE_EVENT:
                if(event.isSuccess()) {
                    initTimer();
                    timer.schedule(task, 1000, 1000);
                    CustomToast.showRightToast(this, event.getMessage("验证码获取成功"));
                } else {
                    clearTimer();
                    CustomToast.showWorningToast(this, event.getMessage("验证码获取失败"));
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(FindpasswordActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(FindpasswordActivity.this);
    }

}
