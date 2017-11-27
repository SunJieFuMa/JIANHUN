package com.chantyou.janemarried.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.PhasesAllRunner;
import com.chantyou.janemarried.httprunner.my.UserEditRunner;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.utils.SystemUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/2/20.
 * Email 766082577@qq.com
 */
public class MarryPhasesAllActivity extends PullrefreshBottomloadActivity {

    public static int REQCODE = 22;
    private MarryPhasesAdapter mAdapter;
    private boolean isEdit;

    public static void launchForResult(Activity act) {
        Intent intent = new Intent(act, MarryPhasesAllActivity.class);
        act.startActivityForResult(intent, REQCODE);
    }

    public static void launchForResult(Activity act, boolean isEdit) {
        Intent intent = new Intent(act, MarryPhasesAllActivity.class);
        intent.putExtra("isEdit", isEdit);
        act.startActivityForResult(intent, REQCODE);
    }

    @Override
    protected void init() {
        super.init();
        isEdit = getIntent().getBooleanExtra("isEdit", false);

        pushEvent(new PhasesAllRunner());
    }

    @Override
    protected void setupRecyclerView() {

        mAdapter = new MarryPhasesAdapter(this);
//        final GridLayoutManager manager = new GridLayoutManager(this, 3);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                try {
//                    if(mAdapter.getItemViewType(position) == 1) {
//                        return manager.getSpanCount();
//                    }
//                    int len = JsonUtil.getItemString(mAdapter.getValueAt(position), "name").length();
//                    if(len >= 18) {
//                        return 3;
//                    }
//                    if(len >= 8) {
//                        return 2;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return 1;
//            }
//        });
        setupRecyclerView(new LinearLayoutManager(this), mAdapter, RecyclerMode.NONE);
    }

    /**
     * Toolbar（右标题）菜单选项
     * @param
     * @return
     *//*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.font_save);
        return true;
    }*/

    private void doResult() {
        Map<String, Object> map = mAdapter.getValueAt(mAdapter.cPos);
        Intent intent = new Intent();
        intent.putExtra("phasesinfo", JsonUtil.objectToJson(map));
        setResult(RESULT_OK, intent);
        finish();
    }

    //点击item直接保存就可以了
    public void save(){
        try {
            if(mAdapter.cPos < 0) {
                CustomToast.showWorningToast(this, "请选择婚礼阶段");
//                return true;
            }
            if(isEdit) {
                Map<String, Object> map = mAdapter.getValueAt(mAdapter.cPos);
                showProgressDialog(null, "正在修改...");
                pushEventEx(false, null, new UserEditRunner("", "", JsonUtil.getItemString(map, "name"), "", "", "", ""), MarryPhasesAllActivity.this);
            } else {
                doResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Toolbar 菜单点击事件
     * @param
     * @return
     *//*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                try {
                    if(mAdapter.cPos < 0) {
                        CustomToast.showWorningToast(this, "请选择婚礼阶段");
                        return true;
                    }
                    if(isEdit) {
                        *//*showYesOrNoDialog("提示", "确定", "取消", "确定修改当前婚礼阶段？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    Map<String, Object> map = mAdapter.getValueAt(mAdapter.cPos);
                                    showProgressDialog(null, "正在修改...");
                                    pushEventEx(false, null, new UserEditRunner("", "", JsonUtil.getItemString(map, "name"), "", "", "", ""), MarryPhasesAllActivity.this);
                                }
                            }
                        });*//*
                        Map<String, Object> map = mAdapter.getValueAt(mAdapter.cPos);
                        showProgressDialog(null, "正在修改...");
                        pushEventEx(false, null, new UserEditRunner("", "", JsonUtil.getItemString(map, "name"), "", "", "", ""), MarryPhasesAllActivity.this);
                    } else {
                        doResult();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }*/

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PHASES_ALL:
                    if(event.isSuccess()) {
                        JSONObject object = (JSONObject) event.getReturnParamsAtIndex(0);
                        mAdapter.setData((List<? extends Map<String, Object>>) JsonUtil.jsonToList(object.optString("phases")));
                    } else {
                        CustomToast.showErrorToast(this, event.getMessage("婚礼标签获取失败"));
                    }
                break;
            case XEventCode.HTTP_USER_EDIT:
                if(event.isSuccess()) {
                    doResult();
                } else {
                    CustomToast.showWorningToast(this, "修改失败");
                }
                break;
        }
    }

    private class MarryPhasesAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {
        public int cPos = -1;

        public MarryPhasesAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 1 : 2;
        }

        @Override
        public int getItemCount() {
            return super.getItemCount() + 1;
        }

        @Override
        public Map<String, Object> getValueAt(int position) {
            return position == 0 ? null : super.getValueAt(position - 1);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == 1) {
                return new ViewHolder2(new TextView(parent.getContext()));
            }
            return new ViewHolder(inflateView(parent, R.layout.adapter_marryphases));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder == null) {
                return;
            }
            if(holder instanceof ViewHolder) {
                Map<String, Object> map = getValueAt(position);
                ViewHolder vh = (ViewHolder) holder;
                vh.map = map;
                vh.tv.setText(JsonUtil.getItemString(map,"name"));
                vh.pos = position;
                vh.tv.setSelected(cPos == position);
            } else if(holder instanceof ViewHolder2) {
                ViewHolder2 vh = (ViewHolder2) holder;
                vh.tv.setText("选择一个婚礼阶段");
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv;
            int pos;
            Map<String, Object> map;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = get(itemView, R.id.tv);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cPos = pos;
                        notifyDataSetChanged();
                        save();
                    }
                });
            }
        }



        private  class ViewHolder2 extends RecyclerView.ViewHolder {

            TextView tv;
            public ViewHolder2(View itemView) {
                super(itemView);
                tv = (TextView) itemView;
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setMinHeight(SystemUtils.dipToPixel(tv.getContext(), 60));
                tv.setGravity(Gravity.CENTER);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MarryPhasesAllActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MarryPhasesAllActivity.this);
    }

}
