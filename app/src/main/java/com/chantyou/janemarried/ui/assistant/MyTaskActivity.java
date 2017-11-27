package com.chantyou.janemarried.ui.assistant;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.base.BaseListAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.TaskCompleteRunneer;
import com.chantyou.janemarried.httprunner.assistant.TaskDelRunner;
import com.chantyou.janemarried.httprunner.assistant.TaskMyRunner;
import com.chantyou.janemarried.httprunner.my.MarryDayEditRunner;
import com.chantyou.janemarried.httprunner.my.UserInfoRunner;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.chantyou.janemarried.ui.shop.ShopHomeActivity;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.widget.RoundProgressBar;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.helper.AbsChoosePhotoHelper2;
import com.mhh.lib.ui.helper.AbsDateTimerPickerHelper;
import com.mhh.lib.ui.helper.IDateTimePickerHelper;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.utils.SystemUtils;
import com.mhh.lib.widget.ListViewEx;
import com.mhh.lib.widget.XDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/2/25.
 * Email 766082577@qq.com
 */
//结婚任务
public class MyTaskActivity extends PullrefreshBottomloadActivity implements View.OnClickListener {

    @ViewInject(R.id.llDate)
    private LinearLayout llDate;
    @ViewInject(R.id.rPbar)
    private RoundProgressBar rPbar;
    @ViewInject(R.id.tvProcess1)
    private TextView tvProcess1;
    @ViewInject(R.id.tvProcess2)
    private TextView tvProcess2;

    @ViewInject(R.id.vEmpty)
    private View vEmpty;

    private MyTaskAdapter adapter;
    private String day = "";

    /**
     * 日期选择 类
     */
    private IDateTimePickerHelper dateTimePickerHelper;
    private AbsChoosePhotoHelper2 choosePhotoHelper;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    //    private MyTaskShopInfo myTaskShopInfo;
    //    private List<MyTaskShopInfo.DataEntity.UndoneEntity.ChildNodesEntity> childNodes1;
    //    List<MyTaskShopInfo.DataEntity.UndoneEntity.ChildNodesEntity.ShopItem> shops = null;
    private TextView tv_total_money;
    private LinearLayout ll_compute;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mytask;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);

        addAndroidEventListener(XEventCode.HTTP_TASK_USERADD, this);
        addAndroidEventListener(XEventCode.HTTP_TASK_USEREDIT, this);

        dateTimePickerHelper = new AbsDateTimerPickerHelper();
        tv_total_money = (TextView) findViewById(R.id.tv_total_money);
        ll_compute = (LinearLayout) findViewById(R.id.ll_compute);
        ll_compute.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_compute:
                Intent intent = new Intent(this, BudgetActivity.class);
                intent.putExtra("issave", true);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                onPullDown();//刷新一下
            }
        }
    }

    public static void launch(Activity act, boolean isEdit) {
        Intent intent = new Intent(act, MyTaskActivity.class);
        intent.putExtra("isShowBack", isEdit);
        act.startActivity(intent);
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);

        boolean type = false;
        try {
            type = getIntent().getExtras().getBoolean("isShowBack");
        } catch (Exception e) {

        }
        if (!type) {
            toolbarAttribute.setNavigation(false, null, null);
        }
        //        toolbarAttribute.setTitleAttr(false, Gravity.LEFT, "");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeEventListener(XEventCode.HTTP_TASK_USERADD, this);
        removeEventListener(XEventCode.HTTP_TASK_USEREDIT, this);
    }

    @Override
    protected void init() {
        super.init();

        if (null != llDate) {
            llDate.removeAllViews();
        }

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(v.getContext(), TaskAddActivity.class);
            }
        });

        String marryDay = Constants.getUserInfoByKey(Constants.SU_MARRYDATE);
        int haveDay = 0;

        try {
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(marryDay);
            haveDay = Constants.getGapCount(new Date(), endDate);
            if (haveDay < 0) {
                haveDay = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String day = Integer.toString(haveDay);
        int len = day.length();
        if (len > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(SystemUtils.dipToPixel(this, 2), 0, SystemUtils.dipToPixel(this, 1), 0);

            for (int i = 0; i < len; i++) {
                TextView tv = new TextView(this);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                tv.setTextColor(getResources().getColor(R.color.c_ff3257));
                tv.setBackgroundResource(R.drawable.xml_date_itembg);//textview的白色背景、圆角、大小设置
                llDate.addView(tv, params);
                if (i >= 3 && len > 4) {
                    tv.setText("...");
                    break;
                } else {
                    tv.setText(day.substring(i, i + 1));
                }
            }
        }

        //        rPbar.setMax(100);
        //        rPbar.setProgress(10);

        onPullDown();
    }


    private void setTaskProgress(int undone, int complete) {
        int max = undone + complete;
        if (max > 0) {
            tvProcess1.setText(String.valueOf(undone));
            tvProcess2.setText(String.valueOf(complete));
            rPbar.setMax(max);
            rPbar.setProgress(complete);
        }
    }


    /**
     * Toolbar（右标题）菜单选项
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setTitle("设置婚期");
        //        item.setIcon(R.drawable.icon_plus);
        return true;
    }


    /**
     * Toolbar （右标题）菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                final Calendar cal = Calendar.getInstance();
                dateTimePickerHelper.showDatePicker(this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, new XDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        day = format.format(new Date(cal.getTimeInMillis()));
                        System.out.println("zhuwx:" + format.format(new Date(cal.getTimeInMillis())));
                        if (!TextUtils.isEmpty(day)) {
                            pushEventEx(false, null, new MarryDayEditRunner(format.format(new Date(cal.getTimeInMillis()))), MyTaskActivity.this);
                        }
                    }
                });
                break;
        }
        return true;
    }


    @Override
    public void onPullDown() {
        super.onPullDown();
        pushEvent(new TaskMyRunner());
        /////////////////////这里加一个
        //post请求
        /*OkHttpUtils
                .post()
                .url(UrlConfig.TASK_MY)
                .addParams("access_token", AppAndroid.getAccessToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("response::::::::::"+response);
                        myTaskShopInfo = new Gson().fromJson(response, MyTaskShopInfo.class);
                    }
                });*/
    }

    @Override
    protected void setupRecyclerView() {
        adapter = new MyTaskAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), adapter, RecyclerMode.NONE);
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TASK_MY:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    map = (Map<String, Object>) map.get("data");
                    tv_total_money.setText(JsonUtil.getItemString(map, "budgets"));

                    setTaskProgress(JsonUtil.getItemInt(map, "undoneNum"), JsonUtil.getItemInt(map, "completeNum"));
                    List<Map<String, Object>> undone = (List<Map<String, Object>>) map.get("undone");
                    List<Map<String, Object>> complete = (List<Map<String, Object>>) map.get("complete");
                    adapter.setComplete(JsonUtil.getItemInt(map, "completeNum"), complete);
                    adapter.setData(undone);
                    adapter.notifyDataSetChanged();
                }
                if (adapter != null && adapter.getItemCount() > 0) {
                    vEmpty.setVisibility(View.GONE);
                } else {
                    vEmpty.setVisibility(View.VISIBLE);
                }
                break;
            case XEventCode.HTTP_TASK_USERADD:
            case XEventCode.HTTP_TASK_USEREDIT:
                if (event.isSuccess()) {
                    onPullDown();
                }
            case XEventCode.HTTP_TASK_USERDEL:
            case XEventCode.HTTP_TASK_USERCOM:
                if (event.isSuccess()) {
                    onPullDown();
                    CustomToast.showRightToast(this, event.getMessage("操作成功"));
                } else {
                    CustomToast.showWorningToast(this, event.getMessage("操作失败"));
                }
                break;
            case XEventCode.HTTP_MARRY_DAY_EDIT:
                if (event.isSuccess()) {
                    CustomToast.showRightToast(this, event.getMessage("操作成功"));
                    pushEventEx(false, null, new UserInfoRunner(), this);
                    Constants.setUserInfo(Constants.SU_MARRYDATE, day);
                    init();
                } else {
                    CustomToast.showWorningToast(this, event.getMessage("操作失败"));
                }
                break;

        }
    }


    private class MyTaskAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

        private List<Map<String, Object>> complete;
        private int completeNum;


        public MyTaskAdapter(Context context) {
            super(context);
            complete = new ArrayList<>();
        }

        public void setComplete(int num, List<Map<String, Object>> list) {
            complete.clear();
            completeNum = num;
            if (list != null) {
                for (Map<String, Object> map : list) {
                    List<Map<String, Object>> childNodes = (List<Map<String, Object>>) map.get("childNodes");
                    complete.addAll(childNodes);
                }
            }
        }

        private boolean isNotEmple() {
            return complete != null && complete.size() > 0;
        }

        @Override
        public int getItemCount() {
            int count = super.getItemCount();
            if (isNotEmple()) {
                count += 1;
            }
            return count;
        }

        @Override
        public int getItemViewType(int position) {
            //            if (isNotEmple() && position == getItemCount() - 1) {
            //                return 2;
            //            }
            //            return 1;

            if (isNotEmple() && position == 0) {
                return 2;
            }
            return 1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 2) {
                return new ViewHolder2(inflateView(parent, R.layout.adapter_mytask_complete));
            }
            return new ViewHolder(inflateView(parent, R.layout.adapter_mytask));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder != null) {
                if (holder instanceof ViewHolder) {
                    ViewHolder vh = (ViewHolder) holder;
                    Map<String, Object> map = null;
                    if (!isNotEmple()) {
                        map = getValueAt(position);
                    } else {
                        map = getValueAt3(position - 1);
                    }
                    if (map != null) {
                        vh.tvTitle.setText(JsonUtil.getItemString(map, "name"));
                        List<Map<String, Object>> childNodes = (List<Map<String, Object>>) map.get("childNodes");
                        vh.list.setAdapter(vh.adapter);
                        vh.adapter.setData(childNodes);
                    }
                } else if (holder instanceof ViewHolder2) {
                    ViewHolder2 vh = (ViewHolder2) holder;
                    vh.tvComplete.setText(String.format("查看%1$d个已完成任务", completeNum));
                    vh.list.setVisibility(vh.tvComplete.isSelected() ? View.VISIBLE : View.GONE);
                    vh.list.setAdapter(vh.adapter);
                    vh.adapter.setData(complete);
                }
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            ListViewEx list;
            MyTaskItemAdapter adapter;

            public ViewHolder(View itemView) {
                super(itemView);
                tvTitle = get(itemView, R.id.tvTitle);
                list = get(itemView, R.id.lv_list);

                adapter = new MyTaskItemAdapter();
                //                list.setLayoutManager(new LinearLayoutManager(tvTitle.getContext()));
                list.setAdapter(adapter);
            }
        }

        private class ViewHolder2 extends RecyclerView.ViewHolder {

            TextView tvComplete;
            ListViewEx list;
            MyTaskItemAdapter2 adapter;

            public ViewHolder2(View itemView) {
                super(itemView);
                tvComplete = get(itemView, R.id.tvComplete);
                list = get(itemView, R.id.lv_clist);

                adapter = new MyTaskItemAdapter2();
                list.setAdapter(adapter);
                tvComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvComplete.setSelected(!tvComplete.isSelected());
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }

    private class MyTaskItemAdapter2 extends BaseListAdapter<Map<String, Object>> {

        @Override
        protected int getLayoutId() {
            return R.layout.adapter_mytask_complete_item;
        }

        @Override
        protected AbsViewHolder setViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        public void setData(List<Map<String, Object>> data) {
            clear();
            super.setData(data);
        }

        private class ViewHolder extends AbsViewHolder implements View.OnClickListener {

            TextView tvName;
            ImageView ivDel;
            Map<String, Object> map;

            public ViewHolder(View itemView) {
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                ivDel = (ImageView) itemView.findViewById(R.id.ivDel);
                tvName.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                ivDel.setOnClickListener(this);
            }

            @Override
            public void setValue(int position, Map<String, Object> item) {
                super.setValue(position, item);
                this.map = item;
                if (map != null) {
                    tvName.setText(JsonUtil.getItemString(map, "title"));
                }
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ivDel:
                        if (map != null) {
//                            pushEvent(new TaskCompleteRunneer(JsonUtil.getItemInt(map, "id"), 0));
                            /////////这个接口有问题，删除不了，不但删除不了，还会把这个已完成的任务添加到未完成的里面
                            //所以我就直接使用的未完成任务的删除的接口，结果还成功了，真是有意思
                            pushEvent(new TaskDelRunner(JsonUtil.getItemInt(map, "id")));

                        }
                        break;
                }
            }
        }
    }

    private class MyTaskItemAdapter extends BaseListAdapter<Map<String, Object>> {

        @Override
        protected int getLayoutId() {
            return R.layout.adapter_mytask_item;
        }

        @Override
        protected AbsViewHolder setViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        public void setData(List<Map<String, Object>> data) {
            clear();
            super.setData(data);
        }

        private class ViewHolder extends AbsViewHolder implements View.OnClickListener {

            TextView tvName;
            TextView tvTime;
            TextView tvEdit;
            ImageView ivOp;
            ImageView ivTip;
            View vTop, vEdit, vOk, vDel, vBottom;
            Map<String, Object> map;
            LinearLayout ll_shop;
            LinearLayout ll_shop_item1;
            LinearLayout ll_shop_item2;
            LinearLayout ll_shop_item3;
            ImageView iv_shop1;
            ImageView iv_shop2;
            ImageView iv_shop3;
            TextView tv_shop1;
            TextView tv_shop2;
            TextView tv_shop3;
            TextView tv_item_yusuan;
            LinearLayout ll_item_yusuan;

            public ViewHolder(View itemView) {
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                tvEdit = (TextView) itemView.findViewById(R.id.tvEdit);
                ivTip = (ImageView) itemView.findViewById(R.id.ivTip);
                ivOp = (ImageView) itemView.findViewById(R.id.ivOp);
                vTop = itemView.findViewById(R.id.vTop);
                vEdit = itemView.findViewById(R.id.vEdit);
                vOk = itemView.findViewById(R.id.vOk);
                vDel = itemView.findViewById(R.id.vDel);
                vBottom = itemView.findViewById(R.id.vBottom);

                tvTime.setVisibility(View.VISIBLE);

                vTop.setOnClickListener(this);
                vEdit.setOnClickListener(this);
                vOk.setOnClickListener(this);
                vDel.setOnClickListener(this);

                ///////////////////////////////////
                ll_shop = (LinearLayout) itemView.findViewById(R.id.ll_shop);
                ll_shop_item1 = (LinearLayout) itemView.findViewById(R.id.ll_shop_item1);
                ll_shop_item1.setOnClickListener(this);
                ll_shop_item2 = (LinearLayout) itemView.findViewById(R.id.ll_shop_item2);
                ll_shop_item2.setOnClickListener(this);
                ll_shop_item3 = (LinearLayout) itemView.findViewById(R.id.ll_shop_item3);
                ll_shop_item3.setOnClickListener(this);
                tv_shop1 = (TextView) itemView.findViewById(R.id.tv_shop1);
                tv_shop2 = (TextView) itemView.findViewById(R.id.tv_shop2);
                tv_shop3 = (TextView) itemView.findViewById(R.id.tv_shop3);
                iv_shop1 = (ImageView) itemView.findViewById(R.id.iv_shop1);
                iv_shop2 = (ImageView) itemView.findViewById(R.id.iv_shop2);
                iv_shop3 = (ImageView) itemView.findViewById(R.id.iv_shop3);
                tv_item_yusuan = (TextView) itemView.findViewById(R.id.tv_item_yusuan);
                ll_item_yusuan = (LinearLayout) itemView.findViewById(R.id.ll_item_yusuan);
            }

            List<Map<String, Object>> list = new ArrayList<>();

            @Override
            public void setValue(int position, Map<String, Object> item) {
                super.setValue(position, item);
                this.map = item;
                if (map != null) {
                    list = (List<Map<String, Object>>) map.get("shops");
                    //                    System.out.println("list::::::::::::::::::" + list);
                    //                    Toast.makeText(MyTaskActivity.this, "list::::"+list, Toast.LENGTH_SHORT).show();

                    tvName.setText(JsonUtil.getItemString(this.map, "title"));
                    tv_item_yusuan.setText(JsonUtil.getItemString(this.map, "budget"));
                    String completeTime = JsonUtil.getItemString(this.map, "completeTime");
                    if (TextUtils.isEmpty(completeTime)) {
                        tvTime.setVisibility(View.GONE);
                    } else {
                        tvTime.setVisibility(View.VISIBLE);
                        tvTime.setText(completeTime);
                    }
                    if (JsonUtil.getItemBoolean(this.map, "isopen")) {
                        vBottom.setVisibility(View.VISIBLE);
                        ivOp.setImageResource(R.drawable.icon_open);
                        /////////////////////////
                        ll_item_yusuan.setVisibility(View.VISIBLE);///////
                        if (null != list && list.size() > 0) {
                            //                            Toast.makeText(MyTaskActivity.this, "不为空 ", Toast.LENGTH_SHORT).show();
                            Map<String, Object> map1 = list.get(0);
                            String name = JsonUtil.getItemString(map1, "name");
                            //                            Toast.makeText(MyTaskActivity.this, "name：：： "+name, Toast.LENGTH_SHORT).show();
                            String imageUrl = JsonUtil.getItemString(map1, "imageUrl");
                            //                            Toast.makeText(MyTaskActivity.this, "imageUrl：： "+imageUrl, Toast.LENGTH_SHORT).show();
                            ll_shop.setVisibility(View.VISIBLE);
                            tv_shop1.setText(name);
                            Glide.with(MyTaskActivity.this).load(imageUrl).into(iv_shop1);


                            Map<String, Object> map2 = list.get(1);
                            name = JsonUtil.getItemString(map2, "name");
                            imageUrl = JsonUtil.getItemString(map2, "imageUrl");
                            tv_shop2.setText(name);
                            Glide.with(MyTaskActivity.this).load(imageUrl).into(iv_shop2);


                            Map<String, Object> map3 = list.get(2);
                            name = JsonUtil.getItemString(map3, "name");
                            imageUrl = JsonUtil.getItemString(map3, "imageUrl");
                            tv_shop3.setText(name);
                            Glide.with(MyTaskActivity.this).load(imageUrl).into(iv_shop3);
                        } else {
                            ll_shop.setVisibility(View.GONE);
                        }
                        ////////////////////////
                    } else {
                        vBottom.setVisibility(View.GONE);
                        ll_shop.setVisibility(View.GONE);/////////////
                        ll_item_yusuan.setVisibility(View.GONE);///////
                        ivOp.setImageResource(R.drawable.icon_close);
                    }
                    String tipTime = JsonUtil.getItemString(this.map, "tipTime");
                    if (TextUtils.isEmpty(tipTime)) {
                        ivTip.setVisibility(View.GONE);
                    } else {
                        ivTip.setVisibility(View.VISIBLE);
                    }
                    String remark = JsonUtil.getItemString(this.map, "remark");
                    if (TextUtils.isEmpty(remark)) {
                        tvEdit.setSelected(false);
                    } else {
                        tvEdit.setSelected(true);
                    }

                }
            }

            int shopLevel;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.vTop:
                        map.put("isopen", !JsonUtil.getItemBoolean(map, "isopen"));
                        notifyDataSetChanged();
                        break;
                    case R.id.vEdit:
                        if (map != null) {
                            TaskEditActivity.launch(v.getContext(), map);
                        }
                        break;
                    case R.id.vOk:
                        if (map != null) {
                            pushEvent(new TaskCompleteRunneer(JsonUtil.getItemInt(map, "id"), 1));
                        }
                        break;
                    case R.id.vDel:
                        if (map != null) {
                            showYesOrNoDialog("提示", "删除", "取消", "是否删除？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == DialogInterface.BUTTON_POSITIVE) {
                                        pushEvent(new TaskDelRunner(JsonUtil.getItemInt(map, "id")));
                                    }
                                }
                            });
                        }
                        break;
                    case R.id.ll_shop_item1:
                        Intent intent = new Intent(MyTaskActivity.this, ShopHomeActivity.class);
                        intent.putExtra("id", JsonUtil.getItemInt(list.get(0), "id"));
                        shopLevel = JsonUtil.getItemInt(list.get(0), "shopLevel");
                        intent.putExtra("shopLevel", shopLevel > 0 ? shopLevel : -1);
                        MyTaskActivity.this.startActivity(intent);
                        break;
                    case R.id.ll_shop_item2:
                        Intent intent2 = new Intent(MyTaskActivity.this, ShopHomeActivity.class);
                        intent2.putExtra("id", JsonUtil.getItemInt(list.get(1), "id"));
                        shopLevel = JsonUtil.getItemInt(list.get(1), "shopLevel");
                        intent2.putExtra("shopLevel", shopLevel > 0 ? shopLevel : -1);
                        MyTaskActivity.this.startActivity(intent2);
                        break;
                    case R.id.ll_shop_item3:
                        Intent intent3 = new Intent(MyTaskActivity.this, ShopHomeActivity.class);
                        intent3.putExtra("id", JsonUtil.getItemInt(list.get(2), "id"));
                        shopLevel = JsonUtil.getItemInt(list.get(2), "shopLevel");
                        intent3.putExtra("shopLevel", shopLevel > 0 ? shopLevel : -1);
                        MyTaskActivity.this.startActivity(intent3);
                        break;
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MyTaskActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MyTaskActivity.this);
    }
}
