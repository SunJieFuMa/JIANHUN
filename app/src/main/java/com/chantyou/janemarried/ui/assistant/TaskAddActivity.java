package com.chantyou.janemarried.ui.assistant;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.base.BaseListAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.TaskAddListRunner;
import com.chantyou.janemarried.httprunner.assistant.TaskAddRunner;
import com.lib.mark.core.Event;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.widget.ListViewEx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2016/2/26.
 * Email 766082577@qq.com
 */
public class TaskAddActivity extends XBaseActivity {

    @ViewInject(R.id.tvAdd)
    private TextView tvAdd;
    @ViewInject(R.id.lv_refrecyclerview)
    private ListView mListView;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_taskadd);
    }

    @Override
    protected void init() {
        super.init();

        adapter = new TaskAdapter();
        mListView.setAdapter(adapter);

        pushEvent(new TaskAddListRunner());
    }

    @OnClick({R.id.tvAdd})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.tvAdd:
                if(v.isEnabled() && adapter.mSelectList != null && adapter.mSelectList.size() > 0) {
                    Collection<List<Map<String, Object>>> lists = adapter.mSelectList.values();
                    StringBuilder builder = new StringBuilder();
                    for(List<Map<String, Object>> list : lists) {
                        for(Map<String, Object> map : list) {
                            builder.append(JsonUtil.getItemInt(map, "id")).append(",");
                        }
                    }
                    if(builder.length() > 1) {
                        builder.deleteCharAt(builder.length() - 1);
                    }
                    pushEvent(new TaskAddRunner(builder.toString()));
                }
                break;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TASK_USERADDLIST:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> nodes = (List<Map<String, Object>>) map.get("nodes");
                    adapter.setData(nodes);
                } else {
                    adapter.setData(null);
                }
                break;
            case XEventCode.HTTP_TASK_USERADD:
                if(event.isSuccess()) {
                    finish();
                    CustomToast.showRightToast(this, "添加成功");
                } else {
                    CustomToast.showWorningToast(this, "添加失败");
                }
                break;
        }
    }

    private class TaskAdapter extends BaseListAdapter<Map<String, Object>> {

        private Map<Map<String, Object>, List<Map<String, Object>>> mSelectList;

        public TaskAdapter() {
            super();
            mSelectList = new HashMap<>();
        }

        @Override
        protected int getLayoutId() {
            return R.layout.adapter_taskadd;
        }

        @Override
        protected AbsViewHolder setViewHolder(View view) {
            return new ViewHolder(view);
        }

        private class ViewHolder extends AbsViewHolder implements View.OnClickListener {

            CheckBox cbx;
            ImageView ivOp;
            View view;
            Map<String, Object> map;

            ListViewEx list;
            MyTaskItemAdapter adapter;

            public ViewHolder(View itemView) {
                cbx = (CheckBox) itemView.findViewById(R.id.cbx);
                ivOp = (ImageView) itemView.findViewById(R.id.ivOp);
                view = itemView.findViewById(R.id.view_item);

                view.setOnClickListener(this);
                list = (ListViewEx) itemView.findViewById(R.id.lv_list);

                adapter = new MyTaskItemAdapter();
//                list.setLayoutManager(new LinearLayoutManager(tvTitle.getContext()));
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        List<Map<String, Object>> list = mSelectList.get(map);
                        if (list == null) {
                            list = new ArrayList<Map<String, Object>>();
                            mSelectList.put(map, list);
                        }
                        Map<String, Object> item = adapter.getItem(position);
                        if (list.contains(item)) {
                            list.remove(item);
                        } else {
                            list.add(item);
                        }
                        if (list.size() == 0) {
                            mSelectList.remove(map);
                        }
                        cbx.setChecked(mSelectList.containsKey(map) && mSelectList.get(map).size() == adapter.getCount());
                        adapter.pMap = map;
//                        notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                        tvAdd.setEnabled(mSelectList.size() > 0);
                    }
                });
            }

            @Override
            public void setValue(int position, Map<String, Object> item) {
                super.setValue(position, item);
                map = item;
                if (map != null) {
                    cbx.setText(JsonUtil.getItemString(map, "name"));
                    adapter.pMap = map;
                    List<Map<String, Object>> childNodes = (List<Map<String, Object>>) map.get("childNodes");
                    adapter.setData(childNodes);
                    list.setAdapter(adapter);
                    if(mSelectList.containsKey(map)) {
                        int size = childNodes == null ? 0 : childNodes.size();
                        cbx.setChecked(size == mSelectList.get(map).size());
                    } else {
                        cbx.setChecked(false);
                    }
//                    boolean iscolse = JsonUtil.getItemBoolean(map, "iscolse");
//                    ivOp.setImageResource(!iscolse ? R.drawable.icon_open : R.drawable.icon_close);
                    ivOp.setImageDrawable(null);
//                    list.setVisibility(!iscolse ? View.VISIBLE : View.GONE);
                    list.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onClick(View v) {
//                map.put("iscolse", !JsonUtil.getItemBoolean(map, "iscolse"));
//                boolean iscolse = JsonUtil.getItemBoolean(map, "iscolse");
//                ivOp.setImageResource(!iscolse ? R.drawable.icon_open : R.drawable.icon_close);
//                list.setVisibility(!iscolse ? View.VISIBLE : View.GONE);
                if(!mSelectList.containsKey(map)) {
                    List<Map<String, Object>> list = new ArrayList<>();
                    list.addAll((List<Map<String, Object>>) map.get("childNodes"));
                    mSelectList.put(map, list);
                } else {
                    List<Map<String, Object>> childNodes = (List<Map<String, Object>>) map.get("childNodes");
                    int size = childNodes == null ? 0 : childNodes.size();
                    if(size == mSelectList.get(map).size()) {
                        mSelectList.remove(map);
                    } else {
                        mSelectList.remove(map);
                        List<Map<String, Object>> list = new ArrayList<>();
                        list.addAll((List<Map<String, Object>>) map.get("childNodes"));
                        mSelectList.put(map, list);
                    }
                }
                tvAdd.setEnabled(mSelectList.size() > 0);
                notifyDataSetChanged();
            }
        }

        public boolean isSelect(Map<String, Object> pMap, Map<String, Object> map) {
            return mSelectList != null && pMap != null && map != null
                    && mSelectList.containsKey(pMap)
                    && mSelectList.get(pMap).indexOf(map) != -1;
        }
    }

    private class MyTaskItemAdapter extends BaseListAdapter<Map<String, Object>> {

        Map<String, Object> pMap;

        public void setData(List<Map<String, Object>> data) {
            clear();
            super.setData(data);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.adapter_taskadd_item;
        }

        @Override
        protected AbsViewHolder setViewHolder(View view) {
            return new ViewHolder(view);
        }

        private class ViewHolder extends AbsViewHolder {

            CheckBox cbx;
            Map<String, Object> map;

            public ViewHolder(View itemView) {
                cbx = (CheckBox) itemView.findViewById(R.id.cbx);
            }

            @Override
            public void setValue(int position, Map<String, Object> item) {
                super.setValue(position, item);
                this.map = item;
                if (map != null) {
                    cbx.setText(JsonUtil.getItemString(map, "title"));
                    cbx.setChecked(adapter.isSelect(pMap, map));
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(TaskAddActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(TaskAddActivity.this);
    }
}
