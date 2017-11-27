package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.SpeechAddRunner;
import com.chantyou.janemarried.listener.ZoomListenter;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;

import java.util.Map;

/**
 * Created by j_turn on 2016/2/23.
 * Email 766082577@qq.com
 */
public class SpeechAddActivity extends XBaseActivity {

    @ViewInject(R.id.etTitle)
    private EditText etTitle;
    @ViewInject(R.id.etContent)
    private EditText etContent;
    @ViewInject(R.id.tv_Content)
    private TextView tv_Content;
    @ViewInject(R.id.scrollView)
    private NestedScrollView scrollView;

    private String id;
    private boolean isRead;

    public static void launch(Context cxt, Map<String, Object> map, boolean isRead) {
        Intent intent = new Intent(cxt, SpeechAddActivity.class);
        intent.putExtra("id", map.containsKey("id") ? JsonUtil.getItemInt(map, "id")+"" : "");
        intent.putExtra("title" , JsonUtil.getItemString(map, "title"));
        intent.putExtra("content" , JsonUtil.getItemString(map, "content"));
        intent.putExtra("isRead" , isRead);
        cxt.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_speechadd);

        registerEditTextInputManager(etTitle);
        registerEditTextInputManager(etContent);

    }

    @Override
    protected void init() {
        super.init();
        id = getIntent().getStringExtra("id");
        isRead = getIntent().getBooleanExtra("isRead", false);

        etTitle.setHint("请输入发言稿标题");
        etContent.setHint("请输入发言稿内容");

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        try {
            if(title != null) {
                etTitle.setText(Html.fromHtml(title));
                etTitle.setSelection(title.length());
            }
            if(content != null) {
                etContent.setText(Html.fromHtml(content));
                etContent.setSelection(content.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isRead) {
//            Toast.makeText(this, "您可以通过手指放大或缩小内容区域的文字~", Toast.LENGTH_SHORT).show();
            etTitle.setFocusable(false);
            etTitle.setEnabled(false);
            etContent.setVisibility(View.GONE);
            tv_Content.setVisibility(View.VISIBLE);
            tv_Content.setText(Html.fromHtml(content));
            /*
    到SP中寻找这个文字的大小，默认自己设置差不多就行
     */
            tv_Content.setTextSize(SharedPreferenceUtils.getFloat(this,"speech_textsize",16));
//            etContent.setFocusable(true);
//            etContent.setEnabled(true);
            tv_Content.setFocusable(true);
            tv_Content.setEnabled(true);
            tv_Content.setOnTouchListener(new ZoomListenter());//设置双指触摸实现文字放大缩小
        } else {
            tv_Content.setVisibility(View.GONE);
            //            etContent.setFocusable(true);
            //            etContent.setEnabled(true);
            etContent.setVisibility(View.VISIBLE);
            etTitle.setFocusable(true);
            etTitle.setEnabled(true);
            etContent.setFocusable(true);
            etContent.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEditTextInputManager(etTitle);
        unRegisterEditTextInputManager(etContent);
    }

    private void doSave() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if(TextUtils.isEmpty(title)) {
            CustomToast.showWorningToast(this, "请输入标题");
            return;
        }

        if(TextUtils.isEmpty(content)) {
            CustomToast.showWorningToast(this, "请输入内容");
            return;
        }

        if(id == null) {
            id = "";
        }

        pushEvent(new SpeechAddRunner(id, title, content));
    }

    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getIntent().getBooleanExtra("isRead", false)) {
            return true;
        }
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
            doSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SPEECH_ADD:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, "保存成功");
                    finish();
                } else {
                    CustomToast.showWorningToast(this, "操作失败");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(SpeechAddActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(SpeechAddActivity.this);
    }

}
