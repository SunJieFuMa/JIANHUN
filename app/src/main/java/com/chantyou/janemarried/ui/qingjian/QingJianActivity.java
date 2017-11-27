package com.chantyou.janemarried.ui.qingjian;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.bean.MyCard;
import com.chantyou.janemarried.config.UrlConfig;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/7.
 */
public class QingJianActivity extends MyBaseActivity {//自定义的接口，没用到
    private Button title_back;
    private RecyclerView recycler_qingjian;
    private ProgressBar bar;
    private QingJianAdapter adapter;
    private List<MyCard.DataEntity> data;
    private int lastOffset;
    private int lastPosition;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        //        AppAndroid.addActivity(this);
        setContentView(R.layout.activity_qingjian);
        System.out.println("用户的id为：：：" + AppAndroid.getUid() + "");
        bar = (ProgressBar) findViewById(R.id.bar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recycler_qingjian = (RecyclerView) findViewById(R.id.recycler_qingjian);
        title_back = (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
        initListener();//恢复recyclerview滚动的位置
        initRefrshLayout();
    }


    private void initRefrshLayout() {
        //可以设置圈圈的颜色,怎么不管用
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light);
        //可以设置圈圈的背景颜色，默认是白色
        //        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(android.R.color.background_dark));
        //        mSwipeRefreshLayout.setDistanceToTriggerSync(150);//设置滑动多少距离出来刷新头，可以使用默认的，不用自己设置
        //设置刷新事件的监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //                SystemClock.sleep(2000);//这样sleep没用
                //模拟加载数据
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);//这里可以进行从网络上拉取数据
                        //在主线程中执行更新UI
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //得到adapter.然后刷新
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                                //停止刷新操作
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });

                    }
                }).start();*/

                /*
                这里也可以用这个方法，handler在哪new的Runnable就在哪个线程中执行，这里是在主线程
                postDelayed()方法里的Runnable的执行是在Handler对象所在的线程
                如果其所在的线程是UI线程的话，Runnable中还是不能执行耗时操作，不然会ANR
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();//为了实现刷新头转几圈的效果，所以加了一个handler延迟
                        //停止刷新操作
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });
    }

    private void initListener() {
        //监听recyclerView的滚动
        recycler_qingjian.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    //记录RecyclerView当前位置
                    GridLayoutManager layoutManager = (GridLayoutManager) recycler_qingjian.getLayoutManager();
                    //获取可视的第一个view
                    View topView = layoutManager.getChildAt(0);
                    if (topView != null) {
                        //获取与该view的顶部的偏移量
                        lastOffset = topView.getTop();
                        //得到该View的数组位置
                        lastPosition = layoutManager.getPosition(topView);

                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        AppAndroid.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //这里让swipeRefreshLayout自动刷新,怎么实现？
        //如果在onResume()方法中没有进行setAdapter的操作，那么RecyclerView的位置就不会改变，也就是之前滑动到哪再返回的时候还是那个位置
        initData();
//        recycler_qingjian.smoothScrollToPosition(7);//这样是平滑的滚动到某个位置，也要在initData()方法里面跟着调用才可以

    }

    private void initData() {
        String url = UrlConfig.QingjianHost+"/qingjian/user/template/list";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("timestamped", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(QingJianActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                        System.out.println("response::" + response);
                    }
                });
    }

    private void parseJson(String response) {
        MyCard myCard = new Gson().fromJson(response, MyCard.class);
        data = myCard.getData();
        showUi();
    }

    private void showUi() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recycler_qingjian.setLayoutManager(gridLayoutManager);
        //R.drawable.icon_add_qingjian格式的本身是int类型的值
        //        int icon_add_qingjian = R.drawable.icon_add_qingjian;
        //        imgUrls.add(0,""+icon_add_qingjian);
        adapter = new QingJianAdapter(this, data);
        recycler_qingjian.setAdapter(adapter);
        bar.setVisibility(View.GONE);
        //下面这几行代码必须在这里调用，如果在onresume()方法中紧跟着initData()调用的话是不管用的，因为不是在一个线程中
        if(recycler_qingjian.getLayoutManager() != null && lastPosition >= 0) {
           ((GridLayoutManager) recycler_qingjian.getLayoutManager())
            .scrollToPositionWithOffset(lastPosition, lastOffset);//这个方法是在recyclerview的布局管理器中的
                            }
    }

}
