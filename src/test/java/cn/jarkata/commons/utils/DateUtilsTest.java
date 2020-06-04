package cn.jarkata.commons.utils;

import cn.jarkata.commons.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

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
    public void testtoLocalDateTimeWithDate() {
        LocalDateTime localDate = DateUtils.toLocalDateTime(new Date());
        System.out.println(localDate);
    }
}