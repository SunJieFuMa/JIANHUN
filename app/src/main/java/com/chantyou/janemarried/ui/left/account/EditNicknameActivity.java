package com.chantyou.janemarried.ui.left.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.ToastManager;
import com.mhh.lib.annotations.ViewInject;

/**
 * Created by j_turn on 2015/12/18.
 * Email 766082577@qq.com
 */
public class EditNicknameActivity extends XBaseActivity {

    public static final int RCODE = 24;

    @ViewInject(R.id.et)
    private EditText et;

    public static void launchForResult(Activity act, String txt) {
        Intent intent = new Intent(act, EditNicknameActivity.class);
        intent.putExtra("edittxt", txt);
        act.startActivityForResult(intent, RCODE);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_editnickname);
    }

    @Override
    protected void init() {
        super.init();

        et.setText(getIntent().getStringExtra("edittxt"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main_menu) {
            if(TextUtils.isEmpty(et.getText().toString())){
                ToastManager.getInstance(EditNicknameActivity.this).show("请输入昵称");
                return false;
            }
            Intent intent = new Intent();
            intent.putExtra("returntxt", et.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.font_save);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(EditNicknameActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(EditNicknameActivity.this);
    }
}
