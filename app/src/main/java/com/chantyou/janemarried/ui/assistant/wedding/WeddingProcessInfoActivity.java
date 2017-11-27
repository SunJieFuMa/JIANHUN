package com.chantyou.janemarried.ui.assistant.wedding;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.MarryDelOneProcessRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;

import java.util.Map;

/**
 * Created by j_turn on 2016/4/29.
 * Email 766082577@qq.com
 */
public class WeddingProcessInfoActivity extends AddWeddingProcessActivity {

    private TextView tvDel;

    public static void launch(Context cxt, String info) {
        Intent intent = new Intent(cxt, WeddingProcessInfoActivity.class);
        intent.putExtra("info", info);
        cxt.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weddingprocessinfo;
    }

    @Override
    protected void setView() {
        super.setView();
        tvDel = (TextView) findViewById(R.id.tvDel);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        try {
            Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(info);
            tvDel.setVisibility(JsonUtil.getItemBoolean(map, "candel") ? View.VISIBLE : View.GONE);
            processId = JsonUtil.getItemInt(map, "processId");
            tvProcess.setText(JsonUtil.getItemString(map, "type"));
            tvTime.setText(JsonUtil.getItemString(map, "dateTime"));
            etThing.setText(JsonUtil.getItemString(map, "thing"));
            etPerson.setText(JsonUtil.getItemString(map, "persons"));
        } catch (Exception e){
            e.printStackTrace();
        }

        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(null, "正在删除");
                pushEventEx(false, null, new MarryDelOneProcessRunner(processId), WeddingProcessInfoActivity.this);
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_MARRY_DELONEPRO:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, "删除成功");
                    finish();
                }
                break;
        }
    }
}
