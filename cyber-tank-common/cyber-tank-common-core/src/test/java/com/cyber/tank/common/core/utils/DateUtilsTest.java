package com.cyber.tank.common.core.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DateUtilsTest 的核心定义。
 */
class DateUtilsTest {

    @Test
    void shouldFormatAndParseDate() {
        LocalDate date = LocalDate.of(2026, 2, 5);

        String text = DateUtils.formatDate(date);
        LocalDate parsed = DateUtils.parseDate(text);

        assertEquals("2026-02-05", text);
        assertEquals(date, parsed);
    }

    @Test
    void shouldFormatAndParseDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 2, 5, 10, 30, 8);

        String text = DateUtils.formatDateTime(dateTime);
        LocalDateTime parsed = DateUtils.parseDateTime(text);

        assertEquals("2026-02-05 10:30:08", text);
        assertEquals(dateTime, parsed);
    }

    @Test
    void shouldConvertBetweenDateAndLocalDateTime() {
        LocalDateTime now = LocalDateTime.of(2026, 2, 5, 11, 40, 0);

        Date date = DateUtils.toDate(now);
        LocalDateTime result = DateUtils.toLocalDateTime(date);

        assertEquals(now, result);
    }
}
