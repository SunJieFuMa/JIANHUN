package com.chantyou.janemarried.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.sticky.HeaderAdAdapter;
import com.chantyou.janemarried.manager.ImageManager;
import com.chantyou.janemarried.model.Shop.ShopADListBean;
import com.chantyou.janemarried.utils.sticky.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class HeaderAdViewView {

    private ViewPager vpAd;
    private LinearLayout llIndexContainer;

    private static final int TYPE_CHANGE_AD = 0;
    private Thread mThread;
    private List<ImageView> ivList;
    private boolean isStopThread = false;
    private ImageManager mImageManager;

    private Context mContext;
    protected LayoutInflater mInflate;
    private HeaderAdAdapter photoAdapter;


    public HeaderAdViewView(Activity context) {
        this.mContext = context;
        mInflate = LayoutInflater.from(mContext);
        ivList = new ArrayList<>();
        mImageManager = new ImageManager(context);
    }

    public boolean fillView(ListView listView) {
        getView(listView);
        return true;
    }

    protected void getView(ListView listView) {

        View view = mInflate.inflate(R.layout.header_ad_layout, listView, false);

        vpAd = (ViewPager) view.findViewById(R.id.vp_ad);
        llIndexContainer = (LinearLayout) view.findViewById(R.id.ll_index_container);


        dealWithTheView();
        listView.addHeaderView(view);//填充到父容器
    }

    private void dealWithTheView() {
        ivList.clear();
        int size = 0;
        photoAdapter = new HeaderAdAdapter(mContext, ivList);
        vpAd.setAdapter(photoAdapter);

        addIndicatorImageViews(size);//添加指示图标
        setViewPagerChangeListener(size);//为ViewPager设置监听器
        startADRotate();//启动循环广告的线程
    }


    public void resetList(List<ShopADListBean.DataBean> dataBeanList) {
        stopADRotate();
        ivList.clear();
        int size = dataBeanList.size();
        for (int i = 0; i < dataBeanList.size(); i++) {
            ivList.add(createImageView(dataBeanList.get(i).getAdUrl()));
        }

        photoAdapter = new HeaderAdAdapter(mContext, ivList);
        vpAd.setAdapter(photoAdapter);

        addIndicatorImageViews(size);//添加指示图标
        setViewPagerChangeListener(size);//为ViewPager设置监听器
        isStopThread = false;
        startADRotate();//启动循环广告的线程

        photoAdapter.setDataBeanList(dataBeanList);
    }


    protected void getView(List<String> list, ListView listView) {

        View view = mInflate.inflate(R.layout.header_ad_layout, listView, false);

        vpAd = (ViewPager) view.findViewById(R.id.vp_ad);
        llIndexContainer = (LinearLayout) view.findViewById(R.id.ll_index_container);


        dealWithTheView(list);
        listView.addHeaderView(view);
    }

    private void dealWithTheView(List<String> list) {
        ivList.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ivList.add(createImageView(list.get(i)));
        }

        HeaderAdAdapter photoAdapter = new HeaderAdAdapter(mContext, ivList);
        vpAd.setAdapter(photoAdapter);

        addIndicatorImageViews(size);
        setViewPagerChangeListener(size);
        startADRotate();
    }

    // 创建要显示的ImageView
    private ImageView createImageView(String url) {
        ImageView imageView = new ImageView(mContext);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageManager.loadUrlImage(url, imageView);
        return imageView;
    }

    // 添加指示图标
    private void addIndicatorImageViews(int size) {
        llIndexContainer.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 5));
            if (i != 0) {
                lp.leftMargin = DensityUtil.dip2px(mContext, 7);
            }
            iv.setLayoutParams(lp);
            iv.setBackgroundResource(R.drawable.xml_round_orange_grey_sel);
            iv.setEnabled(false);
            if (i == 0) {
                iv.setEnabled(true);
            }
            llIndexContainer.addView(iv);
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TYPE_CHANGE_AD) {
                vpAd.setCurrentItem(vpAd.getCurrentItem() + 1);
            }
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener;

    // 为ViewPager设置监听器
    private void setViewPagerChangeListener(final int size) {
        if (null != pageChangeListener) {
            vpAd.removeOnPageChangeListener(pageChangeListener);
        }
        vpAd.addOnPageChangeListener(pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (ivList != null && ivList.size() > 0) {
                    int newPosition = position % size;
                    for (int i = 0; i < size; i++) {
                        llIndexContainer.getChildAt(i).setEnabled(false);
                        if (i == newPosition) {
                            llIndexContainer.getChildAt(i).setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    // 启动循环广告的线程
    private void startADRotate() {
        // 一个广告的时候不用转
        if (ivList == null || ivList.size() <= 1) {
            return;
        }
        if (mThread == null) {
            mThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // 当没离开该页面时一直转
                    while (!isStopThread) {
                        // 每隔5秒转一次
                        SystemClock.sleep(5000);
                        // 在主线程更新界面
                        mHandler.sendEmptyMessage(TYPE_CHANGE_AD);
                    }
                }
            });
            mThread.start();
        }
    }

    // 停止循环广告的线程，清空消息队列
    public void stopADRotate() {
        isStopThread = true;
        if (mHandler != null && mHandler.hasMessages(TYPE_CHANGE_AD)) {
            mHandler.removeMessages(TYPE_CHANGE_AD);
        }
        if (null != mThread) {
            mThread.stop();
            mThread = null;
        }
    }

}
