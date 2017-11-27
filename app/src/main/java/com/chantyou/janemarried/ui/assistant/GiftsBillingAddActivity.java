package com.chantyou.janemarried.ui.assistant;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.GiftsAddOneRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;

import org.json.JSONObject;

/**
 * Created by j_turn on 2016/1/24 18:33
 * Email：766082577@qq.com
 */
public class GiftsBillingAddActivity extends XBaseActivity {

    @ViewInject(R.id.etName)
    private EditText etName;
    @ViewInject(R.id.etMoney)
    private EditText etMoney;
    @ViewInject(R.id.etTip)
    private EditText etTip;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_giftsbillingadd);
    }

    private void doAdd() {
        String name = etName.getText().toString();
        String money = etMoney.getText().toString();
        String tip = etTip.getText().toString();
        if(TextUtils.isEmpty(name)) {
            CustomToast.showWorningToast(this,"请输入亲友姓名");
            return;
        }
        if(TextUtils.isEmpty(money)) {
            CustomToast.showWorningToast(this,"请输入亲友姓名");
            return;
        }
        showProgressDialog("添加礼金记账", "正在保存...");
        pushEventEx(false, null, new GiftsAddOneRunner(name, money, tip), this);
    }

    /**
     * Toolbar（右标题）菜单选项
     *
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
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu) {
            doAdd();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_GIFTS_ADDONE:
                    if(event.isSuccess()) {
                        JSONObject object = (JSONObject) event.getReturnParamsAtIndex(0);
                        if(Constants.verifySuccess(object)) {
                            CustomToast.showRightToast(this, "添加成功");
                            finish();
                        } else {
                            CustomToast.showWorningToast(this, Constants.getErrorMsg(object, "添加失败"));
                        }
                    } else {
                        CustomToast.showErrorToast(this, "添加异常");
                    }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(GiftsBillingAddActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(GiftsBillingAddActivity.this);
    }
}
