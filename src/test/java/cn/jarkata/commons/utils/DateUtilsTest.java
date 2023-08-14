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
        Date date = DateUtils.toDate(LocalDate.of(2023, 8, 14));
        Assert.assertNotNull(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatStr = dateFormat.format(date);
        Assert.assertEquals("20230814000000", formatStr);
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
    public void formatIsoDate() {
        LocalDateTime localDate = LocalDateTime.of(2023, 8, 14, 12, 3, 1);
        String basicDate = DateUtils.formatIsoDate(localDate);
        Assert.assertNotNull(basicDate);
        Assert.assertEquals("2023-08-14", basicDate);
    }

    @Test
    public void testFormatIsoDate() {
        LocalDate localDate = LocalDate.of(2023, 8, 14);
        String basicDate = DateUtils.formatIsoDate(localDate);
        Assert.assertNotNull(basicDate);
        Assert.assertEquals("2023-08-14", basicDate);
    }

    @Test
    public void formatBasicDate() {
        LocalDateTime localDate = LocalDateTime.of(2023, 8, 14, 12, 3, 1);
        String basicDate = DateUtils.formatBasicDate(localDate);
        Assert.assertNotNull(basicDate);
        Assert.assertEquals("20230814", basicDate);
    }

    @Test
    public void testTestFormatBasicDate() {
        LocalDate localDate = LocalDate.of(2023, 8, 14);
        String basicDate = DateUtils.formatBasicDate(localDate);
        Assert.assertNotNull(basicDate);
        Assert.assertEquals("20230814", basicDate);
    }

    @Test
    public void testFormatStdIsoDateTime() {
        LocalDateTime localDate = LocalDateTime.of(2023, 8, 14, 12, 3, 1);
        String basicDate = DateUtils.formatStdIsoDateTime(localDate);
        Assert.assertNotNull(basicDate);
        Assert.assertEquals("2023-08-14T12:03:01", basicDate);
    }

    @Test
    public void testFormatIsoDateTime() {
        LocalDateTime localDate = LocalDateTime.of(2023, 8, 14, 12, 3, 1);
        String basicDate = DateUtils.formatIsoDateTime(localDate);
        Assert.assertNotNull(basicDate);
        Assert.assertEquals("20230814120301", basicDate);
    }

    @Test
    public void testTestOfLocalDateTime() {
        LocalDate localDate = LocalDate.of(2023, 8, 14);
        LocalDateTime localDateTime = DateUtils.ofLocalDateTime(localDate);
        System.out.println(localDateTime);
        Assert.assertNotNull(localDateTime);
        LocalDate timeLocalDate = localDateTime.toLocalDate();
        Assert.assertEquals(localDate, timeLocalDate);
        Assert.assertEquals(localDateTime.toLocalTime(), LocalTime.of(0, 0));
    }

    @Test
    public void testTestOfLocalDateTime1() {
        LocalTime localDate = LocalTime.of(12, 8, 14);
        LocalDateTime localDateTime = DateUtils.ofLocalDateTime(localDate);
        System.out.println(localDateTime);
        Assert.assertNotNull(localDateTime);
        Assert.assertEquals(localDateTime.toLocalDate(), LocalDate.now());
        Assert.assertEquals(localDateTime.toLocalTime(), localDate);
    }

    @Test
    public void testTestOfLocalDate() {
        LocalDateTime localDate = LocalDateTime.of(2023, 8, 14, 12, 3, 1);
        LocalDate ofLocalDate = DateUtils.ofLocalDate(localDate);
        Assert.assertNotNull(ofLocalDate);
        Assert.assertEquals(ofLocalDate, LocalDate.of(2023, 8, 14));
    }

    @Test
    public void testTestOfLocalTime() {
        LocalDateTime localDate = LocalDateTime.of(2023, 8, 14, 12, 3, 1);
        LocalTime ofLocalDate = DateUtils.ofLocalTime(localDate);
        Assert.assertNotNull(ofLocalDate);
        Assert.assertEquals(ofLocalDate, LocalTime.of(12, 3, 1));
    }

    @Test
    public void testTestOfInstant() {
        LocalDateTime localDate = LocalDateTime.of(2023, 8, 14, 12, 3, 1);
        Instant instant = DateUtils.ofInstant(localDate);
        Assert.assertNotNull(instant);
        Assert.assertEquals(instant.toString(), "2023-08-14T04:03:01Z");
    }
}