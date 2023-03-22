package com.jiang.task_manage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final String DEFAULT_FILE_FORMAT = "yyyy-MM-dd_HH-mm-ss";

    public static String dateToFileString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FILE_FORMAT);
        return sdf.format(date);
    }

}
