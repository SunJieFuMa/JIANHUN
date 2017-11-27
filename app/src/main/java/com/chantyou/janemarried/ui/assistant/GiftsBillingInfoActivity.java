package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.GiftsDelOneRunner;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.widget.RippleView;

import java.util.Map;

/**
 * Created by j_turn on 2016/5/5.
 * Email 766082577@qq.com
 */
public class GiftsBillingInfoActivity extends XBaseActivity {

    @ViewInject(R.id.etName)
    private EditText etName;
    @ViewInject(R.id.etMoney)
    private EditText etMoney;
    @ViewInject(R.id.etTip)
    private EditText etTip;

    private int id;

    public static void launch(Context cxt, Map<String, Object> info) {
        Intent intent = new Intent(cxt, GiftsBillingInfoActivity.class);
        intent.putExtra("giftsInfo", JsonUtil.objectToJson(info));
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_giftsbillinginfo);
    }

    @Override
    protected void init() {
        super.init();
        etName.setKeyListener(null);
        etMoney.setKeyListener(null);
        etTip.setKeyListener(null);

        try {
            String giftsInfo = getIntent().getStringExtra("giftsInfo");
            Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(giftsInfo);
            id = JsonUtil.getItemInt(map, "id");
            etName.setText(JsonUtil.getItemString(map, "name"));
            etMoney.setText(JsonUtil.parseDouble(map.get("amountOfMoney"))+"");
            etTip.setText(JsonUtil.getItemString(map, "tip"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((RippleView) findViewById(R.id.vDel)).setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                showYesOrNoDialog(null, "删除", "取消", "确认删除这条记录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            pushEvent(new GiftsDelOneRunner(id + ""));
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_GIFTS_DELONE:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, event.getMessage("删除成功"));
                    finish();
                } else {
                    CustomToast.showWorningToast(this, event.getMessage("删除失败"));
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(GiftsBillingInfoActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(GiftsBillingInfoActivity.this);
    }
}
