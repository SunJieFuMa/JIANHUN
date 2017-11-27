package com.chantyou.janemarried.ui.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.TagAddRunner;
import com.chantyou.janemarried.httprunner.TagsInPhaseRunner;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.framework.CustomToast;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.utils.SystemUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/2/6 14:05
 * Email：766082577@qq.com
 */
public class AddLabelActivity extends PullrefreshBottomloadActivity {

    public static int REQCODE = 23;
    private int phaseId = -1;
    private TagsAdapter mAdapter;
    private AddLabelDialog addLabelDialog;

    public static void launchForResult(Context cxt, int phaseId) {
        Intent intent = new Intent(cxt, AddLabelActivity.class);
        intent.putExtra("phaseId" ,phaseId);
        if(cxt instanceof Activity) {
            ((Activity) cxt).startActivityForResult(intent, REQCODE);
        } else {
            cxt.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        addAndroidEventListener(XEventCode.EVENT_RUNCODE, this);
//        setContentView(R.layout.activity_addlabel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.EVENT_RUNCODE, this);
    }

    @Override
    protected void init() {
        phaseId = getIntent().getIntExtra("phaseId" , -1);
        System.out.println("添加标签activity：phaseId="+phaseId);
        if(phaseId == -1) {
            finish();
            return;
        }
        super.init();
        onPullDown();
    }

    @Override
    protected void setupRecyclerView() {

        mAdapter = new TagsAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        //spanSize表示一个item的跨度，跨度了多少个span,也就是一个item占有的列数
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == 1 ? 3 : 1;
            }
        });
        setupRecyclerView(manager, mAdapter, RecyclerMode.NONE);
    }

    @Override
    public void onPullDown() {
        super.onPullDown();
        pushEvent(new TagsInPhaseRunner(phaseId));
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
        item.setIcon(R.drawable.font_save);
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
                try {
                    List<Map<String, Object>> list = mAdapter.sMap;
                    Intent intent = new Intent();
                    intent.putExtra("tagsinfo", JsonUtil.objectToJson(list));
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }

    private void dismissPopup() {
        if(addLabelDialog != null && addLabelDialog.isShowing()) {
            addLabelDialog.dismiss();
            addLabelDialog = null;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TAGS_INPHASE:
                if(event.isSuccess()) {
                    JSONObject object = (JSONObject) event.getReturnParamsAtIndex(0);
                    mAdapter.setData((List<? extends Map<String, Object>>) JsonUtil.jsonToList(object.optString("tags")));
                } else {
//                    CustomToast.showErrorToast(this, event.getMessage("婚礼标签获取失败"));
                }
                break;
            case XEventCode.HTTP_TAGS_ADDONE:
                if(event.isSuccess()) {
                    dismissPopup();
                    onPullDown();
                    CustomToast.showRightToast(this, "添加成功");
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("添加失败"));
                }
                break;
            case XEventCode.EVENT_RUNCODE:
                int code = (int) event.getParamsAtIndex(0);
                int phaseId = (int) event.getParamsAtIndex(1);
                if(code == XEventCode.HTTP_TAGS_ADDONE && this.phaseId == phaseId) {
                    pushEvent(new TagAddRunner(phaseId, (String) event.getParamsAtIndex(2)));
                }
                break;
        }
    }

    private class TagsAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {
        public List<Map<String, Object>> sMap = new ArrayList<>();

        public TagsAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 1 : 2;
        }

        @Override
        public int getItemCount() {
            return super.getItemCount() + 2;
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
                vh.pos = position;
                if(map == null) {
                    vh.tv.setText("添加一个");
                    vh.tv.setSelected(false);
                } else {
                    vh.tv.setText(JsonUtil.getItemString(map, "name"));
                    vh.tv.setSelected(sMap.contains(map));
                }
            } else if(holder instanceof ViewHolder2) {
                ViewHolder2 vh = (ViewHolder2) holder;
                vh.tv.setText(super.getItemCount() == 0 ? "无该阶段婚礼标签，添加一个" : "选择婚礼标签");
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
                        if(map != null) {
                            if(sMap.contains(map)){
                                sMap.remove(map);
                            } else {
                                sMap.add(map);
                            }
                            notifyDataSetChanged();
                        } else {
                            dismissPopup();
                            addLabelDialog = new AddLabelDialog(v.getContext(), phaseId);
                            addLabelDialog.show();
                        }
                    }
                });
            }
        }

        private class ViewHolder2 extends RecyclerView.ViewHolder {

            TextView tv;
            public ViewHolder2(View itemView) {
                super(itemView);
                tv = (TextView) itemView;
                tv.setMinHeight(SystemUtils.dipToPixel(tv.getContext(), 60));
                tv.setGravity(Gravity.CENTER);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(AddLabelActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(AddLabelActivity.this);
    }

}
