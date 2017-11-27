package com.mhh.lib.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class FilePaths {
	
	public static String getCameraSaveFilePath(Context cxt){
		return SystemUtils.getExternalCachePath(cxt) +
				File.separator + "camera.jpg";
	}

	public static String getPictureCropPath(Context cxt){
		return SystemUtils.getExternalCachePath(cxt) +
				File.separator + "crop.jpg";
	}
	
	public static String getPictureChooseFilePath(Context cxt){
		return SystemUtils.getExternalCachePath(cxt) +
				File.separator + "choose.jpg";
	}
	
	public static String getShareFilePath(Context cxt){
		return SystemUtils.getExternalCachePath(cxt) +
				File.separator + "share.jpg";
	}

	public static String getAudioFilePath(Context cxt, String url){
		return SystemUtils.getExternalCachePath(cxt) +
				File.separator + "audio"+File.separator+SystemUtils.encryptBySHA1(url);
	}

	public static String getVideoFilePath(Context cxt, String url){
		return SystemUtils.getExternalCachePath(cxt) +
				File.separator + "video"+File.separator+SystemUtils.encryptBySHA1(url);
	}

	private static String getAppDir(Context cxt){
		return SystemUtils.getExternalCachePath(cxt) +
				File.separator + "App";
	}

	public static String getAppUpdatePath(Context cxt, String fileName){
		String pathDir = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				File dir = new File(
						Environment.getExternalStorageDirectory().getAbsolutePath() +
								File.separator + "Download"+File.separator + "App");
				if (!dir.exists())
					dir.mkdir();
				pathDir = dir.getPath();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(TextUtils.isEmpty(pathDir)) {
			pathDir = getAppDir(cxt);
		}
		return pathDir +File.separator +fileName;
	}

	public static String getUrlFileCachePath(Context cxt, String strUrl){
		if(TextUtils.isEmpty(strUrl)){
			return SystemUtils.getExternalCachePath(cxt) + File.separator + "urlfile";
		}
		return SystemUtils.getExternalCachePath(cxt) +
				File.separator + "urlfile" + File.separator + SystemUtils.encryptBySHA1(strUrl);
	}
//	
//	public static String getQrcodeSavePath(String strIMUser){
//		if(TextUtils.isEmpty(strIMUser)){
//			return SystemUtils.getExternalCachePath(XApplication.getApplication()) + 
//					File.separator + "qrcode";
//		}
//		return SystemUtils.getExternalCachePath(XApplication.getApplication()) + 
//				File.separator + "qrcode" + File.separator + strIMUser;
//	}
//	
//	public static String getImportFolderPath(){
//		return SystemUtils.getExternalCachePath(XApplication.getApplication()) +
//				File.separator + "importfile" + File.separator;
//	}
//	
//	public static String getCameraVideoFolderPath(){
//		return SystemUtils.getExternalCachePath(XApplication.getApplication()) +
//				File.separator + "videos" + File.separator;
//	}
}
