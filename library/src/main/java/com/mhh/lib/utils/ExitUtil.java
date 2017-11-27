package com.mhh.lib.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;


public class ExitUtil {
    private final List<Activity> mList = new ArrayList<Activity>();
    private static ExitUtil instance;

    private ExitUtil() {//构造器私有化

    }

    public static ExitUtil getInstance() {
        if (null == instance) {
            instance = new ExitUtil();
        }
        return instance;
    }

    /**
     * �????activity已�?? destory�? 就移???
     *
     * @param activity
     */
    public void remove(Activity activity) {
        mList.remove(activity);
    }

    /**
     * 添�??ativity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void finishAll() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mList.clear();
        }
    }

//	public void launchMain() {
//    	try {
//    	    for (Activity activity : mList) {
//	    		if (activity != null && !(activity instanceof MainActivity)) {
//	    		    activity.finish();
//	    		}
//    	    }
//    	} catch (Exception e) {
//    	    e.printStackTrace();
//    	} finally {
//    		if(mList.size() > 0 && mList.get(0) instanceof MainActivity) {
//    			Activity act = mList.get(0);
//    			mList.clear();
//    			mList.add(act);
//    			((MainActivity) act).selectTab(0);
//    		}
//    	}
//	}

    /**
     * ?????? �????activity 并�????????
     */
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*
            android.os.Process.killProcess(android.os.Process.myPid())这个绕过了生命周期的顺序，属于强制关闭。
            在某个activity中调用了这个方法后，则该activity的onDestroy()方法就不会再去执行
             */
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            mList.clear();
        }
    }

}
