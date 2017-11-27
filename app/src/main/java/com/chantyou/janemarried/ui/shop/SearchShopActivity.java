package com.chantyou.janemarried.ui.shop;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.adapter.shop.ShopListAdapter2;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.data.SharedPreferenceKey;
import com.chantyou.janemarried.model.Shop.ShopListBean;
import com.chantyou.janemarried.utils.DrawableUtils;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.chantyou.janemarried.utils.SoftKeyboardTool;
import com.chantyou.janemarried.utils.SpUtils;
import com.chantyou.janemarried.utils.sticky.DensityUtil;
import com.chantyou.janemarried.view.YhFlowLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.chantyou.janemarried.config.UrlConfig.SHOP_LIST;

/**
 * Created by Administrator on 2017/1/23.
 */
public class SearchShopActivity extends MyBaseActivity implements View.OnClickListener {// implements View.OnClickListener, AdapterView.OnItemClickListener
    private View mSearchImg;
    private EditText mSearchEt;
    private View mDeleteImg;
    //历史搜索相关
    private LinearLayout search_history_ll;
    private TextView mClearHistorySearch;//清除历史
    private YhFlowLayout flowLayout;
    private List<SearchHistoryEntity> mHistoryListData;
    private List<String> mDatas = new ArrayList<>();
    private int position;
    private AlertDialog dialog;
    private TextView tv_noHistory;
    private ImageView search_back;//返回按钮
    //    private SearchShopBean searchShopBean;
    private ListView lv;
    private ShopListAdapter2 mAdapter;
    private List<ShopListBean.DataBean> shopList = new ArrayList<>(); // ListView数据
    private LinearLayout ll;
    private ProgressBar bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
        displayUI();
    }

    @Override
    public void onBackPressed() {
        if (lv.getVisibility()==View.VISIBLE) {
            lv.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            initData2();
        } else {
            super.onBackPressed();
        }
    }

    private void parseJson(String response, String content) {
        ShopListBean shopListBean = new Gson().fromJson(response, ShopListBean.class);
        shopList = shopListBean.getData();
        if (null == shopList || shopList.size() <= 0) {
            bar.setVisibility(View.GONE);
            Toast.makeText(this, "抱歉，没有您想要搜索的内容~", Toast.LENGTH_SHORT).show();
            return;
        }
        ll.setVisibility(View.GONE);
        lv.setVisibility(View.VISIBLE);
        mAdapter = new ShopListAdapter2(this, shopList);
        lv.setAdapter(mAdapter);
        bar.setVisibility(View.GONE);
        ////////////////////////////////////////////////////////////////////////////////
        //将搜索关键字保存到历史搜索
        //        content = mSearchEt.getText().toString();

        if (isHasSelectData(content)) {//查重
            mHistoryListData.remove(position);
        }
        //后来搜索的文字放在集合中的第一个位置
        mHistoryListData.add(0, new SearchHistoryEntity(content));

        if (mHistoryListData.size() == 10) {//实现本地历史搜索记录最多不超过十个
            mHistoryListData.remove(9);
        }
        mClearHistorySearch.setVisibility(View.VISIBLE);//因为点击了搜索，所以就将清除历史视图显示出来
        //将这个mHistoryListData保存到sp中，其实sp中保存的就是这个mHistoryListData集合
        saveHistory();

        tv_noHistory.setVisibility(View.GONE);
    }


    private void initView() {
        bar = (ProgressBar) findViewById(R.id.bar);
        ll = (LinearLayout) findViewById(R.id.ll);
        lv = (ListView) findViewById(R.id.lv);

        for (int i = 0; i < 20; i++) {
            mDatas.add("我是" + i);
        }
        mHistoryListData = new ArrayList<>();
        //        System.out.println("mHistoryListData::::::::::::11" + mHistoryListData);

        mHistoryListData = getHistory();
        //        System.out.println("mHistoryListData::::::::::::22" + mHistoryListData);

        mClearHistorySearch = (TextView) findViewById(R.id.search_clearHistory);//清除历史
        mClearHistorySearch.setOnClickListener(this);
        search_history_ll = (LinearLayout) findViewById(R.id.search_history_ll);//盛放历史记录的容器
        mSearchImg = findViewById(R.id.search_btn);//搜索的那个imageview
        mSearchImg.setOnClickListener(this);
        mSearchEt = (EditText) findViewById(R.id.search_et);
        mSearchEt.addTextChangedListener(editTextWatcher);
        mSearchEt.setOnClickListener(this);
        mSearchEt.setOnEditorActionListener(editorActionListener);
        mDeleteImg = findViewById(R.id.search_delete);
        mDeleteImg.setOnClickListener(this);
        tv_noHistory = (TextView) findViewById(R.id.tv_noHistory);
        tv_noHistory.setVisibility(View.GONE);
        search_back = (ImageView) findViewById(R.id.search_back);
        search_back.setOnClickListener(this);
        //设置流式布局
        flowLayout = (YhFlowLayout) findViewById(R.id.flowlayout);
        flowLayout.setSpace(DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 5));
        flowLayout.setPadding(DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 5),
                DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 5));
    }

    //监听EditText内容改变的监听器
    TextWatcher editTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(mSearchEt.getText())) {
                mDeleteImg.setVisibility(View.GONE);
            } else {
                mDeleteImg.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //可以在EditText输入的文字停止改变后做一些事情
        }
    };

    TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                clickSearch();
                SoftKeyboardTool.closeKeyboard(mSearchEt);//关闭软键盘
            }
            return false;
        }
    };

    /**
     * 点击了搜索按钮的处理
     */
    private void clickSearch() {
        if (TextUtils.isEmpty(mSearchEt.getText())) {
            Toast.makeText(this, "请输入关键字！", Toast.LENGTH_SHORT).show();
            return;
        }
        //执行跳转
        //        Intent intent = new Intent(this, SearchShopReturnActivity.class);
        //        intent.putExtra("data", content);//传假数据过去
        //        startActivity(intent);
        bar.setVisibility(View.VISIBLE);
        goSearchedShop();

    }

    private void goSearchedShop() {
        String url = SHOP_LIST;
        String city = SharedPreferenceUtils.getString(this, SharedPreferenceKey.userCity, "济南");
        city = city.replace("市", "");
        OkHttpUtils
                .post()
                .url(url)
                .addParams("token", AppAndroid.getAccessToken())
                .addParams("city", city)
                .addParams("search", mSearchEt.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        bar.setVisibility(View.GONE);
                        Toast.makeText(SearchShopActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //                        Toast.makeText(SearchShopActivity.this, "查询到的："+response, Toast.LENGTH_SHORT).show();
                        parseJson(response, mSearchEt.getText().toString());
                    }
                });
    }

    //这个是点击了搜索历史item后跳转的代码
    private void goSearchedShop2(final String content) {
        String url = SHOP_LIST;
        String city = SharedPreferenceUtils.getString(this, SharedPreferenceKey.userCity, "济南");
        city = city.replace("市", "");
        OkHttpUtils
                .post()
                .url(url)
                .addParams("token", AppAndroid.getAccessToken())
                .addParams("city", city)
                .addParams("search", content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        bar.setVisibility(View.GONE);
                        Toast.makeText(SearchShopActivity.this, "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response, content);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData2();
    }

    /**
     * 保存历史查询记录
     */
    private void saveHistory() {
        SpUtils.putString(this, "history",
                new Gson().toJson(mHistoryListData));//将java对象转换成json字符串进行保存
    }

    /**
     * 获取历史查询记录
     *
     * @return
     */
    private List<SearchHistoryEntity> getHistory() {
        String historyJson = SpUtils.getString(this, "history", "");
        if (historyJson != null && !historyJson.equals("")) {//必须要加上后面的判断，因为获取的字符串默认值就是空字符串
            //将json字符串转换成list集合
            return new Gson().fromJson(historyJson, new TypeToken<List<SearchHistoryEntity>>() {
            }.getType());
        }
        return new ArrayList<SearchHistoryEntity>();
    }

    //判断本地数据中有没有存在搜索过的数据，查重
    private boolean isHasSelectData(String content) {
        if (mHistoryListData == null) {
            return false;
        }
        for (int i = 0; i < mHistoryListData.size(); i++) {
            if (mHistoryListData.get(i).getContent().equals(content)) {
                position = i;
                return true;
            }
        }
        return false;
    }

    private void initData() {
        if (null == mHistoryListData || mHistoryListData.size() == 0) {
            return;
        }
        mClearHistorySearch.setVisibility(View.VISIBLE);
        for (int i = 0; i < mHistoryListData.size(); i++) {
            View view = View.inflate(this, R.layout.item_search_history, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_search_history);
            textView.setText(mHistoryListData.get(i).getContent());
            search_history_ll.addView(view);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bar.setVisibility(View.VISIBLE);
                    goSearchedShop2(mHistoryListData.get(finalI).getContent());
                }
            });
        }
    }

    private void initData2() {
        search_history_ll.removeAllViews();
        mHistoryListData = getHistory();
        if (null == mHistoryListData || mHistoryListData.size() == 0) {
            return;
        }
        for (int i = 0; i < mHistoryListData.size(); i++) {
            View view = View.inflate(this, R.layout.item_search_history, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_search_history);
            textView.setText(mHistoryListData.get(i).getContent());
            search_history_ll.addView(view);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bar.setVisibility(View.VISIBLE);
                    goSearchedShop2(mHistoryListData.get(finalI).getContent());
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (v == mDeleteImg) {
            mSearchEt.getEditableText().clear();//清除文字
            mDeleteImg.setVisibility(View.GONE);//隐藏删除图标
            mSearchEt.requestFocus();//输入框重新获取焦点
            SoftKeyboardTool.showSoftKeyboard(mSearchEt);//弹出软键盘
            lv.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
        } else if (v == mClearHistorySearch) {//清空历史搜索按钮
            dialog = new AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("确定要清除搜索历史吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            mHistoryListData.clear();//清空listview的adapter中的数据
                            saveHistory();//刷新sp中的文件
                            initData2();
                            mClearHistorySearch.setVisibility(View.GONE);//因为已经清除历史了，所以就隐藏清除历史控件
                            dialog.dismiss();
                            tv_noHistory.setVisibility(View.VISIBLE);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else if (v == search_back) {
            SoftKeyboardTool.closeKeyboard(mSearchEt);//关闭软键盘
            finish();//返回按钮
        } else if (v == mSearchImg) {
            clickSearch();
            SoftKeyboardTool.closeKeyboard(mSearchEt);//关闭软键盘
        } else if (v == mSearchEt) {
            //            lv.setAdapter(null);
            lv.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
        }
    }

    private void displayUI() {
        for (int i = 0; i < mDatas.size(); i++) {
            final String data = mDatas.get(i);
            TextView tv = new TextView(this);
            tv.setText(data);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setGravity(Gravity.CENTER);
            int paddingy = DensityUtil.dip2px(this, 7);
            int paddingx = DensityUtil.dip2px(this, 6);
            tv.setPadding(paddingx, paddingy, paddingx, paddingy);
            tv.setClickable(false);

            int shape = GradientDrawable.RECTANGLE;
            int radius = DensityUtil.dip2px(this, 4);
            int strokeWeight = DensityUtil.dip2px(this, 1);
            int stokeColor = getResources().getColor(R.color.gray);
            int stokeColor2 = getResources().getColor(R.color.gray2);

            GradientDrawable normalBg = DrawableUtils.getShape(shape, radius, strokeWeight, stokeColor, Color.WHITE);
            GradientDrawable pressedBg = DrawableUtils.getShape(shape, radius, strokeWeight, stokeColor2, getResources().getColor(R.color.gray2));
            StateListDrawable selector = DrawableUtils.getSelector(normalBg, pressedBg);
            tv.setBackgroundDrawable(selector);
            ColorStateList colorStateList = DrawableUtils.getColorSelector(getResources().getColor(R.color.gray), getResources().getColor(R.color.white));
            tv.setTextColor(colorStateList);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            flowLayout.addView(tv);
        }
    }
}
