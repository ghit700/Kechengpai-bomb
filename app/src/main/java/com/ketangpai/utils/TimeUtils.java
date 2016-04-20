package com.ketangpai.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/14.
 */
public class TimeUtils {

    public static String getCurrentTimeFormat(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(calendar.getTimeInMillis());
        return simpleDateFormat.format(date);
    }

    public static String getCurrentDateFormat(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(calendar.getTimeInMillis());
        return simpleDateFormat.format(date);
    }
}
