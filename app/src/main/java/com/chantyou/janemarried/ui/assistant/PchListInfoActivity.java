package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.PchListDelRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by j_turn on 2016/2/4 23:16
 * Email：766082577@qq.com
 */
public class PchListInfoActivity extends XBaseActivity {

    @ViewInject(R.id.etName)
    private TextView etName;
    @ViewInject(R.id.etMoney)
    private TextView etMoney;
    @ViewInject(R.id.etNum)
    private TextView etNum;
    @ViewInject(R.id.etPp)
    private TextView etPp;
    @ViewInject(R.id.etTip)
    private TextView etTip;
    @ViewInject(R.id.tvDel)
    private TextView tvDel;

    private long id;

    public static void launch(Context cxt, String info) {
        Intent intent = new Intent(cxt, PchListInfoActivity.class);
        intent.putExtra("info", info);
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_pchlistinfo);
    }

    @Override
    protected void init() {
        super.init();

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        try {
            Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(info);
            tvDel.setVisibility(JsonUtil.getItemBoolean(map, "candel") ? View.VISIBLE : View.GONE);
            id = JsonUtil.getItemInt(map, "id");
            etName.setText(JsonUtil.getItemString(map, "name"));
            etMoney.setText(JsonUtil.getItemString(map, "itemTotalPrice"));
            etNum.setText(JsonUtil.getItemString(map, "num"));
            etPp.setText(JsonUtil.getItemString(map, "Brand"));
            etTip.setText(JsonUtil.getItemString(map, "tip"));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @OnClick({R.id.tvDel})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.tvDel:
                showProgressDialog(null, "正在删除...");
                pushEventEx(false, null, new PchListDelRunner(id), this);
                break;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PCHLIST_DEL:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, Constants.getErrorMsg((JSONObject) event.getReturnParamsAtIndex(0), "已删除"));
                    finish();
                } else {
                    CustomToast.showErrorToast(this, "删除异常");
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PchListInfoActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PchListInfoActivity.this);
    }
}
