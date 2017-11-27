package com.chantyou.janemarried.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.widget.CycleWheelView;
import com.mhh.lib.widget.XPopupWindow;

import java.util.List;

/**
 * Created by j_turn on 2016/1/24 22:55
 * Email：766082577@qq.com
 */
public class PopupWheelSelector extends XPopupWindow {

    private CycleWheelView cycleWheelView;

    public PopupWheelSelector(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_wheel_selector, null);
        setContentView(view);
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cycleWheelView = (CycleWheelView) view.findViewById(R.id.cycleWheelView);
//        List<String> labels2 = new ArrayList<>();
//        for (int i = 0; i < 60; i++) {
//            labels2.add("" + i);
//        }
//        cycleWheelView.setLabels(labels2);
//        try {
//            cycleWheelView.setWheelSize(5);
//        } catch (CycleWheelView.CycleWheelViewException e) {
//            e.printStackTrace();
//        }
//        cycleWheelView.setSelection(30);
//        cycleWheelView.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position, String label) {
//                Log.d("test", label);
//            }
//        });
    }

    public PopupWheelSelector setLabels(List<String> labels) {
        cycleWheelView.setLabels(labels);
        cycleWheelView.setCycleEnable(true);
        cycleWheelView.setAlphaGradual(0.6f);
        cycleWheelView.setDivider(Color.parseColor("#abcdef"), 2);
        cycleWheelView.setSolid(Color.WHITE,Color.WHITE);
        cycleWheelView.setLabelColor(Color.BLUE);
        cycleWheelView.setLabelSelectColor(Color.RED);
        return this;
    }

    public PopupWheelSelector setSelection(int selection) {
        cycleWheelView.setSelection(selection);
        return this;
    }

    /**
     * Must Be 3,5,7,9... size > 3
     * @param wheelSize
     * @return
     */
    public PopupWheelSelector setWheelSize(int wheelSize) {
        try {
            cycleWheelView.setWheelSize(wheelSize);
        } catch (CycleWheelView.CycleWheelViewException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PopupWheelSelector setOnWheelItemSelectedListener(CycleWheelView.WheelItemSelectedListener listener) {
        cycleWheelView.setOnWheelItemSelectedListener(listener);
        return this;
    }
}
