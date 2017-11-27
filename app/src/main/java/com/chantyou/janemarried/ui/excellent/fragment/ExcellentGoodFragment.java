package com.chantyou.janemarried.ui.excellent.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.product.ProductCateRunner;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.ui.base.BaseFragment;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * 优婚品
 */
public class ExcellentGoodFragment extends BaseFragment implements EventManager.OnEventListener {


    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_basetabs;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initView();
    }
//
//    private void initView(){
//        mRootView.findViewById(R.id.lv_root).setBackgroundColor(getResources().getColor(R.color.c_eeeeee));
//
//        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
//        tabLayout = (TabLayout) mRootView.findViewById(R.id.tabs);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//
//        System.out.println("zhuwx:initView");
//        ((XBaseActivity) getActivity()).pushEvent(new ProductCateRunner(), this);
//    }

    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);

        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        ((XBaseActivity) getActivity()).pushEvent(new ProductCateRunner(), this);
//        ((XBaseActivity) getActivity()).pushEventEx(false, null, new ProductCateRunner(), this);//获取优婚品
//        pushEvent(new ProductCateRunner());
    }


    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PRODUCT_CATE:
                System.out.println("ZhuWX:onEventRunEnd");
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    setPagers((List<Map<String, Object>>) map.get("data"));
                }
                break;
        }
    }

    private void setPagers(List<Map<String, Object>> cates) {
        System.out.println("zhuwx:setPagers");
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getChildFragmentManager());
        if (cates != null && cates.size() > 0) {
            for (Map<String, Object> map : cates) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", JsonUtil.getItemInt(map, "cateId"));
                adapter.addFragment(ExcellentMarriageGoodsFragment.class, JsonUtil.getItemString(map, "name"), bundle);
            }
        }
        mViewPager.setAdapter(adapter);
        if (mViewPager != null && tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
        }
    }
}
