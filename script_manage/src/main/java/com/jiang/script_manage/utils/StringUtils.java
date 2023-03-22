package com.jiang.script_manage.utils;

public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String s : strings) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyEquals(String s, String... strings) {
        if (strings == null) {
            return false;
        }
        for (String string : strings) {
            if (s.equals(string)) {
                return true;
            }
        }
        return false;
    }

}
