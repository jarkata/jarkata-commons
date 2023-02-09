package cn.jarkata.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class DateUtilsTest {

    @Test
    public void toLocalDateTime() {
        LocalDateTime dateTime = DateUtils.toLocalDateTime(System.currentTimeMillis());
        System.out.println(dateTime);
    }

    @Test
    public void testParse() {
        LocalDate query = DateUtils.parseToDate("2020-01-01");
        Assert.assertEquals(query, LocalDate.of(2020, 1, 1));
    }

    @Test
    public void testDate() {
        Date toDate = DateUtils.toDate(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(toDate);
        Assert.assertEquals("2022-01-01", format);
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime localDateTime = DateUtils.parseToDateTime("2020-01-01T10:01:01");
        Assert.assertEquals(LocalDateTime.of(2020, 1, 1, 10, 1, 1), localDateTime);
    }

    @Test
    public void toLocalDate() {
        LocalDate localDate = DateUtils.toLocalDate(System.currentTimeMillis());
        System.out.println(localDate);
    }

    @Test
    public void testToMills() {
        long millis = DateUtils.toMillis(LocalDate.now());
        System.out.println(millis);
    }

    @Test
    public void testtoLocalDateWithDate() {
        LocalDate localDate = DateUtils.toLocalDate(new Date());
        System.out.println(localDate);
    }

    @Test
    public void testToLocalDateTimeWithDate() {
        LocalDateTime localDate = DateUtils.toLocalDateTime(new Date());
        System.out.println(localDate);
    }

    @Test
    public void testParseToTime() {
        LocalTime localTime = DateUtils.parseToTime("201111");
        Assert.assertEquals(localTime, LocalTime.of(20, 11, 11));
        LocalTime localTime1 = DateUtils.parseToTime("2011");
        Assert.assertEquals(localTime1, LocalTime.of(20, 11));
        LocalTime localTime2 = DateUtils.parseToTime("20:11:11");
        Assert.assertEquals(localTime2, LocalTime.of(20, 11, 11));
    }

    @Test
    public void testParseToDate() {
        LocalDate localDate = DateUtils.parseToDate("20211101");
        Assert.assertEquals(localDate, LocalDate.of(2021, 11, 1));
        LocalDate localDate1 = DateUtils.parseToDate("2021-11-01");
        Assert.assertEquals(localDate1, LocalDate.of(2021, 11, 1));
    }

    @Test
    public void testParseToDateTime() {
        LocalDateTime dateTime = DateUtils.parseToDateTime("2022-12-11 10:10:11");
        Assert.assertEquals(dateTime, LocalDateTime.of(2022, 12, 11, 10, 10, 11));
        LocalDateTime dateTime1 = DateUtils.parseToDateTime("2022-12-11T10:10:11");
        Assert.assertEquals(dateTime1, LocalDateTime.of(2022, 12, 11, 10, 10, 11));
        LocalDateTime dateTime2 = DateUtils.parseToDateTime("2022-12-11");
        Assert.assertEquals(dateTime2, LocalDateTime.of(2022, 12, 11, 0, 0, 0));
        LocalDateTime dateTime3 = DateUtils.parseToDateTime("20221211");
        Assert.assertEquals(dateTime3, LocalDateTime.of(2022, 12, 11, 0, 0, 0));
    }

    @Test
    public void parseToTime() {
    }

    @Test
    public void ofLocalDateTime() {
    }

    @Test
    public void testOfLocalDateTime() {
    }

    @Test
    public void ofLocalDate() {
    }

    @Test
    public void ofLocalTime() {
    }

    @Test
    public void ofInstant() {
    }

    @Test
    public void testToLocalDateTime() {
    }

    @Test
    public void testToLocalDate() {
    }

    @Test
    public void parseToDate() {
    }

    @Test
    public void parseToDateTime() {
    }

    @Test
    public void testToLocalDateTime1() {
    }

    @Test
    public void toDate() {
    }

    @Test
    public void testToDate() {
    }

    @Test
    public void testToLocalDate1() {
    }

    @Test
    public void toMillis() {
    }

    @Test
    public void testToMillis() {
    }

    @Test
    public void toBasicDate() {
    }

    @Test
    public void toIsoDate() {
    }

    @Test
    public void formatIsoDate() {
    }

    @Test
    public void formatBasicDate() {
    }
}