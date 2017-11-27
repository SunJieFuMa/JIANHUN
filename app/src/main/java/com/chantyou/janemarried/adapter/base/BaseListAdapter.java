package com.chantyou.janemarried.adapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chantyou.janemarried.AppAndroid;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseListAdapter<E> extends BaseAdapter {

    protected String TAG = getClass().getSimpleName();

    protected List<E> mDataList;

    protected int width = AppAndroid.dipToPixel(60);
    protected int height = width;

    public BaseListAdapter() {
        mDataList = new ArrayList<E>();
    }

    public void addItem(E item) {
        if (item != null) {
            mDataList.add(item);
            notifyDataSetChanged();
        }
    }

    public void setData(List<E> data) {
        if (data == null) {
            return;
        }
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    public List<E> getData() {
        return mDataList;
    }

    public void deleteItem(int position) {
        if (position < 0 || position >= mDataList.size()) {
            return;
        }
        mDataList.remove(position);
        notifyDataSetChanged();
    }

    public void removeItem(E e) {
        if (mDataList != null) {
            mDataList.remove(e);
        }
    }

    public void clear() {
        if (mDataList != null) {
            mDataList.clear();
        }
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public E getItem(int arg0) {
        return mDataList == null || arg0 >= mDataList.size() ? null : mDataList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(final int position, View contentView, ViewGroup view) {
        AbsViewHolder holder;
        if (contentView == null) {
            contentView = LayoutInflater.from(view.getContext()).inflate(getLayoutId(), null);
            holder = setViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (AbsViewHolder) contentView.getTag();
        }
        setValue(holder, position);
        return contentView;
    }

    protected void setValue(AbsViewHolder holder, int position) {
        holder.setValue(position, getItem(position));
    }

    protected abstract int getLayoutId();

    protected abstract AbsViewHolder setViewHolder(View view);

    protected class AbsViewHolder {

        public void setValue(int position, E item) {

        }
    }
}
