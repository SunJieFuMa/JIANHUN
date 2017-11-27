package com.chantyou.janemarried.ui.qingjian;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.bean.MyCard;
import com.chantyou.janemarried.config.UrlConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/7.
 */
public class QingJianAdapter extends RecyclerView.Adapter<QingJianAdapter.MyHolder> {

    private List<MyCard.DataEntity> data = new ArrayList<>();
    private Context mContext;
    private List<String> imgUrls = new ArrayList<>();

    public QingJianAdapter(Context context, List<MyCard.DataEntity> data) {
        this.mContext = context;
        this.data = data;
        //        if (null!=imgUrls && imgUrls.size()>0){
        //            imgUrls.clear();
        //        }
        if (null != data && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                String showImage = data.get(i).getShowImage();
                if (null != showImage && !TextUtils.isEmpty(showImage)) {
                    imgUrls.add(showImage);
                }
            }
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_qingjian_user, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        if (position == 0) {
            //第一个item加载的是本地的图片----新建请柬
            Glide.with(mContext).load(R.drawable.icon_add_qingjian).into(holder.iv_qingjian);
            holder.iv_del.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击第一个item的时候，跳转到请柬模板页面
                    Intent intent = new Intent(mContext, CardModelActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else {
            //进到这里之后，position已经是大于0了
            Glide.with(mContext).load(imgUrls.get(position - 1)).into(holder.iv_qingjian);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击的是模板item
                    //                    Toast.makeText(mContext, "点击的是用户创建好的请柬item第" + position + "个", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, CardDetailActivity.class);
                    intent.putExtra("detail", data.get(position - 1));
                    intent.putExtra("position", position-1);//因为第一个item是我们自己加上去的
                    mContext.startActivity(intent);
                }
            });
            //请柬上的删除按钮的点击事件
            holder.iv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    String url = UrlConfig.QingjianHost+"/qingjian/user/templete/del";
                    OkHttpUtils
                            .post()
                            .url(url)
                            .addParams("userId", AppAndroid.getUid() + "")
                            .addParams("userTempleteId", String.valueOf(data.get(position - 1).getId()))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(mContext, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    /*
                                    这样写的话，很快会产生数据删除错乱和超出索引异常导致崩溃。
                                    原因是onBindViewHolder()里面传入的参数position，它是在进行onBind操作时确定的，
                                    已经出现在画面里的项不会再有调用onBind的机会，所以在删除单项后，
                                    这样它保留的position一直是未进行删除操作前的position值。
                                    解决办法是使用getLayoutPosition或者holder.getAdapterPosition()
                                     */
//                                    imgUrls.remove(position - 1);//别忘了把集合中的数据删除掉
//                                    QingJianAdapter.this.notifyItemRemoved(position);
                                    imgUrls.remove(holder.getAdapterPosition() - 1);//别忘了把集合中的数据删除掉
                                    QingJianAdapter.this.notifyItemRemoved(holder.getAdapterPosition());

                                }
                            });
                }

            });

        }

    }

    @Override
    public int getItemCount() {
        if (imgUrls == null) {
            return 0;
        }
        return imgUrls.size() + 1;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView iv_qingjian;
        private ImageView iv_del;

        public MyHolder(View view) {
            super(view);
            iv_qingjian = (ImageView) view.findViewById(R.id.iv_qingjian);
            iv_del = (ImageView) view.findViewById(R.id.iv_del);
        }
    }

}
