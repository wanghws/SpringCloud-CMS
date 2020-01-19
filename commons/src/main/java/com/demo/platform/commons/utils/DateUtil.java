package com.demo.platform.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by github.com/wanghws on 2019-03-11.
 */
public class DateUtil {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getDateFormat(LocalDateTime date){
        try {
            return date.format(dateTimeFormatter);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static LocalDateTime getDateParse(String date){
        try {
            return LocalDateTime.parse(date, dateTimeFormatter);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}