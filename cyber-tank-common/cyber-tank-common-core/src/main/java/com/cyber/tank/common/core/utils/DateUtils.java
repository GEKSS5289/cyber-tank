package com.cyber.tank.common.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
/**
 * DateUtils 的核心定义。
 */
public final class DateUtils {

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * formatDate 方法。
     * @param date 参数。
     * @return 执行结果。
     */
    public static String formatDate(LocalDate date) {
        return formatDate(date, DEFAULT_DATE_PATTERN);
    }

    /**
     * formatDate 方法。
     * @param date 参数。
     * @param pattern 参数。
     * @return 执行结果。
     */
    public static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * formatDateTime 方法。
     * @param dateTime 参数。
     * @return 执行结果。
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DEFAULT_DATE_TIME_PATTERN);
    }

    /**
     * formatDateTime 方法。
     * @param dateTime 参数。
     * @param pattern 参数。
     * @return 执行结果。
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * parseDate 方法。
     * @param dateText 参数。
     * @return 执行结果。
     */
    public static LocalDate parseDate(String dateText) {
        return parseDate(dateText, DEFAULT_DATE_PATTERN);
    }

    /**
     * parseDate 方法。
     * @param dateText 参数。
     * @param pattern 参数。
     * @return 执行结果。
     */
    public static LocalDate parseDate(String dateText, String pattern) {
        return LocalDate.parse(dateText, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * parseDateTime 方法。
     * @param dateTimeText 参数。
     * @return 执行结果。
     */
    public static LocalDateTime parseDateTime(String dateTimeText) {
        return parseDateTime(dateTimeText, DEFAULT_DATE_TIME_PATTERN);
    }

    /**
     * parseDateTime 方法。
     * @param dateTimeText 参数。
     * @param pattern 参数。
     * @return 执行结果。
     */
    public static LocalDateTime parseDateTime(String dateTimeText, String pattern) {
        return LocalDateTime.parse(dateTimeText, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * toDate 方法。
     * @param localDateTime 参数。
     * @return 执行结果。
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * toLocalDateTime 方法。
     * @param date 参数。
     * @return 执行结果。
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
