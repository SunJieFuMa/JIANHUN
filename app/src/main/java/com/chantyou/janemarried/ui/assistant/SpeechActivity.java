package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.SpeechDelRunner;
import com.chantyou.janemarried.httprunner.assistant.SpeechListRunner;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/2/23.
 * Email 766082577@qq.com
 */
public class SpeechActivity extends PullrefreshBottomloadActivity {

    private SpeechAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        addAndroidEventListener(XEventCode.HTTP_SPEECH_ADD, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.HTTP_SPEECH_LIST, this);
    }

    @Override
    protected void init() {
        super.init();
        findViewById(R.id.lv_root).setBackgroundColor(getResources().getColor(R.color.c_eeeeee));

        onPullDown();
    }

    @Override
    protected void setupRecyclerView() {
        adapter = new SpeechAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), adapter, RecyclerMode.TOP);
    }

    @Override
    public void onPullDown() {
        pushEvent(new SpeechListRunner());
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
        item.setIcon(R.drawable.icon_plus);
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
           launch(this, SpeechAddActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SPEECH_LIST:
                onRefreshCompleted();
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("mySpeechs");
                    adapter.setData(list);
                } else {
                    adapter.setData(null);
                }
                break;
            case XEventCode.HTTP_SPEECH_ADD:
                if(event.isSuccess()) {
                    onPullDown();
                }
                break;
            case XEventCode.HTTP_SPEECH_DEL:
                if(event.isSuccess()) {
                    onPullDown();
                    CustomToast.showRightToast(this, "已删除");
                } else {
                    CustomToast.showWorningToast(this, "删除失败");
                }
                break;
        }
    }

    private class SpeechAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

        public SpeechAdapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflateView(parent, R.layout.adapter_speech));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            Map<String, Object> map = getValueAt(position);
            vh.map = map;
//            vh.ivDel.setImageResource(JsonUtil.getItemBoolean(map, "candel") ? R.drawable.icon_del : R.drawable.icon_mb);
            if(JsonUtil.getItemBoolean(map, "candel")){
                vh.ivDel.setImageResource(R.drawable.icon_del);
            }
            if(map != null) {
                vh.tvTitle.setText(Html.fromHtml(JsonUtil.getItemString(map, "title")));
                vh.tvContent.setText(Html.fromHtml(JsonUtil.getItemString(map, "content")));
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvTitle, tvContent;
            ImageView ivDel;
            ImageView ivEdit;
            Map<String, Object> map;
            public ViewHolder(View itemView) {
                super(itemView);
                tvTitle = get(itemView, R.id.tvTitle);
                tvContent = get(itemView, R.id.tvContent);
                ivDel = get(itemView, R.id.ivDel);
                ivEdit = get(itemView, R.id.ivEdit);

                get(itemView, R.id.view_item).setOnClickListener(this);
                ivDel.setOnClickListener(this);
                ivEdit.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.view_item:
                        if(map != null) {
                            SpeechAddActivity.launch(v.getContext(), map, true);
                        }
                        break;
                    case R.id.ivDel:
                        if(map != null && JsonUtil.getItemBoolean(map, "candel") && map.containsKey("id")) {
                            showYesOrNoDialog("提示", "删除", "取消", "确定删除该发言稿吗？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == DialogInterface.BUTTON_POSITIVE) {
                                        pushEvent(new SpeechDelRunner(JsonUtil.getItemInt(map, "id")));
                                    }
                                }
                            });
                        }
                        break;
                    case R.id.ivEdit:
                        if(map != null) {
                            SpeechAddActivity.launch(v.getContext(), map, false);
                        }
                        break;
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(SpeechActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(SpeechActivity.this);
    }
}
