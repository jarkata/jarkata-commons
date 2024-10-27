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
     * yyyy-MM-dd'T'HH:mm:ss
     */
    public static final DateTimeFormatter ISO_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static final DateTimeFormatter ISO_DATETIME4 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter ISO_DATETIME1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormatter ISO_DATETIME3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * yyyyMMddHHmmss
     */
    public static final DateTimeFormatter ISO_DATETIME2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /**
     * yyyyMMdd格式
     */
    public static final DateTimeFormatter BASIC_ISO_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**
     * HH:mm:ss
     */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * HH:mm
     */
    public static final DateTimeFormatter TIME_FORMATTER2 = DateTimeFormatter.ofPattern("HH:mm");
    /**
     * HHmm
     */
    public static final DateTimeFormatter TIME_FORMATTER3 = DateTimeFormatter.ofPattern("HHmm");
    /**
     * HHmmss
     */
    public static final DateTimeFormatter TIME_FORMATTER4 = DateTimeFormatter.ofPattern("HHmmss");

    /**
     * 解析如下格式的字符串为时间部分
     * 1)'HHmm'
     * 2)'HH:mm'
     * 3)'HHmmss'
     * 4)'HH:mm:ss'
     *
     * @param str 时间字符串
     * @return 时间对象
     */
    public static LocalTime parseToTime(String str) {
        str = StringUtils.trimToEmpty(str);
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
        return LocalDateTime.of(localDate, LocalTime.of(0, 0, 0, 0));
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

    /**
     * 仅提取LocalDateTime的LocalTime对象
     *
     * @param dateTime 时间
     * @return LocalTime对象
     */
    public static LocalTime ofLocalTime(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return dateTime.toLocalTime();
    }

    /**
     * LocalDateTime转换为Instant对象
     *
     * @param dateTime 时间
     * @return Instant对象
     */
    public static Instant ofInstant(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    /**
     * Date Utils
     * timestamp的long值转换为LocalDateTime
     *
     * @param timestamp the long of timestamp
     * @return LocalDateTime object
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        if (timestamp <= 0) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * timestamp long值转换位LocalDate
     *
     * @param timestamp 时间戳
     * @return 返回LocalDate对象
     */
    public static LocalDate toLocalDate(long timestamp) {
        if (timestamp <= 0) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 解析如下格式的日期解析为LocalDate
     * 1)'yyyy-MM-dd'
     * 2)'yyyyMMdd'
     *
     * @param localDateStr yyyy-MM-dd格式的日期
     * @return LocalDate对象
     */
    public static LocalDate parseToDate(String localDateStr) {
        localDateStr = StringUtils.trimToEmpty(localDateStr);
        if (StringUtils.isBlank(localDateStr)) {
            return null;
        }
        if (StringUtils.length(localDateStr) == 8) {
            return LocalDate.parse(localDateStr, BASIC_ISO_DATE);
        }
        return LocalDate.parse(localDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 解析如下格式的日期字符串为LocalDateTime对象
     * yyyyMMdd  时间部分为'00:00:00'
     * yyyy-MM-dd 时间部分为'00:00:00'
     * yyyy-MM-dd'T'HH:mm:ss
     * yyyy-MM-dd'T'HH:mm:ss.SSS
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS
     * yyyyMMddHHmmss
     * 2023-12-31T16:00:00.000Z
     *
     * @param localDateTimeStr 见描述
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseToDateTime(String localDateTimeStr) {
        localDateTimeStr = StringUtils.trimToEmpty(localDateTimeStr);
        if (StringUtils.isBlank(localDateTimeStr)) {
            return null;
        }
        //2023-12-31T16:00:00.000Z
        if (localDateTimeStr.endsWith("Z")) {
            int length = StringUtils.length(localDateTimeStr);
            localDateTimeStr = localDateTimeStr.substring(0, length - 1);
        }
        int length = StringUtils.length(localDateTimeStr);
        if (length == 8 || length == 10) {
            LocalDate localDate = parseToDate(localDateTimeStr);
            if (Objects.isNull(localDate)) {
                return null;
            }
            return LocalDateTime.of(localDate, LocalTime.of(0, 0, 0, 0));
        }
        length = StringUtils.length(localDateTimeStr);
        if (length == 23) {
            int indexOf = localDateTimeStr.indexOf("T");
            if (indexOf > 0) {
                return LocalDateTime.parse(localDateTimeStr, ISO_DATETIME4);
            }
            return LocalDateTime.parse(localDateTimeStr, ISO_DATETIME3);
        }

        if (length == 14) {
            return LocalDateTime.parse(localDateTimeStr, ISO_DATETIME2);
        }
        if (length == 19) {
            int index = localDateTimeStr.indexOf("T");
            if (index > 0) {
                return LocalDateTime.parse(localDateTimeStr, ISO_DATETIME);
            }
            return LocalDateTime.parse(localDateTimeStr, ISO_DATETIME1);
        }
        TemporalAccessor temporalAccessor = ISO_DATETIME.parse(localDateTimeStr);
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
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
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
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0, 0));
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
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
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
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
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0, 0));
        return toMillis(localDateTime);
    }

    /**
     * 转换为ISO标准日期字符串
     * yyyy-MM-ddTHH:mm:ss
     * 默认返回为空字符串
     *
     * @param dateTime datetime variable
     * @return yyyy-MM-ddTHH:mm:ss
     */
    public static String formatStdIsoDateTime(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return JarkataConstants.EMPTY_STR;
        }
        return dateTime.format(ISO_DATETIME);
    }

    /**
     * 转换为ISO标准日期字符串
     * yyyy-MM-ddTHH:mm:ss
     * 默认返回为空字符串
     *
     * @param dateTime datetime variable
     * @return yyyy-MM-ddTHH:mm:ss
     */
    public static String formatDateTime(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (Objects.isNull(dateTime)) {
            return JarkataConstants.EMPTY_STR;
        }
        return dateTime.format(formatter);
    }

    /**
     * 转换为'yyyyMMddHHmmss'格式的日期字符串
     *
     * @param dateTime datetime variable
     * @return yyyyMMddHHmmss
     */
    public static String formatIsoDateTime(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return JarkataConstants.EMPTY_STR;
        }
        return dateTime.format(ISO_DATETIME2);
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
     * @return yyyy-MM-dd格式的日期
     */
    public static String formatIsoDate(LocalDate dateTime) {
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

    /**
     * @param localDate 日期
     * @return yyyyMMdd 格式的日期
     */
    public static String formatBasicDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return JarkataConstants.EMPTY_STR;
        }
        return localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}