package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.PchListAllRunner;
import com.chantyou.janemarried.httprunner.assistant.PchListDelRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.framework.CustomToast;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.mhh.lib.utils.JsonUtil;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/2/2 00:27
 * Email：766082577@qq.com
 */
public class PchListActivity extends PullrefreshBottomloadActivity {

    private PchListAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        addAndroidEventListener(XEventCode.HTTP_PCHLIST_ADD, this);
        addAndroidEventListener(XEventCode.HTTP_PCHLIST_DEL, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.HTTP_PCHLIST_ADD, this);
        removeEventListener(XEventCode.HTTP_PCHLIST_DEL, this);
    }

    @Override
    protected void init() {
        super.init();
        findViewById(R.id.lv_root).setBackgroundColor(getResources().getColor(R.color.c_eeeeee));

        onPullDown();
    }

    @Override
    public void onPullDown() {
        super.onPullDown();
        pushEvent(new PchListAllRunner());
    }

    @Override
    protected void setupRecyclerView() {
        adapter = new PchListAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), adapter, RecyclerMode.TOP);
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
        item.setIcon(R.drawable.icon_plus);
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
                launch(this, PchListAddActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PCHLIST_ALL:
                onRefreshCompleted();
                if(event.isSuccess()) {
                    JSONObject object = (JSONObject) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) JsonUtil.jsonToList(object.optString("purchaseLists"));
                    adapter.setData(list);
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("获取异常"));
                }
                break;
            case XEventCode.HTTP_PCHLIST_ADD:
                if(event.isSuccess()) {
                    onPullDown();
                }
                break;
            case XEventCode.HTTP_PCHLIST_DEL:
                if(event.isSuccess()) {
                    onPullDown();
                    CustomToast.showRightToast(this, Constants.getErrorMsg((JSONObject) event.getReturnParamsAtIndex(0), "已删除"));
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("删除异常"));
                }
                break;
        }
    }


    private class PchListAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

        public PchListAdapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflateView(parent, R.layout.adapter_pchlist));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            Map<String, Object> map = getValueAt(position);
            vh.map = map;
            //vh.ivDel.setImageResource(JsonUtil.getItemBoolean(map, "candel") ? R.drawable.icon_del : R.drawable.icon_mb);
            if(JsonUtil.getItemBoolean(map, "candel")){
                vh.ivDel.setImageResource(R.drawable.icon_del);
            }

            if(map != null) {
                vh.tvName.setText(JsonUtil.getItemString(map, "name"));
                vh.tvIntro.setText(JsonUtil.getItemString(map, "Brand")+":"
                        +JsonUtil.getItemInt(map,"num")+"|"
                        +JsonUtil.getItemString(map, "tip"));
                vh.tvMoney.setText(String.format(vh.tvMoney.getResources().getString(R.string.fmt_money), JsonUtil.getItemString(map, "itemTotalPrice")));
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvName;
            TextView tvIntro;
            TextView tvMoney;
            ImageView ivDel;
            ImageView ivEdit;
            Map<String, Object> map;

            public ViewHolder(View itemView) {
                super(itemView);

                tvName = get(itemView, R.id.tvName);
                tvIntro = get(itemView, R.id.tvIntro);
                tvMoney = get(itemView, R.id.tvMoney);
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
                            PchListInfoActivity.launch(v.getContext(), JsonUtil.objectToJson(map));
                        }
                        break;
                    case R.id.ivEdit:
                        if(map != null) {
                            PchListAddActivity.launch(v.getContext(), map);
                        }
                        break;
                    case R.id.ivDel:
                        if(map != null && JsonUtil.getItemBoolean(map, "candel") && map.containsKey("id")) {
                            showYesOrNoDialog("提示", "删除", "取消", "确定删除该清单", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == DialogInterface.BUTTON_POSITIVE) {
                                        showProgressDialog(null, "正在删除...");
                                        pushEventEx(false, null, new PchListDelRunner(JsonUtil.getItemInt(map, "id")), PchListActivity.this);
                                    }
                                }
                            });
                        }
                        break;
                }
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PchListActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PchListActivity.this);
    }
}
