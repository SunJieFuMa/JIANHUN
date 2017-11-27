package com.chantyou.janemarried.ui.excellent.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.excellent.MgCommentAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.product.ProductFindCommentRunner;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.ui.base.BottomLoadFragment;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2015/12/19.
 * Email 766082577@qq.com
 */
public class MgCommentFragment extends BottomLoadFragment implements EventManager.OnEventListener {

    private MgCommentAdapter mAdapter;

    private View vEmpty;
    private int proId;

    @Override
    protected int getLayoutId() {
        return R.layout.fmt_mgcomment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((XBaseActivity) getActivity()).addAndroidEventListener(XEventCode.HTTP_PRODUCT_COMMENT, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((XBaseActivity) getActivity()).removeEventListener(XEventCode.HTTP_PRODUCT_COMMENT, this);
    }

    @Override
    protected void setupRecyclerView() {
        vEmpty = mRootView.findViewById(R.id.vEmpty);
        mAdapter = new MgCommentAdapter(getActivity());
        setupRecyclerView(new LinearLayoutManager(getActivity()), mAdapter, RecyclerMode.BOTTOM);

        if(getArguments() != null) {
            proId = getArguments().getInt("proId", 0);
        }

        onPullDown();
    }

    @Override
    public void onPullDown() {
        mAdapter.clear();
        pageCur = 0;
        ((XBaseActivity) getActivity()).pushEvent(new ProductFindCommentRunner(proId + "", pageCur), this);
    }

    @Override
    public void onLoadMore() {
        pageCur += 1;
        ((XBaseActivity) getActivity()).pushEvent(new ProductFindCommentRunner(proId + "", pageCur), this);
    }

    private void notifyData() {
        if(mAdapter != null && mAdapter.getItemCount() > 0) {
            vEmpty.setVisibility(View.GONE);
        } else {
            vEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PRODUCT_FINDCOMMENT:
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> pros = (List<Map<String, Object>>) map.get("reviews");
                    hasMore(pros != null && pros.size() >= 10);
                    mAdapter.addData(pros);
                } else {
                    if(pageCur > 0) {
                        pageCur -= 1;
                    }
                }
                notifyData();
                break;
            case XEventCode.HTTP_PRODUCT_COMMENT:
                if(event.isSuccess()) {
                    onPullDown();
                }
                break;
        }
    }
}
