package com.chantyou.janemarried.ui.excellent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.data.SharedPreferenceKey;
import com.chantyou.janemarried.httprunner.shop.ShopListRunner;
import com.chantyou.janemarried.ui.base.CitySelectActivity;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.ui.excellent.fragment.ExcellentGoodFragment;
import com.chantyou.janemarried.ui.shop.ExcellentShopFragment;
import com.chantyou.janemarried.ui.shop.SearchShopActivity;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.annotations.ViewInject;
//优婚品Tab
//商户、优婚品

/**
 * Created by j_turn on 2015/12/12 14:24
 * Email：766082577@qq.com
 */
public class ExcellentMarriageShopActivity extends XBaseActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rbtn1)
    private RadioButton rbtn1;
    @ViewInject(R.id.rbtn2)
    private RadioButton rbtn2;
    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;
    @ViewInject(R.id.tv_sel_address)
    private TextView tv_sel_address;
    private ImageView iv_search;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_excellent_marriage_shop);
        iv_search= (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击搜索按钮跳转到新的activity界面
                Intent intent=new Intent(ExcellentMarriageShopActivity.this,SearchShopActivity.class);
                startActivity(intent);
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        ((RadioGroup) findViewById(R.id.rgp)).setOnCheckedChangeListener(this);


        initListener();//点击城市的时候才会跳转过去进行定位

        String city = SharedPreferenceUtils.getString(ExcellentMarriageShopActivity.this, SharedPreferenceKey.userCity, "济南");
        if ("".equalsIgnoreCase(city)) {
            city = "济南";
            SharedPreferenceUtils.putString(ExcellentMarriageShopActivity.this,SharedPreferenceKey.userCity,"济南");
        }
        tv_sel_address.setText(city);
        System.out.println("zhuwx:商户城市，" + SharedPreferenceUtils.getString(ExcellentMarriageShopActivity.this, SharedPreferenceKey.userCity, "济南"));
    }


    private void initListener() {
        //切换区域
        tv_sel_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mToastManager.show("选择地区");
                CitySelectActivity.launchForResult(ExcellentMarriageShopActivity.this, false);

            }
        });
    }

    //地区选择返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CitySelectActivity.RCODE) {
                tv_sel_address.setText(data.getStringExtra("returncity"));
                SharedPreferenceUtils.putString(ExcellentMarriageShopActivity.this,
                        SharedPreferenceKey.userCity, data.getStringExtra("returncity"));
//                pushEvent(new ShopListRunner("", data.getStringExtra("returncity"), "", "", "", "3", "1", "20"), this);
                //把排序也就是倒数第二个参数赋为空字符串
                pushEvent(new ShopListRunner("", data.getStringExtra("returncity"), "", "", "", "", "1", "20"), this);
            }
        }
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        toolbarAttribute.setNavigation(false, null, null);
        toolbarAttribute.setTitleAttr(false, Gravity.LEFT, null);
    }

    private void setupViewPager(ViewPager viewPager) {
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ExcellentShopFragment.class);//商铺
        adapter.addFragment(ExcellentGoodFragment.class);//优婚品
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rbtn1.setChecked(position == 0);
                rbtn2.setChecked(position == 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbtn1) {
            mViewPager.setCurrentItem(0);
        } else if (checkedId == R.id.rbtn2) {
            mViewPager.setCurrentItem(1);
        }
    }

    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置右侧按钮
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem item = menu.findItem(R.id.main_menu);
//        item.setIcon(R.drawable.navbar_icon_search);//设置右侧按钮
        return true;
    }

    /**
     * Toolbar 菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.main_menu://设置右侧按钮
//                launch(this, TopicAddActivity.class);
//                break;
//        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ExcellentMarriageShopActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ExcellentMarriageShopActivity.this);
    }


    //后台返回数据
    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {

        }
    }
}
