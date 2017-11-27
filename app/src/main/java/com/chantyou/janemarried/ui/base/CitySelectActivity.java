package com.chantyou.janemarried.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.data.SharedPreferenceKey;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.local.CityAllRunner;
import com.chantyou.janemarried.httprunner.my.UserEditRunner;
import com.chantyou.janemarried.utils.SharedPreferenceUtils;
import com.chantyou.janemarried.utils.XLocationManager;
import com.chantyou.janemarried.widget.MyLetterListView;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.framework.CustomToast;

import java.util.List;

import space.sye.z.library.manager.RecyclerMode;

/**
 * 城市选择
 * 
 * @author raiyi-suzhou
 * 
 */
public class CitySelectActivity extends PullrefreshBottomloadActivity implements XLocationManager.OnGetLocationListener {

    public static final int RCODE = 25;

    private boolean isEdit;

    private CityAdapter adapter;

    /**
     * 右侧可滑动字母表
     */
    private MyLetterListView mLetterListView;

    /**
     * 弹出式分组上的文字
     */
    private TextView sectionToastText;

    public static void launchForResult(Activity act) {
        Intent intent = new Intent(act, CitySelectActivity.class);
        act.startActivityForResult(intent, RCODE);
    }

    public static void launchForResult(Activity act, boolean isEdit) {
        Intent intent = new Intent(act, CitySelectActivity.class);
        intent.putExtra("isEdit", isEdit);
        act.startActivityForResult(intent, RCODE);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_cityselect;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        isEdit = getIntent().getBooleanExtra("isEdit", false);

        sectionToastText = (TextView) findViewById(R.id.section_toast_text);
        mLetterListView = (MyLetterListView) findViewById(R.id.mLetterListView);

        XLocationManager.requestGetLocation(this);
        pushEvent(new CityAllRunner());
    }

    @Override
    protected void setupRecyclerView() {
        adapter = new CityAdapter(this);
        setupRecyclerView(new LinearLayoutManager(this), adapter, RecyclerMode.NONE);
    }

    /**
     * 设置字母表上的触摸事件，根据当前触摸的位置结合字母表的高度，计算出当前触摸在哪个字母上。
     * 当手指按在字母表上时，展示弹出式分组。手指离开字母表时，将弹出式分组隐藏。
     */
    private void setAlpabetListener() {
        mLetterListView
                .setOnTouchingLetterChangedListener(new MyLetterListView.OnTouchingLetterChangedListener() {

                    @Override
                    public void onTouchingLetterChanged(int selectionIndex,
                                                        String sectionLetter, int state) {

                        int position = adapter
                                .getPositionForSection(selectionIndex);

                        switch (state) {
                            case MyLetterListView.FINGER_ACTION_DOWN: // 手指按下
                                sectionToastText.setVisibility(View.VISIBLE);
                                sectionToastText.setText(sectionLetter);
//                                mRefreshRecyclerView.setSelection(position);
                                ((LinearLayoutManager) mRefreshRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
                                break;
                            case MyLetterListView.FINGER_ACTION_MOVE: // 手指滑动
                                sectionToastText.setText(sectionLetter);
//                                mLoadListView.setSelection(position);
                                ((LinearLayoutManager) mRefreshRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
                                break;
                            case MyLetterListView.FINGER_ACTION_UP:
                                sectionToastText.setVisibility(View.GONE);// 手指离开
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    @Override
    public void onGetLocationFinished(AMapLocation location, boolean bSuccess) {
        if(bSuccess) {
            adapter.cCity = location.getCity();//解析AMapLocation对象，获取城市名称
            adapter.notifyDataSetChanged();
            System.out.println("定位的城市："+adapter.cCity);//高德地图定位输出的是济南市
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.EVENT_CITY_ALL:
                if(event.isSuccess()) {
                    List<CityBean> list = (List<CityBean>) event.getReturnParamsAtIndex(0);
                    adapter.setData(list);
                    setAlpabetListener();
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("城市信息获取异常"));
                }
                break;
            case XEventCode.HTTP_USER_EDIT:
                if(event.isSuccess()) {
                    doResult((String) event.getParamsAtIndex(4));
                } else {
                    CustomToast.showWorningToast(this, "修改失败");
                }
                break;
        }
    }

    private class CityAdapter extends BaseRecyclerViewAdapter<CityBean> {

        String cCity;

        public CityAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemCount() {
            int num = super.getItemCount();
            if(num <= 0) {
                return 1;
            } else {
                int size = num;//size是不变的
                for (int i = 0; i < size; i++) {
                    List<City> cities = super.getValueAt(i).getCityList();
                    if(cities != null && cities.size() > 0) {
                        num += cities.size();//num是变化的
                    }
                }
                num += 1;//顶部还有一个item，正在定位。。。。
            }
            return num;
        }

        public String getValueAt2(int position) {
            if(position <= 0) {
                return TextUtils.isEmpty(cCity) ? "正在定位..." : cCity +" (GPS定位)";
            }
            String value = null;
            int num = super.getItemCount();
            if(num > 0) {
                int size = num;
                num = 0;
                for (int i = 0; i < size; i++) {
                    num += 1;
                    if(position == num) {
                        return super.getValueAt(i).getKey();
                    }
                    List<City> cities = super.getValueAt(i).getCityList();
                    if(cities != null && cities.size() > 0) {
                        num += cities.size();
                        if(num >= position) {
                            return cities.get(cities.size() - num + position - 1).getSName();
                        }
                    }
                }
            }
            return value;
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0) {
                return 1;
            }
            int num = super.getItemCount();
            if(num > 0) {
                int size = num;
                num = 0;
                for (int i = 0; i < size; i++) {
                    num += 1;
                    if(position == num) {
                        return 2;
                    }
                    List<City> cities = super.getValueAt(i).getCityList();
                    if(cities != null && cities.size() > 0) {
                        num += cities.size();
                        if(num >= position) {
                            return 0;
                        }
                    }
                }
            }
            return 0;
        }

        public int getPositionForSection(int selectionIndex) {
            if(selectionIndex == 0) {
                return 0;
            }
            int num = super.getItemCount();
            if(num > 0) {
                int size = num;
                num = 0;
                for (int i = 0; i < size; i++) {
                    num += 1;
                    if(selectionIndex <= i + 1) {
                        return num;
                    }
                    List<City> cities = super.getValueAt(i).getCityList();
                    if(cities != null && cities.size() > 0) {
                        num += cities.size();
//                        if(num > selectionIndex) {
//                            return num;
//                        }
                    }
                }
            }
            return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflateView(parent, viewType == 2 ? R.layout.adapter_city_item1 : R.layout.adapter_city_item2));
        }

        @Override
        protected boolean isDefBg() {
            return false;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            vh.pos = position;
            vh.tv.setText(getValueAt2(position));
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv;
            int pos;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = get(itemView, R.id.tv);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String city;
                        if(pos == 0) {
                            city = cCity;
                        } else {
                            city = tv.getText().toString();
                        }
                        if(city != null && city.length() > 1) {
                            if(isEdit) {
                                final String mc = city;
                                showYesOrNoDialog("提示", "确定", "取消", "确定更改所在城市为："+mc+"？", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == DialogInterface.BUTTON_POSITIVE) {
                                            showProgressDialog(null, "正在修改...");
                                            pushEventEx(false, null, new UserEditRunner("", "", "", "", mc, "", ""), CitySelectActivity.this);
                                        }
                                    }
                                });
                            } else {
                                doResult(city);
                            }
                        }
                    }
                });
            }
        }
    }

    private void doResult(String city) {
        String userCity =SharedPreferenceUtils.getString(CitySelectActivity.this,SharedPreferenceKey.userCity,"");
        if("".equalsIgnoreCase(userCity)){
            SharedPreferenceUtils.putString(CitySelectActivity.this, SharedPreferenceKey.userCity, city);
        }
        Intent intent = new Intent();
        intent.putExtra("returncity", city);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(CitySelectActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(CitySelectActivity.this);
    }
}
