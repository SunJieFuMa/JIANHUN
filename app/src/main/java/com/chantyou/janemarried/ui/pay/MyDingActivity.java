package com.chantyou.janemarried.ui.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.base.MyBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
public class MyDingActivity extends MyBaseActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private String[] titles = {"待付款", "已完成", "待评价"};
    private ViewPager id_viewpager;
    private List<MyBaseFragment> mFragments;
    private MyAdapter adapter;
    private TabLayout id_tablayout;//一定要注意这里导入的tablayout是哪个包的，否则方法不管用
    private String shopPhone;//商家的电话号码
    private String ding_status;
    private Button title_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myding);
        //        mRecyclerView = (RecyclerView) findViewById(R.id.ding_recyclerView);
        //        initData();
        id_viewpager = (ViewPager) findViewById(R.id.id_viewpager);
        id_tablayout = (TabLayout) findViewById(R.id.id_tablayout);
        ding_status = getIntent().getStringExtra("ding_status");
        title_back= (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        initPager();
        //post请求--测试
       /* OkHttpUtils
                .post()
                .url("http://101.201.209.200/MarryTrade/interfaces/user!checkLogin.action")
                .addParams("type", "1")
                .addParams("phone", "15732634148")
                .addParams("pswd", MD5Utils.md5("123456"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("出错了");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("令牌是："+response);
                    }
                });*/
    }


    private void initPager() {
        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            MyDingFragment fragment = new MyDingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", titles[i]);
            bundle.putString("shopPhone", getIntent().getStringExtra("shopPhone"));//将商家的电话号码传递给fragment
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }

        //                id_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        /*
        加上这个之后tabLayout就不能占满屏幕宽度了，当tab数码少的时候要想tabLayout占满整个屏幕，有两个办法
        一个是在代码中设置：
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        或者在xml文件中设置：
        app:tabGravity="fill"
        app:tabMode="fixed"
         */

        adapter = new MyAdapter(getSupportFragmentManager(), titles, mFragments);
        // 设置ViewPager最大缓存的页面个数
        id_viewpager.setOffscreenPageLimit(3);
        id_viewpager.setAdapter(adapter);
        id_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //                mFragments.get(position).setNewsId(position+1);
                //                mFragments.get(position).initData();
                System.out.println("onPageSelected看看每次都会被调用吗" + (position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 将TabLayout和ViewPager进行关联，让两者联动起来
        id_tablayout.setupWithViewPager(id_viewpager);

        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        id_tablayout.setTabsFromPagerAdapter(adapter);
        if ("waitpay".equals(ding_status)) {//如果不这样写容易空指针异常，因为ding_status一开始为null
            //待支付订单，跳到待支付的fragment
            id_viewpager.setCurrentItem(0);
        }
    }

    private void initData() {
        //设置layoutManager
        LinearLayoutManager layoutManger = new LinearLayoutManager(MyDingActivity.this);
        //为RecyclerView创建布局管理器，默认是垂直布局
        // 这里使用的是LinearLayoutManager，表示里面的Item排列是线性排列
        mRecyclerView.setLayoutManager(layoutManger);

        //设置adapter
        //        MyDingAdapter adapter= new MyDingAdapter(MyDingActivity.this, mDatas);
        //        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }
}
