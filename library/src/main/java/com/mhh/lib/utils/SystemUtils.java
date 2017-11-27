package com.mhh.lib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.security.MessageDigest;

/**
 * Created by j_turn on 2015/12/8.
 * Email 766082577@qq.com
 */
public class SystemUtils {

    public static String getFromAssets(Context cxt, String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(cxt.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前应用的版本号
     * @param cxt
     * @return
     */
    public static int getCurVCode(Context cxt) {
        try {
            PackageInfo info = cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0);
            if(info != null && !TextUtils.isEmpty(info.versionName))
                return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取当前应用的版本名称
     * @param cxt
     * @return
     */
    public static String getCurVersion(Context cxt) {
        try {
            PackageInfo info = cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0);
            if(info != null && !TextUtils.isEmpty(info.versionName))
                return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int dipToPixel(Context cxt, int nDip) {
        return cxt.getResources().getDimensionPixelOffset(com.mhh.lib.R.dimen.per_dp) * nDip;
    }

    public static String encryptBySHA1(String strMessage){
        String strEncrypt = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte buf[] = digest.digest(strMessage.getBytes());
            String stmp = null;
            for (int n = 0; n < buf.length; n++) {
                stmp = Integer.toHexString(buf[n] & 0xff);
                strEncrypt = stmp.length() == 1 ? (strEncrypt + "0" + stmp) : (strEncrypt + stmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strEncrypt;
    }

    public static String getDeviceUUID(Context cxt){
        final TelephonyManager tm = (TelephonyManager)cxt.getSystemService(Context.TELEPHONY_SERVICE);
        String strIMEI = tm.getDeviceId();
        if(TextUtils.isEmpty(strIMEI)){
            strIMEI = "1";
        }

        String strMacAddress = getMacAddress(cxt);
        if(TextUtils.isEmpty(strMacAddress)){
            strMacAddress = "1";
        }

        return strIMEI + strMacAddress;
    }

    public static String getMacAddress(Context context){
        final WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    public static String getExternalCachePath(Context context){
        return Environment.getExternalStorageDirectory().getPath() +
                "/Android/data/" + context.getPackageName() + "/cache";
    }

    public static boolean compressBitmapFile(String dstPath,String srcPath,int reqWidth,int reqHeight, boolean deletesrc){
        if(dstPath == null || srcPath == null)
            throw new NullPointerException("图片地址不能为空");
        if(!dstPath.equals(srcPath))
            deleteFile(dstPath);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFile(srcPath, options);
        if(options.outWidth > 0){
            if(options.outWidth > reqWidth || options.outHeight > reqHeight){
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
                options.inJustDecodeBounds = false;
                try{
                    Bitmap bmp = BitmapFactory.decodeFile(srcPath,options);
                    FileHelper.saveBitmapToFile(dstPath, bmp, 90);
                }catch(OutOfMemoryError e){
                    e.printStackTrace();
                    return false;
                }
            }else{
                FileHelper.copyFile(dstPath, srcPath);
            }
            if(!dstPath.equals(srcPath) && deletesrc)
                deleteFile(srcPath);
        }else{
            return false;
        }
        return true;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            final float totalPixels = width * height;

            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static void deleteFile(String path) {
        try {
            File file = new File(path);
            if(file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
