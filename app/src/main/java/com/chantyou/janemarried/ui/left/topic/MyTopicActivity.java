package com.chantyou.janemarried.ui.left.topic;

import android.support.v7.widget.LinearLayoutManager;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.adapter.left.MyTopicAdapter;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.my.TopicMyAllRunner;
import com.chantyou.janemarried.ui.base.PullrefreshBottomloadActivity;
import com.lib.mark.core.Event;

import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

/**
 * Created by j_turn on 2015/12/19 14:56
 * Email：766082577@qq.com
 */
public class MyTopicActivity extends PullrefreshBottomloadActivity {

    private MyTopicAdapter mAdapter;

    @Override
    protected void init() {
        super.init();

        onPullDown();
    }

    @Override
    protected void setupRecyclerView() {
        mAdapter = new MyTopicAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), mAdapter, RecyclerMode.BOTH);
    }

    @Override
    public void onPullDown() {
        mAdapter.clear();
        pageCur = 0;
        pushEvent(new TopicMyAllRunner(pageCur));
    }

    @Override
    public void onLoadMore() {
        pageCur += 1;
        pushEvent(new TopicMyAllRunner(pageCur));
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TOPIC_MYALL:
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
//                    CustomToast.showErrorToast(this, "获取异常");
                }
                break;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MyTopicActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MyTopicActivity.this);
    }
}
