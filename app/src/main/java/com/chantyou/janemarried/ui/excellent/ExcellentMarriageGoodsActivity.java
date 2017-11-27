package com.chantyou.janemarried.ui.excellent;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.product.ProductCateRunner;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.ui.excellent.fragment.ExcellentMarriageGoodsFragment;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.utils.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2015/12/12 14:24
 * Email：766082577@qq.com
 */
public class ExcellentMarriageGoodsActivity extends XBaseActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_basetabs);
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        toolbarAttribute.setNavigation(false, null, null);
    }

    @Override
    protected void init() {
        super.init();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pushEvent(new ProductCateRunner());
    }

    private void setPagers(List<Map<String,Object>> cates) {
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        if(cates != null && cates.size() > 0) {
            for(Map<String, Object> map : cates) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", JsonUtil.getItemInt(map, "cateId"));
                adapter.addFragment(ExcellentMarriageGoodsFragment.class, JsonUtil.getItemString(map, "name"), bundle);
            }
        }
        mViewPager.setAdapter(adapter);
        if (mViewPager != null && tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        MenuItem action_search = menu.findItem(R.id.navbar_search); //获取MenuItem的实例,注意,这里获取的不是SearchView的实例.
////        action_search.setVisible(false); //设置Item是否可见,这里与View的设置不太一样,接受的是boolean值,只有两种状态
////        searchViewInit(menu);//自定义的一个初始化SearchView的方法
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    /**
     * SearchView的初始化
     */
    private void searchViewInit(Menu menu) {
        //这里需要用到SearchView的布局文件[1],文章结尾有github的链接
        //用MenuItem的`getActionView()`方法获取`android:actionViewClass`说对应的实例,这里是`android.widget.SearchView`
        SearchView searchView = null;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        searchView = (SearchView) menu.findItem(R.id.navbar_search).getActionView();
        searchView.setSubmitButtonEnabled(true);//是否显示确认搜索按钮
        searchView.setIconifiedByDefault(false);//设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
        searchView.setIconified(false);//设置
        searchView.clearFocus();//清除焦点
        /*
        这里是重点,SearchView并没有提供样式的修改方法,所以只能
        1.用获取到的实例调用getContext()方法,返回当前view的上下文
        2.调用getResources()方法,获取该view的资源实例(Return a Resources instance)
        3.调用getIdentifier()方法,获取相同名字的ID,(Return a resource identifier for the given resource name)
        4.通过findViewById()获取该ID的实例,然后就可以做相应的操作了
        */
        int search_mag_icon_id = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView  search_mag_icon = (ImageView)searchView.findViewById(search_mag_icon_id);//获取搜索图标
        search_mag_icon.setImageResource(R.drawable.navbar_icon_search);//图标都是用src的

        //设置提示文字的颜色,这里走了点奇招,用Html类方法
        searchView.setQueryHint(Html.fromHtml("<font color = #999999>" + getResources().getString(R.string.search) + "</font>"));

        //设置搜索事件的监控
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //当点击搜索按钮,输入法搜索按钮,会触发这个方法.在这里做相应的搜索事件,query为用户输入的值
                //当输入框为空或者""时,此方法没有被调用
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入的文字发生变化的时候,会触发这个方法.在这里做匹配提示的操作等
                return true;
            }
        });
        //如果希望SearchView一直处理展开状态,可以实现它的OnCloseListener方法
        //返回true,截取关闭事件,不让搜索框收起来
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                return true;
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PRODUCT_CATE:
                if(event.isSuccess()) {
                    Map<String , Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    setPagers((List<Map<String, Object>>) map.get("data"));
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ExcellentMarriageGoodsActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ExcellentMarriageGoodsActivity.this);
    }
}
