package com.chantyou.janemarried.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.httprunner.HttpRunner;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class XLocationManager {
	private final static String TAG = "XLocationManager";

	//声明AMapLocationClient类对象
	public static AMapLocationClient mLocationClient = null;

	public static void requestGetLocation(OnGetLocationListener listener) {
        XLocationListener xlistener = new XLocationListener(listener);
        try {
            //初始化定位
            mLocationClient = new AMapLocationClient(AppAndroid.getApp());
            //设置定位回调监听
            mLocationClient.setLocationListener(xlistener);

            //声明mLocationOption对象
            AMapLocationClientOption mLocationOption = null;
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
            //设置是否强制刷新WIFI，默认为强制刷新
            mLocationOption.setWifiActiveScan(true);
            //设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);
            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(2000);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
            nativeLocate(xlistener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AppAndroid.getMainThreadHandler().postDelayed(xlistener, 20000);
	}

	private static void nativeLocate(XLocationListener listener) throws Exception {
		LocationManager lm = (LocationManager) AppAndroid.getApp().getSystemService(Context.LOCATION_SERVICE);
		NativeLocationListener nativeListener = new NativeLocationListener(listener);
		Criteria c = new Criteria();
		final String provider = lm.getBestProvider(c, false);
		if (ActivityCompat.checkSelfPermission(AppAndroid.getApp(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(AppAndroid.getApp(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			lm.requestLocationUpdates(provider, 1000, 0, nativeListener);
		}
		AppAndroid.getMainThreadHandler().postDelayed(nativeListener, 20000);
	}


	public static class AddInfo {
		String long_name;
		String types;

		public AddInfo(JSONObject jo) {
			parse(jo, this);
		}
	}

	public static class AddressComponents {
		List<AddInfo> infos = new ArrayList<AddInfo>();

		String country = "";
		String administrative_area_level_1 = "";
		String locality = "";
		String sublocality = "";
		String route = "";
		String street_number = "";

		public AddressComponents(JSONObject jo) throws JSONException {
			JSONArray ja = jo.getJSONArray("address_components");
			int length = ja.length();
			for (int index = 0; index < length; ++index) {
				AddInfo ai = new AddInfo(ja.getJSONObject(index));
				infos.add(ai);
				if (ai.types.contains("country")) {
					country = ai.long_name;
				} else if (ai.types.contains("locality")) {
					locality = ai.long_name;
					if (locality != null) {
						if (!locality.contains("市")) {
							locality = locality + "市";
						}
					}
				} else if (ai.types.contains("administrative_area_level_1")) {
					administrative_area_level_1 = ai.long_name;
				} else if (ai.types.contains("sublocality")) {
					sublocality = ai.long_name;
				} else if (ai.types.contains("route")) {
					route = ai.long_name;
				} else if (ai.types.contains("street_number")) {
					street_number = ai.long_name;
				}
			}
		}
	}

	public static void requestGetAddress(final double lat, final double lng,
										 final OnGetAddressListener listener) {
		try {
			Response<String> response = HttpRunner.getLiteHttp().executeOrThrow(new StringRequest(String
					.format("http://maps.google.com/maps/api/geocode/json?latlng=%s&language=zh-CN&sensor=false",
							lat + "," + lng))
					.setMethod(HttpMethods.Get));
			String result = response.getResult();
			try {
				JSONObject jo = new JSONObject(result);
				JSONArray ja = jo.getJSONArray("results");
				final AddressComponents ac = new AddressComponents(ja
						.getJSONObject(0));
				if (listener != null) {
					AppAndroid.getMainThreadHandler().post(
							new Runnable() {
								@Override
								public void run() {
									Address a = new Address(Locale
											.getDefault());
									a.setCountryName(ac.country);
									a.setAdminArea(ac.administrative_area_level_1);
									a.setLocality(ac.locality);
									a.setFeatureName(ac.sublocality
											+ ac.route
											+ ac.street_number);
									listener.onGetAddressFinished(a,
											true);
								}
							});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class XLocationListener implements
			AMapLocationListener, Runnable {

		private final OnGetLocationListener mListener;
		private AMapLocation mLocation;

		public XLocationListener(OnGetLocationListener listener) {
			mListener = listener;
		}

		@Override
		public void onLocationChanged(final AMapLocation location) {
			mLocation = location;
//			mLastLocation = location;
			if (mListener != null) {
				AppAndroid.getMainThreadHandler().post(new Runnable() {
					@Override
					public void run() {
						mListener.onGetLocationFinished(location, location != null);
//						mAMapLocManager.removeUpdates(XLocationListener.this);
                        mLocationClient.unRegisterLocationListener(XLocationListener.this);
						mLocationClient.stopLocation();
						AppAndroid.getMainThreadHandler().removeCallbacks(XLocationListener.this);
					}
				});

			}
		}

		@Override
		public void run() {
//			mAMapLocManager.removeUpdates(this);
            mLocationClient.unRegisterLocationListener(this);
		}

	}

	private static class NativeLocationListener implements LocationListener, Runnable {

		private final OnGetLocationListener mListener;
		private final XLocationListener mAMapListener;
		private AMapLocation mLocation;
		private boolean mIsGettingAddress;
		private boolean mNeedNotifyAfterGetAddress;

		public NativeLocationListener(XLocationListener listener) {
			mAMapListener = listener;
			mListener = mAMapListener.mListener;
		}

		@Override
		public void run() {
			final LocationManager lm = (LocationManager) AppAndroid.getApp().getSystemService(Context.LOCATION_SERVICE);
			if (ActivityCompat.checkSelfPermission(AppAndroid.getApp(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AppAndroid.getApp(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			lm.removeUpdates(this);
			if(mLocation != null){
				notifyListener();
			}else{
				if(mIsGettingAddress){
					mNeedNotifyAfterGetAddress = true;
				}
			}
		}

		@Override
		public void onLocationChanged(final Location location) {
			//MyLog.w(TAG, "native location:" + location);
			//mLocation = location;
			if(!mIsGettingAddress){
				mIsGettingAddress = true;
				requestGetAddress(location.getLatitude(), location.getLongitude(),
						new OnGetAddressListener() {
							@Override
							public void onGetAddressFinished(Address address, boolean bSuccess) {
								if(bSuccess){
									mLocation = new AMapLocation(location);
									Bundle b = new Bundle();
									b.putString("desc", getAddressDesc(address));
									mLocation.setExtras(b);
//									mLastLocation = mLocation;
									if(mNeedNotifyAfterGetAddress){
										notifyListener();
									}
								}
							}
						});
			}
		}

		protected void notifyListener(){
			if(mAMapListener.mLocation == null &&
					mLocation != null &&
					mListener != null){
				AppAndroid.getMainThreadHandler().post(new Runnable() {
					@Override
					public void run() {
						mListener.onGetLocationFinished(mLocation, true);
					}
				});
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

	}

	public static String getAddressDesc(Address a){
		return a.getCountryName() + a.getAdminArea() + a.getLocality() + a.getFeatureName();
	}

	public static void parse(JSONObject jo, Object item) {
		parse(jo, item, item.getClass(), true);
	}

	public static void parse(JSONObject jo, Object item, Class<?> clazz) {
		parse(jo, item, clazz, true);
	}

	public static void parse(JSONObject jo, Object item, Class<?> c,
			boolean bUseLowerCase) {
		Field fs[] = c.getDeclaredFields();
		for (Field f : fs) {
			String name = f.getName();
			if (bUseLowerCase) {
				name = name.toLowerCase(Locale.getDefault());
			}
			if (jo.has(name) && !jo.isNull(name)) {
				final Class<?> clazz = f.getType();
				try {
					f.setAccessible(true);
					if (clazz.equals(String.class)) {
						f.set(item, jo.getString(name));
					} else if (clazz.equals(int.class)) {
						f.set(item, jo.getInt(name));
					} else if (clazz.equals(boolean.class)) {
						final String value = jo.getString(name);
						f.set(item, "1".equals(value) || "true".equals(value));
					} else if (clazz.equals(long.class)) {
						f.set(item, jo.getLong(name));
					} else if (clazz.equals(double.class)) {
						f.set(item, jo.getDouble(name));
					} else if (clazz.equals(float.class)) {
						f.set(item, (float) jo.getDouble(name));
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
	}

	public static interface OnGetLocationListener{
		public void onGetLocationFinished(AMapLocation location, boolean bSuccess);
	}

	public static interface OnGetLocationImageListener{
		public void onGetImageFinished(Bitmap bmp, boolean bSuccess);
	}

	public static interface OnGetAddressListener{
		public void onGetAddressFinished(Address address, boolean bSuccess);
	}
}
