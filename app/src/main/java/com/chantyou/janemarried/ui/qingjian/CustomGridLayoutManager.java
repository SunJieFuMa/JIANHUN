package com.chantyou.janemarried.ui.qingjian;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/2/23.
 */
public class CustomGridLayoutManager extends GridLayoutManager {
    /*
    自定义这个类是为了让recyclerView不能滑动，而且上拉下拉不会有阴影
    因为这里针对的是GridLayoutManager类型的RecyclerView，所以就继承GridLayoutManager。
    LinearLayout类型的RecyclerView也可以继承LinearLayoutManager
     */
    private boolean isScrollEnabled = true;

    public CustomGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CustomGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
