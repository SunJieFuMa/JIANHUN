package com.chantyou.janemarried.ui.excellent.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.chantyou.janemarried.adapter.base.AdjustImgAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.ui.base.BottomLoadFragment;
import com.mhh.lib.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2015/12/19.
 * Email 766082577@qq.com
 */
//优婚品-婚品详情-图文详情
public class MgPicInfoFragment extends BottomLoadFragment implements EventManager.OnEventListener {

    private AdjustImgAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((XBaseActivity) getActivity()).addAndroidEventListener(XEventCode.HTTP_PRODUCT_DETAIL, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((XBaseActivity) getActivity()).removeEventListener(XEventCode.HTTP_PRODUCT_DETAIL, this);
    }

    @Override
    protected void setupRecyclerView() {
        mAdapter = new AdjustImgAdapter(getActivity());
        setupRecyclerView(new LinearLayoutManager(getActivity()), mAdapter, RecyclerMode.NONE);
    }

    @Override
    public void onEventRunEnd(Event event) {
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PRODUCT_DETAIL:
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    map = (Map<String, Object>) map.get("prodetail");
                    List<Map<String, Object>> images = (List<Map<String, Object>>) map.get("tuwen");
                    List<String> pics = new ArrayList<>();
                    if(images != null) {
                        for(Map<String, Object> item : images) {
                            pics.add(JsonUtil.getItemString(item, "source"));
                        }
                    }
                    mAdapter.setData(pics);
                } else {
                    mAdapter.setData(null);
                }
                break;
        }
    }
}
