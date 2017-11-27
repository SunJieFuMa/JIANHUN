package com.chantyou.janemarried.ui.left;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.my.UserEditRunner;
import com.chantyou.janemarried.httprunner.user.UserSignoutRunner;
import com.chantyou.janemarried.ui.base.CitySelectActivity;
import com.chantyou.janemarried.ui.base.MarryPhasesAllActivity;
import com.chantyou.janemarried.ui.base.XBaseActivity;
import com.chantyou.janemarried.ui.guide.LoginActivity;
import com.chantyou.janemarried.ui.left.account.AddressActivity;
import com.chantyou.janemarried.ui.left.account.EditNicknameActivity;
import com.chantyou.janemarried.ui.left.account.EditPersionInfoActivity;
import com.chantyou.janemarried.utils.Constants;
import com.chantyou.janemarried.utils.HImageLoader;
import com.lib.mark.core.Event;
import com.mhh.lib.annotations.ViewInject;
import com.mhh.lib.annotations.event.OnClick;
import com.mhh.lib.framework.CustomToast;
import com.mhh.lib.ui.helper.AbsChoosePhotoHelper2;
import com.mhh.lib.ui.helper.AbsDateTimerPickerHelper;
import com.mhh.lib.ui.helper.IDateTimePickerHelper;
import com.mhh.lib.utils.ExitUtil;
import com.mhh.lib.utils.JsonUtil;
import com.mhh.lib.widget.XDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by j_turn on 2015/12/17 23:54
 * Email：766082577@qq.com
 */
public class PersonInfoActivity extends XBaseActivity {

    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;
    @ViewInject(R.id.ivHead)
    private ImageView ivHead;
    @ViewInject(R.id.tvName)
    private TextView tvName;
    @ViewInject(R.id.tvState)
    private TextView tvState;
    @ViewInject(R.id.tvMarDate)
    private TextView tvMarDate;
    @ViewInject(R.id.tvLCity)
    private TextView tvLCity;
    @ViewInject(R.id.tvAddress)
    private TextView tvAddress;
    @ViewInject(R.id.tvPersionInfo)
    private TextView tvPersionInfo;
    @ViewInject(R.id.vitem1)
    private FrameLayout vitem1;

    private String mUserPic;

    //    private PopupWheelSelector wheelSelector;
    /**
     * 日期选择 类
     */
    private IDateTimePickerHelper dateTimePickerHelper;
    private AbsChoosePhotoHelper2 choosePhotoHelper;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_personinfo);
    }

    @Override
    protected void init() {
        super.init();

        dateTimePickerHelper = new AbsDateTimerPickerHelper();
        choosePhotoHelper = new AbsChoosePhotoHelper2(this) {
            @Override
            public void launchActivityForResult(Intent intent, int requestCode) {
                startActivityForResult(intent, requestCode);
            }

            @Override
            public void onPictureChoosed(String filePath, String displayName) {
                HImageLoader.init().removeItemFileCache(filePath);
                mUserPic = filePath;
                HImageLoader.init().removeItemFileCache(mUserPic);
                HImageLoader.setBitmapFile(mUserPic, ivHead, HImageLoader.createOptions(R.drawable.defaulthead, 0));
            }
        };

        HImageLoader.displayImage(Constants.getUserInfoByKey(Constants.SU_PHOTO), ivHead, R.drawable.defaulthead);
        tvName.setText(Constants.getUserInfoByKey(Constants.SU_NAME));
        tvState.setText(Constants.getUserInfoByKey(Constants.SU_STATE));
        tvMarDate.setText(Constants.getUserInfoByKey(Constants.SU_MARRYDATE));
        tvLCity.setText(Constants.getUserInfoByKey(Constants.SU_CITY));
        tvAddress.setText(Constants.getUserInfoByKey(Constants.SU_ADDRESS));
        tvPersionInfo.setText(Constants.getUserInfoByKey(Constants.SU_INTRODUCE));
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
        toolbarAttribute.setTitleAttr(true, Gravity.CENTER, getString(R.string.act_person));
    }

    //    private void dismissPopup() {
    //        if(wheelSelector != null && wheelSelector.isShowing()) {
    //            wheelSelector.dismiss();
    //            wheelSelector = null;
    //        }
    //    }

    @OnClick({R.id.vitem1, R.id.vitem2, R.id.vitem3, R.id.vitem4, R.id.vitem5, R.id.vitem6, R.id.vitem7, R.id.tvSignout})
    public void onViClick(View v) {
        switch (v.getId()) {
            case R.id.vitem1:
                doRequestPermissions();
                //                choosePhotoHelper.launchChoosePhotoWithCrop(v, 1, 1, 600, 600);
                break;
            case R.id.vitem2:
                EditNicknameActivity.launchForResult(this, tvName.getText().toString());
                break;
            case R.id.vitem3:
                MarryPhasesAllActivity.launchForResult(this);
                //                dismissPopup();
                //                wheelSelector = new PopupWheelSelector(this);
                //                List<String> labels = Arrays.asList((String[]) Constants.MRP_TYPE.getNames());
                //                wheelSelector.setLabels(labels);
                //                wheelSelector.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
                //                    @Override
                //                    public void onItemSelected(int position, String label) {
                //                        tvState.setTag(position);
                //                        tvState.setText(label);
                //                    }
                //                });
                //                wheelSelector.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
                break;
            case R.id.vitem4:
                final Calendar cal = Calendar.getInstance();
                dateTimePickerHelper.showDatePicker(this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, new XDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        tvMarDate.setText(format.format(new Date(cal.getTimeInMillis())));
                    }
                });
                break;
            case R.id.vitem5:
                CitySelectActivity.launchForResult(this);
                break;
            case R.id.vitem6:
                AddressActivity.launchForResult(this, tvAddress.getText().toString());
                break;
            case R.id.vitem7:
                EditPersionInfoActivity.launchForResult(this, tvPersionInfo.getText().toString());
                break;
            case R.id.tvSignout:
                showProgressDialog(null, "退出登录...");
                pushEventEx(false, null, new UserSignoutRunner(), PersonInfoActivity.this);
                break;
            default:
                break;
        }
    }

    //进行动态权限获取
    private void doRequestPermissions() {
        //判断当前系统是否高于或等于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //这里PERMISSION_GRANTED表示具有权限，PERMISSION_DENIED表示无权限
                //具有拍照权限和读取存储卡权限，直接进入相册
                //具体调用代码
                choosePhotoHelper.launchChoosePhotoWithCrop(vitem1, 1, 1, 600, 600);
            } else {
                /*
                不具有拍照权限，需要进行权限申请
                在判断应用没有相关权限的后，我们通过requestPermissions进行权限申请，
                这里的 String[] permissions是个字符串数组，可以对多个权限进行申请
                REQUEST_PERMISSION_CAMERA_CODE是个标识码，类似Intent跳转的REQUEST_CODE的，
                然后我们就可以在onRequestPermissionsResult进行权限申请的回调处理
                 */
                ActivityCompat.requestPermissions(PersonInfoActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_CAMERA_CODE);
            }
        } else {
            //当前系统小于6.0，直接调用拍照
            choosePhotoHelper.launchChoosePhotoWithCrop(vitem1, 1, 1, 600, 600);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //拍照
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE) {
            if (grantResults.length >= 1) {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    //具有拍照权限，调用相机
                    choosePhotoHelper.launchChoosePhotoWithCrop(vitem1, 1, 1, 600, 600);
                } else {
                    //不具有相关权限，给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限开启
                    //                    tishi("请去系统设置-应用管理里面把相机权限开启");
                    tishi("可以到系统设置-应用管理里打开被禁止的权限");
                }
            }

        }
    }

    private void tishi(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_USER_SIGNOUT:
                if (event.isSuccess()) {
                    CustomToast.showRightToast(this, "已退出");
                    ExitUtil.getInstance().finishAll();
                    launch(this, LoginActivity.class);
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("退出失败"));
                }
                break;
            case XEventCode.HTTP_USER_EDIT:
                if (event.isSuccess()) {
                    CustomToast.showRightToast(this, event.getMessage("已修改"));
                    finish();
                } else {
                    CustomToast.showErrorToast(this, event.getMessage("保存失败"));
                }
                break;
        }
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
        item.setIcon(R.drawable.font_save);
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
                doEditUser();
                break;
        }
        return true;
    }

    private void doEditUser() {
        String file = mUserPic;
        String nickname = tvName.getText().toString();
        String state = tvState.getText().toString();
        //        if(!TextUtils.isEmpty(state) ) {
        //            state = Constants.MRP_TYPE.getItemByName(tvMarDate.getText().toString()).toName();
        //        }
        //        if(TextUtils.isEmpty(state)) {
        //            CustomToast.showWorningToast(this, "选择当前的婚礼状态");
        //            return;
        //        }
        String marryDay = tvMarDate.getText().toString();
        //        if(!TextUtils.isEmpty(marryDay)) {
        //            try {
        //                marryDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(format.parse(marryDay));
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //                marryDay = "";
        //            }
        //        }
        //        if(TextUtils.isEmpty(marryDay)) {
        //            CustomToast.showWorningToast(this, "选择结婚日期");
        //            return;
        //        }
        String city = tvLCity.getText().toString();
        String address = tvAddress.getText().toString();
        String introduce = tvPersionInfo.getText().toString();

        showProgressDialog(null, "正在修改...");
        pushEventEx(false, null, new UserEditRunner(file, nickname, state, marryDay, city, address, introduce), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data, RESULT_OK);
        if (resultCode == RESULT_OK) {
            if (requestCode == EditNicknameActivity.RCODE) {
                String returntxt = data.getStringExtra("returntxt");
                tvName.setText(returntxt);
            } else if (requestCode == CitySelectActivity.RCODE) {
                tvLCity.setText(data.getStringExtra("returncity"));
            } else if (requestCode == EditPersionInfoActivity.RCODE) {
                String returntxt = data.getStringExtra("returntxt");
                tvPersionInfo.setText(returntxt);
            } else if (requestCode == AddressActivity.RCODE) {
                String returntxt = data.getStringExtra("returntxt");
                tvAddress.setText(returntxt);
            } else if (requestCode == MarryPhasesAllActivity.REQCODE) {
                String phasesinfo = data.getStringExtra("phasesinfo");
                Map<String, Object> map = (Map<String, Object>) JsonUtil.jsonToMap(phasesinfo);
                tvState.setText(JsonUtil.getItemString(map, "name"));
                //                phaseId = JsonUtil.getItemInt(map, "phaseId");
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PersonInfoActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PersonInfoActivity.this);
    }
}
