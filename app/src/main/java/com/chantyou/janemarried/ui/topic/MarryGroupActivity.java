package com.chantyou.janemarried.ui.topic;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.ui.topic.fragment.MgChoiceFragment;
import com.chantyou.janemarried.ui.topic.fragment.MgLatestFragment;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.annotations.ViewInject;

/**
 * Created by j_turn on 2015/12/15 14:22
 * Email：766082577@qq.com
 */
//结婚绑
public class MarryGroupActivity extends XBaseActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rbtn1)
    private RadioButton rbtn1;
    @ViewInject(R.id.rbtn2)
    private RadioButton rbtn2;
    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_marrygroup);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        ((RadioGroup) findViewById(R.id.rgp)).setOnCheckedChangeListener(this);
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        toolbarAttribute.setNavigation(false, null, null);
        toolbarAttribute.setTitleAttr(false, Gravity.LEFT, null);
    }

    private void setupViewPager(ViewPager viewPager) {
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MgChoiceFragment.class);
        adapter.addFragment(MgLatestFragment.class);
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
        if(checkedId == R.id.rbtn1) {
            mViewPager.setCurrentItem(0);
        } else if(checkedId == R.id.rbtn2) {
            mViewPager.setCurrentItem(1);
        }
    }

    /**
     * Toolbar（右标题）菜单选项
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.icon_plus);
        return true;
    }

    /**
     * Toolbar 菜单点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                launch(this, TopicAddActivity.class);
                break;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MarryGroupActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MarryGroupActivity.this);
    }
}
