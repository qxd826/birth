package com.qxd.birth.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiangDong.qu on 16/2/17.
 */
@Slf4j
public class DateUtil {

    /**
     * 根据自定义日期格式生成日期字符串
     *
     * @param date      日期
     * @param formatStr 日期格式 例如:"yyyy-MM-dd HH:mm:ss","yyyy年MM月dd日 HH:mm"
     */
    public static String formatDate(Date date, String formatStr) {
        if (date == null || StringUtils.isBlank(formatStr)) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(formatStr);
        return f.format(date);
    }

    /**
     * 根据自定义日期格式 生成日期对象
     *
     * @param dateStr   日期字符串
     * @param formatStr 日期格式 例如:"yyyy-MM-dd HH:mm:ss","yyyy年MM月dd日 HH:mm"
     *
     * @return
     */
    public static Date parseDate(String dateStr, String formatStr) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(formatStr)) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(formatStr);
        try {
            return f.parse(dateStr);
        } catch (ParseException e) {
            log.error("日期转换异常", e);
        }
        return null;
    }

    /**
     * 将long类型时间转化为date
     *
     * @param millis long日期
     */
    public static Date long2Date(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.getTime();
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy-MM-dd hh:MM:ss"
     *
     * @param date 时间
     *
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToYMDHMS(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy-MM-dd hh:MM"
     *
     * @param date 时间
     *
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToYMDHM(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "yyyy-MM-dd hh:MM:ss"
     *
     * @param date 时间
     *
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToYMDHMStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return f.format(date);
    }

    /**
     * 把时间类型转化为规定格式的字符串
     * "HH:mm"
     *
     * @param date 时间
     *
     * @return 格式化以后的时间字符串
     */
    public static String convertDateToHm(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        return f.format(date);
    }

    /**
     * 将一定格式的时间字符串 转换为date
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @param dateStr 时间字符串
     */
    public static Date convertStringToDate(String dateStr) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return f.parse(dateStr);
        } catch (ParseException e) {
            log.error("转化为时间失败", e);
        }
        return null;
    }

    /**
     * 将一定格式的时间字符串 转换为date
     * "yyyy-MM-dd"
     *
     * @param dateStr 时间字符串
     */
    public static Date convertStringToDateYMD(String dateStr) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return f.parse(dateStr);
        } catch (ParseException e) {
            log.error("转化为时间失败", e);
        }
        return null;
    }


    /**
     * 获取上周日的日期
     * "yyyy-MM-dd"
     */
    public static String getSundayStr() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return df.format(calendar.getTime());
    }

    /**
     * 获取选定日期的目标间隔日期
     *
     * @param date   选定日期
     * @param number 间隔日期
     */
    public static String getDateStr(Date date, int number) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }

    /**
     * 获取两个日期的间隔时间
     *
     * @param currentDate 当前日期
     * @param lastDate    上一个日期
     */
    public static String getDifDateStr(Date currentDate, Date lastDate) {
        long difDate = currentDate.getTime() - lastDate.getTime();
        StringBuffer stringBuffer = new StringBuffer();
        long day = difDate / (24 * 60 * 60 * 1000);

        long hour = difDate % (24 * 60 * 60 * 1000) / (60 * 60 * 1000);

        long minute = difDate % (60 * 60 * 1000) / (60 * 1000);

        if (minute >= 0) {
            stringBuffer.append(minute + "分");
        }
        if (hour > 0) {
            stringBuffer.insert(0, hour + "小时");
        }
        if (day > 0) {
            stringBuffer.insert(0, day + "天");
        }
        return stringBuffer.toString();
    }
}
