package com.chantyou.janemarried.ui.view;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.mhh.lib.utils.SystemUtils;
import com.mhh.lib.widget.GridViewEx;

/**
 * 用于生成日历展示的GridView布局
 *
 * @author zhouxin@easier.cn
 */
public class CalendarGridView extends GridViewEx {

    public static final int VERSPACE = 4;

    /**
     * 当前操作的上下文对象
     */
    private Context mContext;

    /**
     * CalendarGridView 构造器
     *
     * @param context 当前操作的上下文对象
     */
    public CalendarGridView(Context context) {
        super(context);
        mContext = context;

        setGirdView();
    }

    public CalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setGirdView();
    }

    public CalendarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        setGirdView();
    }

    /**
     * 初始化gridView 控件的布局
     */
    private void setGirdView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        setLayoutParams(params);
        setNumColumns(7);// 设置每行列数
        setGravity(Gravity.CENTER_VERTICAL);// 位置垂直居中
        setVerticalSpacing(SystemUtils.dipToPixel(getContext(), VERSPACE));// 垂直间隔4dp
        setHorizontalSpacing(0);// 水平间隔
        setBackgroundColor(Color.argb(255, 250, 250, 250));

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int i = display.getWidth() / 7;
        int j = display.getWidth() - (i * 7);
        int x = j / 2;
        setPadding(x, 0, 0, 0);// 居中
    }
}
