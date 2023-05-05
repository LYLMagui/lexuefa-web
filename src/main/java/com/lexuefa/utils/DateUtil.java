package com.lexuefa.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ukir
 * @date 2023/04/21 12:48
 **/
public class DateUtil {
    /**
     * 时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 时间格式：HH:mm:ss
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 将日期转换成指定格式的字符串
     *
     * @param date   日期
     * @param format 格式化字符串
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 格式化LocalDateTime
     * @param localDateTime
     * @param format
     * @return
     */
    public static LocalDateTime parseDate(LocalDateTime localDateTime,String format){
        return LocalDateTime.parse(localDateTime.toString(), DateTimeFormatter.ofPattern(format));
    }

    /**
     * 将日期字符串转换成指定格式的日期
     *
     * @param dateStr 日期字符串
     * @param format  格式化字符串
     * @return 格式化后的日期
     * @throws ParseException 解析异常
     */
    public static Date parseDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateStr);
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前日期时间
     */
    public static Date getCurrentDateTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static Date getCurrentDate() {
        String dateStr = formatDate(getCurrentDateTime(), DATE_FORMAT);
        try {
            return parseDate(dateStr, DATE_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static Date getCurrentTime() {
        String timeStr = formatDate(getCurrentDateTime(), TIME_FORMAT);
        try {
            return parseDate(timeStr, TIME_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
