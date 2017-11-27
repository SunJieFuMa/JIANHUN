package com.chantyou.janemarried.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.chantyou.janemarried.R;



/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderFilterViewView extends HeaderViewInterface<Object> implements FilterView.OnFilterClickListener  {

    private FilterView fvFilter;
    private TextView tv_region;//地区
    private TextView tv_category;//筛选
    private TextView tv_sort;//排序

    public HeaderFilterViewView(Activity context) {
        super(context);
    }

    public  void setVisibility(int visibility){
        fvFilter.setVisibility(visibility);
    }

    //设置地区
    public void setRegionText(String text){
        tv_region.setText(text);
    }
    //设置分类
    public void setCategoryText(String text){
        tv_category.setText(text);
    }
    //设置排序
    public void setSortText(String text){
        tv_sort.setText(text);
    }


    @Override
    protected void getView(Object obj, ListView listView) {
        View view = mInflate.inflate(R.layout.header_filter_layout, listView, false);
        tv_region = (TextView) view.findViewById(R.id.tv_region);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_sort = (TextView) view.findViewById(R.id.tv_sort);
        fvFilter = (FilterView) view.findViewById(R.id.fv_filter);

        dealWithTheView(obj);
        listView.addHeaderView(view);
    }

    // 获得筛选View
    public FilterView getFilterView() {
        return fvFilter;
    }

    private void dealWithTheView(Object obj) {
        fvFilter.setOnFilterClickListener(this);
    }

    @Override
    public void onFilterClick(int position) {
        if (onFilterClickListener != null) {
            onFilterClickListener.onFilterClick(position);
        }
    }

    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }


    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }



}
