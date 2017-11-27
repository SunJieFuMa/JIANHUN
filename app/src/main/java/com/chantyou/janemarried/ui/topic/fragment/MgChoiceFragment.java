package com.chantyou.janemarried.ui.topic.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.chantyou.janemarried.adapter.group.MgChoiceAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.topic.TopicBestRunner;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.mhh.lib.ui.base.BottomLoadFragment;
import com.mhh.lib.utils.JsonUtil;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2015/12/16 17:47
 * Email：766082577@qq.com
 */
public class MgChoiceFragment extends BottomLoadFragment implements EventManager.OnEventListener {

    private static final String TAG = MgChoiceFragment.class.getSimpleName();

    private MgChoiceAdapter mAdapter;

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
        mAdapter = new MgChoiceAdapter(getActivity());
        setupRecyclerView(manager, mAdapter, RecyclerMode.BOTH);
        onPullDown();
    }

    @Override
    public void onPullDown() {
        pageCur = 0;
        ((XBaseActivity) getActivity()).pushEvent(new TopicBestRunner(pageCur), this);
    }

    @Override
    public void onLoadMore() {
        pageCur++;
        ((XBaseActivity) getActivity()).pushEvent(new TopicBestRunner(pageCur), this);
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TOPIC_BEST:
                onRefreshCompleted();
                if((int) event.getParamsAtIndex(0) == 0) {
                    mAdapter.clear();
                }
                if(event.isSuccess()) {
                    JSONObject object = (JSONObject) event.getReturnParamsAtIndex(0);
                    if(Constants.DEBUG) {
                        Log.v(TAG, "object = " + object);
                    }
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
