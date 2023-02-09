package cn.jarkata.commons.utils;

import cn.jarkata.commons.JarkataConstants;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Date;
import java.util.Objects;

/**
 * Date Utils
 */
public class DateUtils {

    /**
     * ISO_DATE_TIME
     */
    private static final DateTimeFormatter ISO_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter ISO_DATETIME1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * yyyyMMdd格式
     */
    private static final DateTimeFormatter BASIC_ISO_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER2 = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER3 = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter TIME_FORMATTER4 = DateTimeFormatter.ofPattern("HHmmss");

    public static LocalTime parseToTime(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        if (StringUtils.length(str) == 4) {
            return LocalTime.parse(str, TIME_FORMATTER3);
        }
        if (StringUtils.length(str) == 5) {
            return LocalTime.parse(str, TIME_FORMATTER2);
        }
        if (StringUtils.length(str) == 6) {
            return LocalTime.parse(str, TIME_FORMATTER4);
        }
        return LocalTime.parse(str, TIME_FORMATTER);
    }

    public static LocalDateTime ofLocalDateTime(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }
        return LocalDateTime.of(localDate, LocalTime.of(0, 0));
    }

    public static LocalDateTime ofLocalDateTime(LocalTime localTime) {
        if (Objects.isNull(localTime)) {
            return null;
        }
        return LocalDateTime.of(LocalDate.now(), localTime);
    }

    public static LocalDate ofLocalDate(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return dateTime.toLocalDate();
    }

    public static LocalTime ofLocalTime(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return dateTime.toLocalTime();
    }

    public static Instant ofInstant(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    /**
     * Date Utils
     *
     * @param timestamp the long of timestamp
     * @return LocalDateTime object
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        if (timestamp <= 0) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 转换位LocalDate
     *
     * @param timestamp 时间戳
     * @return 返回LocalDate对象
     */
    public static LocalDate toLocalDate(long timestamp) {
        if (timestamp <= 0) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * yyyy-MM-dd格式的日期解析为LocalDate
     *
     * @param localDateStr yyyy-MM-dd格式的日期
     * @return LocalDate对象
     */
    public static LocalDate parseToDate(String localDateStr) {
        if (StringUtils.isBlank(localDateStr)) {
            return null;
        }
        if (StringUtils.length(localDateStr) == 8) {
            return LocalDate.parse(localDateStr, BASIC_ISO_DATE);
        }
        return LocalDate.parse(localDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * yyyy-MM-dd'T'HH:mm:ss 格式的日期解析为LocalDateTime对象
     * yyyy-MM-dd HH:mm:ss 格式的日期解析为LocalDateTime对象
     * yyyy-MM-dd 格式解析为LocalDateTime对象
     *
     * @param localDateTimeStr yyyy-MM-dd'T'HH:mm:ss
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseToDateTime(String localDateTimeStr) {
        if (StringUtils.isBlank(localDateTimeStr)) {
            return null;
        }
        if (StringUtils.length(localDateTimeStr) == 8 || StringUtils.length(localDateTimeStr) == 10) {
            LocalDate localDate = parseToDate(localDateTimeStr);
            if (Objects.isNull(localDate)) {
                return null;
            }
            return LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        }
        if (StringUtils.length(localDateTimeStr) == 19) {
            int index = localDateTimeStr.indexOf("T");
            if (index < 0) {
                return LocalDateTime.parse(localDateTimeStr, ISO_DATETIME1);
            }
        }
        TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(localDateTimeStr);
        LocalDate localDate = temporalAccessor.query(TemporalQueries.localDate());
        LocalTime localTime = temporalAccessor.query(TemporalQueries.localTime());
        return LocalDateTime.of(localDate, localTime);
    }

    /**
     * 将java.util.Date类型转换位java.time.LocalDateTime类型
     * 如果参数位空，返回null
     *
     * @param date java.util.Date对象
     * @return java.util.LocalDateTime类型的数据
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 将LocalDateTime转换为Date类型
     *
     * @param localDateTime 本地时间
     * @return Date数据
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        Instant instant = localDateTime.atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * 将LocalDate 转换为Date 时间部分为00:00:00
     *
     * @param localDate 本地时间
     * @return Date类型的数据
     */
    public static Date toDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        Instant instant = localDateTime.atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    /**
     * 将java.util.Date类型转换位java.time.LocalDate类型,转换之后仅剩日期
     *
     * @param date java.util.Date对象
     * @return java.time.LocalDate对象，仅包含日期
     */
    public static LocalDate toLocalDate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * convert date to millis
     *
     * @param localDateTime localDateTime，转换为毫秒数
     * @return the millis of date
     */
    public static long toMillis(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return -1;
        }
        return localDateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    /**
     * LocalDate 00:00:00的毫秒数
     *
     * @param localDate localDate对象
     * @return LocalDate 00:00:00的毫秒数
     */
    public static long toMillis(LocalDate localDate) {
        if (localDate == null) {
            return -1;
        }
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        return toMillis(localDateTime);
    }

    /**
     * convert data to yyyyMMdd pattern string
     *
     * @param localDate date
     * @return yyyyMMdd date string
     */
    public static String toBasicDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return JarkataConstants.EMPTY_STR;
        }
        return localDate.format(BASIC_ISO_DATE);
    }

    /**
     * 转换为ISO标准日期字符串
     *
     * @param dateTime datetime variable
     * @return yyyy-MM-ddTHH:mm:ss
     */
    public static String toIsoDate(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return JarkataConstants.EMPTY_STR;
        }
        return dateTime.format(ISO_DATETIME);
    }

    /**
     * @param dateTime 日期
     * @return yyyy-MM-dd格式的日期
     */
    public static String formatIsoDate(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return JarkataConstants.EMPTY_STR;
        }
        return dateTime.format(DateTimeFormatter.ISO_DATE);
    }

    /**
     * @param dateTime 日期
     * @return yyyyMMdd 格式的日期
     */
    public static String formatBasicDate(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return JarkataConstants.EMPTY_STR;
        }
        return dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}