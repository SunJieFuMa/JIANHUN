package com.chantyou.janemarried.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chantyou.janemarried.framework.db.BaseDAO;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * SharedPreferences的数据库实现方式
 *
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 2014-2-20]
 */
public class SPDBHelper {
    public static final String TABLE_NAME = "shared_prefs";
    private static final String COLUMN_KEY = "key";
    private static final String COLUMN_VALUE = "value";
    /**
     * 建表语句
     */
    public static final String TABLE_CREATE_SQL = new StringBuilder()
            .append("CREATE TABLE ")//注意空格一定要有
            .append(TABLE_NAME)
            .append("(")
            .append(COLUMN_KEY)
            .append(" TEXT,")
            .append(COLUMN_VALUE)
            .append(" TEXT)")
            .toString();
    /*
      ExecuteService是一个接口，继承了Executor，
      Executor也是一个接口，Executor里面只有一个方法： void execute(Runnable command)
      所以说Executors.newCachedThreadPool()既可以是ExecuteService的实例也可以是Executor的实例
     */
    private static final Executor mExecutor = Executors.newCachedThreadPool();

    /**
     * 数据库操作对象
     */
    private BaseDAO baseDAO;

    public SPDBHelper() {
        baseDAO = BaseDAO.getInstance();
    }

    public void contains(final String key, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onResult(contains(key));
            }
        });
    }

    private boolean contains(final String key) {
        boolean isExist = false;
        Cursor cursor = null;
        try {
            cursor = baseDAO.query(TABLE_NAME,
                    null,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key},
                    null,
                    null,
                    null);
            if (cursor.getCount() > 0) {
                isExist = true;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return isExist;
    }

    public static void contains(final SQLiteDatabase db, final String key, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onResult(contains(db, key));
            }
        });
    }

    private static boolean contains(final SQLiteDatabase db, final String key) {
        boolean isExist = false;
        Cursor cursor = null;
        try {
            cursor = BaseDAO.query(db,
                    TABLE_NAME,
                    null,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key},
                    null,
                    null,
                    null);
            if (cursor.getCount() > 0) {//如果游标的数目大于0，就说明数据库里这个字段有数据
                isExist = true;
            }
        } finally {
            if (cursor != null) {
                cursor.close();//关闭游标
            }
        }
        return isExist;
    }

    public void getBoolean(final String key, final boolean defaultResult, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onResult(getBoolean(key, defaultResult));
            }
        });
    }

    /**
     * 查询某个value是否存在
     *
     * @param key
     * @param defaultResult
     * @return
     */
    public boolean getBoolean(final String key, final boolean defaultResult) {
        boolean result = defaultResult;
        Cursor cursor = null;
        try {
            cursor = baseDAO.query(TABLE_NAME,
                    null,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key},
                    null,
                    null,
                    null);
            if (cursor.moveToNext()) {
                result = cursor.getInt(cursor.getColumnIndex(COLUMN_VALUE)) == 1;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public void putBoolean(final String key, final boolean value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putBoolean(key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    /**
     * 存入布尔值
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        if (contains(key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.update(TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.insert(TABLE_NAME,
                    values);
        }
    }

    public static void putBoolean(final SQLiteDatabase db, final String key, final boolean value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putBoolean(db, key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public static void putBoolean(SQLiteDatabase db, String key, boolean value) {
        if (contains(db,
                key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.update(db,
                    TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.insert(db,
                    TABLE_NAME,
                    values);
        }
    }

    public void getString(final String key, final String defaultValue, final ResultListener<String> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onResult(getString(key, defaultValue));
            }
        });
    }

    public String getString(String key, String defaultValue) {
        String result = defaultValue;
        Cursor cursor = null;
        try {
            cursor = baseDAO.query(TABLE_NAME,
                    new String[]
                            {COLUMN_VALUE},
                    COLUMN_KEY + "=?",
                    new String[]
                            {key},
                    null,
                    null,
                    null);
            if (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex(COLUMN_VALUE));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public void putString(final String key, final String value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putString(key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public void putString(String key, String value) {
        if (contains(key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.update(TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.insert(TABLE_NAME,
                    values);
        }
    }

    public static void putString(final SQLiteDatabase db, final String key, final String value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putString(db, key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public static void putString(SQLiteDatabase db, String key, String value) {
        if (contains(db,
                key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.update(db,
                    TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.insert(db,
                    TABLE_NAME,
                    values);
        }
    }

    public void getInteger(final String key, final int defaultValue, final ResultListener<Integer> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onResult(getInteger(key, defaultValue));
            }
        });
    }

    public int getInteger(String key, int defaultValue) {
        int result = defaultValue;
        Cursor cursor = null;
        try {
            cursor = baseDAO.query(TABLE_NAME,
                    new String[]
                            {COLUMN_VALUE},
                    COLUMN_KEY + "=?",
                    new String[]
                            {key},
                    null,
                    null,
                    null);
            if (cursor.moveToNext()) {
                result = cursor.getInt(cursor.getColumnIndex(COLUMN_VALUE));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public void putInteger(final String key, final int value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putInteger(key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public void putInteger(String key, int value) {
        if (contains(key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.update(TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.insert(TABLE_NAME,
                    values);
        }
    }

    public static void putInteger(final SQLiteDatabase db, final String key, final int value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putInteger(db, key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public static void putInteger(SQLiteDatabase db, String key, int value) {
        if (contains(db, key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.update(db, TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.insert(db, TABLE_NAME, values);
        }
    }

    public void getLong(final String key, final long defaultValue, final ResultListener<Long> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onResult(getLong(key, defaultValue));
            }
        });
    }

    public long getLong(String key, long defaultValue) {
        long result = defaultValue;
        Cursor cursor = null;
        try {
            cursor = baseDAO.query(TABLE_NAME,
                    new String[]
                            {COLUMN_VALUE},
                    COLUMN_KEY + "=?",
                    new String[]
                            {key},
                    null,
                    null,
                    null);
            if (cursor.moveToNext()) {
                result = cursor.getLong(cursor.getColumnIndex(COLUMN_VALUE));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public void putLong(final String key, final long value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putLong(key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public void putLong(String key, long value) {
        if (contains(key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.update(TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.insert(TABLE_NAME,
                    values);
        }
    }

    public static void putLong(final SQLiteDatabase db, final String key, final long value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putLong(db, key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public static void putLong(SQLiteDatabase db, String key, long value) {
        if (contains(db,
                key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.update(db,
                    TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.insert(db,
                    TABLE_NAME,
                    values);
        }
    }

    public void getDouble(final String key, final double defaultValue, final ResultListener<Double> listener) {
        mExecutor.execute(new Runnable() {

            @Override
            public void run() {
                listener.onResult(getDouble(key, defaultValue));
            }
        });
    }

    public double getDouble(String key, double defaultValue) {
        double result = defaultValue;
        Cursor cursor = null;
        try {
            cursor = baseDAO.query(TABLE_NAME,
                    new String[]
                            {COLUMN_VALUE},
                    COLUMN_KEY + "=?",
                    new String[]
                            {key},
                    null,
                    null,
                    null);
            if (cursor.moveToNext()) {
                result = cursor.getDouble(cursor.getColumnIndex(COLUMN_VALUE));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public void putDouble(final String key, final double value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putDouble(key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public void putDouble(String key, double value) {
        if (contains(key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.update(TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.insert(TABLE_NAME,
                    values);
        }
    }

    public static void putDouble(final SQLiteDatabase db, final String key, final double value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                putDouble(db, key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public static void putDouble(SQLiteDatabase db, String key, double value) {
        if (contains(db,
                key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.update(db,
                    TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.insert(db,
                    TABLE_NAME,
                    values);
        }
    }

    public void getFloat(final String key, final float defaultValue, final ResultListener<Float> listener) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onResult(getFloat(key, defaultValue));
            }
        });
    }

    public float getFloat(String key, float defaultValue) {
        float result = defaultValue;
        Cursor cursor = null;
        try {
            cursor = baseDAO.query(TABLE_NAME,
                    new String[]
                            {COLUMN_VALUE},
                    COLUMN_KEY + "=?",
                    new String[]
                            {key},
                    null,
                    null,
                    null);
            if (cursor.moveToNext()) {
                result = cursor.getFloat(cursor.getColumnIndex(COLUMN_VALUE));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public void putFloat(final String key, final float value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {

            @Override
            public void run() {
                putFloat(key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public void putFloat(String key, float value) {
        if (contains(key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.update(TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            baseDAO.insert(TABLE_NAME,
                    values);
        }
    }

    public static void putFloat(final SQLiteDatabase db, final String key, final float value, final ResultListener<Boolean> listener) {
        mExecutor.execute(new Runnable() {

            @Override
            public void run() {
                putFloat(db, key, value);
                if (listener != null) {
                    listener.onResult(true);
                }
            }
        });
    }

    public static void putFloat(SQLiteDatabase db, String key, float value) {
        if (contains(db,
                key)) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.update(db,
                    TABLE_NAME,
                    values,
                    COLUMN_KEY + "=?",
                    new String[]
                            {key});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEY,
                    key);
            values.put(COLUMN_VALUE,
                    value);
            BaseDAO.insert(db,
                    TABLE_NAME,
                    values);
        }
    }

    public interface ResultListener<T> {
        void onResult(T response);
    }
}
