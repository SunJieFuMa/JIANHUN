package com.mhh.lib;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.mhh.lib.R;
import com.mhh.lib.ToastManager;
import com.mhh.lib.annotations.ViewUtils;
import com.mhh.lib.sb.SwipeBackActivityBase;
import com.mhh.lib.sb.SwipeBackActivityHelper;
import com.mhh.lib.sb.SwipeBackLayout;
import com.mhh.lib.sb.Utils;
import com.mhh.lib.utils.ExitUtil;

public abstract class SwipBaseActivity extends com.lib.mark.ui.BaseActivity implements SwipeBackActivityBase {

    protected boolean mSwipeBackFinish = true;
    private SwipeBackActivityHelper mHelper;
    public ToastManager mToastManager;
    protected AlertDialog dialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        ExitUtil.getInstance().addActivity(this);
        if (mSwipeBackFinish) {
            mHelper = new SwipeBackActivityHelper(this);
            mHelper.onActivityCreate();
        }

//		mEventManager = AndroidEventManager.getInstance();
        mToastManager = ToastManager.getInstance(getApplicationContext());

    }

    public void showYesOrNoDialog(String title, String yesTxt, String noTxt, String message, DialogInterface.OnClickListener listener) {
        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setPositiveButton(yesTxt, listener)
                .setNegativeButton(noTxt, listener);
        if(!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if(!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog() {
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitUtil.getInstance().remove(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mHelper != null) {
            mHelper.onPostCreate();
        }
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        if (mHelper != null) {
            return mHelper.getSwipeBackLayout();
        }
        return null;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        try {
            getSwipeBackLayout().setEnableGesture(enable);
        } catch (Exception e) {
        }
    }

    @Override
    public void scrollToFinishActivity() {
        try {
            Utils.convertActivityToTranslucent(this);
            getSwipeBackLayout().scrollToFinishActivity();
        } catch (Exception e) {
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView();
    }

    /**
     * setContentView之后调用, 进行view的初始化等操作
     */
    private void afterSetContentView() {
        ViewUtils.inject(this);
        init();
        if (mToobarAttribute != null && mToobarAttribute.mHasToolbar) {
            mToobarAttribute.mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
            setupToolbar(mToobarAttribute.mToolbar);
        }
    }

    protected void onInitToolbarAttribute(BaseToolbarAttribute toolbarAttribute) {
        if (mToobarAttribute.mHasToolbar) {
            mToobarAttribute.setTitleAttr(true, Gravity.CENTER, /*getResources().getString(R.string.app_name)*/getTitle() + "");
            mToobarAttribute.setTitleTxtColor(getResources().getColor(R.color.c_title));
            mToobarAttribute.setNavigation(true, getResources().getDrawable(R.drawable.navbar_icon_back),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
        }
    }
}
