package com.chantyou.janemarried.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.ShopPackageInfoRunner;
import com.chantyou.janemarried.ui.base.ActivityEvent;
import com.chantyou.janemarried.ui.base.BaseTabsActivity;
import com.chantyou.janemarried.ui.base.ThreadShareHelper;
import com.chantyou.janemarried.ui.pay.WriteOrderActivity;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.framework.CustomToast;

import java.util.Map;


//套餐详情
public class ShopPackageInfoActivity extends BaseTabsActivity implements ActivityEvent {


    @ViewInject(R.id.tvTitle)
    private TextView tvTitle;
    @ViewInject(R.id.tvMoney)
    private TextView tvMoney;

    @ViewInject(R.id.tvIntro)
    private TextView tvIntro;
    @ViewInject(R.id.tvFavorite)
    private TextView tvFavorite;
    @ViewInject(R.id.tvGoBuy)
    private TextView tvGoBuy;
    @ViewInject(R.id.iv_head)
    private ImageView iv_head;
    @ViewInject(R.id.btn_buy)
    private Button btn_buy;

    private int shopId;
    private int productId;
    private String imageUrl;
    private String hotelName;
    private String name;
    private String label;
    private String price;


    private boolean isFollowed=false;

    private Map<String, Object> info;
    private String shopPhone;


    public static void launch(Context cxt, int shopId, int productId, String imageUrl, String hotelName, String name, String price, String label,String shopPhone) {
        Intent intent = new Intent(cxt, ShopPackageInfoActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putExtra("productId", productId);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("hotelName", hotelName);
        intent.putExtra("name", name);
        intent.putExtra("price", price);
        intent.putExtra("label", label);
        intent.putExtra("shopPhone", shopPhone);//传递过来的商家的电话号码
        cxt.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        shopId = getIntent().getIntExtra("shopId", 0);
        productId = getIntent().getIntExtra("productId", 0);//套餐id
        hotelName = getIntent().getStringExtra("hotelName");
        imageUrl = getIntent().getStringExtra("imageUrl");
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        label = getIntent().getStringExtra("label");
        shopPhone = getIntent().getStringExtra("shopPhone");//传递过来的商家的电话号码
        if (productId == 0) {
            CustomToast.showWorningToast(this, "商品Id错误");
            finish();
        }
        return R.layout.activity_shop_package_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
    }

    @Override
    protected void init() {
        super.init();

        ((TabLayout) findViewById(R.id.tabs)).setTabMode(TabLayout.MODE_FIXED);

        //跳转到淘宝
//        TBAppLinkParam param = new TBAppLinkParam(getString(R.string.tb_key), getString(R.string.tb_secret), "", "");
//        TBAppLinkSDK.getInstance().init(param);
//        TBAppLinkSDK.getInstance().setJumpFailedMode(JumpFailedMode.OPEN_H5);

        setBaseInfo();//设置基本信息
        getInfoDetail();//获取详情
//        initListener();//我写的
    }

    private void initListener() {
        //处理点击了支付按钮的操作
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShopPackageInfoActivity.this,WriteOrderActivity.class);
                //将需要的参数传递过去
                intent.putExtra("name",name);
                intent.putExtra("label",label);
                intent.putExtra("price",price);
                intent.putExtra("imageUrl",imageUrl);
                intent.putExtra("shopPhone",shopPhone);//商家的电话号码
                ShopPackageInfoActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.start_activity_in,R.anim.start_activity_out);
            }
        });
    }

    private void setBaseInfo() {
        tvTitle.setText(name);
        tvIntro.setText(label);
        tvMoney.setText("￥" + price);

        if (!TextUtils.isEmpty(imageUrl)) {
            HImageLoader.displayImage(imageUrl, iv_head, R.drawable.state1);
        }
    }

    //获取详情
    private void getInfoDetail() {
        pushEvent(new ShopPackageInfoRunner(shopId + "", productId + ""));
    }

    @Override
    protected void setupViewPager(BaseFragmentPagerAdapter adapter) {
//        //tab 图文详情
//        Bundle bundle = new Bundle();
//        //传递数据
//        final SerializableMap myMap = new SerializableMap();
//        myMap.setMap(map);//将map数据添加到封装的myMap中
//        bundle.putSerializable("map", myMap);
        adapter.addFragment(ShopPackageInfoFragment.class, "图文详情");

        //tab 服务内容
        Bundle bundleService = new Bundle();
        adapter.addFragment(ShopPackageServiceFragment.class, "服务内容");
    }


   /* @OnClick({R.id.fab, R.id.tvFavorite, R.id.tvGoBuy})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                onShowCommentView();
                break;
            case R.id.tvFavorite://收藏
                if (isFollowed) {//已关注，取消关注
                    pushEventEx(false, "", new ShopDisFollowRunner(shopId), ShopPackageInfoActivity.this);
                } else {//未关注，加关注
                    pushEventEx(false, "", new ShopFollowRunner(shopId), ShopPackageInfoActivity.this);
                }
                break;
            case R.id.tvGoBuy://预约到店
                AppointmentActivity.launch(ShopPackageInfoActivity.this, shopId);
                break;
        }
    }
*/
    /**
     * Toolbar（右标题）菜单选项
     *
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
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu://设置右侧按钮(分享)
            {
                new ThreadShareHelper(this)
                        .showShareDialog(Constants.buildShopUrl(0),
                                "分享来自简婚",
                                "我们结婚啦！诚挚的邀请您来见证我们的浪漫婚礼！");
            }
            break;
        }
        return true;
    }

    @Override
    public void onCommentBackBtnClick() {

    }

    @Override
    public void onShowCommentView(Object... args) {

    }

    @Override
    public void onCommentBtnClick(Object... args) {
//        hidenSoftInput(mCommentView.getEt());
//        if (args != null) {
////            publishComment(proId + "", (String) args[2]);
//        }
    }

    //评论
    private void publishComment(String topicId, String comment) {
//        pushEvent(new ProductCommentRunner(topicId, comment));
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {

            case XEventCode.HTTP_PRODUCT_COMMENT://评论
                if (event.isSuccess()) {
                    CustomToast.showRightToast(this, "评论成功");
                    onCommentBackBtnClick();
                }
                break;
            case XEventCode.HTTP_PRODUCT_FAVORITE://加关注
                if (event.isSuccess()) {
                    CustomToast.showRightToast(this, "操作成功");
                    getInfoDetail();
                } else {
                    CustomToast.showWorningToast(this, "操作失败");
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        StatService.onResume(ShopPackageInfoActivity.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(ShopPackageInfoActivity.this);
    }

}
