package space.sye.z.library.manager;

import android.support.v7.widget.GridLayoutManager;

import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;

/**
 * Created by Syehunter on 2015/11/22.
 * Email 766082577@qq.com
 */
public class HeaderSapnSizeLookUp extends GridLayoutManager.SpanSizeLookup {

    private RefreshRecyclerViewAdapter mAdapter;
    private int mSpanSize;
    private GridLayoutManager.SpanSizeLookup lookup;

    public HeaderSapnSizeLookUp(RefreshRecyclerViewAdapter adapter, GridLayoutManager manager){
        this.mAdapter = adapter;
        this.mSpanSize = manager.getSpanCount();
        this.lookup = manager.getSpanSizeLookup();
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = mAdapter.isHeader(position) || mAdapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : (lookup != null ? lookup.getSpanSize(position) : 1);
    }
}
