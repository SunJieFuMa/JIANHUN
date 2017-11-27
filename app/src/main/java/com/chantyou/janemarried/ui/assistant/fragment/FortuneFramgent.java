package com.chantyou.janemarried.ui.assistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.FortuneDataRunner;
import com.chantyou.janemarried.ui.assistant.FortuneActivity;
import com.chantyou.janemarried.ui.view.CalendarGridView;
import com.chantyou.janemarried.ui.view.CalendarGridViewAdapter;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.ui.base.BaseFragment;

import java.util.Calendar;
import java.util.List;

/**
 * Created by j_turn on 2016/4/6.
 * Email 766082577@qq.com
 */
public class FortuneFramgent extends BaseFragment implements EventManager.OnEventListener {

    private CalendarGridView calGridView;
    private CalendarGridViewAdapter adapter;
    int position;

    @Override
    protected int getLayoutId() {
        return R.layout.fmt_fortune;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((XBaseActivity) getActivity()).addAndroidEventListener(XEventCode.EVENT_SWIP_CALENAR, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((XBaseActivity) getActivity()).removeEventListener(XEventCode.EVENT_SWIP_CALENAR, this);
    }

    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);
        if(getArguments() != null) {
            position = getArguments().getInt("posi", -1);
            if(position != -1) {
                calGridView = (CalendarGridView) mRootView.findViewById(R.id.calGridView);
                Calendar firstCal = Calendar.getInstance();
                firstCal.add(Calendar.MONTH, position);
                adapter = new CalendarGridViewAdapter(getActivity(), firstCal);
                if(getActivity() != null && getActivity() instanceof FortuneActivity) {
                    adapter.setOnDateSelectListener(((FortuneActivity) getActivity()).listener);
                }
                calGridView.setAdapter(adapter);

                ((XBaseActivity) getActivity()).pushEventEx(false, null, new FortuneDataRunner(position, firstCal), this);
            }
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.EVENT_SWIP_CALENAR:
                int pos = (int) event.getParamsAtIndex(0);
                if(pos == position) {
                    if(adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            case XEventCode.ASYN_CALENAR:
                if(event.isSuccess()) {
                    if(position == (int) event.getParamsAtIndex(0)) {
                        List<FortBean> list = (List<FortBean>) event.getReturnParamsAtIndex(0);
                        adapter.setData(list);
                    }
                }
                break;
        }
    }
}
