package com.chantyou.janemarried.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {


    public MyScrollView(Context context) {
        super(context);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public MyScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        View view = (View)getChildAt(getChildCount()-1);
        int d = view.getBottom();
        d -= (getHeight()+ getScrollY());
        if(d==0)
        {
            //you are at the end of the list in scrollview
            //do what you wanna do here

            System.out.println("zhuwx:最低端了");
        }
        else
            super.onScrollChanged(l,t,oldl,oldt);
    }
}
