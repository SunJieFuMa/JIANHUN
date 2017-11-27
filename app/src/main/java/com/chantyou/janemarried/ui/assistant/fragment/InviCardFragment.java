package com.chantyou.janemarried.ui.assistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.ui.base.WebPageActivity;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.mhh.lib.ui.base.BaseFragment;
import com.mhh.lib.utils.JsonUtil;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by j_turn on 2016/4/12.
 * Email 766082577@qq.com
 */
public class InviCardFragment extends BaseFragment implements EventManager.OnEventListener {

    int layId;
    int pageId;
    int cid;
    Map<String, Object> map;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((XBaseActivity) getActivity()).addAndroidEventListener(XEventCode.CARD_GETINFO, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((XBaseActivity) getActivity()).removeEventListener(XEventCode.CARD_GETINFO, this);
    }

    @Override
    protected int getLayoutId() {
        if(getArguments() != null) {
            layId = getArguments().getInt("layId", 0);
            pageId = getArguments().getInt("pageId", 0);
        }
        return layId;
    }

    @Override
    protected void onInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View root) {
        super.onInitView(inflater, container, savedInstanceState, root);

        setData(null);
        mRootView.findViewById(R.id.btnView).setOnClickListener(listener);//预览的点击事件
//        mRootView.setOnClickListener(this);
    }

//    http://event.daoxila.com/M-HunLiXiu/XiTie/?id=m2bfequ1sFE=&tid=3&isapp=1&platform=dxlapp-android&user_id=m2bfequ1sFE=#invitationPage
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnView:
//                    if(map != null && map.containsKey("id")) {
                    System.out.println("用户id:"+AppAndroid.getUid()+" pageId:"+pageId+" cid:"+cid);
                        WebPageActivity.launch(getActivity(), false, "请帖",
                                                    //用户id，viewPager第几个item，查询请柬接口返回的id
                                Constants.buildCardUrl(AppAndroid.getUid(), pageId, cid));
//                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        ((XBaseActivity) getActivity()).runEvent(XEventCode.EVENT_RUNCODE, XEventCode.CARD_GETINFO);
    }

    private int getIvId() {
        if(pageId == 1) {
            return R.drawable.card21;
        } else if(pageId == 2) {
            return R.drawable.card22;
        } else if(pageId == 3) {
            return R.drawable.card23;
        } else if(pageId == 4) {
            return R.drawable.card24;
        } else if(pageId == 5) {
            return R.drawable.card25;
        }
        return 0;
    }

    public void setData(Map<String, Object> map) {
        this.map = map;
        if(mRootView != null) {

            HImageLoader.setBitmapDrawable(getIvId(), ((ImageView) mRootView.findViewById(R.id.iv)), R.color.white);
            if (map != null && !map.isEmpty()) {
//            ((ImageView) view.findViewById(R.id.iv)).setImageResource(R.drawable.card21);
                HImageLoader.displayImage(JsonUtil.getItemString(map, "image"), ((ImageView) mRootView.findViewById(R.id.ivPic)), R.drawable.card_defpic);
                ((TextView) mRootView.findViewById(R.id.tv1)).setText(JsonUtil.getItemString(map, "newMan"));
                ((TextView) mRootView.findViewById(R.id.tv2)).setText(JsonUtil.getItemString(map, "newWoman"));
                ((TextView) mRootView.findViewById(R.id.tvDate)).setText(JsonUtil.getItemString(map, "marryDay"));
                ((TextView) mRootView.findViewById(R.id.tvTime)).setText(JsonUtil.getItemString(map, "marryTime"));
                ((TextView) mRootView.findViewById(R.id.tvHotel)).setText(JsonUtil.getItemString(map, "hotelName"));
            } else {
                HImageLoader.displayImage(ImageDownloader.Scheme.DRAWABLE.wrap("R.drawable.card_defpic"), ((ImageView) mRootView.findViewById(R.id.ivPic)), R.drawable.card_defpic);
                ImageView iv = (ImageView) mRootView.findViewById(R.id.iv);
                ((TextView) mRootView.findViewById(R.id.tv1)).setText("新郎");
                ((TextView) mRootView.findViewById(R.id.tv2)).setText("新娘");
                ((TextView) mRootView.findViewById(R.id.tvHotel)).setText("婚宴酒店名称");
                ((TextView) mRootView.findViewById(R.id.tvDate)).setText(new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()).format(new Date()));
                iv.setAdjustViewBounds(true);
                iv.setMaxWidth(AppAndroid.getScreenWidth());
                iv.setMaxHeight(AppAndroid.getScreenHeight());
            }
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.CARD_GETINFO:
                if (event.isSuccess()) {
                    Map<String, Object> map = (Map<String, Object>) event.getParamsAtIndex(0);
                    map = (Map<String, Object>) map.get("data");
                    cid = JsonUtil.getItemInt(map, "id");
                    setData(map);
                } else {
                    setData(null);
                }
                break;
        }
    }
}
