package com.chantyou.janemarried.ui.qingjian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.bean.PageModel;
import com.chantyou.janemarried.config.UrlConfig;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/14.
 */
public class PageModelActivity extends MyBaseActivity {
    private List<String> imgUrls = new ArrayList<>();
    private RecyclerView recycler_qingjian;
    private ProgressBar bar;
    private Button title_back;
    private PageModelAdapter adapter;
    private List<PageModel.DataEntity> data;
    private long userTempleteId;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_qingjian_page_model);
        recycler_qingjian = (RecyclerView) findViewById(R.id.recycler_qingjian);
        bar = (ProgressBar) findViewById(R.id.bar);
        title_back = (Button) findViewById(R.id.title_back);
        userTempleteId=getIntent().getLongExtra("userTempleteId",0);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        final String url = UrlConfig.QingjianHost+"/qingjian/user/page/list";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("timestamped", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AppAndroid.getContext(), "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                        System.out.println("response::" + response);
                    }
                });
    }

    private void parseJson(String response) {
        PageModel pageModel = new Gson().fromJson(response, PageModel.class);
        data = pageModel.getData();
        if (null != pageModel && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                imgUrls.add(data.get(i).getShowImage());
            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler_qingjian.setLayoutManager(gridLayoutManager);
        adapter = new PageModelAdapter();
        recycler_qingjian.setAdapter(adapter);
        bar.setVisibility(View.GONE);
    }

    public class PageModelAdapter extends RecyclerView.Adapter<PageModelAdapter.PageModelHolder> {

        @Override
        public PageModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(PageModelActivity.this, R.layout.item_qingjian_page_moban, null);
            return new PageModelHolder(view);
        }

        @Override
        public void onBindViewHolder(PageModelAdapter.PageModelHolder holder, final int position) {
            Glide.with(PageModelActivity.this).load(imgUrls.get(position)).into(holder.iv_qingjian);
            final String url = UrlConfig.QingjianHost+"/qingjian/user/page/use";
            holder.iv_qingjian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpUtils
                            .post()
                            .url(url)
                            .addParams("userId", AppAndroid.getUid() + "")
                            .addParams("pageId", data.get(position).getId()+"")//从页面列表的接口中获取到的
                            .addParams("userTempleteId", userTempleteId+"")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(AppAndroid.getContext(), "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {

                                    goDetailActivity(response);
                                    System.out.println("response::" + response);
                                }
                            });
                }
            });
        }

        @Override
        public int getItemCount() {
            return imgUrls == null ? 0 : imgUrls.size();
        }

        public class PageModelHolder extends RecyclerView.ViewHolder {
            private ImageView iv_qingjian;

            public PageModelHolder(View itemView) {
                super(itemView);
                iv_qingjian = (ImageView) itemView.findViewById(R.id.iv_qingjian);

            }
        }
    }

    private void goDetailActivity(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status==1){
                int userPageId = jsonObject.getInt("userPageId");
//                Toast.makeText(this, "使用页面成功userPageId："+userPageId, Toast.LENGTH_SHORT).show();
//                doSome();
                setResult(1,new Intent());
                finish();
            }else {
                Toast.makeText(this, "使用页面失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*private void doSome() {
        String url = "http://101.201.209.200:1661/qingjian/action/qingjian/user/template/list";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("timestamped", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(PageModelActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyCard myCard = new Gson().fromJson(response, MyCard.class);
//                        myCard.getData().get(myCard.getData().getsi)
                        System.out.println("response::"+response);
                    }
                });
    }*/
}
