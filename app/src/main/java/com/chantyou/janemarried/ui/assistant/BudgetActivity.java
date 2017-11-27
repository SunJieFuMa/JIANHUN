package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.BudgetComputeRunner;
import com.chantyou.janemarried.httprunner.assistant.BudgetGetRunner;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.chantyou.janemarried.utils.SpUtils;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/4/7.
 * Email 766082577@qq.com
 */
public class BudgetActivity extends PullrefreshBottomloadActivity {

    @ViewInject(R.id.etMoney)
    private EditText etMoney;
    @ViewInject(R.id.etDNum)
    private EditText etDNum;

    private BudgetAdapter adapter;

    //    FrameLayout mChatView;
    private boolean computed;
    private List<Map<String, Object>> list;
    private boolean issave;//是否显示保存按钮

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_budget;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbarLayout.setTitleEnabled(false);
        addAndroidEventListener(XEventCode.HTTP_BUDGET_EDIT, this);

        registerEditTextInputManager(etMoney);
        registerEditTextInputManager(etDNum);
        issave = getIntent().getBooleanExtra("issave", false);//是否显示保存按钮
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.HTTP_BUDGET_EDIT, this);
        unRegisterEditTextInputManager(etMoney);
        unRegisterEditTextInputManager(etDNum);
    }

    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (issave) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            MenuItem item = menu.findItem(R.id.main_menu);
            item.setIcon(R.drawable.font_save);
        }
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
                saveBudget();
                break;
        }
        return true;
    }

    //
    private void saveBudget() {
        if (computed) {
            //保存婚礼预算
            String saveBudget = SpUtils.getString(this, "saveBudget", "");
            if (TextUtils.isEmpty(saveBudget)) {
                return;
            }
            System.out.println("saveBudget::::" + saveBudget);
            OkHttpUtils
                    .post()
                    .url(UrlConfig.BUDGET_SAVE)
                    .addParams("access_token", AppAndroid.getAccessToken())
                    .addParams("budgetTask", saveBudget)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(AppAndroid.getContext(), "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                            System.out.println("保存预算失败::" + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            System.out.println("保存预算成功::" + response);
                            setResult(RESULT_OK,new Intent());
                            finish();
                        }
                    });
        }
    }


    @Override
    protected void init() {
        super.init();

        findViewById(R.id.lv_root).setBackgroundColor(getResources().getColor(R.color.c_eeeeee));

        onPullDown();
    }

    //    String[] colors = new String[]{"#ff4444","#33b5e5","#ffbb33","#aa66cc","#00aaee","#99cc00"};
    //    private void setupChatView(List<Map<String, Object>> list) {
    //        mChatView.removeAllViews();
    //        if(list != null && list.size() > 0) {
    //            PieChartView pieView = new PieChartView(this);
    //            pieView.setChartRotation(270, true);
    //            PieChartData data = new PieChartData();
    //            data.setSlicesSpacing(0);
    //            List<SliceValue> values = new ArrayList<>();
    //            data.setValues(values);
    //            int i = 0;
    //            for(Map<String , Object> map : list) {
    //                values.add(new SliceValue((float) JsonUtil.parseDouble(map.get("money")), Color.parseColor(colors[i % 6])));
    //                i++;
    //            }
    ////            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(AppAndroid.dipToPixel(110), AppAndroid.dipToPixel(110));
    ////            pieView.setLayoutParams(params);
    ////            params.gravity = Gravity.CENTER;
    //            mChatView.addView(pieView, new FrameLayout.LayoutParams(AppAndroid.dipToPixel(110), AppAndroid.dipToPixel(110)));
    //
    //            pieView.setPieChartData(data);
    //        }
    //    }

    @Override
    public void onPullDown() {
        super.onPullDown();
        adapter.clear();
        pushEvent(new BudgetGetRunner());
    }

    @Override
    protected void setupRecyclerView() {
        adapter = new BudgetAdapter(this);
        //        mChatView = new FrameLayout(this);
        //        RefreshRecyclerAdapterManager manager =
        setupRecyclerView(new LinearLayoutManager(this), adapter, RecyclerMode.NONE);
        //        manager.addHeaderView(mChatView);
    }

    @OnClick({R.id.fab, R.id.tvCompute})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                launch(v.getContext(), BudgetEditActivity.class);
                break;
            case R.id.tvCompute://计算
                String money = etMoney.getText().toString();
                String num = etDNum.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    CustomToast.showWorningToast(this, "请输入预算金额");
                    return;
                }
                if (TextUtils.isEmpty(num)) {
                    CustomToast.showWorningToast(this, "请输入婚宴桌数");
                    return;
                }
                pushEvent(new BudgetComputeRunner(money, num));
                break;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_BUDGET_GET:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
                    adapter.setData(list);
                }
                break;
            case XEventCode.HTTP_BUDGET_EDIT:
                if (event.isSuccess()) {
                    onPullDown();
                }
                break;
            case XEventCode.HTTP_BUDGET_COMPUTE:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    list = (List<Map<String, Object>>) map.get("data");
                    //                    setupChatView(list);
                    adapter.setData(list);
                    computed = true;//记录是否计算预算成功

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
            if (map != null) {
                vh.tvName.setText(JsonUtil.getItemString(map, "name"));
                vh.tv.setText(JsonUtil.getItemString(map, "money"));
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;
            TextView tv;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName = get(itemView, R.id.tvName);
                tv = get(itemView, R.id.tv);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(BudgetActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(BudgetActivity.this);
    }
}
