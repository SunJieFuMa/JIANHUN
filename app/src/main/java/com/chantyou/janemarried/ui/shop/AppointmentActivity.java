package com.chantyou.janemarried.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.chantyou.janemarried.CheckVersion.CallBackInterface;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.framework.XEventCode;
import com.chantyou.janemarried.httprunner.shop.AppointmentRunner;
import com.chantyou.janemarried.ui.base.ActivityEvent;
import com.chantyou.janemarried.ui.base.BaseTabsActivity;
import com.chantyou.janemarried.utils.MyDialog;
import com.lib.mark.core.Event;
import com.mhh.lib.ToastManager;
import com.mhh.lib.adapter.base.BaseFragmentPagerAdapter;
import com.mhh.lib.annotations.ViewInject;

import java.util.Map;


//预约到店
public class AppointmentActivity extends BaseTabsActivity implements ActivityEvent {

    @ViewInject(R.id.tvTitle)
    private TextView tvTitle;
    @ViewInject(R.id.tv_do)
    private TextView tv_do;
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;

    private int shopId;


    private Map<String, Object> info;


    public static void launch(Context cxt, int shopId) {
        Intent intent = new Intent(cxt, AppointmentActivity.class);
        intent.putExtra("shopId", shopId);
        cxt.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        shopId = getIntent().getIntExtra("shopId", 0);

        return R.layout.activity_shop_appointment;
    }

    @Override
    protected void setupViewPager(BaseFragmentPagerAdapter adapter) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        super.onInitToolbarAttribute(toolbarAttribute);
    }

    @Override
    protected void init() {
        super.init();


        tv_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_name.getText().toString()) || TextUtils.isEmpty(et_phone.getText().toString())) {
                    ToastManager.getInstance(AppointmentActivity.this).show("请先输入您的姓名和手机号");
                    return;
                }
                if (et_phone.getText().length() != 11) {
                    ToastManager.getInstance(AppointmentActivity.this).show("请输入正确的手机号");
                    return;
                }
                pushEvent(new AppointmentRunner(shopId + "", et_name.getText().toString(), et_phone.getText().toString()));
            }
        });
    }


    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        switch (event.getEventCode()) {
            case XEventCode.HTTP_APPOINTMENT://预约到店
                if (event.isSuccess()) {
//                    CustomToast.showRightToast(this, "预约成功");

                    et_phone.setEnabled(false);
                    et_name.setEnabled(false);
                    tv_do.setVisibility(View.GONE);

                    MyDialog popWindow = new MyDialog(AppointmentActivity.this);
                    popWindow.show("预约成功", "您已预约成功，请耐心等待，稍后我们将有专人与您联系", false, new CallBackInterface() {
                        @Override
                        public void doSome() {
//                            AppointmentActivity.this.finish();
                        }

                        @Override
                        public void onFail() {

                        }

                        @Override
                        public void onSuccess() {

                        }
                    });


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
        StatService.onResume(AppointmentActivity.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(AppointmentActivity.this);
    }

    @Override
    public void onShowCommentView(Object... args) {

    }

    @Override
    public void onCommentBtnClick(Object... args) {

    }

    @Override
    public void onCommentBackBtnClick() {

    }
}
