package com.chantyou.janemarried.adapter.excellent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.excellent.MarriageGoodsInfoActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2015/12/12.
 */
public class ExcellentMarriageGoodsAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

    public ExcellentMarriageGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_excellentmarriagegoods_item, parent, false));
    }

    @Override
    protected boolean isDefBg() {
        return false;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.map = getValueAt(position);
//        vh.setValue();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setValue();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView iv;
        TextView tv, tvMoney, tvPraise;
        Map<String, Object> map;

        public ViewHolder(View view) {
            super(view);
            view = view.findViewById(R.id.view_item);
            iv = (ImageView) view.findViewById(R.id.iv);
            iv.setImageResource(R.drawable.ic_marray_def);
            tv = (TextView) view.findViewById(R.id.tv);
            tvMoney = (TextView) view.findViewById(R.id.tvMoney);
            tvPraise = (TextView) view.findViewById(R.id.tvPraise);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (map != null) {
                        MarriageGoodsInfoActivity.launch(v.getContext(), JsonUtil.getItemInt(map, "id"));
                    }
                }
            });
        }

        void setValue() {
            if (map != null) {
                List<Map<String, Object>> zhanshi = (List<Map<String, Object>>) map.get("zhanshi");
                if (null == zhanshi) {
                    return;
                }
                if (zhanshi.size() > 0) {
                    HImageLoader.displayImage(JsonUtil.getItemString(zhanshi.get(0), "source"), iv, R.color.white_gray);
                }
                tv.setText(JsonUtil.getItemString(map, "name"));
                double discountPrice = JsonUtil.parseDouble(map.get("discountPrice"));
                double price = JsonUtil.parseDouble(map.get("price"));
                if (discountPrice <= 0) {
                    discountPrice = price;
                }
                tvMoney.setText(Html.fromHtml(String.format(tvMoney.getResources().getString(R.string.fmt_money), String.valueOf(discountPrice))));
                int hits = JsonUtil.getItemInt(map, "hits");
                if (hits < 10000) {
                    tvPraise.setText(JsonUtil.getItemInt(map, "hits") + "");
                } else {
                    tvPraise.setText(hits / 10000 + "." + ((hits % 10000) / 100) + "万");
                }
            }
        }
    }
}
