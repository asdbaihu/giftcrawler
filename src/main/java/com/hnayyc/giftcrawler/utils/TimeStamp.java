package com.hnayyc.giftcrawler.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YangCheng on 2017/3/2.
 */
public class TimeStamp {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public static String getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    public static void main(String[] args) {

        //method 1
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("TimeStamp2 : " + timestamp);

        //method 2 - via Date
        Date date = new Date();
        System.out.println("TimeStamp3 : " + new Timestamp(date.getTime()));

        //return number of milliseconds since January 1, 1970, 00:00:00 GMT
        System.out.println("TimeStamp4 : " + timestamp.getTime());

        //format timestamp
        System.out.println("TimeStamp5 : " + sdf.format(timestamp));
    }
}

