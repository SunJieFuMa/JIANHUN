package com.chantyou.janemarried.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.chantyou.janemarried.utils.FortuneHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CoreService extends Service {
	
	private final String TAG = "CoreService";
	
	private boolean bIsload = false, bIsThreadStart = false;
	private final int COUNT = 1736;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initCityDb();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	private void initCityDb() {
		if(!bIsload) {//如果没有加载过
			int count = FortuneHelper.getInstance().queryCount();
			if(count >= COUNT) {
				bIsload = true;//说明已经加载过了，就不用再从assets里面获取了
				return;
			}
			final long stime = System.currentTimeMillis();
			Log.v(TAG, "starttime=" +stime);
			Log.d(TAG, "count=" +FortuneHelper.getInstance().delAll());
			Log.v(TAG, "deltime=" +(System.currentTimeMillis() - stime));
			if(!bIsThreadStart) {//如果没有线程运行的话就新开线程
				//读写操作数据库，耗时，开线程
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						bIsThreadStart = true;//线程正在运行
						insert1();
						int count = FortuneHelper.getInstance().queryCount();
						if(count >= COUNT) {
							bIsload = true;
						}
						Log.v(TAG, "inserttime=" +(System.currentTimeMillis() - stime)+",success="+bIsload);
						bIsThreadStart = false;
					}
				}).start();
			}
		}
	}
	
	private void insert1() {
		final String fn = "fort"+File.separator +"fort";//assets文件夹下的 fort文件夹里的fort文件
		for (int i = 1; i < 5; i++) {
			String str = getAssetString(fn+i+".sql", getBaseContext());
			try {
				FortuneHelper.getInstance().insertSql(str);
			} catch (Exception e) {
				Log.e(TAG, "file="+i +".txt  ,e=" +e.getMessage());
//				e.printStackTrace();
			}
		}
	}
	
	public static String getAssetString(String asset, Context context) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(context
					.getAssets().open(asset), "utf-8"));
			String line = null;
			StringBuilder builder = new StringBuilder();
			while (null != (line = bufferedReader.readLine())) {
				builder.append(line).append("\n");
			}
			return builder.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bufferedReader = null;
		}
		return "";
	}
}
