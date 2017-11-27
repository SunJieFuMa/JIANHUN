package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.PchListAddRunner;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;

import java.util.Map;

/**
 * Created by j_turn on 2016/2/2 00:49
 * Email：766082577@qq.com
 */
public class PchListAddActivity extends XBaseActivity {

    @ViewInject(R.id.etName)
    private EditText etName;
    @ViewInject(R.id.etMoney)
    private EditText etMoney;
    @ViewInject(R.id.etNum)
    private EditText etNum;
    @ViewInject(R.id.etPp)
    private EditText etPp;
    @ViewInject(R.id.etTip)
    private EditText etTip;

    Map<String , Object> info;
    String id;

    public static void launch(Context cxt, Map<String, Object> map) {
        Intent intent = new Intent(cxt, PchListAddActivity.class);
        intent.putExtra("pchinfo", JsonUtil.objectToJson(map));
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        info = (Map<String, Object>) JsonUtil.jsonToMap(getIntent().getStringExtra("pchinfo"));
        setContentView(R.layout.activity_pchlistadd);
    }

    @Override
    protected void init() {
        super.init();
        if(info != null) {
            setTitle("修改清单");
            id = String.valueOf(JsonUtil.getItemInt(info, "id"));
            etName.setText(JsonUtil.getItemString(info, "name"));
            etMoney.setText(String.valueOf(JsonUtil.parseDouble(info.get("itemTotalPrice"))));
            etPp.setText(JsonUtil.getItemString(info, "Brand"));
            etNum.setText(String.valueOf(JsonUtil.getItemInt(info, "num")));
            etTip.setText(JsonUtil.getItemString(info, "tip"));
        }
    }

    private void doAddPch() {
        String name = etName.getText().toString();
        String money = etMoney.getText().toString();
        String brand = etPp.getText().toString();
        String num = etNum.getText().toString();
        String tip = etTip.getText().toString();

        if(TextUtils.isEmpty(name)) {
            CustomToast.showWorningToast(this, "请输入采购清单名称");
            return;
        }
        if(TextUtils.isEmpty(money)) {
            CustomToast.showWorningToast(this, "请输入总计");
            return;
        }
        if(TextUtils.isEmpty(num)) {
            CustomToast.showWorningToast(this, "请输入数量");
            return;
        }
        if(TextUtils.isEmpty(brand)) {
            CustomToast.showWorningToast(this, "请输入品牌");
            return;
        }
        if(TextUtils.isEmpty(tip)) {
//            CustomToast.showWorningToast(this, "请输入备注");
//            return;
            tip = "";
        }
        if(TextUtils.isEmpty(id)) {
            id = "";
        }

        showProgressDialog(null, "正在保存...");
        pushEventEx(false, null, new PchListAddRunner(id, name, money, brand, num, tip), this);
    }

    /**
     * Toolbar（右标题）菜单选项
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.font_save);
        return true;
    }

    /**
     * Toolbar 菜单点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                doAddPch();
                break;
        }
        return true;
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PCHLIST_ADD:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, event.getMessage("保存成功"));
                    finish();
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("保存失败"));
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PchListAddActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PchListAddActivity.this);
    }
}
