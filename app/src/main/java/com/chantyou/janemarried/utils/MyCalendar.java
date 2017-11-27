package com.chantyou.janemarried.utils;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by j_turn on 2016/4/13.
 * Email 766082577@qq.com
 */
public class MyCalendar {

    //Android2.2版本以后的URL，之前的就不写了
    private static String calanderURL = "content://com.android.calendar/calendars";
    private static String calanderEventURL = "content://com.android.calendar/events";
    private static String calanderRemiderURL = "content://com.android.calendar/reminders";

//    static {
//        if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
//            calanderEventURL = "content://com.android.calendar/events";
//        } else {
//            calanderEventURL = "content://calendar/events";
//        }
//    }

//    public void addCal(Context context) {
//        long calID = 3;
//        long startMillis = 0;
//        long endMillis = 0;
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(2016, 3, 13, 16, 45);
//        startMillis = beginTime.getTimeInMillis();
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(2016, 3, 13, 16, 55);
//        endMillis = endTime.getTimeInMillis();
//
//        ContentResolver cr = context.getContentResolver();
//        ContentValues values = new ContentValues();
//        values.put(CalendarContract.Events.DTSTART, startMillis);
//        values.put(CalendarContract.Events.DTEND, endMillis);
//        values.put(CalendarContract.Events.TITLE, "简婚：商量事宜");
//        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
//        values.put(CalendarContract.Events.CALENDAR_ID, calID);
//        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
//            Uri uri = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
//            }
//
//            long eventID = Long.parseLong(uri.getLastPathSegment());
//        }
//    }
//
//    public void addReminder(Context context) {
//        ContentResolver cr = context.getContentResolver();
//        ContentValues values = new ContentValues();
//        values.put(CalendarContract.Reminders.MINUTES, 15);
//        values.put(CalendarContract.Reminders.EVENT_ID, 2212);
//        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
//            Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
//        }
//    }

//    /**
//     * 添加日历账户
//     *
//     * @param context
//     */
//    public void addCalendar(Context context) {
//        TimeZone timeZone = TimeZone.getDefault();
//        ContentValues value = new ContentValues();
//        value.put(CalendarContract.Calendars.NAME, "yy");
//
//        value.put(CalendarContract.Calendars.ACCOUNT_NAME, "married@gmail.com");
//        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, "com.android.exchange");
//        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "简婚：商量事宜");
//        value.put(CalendarContract.Calendars.VISIBLE, 1);
//        value.put(CalendarContract.Calendars.CALENDAR_COLOR, -9206951);
//        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
//        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
//        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
//        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, "married@gmail.com");
//        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);
//
//        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
//        calendarUri = calendarUri.buildUpon()
//                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
//                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "married@gmail.com")
//                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "com.android.exchange")
//                .build();
//
//        context.getContentResolver().insert(calendarUri, value);
//    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void addEvent(Context cxt, String title, String desc, Calendar startCal) {
        // 获取要出入的gmail账户的id
        String calId;
        Cursor userCursor = cxt.getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);
//        ArrayList<Map<String, String>> list = new ArrayList<>();
//        if(userCursor != null) {
//            userCursor.moveToFirst();
//            do {
//                Map<String, String> map = new HashMap<>();
//                int cc = userCursor.getColumnCount();
//                list.add(map);
//                for (int ic = 0; ic < cc; ic++) {
//                    try {
//                        String cn = userCursor.getColumnName(ic);
//                        int index = userCursor.getColumnIndexOrThrow(cn);
//                        map.put(cn, userCursor.getString(index));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            } while (userCursor.moveToNext());
//        }

        if (userCursor != null && userCursor.getCount() > 0) {
            userCursor.moveToFirst();  //注意：是向最后一个账户添加，开发者可以根据需要改变添加事件 的账户
            calId = userCursor.getString(userCursor.getColumnIndex("_id"));
            userCursor.close();
        } else {
//            Toast.makeText(cxt, "没有账户，请先添加账户", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues event = new ContentValues();
        event.put(Events.TITLE, title);
        event.put(Events.DESCRIPTION, desc);
        // 插入账户
        event.put(Events.CALENDAR_ID, calId);
        System.out.println("calId: " + calId);
        event.put(Events.EVENT_LOCATION, "");

//        startCal.set(Calendar.HOUR_OF_DAY, 18);
//        startCal.set(Calendar.MINUTE, 39);
        long start = startCal.getTime().getTime();
        startCal.set(Calendar.HOUR_OF_DAY, 23);
        startCal.set(Calendar.MINUTE, 59);
        long end = startCal.getTime().getTime();

        event.put(Events.DTSTART, start);
        event.put(Events.DTEND, end);
        event.put(Events.HAS_ALARM, 1);
        event.put(Events.HAS_ATTENDEE_DATA, 1);
        event.put(Events.STATUS, Events.STATUS_CONFIRMED);

        event.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());  //这个是时区，必须有，

//        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
//        int rows = getContentResolver().update(updateUri, values, null, null);

        Uri calendarUri = Uri.parse(calanderEventURL);
//                .buildUpon()
//                .appendQueryParameter(CalendarContract.Events.TITLE, title)
//                .build();
//        int iu = cxt.getContentResolver().delete(calendarUri, CalendarContract.Events.TITLE + "=?", new String[]{title});

        long id = cxt.getContentResolver().update(calendarUri, event, Events.TITLE + "=?", new String[]{title});
        if (id <= 0) {
            //添加事件
            Uri newEvent = cxt.getContentResolver().insert(calendarUri, event);
            if (newEvent != null) {
                id = Long.parseLong(newEvent.getLastPathSegment());
            }
        }
        if (id > 0) {
            //事件提醒的设定
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.EVENT_ID, id);
            // 提前10分钟有提醒
            values.put(CalendarContract.Reminders.MINUTES, 2);
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            cxt.getContentResolver().insert(Uri.parse(calanderRemiderURL), values);

//            CustomToast.showRightToast(cxt, "success");
        }
    }
}
