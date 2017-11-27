package com.chantyou.janemarried.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhangYB on 2016/5/4.
 */
public class SharedPreferenceUtils {

    //工具类不允许创建对象
    private SharedPreferenceUtils() {
        throw new AssertionError("No instances.");
    }


    /**
     * 保存变量
     *
     * @param context
     * @param name
     * @param value
     */
    public static void putString(Context context, String name, String value) {
        SharedPreferences sss = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sss.edit();
        editor.putString(name, value);
        editor.commit();
    }

    /**
     * 获取变量
     *
     * @param context
     * @param name
     * @return
     */
    public static String getString(Context context, String name, String defaultString) {
        SharedPreferences sss = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sss.getString(name, defaultString);
    }

    /**
     * 保存变量
     *
     * @param context
     * @param name
     * @param value
     */
    public static void putInt(Context context, String name, int value) {
        SharedPreferences sss = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sss.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    /**
     * 获取变量
     *
     * @param context
     * @param name
     * @return
     */
    public static int getInt(Context context, String name, int defaultString) {
        SharedPreferences sss = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sss.getInt(name, defaultString);
    }

    /**
     * 保存变量
     *
     * @param context
     * @param name
     * @param value
     */
    public static void putFloat(Context context, String name, float value) {
        SharedPreferences sss = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sss.edit();
        editor.putFloat(name, value);
        editor.commit();
    }

    /**
     * 获取变量
     *
     * @param context
     * @param name
     * @return
     */
    public static float getFloat(Context context, String name, float defaultString) {
        SharedPreferences sss = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sss.getFloat(name, defaultString);
    }
    /**
     * 保存变量
     *
     * @param context
     * @param name
     * @param value
     */
    public static void putBoolean(Context context, String name, boolean value) {
        SharedPreferences sss = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sss.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    /**
     * 获取变量
     *
     * @param context
     * @param name
     * @return
     */
    public static boolean getBoolean(Context context, String name, boolean defaultString) {
        SharedPreferences sss = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sss.getBoolean(name, defaultString);
    }
}
