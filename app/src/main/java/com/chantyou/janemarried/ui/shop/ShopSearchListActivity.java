package com.chantyou.janemarried.ui.shop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.shop.ShopListAdapter;
import com.chantyou.janemarried.config.LoadType;
import com.chantyou.janemarried.data.SharedPreferenceKey;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.ShopListRunner;
import com.chantyou.janemarried.model.ChannelEntity;
import com.chantyou.janemarried.model.FilterData;
import com.chantyou.janemarried.model.FilterEntity;
import com.chantyou.janemarried.model.Shop.ShopListBean;
import com.chantyou.janemarried.ui.base.BaseTabsActivity;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.chantyou.janemarried.utils.sticky.ColorUtil;
import com.chantyou.janemarried.utils.sticky.DensityUtil;
import com.chantyou.janemarried.utils.sticky.ModelUtil;
import com.chantyou.janemarried.view.FilterView;
import com.chantyou.janemarried.view.HeaderAdViewView;
import com.chantyou.janemarried.view.HeaderChannelViewView;
import com.chantyou.janemarried.view.HeaderDividerViewView;
import com.chantyou.janemarried.view.HeaderFilterViewView;
import com.chantyou.janemarried.view.SmoothListView.SmoothListView;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.chantyou.janemarried.config.UrlConfig.SHOP_LIST;

//店铺搜索
public class ShopSearchListActivity extends BaseTabsActivity implements SmoothListView.ISmoothListViewListener, EventManager.OnEventListener {
    private SmoothListView smoothListView;
    private FilterView filterView;

    private RelativeLayout rlBar;
    private TextView tvTitle;
    private FrameLayout flActionMore;
    private View viewTitleBg;
    private View viewActionMoreBg;


    private int mScreenHeight; // 屏幕高度

    //    private List<ShopADListBean.DataBean> adList = new ArrayList<ShopADListBean.DataBean>(); //轮播图数据
    private List<String> adList = new ArrayList<String>(); //轮播图数据
    private List<ChannelEntity> channelList = new ArrayList<>(); // 分类数据
    //    private List<OperationEntity> operationList = new ArrayList<>(); // 运营数据
    private List<ShopListBean.DataBean> shopList = new ArrayList<>(); // ListView数据

    private HeaderAdViewView listViewAdHeaderView; // 轮播图
    private HeaderChannelViewView headerChannelView; // 分类
    //    private HeaderOperationViewView headerOperationViewView; // 隐藏了
    private HeaderDividerViewView headerDividerViewView; // 分割线占位图
    private HeaderFilterViewView headerFilterViewView; //筛选
    private FilterData filterData; // 筛选数据
    private ShopListAdapter mAdapter; // 主页数据

    private View itemHeaderAdView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int titleViewHeight = 0; // 标题栏的高度
    private int filterPosition = -1; // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)

    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 3; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离


    private static final String url = SHOP_LIST;

    private int page = 1;
    private int pageSize = 20;
    private int loadType = LoadType.Refresh;

    private int orderType = 1;
    private String merryType = "";//店铺类型


    private String city = "";//市
    private String county = "";//区

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shop_search_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        merryType = getIntent().getStringExtra("merryType");
        System.out.println("zhuwx:merryType," + merryType);
        initData();


        smoothListView = (SmoothListView) findViewById(R.id.listView);
        filterView = (FilterView) findViewById(R.id.fv_top_filter);

        rlBar = (RelativeLayout) findViewById(R.id.rl_bar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        flActionMore = (FrameLayout) findViewById(R.id.fl_action_more);

        viewTitleBg = (View) findViewById(R.id.view_title_bg);
        viewActionMoreBg = (View) findViewById(R.id.view_action_more_bg);


        filterView.setVisibility(View.GONE);

        // 设置筛选数据
        filterView.setFilterData(ShopSearchListActivity.this, filterData);

        // 设置筛选数据
        headerFilterViewView = new HeaderFilterViewView(ShopSearchListActivity.this);
        headerFilterViewView.fillView(new Object(), smoothListView);

        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;

        initListener();


    }

    @Override
    protected void setupViewPager(BaseFragmentPagerAdapter adapter) {

    }


    //初始数据
    private void initData() {

        mScreenHeight = DensityUtil.getWindowHeight(ShopSearchListActivity.this);

        // 筛选数据
        filterData = new FilterData();
        filterData.setCategory(ModelUtil.getCategoryOneData());//类型
        filterData.setSorts(ModelUtil.getSortData());//排序


        //所在城市
        city = SharedPreferenceUtils.getString(ShopSearchListActivity.this, SharedPreferenceKey.userCity, "济南");
        //获取商铺列表
        getShopList();

        addAndroidEventListener(XEventCode.HTTP_SHOP_LIST, this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listViewAdHeaderView != null) {
            listViewAdHeaderView.stopADRotate();
        }
        removeEventListener(XEventCode.HTTP_SHOP_LIST, this);
    }


    //获取商铺列表
    private void getShopList() {
        pushEvent(new ShopListRunner("", city, county, merryType, "", orderType + "", page + "", pageSize + ""), this);
    }


    private void initListener() {
        // (假的ListView头部展示的)筛选视图点击
        headerFilterViewView.setOnFilterClickListener(new HeaderFilterViewView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(ShopSearchListActivity.this, titleViewHeight));
            }
        });

        // (真正的)筛选视图点击
        filterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (isStickyTop) {
                    filterPosition = position;
                    filterView.showFilterLayout(position);
                    if (titleViewHeight - 3 > filterViewTopSpace || filterViewTopSpace > titleViewHeight + 3) {
                        smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(ShopSearchListActivity.this, titleViewHeight));
                    }
                }


            }
        });

        //1 地区
        filterView.setOnItemRegionClickListener(new FilterView.OnItemRegionClickListener() {
            @Override
            public void onItemRegionClick(FilterEntity entity) {
                city = SharedPreferenceUtils.getString(ShopSearchListActivity.this, SharedPreferenceKey.userCity, "济南");
                county = entity.getValue();
                getShopList();
                filterView.setVisibility(View.GONE);
            }
        });


        // 2分类
        filterView.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                merryType = entity.getValue();
                getShopList();
                filterView.setVisibility(View.GONE);
            }
        });

        //3 排序
        filterView.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                orderType = Integer.parseInt(entity.getValue());
                getShopList();
                filterView.setVisibility(View.GONE);
            }
        });


        smoothListView.setRefreshEnable(true);
        smoothListView.setLoadMoreEnable(true);
        smoothListView.setSmoothListViewListener(this);
        smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && adViewTopSpace < 0) return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = smoothListView.getChildAt(1 - firstVisibleItem);
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = DensityUtil.px2dip(ShopSearchListActivity.this, itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(ShopSearchListActivity.this, itemHeaderAdView.getHeight());
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopSpace = DensityUtil.px2dip(ShopSearchListActivity.this, itemHeaderFilterView.getTop());
                }

                // 处理筛选是否吸附在顶部
                if (filterViewTopSpace > titleViewHeight) {
                    isStickyTop = false; // 没有吸附在顶部
                    filterView.setVisibility(View.GONE);
                } else {
                    isStickyTop = true; // 吸附在顶部
                    filterView.setVisibility(View.VISIBLE);
                }

                if (firstVisibleItem > filterViewPosition) {
                    isStickyTop = true;
                    filterView.setVisibility(View.VISIBLE);
                }

                if (isSmooth && isStickyTop) {
                    isSmooth = false;
                    filterView.showFilterLayout(filterPosition);
                }

                filterView.setStickyTop(isStickyTop);

                // 处理标题栏颜色渐变
                handleTitleBarColorEvaluate();
            }
        });

    }


    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (adViewTopSpace > 0) {
            fraction = 1f - adViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(adViewTopSpace) * 1f;
        fraction = space / (adViewHeight - titleViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rlBar.setAlpha(1f);

        if (fraction >= 1f || isStickyTop) {
            isStickyTop = true;
            viewTitleBg.setAlpha(0f);
            viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(ShopSearchListActivity.this.getResources().getColor(R.color.orange));
        } else {
            viewTitleBg.setAlpha(1f - fraction);
            viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(ShopSearchListActivity.this, fraction, R.color.transparent, R.color.orange));
        }
    }


    //刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                loadType = LoadType.Refresh;
                getShopList();
                smoothListView.stopRefresh();
                smoothListView.setRefreshTime("刚刚");
            }
        }, 2);
    }

    //加载更多
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                loadType = LoadType.LoadMore;
                getShopList();
                smoothListView.stopLoadMore();
                filterView.setVisibility(View.GONE);
            }
        }, 2);
    }


    //后台返回数据
    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SHOP_LIST://店铺列表
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopListBean shopListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopListBean.class);
                    System.out.println("zhuwx:" + shopListBean.getMsg());
                    if (null != shopList) {
                        if (shopList.size() > 0) {
                            if (LoadType.Refresh == loadType) {
                                shopList.clear();
                            }
                        } else {
                            shopList = new ArrayList<ShopListBean.DataBean>();
                        }
                    }
                    shopList.addAll(shopListBean.getData());
                    mAdapter = new ShopListAdapter(ShopSearchListActivity.this, shopList);
                    smoothListView.setAdapter(mAdapter);


                    if (null != shopListBean.getData() && shopListBean.getData().size() < 1) {
                        if (LoadType.LoadMore != loadType) {
                            smoothListView.setLoadMoreEnable(false);
                            int height = mScreenHeight - DensityUtil.dip2px(ShopSearchListActivity.this, 95); // 95 = 标题栏高度 ＋ FilterView的高度
                            shopList.addAll(ModelUtil.getNoDataShopListEntity(height));
                            mAdapter.setData(shopList);
                        }

                    } else {
                        mAdapter.setData(shopList);
                        smoothListView.setLoadMoreEnable(true);
                    }

                    filterViewPosition = smoothListView.getHeaderViewsCount() - 1;


                }
                break;


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        StatService.onResume(ShopSearchListActivity.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(ShopSearchListActivity.this);
    }

}
