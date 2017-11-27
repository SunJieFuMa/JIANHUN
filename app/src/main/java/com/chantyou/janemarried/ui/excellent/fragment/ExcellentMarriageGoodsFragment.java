package com.chantyou.janemarried.ui.excellent.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.excellent.ExcellentMarriageGoodsAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.product.ProductByTypeRunner;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.ui.base.BottomLoadFragment;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2015/12/12.
 * Email 766082577@qq.com
 *
 * 优婚品
 */
public class ExcellentMarriageGoodsFragment extends BottomLoadFragment implements EventManager.OnEventListener {

    protected ExcellentMarriageGoodsAdapter adapter;
    private int type;

    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);
        mRootView.findViewById(R.id.lv_root).setBackgroundColor(getResources().getColor(R.color.c_eeeeee));
    }

    @Override
    protected void setupRecyclerView() {
        if(getArguments() != null) {
            type = getArguments().getInt("type");
        }
        adapter = new ExcellentMarriageGoodsAdapter(getContext());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        setupRecyclerView(manager, adapter, RecyclerMode.BOTH);

        onPullDown();
    }

    @Override
    public void onPullDown() {
        adapter.clear();
        pageCur = 0;
        ((XBaseActivity) getActivity()).pushEvent(new ProductByTypeRunner(type, pageCur), this);
    }

    @Override
    public void onLoadMore() {
        pageCur += 1;
        ((XBaseActivity) getActivity()).pushEvent(new ProductByTypeRunner(type, pageCur), this);
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PRODUCT_BYTYPE:
                onRefreshCompleted();
                if((int) event.getParamsAtIndex(1) == 0) {
                    adapter.clear();
                }
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> pros = (List<Map<String, Object>>) map.get("pros");
                    hasMore(pros != null && pros.size() >= 10);
                    adapter.addData(pros);
                } else {
                    if(pageCur > 0) {
                        pageCur -= 1;
                    }
                }
                break;
        }
    }
}
