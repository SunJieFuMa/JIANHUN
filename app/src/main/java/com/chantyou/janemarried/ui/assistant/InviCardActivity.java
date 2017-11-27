package com.chantyou.janemarried.ui.assistant;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.assistant.InviCardInfoRunner;
import com.chantyou.janemarried.ui.assistant.fragment.InviCardFragment;
import com.chantyou.janemarried.ui.base.ThreadShareActivity;
import com.chantyou.janemarried.utils.Constants;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.ui.base.ScalePageTransformer;
import com.mhh.lib.utils.JsonUtil;

import java.util.Map;

/**
 * Created by j_turn on 2016/2/7 13:07
 * Email：766082577@qq.com
 */
public class InviCardActivity extends ThreadShareActivity {

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    Map<String, Object> mInfo;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_invicard);

        addAndroidEventListener(XEventCode.EVENT_RUNCODE, this);
        addAndroidEventListener(XEventCode.HTTP_CARD_SAVE, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        removeEventListener(XEventCode.EVENT_RUNCODE, this);
        removeEventListener(XEventCode.HTTP_CARD_SAVE, this);
    }

    @Override
    protected void init() {
        super.init();
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putInt("pageId", 1);
        bundle.putInt("layId", R.layout.adapter_invicard1);
        adapter.addFragment(InviCardFragment.class, null, bundle);

        bundle = new Bundle();
        bundle.putInt("pageId", 2);
        bundle.putInt("layId", R.layout.adapter_invicard2);
        adapter.addFragment(InviCardFragment.class, null, bundle);

        bundle = new Bundle();
        bundle.putInt("pageId", 3);
        bundle.putInt("layId", R.layout.adapter_invicard3);
        adapter.addFragment(InviCardFragment.class, null, bundle);

        bundle = new Bundle();
        bundle.putInt("pageId", 4);
        bundle.putInt("layId", R.layout.adapter_invicard4);
        adapter.addFragment(InviCardFragment.class, null, bundle);

        bundle = new Bundle();
        bundle.putInt("pageId", 5);
        bundle.putInt("layId", R.layout.adapter_invicard5);
        adapter.addFragment(InviCardFragment.class, null, bundle);

        viewPager.setPageTransformer(true, new ScalePageTransformer(1.0f, 0.75f));//用于ViewPager的滑动动画
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);//最少缓存3页
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        refresh();
    }



    private void refresh() {
        pushEvent(new InviCardInfoRunner());
//        pushEvent(new WebServiceRunner());
    }

    @OnClick({R.id.vitem1, R.id.vitem2, R.id.vitem3, R.id.vShare})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.vitem1://文字
                CardEditActivity.launch(this, CardEditActivity.class);
                break;
            case R.id.vitem2://图片
                if(mInfo != null) {
                    try {
                        CardEditImgActivity.launch(this, JsonUtil.getItemString((Map<String, Object>) mInfo.get("data"), "image"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    CardEditImgActivity.launch(this, "");
                }
                break;
            case R.id.vitem3://音乐
                if(mInfo != null) {
                    try {
                        CardEditMusicActivity.launch(this, JsonUtil.getItemString((Map<String, Object>) mInfo.get("data"), "music"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    CardEditMusicActivity.launch(this, "");
                }
                break;
            case R.id.vShare://分享
                if(mInfo != null) {
                    Map<String, Object> map = (Map<String, Object>) mInfo.get("data");
                    if(map.containsKey("id")) {
                        showShareDialog(Constants.buildCardUrl(AppAndroid.getUid(), viewPager.getCurrentItem() + 1, JsonUtil.getItemInt(map, "id")),
                                "我们结婚啦！【"+JsonUtil.getItemString(map, "newMan")+"&"+JsonUtil.getItemString(map, "newWoman")+"】的婚礼邀请函！",
                                "我们结婚啦！诚挚的邀请您来见证我们的浪漫婚礼！");
                        return;
                    }
                }
                showYesOrNoDialog("提示", "确定", "取消", "信息不完善", null);
                break;
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_CARD_INFO:
                if (event.isSuccess()) {
                    mInfo = (Map<String, Object>) event.getReturnParamsAtIndex(0);
                    if (mInfo != null) {
                        runEvent(XEventCode.CARD_GETINFO, mInfo);
                    }
                }
                break;
            case XEventCode.HTTP_CARD_SAVE:
                if (event.isSuccess()) {
                    refresh();
                }
                break;
            case XEventCode.EVENT_RUNCODE:
                if (event.isSuccess()) {
                    int code = (int) event.getParamsAtIndex(0);
                    if (code == XEventCode.CARD_GETINFO) {
                        if (mInfo != null) {
                            runEvent(XEventCode.CARD_GETINFO, mInfo);
                        }
                    }
                }
                break;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(InviCardActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(InviCardActivity.this);
    }

/*
    private class MyPagerAdapter extends LimPagerAdapter implements View.OnClickListener {

        protected int maxW, maxH;

        public MyPagerAdapter() {
            super();
            maxW = AppAndroid.getScreenWidth();
            maxH = AppAndroid.getScreenHeight();
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.view_item:
                    break;
            }
        }

        private int getLayId(int position) {
            if(position == 0) {
                return R.layout.adapter_invicard1;
            } else if(position == 1) {
                return R.layout.adapter_invicard2;
            } else if(position == 2) {
                return R.layout.adapter_invicard3;
            } else if(position == 3) {
                return R.layout.adapter_invicard4;
            } else if(position == 4) {
                return R.layout.adapter_invicard5;
            }
            return R.layout.adapter_invicard1;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        protected View getView(View container, int position) {
            View v = LayoutInflater.from(container.getContext()).inflate(getLayId(position), null);
            ImageView iv = (ImageView) v.findViewById(R.id.iv);
            ((TextView) v.findViewById(R.id.tvHotel)).setText("婚宴酒店名称");
            ((TextView) v.findViewById(R.id.tvDate)).setText(new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()).format(new Date()));
            iv.setAdjustViewBounds(true);
            iv.setMaxWidth(maxW);
            iv.setMaxHeight(maxH);
            v.setOnClickListener(this);
            return v;
        }

        @Override
        protected void setView(View view, int position) {
            if(mInfo != null) {
//            ((ImageView) view.findViewById(R.id.iv)).setImageResource(R.drawable.card21);
                HImageLoader.displayImage(JsonUtil.getItemString(mInfo, "image"), ((ImageView) view.findViewById(R.id.ivPic)), R.drawable.card_defpic);
                ((TextView) view.findViewById(R.id.tv1)).setText(JsonUtil.getItemString(mInfo, "newMan"));
                ((TextView) view.findViewById(R.id.tv2)).setText(JsonUtil.getItemString(mInfo, "newWoman"));
                ((TextView) view.findViewById(R.id.tvDate)).setText(JsonUtil.getItemString(mInfo, "marryDay"));
                ((TextView) view.findViewById(R.id.tvTime)).setText(JsonUtil.getItemString(mInfo, "marryTime"));
                ((TextView) view.findViewById(R.id.tvHotel)).setText(JsonUtil.getItemString(mInfo, "hotelAddress"));
            }
        }
    }*/
}
