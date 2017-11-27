package com.chantyou.janemarried.ui.shop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.shop.ShopListAdapter;
import com.chantyou.janemarried.config.LoadType;
import com.chantyou.janemarried.data.SharedPreferenceKey;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.ShopADPicListRunner;
import com.chantyou.janemarried.httprunner.shop.ShopListRunner;
import com.chantyou.janemarried.model.ChannelEntity;
import com.chantyou.janemarried.model.FilterData;
import com.chantyou.janemarried.model.FilterEntity;
import com.chantyou.janemarried.model.Shop.ShopADListBean;
import com.chantyou.janemarried.model.Shop.ShopListBean;
import com.chantyou.janemarried.ui.base.XBaseActivity;
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
import com.mhh.lib.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.chantyou.janemarried.config.UrlConfig.SHOP_LIST;


//店铺

/**
 * 商户
 */
public class ExcellentShopFragment extends BaseFragment implements SmoothListView.ISmoothListViewListener, EventManager.OnEventListener {

    private SmoothListView smoothListView;
    private FilterView fvTopFilter;

    private RelativeLayout rlBar;
    private View viewTitleBg;
    private View viewActionMoreBg;

    private Context mContext;
    private Activity mActivity;

    private int mScreenHeight; // 屏幕高度

    private List<String> adList = new ArrayList<String>(); //轮播图数据
    private List<ChannelEntity> channelList = new ArrayList<>(); // 分类数据
    private List<ShopListBean.DataBean> shopList = new ArrayList<>(); // ListView数据

    private HeaderAdViewView listViewAdHeaderView; // 轮播图
    private HeaderChannelViewView headerChannelView; // 分类
    private HeaderDividerViewView headerDividerViewView; // 分割线占位图
    private HeaderFilterViewView headerFilterViewView; //筛选
    private FilterData filterdata; // 筛选数据
    private ShopListAdapter mAdapter; // 主页数据

    private View itemHeaderAdView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int titleViewHeight = 0; // 标题栏的高度
    private int filterPosition = -1; // 点击FilterView的位置：地区(0)、类型(1)、排序(2)

    private int adViewHeight = 200; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 3; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离


    private static final String url = SHOP_LIST;

    private int page = 1;
    private int pageSize = 20;
    private int loadType = LoadType.Refresh;

    private int orderType = 3;//值为3的话是默认按案例数量排序
    private String merryType = "";//店铺类型

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private String city = "";//市
    private String county = "";//区

    @Override
    protected int getLayoutId() {
        return R.layout.frame_shop;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = ((XBaseActivity) getActivity());
        mActivity = ((XBaseActivity) getActivity());
    }


    //初始数据
    private void initData() {
        mScreenHeight = DensityUtil.getWindowHeight(mActivity);

        // 筛选数据
        filterdata = new FilterData();
        filterdata.setCategory(ModelUtil.getCategoryOneData());//类型---四大金刚那部分的文字
        filterdata.setSorts(ModelUtil.getSortData());//排序

        // 轮播图
        adList = ModelUtil.getAdData();

        //频道（模块）
        channelList = ModelUtil.getChannelData();//类型---四大金刚那部分

        //所在城市
        city = SharedPreferenceUtils.getString(mContext, SharedPreferenceKey.userCity, "济南");

        //获取轮播图
        getShopAdList();
        //获取商铺列表
        getShopList2();//改成了2

        ((XBaseActivity) getActivity()).addAndroidEventListener(XEventCode.HTTP_SHOP_LIST, this);

    }


    //1. 获取商铺轮播图
    private void getShopAdList() {
        ((XBaseActivity) getActivity()).pushEvent(new ShopADPicListRunner(), this);
    }

    //2. 获取商铺列表
    private void getShopList() {
        //必须重新获取city  因为修改之后没有回调修改的地方
        city = SharedPreferenceUtils.getString(mContext, SharedPreferenceKey.userCity, "济南");
        ((XBaseActivity) getActivity()).pushEvent(new ShopListRunner("", city, county, merryType, "", orderType + "", page + "", pageSize + ""), this);

    }
    //2. 获取商铺列表
    private void getShopList2() {
        //必须重新获取city  因为修改之后没有回调修改的地方
        city = SharedPreferenceUtils.getString(mContext, SharedPreferenceKey.userCity, "济南");
        //把排序也就是倒数第3个参数赋为空字符串
        ((XBaseActivity) getActivity()).pushEvent(new ShopListRunner("", city, county, merryType, "", "", page + "", pageSize + ""), this);
    }

    //初始化视图
    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);

        initData();

        smoothListView = (SmoothListView) mRootView.findViewById(R.id.listView);
        fvTopFilter = (FilterView) mRootView.findViewById(R.id.fv_top_filter);//这就是那个悬浮的筛选部分视图，默认隐藏

        rlBar = (RelativeLayout) mRootView.findViewById(R.id.rl_bar);

        viewTitleBg = (View) mRootView.findViewById(R.id.view_title_bg);
        viewActionMoreBg = (View) mRootView.findViewById(R.id.view_action_more_bg);

        fvTopFilter.setVisibility(View.GONE);


        //填充轮播图
        listViewAdHeaderView = new HeaderAdViewView(mActivity);
        listViewAdHeaderView.fillView(smoothListView);

        //填充分类数据
        headerChannelView = new HeaderChannelViewView(mActivity);
        headerChannelView.fillView(channelList, smoothListView);

        // 设置分割线
        headerDividerViewView = new HeaderDividerViewView(mActivity);
        headerDividerViewView.fillView("", smoothListView);

        // 设置筛选数据
        headerFilterViewView = new HeaderFilterViewView(mActivity);//将这个筛选视图添加到listView的头部
        headerFilterViewView.fillView(new Object(), smoothListView);

        // 设置筛选数据
        fvTopFilter.setFilterData(mActivity, filterdata);//悬浮的那个视图

        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;

        initListener();
    }


    //处理事件
    private void initListener() {
        // (假的ListView头部展示的)筛选视图点击
        headerFilterViewView.setOnFilterClickListener(new HeaderFilterViewView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(mContext, titleViewHeight));

            }
        });

        // (真正的)筛选视图点击----悬浮的那个
        fvTopFilter.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (isStickyTop) {
                    filterPosition = position;
                    fvTopFilter.showFilterLayout(position);
                    if (titleViewHeight - 3 > filterViewTopSpace || filterViewTopSpace > titleViewHeight + 3) {
                        smoothListView.smoothScrollToPositionFromTop(filterViewPosition,
                                DensityUtil.dip2px(mContext, titleViewHeight));
                    }
                }

            }
        });

        //1 地区
        fvTopFilter.setOnItemRegionClickListener(new FilterView.OnItemRegionClickListener() {
            @Override
            public void onItemRegionClick(FilterEntity entity) {
                city = SharedPreferenceUtils.getString(mContext, SharedPreferenceKey.userCity, "济南");
                county = entity.getValue();

                page = 1;
                loadType = LoadType.Load;
//                getShopList();
                getShopList2();//改成2
                fvTopFilter.setVisibility(View.GONE);

                if (entity.getKey().equalsIgnoreCase("全部")) {
                    headerFilterViewView.setRegionText("地区");
                } else {
                    headerFilterViewView.setRegionText(entity.getKey());
                }
            }
        });


        // 2分类
        fvTopFilter.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                merryType = entity.getValue();
                page = 1;
                loadType = LoadType.Load;
//                getShopList();
                getShopList2();//改成2
                fvTopFilter.setVisibility(View.GONE);
                if (entity.getKey().equalsIgnoreCase("全部")) {
                    headerFilterViewView.setCategoryText("类型");
                } else {
                    headerFilterViewView.setCategoryText(entity.getKey());
                }
            }
        });


        //3 排序
        fvTopFilter.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                orderType = Integer.parseInt(entity.getValue());
                page = 1;
                loadType = LoadType.Load;
                getShopList();
                fvTopFilter.setVisibility(View.GONE);
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
                if (isScrollIdle && adViewTopSpace < 0)
                    return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = smoothListView.getChildAt(1 - firstVisibleItem);
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(mContext, itemHeaderAdView.getHeight());
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderFilterView.getTop());
                }

                // 处理筛选是否吸附在顶部
                if (filterViewTopSpace > titleViewHeight) {
                    isStickyTop = false; // 没有吸附在顶部
                    fvTopFilter.setVisibility(View.GONE);//悬浮视图隐藏
                } else {
                    isStickyTop = true; // 吸附在顶部
                    fvTopFilter.setVisibility(View.VISIBLE);//悬浮视图显示
                }

                if (firstVisibleItem > filterViewPosition) {
                    isStickyTop = true;
                    fvTopFilter.setVisibility(View.VISIBLE);
                }

                if (isSmooth && isStickyTop) {
                    isSmooth = false;
                    fvTopFilter.showFilterLayout(filterPosition);
                }

                fvTopFilter.setStickyTop(isStickyTop);

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
            if (fraction < 0f)
                fraction = 0f;
            rlBar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(adViewTopSpace) * 1f;
        fraction = space / (adViewHeight - titleViewHeight);
        if (fraction < 0f)
            fraction = 0f;
        if (fraction > 1f)
            fraction = 1f;
        rlBar.setAlpha(1f);

        if (fraction >= 1f || isStickyTop) {
            isStickyTop = true;
            viewTitleBg.setAlpha(0f);
            viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
        } else {
            viewTitleBg.setAlpha(1f - fraction);
            viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.orange));
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
//                getShopList();
                getShopList2();//改成2
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
                getShopList2();//改成2
                smoothListView.stopLoadMore();
                fvTopFilter.setVisibility(View.GONE);
            }
        }, 2);
    }


    //后台返回数据
    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SHOP_LIST://店铺列表
                fvTopFilter.setVisibility(View.GONE);
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopListBean shopListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopListBean.class);

                    //                    if (null != shopList) {
                    //                        if (shopList.size() > 0) {
                    //                            if (LoadType.Refresh == loadType) {
                    //                                shopList.clear();
                    //                            }
                    //                        } else {
                    //                            shopList = new ArrayList<ShopListBean.DataBean>();
                    //                        }
                    //                    }
                    //                    shopList.addAll(shopListBean.getData());
                    //                    mAdapter = new ShopListAdapter(mActivity, shopList);
                    //                    smoothListView.setAdapter(mAdapter);

                    //                    filterViewPosition = smoothListView.getHeaderViewsCount() - 1;

                    if (null != shopList) {
                        if (shopList.size() > 0) {
                            if (LoadType.Refresh == loadType || LoadType.Load == loadType) {
                                shopList.clear();
                            }
                        } else {
                            shopList = new ArrayList<ShopListBean.DataBean>();
                        }
                    }
                    shopList.addAll(shopListBean.getData());


                    if (null == mAdapter) {
                        mAdapter = new ShopListAdapter(mActivity, shopList);
                        smoothListView.setAdapter(mAdapter);
                    }


                    if (null != shopListBean.getData() && shopListBean.getData().size() < 1) {
                        if (LoadType.LoadMore != loadType) {
                            smoothListView.setLoadMoreEnable(false);
                            int height = mScreenHeight - DensityUtil.dip2px(mContext, 95); // 95 = 标题栏高度 ＋ FilterView的高度
                            shopList.addAll(ModelUtil.getNoDataShopListEntity(height));
                            mAdapter.setData(shopList);
                        }

                    } else {
                        mAdapter.setData(shopList);
                        smoothListView.setLoadMoreEnable(true);
                    }

                    filterViewPosition = smoothListView.getHeaderViewsCount() - 1;
                    /*就是他大爷的下面这个地方，改成LoadMore就好了，我调了4个小时终于他妈的调好了*/
                    if (LoadType.LoadMore != loadType) {
                        smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(mContext, titleViewHeight - 1));
                    }
                    //                    fvTopFilter.setVisibility(View.GONE);
                    //                    headerFilterViewView.setVisibility(View.GONE);

                    loadType = LoadType.Refresh;
                }
                break;
            case XEventCode.HTTP_SHOP_AD_LIST: {//轮播图
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopADListBean shopAdList = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopADListBean.class);
                    List<ShopADListBean.DataBean> shopAdListDataList = shopAdList.getData();
                    listViewAdHeaderView.resetList(shopAdListDataList);
                    //                    List<String> list = new ArrayList<>();
                    //                    if (null != shopAdListDataList && shopAdListDataList.size() > 0) {
                    //                        for (int i = 0; i < shopAdListDataList.size(); i++) {
                    //                            list.add(shopAdListDataList.get(i).getAdUrl());
                    //                        }
                    //                    }
                    //                    if (null != adList && adList.size() > 0) {
                    //                        adList.clear();
                    //                    }
                    //                    adList.addAll(shopAdListDataList);
                    //                    listViewAdHeaderView.notifyDataSetChanged();
                    //                    listViewAdHeaderView.fillView(adList, smoothListView);
                }

            }
            break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        StatService.onResume(ExcellentShopFragment.this);

        ((XBaseActivity) getActivity()).addAndroidEventListener(XEventCode.HTTP_SHOP_LIST, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(ExcellentShopFragment.this);

        ((XBaseActivity) getActivity()).removeEventListener(XEventCode.HTTP_SHOP_LIST, this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (listViewAdHeaderView != null) {
                listViewAdHeaderView.stopADRotate();
            }
            ((XBaseActivity) getActivity()).removeEventListener(XEventCode.HTTP_SHOP_LIST, this);
        } catch (Exception e) {

        }
    }

}
