package com.chantyou.janemarried.ui.qingjian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.bean.MyCard;
import com.chantyou.janemarried.config.UrlConfig;
import com.chantyou.janemarried.ui.base.ThreadShareActivity;
import com.google.gson.Gson;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.ui.base.ScalePageTransformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/10.
 */
public class CardDetailActivity extends ThreadShareActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private MyCard.DataEntity dataEntity;
    //    private List<BaseFragment> mFragments=new ArrayList<>();
    //    private QingJianDetailPagerAdapter adapter;
    private int pageCount;
    private BaseFragmentPagerAdapter adapter;
    private Button title_back;
    private Button title_setting;
    private FrameLayout vitem_yulan;
    private FrameLayout vShare;
    private int pos;
    private int myPosition;
    private int fu_width;
    private int fu_height;
    private int a;
    private int b;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_detail_qingjian);
        myPosition = getIntent().getIntExtra("position", 0);//这个position主要用于刷新界面
        dataEntity = (MyCard.DataEntity) getIntent().getSerializableExtra("detail");


        vShare = (FrameLayout) findViewById(R.id.vShare);
        vShare.setOnClickListener(this);
        vitem_yulan = (FrameLayout) findViewById(R.id.vitem_yulan);
        vitem_yulan.setOnClickListener(this);
        title_setting = (Button) findViewById(R.id.title_setting);
        title_back = (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        title_setting.setOnClickListener(this);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fu_width = dataEntity.getWidth();
        fu_height = dataEntity.getHeight();
        /*viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //                int width = viewPager.getWidth();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewPager.getLayoutParams();
                double fu_r = (double) fu_width / (double)fu_height;
                double v = AppAndroid.getScreenWidth() * 0.68;
                a=params.width= (int) v;
                b=params.height= (int) (params.width/fu_r);
                SpUtils.putInt(AppAndroid.getContext(),"width",a);
                SpUtils.putInt(AppAndroid.getContext(),"height",b);

//                489  795
//                Toast.makeText(CardDetailActivity.this, params.width+" "+params.height, Toast.LENGTH_SHORT).show();
                viewPager.setLayoutParams(params);
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });*/

        viewPager.setPageTransformer(true, new ScalePageTransformer(1.0f, 0.75f));//用于ViewPager的滑动动画
        viewPager.setOffscreenPageLimit(3);//最少缓存3页
        //动态设置viewpager的宽高
//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewPager.getLayoutParams();
//        layoutParams.width= (int) (AppAndroid.getScreenWidth()*0.7);
//        layoutParams.height=(int) (AppAndroid.getScreenHeight()*0.62);
//        viewPager.setLayoutParams(layoutParams);
        //在onCreate中得到的结果都为0，要在onWindowFocusChanged方法中才能取到值
        //        int[] ints = new int[2];
        //        viewPager.getLocationOnScreen(ints);
        //        Toast.makeText(this, "viewpage::"+ints[0]+" "+ints[1], Toast.LENGTH_SHORT).show();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                if (position == 0 || position == pageCount - 1 || position == pageCount - 2) {
                    title_setting.setText("");
                } else {
                    title_setting.setText("删除");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initData();
    }

    private void initData() {
        //        Toast.makeText(this, "执行了----- ", Toast.LENGTH_SHORT).show();

//        Toast.makeText(CardDetailActivity.this, a+" "+b, Toast.LENGTH_SHORT).show();
        int myPageCount = dataEntity.getPages().size();
        pageCount = 3 + myPageCount;//3是指不添加页面模板的情况下就有的页面

        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        //三个基本页面--首页
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataEntity", dataEntity);
        bundle.putInt("layId", R.layout.adapter_qingjian_card1);
        //        bundle.putInt("left", ints[0]);
        //        bundle.putInt("top", ints[1]);
        adapter.addFragment(DetailCardFragment1.class, null, bundle);
        //自己添加的页面
        if (myPageCount > 0) {
            for (int i = 0; i < myPageCount; i++) {
                Bundle mybundle = new Bundle();
                mybundle.putSerializable("pages", dataEntity.getPages().get(i));
                mybundle.putInt("layId", R.layout.adapter_qingjian_card4);
//                mybundle.putInt("templateId", dataEntity.getTemplateId());//没用到
//                mybundle.putInt("position", viewPager.getCurrentItem());//没用到
                adapter.addFragment(DetailCardFragment4.class, null, mybundle);

                //                Bundle mybundle = new Bundle();
                //                mybundle.putSerializable("pages", dataEntity.getPages().get(i));
                //                mybundle.putInt("layId", R.layout.adapter_qingjian_card4);
                //                mybundle.putInt("templateId",dataEntity.getTemplateId());
                //                adapter.addFragment(DetailCardFragment5.class, null, mybundle);
            }
        }

        //三个基本页面--选择模板页
        bundle = new Bundle();
        bundle.putInt("layId", R.layout.adapter_qingjian_card2);
        bundle.putSerializable("dataEntity", dataEntity);
        adapter.addFragment(DetailCardFragment2.class, null, bundle);
        //三个基本页面--尾页
        bundle = new Bundle();
        bundle.putInt("layId", R.layout.adapter_qingjian_card3);
        bundle.putString("endImage", dataEntity.getEndImage());
        adapter.addFragment(DetailCardFragment3.class, null, bundle);

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_setting:
                //点击了删除按钮
                if (!TextUtils.isEmpty(title_setting.getText().toString())) {
                    delete();
                }
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.vitem_yulan://点击了预览
                look();
                break;
            case R.id.vShare://点击了分享
                share();
                break;
        }
    }

    private void share() {
        String url="http://www.easymarrytec.com:1661/qingjian/v/vdemo.html?" +
                "userId="+ AppAndroid.getUid()+"&templateId=" + dataEntity.getId();
        showShareDialog(url,
                "我们结婚啦！【"+dataEntity.getFirstPage().get(0).getTextContent()
                        +"&"+dataEntity.getFirstPage().get(1).getTextContent()+"】的婚礼邀请函！",
                "我们结婚啦！诚挚的邀请您来见证我们的浪漫婚礼！");
        return;
    }

    private void look() {
//        String url="http://www.easymarrytec.com:1661/qingjian/v/vdemo.html?" +
//                "userId=0&templateId=" + dataEntity.getId();
        Intent intent=new Intent(this,QingJianLookActivity.class);
        intent.putExtra("dataEntity", dataEntity);//之前傻得把这行放到下面去了
        this.startActivity(intent);
    }

    int[] ints = new int[2];

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        viewPager.getLocationOnScreen(ints);
        //        Toast.makeText(this, "viewpage::"+ints[0]+" "+ints[1], Toast.LENGTH_SHORT).show();
    }

    private void delete() {
        //                Toast.makeText(getContext(), "点击了删除按钮", Toast.LENGTH_SHORT).show();

        final String url = UrlConfig.QingjianHost+"/qingjian/user/page/del";
        System.out.println("pageId::" + dataEntity.getPages().get(pos - 1).getId()
                + " userTempleteId::" + dataEntity.getPages().get(pos - 1).getUserTempleteId());
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("pageId", dataEntity.getPages().get(pos - 1).getId() + "")
                //减1是因为第一页是展示页，没有删除选项，也就是删除选项是从第二页开始的，所以要减1
                //                .addParams("pageId", pages.getPageId() + "")
                //                .addParams("userTempleteId", templateId + "")
                .addParams("userTempleteId", dataEntity.getPages().get(pos - 1).getUserTempleteId() + "")
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
        try {
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                refreshDel();
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        //        refresh();不好使
    }

    private void refreshDel() {
        //不用再重新从网络上请求一遍了，直接删除页面就行，再次进入的时候就会自动更新网络上的数据
//        adapter.removeFragment(viewPager.getCurrentItem());//我自己加了这个方法后就成功了
//        viewPager.removeViewAt(viewPager.getCurrentItem());
//        adapter.notifyDataSetChanged();
//        viewPager.setCurrentItem(pos,false);//去掉动画，不管用
        //删除的时候总是有个动画去不了
//        viewPager.setCurrentItem(pos,true);

        //因为删除时的动画去不掉，所以就重新从网络上获取一遍数据
        refreshDelPage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
//            Toast.makeText(this, "返回了", Toast.LENGTH_SHORT).show();
            //这个是添加了页面模板后返回刷新页面
            refreshAddModelPage();
        }else if(resultCode==2){
            //这个是在页面模板添加图片后返回刷新页面
            refreshAddPhoto();
        } else if(resultCode==3){
            //这个重写新郎新娘信息后返回刷新页面
            refreshFirstPage();
        }
    }

    //删除页面后刷新数据
    private void refreshDelPage() {
        //重新获取一遍用户的请柬数据
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
                        Toast.makeText(CardDetailActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*
                        之前因为用的是FragmentPagerAdapter，好像是缓存的问题，
                        总是不成功，改成了FragmentStatePagerAdapter就成功了
                         */
                        adapter.clear();
                        viewPager.removeAllViews();
                        pageCount = 0;//重置
                        MyCard myCard = new Gson().fromJson(response, MyCard.class);
                        dataEntity = myCard.getData().get(myPosition);
                        int myPageCount = dataEntity.getPages().size();
                        pageCount = 3 + myPageCount;//3是指不添加页面模板的情况下就有的页面

                        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
                        //三个基本页面--首页
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataEntity", dataEntity);
                        bundle.putInt("layId", R.layout.adapter_qingjian_card1);
                        adapter.addFragment(DetailCardFragment1.class, null, bundle);
                        //自己添加的页面
                        if (myPageCount > 0) {
                            for (int i = 0; i < myPageCount; i++) {
                                Bundle mybundle = new Bundle();
                                mybundle.putSerializable("pages", dataEntity.getPages().get(i));
                                mybundle.putInt("layId", R.layout.adapter_qingjian_card4);
                                //                                mybundle.putInt("templateId", dataEntity.getTemplateId());//没用到
                                adapter.addFragment(DetailCardFragment4.class, null, mybundle);
                            }
                        }

                        //三个基本页面--选择模板页
                        bundle = new Bundle();
                        bundle.putInt("layId", R.layout.adapter_qingjian_card2);
                        bundle.putSerializable("dataEntity", dataEntity);
                        adapter.addFragment(DetailCardFragment2.class, null, bundle);
                        //三个基本页面--尾页
                        bundle = new Bundle();
                        bundle.putInt("layId", R.layout.adapter_qingjian_card3);
                        bundle.putString("endImage", dataEntity.getEndImage());
                        adapter.addFragment(DetailCardFragment3.class, null, bundle);

                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(pos);
                        System.out.println("response::" + response);
                    }
                });
    }

    private void refreshFirstPage() {
        //重新获取一遍用户的请柬数据
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
                        Toast.makeText(CardDetailActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*
                        之前因为用的是FragmentPagerAdapter，好像是缓存的问题，
                        总是不成功，改成了FragmentStatePagerAdapter就成功了
                         */
                        adapter.clear();
                        viewPager.removeAllViews();
                        pageCount = 0;//重置
                        MyCard myCard = new Gson().fromJson(response, MyCard.class);
                        dataEntity = myCard.getData().get(myPosition);
                        int myPageCount = dataEntity.getPages().size();
                        pageCount = 3 + myPageCount;//3是指不添加页面模板的情况下就有的页面

                        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
                        //三个基本页面--首页
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataEntity", dataEntity);
                        bundle.putInt("layId", R.layout.adapter_qingjian_card1);
                        adapter.addFragment(DetailCardFragment1.class, null, bundle);
                        //自己添加的页面
                        if (myPageCount > 0) {
                            for (int i = 0; i < myPageCount; i++) {
                                Bundle mybundle = new Bundle();
                                mybundle.putSerializable("pages", dataEntity.getPages().get(i));
                                mybundle.putInt("layId", R.layout.adapter_qingjian_card4);
//                                mybundle.putInt("templateId", dataEntity.getTemplateId());//没用到
                                adapter.addFragment(DetailCardFragment4.class, null, mybundle);
                            }
                        }

                        //三个基本页面--选择模板页
                        bundle = new Bundle();
                        bundle.putInt("layId", R.layout.adapter_qingjian_card2);
                        bundle.putSerializable("dataEntity", dataEntity);
                        adapter.addFragment(DetailCardFragment2.class, null, bundle);
                        //三个基本页面--尾页
                        bundle = new Bundle();
                        bundle.putInt("layId", R.layout.adapter_qingjian_card3);
                        bundle.putString("endImage", dataEntity.getEndImage());
                        adapter.addFragment(DetailCardFragment3.class, null, bundle);

                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(0);//回到首页
                        System.out.println("response::" + response);
                    }
                });
    }

    private void refreshAddPhoto() {
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
                        Toast.makeText(CardDetailActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*
                        之前因为用的是FragmentPagerAdapter，好像是缓存的问题，
                        总是不成功，改成了FragmentStatePagerAdapter就成功了
                         */
                        adapter.clear();
                        viewPager.removeAllViews();
                        pageCount = 0;//重置
                        MyCard myCard = new Gson().fromJson(response, MyCard.class);
                        dataEntity = myCard.getData().get(myPosition);
                        int myPageCount = dataEntity.getPages().size();
                        pageCount = 3 + myPageCount;//3是指不添加页面模板的情况下就有的页面

                        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
                        //三个基本页面--首页
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataEntity", dataEntity);
                        bundle.putInt("layId", R.layout.adapter_qingjian_card1);
                        adapter.addFragment(DetailCardFragment1.class, null, bundle);
                        //自己添加的页面
                        if (myPageCount > 0) {
                            for (int i = 0; i < myPageCount; i++) {
                                Bundle mybundle = new Bundle();
                                mybundle.putSerializable("pages", dataEntity.getPages().get(i));
                                mybundle.putInt("layId", R.layout.adapter_qingjian_card4);
//                                mybundle.putInt("templateId", dataEntity.getTemplateId());//没用到
                                adapter.addFragment(DetailCardFragment4.class, null, mybundle);
                            }
                        }

                        //三个基本页面--选择模板页
                        bundle = new Bundle();
                        bundle.putInt("layId", R.layout.adapter_qingjian_card2);
                        bundle.putSerializable("dataEntity", dataEntity);
                        adapter.addFragment(DetailCardFragment2.class, null, bundle);
                        //三个基本页面--尾页
                        bundle = new Bundle();
                        bundle.putInt("layId", R.layout.adapter_qingjian_card3);
                        bundle.putString("endImage", dataEntity.getEndImage());
                        adapter.addFragment(DetailCardFragment3.class, null, bundle);

                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(pos);
                        System.out.println("response::" + response);
                    }
                });
    }

    public void refreshAddModelPage() {
        //        initData();
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
                        Toast.makeText(CardDetailActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*
                        之前因为用的是FragmentPagerAdapter，好像是缓存的问题，
                        总是不成功，改成了FragmentStatePagerAdapter就成功了
                         */
                        adapter.clear();
                        viewPager.removeAllViews();
                        pageCount = 0;//重置
                        MyCard myCard = new Gson().fromJson(response, MyCard.class);
                        dataEntity = myCard.getData().get(myPosition);
                        int myPageCount = dataEntity.getPages().size();
                        pageCount = 3 + myPageCount;//3是指不添加页面模板的情况下就有的页面

                        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
                        //三个基本页面--首页
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataEntity", dataEntity);
                        bundle.putInt("layId", R.layout.adapter_qingjian_card1);
                        adapter.addFragment(DetailCardFragment1.class, null, bundle);
                        //自己添加的页面
                        if (myPageCount > 0) {
                            for (int i = 0; i < myPageCount; i++) {
                                Bundle mybundle = new Bundle();
                                mybundle.putSerializable("pages", dataEntity.getPages().get(i));
                                mybundle.putInt("layId", R.layout.adapter_qingjian_card4);
//                                mybundle.putInt("templateId", dataEntity.getTemplateId());//没用到
                                adapter.addFragment(DetailCardFragment4.class, null, mybundle);
                            }
                        }

                        //三个基本页面--选择模板页
                        bundle = new Bundle();
                        bundle.putInt("layId", R.layout.adapter_qingjian_card2);
                        bundle.putSerializable("dataEntity", dataEntity);
                        adapter.addFragment(DetailCardFragment2.class, null, bundle);
                        //三个基本页面--尾页
                        bundle = new Bundle();
                        bundle.putInt("layId", R.layout.adapter_qingjian_card3);
                        bundle.putString("endImage", dataEntity.getEndImage());
                        adapter.addFragment(DetailCardFragment3.class, null, bundle);

                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(pageCount - 3);//新添加的模板页就放在倒数第三个的位置上
                        System.out.println("response::" + response);
                    }
                });
    }
}
