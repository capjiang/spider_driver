package com.jiang.user_manage.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    public static void main(String[] args) {
        System.out.println(encode("123456"));
    }

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean matches(String get_pw, String db_pw) {
        return passwordEncoder.matches(get_pw, db_pw);
    }
}
