package com.chantyou.janemarried.ui.topic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.dialog.PopupWheelSelector;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.topic.TopicAddEditRunner;
import com.chantyou.janemarried.imagepicker.PhotoSelectorActivity;
import com.chantyou.janemarried.ui.base.MarryPhasesAllActivity;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.lib.mark.core.Event;
import com.mhh.lib.adapter.base.BaseRecyclerViewAdapter;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.helper.AbsChoosePhotoHelper2;
import com.mhh.lib.ui.helper.AbsDateTimerPickerHelper;
import com.mhh.lib.ui.helper.IDateTimePickerHelper;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.utils.SystemUtils;
import com.mhh.lib.view.SquareImageView;
import com.mhh.lib.widget.XDatePickerDialog;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by j_turn on 2016/2/5 14:37
 * Email：766082577@qq.com
 */
public class TopicAddActivity extends XBaseActivity{

    private static final String TAG = TopicAddActivity.class.getSimpleName();

    private final static int MAX_NUM = 9;

    @ViewInject(R.id.etTitle)
    private EditText etTitle;
    @ViewInject(R.id.etTip)
    private EditText etTip;
    @ViewInject(R.id.lv_photos)
    private RecyclerView lv_photos;

    @ViewInject(R.id.tvStage)
    private TextView tvStage;
    @ViewInject(R.id.rl_Stage)
    private RelativeLayout rl_Stage;

    @ViewInject(R.id.tvLabel)
    private TextView tvLabel;
    @ViewInject(R.id.rl_Label)
    private RelativeLayout rl_Label;

    @ViewInject(R.id.tvDate)
    private TextView tvDate;
    @ViewInject(R.id.rl_Date)
    private RelativeLayout rl_Date;

    private int phaseId;
    private String tags;

    PhotoAdapter adapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");


    /**
     * 图片 日期选择 类
     */
    private AbsChoosePhotoHelper2 photoHelper;
    private IDateTimePickerHelper dateTimePickerHelper;

    /**
     * 滚动选择类
     *
     * @param arg0
     */
    private PopupWheelSelector wheelSelector;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_topicadd);
        //        tvLabel.setText("预定酒店");

        registerEditTextInputManager(etTip);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEditTextInputManager(etTip);
    }

    @Override
    protected void init() {
        super.init();

        adapter = new PhotoAdapter(this);
        lv_photos.setLayoutManager(new GridLayoutManager(this, 5));
        lv_photos.setAdapter(adapter);

        dateTimePickerHelper = new AbsDateTimerPickerHelper();

//        photoHelper = new AbsChoosePhotoHelper2(this) {
//            @Override
//            public String getCameraSaveFilePath() {
////                return FilePaths.getCameraSaveFilePath(TopicAddActivity.this);
//                return super.getCameraSaveFilePath();
////                return getCameraSaveFilePath2(TopicAddActivity.this);
//            }
//
//            @Override
//            public void launchActivityForResult(Intent intent, int requestCode) {
//                startActivityForResult(intent, requestCode);
//            }
//
//            @Override
//            public void onPictureChoosed(String filePath, String displayName) {
//                super.onPictureChoosed(filePath, displayName);
//                String path = getPictureSaveFilePath2(TopicAddActivity.this);
//                SystemUtils.compressBitmapFile(path, filePath, 1000, 1000, true);
//
//                HImageLoader.init().removeItemFileCache(filePath);
//                HImageLoader.init().removeItemFileCache(path);
//                if (Constants.DEBUG) {
//                    Log.v(TAG, "onPictureChoosed : filePath = " + filePath);
//                }
//                adapter.addItem(path);
//            }
//        };
    }

    private void dismissPopup() {
        if (wheelSelector != null && wheelSelector.isShowing()) {
            wheelSelector.dismiss();
            wheelSelector = null;
        }
    }

 @OnClick({R.id.rl_Stage,R.id.tvStage, R.id.rl_Label,R.id.tvLabel, R.id.rl_Date,R.id.tvDate})
    public void onUiClick(View v) {
        switch (v.getId()) {
            case R.id.rl_Stage:
            case R.id.tvStage:
//                dismissPopup();
//                String[] mastates = getResources().getStringArray(R.array.marstates);
//                List<String> labels = new ArrayList<>();
//                for(String label : mastates) {
//                    labels.add(label);
//                }
//                wheelSelector = new PopupWheelSelector(this)
//                        .setLabels(labels)
//                        .setWheelSize(4)
//                        .setSelection(0)
//                        .setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(int position, String label) {
//                                tvStage.setText(label);
//                            }
//                    });
//                wheelSelector.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);

                MarryPhasesAllActivity.launchForResult(this);
                break;
            case R.id.rl_Label:
            case R.id.tvLabel:
                if (phaseId != -1) {
                    AddLabelActivity.launchForResult(this, phaseId);
                } else {
                    CustomToast.showWorningToast(this, "请先选择婚礼阶段");
                }
                break;
            case R.id.rl_Date:
            case R.id.tvDate:
                Calendar cal = Calendar.getInstance();
                dateTimePickerHelper.showDatePicker(this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, new XDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        tvDate.setText(dateFormat.format(new Date(cal.getTimeInMillis())));
                    }
                });
                break;
        }
    }


    //保存
    @SuppressLint("SimpleDateFormat")
    private void doAddTopic() {
        String title = etTitle.getText().toString();
        String content = etTip.getText().toString();
        String phasename = tvStage.getText().toString();
//        String tags = tvLabel.getText().toString();
        String datatime = tvDate.getText().toString();

        if (TextUtils.isEmpty(title)) {
            CustomToast.showWorningToast(this, "请输入话题标题");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            CustomToast.showWorningToast(this, "请输入话题内容");
            return;
        }
        if (TextUtils.isEmpty(phasename)) {
            CustomToast.showWorningToast(this, "请选择婚礼阶段");
            return;
        }
        if (TextUtils.isEmpty(tags)) {
            CustomToast.showWorningToast(this, "请选择婚礼标签");
            return;
        }
        if (TextUtils.isEmpty(datatime)) {
            CustomToast.showWorningToast(this, "请选择婚礼日期");
            return;
        }

        try {
            datatime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateFormat.parse(datatime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        showProgressDialog(null, "正在保存...");
        pushEventEx(false, null, new TopicAddEditRunner("", title, content, phasename, tags, datatime, adapter.getData()), this);
    }

    /**
     * Toolbar（右标题）菜单选项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.main_menu);
        item.setIcon(R.drawable.font_fabu);
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
            case R.id.main_menu:
                doAddTopic();
                break;
        }
        return true;
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_TOPIC_ADD_EDIT:
                if (event.isSuccess()) {
                    JSONObject object = (JSONObject) event.getReturnParamsAtIndex(0);
                    CustomToast.showRightToast(this, Constants.getErrorMsg(object, "发布成功"));
                    finish();
                } else {
                    CustomToast.showErrorToast(this, "提交异常");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        photoHelper.onActivityResult(requestCode, resultCode, data, RESULT_OK);
        if (resultCode == RESULT_OK) {
            if (requestCode == MarryPhasesAllActivity.REQCODE) {
                String phasesinfo = data.getStringExtra("phasesinfo");
                Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(phasesinfo);
                tvStage.setText(JsonUtil.getItemString(map, "name"));
                phaseId = JsonUtil.getItemInt(map, "phaseId");
            } else if (requestCode == AddLabelActivity.REQCODE) {
                String tagsinfo = data.getStringExtra("tagsinfo");
                List<Map<String, Object>> list = (List<Map<String, Object>>) JsonUtil.jsonToList(tagsinfo);
                int size = list == null ? 0 : list.size();
                tags = "";
                String name = "";
                for (int i = 0; i < size; i++) {
                    Map<String, Object> map = list.get(i);
                    tags += JsonUtil.getItemInt(map, "id");
                    name += JsonUtil.getItemString(map, "name");
                    if (i != size - 1) {
                        tags += "&&";
                        name += ",";
                    }
                }
                tvLabel.setText(name);
            }
        }
        if (0 == requestCode) {
            if (data != null) {
                List<String> paths = (List<String>) data.getExtras().getSerializable("photos");
                for (int i = 0; i < paths.size(); i++) {
                    adapter.addItem(paths.get(i));
                }

            }

        }
    }


    private class PhotoAdapter extends BaseRecyclerViewAdapter<String> {

        public PhotoAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemCount() {
            int count = super.getItemCount();
            if (count < MAX_NUM) {
                count = count + 1;
            } else {
                count = MAX_NUM;
            }
            if (count > 5) {
                lv_photos.getLayoutParams().height = AppAndroid.dipToPixel(140);
            }
            return count;
        }

        @Override
        public void addItem(String item) {
            if (item != null) {
                getData().add(item);
                notifyDataSetChanged();
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflateView(parent, R.layout.adapter_lib_squareimg));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder) holder;
            String file = getValueAt(position);
            if (Constants.DEBUG) {
                Log.v(TAG, "file = " + file);
            }
            vh.path = file;
//            HImageLoader.setBitmapFile("/storage/emulated/0/Android/data/com.chantyou.janemarried/cache/cameras/temp0.jpg", vh.iv, HImageLoader.createOptions(R.drawable.ic_add_pic, 0));
            if (TextUtils.isEmpty(file)) {
                HImageLoader.displayImage("", vh.iv, R.drawable.ic_add_pic);
            } else {
                HImageLoader.setBitmapFile(file, vh.iv, HImageLoader.createOptions(R.drawable.ic_add_pic, 0));
            }
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        SquareImageView iv;
        String path;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (SquareImageView) itemView.findViewById(R.id.iv);
            int width = SystemUtils.dipToPixel(itemView.getContext(), 56);
            iv.getLayoutParams().width = iv.getLayoutParams().height = width;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (TextUtils.isEmpty(path)) {
//                        photoHelper.launchChoosePhoto(v);
//                    } else {
//                    }


                    Intent intent = new Intent(TopicAddActivity.this, PhotoSelectorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("limit", 9 - (adapter.getItemCount() - 1));
                    startActivityForResult(intent, 0);
                }
            });
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(TopicAddActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(TopicAddActivity.this);
    }
}
