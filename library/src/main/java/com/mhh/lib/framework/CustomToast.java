package com.mhh.lib.framework;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhh.lib.R;
import com.mhh.lib.ToastManager;


public class CustomToast {

	private static Toast toast;
    private static Handler mHandler = new Handler();
    private static long mLastTime;
    private static String mLastTxt;
    private static Runnable r = new Runnable() {
		@Override
		public void run() {
		    if (toast != null) {
				try{
					toast.show();
					mLastTime = System.currentTimeMillis();
				}catch (Exception e) {
				    e.printStackTrace();
				}
		    }
		}
    };
    
	public static void showRightToast(Context context, String text) {
		ToastManager.getInstance(context).show(text);
//		show(context, R.drawable.toast_right, text);
	}

	public static void showWorningToast(Context context, String text) {
		ToastManager.getInstance(context).show(text);
//		show(context, R.drawable.toast_worning, text);
	}

	public static void showErrorToast(Context context, String text) {
		ToastManager.getInstance(context).show(text);
//		show(context, R.drawable.toast_error, text);
	}
	
	public static void show(Context context, int imageId, String text) {
		if(context == null) {
			return;
		}
		if(toast == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.lib_toast, null);

			toast = new Toast(context);
			toast.setView(view);
		}
		if(mLastTxt != null && mLastTxt.equals(text)) {
			if(System.currentTimeMillis() - mLastTime < 2000) {
				return;
			}
		}
		if(toast != null) {
			toast.cancel();
		}
		mLastTxt = text;
		View v = toast.getView();
		((TextView) v.findViewById(R.id.toast_tv)).setText(text);
		((ImageView) v.findViewById(R.id.toast)).setImageResource(imageId != 0 ? imageId : R.color.transparent);
		
		mHandler.post(r);
	}
}