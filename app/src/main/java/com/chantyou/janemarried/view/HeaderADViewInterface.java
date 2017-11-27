package com.chantyou.janemarried.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;

public abstract class HeaderADViewInterface<T> {

    protected Activity mContext;
    protected LayoutInflater mInflate;
    protected List<String> mEntity;

    public HeaderADViewInterface(Activity context) {
        this.mContext = context;
        mInflate = LayoutInflater.from(context);
    }

    public boolean fillView(List<String> t, ListView listView) {
        if (t == null) {
            return false;
        }
        if ((t instanceof List) && ((List) t).size() == 0) {
            return false;
        }
        this.mEntity = t;
        getView(t, listView);
        return true;
    }

    protected abstract void getView(List<String> list, ListView listView);
}
