package com.jiang.script_manage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static String pattern = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public static String dateToSQLString(Date date) {
        return simpleDateFormat.format(date);
    }
}
