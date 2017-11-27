package com.chantyou.janemarried.ui.left.favorite;

import android.support.v7.widget.LinearLayoutManager;

import com.chantyou.janemarried.adapter.left.MyFavoriteShopAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.MyFavoriteShopListRunner;
import com.chantyou.janemarried.model.Shop.ShopFavoriteListBean;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.mhh.lib.ui.base.BottomLoadFragment;

import space.sye.z.library.manager.RecyclerMode;


//收藏店铺
public class ShopFavoriteFragment extends BottomLoadFragment implements EventManager.OnEventListener {

    private MyFavoriteShopAdapter mAdapter;

    @Override
    protected void setupRecyclerView() {//
        mAdapter = new MyFavoriteShopAdapter(getActivity());
        setupRecyclerView(new LinearLayoutManager(getActivity()), mAdapter, RecyclerMode.TOP);

        onPullDown();
    }


    @Override
    public void onPullDown() {
        mAdapter.clear();
        pageCur = 0;
        ((XBaseActivity) getActivity()).pushEvent(new MyFavoriteShopListRunner(pageCur + "", 100 + ""), this);
    }

    @Override
    public void onLoadMore() {
        pageCur += 1;
        ((XBaseActivity) getActivity()).pushEvent(new MyFavoriteShopListRunner(pageCur + "", 100 + ""), this);
    }


    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SHOP_MY_COLLECT://我收藏的店铺
                onRefreshCompleted();
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopFavoriteListBean shopListBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopFavoriteListBean.class);
//                    System.out.println("zhuwx:" + event.getReturnParamsAtIndex(0).toString());
//                    Gson gson = new Gson();
//                    ShopFavoriteListBean shopListBean2 = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopFavoriteListBean.class);

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
//                    shopListAdapter = new ShopListAdapter(mContent, shopList);
////                    smoothListView.setAdapter(mAdapter);
//
//
//                    if (null != shopListBean.getData() && shopListBean.getData().size() < 1) {
//                        if (LoadType.LoadMore != loadType) {
////                            smoothListView.setLoadMoreEnable(false);
////                            int height = mScreenHeight - DensityUtil.dip2px(mContent, 95); // 95 = 标题栏高度 ＋ FilterView的高度
////                            shopList.addAll(ModelUtil.getNoDataShopListEntity(height));
////                            shopListAdapter.setData(shopList);
//                        }
//
//                    } else {
//                        shopListAdapter.setData(shopList);
////                        smoothListView.setLoadMoreEnable(true);
//                    }

                    mAdapter.addData(shopListBean.getData());
                    hasMore(true);
//                    Map<String ,Object> map = (Map<String ,Object>) event.getReturnParamsAtIndex(0);
//                    List<Map<String, Object>> mytopics = (List<Map<String, Object>>) map.get("mytopics");
//                    mAdapter.addData(mytopics);
//                    hasMore(mytopics != null && mytopics.size() >= 10);
                } else {
                    if (pageCur > 0) {
                        pageCur -= 1;
                    }
//                    CustomToast.showErrorToast(getActivity(), "获取异常");
                }
                break;
        }
    }
}
