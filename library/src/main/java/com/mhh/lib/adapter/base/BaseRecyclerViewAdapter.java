package com.mhh.lib.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhh.lib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j_turn on 2015/12/10 10:33
 * Email：766082577@qq.com
 */
public abstract class BaseRecyclerViewAdapter<E> extends RecyclerView.Adapter {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<E> mDataList;

    public BaseRecyclerViewAdapter(Context context) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mDataList = new ArrayList<E>();
    }

    public void setData(List<? extends E> data) {
        clear();
        if(data != null) {
            mDataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(List<E> data) {
        if(data != null) {
            mDataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<E> getData() {
        return mDataList;
    }

    public void addItem(E item) {
        if(item != null) {
            int pos = getItemCount();
            mDataList.add(item);
            notifyItemInserted(pos);
        }
    }

    public void removeItem(int position) {
        if(position < 0 || position >= mDataList.size()) {
            return;
        }
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(E e) {
        if(mDataList != null) {
            int pos = mDataList.indexOf(e);
            if(pos != -1) {
                mDataList.remove(e);
                notifyItemRemoved(pos);
            }
        }
    }

    public void clear() {
        if(mDataList != null) {
            mDataList.clear();
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public E getValueAt(int position) {
        return mDataList != null && position >= 0 && position < mDataList.size() ? mDataList.get(position) : null;
    }

    public E getValueAt3(int position) {//这个方法是我加的，因为我的结婚任务那里跟这个的逻辑有所不同
        return mDataList != null? mDataList.get(position) : null;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    protected View inflateView(ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        if(isDefBg()) {
            view.setBackgroundResource(mBackground);
        }
        return view;
    }

    public static <T extends View> T get(View convertView, int id) {
        return (T) convertView.findViewById(id);
    }

    protected boolean isDefBg() {
        return true;
    }

//    protected static class ViewHolder extends RecyclerView.ViewHolder {
//
//        public ViewHolder(View view) {
//            super(view);
//            if(isDefBg()) {
//                view.setBackgroundResource(mBackground);
//            }
//        }
//
//        @Override
//        public String toString() {
//            return super.toString();
//        }
//    }
}
