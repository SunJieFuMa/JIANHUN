package com.chantyou.janemarried.ui.assistant.wedding;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.MarryQueryAllRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.ViewUtils;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.mhh.lib.utils.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/1/23 23:39
 * Email：766082577@qq.com
 */
public class WeddingProcessActivity extends PullrefreshBottomloadActivity {

    private WeddingProcessAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        addAndroidEventListener(XEventCode.HTTP_MARRY_ADDPROCESS, this);
        addAndroidEventListener(XEventCode.HTTP_MARRY_DELONEPRO, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.HTTP_MARRY_ADDPROCESS, this);
        removeEventListener(XEventCode.HTTP_MARRY_DELONEPRO, this);
    }

    @Override
    protected void init() {
        super.init();

        onPullDown();
    }

    @Override
    protected void setupRecyclerView() {
        adapter = new WeddingProcessAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), adapter, RecyclerMode.TOP);
    }

    @Override
    public void onPullDown() {
        super.onPullDown();
        pushEvent(new MarryQueryAllRunner());
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
                    launch(this, AddWeddingProcessActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_MARRY_QUERYALLPRO:
                onRefreshCompleted();
                if(event.isSuccess()) {
                    JSONObject object = (JSONObject) event.getReturnParamsAtIndex(0);
                    adapter.setData((List<? extends Map<String, Object>>) JsonUtil.jsonToList(object.optJSONArray("allmyprocess").toString()));
                }
                break;
            case XEventCode.HTTP_MARRY_ADDPROCESS:
            case XEventCode.HTTP_MARRY_DELONEPRO:
                if(event.isSuccess()) {
                    onPullDown();
                }
                break;
        }
    }

    private class WeddingProcessAdapter extends BaseRecyclerViewAdapter<Map<String , Object>> {

//        private List<Map<String, Object>> mOpenList;

        public WeddingProcessAdapter(Context context) {
            super(context);
//            mOpenList = new ArrayList<>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflateView(parent, R.layout.adapter_weddingprocess));
        }

        @Override
        protected boolean isDefBg() {
            return false;
        }

        @Override
        public void setData(List<? extends Map<String, Object>> data) {
//            if(data != null && data.size() > 0 && mOpenList.size() == 0) {
//                mOpenList.add(data.get(0));
//            }
            super.setData(data);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            Map<String, Object> map = getValueAt(position);
            vh.map = map;
            vh.tvTitle.setTextColor(vh.tvTitle.getResources().getColor(JsonUtil.getItemBoolean(map, "candel") ? R.color.black : R.color.red));
            if(map != null) {
                if(JsonUtil.getItemBoolean(map, "candel")) {
                    vh.tvTitle.setText(JsonUtil.getItemString(map, "type"));
                } else {
                    vh.tvTitle.setText(JsonUtil.getItemString(map, "type")+"("+"模板"+")");
                }
                vh.tvTime.setText(JsonUtil.getItemString(map, "dateTime"));
                vh.tvThing.setText(JsonUtil.getItemString(map, "thing"));
                vh.tvPersons.setText(JsonUtil.getItemString(map, "persons"));
//                if(mOpenList.contains(map)) {
//                    vh.vView.setVisibility(View.VISIBLE);
//                    vh.tvTitle.setSelected(true);
//                } else {
//                    vh.vView.setVisibility(View.GONE);
//                    vh.tvTitle.setSelected(false);
//                }
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.tvTitle)
            TextView tvTitle;
            @ViewInject(R.id.tvTime)
            TextView tvTime;
            @ViewInject(R.id.tvThing)
            TextView tvThing;
            @ViewInject(R.id.tvPersons)
            TextView tvPersons;
            @ViewInject(R.id.vView)
            View vView;

            Map<String, Object> map;

            public ViewHolder(View itemView) {
                super(itemView);
                ViewUtils.inject(this, itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map != null) {
                            WeddingProcessInfoActivity.launch(v.getContext(), JsonUtil.objectToJson(map));
                        }
                    }
                });

//                tvTitle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(!mOpenList.contains(map)) {
//                            vView.setVisibility(View.VISIBLE);
//                            mOpenList.add(map);
//                            tvTitle.setSelected(true);
//                        } else {
//                            vView.setVisibility(View.GONE);
//                            mOpenList.remove(map);
//                            tvTitle.setSelected(false);
//                        }
//                    }
//                });
            }
        }
    }
}
