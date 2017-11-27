package com.chantyou.janemarried.adapter.sticky;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.model.OperationEntity;

import java.util.List;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderOperationAdapter extends BaseListAdapter<OperationEntity> {

    public HeaderOperationAdapter(Context context) {
        super(context);
    }

    public HeaderOperationAdapter(Context context, List<OperationEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_operation, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OperationEntity entity = getItem(position);

        holder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
        holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        holder.tvSubtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);


        holder.tvTitle.setText(entity.getTitle());
        mImageManager.loadUrlImage(entity.getImage_url(), holder.ivImage);
        if (TextUtils.isEmpty(entity.getSubtitle())) {
            holder.tvSubtitle.setVisibility(View.INVISIBLE);
        } else {
            holder.tvSubtitle.setVisibility(View.VISIBLE);
            holder.tvSubtitle.setText(entity.getSubtitle());
        }

        return convertView;
    }

    public class ViewHolder {
        private ImageView ivImage;
        private TextView tvTitle;
        private TextView tvSubtitle;

    }
}
