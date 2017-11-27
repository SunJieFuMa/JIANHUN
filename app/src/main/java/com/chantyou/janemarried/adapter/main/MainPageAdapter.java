package com.chantyou.janemarried.adapter.main;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.base.AbsViewHolder;
import com.chantyou.janemarried.adapter.base.SquareGrideImgAdapter;
import com.chantyou.janemarried.ui.base.LookPhotoActivity;
import com.chantyou.janemarried.ui.excellent.MarriageGoodsInfoActivity;
import com.chantyou.janemarried.ui.main.InformationActivity;
import com.chantyou.janemarried.ui.topic.TopicDetailsActivity;
import com.chantyou.janemarried.utils.HImageLoader;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.widget.GridViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2015/12/10.
 * Email 766082577@qq.com
 */
public class MainPageAdapter extends BaseRecyclerViewAdapter<Map<String, Object>> {

    public MainPageAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 3) {
            View view = inflateView(parent, R.layout.adapter_mainpage_item1);//首页攻略那一块
            return new ViewHolder(view);
        } else if (viewType == 1) {
            View view = inflateView(parent, R.layout.adapter_mainpage_item2);//阶段推荐里面那个带3张图片的item
            return new ViewHolder2(view);
        } else if (viewType == 2) {
            View view = inflateView(parent, R.layout.adapter_mainpage_item3);//阶段推荐里面那个带价格的item
            return new ViewHolder3(view);
        } else if (viewType == 4) {
            View view = inflateView(parent, R.layout.adapter_mainpage_item4);//阶段推荐里面最简单的item
            return new ViewHolder4(view);
        } else {
            return new AbsViewHolder(new View(parent.getContext()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Map<String, Object> map = getValueAt(position);
        int type = JsonUtil.getItemInt(map, "type");
        return type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Map<String, Object> map = getValueAt(position);
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.map = map;
//            vh.setData();

        } else if (holder instanceof ViewHolder2) {
            ViewHolder2 vh = (ViewHolder2) holder;
            vh.map = map;
//            vh.setData();
        } else if (holder instanceof ViewHolder3) {
            ViewHolder3 vh = (ViewHolder3) holder;
            vh.map = map;
//            vh.setData();
        } else if (holder instanceof ViewHolder4) {
            ViewHolder4 vh = (ViewHolder4) holder;
            vh.map = map;
//            vh.setData();
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder != null && holder instanceof AbsViewHolder) {
            ((AbsViewHolder) holder).setData();
        }
    }

    @Override
    protected boolean isDefBg() {
        return false;
    }

    //头部
    private class ViewHolder extends AbsViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;
        //        public final TextView mTextView2;
        LinearLayout vTexts;
        TextView tvState, tvOpen;
        ImageView ivTip;

        FrameLayout fl_open;

        Map<String, Object> map;
        private final Runnable run;
        String things;

        public ViewHolder(final View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            ivTip = (ImageView) view.findViewById(R.id.ivTip);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
            vTexts = get(view, R.id.vTexts);
//            mTextView2 = (TextView) view.findViewById(android.R.id.text2);
//            mTextView2.setMaxLines(20);
            tvState = (TextView) view.findViewById(R.id.tvState);
            tvOpen = (TextView) view.findViewById(R.id.tvOpen);
            fl_open= (FrameLayout) view.findViewById(R.id.fl_open);

            tvOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int key = JsonUtil.getItemInt(map, "isall");
                    key = key - 1;
                    if (key < 0) {
                        key = 1;
                    }
                    map.put("isall", key);
//                    mTextView2.post(run);
                    vTexts.post(run);
                }
            });


            fl_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int key = JsonUtil.getItemInt(map, "isall");
                    key = key - 1;
                    if (key < 0) {
                        key = 1;
                    }
                    map.put("isall", key);
//                    mTextView2.post(run);
                    vTexts.post(run);
                }
            });


            run = new Runnable() {
                @Override
                public void run() {
                    if (vTexts != null && !TextUtils.isEmpty(things)) {
                        String[] ths = things.split("\n");
                        int totalCount = ths.length;
                        int count = totalCount;
                        if (totalCount > 3) {
                            map.put("txtline", totalCount);
                            count = JsonUtil.getItemInt(map, "txtline");
                        }
                        int showNum = 0;
                        if (count > 3) {
                            tvOpen.setVisibility(View.VISIBLE);
                            if (JsonUtil.getItemInt(map, "isall") == 0) {
//								tvContent.setText(intro.substring(0, 100)+"...");
//                                mTextView2.setMaxLines(3);
                                showNum = 3;
//                                tvOpen.setText("查看全部");
                                tvOpen.setSelected(false);
                            } else {
//                                mTextView2.setText(things);
//                                mTextView2.setMaxLines(20);
                                showNum = Math.min(20, count);
//                                tvOpen.setText("收缩");
                                tvOpen.setSelected(true);
                            }
                        } else {
                            showNum = count;
//                            mTextView2.setText(things);
                            tvOpen.setVisibility(View.GONE);
//                            tvOpen.setText("");
                        }
                        if (showNum > totalCount) {
                            showNum = totalCount;
                        }
                        vTexts.removeAllViews();
                        int pd = AppAndroid.dipToPixel(6);
                        int height = AppAndroid.dipToPixel(36);
                        int margin = AppAndroid.dipToPixel(40);
                        LinearLayout.LayoutParams params;
                        for (int i = 0; i < showNum; i++) {
                            TextView tv = new TextView(vTexts.getContext());
                            tv.setTextColor(vTexts.getResources().getColor(R.color.light_black));
                            tv.setText(ths[i]);
                            tv.setMinHeight(height);
                            tv.setGravity(Gravity.CENTER_VERTICAL);
                            tv.setCompoundDrawablePadding(pd / 2);
                            tv.setSingleLine(true);
                            /*
                            代码中动态设置textview的drawable的位置----显示在左边
                            可以在上、下、左、右设置图标，如果不想在某个地方显示，则设置为null。
                            图标的宽高将会设置为固有宽高，既自动通过getIntrinsicWidth和getIntrinsicHeight获取。
                             */
                            tv.setCompoundDrawablesWithIntrinsicBounds(vTexts.getResources().getDrawable(items[i % 3]), null, null, null);
                            tv.setPadding(0, pd, 0, pd);
                            if (i < 2) {
                                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(0, 0, margin, 0);
                                tv.setLayoutParams(params);
                            }
                            vTexts.addView(tv);

                            if (i < showNum - 1) {
                                View v = new View(vTexts.getContext());
//                                v.setBackgroundResource(R.drawable.ic_line_bg);//虚线
                                v.setBackgroundResource(R.drawable.shape_line_dotted);
                                if (i < 2) {
                                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0, 0, margin, 0);
                                    v.setLayoutParams(params);
                                }
                                vTexts.addView(v);
                            }
                        }
                    }
                }
            };
        }

        int items[] = new int[]{R.drawable.round_item2, R.drawable.round_item3, R.drawable.round_item4};

        @Override
        public void setData() {
            mImageView.setImageResource(R.drawable.icon_state1);
            if (map != null) {
                mTextView.setVisibility(View.GONE);
//                tvState.setText(JsonUtil.getItemString(map, "state"));
                ArrayList<Object> array = (ArrayList<Object>) map.get("attentionThings");
                StringBuilder things = new StringBuilder();
                int len = array == null ? 0 : array.size();
                for (int i = 0; i < len; i++) {
                    try {
                        if (i > 0) {
                            things.append("\n");
                        }
                        things.append(array.get(i).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.things = things.toString();
                vTexts.post(run);
//                mTextView2.setText(things);
//                mTextView2.post(run);
            }
        }
    }

    //相册
    private class ViewHolder2 extends AbsViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;
        TextView tv2;
        TextView tvTime;
        GridViewEx gve;
        SquareGrideImgAdapter gadapter;
        Map<String, Object> map;

        public ViewHolder2(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
            tv2 = (TextView) view.findViewById(R.id.tv2);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            gve = (GridViewEx) view.findViewById(R.id.gvEx);
            gadapter = new SquareGrideImgAdapter();
            gve.setNumColumns(3);
            gve.setAdapter(gadapter);
            gve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        LookPhotoActivity.launch(view.getContext(), (String) parent.getAdapter().getItem(position), (ArrayList<String>) gadapter.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            get(view, R.id.view_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (map != null) {
                        TopicDetailsActivity.launch(v.getContext(), JsonUtil.getItemInt(map, "id"));
                    }
                }
            });
        }

        @Override
        public void setData() {
            mImageView.setImageResource(R.drawable.icon_state2);
            if (map != null) {
                String title = JsonUtil.getItemString(map, "title");
                if (TextUtils.isEmpty(title)) {
                    title = JsonUtil.getItemString(map, "content");
                }
                mTextView.setText(title);

                List<Map<String, Object>> images = (List<Map<String, Object>>) map.get("images");
                List<String> imgs = new ArrayList<>();
                if (images != null) {
                    for (Map<String, Object> it : images) {
                        imgs.add(JsonUtil.getItemString(it, "source"));
                        if (imgs.size() >= 3) {
                            break;
                        }
                    }
                }
                gve.setAdapter(gadapter);
                gadapter.setData(imgs);
                tv2.setText(JsonUtil.getItemString(map, "phase"));
                tvTime.setText(JsonUtil.getItemString(map, "datetime").split(" ")[0]);
            }
        }
    }


    // 文章
    private class ViewHolder4 extends AbsViewHolder {

        public final View mView;
        public final ImageView mImageView;
        ImageView ivPhoto;
        TextView mTextView;
        TextView tv2;
        Map<String, Object> map;

        public ViewHolder4(View view) {
            super(view);

            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
            tv2 = (TextView) view.findViewById(R.id.tv2);

            get(view, R.id.view_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (map != null && map.containsKey("exLink")) {
                        String img = JsonUtil.getItemString(map, "figurePic");
                        InformationActivity.launch(v.getContext(), JsonUtil.getItemInt(map, "id"), JsonUtil.getItemString(map, "title"),img);
                    }
                }
            });
        }

        @Override
        public void setData() {
            mImageView.setImageResource(R.drawable.icon_state1);
            if (map != null) {
                String img = JsonUtil.getItemString(map, "figurePic");
                HImageLoader.displayImage(img, ivPhoto, R.color.white_gray);
                mTextView.setText(JsonUtil.getItemString(map, "title"));
                String subtitle = JsonUtil.getItemString(map, "subtitle");
                if (TextUtils.isEmpty(subtitle)) {
                    subtitle = JsonUtil.getItemString(map, "description");
                }
                tv2.setText(subtitle);
            }
        }
    }

    //优婚品
    private class ViewHolder3 extends AbsViewHolder {

        public final View mView;
        public final ImageView mImageView;
        ImageView ivPhoto;
        TextView mTextView;
        TextView tv1;
        TextView tv2;
        TextView tvMoney;
        TextView tvMoney2;
        TextView tvTime;
        Map<String, Object> map;

        public ViewHolder3(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
            tv1 = (TextView) view.findViewById(R.id.tv1);
            tv2 = (TextView) view.findViewById(R.id.tv2);
            tvMoney = (TextView) view.findViewById(R.id.tvMoney);
            tvMoney2 = (TextView) view.findViewById(R.id.tvMoney2);
            tvMoney2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            tvTime = (TextView) view.findViewById(R.id.tvTime);

            get(view, R.id.view_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (map != null) {
                        MarriageGoodsInfoActivity.launch(v.getContext(), JsonUtil.getItemInt(map, "id"));
                    }
                }
            });
        }

        @Override
        public void setData() {
            mImageView.setImageResource(R.drawable.icon_state3);
            List<Map<String, Object>> zhanshi = (List<Map<String, Object>>) map.get("zhanshi");
            String img = "";
            if (zhanshi != null && zhanshi.size() > 0) {
                img = JsonUtil.getItemString(zhanshi.get(0), "source");
            }
            HImageLoader.displayImage(img, ivPhoto, R.color.white_gray);
            double price = JsonUtil.parseDouble(map.get("price"));
            double discountPrice = JsonUtil.parseDouble(map.get("discountPrice"));
            if (discountPrice <= 0) {
                discountPrice = price;
            }
            if (price - discountPrice > 0) {
                tv1.setVisibility(View.VISIBLE);
                tv1.setText(String.format("省%1$s", (price - discountPrice) + ""));
            } else {
                tv1.setVisibility(View.GONE);
            }
            tvMoney.setText(String.format(tvMoney.getResources().getString(R.string.fmt_money), discountPrice + ""));
            tvMoney2.setText(String.format(tvMoney2.getResources().getString(R.string.fmt_money), (price) + ""));
            mTextView.setText(JsonUtil.getItemString(map, "name"));
            tv2.setText(JsonUtil.getItemString(map, "brand"));
            tvTime.setText(JsonUtil.getItemString(map, "datetime").split(" ")[0]);
        }
    }
}
