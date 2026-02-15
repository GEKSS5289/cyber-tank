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
public final class DateUtils {

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(LocalDate date) {
        return formatDate(date, DEFAULT_DATE_PATTERN);
    }

    public static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DEFAULT_DATE_TIME_PATTERN);
    }

    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseDate(String dateText) {
        return parseDate(dateText, DEFAULT_DATE_PATTERN);
    }

    public static LocalDate parseDate(String dateText, String pattern) {
        return LocalDate.parse(dateText, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseDateTime(String dateTimeText) {
        return parseDateTime(dateTimeText, DEFAULT_DATE_TIME_PATTERN);
    }

    public static LocalDateTime parseDateTime(String dateTimeText, String pattern) {
        return LocalDateTime.parse(dateTimeText, DateTimeFormatter.ofPattern(pattern));
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
