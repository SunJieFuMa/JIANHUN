package com.chantyou.janemarried.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.ShopDemoInfoRunner;
import com.chantyou.janemarried.model.Shop.ShopDemoInfoBean;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.chantyou.janemarried.ui.base.ThreadShareHelper;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.utils.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

//案例详情
public class ShopDemoInfoActivity extends PullrefreshBottomloadActivity {


    private InfoItemAdapter mAdapter;

    private ThreadShareHelper threadShareHelper;
    private MenuItem item;

    private int id;
    private String title;
    private String subtitle;
    private String url;

    private int shopId;
    private int casesId;
    private String backPic;//背景图
    private String desc;//描述

    private List<ShopDemoInfoBean.DataBean> shopDemoInfoList;

    public static void launch(Context cxt, String title, int shopId, int casesId, String backPic, String desc) {
        Intent intent = new Intent();
//        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("shopId", shopId);
        intent.putExtra("casesId", casesId);
        intent.putExtra("backPic", backPic);
        intent.putExtra("desc", desc);
        intent.setClass(cxt, ShopDemoInfoActivity.class);
        cxt.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
//        id = getIntent().getIntExtra("id", 0);
        shopId = getIntent().getIntExtra("shopId", 0);
        casesId = getIntent().getIntExtra("casesId", 0);
        backPic = getIntent().getStringExtra("backPic");
        desc = getIntent().getStringExtra("desc");
        return super.getLayoutResId();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void init() {
        super.init();
        threadShareHelper = new ThreadShareHelper(this);
        shopDemoInfoList = new ArrayList<ShopDemoInfoBean.DataBean>();
        pushEvent(new ShopDemoInfoRunner(shopId + "", casesId + ""));
    }

    @Override
    protected void setupRecyclerView() {
        mAdapter = new InfoItemAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), mAdapter, RecyclerMode.NONE);
    }

    protected void setPage(Map<String, Object> map) {
        mAdapter.map = map;
        if (map != null) {
            List<Map<String, Object>> images = (List<Map<String, Object>>) map.get("images");
            mAdapter.setData(images);
        } else {
            item.setVisible(false);
            mAdapter.setData(null);
        }
    }

    //服务器返回
    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SHOP_DEMO_INFO://案例详情
                if (event.isSuccess()) {
//                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
//                    setPage((Map<String, Object>) map.get("data"));

                    Gson gson = new Gson();
                    ShopDemoInfoBean shopDemoInfoBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopDemoInfoBean.class);
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    List<ShopDemoInfoBean.DataBean> shopDemoListBeanlist = shopDemoInfoBean.getData();
                    for (int i = 0; i < shopDemoListBeanlist.size(); i++) {
//                        Map<String, Object> mp = new HashMap<String, Object>();
//                        mp.put("imageUrl", shopDemoListBeanlist.get(i).getImageUrl());
//                        mp.put("descs", shopDemoListBeanlist.get(i).getDescs());
//                        mapList.add(mp);

                        String[] imms = shopDemoListBeanlist.get(i).getImageUrl().split(",");
                        if (imms.length > 1) {
                            for (int j = 0; j < imms.length; j++) {
                                Map<String, Object> mp = new HashMap<String, Object>();
                                mp.put("imageUrl", imms[j]);
                                if (0 == j) {
                                    mp.put("descs", shopDemoListBeanlist.get(i).getDescs());
                                }
                                mapList.add(mp);
                            }

                        } else {
                            Map<String, Object> mp = new HashMap<String, Object>();
                            mp.put("imageUrl", shopDemoListBeanlist.get(i).getImageUrl().replace(",", ""));
                            mp.put("descs", shopDemoListBeanlist.get(i).getDescs());
                            mapList.add(mp);
                        }

                    }

                    if (mapList != null) {
                        mAdapter.setData(mapList);
                    } else {
                        item.setVisible(false);
                        mAdapter.setData(null);
                    }


                } else {
                    setPage(null);
                }
                break;
        }
    }

    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.navbar_icon_share);
        item.setVisible(false);
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
        if (item.getItemId() == R.id.main_menu) {
            if (threadShareHelper != null && !TextUtils.isEmpty(url)) {
                threadShareHelper.showShareDialog(url, title, subtitle);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (threadShareHelper != null) {
            threadShareHelper.onActivityResult(requestCode, resultCode, data);
        }
    }


    private class InfoItemAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

        Map<String, Object> map;

        public InfoItemAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemCount() {
            int count = super.getItemCount();
            return count + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 1;
            }
            return 2;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return new IndexViewHolder(inflateView(parent, R.layout.activity_shop_demo_info));
            } else if (viewType == 2) {
                return new ViewHolder(inflateView(parent, R.layout.item_shop_demo_info));
            }
            return null;
        }

        @Override
        public Map<String, Object> getValueAt(int position) {
            if (position > 0) {
                return super.getValueAt(position - 1);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof IndexViewHolder) {
                ((IndexViewHolder) holder).setData(map);
            } else if (holder instanceof ViewHolder) {
                Map<String, Object> map = getValueAt(position);
                ViewHolder vh = (ViewHolder) holder;
                if (map != null) {
                    String pic = JsonUtil.getItemString(map, "imageUrl");
                    vh.iv.setAdjustViewBounds(true);
                    vh.iv.setMaxWidth(AppAndroid.getScreenWidth());
                    vh.iv.setMaxHeight(AppAndroid.getScreenHeight());
                    HImageLoader.displayImage(pic, vh.iv, R.color.white);
                    vh.tvContent.setText(Html.fromHtml(JsonUtil.getItemString(map, "descs")));
                }
            }
        }

        private class IndexViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            ImageView iv;
            TextView tvContent;
            ImageView iv_head;
            TextView tv_desc;

            public IndexViewHolder(View view) {
                super(view);
                tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                iv = (ImageView) view.findViewById(R.id.iv);
                tvContent = (TextView) view.findViewById(R.id.tvContent);


                tv_desc = (TextView) view.findViewById(R.id.tv_desc);


                //套系包含
                tv_desc.setText(desc);
                //案例背景
                iv_head = (ImageView) view.findViewById(R.id.iv_head);
                if (null != backPic && !"".equalsIgnoreCase(backPic)) {
                    HImageLoader.displayImage(backPic, iv_head, R.drawable.state2);
                }
            }

            void setData(Map<String, Object> map) {
                if (map != null) {
//                    id = JsonUtil.getItemInt(map, "id");
//                    title = JsonUtil.getItemString(map, "title");
//                    url = JsonUtil.getItemString(map, "exLink");
//                    String time = JsonUtil.getItemString(map, "author");
//                    if (!TextUtils.isEmpty(time)) {
//                        time += "  ";
//                    }
//                    time += JsonUtil.getItemString(map, "time");
//                    if (TextUtils.isEmpty(time)) {
//                        tvTime.setVisibility(View.GONE);
//                    } else {
//                        tvTime.setVisibility(View.VISIBLE);
//                        tvTime.setText(time);
//                    }
                    if (TextUtils.isEmpty(url)) {
//                        url = "http://101.201.209.200/MarryTrade/news.html?id=" + id;
                        url = Constants.buildNewsUrl(id);
                    }
                    tvTitle.setText(title);
                    subtitle = JsonUtil.getItemString(map, "subtitle");

                    String pic = JsonUtil.getItemString(map, "figurePic");
                    iv.setAdjustViewBounds(true);
                    iv.setMaxWidth(AppAndroid.getScreenWidth());
                    iv.setMaxHeight(AppAndroid.getScreenHeight());
//                    if(threadShareHelper != null) {
//                        threadShareHelper.setImageUrl(pic);
//                    }
                    HImageLoader.displayImage(pic, iv, R.color.white);
                    tvContent.setText(Html.fromHtml(JsonUtil.getItemString(map, "description")));
                    if (item != null) {
                        if (TextUtils.isEmpty(url) || "about:blank".equalsIgnoreCase(url)) {
                            item.setVisible(false);
                        } else {
                            item.setVisible(true);
                        }
                    }
                }
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            ImageView iv;
            TextView tvContent;

            public ViewHolder(View itemView) {
                super(itemView);
                iv = get(itemView, R.id.iv);
                tvContent = get(itemView, R.id.tvContent);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        StatService.onResume(ShopDemoInfoActivity.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(ShopDemoInfoActivity.this);
    }
}
