package com.chantyou.janemarried.ui.main;

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
import com.chantyou.janemarried.httprunner.home.NeswDetailRunner;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.chantyou.janemarried.ui.base.ThreadShareHelper;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by mhh on 2016/5/19.
 * Email:766082577@qq.com
 */
public class InformationActivity extends PullrefreshBottomloadActivity {


    private InfoItemAdapter mAdapter;

    private ThreadShareHelper threadShareHelper;
    MenuItem item;

    int id;
    String title;
    String subtitle;
    String url;

    String imageUrl;//图片

    public static void launch(Context cxt, int id, String title) {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.setClass(cxt, InformationActivity.class);
        cxt.startActivity(intent);
    }

    public static void launch(Context cxt, int id, String title,String imageUrl) {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("imageUrl", imageUrl);
        intent.setClass(cxt, InformationActivity.class);
        cxt.startActivity(intent);
    }

    public static void launch3(Context cxt, int id, String title,String imageUrl) {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("imageUrl", imageUrl);
        intent.setClass(cxt, InformationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cxt.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        id = getIntent().getIntExtra("id", 0);
        imageUrl = getIntent().getStringExtra("imageUrl");
        return super.getLayoutResId();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        if (id == 0) {
            finish();
        }
//        setContentView(R.layout.activity_infomation);
    }

    @Override
    protected void init() {
        super.init();
        threadShareHelper = new ThreadShareHelper(this);

        pushEvent(new NeswDetailRunner(id + ""));
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

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_NEWS_DETAIL:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    setPage((Map<String, Object>) map.get("data"));
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
        item.setIcon(R.drawable.menu_share);
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
//                if(!TextUtils.isEmpty(imageUrl)){
//                    threadShareHelper.setImageUrl(imageUrl);
//                }
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
                return new IndexViewHolder(inflateView(parent, R.layout.adapter_infomation));
            } else if(viewType == 2) {
                return new ViewHolder(inflateView(parent, R.layout.adapter_info_item));
            }
            return null;
        }

        @Override
        public Map<String, Object> getValueAt(int position) {
            if(position > 0) {
                return super.getValueAt(position - 1);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof  IndexViewHolder) {
                ((IndexViewHolder) holder).setData(map);
            } else  if (holder instanceof ViewHolder) {
                Map<String, Object> map = getValueAt(position);
                ViewHolder vh = (ViewHolder) holder;
                if (map != null) {
                    String pic = JsonUtil.getItemString(map, "pic");
                    vh.iv.setAdjustViewBounds(true);
                    vh.iv.setMaxWidth(AppAndroid.getScreenWidth());
                    vh.iv.setMaxHeight(AppAndroid.getScreenHeight());
                    HImageLoader.displayImage(pic, vh.iv, R.color.white);
                    vh.tvContent.setText(Html.fromHtml(JsonUtil.getItemString(map, "desc")));
                }
            }
        }

        private class IndexViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            TextView tvTime;
            TextView tvSubTitle;
            ImageView iv;
            TextView tvContent;

            public IndexViewHolder(View view) {
                super(view);
                tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvSubTitle = (TextView) view.findViewById(R.id.tvSubTitle);
                iv = (ImageView) view.findViewById(R.id.iv);
                tvContent = (TextView) view.findViewById(R.id.tvContent);
            }

            void setData(Map<String, Object> map) {
                if (map != null) {
                    id = JsonUtil.getItemInt(map, "id");
                    title = JsonUtil.getItemString(map, "title");
                    url = JsonUtil.getItemString(map, "exLink");
                    String time = JsonUtil.getItemString(map, "author");
                    if (!TextUtils.isEmpty(time)) {
                        time += "  ";
                    }
                    time += JsonUtil.getItemString(map, "time");
                    if (TextUtils.isEmpty(time)) {
                        tvTime.setVisibility(View.GONE);
                    } else {
                        tvTime.setVisibility(View.VISIBLE);
                        tvTime.setText(time);
                    }
                    if (TextUtils.isEmpty(url)) {
//                        url = "http://101.201.209.200/MarryTrade/news.html?id=" + id;
                        url = Constants.buildNewsUrl(id);
                    }
                    tvTitle.setText(title);
                    subtitle = JsonUtil.getItemString(map, "subtitle");
                    tvSubTitle.setText(subtitle);
                    if (TextUtils.isEmpty(subtitle)) {
                        tvSubTitle.setVisibility(View.GONE);
                    } else {
                        tvSubTitle.setVisibility(View.VISIBLE);
                    }
                    String pic = JsonUtil.getItemString(map, "figurePic");
                    iv.setAdjustViewBounds(true);
                    iv.setMaxWidth(AppAndroid.getScreenWidth());
                    iv.setMaxHeight(AppAndroid.getScreenHeight());
                    if(threadShareHelper != null) {
                        threadShareHelper.setImageUrl(pic);
                    }
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
    protected void onResume() {
        super.onResume();
        StatService.onResume(InformationActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(InformationActivity.this);
    }
}
