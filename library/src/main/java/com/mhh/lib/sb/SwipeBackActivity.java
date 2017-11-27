//
//package com.mhh.lib.sb;
//
//import android.os.Bundle;
//import android.view.View;
//
//import com.mhh.lib.BaseActivity;
//
//
//public class SwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {
//
//    protected boolean mSwipeBackFinish = true;
//    private SwipeBackActivityHelper mHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if(mSwipeBackFinish) {
//            mHelper = new SwipeBackActivityHelper(this);
//            mHelper.onActivityCreate();
//        }
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        if(mHelper != null) {
//            mHelper.onPostCreate();
//        }
//    }
//
//    @Override
//    public View findViewById(int id) {
//        View v = super.findViewById(id);
//        if (v == null && mHelper != null)
//            return mHelper.findViewById(id);
//        return v;
//    }
//
//    @Override
//    public SwipeBackLayout getSwipeBackLayout() {
//        if(mHelper != null) {
//            return mHelper.getSwipeBackLayout();
//        }
//        return null;
//    }
//
//    @Override
//    public void setSwipeBackEnable(boolean enable) {
//        try {
//            getSwipeBackLayout().setEnableGesture(enable);
//        } catch (Exception e) {
//        }
//    }
//
//    @Override
//    public void scrollToFinishActivity() {
//        try {
//            Utils.convertActivityToTranslucent(this);
//            getSwipeBackLayout().scrollToFinishActivity();
//        } catch (Exception e) {
//        }
//    }
//}
