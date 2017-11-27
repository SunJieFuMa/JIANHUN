/**
 * @(#) z.sye.space.refreshrecyclerview.Manager 2015/11/19;
 * <p/>
 * Copyright (c), 2009 深圳孔方兄金融信息服务有限公司（Shenzhen kfxiong
 * Financial Information Service Co. Ltd.）
 * <p/>
 * 著作权人保留一切权利，任何使用需经授权。
 */
package space.sye.z.library.manager;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.listener.OnLoadMoreListener;
import space.sye.z.library.listener.OnPullDownListener;
import space.sye.z.library.widget.RefreshRecyclerView;

/**
 * Created by mhh on 2016/07/15 in Suzhou.
 * Email：766082577@qq.com
 */
public class RecyclerViewManager {

    public static RefreshRecyclerAdapterManager with(RefreshRecyclerView refRecyclerView, RecyclerView.LayoutManager
                             layoutManager, RecyclerView.Adapter adapter, RecyclerMode mode, final OnBothRefreshListener listener) {
        if(refRecyclerView == null) {
            throw new NullPointerException("Couldn't resolve a null object reference of RecyclerView");
        }
        if (null == adapter) {
            throw new NullPointerException("Couldn't resolve a null object reference of RecyclerView.Adapter");
        }
        if (null == layoutManager) {
            throw new NullPointerException("Couldn't resolve a null object reference of RecyclerView.LayoutManager");
        }
        RefreshRecyclerAdapterManager recManager = new RefreshRecyclerAdapterManager(adapter, layoutManager);
        recManager.setMode(mode);
        if(mode == RecyclerMode.TOP) {
            recManager.setOnPullDownListener(new OnPullDownListener() {
                @Override
                public void onPullDown() {
                    if (listener != null) {
                        listener.onPullDown();
                    }
                }
            });
        } else if(mode == RecyclerMode.BOTTOM) {
            recManager.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (listener != null) {
                        listener.onLoadMore();
                    }
                }
            });
        } else if(mode == RecyclerMode.BOTH) {
            recManager.setOnBothRefreshListener(listener);
        }
        recManager.into(refRecyclerView, refRecyclerView.getContext());
        return recManager;
    }

    public static void notifyDataSetChanged(RefreshRecyclerAdapterManager refManager) {
        if (null == refManager) {
            throw new RuntimeException("adapter has not been inited");
        }
        refManager.getAdapter().notifyDataSetChanged();
    }

    public static void onRefreshCompleted(RefreshRecyclerAdapterManager refManager) {
        if (null == refManager) {
            throw new RuntimeException("adapter has not been inited");
        }
        refManager.onRefreshCompleted();
    }

//    public static void setMode(RefreshRecyclerAdapterManager refManager, RecyclerMode mode){
//        if (null == refManager) {
//            throw new RuntimeException("adapter has not been inited");
//        }
//        refManager.setMode(mode);
//    }

    public static void setLayoutManager(RefreshRecyclerAdapterManager refManager, RecyclerView.LayoutManager layoutManager){
        if (null == refManager) {
            throw new RuntimeException("adapter has not been inited");
        }
        if (layoutManager instanceof GridLayoutManager){
            //如果是header或footer，设置其充满整列
            ((GridLayoutManager)layoutManager).setSpanSizeLookup(
                    new HeaderSapnSizeLookUp(refManager.getAdapter(), (GridLayoutManager) layoutManager));
        }
        refManager.getAdapter().putLayoutManager(layoutManager);
        refManager.getRecyclerView().setLayoutManager(layoutManager);
    }

    /**
     * Replace RecyclerView.ViewHolder.getLayoutPosition() with this
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        if (null != recyclerView && null != recyclerView.getAdapter() && null != holder) {
            RecyclerView.Adapter mAdapter = recyclerView.getAdapter();
            if (mAdapter instanceof RefreshRecyclerViewAdapter) {
                int headersCount = ((RefreshRecyclerViewAdapter) mAdapter).getHeadersCount();
                if (headersCount > 0) {
                    return holder.getLayoutPosition() - headersCount;
                }
            }
            return holder.getLayoutPosition();
        } else if (null == recyclerView) {
            throw new NullPointerException("RefreshRecyclerView cannot be null");
        } else if (null == recyclerView.getAdapter()) {
            throw new NullPointerException("RecyclerViewAdapter cannot be null");
        } else {
            throw new NullPointerException("RecyclerView.ViewHolde cannot be null");
        }
    }

    /**
     * Replace RecyclerView.ViewHolder.getLayoutPosition() with this
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        if (null != recyclerView && null != recyclerView.getAdapter() && null != holder) {
            RecyclerView.Adapter mAdapter = recyclerView.getAdapter();
            if (mAdapter instanceof RefreshRecyclerViewAdapter) {
                int headersCount = ((RefreshRecyclerViewAdapter) mAdapter).getHeadersCount();
                if (headersCount > 0) {
                    return holder.getAdapterPosition() - headersCount;
                }
            }
            return holder.getAdapterPosition();
        } else if (null == recyclerView) {
            throw new NullPointerException("RefreshRecyclerView cannot be null");
        } else if (null == recyclerView.getAdapter()) {
            throw new NullPointerException("RecyclerViewAdapter cannot be null");
        } else {
            throw new NullPointerException("RecyclerView.ViewHolder cannot be null");
        }
    }

}
