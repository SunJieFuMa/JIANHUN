package com.chantyou.janemarried.adapter.sticky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.model.FilterTwoEntity;

import java.util.List;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterLeftAdapter extends BaseListAdapter<FilterTwoEntity> {

    private FilterTwoEntity selectedEntity;

    public FilterLeftAdapter(Context context) {
        super(context);
    }

    public FilterLeftAdapter(Context context, List<FilterTwoEntity> list) {
        super(context, list);
    }

    public void setSelectedEntity(FilterTwoEntity filterEntity) {
        this.selectedEntity = filterEntity;
        for (FilterTwoEntity entity : getData()) {
            entity.setSelected(entity.getType().equals(selectedEntity.getType()));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_filter_left, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.llRootView = (LinearLayout) convertView.findViewById(R.id.ll_root_view);
        holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);



        FilterTwoEntity entity = getItem(position);

        holder.tvTitle.setText(entity.getType());
        if (entity.isSelected()) {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.orange));
            holder.llRootView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
            holder.llRootView.setBackgroundColor(mContext.getResources().getColor(R.color.font_black_6));
        }

        return convertView;
    }

    public class ViewHolder {

        LinearLayout llRootView;

        TextView tvTitle;

    }
}
