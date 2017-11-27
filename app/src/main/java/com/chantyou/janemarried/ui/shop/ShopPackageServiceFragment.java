package com.chantyou.janemarried.ui.shop;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.model.Shop.ShopPackageInfoBean;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.google.gson.Gson;
import com.lib.mark.core.Event;
import com.lib.mark.core.EventManager;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.ui.base.BottomLoadFragment;
import com.mhh.lib.utils.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import space.sye.z.library.manager.RecyclerMode;

//服务内容详情
public class ShopPackageServiceFragment extends BottomLoadFragment implements EventManager.OnEventListener {

    private InfoItemAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((XBaseActivity) getActivity()).addAndroidEventListener(XEventCode.HTTP_SHOP_PACKAGE_INFO, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((XBaseActivity) getActivity()).removeEventListener(XEventCode.HTTP_SHOP_PACKAGE_INFO, this);
    }

    @Override
    protected void setupRecyclerView() {
        mAdapter = new InfoItemAdapter(getActivity());
        setupRecyclerView(new LinearLayoutManager(getActivity()), mAdapter, RecyclerMode.NONE);


    }


    @Override
    public void onEventRunEnd(Event event) {
        ((XBaseActivity) getActivity()).onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_SHOP_PACKAGE_INFO:
                if (event.isSuccess()) {
                    Gson gson = new Gson();
                    ShopPackageInfoBean shopPackageInfoBean = gson.fromJson(event.getReturnParamsAtIndex(0).toString(), ShopPackageInfoBean.class);
                    System.out.println("zhuwx:套餐详情：" + event.getReturnParamsAtIndex(0).toString());
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    List<Map<String, Object>> mapSeviceList = new ArrayList<Map<String, Object>>();

                    List<ShopPackageInfoBean.DataBean> shopPackageInfoBeanList = shopPackageInfoBean.getData();
                    for (int i = 0; i < shopPackageInfoBeanList.size(); i++) {
//                        System.out.println("zhuwx:" + shopPackageInfoBeanList.get(i).getInfoType());
//                        Map<String, Object> mp = new HashMap<String, Object>();
//                        mp.put("imageUrl", shopPackageInfoBeanList.get(i).getImageUrl());
//                        mp.put("descs", shopPackageInfoBeanList.get(i).getDescs());
//                        switch (shopPackageInfoBeanList.get(i).getInfoType()) {
//                            case 1: {//1图文详情
//                                mapList.add(mp);
//                            }
//                            break;
//                            case 2: {//2服务内容
//                                System.out.println("zhuwx:服务内容");
//                                mapSeviceList.add(mp);
//                            }
//                            break;
//                        }

                        if (null != shopPackageInfoBeanList.get(i) && null != shopPackageInfoBeanList.get(i).getImageUrl()) {
                            String[] imms = shopPackageInfoBeanList.get(i).getImageUrl().split(",");
                            if (imms.length > 1) {
                                for (int j = 0; j < imms.length; j++) {
                                    Map<String, Object> mp = new HashMap<String, Object>();
                                    mp.put("imageUrl", imms[j]);
                                    if (0 == j) {
                                        mp.put("descs", shopPackageInfoBeanList.get(i).getDescs());
                                    }
                                    switch (shopPackageInfoBeanList.get(i).getInfoType()) {
                                        case 1: {//1图文详情
                                            mapList.add(mp);
                                        }
                                        break;
                                        case 2: {//2服务内容
                                            mapSeviceList.add(mp);
                                        }
                                        break;
                                    }
                                }

                            } else {
                                Map<String, Object> mp = new HashMap<String, Object>();
                                mp.put("imageUrl", shopPackageInfoBeanList.get(i).getImageUrl().replace(",", ""));
                                mp.put("descs", shopPackageInfoBeanList.get(i).getDescs());
                                switch (shopPackageInfoBeanList.get(i).getInfoType()) {
                                    case 1: {//1图文详情
                                        mapList.add(mp);
                                    }
                                    break;
                                    case 2: {//2服务内容
                                        mapSeviceList.add(mp);
                                    }
                                    break;
                                }
                            }
                        } else {
                            Map<String, Object> mp = new HashMap<String, Object>();
                            mp.put("imageUrl", shopPackageInfoBeanList.get(i).getImageUrl().replace(",", ""));
                            mp.put("descs", shopPackageInfoBeanList.get(i).getDescs());
                            switch (shopPackageInfoBeanList.get(i).getInfoType()) {
                                case 1: {//1图文详情
                                    mapList.add(mp);
                                }
                                break;
                                case 2: {//2服务内容
                                    mapSeviceList.add(mp);
                                }
                                break;
                            }
                        }

                    }

                    if (mapSeviceList != null) {
                        mAdapter.setData(mapSeviceList);
                    } else {
                        mAdapter.setData(null);
                    }
                } else {
                    mAdapter.setData(null);
                }
                break;
        }
    }


    private class InfoItemAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

        Map<String, Object> map;

        public InfoItemAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemCount() {
            int count = super.getItemCount();
            return count + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 1;
            }
            return 2;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return new IndexViewHolder(inflateView(parent, R.layout.frame_shop_package));
            } else if (viewType == 2) {
                return new ViewHolder(inflateView(parent, R.layout.item_shop_package_info));
            }
            return null;
        }

        @Override
        public Map<String, Object> getValueAt(int position) {
            if (position > 0) {
                return super.getValueAt(position - 1);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof IndexViewHolder) {
                ((IndexViewHolder) holder).setData(map);
            } else if (holder instanceof ViewHolder) {
                Map<String, Object> map = getValueAt(position);
                ViewHolder vh = (ViewHolder) holder;
                if (map != null) {
                    String pic = JsonUtil.getItemString(map, "imageUrl");
                    vh.iv.setAdjustViewBounds(true);
                    vh.iv.setMaxWidth(AppAndroid.getScreenWidth());
                    vh.iv.setMaxHeight(AppAndroid.getScreenHeight());
                    HImageLoader.displayImage(pic, vh.iv, R.color.white);
                    vh.tvContent.setText(Html.fromHtml(JsonUtil.getItemString(map, "descs")));
                }
            }
        }

        private class IndexViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            ImageView iv;
            TextView tvContent;

            public IndexViewHolder(View view) {
                super(view);
                tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                iv = (ImageView) view.findViewById(R.id.iv);
                tvContent = (TextView) view.findViewById(R.id.tvContent);
            }

            void setData(Map<String, Object> map) {
                if (map != null) {
//                    id = JsonUtil.getItemInt(map, "id");
//                    title = JsonUtil.getItemString(map, "title");
//                    url = JsonUtil.getItemString(map, "exLink");
//                    String time = JsonUtil.getItemString(map, "author");
//                    if (!TextUtils.isEmpty(time)) {
//                        time += "  ";
//                    }
//                    time += JsonUtil.getItemString(map, "time");
//                    if (TextUtils.isEmpty(time)) {
//                        tvTime.setVisibility(View.GONE);
//                    } else {
//                        tvTime.setVisibility(View.VISIBLE);
//                        tvTime.setText(time);
//                    }
//                    if (TextUtils.isEmpty(url)) {
////                        url = "http://101.201.209.200/MarryTrade/news.html?id=" + id;
//                        url = Constants.buildNewsUrl(id);
//                    }
//                    tvTitle.setText(title);
//                    subtitle = JsonUtil.getItemString(map, "subtitle");
//
//                    String pic = JsonUtil.getItemString(map, "figurePic");
//                    iv.setAdjustViewBounds(true);
//                    iv.setMaxWidth(AppAndroid.getScreenWidth());
//                    iv.setMaxHeight(AppAndroid.getScreenHeight());
////                    if(threadShareHelper != null) {
////                        threadShareHelper.setImageUrl(pic);
////                    }
//                    HImageLoader.displayImage(pic, iv, R.color.white);
//                    tvContent.setText(Html.fromHtml(JsonUtil.getItemString(map, "description")));
//                    if (item != null) {
//                        if (TextUtils.isEmpty(url) || "about:blank".equalsIgnoreCase(url)) {
//                            item.setVisible(false);
//                        } else {
//                            item.setVisible(true);
//                        }
//                    }
                }
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            ImageView iv;
            TextView tvContent;

            public ViewHolder(View itemView) {
                super(itemView);
                iv = get(itemView, R.id.iv);
                tvContent = get(itemView, R.id.tvContent);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        StatService.onResume(ShopPackageServiceFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(ShopPackageServiceFragment.this);
    }

}
