package com.chantyou.janemarried.ui.left.favorite;

import android.support.v7.widget.LinearLayoutManager;

import com.chantyou.janemarried.adapter.left.MyTopicAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.my.TopicMyFavoriteRunner;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.base.BottomLoadFragment;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2016/3/31.
 * Email 766082577@qq.com
 */
public class TopicFavoriteFragment extends BottomLoadFragment implements EventManager.OnEventListener {

    private MyTopicAdapter mAdapter;

    @Override
    protected void setupRecyclerView() {
        mAdapter = new MyTopicAdapter(getActivity());
        setupRecyclerView(new LinearLayoutManager(getActivity()), mAdapter, RecyclerMode.BOTH);

        onPullDown();
    }

    @Override
    public void onPullDown() {
        mAdapter.clear();
        pageCur = 0;
        ((XBaseActivity) getActivity()).pushEvent(new TopicMyFavoriteRunner(pageCur), this);
    }

    @Override
    public void onLoadMore() {
        pageCur += 1;
        ((XBaseActivity) getActivity()).pushEvent(new TopicMyFavoriteRunner(pageCur), this);
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TOPIC_MYCOLLECT:
                onRefreshCompleted();
                if(event.isSuccess()) {
                    Map<String ,Object> map = (Map<String ,Object>) event.getReturnParamsAtIndex(0);
                    List<Map<String, Object>> mytopics = (List<Map<String, Object>>) map.get("mytopics");
                    mAdapter.addData(mytopics);
                    hasMore(mytopics != null && mytopics.size() >= 10);
                } else {
                    if(pageCur > 0) {
                        pageCur -= 1;
                    }
//                    CustomToast.showErrorToast(getActivity(), "获取异常");
                }
                break;
        }
    }
}
