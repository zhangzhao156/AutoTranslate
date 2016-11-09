package com.baidu.translate.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ABC on 16/11/8.
 */
public class AppLog {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss:");

    public static void i(String msg) {
        System.out.println(dateFormat.format(new Date(System.currentTimeMillis())) + msg);
    }

    public static void d(String msg) {
        System.err.println(dateFormat.format(new Date(System.currentTimeMillis())) + msg);
    }
}
