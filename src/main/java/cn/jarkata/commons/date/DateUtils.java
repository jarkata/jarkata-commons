package cn.jarkata.commons.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Date Utils
 */
public class DateUtils {

    /**
     * ISO_DATE_TIME
     */
    private static final DateTimeFormatter BASIC_ISO_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final DateTimeFormatter SHORT_ISO_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

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
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
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
     * 转换位LocalDate
     *
     * @param timestamp 时间戳
     * @return 返回localdate对象
     */
    public static LocalDate toLocalDate(long timestamp) {
        if (timestamp <= 0) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
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
     * @param localDateTime localdate
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
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        return toMillis(localDateTime);
    }

    /**
     * convert data to yyyyMMdd pattern string
     *
     * @param localDate date
     * @return yyyyMMdd date string
     */
    public static String toShortDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return "";
        }
        return localDate.format(SHORT_ISO_DATE);
    }

    /**
     * @param dateTime datetime variable
     * @return yyyy-MM-ddTHH:mm:ss
     */
    public static String toISODate(LocalDateTime dateTime) {
        return dateTime.format(BASIC_ISO_DATETIME);
    }
}
