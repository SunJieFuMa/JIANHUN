package com.chantyou.janemarried.ui.assistant;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.GuestListAllRunner;
import com.chantyou.janemarried.httprunner.assistant.GuestListDelRunner;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/2/6 21:40
 * Email：766082577@qq.com
 */
public class GuestListActivity extends PullrefreshBottomloadActivity {

    @ViewInject(R.id.vEmpty)
    private View vEmpty;
    @ViewInject(R.id.vLayout)
    private View vLayout;//嵌套recyclerView的FrameLayout
    @ViewInject(R.id.et)
    private EditText et;
    @ViewInject(R.id.tvAddUp)
    private TextView tvAddUp;//共几人 桌数几

    private GuestListAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guestlist;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitleEnabled(false);
        registerEditTextInputManager(et);
    }

    @Override
    protected void init() {
        super.init();

        addAndroidEventListener(XEventCode.HTTP_GUESTLIST_ADD, this);

        vEmpty.setVisibility(View.GONE);
        vLayout.setVisibility(View.INVISIBLE);
        vEmpty.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launch(v.getContext(), GuestListAddActivity.class);
            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                onPullDown();
//                hidenSoftInput(et);
            }
        });

        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_SEND) {
                    onPullDown();
                    hidenSoftInput(et);//关闭软键盘
                    return true;
                }
                return false;
            }
        });
        onPullDown();
    }

    @Override
    public void onPullDown() {
        String name = et.getText().toString();
        if(TextUtils.isEmpty(name)) {
            name = "";
        }
        pushEventEx(false, null ,new GuestListAllRunner(name), this);
    }

    @Override
    protected void onRefreshCompleted() {
        super.onRefreshCompleted();
        List<Map<String, Object>> list = adapter.getData();
        if(list == null || list.isEmpty()) {
            vEmpty.setVisibility(View.VISIBLE);
            vLayout.setVisibility(View.GONE);
        } else {
            vEmpty.setVisibility(View.GONE);
            vLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.HTTP_GUESTLIST_ADD, this);
        unRegisterEditTextInputManager(et);
    }

    @Override
    protected void setupRecyclerView() {
        adapter = new GuestListAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), adapter, RecyclerMode.NONE);
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
                launch(this, GuestListAddActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_GUESTLIST_ALL:
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    if(map != null && map.containsKey("deskNum") && map.containsKey("personNum")) {
                        tvAddUp.setText(String.format("总共%1$d人  桌数：%2$d", JsonUtil.getItemInt(map, "personNum"), JsonUtil.getItemInt(map, "deskNum")));
                        tvAddUp.setVisibility(View.VISIBLE);
                    } else {
                        tvAddUp.setVisibility(View.GONE);
                    }
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("deskArranges");
                    adapter.setData(list);
                } else {
                    tvAddUp.setVisibility(View.GONE);
                    adapter.setData(null);
//                    CustomToast.showErrorToast(this, event.getMessage("查询无数据"));
                }
                onRefreshCompleted();
                break;
            case XEventCode.HTTP_GUESTLIST_ADD:
            case XEventCode.HTTP_GUESTLIST_DEL:
                if(event.isSuccess()) {
                    onPullDown();
                }
                break;
        }
    }

    private class GuestListAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

        public GuestListAdapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflateView(parent, R.layout.adapter_guestlist));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            Map<String, Object> map = getValueAt(position);
            vh.map = map;
            if(map != null) {
                vh.tvNum.setText(String.format("共：%1$d人",JsonUtil.getItemInt(map, "num")));
                vh.tvList.setText(JsonUtil.getItemString(map,"persons"));
                vh.tvName.setText(JsonUtil.getItemString(map,"deskName"));
            }
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNum, tvName, tvList;
        ImageView ivDel, ivEdit;
        Map<String, Object> map;

        public ViewHolder(View itemView) {
            super(itemView);
            ivDel = (ImageView) itemView.findViewById(R.id.ivDel);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            tvNum = (TextView) itemView.findViewById(R.id.tvNum);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvList = (TextView) itemView.findViewById(R.id.tvList);

            ivDel.setOnClickListener(this);
            ivEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivDel:
                    if(map != null) {
                        showYesOrNoDialog("提示", "确定", "取消", "是否删除婚宴桌?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    pushEvent(new GuestListDelRunner(JsonUtil.getItemInt(map, "id")));
                                }
                            }
                        });
                    }
                    break;
                case R.id.ivEdit:
                    GuestListAddActivity.launch(v.getContext(), map);
                    break;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(GuestListActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(GuestListActivity.this);
    }
}
