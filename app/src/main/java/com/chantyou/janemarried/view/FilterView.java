package com.chantyou.janemarried.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.sticky.FilterLeftAdapter;
import com.chantyou.janemarried.adapter.sticky.FilterOneAdapter;
import com.chantyou.janemarried.adapter.sticky.FilterRightAdapter;
import com.chantyou.janemarried.data.SharedPreferenceKey;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.CountyRunner;
import com.chantyou.janemarried.model.FilterData;
import com.chantyou.janemarried.model.FilterEntity;
import com.chantyou.janemarried.model.FilterTwoEntity;
import com.chantyou.janemarried.model.Shop.ShopCityBean;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;

import java.util.ArrayList;
import java.util.List;




//筛选 分类
public class FilterView extends LinearLayout implements View.OnClickListener, EventManager.OnEventListener {

    private LinearLayout ll_region;
    private TextView tv_region;
    private ImageView iv_region_arrow;

    private LinearLayout llCategory;
    private TextView tvCategory;
    private ImageView ivCategoryArrow;

    private TextView tvSort;
    private ImageView ivSortArrow;

    private TextView tvFilter;
    private ImageView ivFilterArrow;


    private LinearLayout llSort;

    private LinearLayout llFilter;

    private ListView lvLeft;
    private ListView lvRight;
    private LinearLayout llHeadLayout;
    private LinearLayout llContentListView;
    private View viewMaskBg;

    private Context mContext;
    private Activity mActivity;
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isShowing = false;
    private int filterPosition = -1;
    private int panelHeight;
    private FilterData filterData;

    private FilterEntity selectedRegionEntity; // 被选择的地区项
    private FilterEntity selectedCategoryEntity; // 被选择的分类项
    private FilterEntity selectedSortEntity; // 被选择的排序项
    private FilterEntity selectedFilterEntity; // 被选择的筛选项

    private FilterLeftAdapter leftAdapter;
    private FilterRightAdapter rightAdapter;
    private FilterOneAdapter regionAdapter;
    private FilterOneAdapter categoryAdapter;
    private FilterOneAdapter sortAdapter;
    private FilterOneAdapter filterAdapter;


    private String city;


    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_layout, this);
        initData();
        initView();
        initListener();
    }

    private void initData() {

    }

    private void initView() {
        ll_region = (LinearLayout) findViewById(R.id.ll_region);
        tv_region = (TextView) findViewById(R.id.tv_region);//地区
        iv_region_arrow = (ImageView) findViewById(R.id.iv_region_arrow);

        llCategory = (LinearLayout) findViewById(R.id.ll_category);
        tvCategory = (TextView) findViewById(R.id.tv_category);//分类
        ivCategoryArrow = (ImageView) findViewById(R.id.iv_category_arrow);


        llSort = (LinearLayout) findViewById(R.id.ll_sort);
        tvSort = (TextView) findViewById(R.id.tv_sort);//排序
        ivSortArrow = (ImageView) findViewById(R.id.iv_sort_arrow);


        llFilter = (LinearLayout) findViewById(R.id.ll_filter);
        tvFilter = (TextView) findViewById(R.id.tv_filter);//酒店----暂不开发
        ivFilterArrow = (ImageView) findViewById(R.id.iv_filter_arrow);


        lvLeft = (ListView) findViewById(R.id.lv_left);
        lvRight = (ListView) findViewById(R.id.lv_right);

        //文字部分的总的LinearLayout
        llHeadLayout = (LinearLayout) findViewById(R.id.ll_head_layout);

        //文字部分的总的LinearLayout下面的frameLayout中的LinearLayout，盛放的是listView
        llContentListView = (LinearLayout) findViewById(R.id.ll_content_list_view);

        viewMaskBg = (View) findViewById(R.id.view_mask_bg);//frameLayout中的填充父控件的背景View

        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initListener() {
        ll_region.setOnClickListener(this);
        llCategory.setOnClickListener(this);
        llSort.setOnClickListener(this);
        llFilter.setOnClickListener(this);
        viewMaskBg.setOnClickListener(this);
        llContentListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;//listview可以自己消费touch事件
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_region) {//点击了地区
            filterPosition = 0;
            if (onFilterClickListener != null) {
                onFilterClickListener.onFilterClick(filterPosition);//接口回调，根据filterPosition这个值来进行区分
            }

        } else if (i == R.id.ll_category) {//点击了类型
            filterPosition = 1;
            if (onFilterClickListener != null) {
                onFilterClickListener.onFilterClick(filterPosition);
            }

        } else if (i == R.id.ll_sort) {//点击了排序
            filterPosition = 2;
            if (onFilterClickListener != null) {
                onFilterClickListener.onFilterClick(filterPosition);
            }

        } else if (i == R.id.ll_filter) {
            filterPosition = 3;
            if (onFilterClickListener != null) {
                onFilterClickListener.onFilterClick(filterPosition);
            }

        } else if (i == R.id.view_mask_bg) {
            hide();
        }

    }

    // 复位整个筛选部分的显示状态
    public void resetFilterStatus() {
        tv_region.setTextColor(mContext.getResources().getColor(R.color.font_black_3));
        iv_region_arrow.setImageResource(R.mipmap.home_down_arrow);


        tvCategory.setTextColor(mContext.getResources().getColor(R.color.font_black_3));
        ivCategoryArrow.setImageResource(R.mipmap.home_down_arrow);

        tvSort.setTextColor(mContext.getResources().getColor(R.color.font_black_3));
        ivSortArrow.setImageResource(R.mipmap.home_down_arrow);

        tvFilter.setTextColor(mContext.getResources().getColor(R.color.font_black_3));
        ivFilterArrow.setImageResource(R.mipmap.home_down_arrow);
    }

    // 复位所有的状态
    public void resetAllStatus() {
        resetFilterStatus();
        hide();
    }

    // 显示筛选布局
    public void showFilterLayout(int position) {
        resetFilterStatus();
        switch (position) {
            case 0:
                setRegionAdapter();//地区
                break;
            case 1:
                setCategoryAdapter();//类型
                break;
            case 2:
                setSortAdapter();//排序
                break;
            case 3:
                setFilterAdapter();
                break;
        }

        if (isShowing) return;
        show();
    }

    //1根据城市设置区县数据
    private void setRegionAdapter() {
        tv_region.setTextColor(mActivity.getResources().getColor(R.color.orange));
        iv_region_arrow.setImageResource(R.mipmap.home_up_arrow);
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);
        String ct = SharedPreferenceUtils.getString(mContext, SharedPreferenceKey.userCity, "济南");
        if (!ct.equalsIgnoreCase(city)) {
            city = ct;
            getCounty(ct);
        } else {
            initRegion();
        }
    }

    // 2设置分类数据
    private void setCategoryAdapter() {
        tvCategory.setTextColor(mActivity.getResources().getColor(R.color.orange));
        ivCategoryArrow.setImageResource(R.mipmap.home_up_arrow);
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);
        categoryAdapter = new FilterOneAdapter(mContext, filterData.getCategory());
        lvRight.setAdapter(categoryAdapter);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryEntity = filterData.getCategory().get(position);
                categoryAdapter.setSelectedEntity(selectedCategoryEntity);
                hide();
                if (onItemFilterClickListener != null) {
                    onItemFilterClickListener.onItemFilterClick(selectedCategoryEntity);
                }

                //设置选中分类
                if (selectedCategoryEntity.getKey().equalsIgnoreCase("全部")) {
                    tvCategory.setText("类型");
                } else {
                    tvCategory.setText(selectedCategoryEntity.getKey());
                }
            }
        });
    }


    // 3设置排序数据
    private void setSortAdapter() {
        tvSort.setTextColor(mActivity.getResources().getColor(R.color.orange));
        ivSortArrow.setImageResource(R.mipmap.home_up_arrow);
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);
        sortAdapter = new FilterOneAdapter(mContext, filterData.getSorts());
        lvRight.setAdapter(sortAdapter);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSortEntity = filterData.getSorts().get(position);
                sortAdapter.setSelectedEntity(selectedSortEntity);
                hide();
                if (onItemSortClickListener != null) {
                    onItemSortClickListener.onItemSortClick(selectedSortEntity);
                }
            }
        });
    }

    // 设置筛选数据
    private void setFilterAdapter() {
        tvFilter.setTextColor(mActivity.getResources().getColor(R.color.orange));
        ivFilterArrow.setImageResource(R.mipmap.home_up_arrow);
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);
        filterAdapter = new FilterOneAdapter(mContext, filterData.getFilters());
        lvRight.setAdapter(filterAdapter);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFilterEntity = filterData.getFilters().get(position);
                filterAdapter.setSelectedEntity(selectedFilterEntity);
                hide();
                if (onItemFilterClickListener != null) {
                    onItemFilterClickListener.onItemFilterClick(selectedFilterEntity);
                }
            }
        });
    }

    // 动画显示
    private void show() {
        isShowing = true;
        viewMaskBg.setVisibility(VISIBLE);
        llContentListView.setVisibility(VISIBLE);
        llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                panelHeight = llContentListView.getHeight();
                ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
            }
        });
    }

    // 隐藏动画
    public void hide() {
        isShowing = false;
        resetFilterStatus();
        viewMaskBg.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(llContentListView, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    // 是否吸附在顶部
    public void setStickyTop(boolean stickyTop) {
        isStickyTop = stickyTop;
    }

    // 设置筛选数据
    public void setFilterData(Activity activity, FilterData filterData) {
        this.mActivity = activity;
        this.filterData = filterData;
    }

    // 是否显示
    public boolean isShowing() {
        return isShowing;
    }

    // 筛选视图点击
    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }


    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }


    // 地区item点击
    private OnItemRegionClickListener onItemRegionClickListener;


    public void setOnItemRegionClickListener(OnItemRegionClickListener onItemRegionClickListener) {
        this.onItemRegionClickListener = onItemRegionClickListener;
    }

    public interface OnItemRegionClickListener {
        void onItemRegionClick(FilterEntity entity);
    }


    // 分类Item点击
    private OnItemCategoryClickListener onItemCategoryClickListener;

    public void setOnItemCategoryClickListener(OnItemCategoryClickListener onItemCategoryClickListener) {
        this.onItemCategoryClickListener = onItemCategoryClickListener;
    }

    public interface OnItemCategoryClickListener {
        void onItemCategoryClick(FilterTwoEntity entity);
    }

    // 排序Item点击
    private OnItemSortClickListener onItemSortClickListener;

    public void setOnItemSortClickListener(OnItemSortClickListener onItemSortClickListener) {
        this.onItemSortClickListener = onItemSortClickListener;
    }

    public interface OnItemSortClickListener {
        void onItemSortClick(FilterEntity entity);
    }

    // 筛选Item点击
    private OnItemFilterClickListener onItemFilterClickListener;

    public void setOnItemFilterClickListener(OnItemFilterClickListener onItemFilterClickListener) {
        this.onItemFilterClickListener = onItemFilterClickListener;
    }

    public interface OnItemFilterClickListener {
        void onItemFilterClick(FilterEntity entity);
    }


    //根据城市获取区县
    private void getCounty(String city) {
        if (TextUtils.isEmpty(city)) {
            return;
        }
        ((XBaseActivity) mContext).pushEvent(new CountyRunner(city), this);
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) mContext).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_CITY_COUNTY://区县列表
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopCityBean shopCityBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopCityBean.class);
                    List<ShopCityBean.DataBean> shopCityDataList = shopCityBean.getData();
                    List<FilterEntity> list = new ArrayList<FilterEntity>();
                    FilterEntity fil = new FilterEntity();
                    fil.setKey("全部");
                    fil.setValue("");
                    list.add(fil);
                    for (int i = 0; i < shopCityDataList.size(); i++) {
                        FilterEntity filterEntity = new FilterEntity();
                        filterEntity.setKey(shopCityDataList.get(i).getArea());
                        filterEntity.setValue(shopCityDataList.get(i).getArea());
                        list.add(filterEntity);
                    }
                    filterData.setRegion(list);
                    initRegion();
                }
                break;
        }
    }


    //初始化区县
    private void initRegion() {

        regionAdapter = new FilterOneAdapter(mContext, filterData.getRegion());
        lvRight.setAdapter(regionAdapter);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedRegionEntity = filterData.getRegion().get(position);
                regionAdapter.setSelectedEntity(selectedRegionEntity);
                hide();
                if (onItemRegionClickListener != null) {
                    onItemRegionClickListener.onItemRegionClick(selectedRegionEntity);
                }

                String selectValue = "地区";
                //设置选中的值
                if (!selectedRegionEntity.getKey().equalsIgnoreCase("全部")) {
                    selectValue = selectedRegionEntity.getKey();
                }
                tv_region.setText(selectValue);

            }
        });
    }

}
