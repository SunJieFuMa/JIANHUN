package com.chantyou.janemarried.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;

import com.chantyou.janemarried.ui.guide.LoginActivity;
import com.lib.mark.core.Event;
import com.mhh.lib.SwipBaseActivity;
import com.mhh.lib.utils.ExitUtil;
import com.mhh.lib.utils.JsonUtil;

import org.json.JSONObject;

import java.util.Map;

public abstract class XBaseActivity extends SwipBaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if(!event.isSuccess()) {
            Object o = event.getReturnParamsAtIndex(0);
            if(o != null) {
                if(o instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) o;
                    if("-1".equalsIgnoreCase(JsonUtil.getItemString(map, "access_token"))
                            &&"未登录（无此令牌）".equalsIgnoreCase(JsonUtil.getItemString(map,"msg"))) {
                        showReLogin();
                    }
                } else if(o instanceof JSONObject) {
                    JSONObject json = (JSONObject) o;
                    if(json.has("access_token") && "-1".equalsIgnoreCase(json.optString("access_token"))
                            &&"未登录（无此令牌）".equalsIgnoreCase(json.optString("msg"))) {
                        showReLogin();
                    }
                }
            }
        }
    }

    private void showReLogin() {
        showYesOrNoDialog("提示", "确定", null, "访问令牌已过期，请重新登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExitUtil.getInstance().finishAll();
                launch(XBaseActivity.this, LoginActivity.class);
            }
        });
    }

}
