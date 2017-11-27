package com.chantyou.janemarried.ui.topic.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.chantyou.janemarried.adapter.group.MgLatestAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.topic.TopicLatestRunner;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.base.BottomLoadFragment;
import com.mhh.lib.utils.JsonUtil;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2015/12/16 19:41
 * Email：766082577@qq.com
 */
public class MgLatestFragment extends BottomLoadFragment implements EventManager.OnEventListener {

    private MgLatestAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((XBaseActivity) getActivity()).addAndroidEventListener(XEventCode.HTTP_TOPIC_ADD_EDIT, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((XBaseActivity) getActivity()).removeEventListener(XEventCode.HTTP_TOPIC_ADD_EDIT, this);
    }

    @Override
    protected void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mAdapter = new MgLatestAdapter(getActivity());
        setupRecyclerView(manager, mAdapter, RecyclerMode.BOTH);

        onPullDown();
    }

    @Override
    public void onPullDown() {
        pageCur = 0;
        ((XBaseActivity) getActivity()).pushEvent(new TopicLatestRunner(pageCur), this);
    }

    @Override
    public void onLoadMore() {
        pageCur++;
        ((XBaseActivity) getActivity()).pushEvent(new TopicLatestRunner(pageCur), this);
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TOPIC_LATEST:
                onRefreshCompleted();
                if((int) event.getParamsAtIndex(0) == 0) {
                    mAdapter.clear();
                }
                if(event.isSuccess()) {
                    JSONObject object = (JSONObject) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) JsonUtil.jsonToList(object.optString("topics"));
                    mAdapter.addData(list);
                    hasMore(list != null && list.size() >= 10);
                } else {
                    if(pageCur > 0) {
                        pageCur -= 1;
                    }
//                    CustomToast.showErrorToast(getActivity(), event.getMessage("获取失败"));
                }
                break;
            case XEventCode.HTTP_TOPIC_ADD_EDIT:
                if(event.isSuccess()) {
                    onPullDown();
                }
                break;
        }
    }
}