package com.mhh.lib.ui.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mhh.lib.R;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;

import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;
import space.sye.z.library.manager.RefreshRecyclerAdapterManager;
import space.sye.z.library.widget.RefreshRecyclerView;

/**
 * Created by j_turn on 2015/12/16.
 * Email 766082577@qq.com
 */
public abstract class BottomLoadFragment extends BaseFragment implements OnBothRefreshListener {

    protected int pageCur;

    private RefreshRecyclerAdapterManager recManager;
    protected RefreshRecyclerView mRefreshRecyclerView;

    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);
        mRefreshRecyclerView = (RefreshRecyclerView) root.findViewById(R.id.lv_refrecyclerview);
        setupRecyclerView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.lv_refreshmoreload_recyclerview;
    }

    protected abstract void setupRecyclerView();

    protected void setupRecyclerView(RecyclerView.LayoutManager manager,
                         BaseRecyclerViewAdapter adapter, RecyclerMode mode) {
        recManager = RecyclerViewManager.with(mRefreshRecyclerView, manager, adapter, mode, this);
    }

    /**
     * 是否加载更多
     * @param hasMore
     */
    protected void hasMore(boolean hasMore) {
        if(mRefreshRecyclerView != null/* && mRefreshRecyclerView.canLoadMore()*/) {
//            mRefreshRecyclerView.hasMore(hasMore);
            recManager.hasMore(hasMore);
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
    }

    @Override
    public void onPullDown() {

    }

    @Override
    public void onLoadMore() {

    }
}
