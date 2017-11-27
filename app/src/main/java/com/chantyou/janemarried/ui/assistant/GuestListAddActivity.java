package com.chantyou.janemarried.ui.assistant;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.GuestListAddRunner;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.ui.pay.ContactsListActivity;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by j_turn on 2016/2/6 22:32
 * Email：766082577@qq.com
 */
public class GuestListAddActivity extends XBaseActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSION_CONTACTS_CODE = 1;
    @ViewInject(R.id.etName)
    private EditText etName;
    @ViewInject(R.id.etList)
    private EditText etList;
    @ViewInject(R.id.btn_contacts)
    private Button btn_contacts;

    Map<String, Object> map;

    public static void launch(Context cxt, Map<String, Object> info) {
        Intent intent = new Intent(cxt, GuestListAddActivity.class);
        intent.putExtra("guestInfo", JsonUtil.objectToJson(info));
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_guestlistadd);
        btn_contacts.setOnClickListener(this);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        map = (Map<String, Object>) JsonUtil.jsonToMap(intent.getStringExtra("guestInfo"));
        etName.setText(JsonUtil.getItemString(map, "deskName"));
        etList.setText(JsonUtil.getItemString(map, "persons"));
    }

    private void doSave() {
        String name = etName.getText().toString();
        String list = etList.getText().toString();
        if (TextUtils.isEmpty(name)) {
            CustomToast.showWorningToast(this, "请输入桌名");
            return;
        }
        if (TextUtils.isEmpty(list)) {
            CustomToast.showWorningToast(this, "请输入宾客信息");
            return;
        }
        String id = "";
        if (map != null) {
            id = JsonUtil.getItemInt(map, "id") + "";
        }
        showProgressDialog(null, "正在保存...");
        pushEventEx(false, null, new GuestListAddRunner(name, list, id), this);
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
        switch (item.getItemId()) {
            case R.id.main_menu:
                doSave();
                break;
        }
        return true;
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_GUESTLIST_ADD:
                if (event.isSuccess()) {
                    CustomToast.showRightToast(this, "保存成功");
                    finish();
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("保存异常"));
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(GuestListAddActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(GuestListAddActivity.this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_contacts) {
            //从通讯录中导入联系人,但是需要动态适配权限
            doRequestPermission();
        }
    }

    private void doRequestPermission() {
        //判断当前系统是否高于或等于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                //这里PERMISSION_GRANTED表示具有权限，PERMISSION_DENIED表示无权限
                //具有读取联系人权限权限
                //具体调用代码
                Intent intent = new Intent(this, ContactsListActivity.class);
                startActivityForResult(intent, 1);
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    //给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限打开
                    showMessageOKCancel("APP需要获取手机联系人的权限，否则将无法从通讯录中导入宾客信息",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(GuestListAddActivity.this,
                                            new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_PERMISSION_CONTACTS_CODE);
                                }
                            });
                    return;
                }
                ActivityCompat.requestPermissions(GuestListAddActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_PERMISSION_CONTACTS_CODE);
            }
        } else {
            //当前系统小于6.0
            Intent intent = new Intent(this, ContactsListActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", null)
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //联系人
        if (requestCode == REQUEST_PERMISSION_CONTACTS_CODE) {
            if (grantResults.length >= 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //读取联系人
                    Intent intent = new Intent(this, ContactsListActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    //不具有相关权限，给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限开启
                    //                    tishi("请去系统设置-应用管理里面把读取联系人权限开启");
                    tishi("可以到系统设置-应用管理里打开被禁止的权限");
                }
            }

        }
    }

    private void tishi(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    StringBuilder builder = new StringBuilder();
    ArrayList<String> get_contact = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                if (null != data) {
                    get_contact = data.getStringArrayListExtra("GET_CONTACT");
                }
                if (null != get_contact && get_contact.size() > 0) {
                    for (int i = 0; i < get_contact.size(); i++) {
                        builder.append(get_contact.get(i).toString());
                    }
                    String preTet = etList.getText().toString();
                    etList.setText(preTet + builder);
                } else {
                    //                    Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


}
