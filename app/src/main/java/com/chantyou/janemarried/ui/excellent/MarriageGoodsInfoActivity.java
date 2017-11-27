package com.chantyou.janemarried.ui.excellent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.product.ProductCommentRunner;
import com.chantyou.janemarried.httprunner.product.ProductDetailRunner;
import com.chantyou.janemarried.httprunner.product.ProductFavoriteRunner;
import com.chantyou.janemarried.ui.base.ActivityEvent;
import com.chantyou.janemarried.ui.base.BaseTabsActivity;
import com.chantyou.janemarried.ui.excellent.fragment.MgCommentFragment;
import com.chantyou.janemarried.ui.excellent.fragment.MgPicInfoFragment;
import com.chantyou.janemarried.ui.view.AutoScrollViewAdapter;
import com.chantyou.janemarried.ui.view.AutoScrollViewPager;
import com.chantyou.janemarried.ui.view.CommentView;
import com.chantyou.janemarried.ui.view.ViewPagerFocusView;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.utils.JsonUtil;
import com.taobao.applink.TBAppLinkParam;
import com.taobao.applink.TBAppLinkSDK;
import com.taobao.applink.exception.TBAppLinkException;
import com.taobao.applink.param.JumpFailedMode;
import com.taobao.applink.param.TBURIParam;

import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2015/12/19 14:21
 * Email：766082577@qq.com
 */

//婚品详情
public class MarriageGoodsInfoActivity extends BaseTabsActivity implements ActivityEvent {

    @ViewInject(R.id.adapter_main_index_viewpager)
    private AutoScrollViewPager autoViewPager;
    @ViewInject(R.id.adapter_main_index_points)
    private ViewPagerFocusView autoPoints;
    private AutoScrollViewAdapter imgAdapter;

    @ViewInject(R.id.tvTitle)
    private TextView tvTitle;
    @ViewInject(R.id.tvMoney)
    private TextView tvMoney;
    @ViewInject(R.id.tvMoney2)
    private TextView tvMoney2;
    @ViewInject(R.id.tvIntro)
    private TextView tvIntro;
    @ViewInject(R.id.tvFavorite)
    private TextView tvFavorite;
    @ViewInject(R.id.tvGoBuy)
    private TextView tvGoBuy;
    private int proId;

    @ViewInject(R.id.vComment)
    View vComment;
    private CommentView mCommentView;

    private Map<String, Object> info;

    public static void launch(Context cxt, int proId) {
        Intent intent = new Intent(cxt, MarriageGoodsInfoActivity.class);
        intent.putExtra("proId", proId);
        cxt.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        proId = getIntent().getIntExtra("proId", 0);
        if(proId == 0) {
            CustomToast.showWorningToast(this, "商品Id错误");
            finish();
        }
        return R.layout.activity_marriagegoodsinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autoViewPager.stopAutoScroll();
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
    }

    @Override
    protected void init() {
        super.init();

        ((TabLayout) findViewById(R.id.tabs)).setTabMode(TabLayout.MODE_FIXED);

        imgAdapter = new AutoScrollViewAdapter(autoPoints);
        autoViewPager.setAdapter(imgAdapter);
        autoViewPager.setOnPageChangeListener(imgAdapter);

        mCommentView = new CommentView(findViewById(R.id.view_comment));

        TBAppLinkParam param = new TBAppLinkParam(getString(R.string.tb_key), getString(R.string.tb_secret), "","");
        TBAppLinkSDK.getInstance().init(param);
        TBAppLinkSDK.getInstance().setJumpFailedMode(JumpFailedMode.OPEN_H5);
        refresh();
    }

    private void refresh() {
        pushEvent(new ProductDetailRunner(proId + ""));
    }

    @Override
    protected void setupViewPager(BaseFragmentPagerAdapter adapter) {
        adapter.addFragment(MgPicInfoFragment.class, "图文详情");
//        adapter.addFragment(MgCommentFragment.class, "口碑");
        Bundle bundle = new Bundle();
        bundle.putInt("proId", proId);
        adapter.addFragment(MgCommentFragment.class, "评论", bundle);
    }

    private void setProductInfo() {
        if(info != null) {
            imgAdapter.setData((List<Map<String, Object>>) info.get("zhanshi"));
            autoViewPager.startAutoScroll(2000);
            autoViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
            autoViewPager.setStopScrollWhenTouch(true);
            tvTitle.setText(JsonUtil.getItemString(info, "name"));
            tvIntro.setText(JsonUtil.getItemString(info, "content"));
            tvFavorite.setText(JsonUtil.getItemInt(info, "ifcollect") == 1 ? "已收藏" : "收藏");
            double discountPrice = JsonUtil.parseDouble(info.get("discountPrice"));
            double price = JsonUtil.parseDouble(info.get("price"));
            if(discountPrice <= 0) {
                discountPrice = price;
            }
            tvMoney.setText(String.format(getString(R.string.fmt_money), discountPrice + ""));
            tvMoney2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            tvMoney2.setText(String.format(getString(R.string.fmt_money), (price) + ""));

//            List<Map<String, Object>> images = (List<Map<String, Object>>) info.get("images");

        }
    }

    @OnClick({R.id.fab, R.id.tvFavorite,R.id.tvGoBuy})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                onShowCommentView();
                break;
            case R.id.tvFavorite:
                if(info != null) {
                    pushEvent(new ProductFavoriteRunner(proId, JsonUtil.getItemInt(info, "ifcollect") == 1 ? 0 : 1));
                }
                break;
            case R.id.tvGoBuy:
                if(info != null) {
//                    WebPageActivity.launch(this, JsonUtil.getItemString(info, "name"), JsonUtil.getItemString(info, "taobaoUrl"));
                    TBURIParam param = new TBURIParam(JsonUtil.getItemString(info, "taobaoUrl"));
//                    param.setBackUrl("...").setE("...");
//                    HashMap<String,String> map = new HashMap<>();
//                    map.put("your_data_key","your_data");
//                    map.put("...","...");
//                    param.addExtraParams(map);
                    try {
                        TBAppLinkSDK.getInstance().jumpTBURI(this, param);
                    } catch (TBAppLinkException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * Toolbar（右标题）菜单选项
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem item = menu.findItem(R.id.main_menu);
//        item.setIcon(R.drawable.navbar_icon_share);
        return true;
    }

    /**
     * Toolbar 菜单点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCommentBackBtnClick() {
        hidenSoftInput(mCommentView.getEt());
        vComment.setVisibility(View.GONE);
        mCommentView.setTxt("", 0, "");
    }

    @Override
    public void onShowCommentView(Object... args) {
        vComment.setVisibility(View.VISIBLE);
        showSoftInput(mCommentView.getEt());
//        mCommentView.setTag(args[0]);
        mCommentView.setTxt("", 0, "");
    }

    @Override
    public void onCommentBtnClick(Object... args) {
        hidenSoftInput(mCommentView.getEt());
        if(args != null) {
            publishComment(proId+"", (String) args[2]);
        }
    }

    private void publishComment(String topicId, String comment) {
        pushEvent(new ProductCommentRunner(topicId, comment));
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_PRODUCT_DETAIL:
                if(event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    map = (Map<String, Object>) map.get("prodetail");
                    info = map;
                    setProductInfo();
                }
                break;
            case XEventCode.HTTP_PRODUCT_COMMENT:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, "评论成功");
                    onCommentBackBtnClick();
                }
                break;
            case XEventCode.HTTP_PRODUCT_FAVORITE:
                if(event.isSuccess()) {
                    CustomToast.showRightToast(this, "操作成功");
                    refresh();
                } else {
                    CustomToast.showWorningToast(this, "操作失败");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MarriageGoodsInfoActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MarriageGoodsInfoActivity.this);
    }
}
