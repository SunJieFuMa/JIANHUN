package com.chantyou.janemarried.ui.qingjian;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.bean.AllCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
public class CardModelAdapter extends RecyclerView.Adapter<CardModelAdapter.MyHolder> {

    private List<AllCard.DataEntity> mDatas;
    private Context mContext;
    private List<String> imgUrls=new ArrayList<>();


    public CardModelAdapter(CardModelActivity context, List<AllCard.DataEntity> data) {
        this.mContext=context;
        this.mDatas=data;
        for (int i = 0; i < data.size(); i++) {
            String showImage = data.get(i).getShowImage();
            if (null != showImage && !TextUtils.isEmpty(showImage))
                imgUrls.add(showImage);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_qingjian_moban, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.setDataAndRefreshUI(imgUrls.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击的是模板item
//                Toast.makeText(mContext, " 点击的是模板item", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,QingJianWebPageActivity.class);
                AllCard.DataEntity dataEntity = mDatas.get(position);
                intent.putExtra("dataEntity", dataEntity);//之前傻得把这行放到下面去了
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (imgUrls == null) {
            return 0;
        }

        return imgUrls.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView iv_qingjian;

        public MyHolder(View view) {
            super(view);
            iv_qingjian = (ImageView) view.findViewById(R.id.iv_qingjian);
        }

        public void setDataAndRefreshUI(String url) {
            Glide.with(mContext).load(url).into(iv_qingjian);
        }

    }


}
