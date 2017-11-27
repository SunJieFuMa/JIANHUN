package com.chantyou.janemarried.ui.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.baidu.mobstat.StatService;
import com.mhh.lib.R;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;

import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;
import space.sye.z.library.manager.RefreshRecyclerAdapterManager;
import space.sye.z.library.widget.RefreshRecyclerView;

/**
 * 支持下拉和加载更多内容的 RecyclerView 基本类
 * Created by j_turn on 2015/12/19.
 */
public abstract class PullrefreshBottomloadActivity extends XBaseActivity implements OnBothRefreshListener {

    protected int pageCur;
    private RefreshRecyclerAdapterManager recManager;
    protected RefreshRecyclerView mRefreshRecyclerView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        super.setContentView(getLayoutResId());
    }

    @Override
    protected void init() {
        super.init();
        mRefreshRecyclerView = (RefreshRecyclerView) findViewById(R.id.lv_refrecyclerview);
        setupRecyclerView();
    }

    /**
     * 默认布局文件ID（R.layout.activity_pullrefreshbottomload）
     * @return
     */
    protected int getLayoutResId() {
        return R.layout.activity_pullrefreshbottomload;
    }

    /**
     * 禁用
     * @param layoutResID
     */
    @Deprecated
    @Override
    public void setContentView(int layoutResID) {
        throw new RuntimeException("请用getLayoutResId()方法设置布局文件Id");
    }

    /**
     * 先于 init()方法执行
     */
    protected abstract void setupRecyclerView();

    protected RefreshRecyclerAdapterManager setupRecyclerView(RecyclerView.LayoutManager
            manager, BaseRecyclerViewAdapter adapter, RecyclerMode mode) {
        recManager = RecyclerViewManager.with(mRefreshRecyclerView, manager, adapter, mode, this);
        return recManager;
    }

    /**
     * 是否加载更多
     * @param hasMore
     */
    protected void hasMore(boolean hasMore) {
        if(mRefreshRecyclerView != null/* && mRefreshRecyclerView.canLoadMore()*/) {
            recManager.hasMore(hasMore);
//            mRefreshRecyclerView.hasMore(hasMore);
        }
    }

    protected void setOnItemClickListener (RefreshRecyclerViewAdapter.OnItemClickListener onItemClickListener){
        if (null == recManager) {
            throw new RuntimeException("adapter has not been inited");
        }
        recManager.setOnItemClickListener(onItemClickListener);
    }

    protected void setOnItemLongClickListener(RefreshRecyclerViewAdapter.OnItemLongClickListener onItemLongClickListener){
        if (null == recManager) {
            throw new RuntimeException("adapter has not been inited");
        }
        recManager.setOnItemLongClickListener(onItemLongClickListener);
    }

    /**
     * 数据加载完成
     */
    protected void onRefreshCompleted() {
        RecyclerViewManager.onRefreshCompleted(recManager);
        RecyclerViewManager.notifyDataSetChanged(recManager);
    }

    @Override
    public void onPullDown() {

    }

    @Override
    public void onLoadMore() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PullrefreshBottomloadActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PullrefreshBottomloadActivity.this);
    }

}
