package com.chantyou.janemarried.ui.qingjian;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.bean.AllCard;
import com.chantyou.janemarried.config.UrlConfig;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/7.
 */
public class CardModelActivity extends MyBaseActivity {

    private RecyclerView recycler_qingjian;
    private CardModelAdapter adapter;
    private ProgressBar bar;
    private Button title_back;
    private List<AllCard.DataEntity> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_cardshow);
        AppAndroid.addActivity(this);
        recycler_qingjian = (RecyclerView) findViewById(R.id.recycler_qingjian);
        bar = (ProgressBar) findViewById(R.id.bar);
        title_back = (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppAndroid.removeActivity(this);
    }

    private void initData() {
        String url = UrlConfig.QingjianHost+"/qingjian/template/list";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("timestamped", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(CardModelActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                        System.out.println("response::" + response);
                    }
                });
    }

    private void parseJson(String response) {
        AllCard allCard = new Gson().fromJson(response, AllCard.class);
        data = allCard.getData();
        if (null != data && data.size() > 0) {
            showUi();
        }
    }

    private void showUi() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler_qingjian.setLayoutManager(gridLayoutManager);
        adapter = new CardModelAdapter(this, data);
        recycler_qingjian.setAdapter(adapter);
        bar.setVisibility(View.GONE);
    }


}
