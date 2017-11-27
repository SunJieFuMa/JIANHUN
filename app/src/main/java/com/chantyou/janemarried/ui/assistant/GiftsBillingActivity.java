package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.GiftsQueryAllRunner;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/1/24 18:17
 * Email：766082577@qq.com
 */
public class GiftsBillingActivity extends PullrefreshBottomloadActivity {

    @ViewInject(R.id.tvTotalNum)
    private TextView tvTotalNum;
    @ViewInject(R.id.tvTotalMoney)
    private TextView tvTotalMoney;

    private GiftsItemAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_giftsbilling;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);
    }

    @Override
    protected void init() {
        super.init();

        addAndroidEventListener(XEventCode.HTTP_GIFTS_ADDONE, this);
        addAndroidEventListener(XEventCode.HTTP_GIFTS_DELONE, this);

        onPullDown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.HTTP_GIFTS_ADDONE, this);
        removeEventListener(XEventCode.HTTP_GIFTS_DELONE, this);
    }

    @Override
    protected void setupRecyclerView() {
        mAdapter = new GiftsItemAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), mAdapter, RecyclerMode.NONE);
    }

    @Override
    public void onPullDown() {
        pushEvent(new GiftsQueryAllRunner());
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
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
                launch(this, GiftsBillingAddActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_GIFTS_QUERYALL:
                    if(event.isSuccess()) {
                        Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                        tvTotalNum.setText(String.format(getString(R.string.fmt_total_num), JsonUtil.getItemInt(map, "sum")+""));
                        tvTotalMoney.setText(String.format(getString(R.string.fmt_total_money), JsonUtil.parseDouble(map.get("sumMoney")+"")));
                        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("cashgiftlist");
                        mAdapter.setData(list);
                    } else {
                        mAdapter.setData(null);
//                        CustomToast.showErrorToast(this, event.getMessage("查询失败"));
                    }
                break;
            case XEventCode.HTTP_GIFTS_ADDONE:
            case XEventCode.HTTP_GIFTS_DELONE:
                if(event.isSuccess()) {
                    onPullDown();
                }
                break;
        }
    }

    private static class GiftsItemAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

        private View.OnLongClickListener longClickListener;

        public GiftsItemAdapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflateView(parent, R.layout.adapter_giftsitem));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            Map<String, Object> map = getValueAt(position);
            vh.map = map;
            vh.view.setTag(map);
            vh.tvName.setText(JsonUtil.getItemString(map, "name"));
            vh.tvMoney.setText(String.format(vh.tvMoney.getResources().getString(R.string.fmt_money),
                    JsonUtil.parseDouble(map.get("amountOfMoney"))+""));
        }

        private static class ViewHolder extends RecyclerView.ViewHolder {

            View view;
            TextView tvName, tvMoney;
            Map<String, Object> map;

            public ViewHolder(View itemView) {
                super(itemView);
                this.view = itemView;
                tvName = get(view, R.id.tvName);
                tvMoney = get(view, R.id.tvMoney);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(map != null) {
                            GiftsBillingInfoActivity.launch(v.getContext(), map);
                        }
                    }
                });
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(GiftsBillingActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(GiftsBillingActivity.this);
    }
}
