package com.chantyou.janemarried.utils;

import android.database.Cursor;

import com.chantyou.janemarried.framework.db.BaseDAO;
import com.chantyou.janemarried.ui.assistant.fragment.FortBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by j_turn on 2016/4/6.
 * Email 766082577@qq.com
 */
public class FortuneHelper {
    public static final String TABLE_NAME = "tb_fortune";
    private static final String C_ID = "id";
    private static final String C_YEARMONTH = "yearMonth";
    private static final String C_DATE = "date";
    private static final String C_LUNAR = "lunar";
    private static final String C_LUNARYEAR = "lunarYear";
    private static final String C_WEEKDAY = "weekday";
    private static final String C_ANIMALSYEAR = "animalsYear";
    private static final String C_AVOID = "avoid";
    private static final String C_SUIT = "suit";

    private SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-M-d");//2016-4-2格式
    public static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-M");//2016-4格式
    /**
     * 建表语句
     */
    public static final String TABLE_CREATE_SQL = new StringBuilder()
            .append("CREATE TABLE IF NOT EXISTS ")
            .append(TABLE_NAME)
            .append("(")
            .append(C_ID).append(" INTEGER PRIMARY KEY  NOT NULL,")
            .append(C_YEARMONTH).append(" varchar(20) NOT NULL,")
            .append(C_DATE).append(" varchar(16) NOT NULL,")
            .append(C_LUNAR).append(" varchar(20),")
            .append(C_LUNARYEAR).append(" varchar(20),")
            .append(C_WEEKDAY).append(" varchar(20),")
            .append(C_ANIMALSYEAR).append(" varchar(10),")
            .append(C_AVOID).append(" varchar(255),")
            .append(C_SUIT).append(" varchar(255)")
            .append(")")
            .toString();

    /**
     * 数据库操作对象
     */
    private BaseDAO baseDAO;

    private static FortuneHelper instance;

    public static FortuneHelper getInstance() {
        if (instance == null) {
            instance = new FortuneHelper();
        }
        return instance;
    }

    private FortuneHelper() {
        baseDAO = BaseDAO.getInstance();
    }

    public int queryCount() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = baseDAO.getReadableSQLiteDatabase().rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);//返回的是一个int类型的值，并且只有一行，也就是游标为0指向的数
            cursor.close();
            return count;
        }
        return 0;
    }

    public int delAll() {
        return baseDAO.delete(TABLE_NAME, null, null);
    }

    public int delAll(Date date) {
        return baseDAO.delete(TABLE_NAME, C_YEARMONTH + "=?", new String[]{mFormat.format(date)});
    }

    public int queryCount(Date date) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + C_YEARMONTH + "='" + mFormat.format(date) + "'";
        Cursor cursor = baseDAO.getReadableSQLiteDatabase().rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    public FortBean queryBean(Date date) {
        FortBean bean = null;
        try {
            bean = new FortBean();
            bean.date2 = date;
            Cursor cursor = baseDAO.query(TABLE_NAME, null, C_DATE + "=?", new String[]{dFormat.format(date)}, null, null, null);
            if (cursor.moveToFirst()) {
                bean.yearMonth = cursor.getString(cursor.getColumnIndexOrThrow(C_YEARMONTH));
                //getColumnIndexOrThrow得到某个字段所在的列，然后getString得到这个列的数据
                bean.date = cursor.getString(cursor.getColumnIndexOrThrow(C_DATE));
                bean.lunar = cursor.getString(cursor.getColumnIndexOrThrow(C_LUNAR));
                bean.lunarYear = cursor.getString(cursor.getColumnIndexOrThrow(C_LUNARYEAR));
                bean.weekday = cursor.getString(cursor.getColumnIndexOrThrow(C_WEEKDAY));
                bean.animalsYear = cursor.getString(cursor.getColumnIndexOrThrow(C_ANIMALSYEAR));
                bean.avoid = cursor.getString(cursor.getColumnIndexOrThrow(C_AVOID));
                bean.suit = cursor.getString(cursor.getColumnIndexOrThrow(C_SUIT));
                bean.flag = (bean.suit + "").contains("嫁娶") ? 1 : 0;//字符串中是否包含“嫁娶”
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public void insertSql(String sql) {
        //        String isql = "INSERT INTO "+ TABLE_NAME + "(" + C_YEARMONTH +"," + C + "," + CityRow.SUPERIOR + ")VALUES" + sql;
        baseDAO.execSQL(sql);
    }
}
