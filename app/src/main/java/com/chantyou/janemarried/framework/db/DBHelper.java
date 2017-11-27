package com.chantyou.janemarried.framework.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.framework.log.Logger;
import com.chantyou.janemarried.utils.FortuneHelper;
import com.chantyou.janemarried.utils.SPDBHelper;


/**
 * 数据库轻量级操作封装
 * 
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 2013-3-18]
 */
public class DBHelper
{
    private DataBaseHelper dbHelper;
    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;
    /** 数据库名称 */
    private static final String DATABASE_NAME = "married.db";
    /** 数据库版本 */
    private static final int DATABASE_VERSION = 2;

    public DBHelper()
    {
        dbHelper = new DataBaseHelper(AppAndroid.getApp().getApplicationContext());
    }

    /**
     * 获取数据库操作对象
     * 
     * @return SQLiteDatabase
     */
    public synchronized SQLiteDatabase getWritableSQLiteDatabase()
    {
        writableDB = dbHelper.getWritableDatabase();
        return writableDB;
    }
    
    /**
     * 获取数据库操作对象
     * 
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getReadableSQLiteDatabase()
    {
        readableDB = dbHelper.getReadableDatabase();
        return readableDB;
    }

    /**
     * 关闭数据库
     */
    public void close()
    {
        writableDB = null;
        readableDB = null;
        if (dbHelper != null)
        {
            dbHelper.close();
        }
    }

    public class DataBaseHelper extends SQLiteOpenHelper
    {
        private static final String TAG = "DataBaseHelper";

        public DataBaseHelper(Context context)
        {
            super(context, DATABASE_NAME,
                    null, DATABASE_VERSION);//在构造器这里进行数据库的创建
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.beginTransaction();//手动设置开始事务
            try
            {
                db.execSQL(SPDBHelper.TABLE_CREATE_SQL);//在onCreate方法里执行sql语句，也就是数据表的创建
                db.execSQL(FortuneHelper.TABLE_CREATE_SQL);
                //在这里执行多个数据库操作 //执行过程中可能会抛出异常
                db.setTransactionSuccessful();//数据操作
            }
            catch (Exception e)
            {
                Logger.e(TAG,
                        e);
            }
            finally
            {
                db.endTransaction();//处理完成，结束事务
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
        }
    }
}
