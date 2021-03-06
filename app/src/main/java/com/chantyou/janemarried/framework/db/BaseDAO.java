package com.chantyou.janemarried.framework.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Sqlite“写”操作必须进行数据同步锁操作, 多线程“读”是支持的 其他DAO的“写”操作需要通过该类来操作
 * 
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 2014-3-10]
 */
public class BaseDAO
{
    private DBHelper dbHelper;

    private BaseDAO()
    {
        dbHelper = new DBHelper();
    }

    private static class SingleInstanceHolder
    {
        private final static BaseDAO sInstance = new BaseDAO();
    }

    public static BaseDAO getInstance()
    {
        return SingleInstanceHolder.sInstance;
    }
    
    public synchronized void closeDB()
    {
        dbHelper.close();
    }
    
    /**
     * 事务执行任务
     * @param transactionListener
     * @return 事务执行成功与否
     */
    public synchronized boolean executeWithTransaction(TransactionListener transactionListener)
    {
        try
        {
            dbHelper.getWritableSQLiteDatabase().beginTransaction();
            if (transactionListener != null)
            {
                transactionListener.doTransaction();
            }
            dbHelper.getWritableSQLiteDatabase().setTransactionSuccessful();
            return true;
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            dbHelper.getWritableSQLiteDatabase().endTransaction();
        }
    }
    
    /**
     * 批量任务接口
     * @author hiphonezhu@gmail.com
     * @version [Android-BaseLine, 2015-4-2]
     */
    public interface TransactionListener
    {
        void doTransaction();
    }

    public Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having, String orderBy)
    {
        return dbHelper.getReadableSQLiteDatabase().query(table,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs)
    {
        return dbHelper.getReadableSQLiteDatabase().rawQuery(sql,
                selectionArgs);
    }

    public static Cursor query(SQLiteDatabase db, String table, String[] columns,
            String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
    {
        return db.query(table,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    /**
     * 插入数据
     * 
     * @param tableName
     * @param values
     */
    public synchronized long insert(String tableName, ContentValues values)
    {
        return dbHelper.getWritableSQLiteDatabase().insert(tableName,
                null,
                values);
    }
    
    /**
     * 插入数据, 适用于数据库创建与升级时使用
     * 
     * @param db
     * @param tableName
     * @param values
     */
    public static synchronized long insert(SQLiteDatabase db, String tableName, ContentValues values)
    {
        return db.insert(tableName,
                null,
                values);
    }

    /**
     * 删除数据
     * 
     * @param tableName
     * @param whereClause
     * @param whereArgs
     */
    public synchronized int delete(String tableName, String whereClause, String[] whereArgs)
    {
        return dbHelper.getWritableSQLiteDatabase().delete(tableName,
                whereClause,
                whereArgs);
    }

    /**
     * 删除数据
     * 
     * @param tableName
     * @param whereClause
     * @param whereArgs
     */
    public static synchronized int delete(SQLiteDatabase db, String tableName, String whereClause,
            String[] whereArgs)
    {
        return db.delete(tableName,
                whereClause,
                whereArgs);
    }

    /**
     * 更新数据
     * 
     * @param tableName
     * @param values
     * @param whereClause
     * @param whereArgs
     */
    public synchronized int update(String tableName, ContentValues values, String whereClause,
            String[] whereArgs)
    {
        return dbHelper.getWritableSQLiteDatabase().update(tableName,
                values,
                whereClause,
                whereArgs);
    }

    /**
     * 更新数据
     * 
     * @param tableName
     * @param values
     * @param whereClause
     * @param whereArgs
     */
    public static synchronized int update(SQLiteDatabase db, String tableName,
            ContentValues values, String whereClause, String[] whereArgs)
    {
        return db.update(tableName,
                values,
                whereClause,
                whereArgs);
    }

    public SQLiteDatabase getReadableSQLiteDatabase() {
        return dbHelper.getReadableSQLiteDatabase();
    }

    /**
     * execSQL语句
     * 
     * @param sql
     */
    public synchronized void execSQL(String sql)
    {
        dbHelper.getWritableSQLiteDatabase().execSQL(sql);
    }

    /**
     * execSQL语句
     * 
     * @param sql
     */
    public static synchronized void execSQL(SQLiteDatabase db, String sql)
    {
        db.execSQL(sql);
    }

    /**
     * execSQL语句
     * 
     * @param sql
     * @param bindArgs
     */
    public synchronized void execSQL(String sql, Object[] bindArgs)
    {
        dbHelper.getWritableSQLiteDatabase().execSQL(sql,
                bindArgs);
    }

    /**
     * execSQL语句
     * 
     * @param sql
     * @param bindArgs
     */
    public static synchronized void execSQL(SQLiteDatabase db, String sql, Object[] bindArgs)
    {
        db.execSQL(sql,
                bindArgs);
    }
}
