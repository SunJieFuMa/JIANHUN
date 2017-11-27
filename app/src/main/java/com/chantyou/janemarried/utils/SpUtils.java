package com.chantyou.janemarried.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/18.
 */
public class SpUtils {
    private static SharedPreferences mSharedPreferences;

    //定义成静态方法，类名可以直接调用，方便
    public static void putString(Context context, String key, String value){
        mSharedPreferences =  context.getSharedPreferences(MyContants.FileName,Context.MODE_PRIVATE);
        mSharedPreferences.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String defValue){
        mSharedPreferences=context.getSharedPreferences(MyContants.FileName,Context.MODE_PRIVATE);
        return mSharedPreferences.getString(key,defValue);
    }

    public static void putBoolean(Context context,String key,boolean value){
        mSharedPreferences=context.getSharedPreferences(MyContants.FileName,Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context,String key,boolean defValue){
        mSharedPreferences=context.getSharedPreferences(MyContants.FileName,Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(key,defValue);
    }

    public static void putInt(Context context,String key,int value){
        mSharedPreferences=context.getSharedPreferences(MyContants.FileName,Context.MODE_PRIVATE);
        mSharedPreferences.edit().putInt(key,value).commit();
    }

    public static int getInt(Context context,String key,int defValue){
        mSharedPreferences=context.getSharedPreferences(MyContants.FileName,Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(key,defValue);
    }

}
