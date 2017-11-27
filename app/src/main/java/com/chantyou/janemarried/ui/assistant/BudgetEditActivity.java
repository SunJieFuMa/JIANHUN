package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.BudgetAllRunner;
import com.chantyou.janemarried.httprunner.assistant.BudgetEditRunner;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/4/7.
 * Email 766082577@qq.com
 */
public class BudgetEditActivity extends PullrefreshBottomloadActivity {

    private  BudgetAdapter adapter;

    @Override
    protected void init() {
        super.init();
        pushEvent(new BudgetAllRunner());
    }

    @Override
    protected void setupRecyclerView() {
        adapter = new BudgetAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), adapter, RecyclerMode.NONE);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_BUDGET_ALL:
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String , Object>> list = (List<Map<String, Object>>) map.get("data");
                    adapter.setData(list);
                }
                break;
            case XEventCode.HTTP_BUDGET_EDIT:
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getParamsAtIndex(2);
                    int type = (int) event.getParamsAtIndex(1);
                    map.put("isadd", type);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private class BudgetAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

        public BudgetAdapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflateView(parent, R.layout.adapter_budget_item));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            Map<String, Object> map = getValueAt(position);
            vh.map = map;
            if(map != null) {
                vh.tvName.setText(JsonUtil.getItemString(map, "name"));
                int isadd = JsonUtil.getItemInt(map, "isadd");
                if(isadd == 1) {
                    vh.tv.setTextColor(getResources().getColor(R.color.gray));
                    vh.tv.setText("移除");
                } else {
                    vh.tv.setTextColor(getResources().getColor(R.color.c_ff3257));
                    vh.tv.setText("添加");
                }
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;
            TextView tv;
            Map<String, Object> map;
            public ViewHolder(View itemView) {
                super(itemView);
                tvName = get(itemView, R.id.tvName);
                tv = get(itemView, R.id.tv);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(map != null) {
                            int isadd = JsonUtil.getItemInt(map, "isadd");
                            if(isadd == 0) {
                                pushEvent(new BudgetEditRunner(JsonUtil.getItemInt(map, "id")+"", 1, map));
                            } else {
                                showYesOrNoDialog("提示", "移除", "取消", "是否移除该预算？", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which == DialogInterface.BUTTON_POSITIVE) {
                                            pushEvent(new BudgetEditRunner(JsonUtil.getItemInt(map, "id")+"", 0, map));
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(BudgetEditActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(BudgetEditActivity.this);
    }
}
