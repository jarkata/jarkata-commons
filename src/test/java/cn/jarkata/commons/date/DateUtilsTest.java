package cn.jarkata.commons.date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void toLocalDateTime() {
        LocalDateTime dateTime = DateUtils.toLocalDateTime(System.currentTimeMillis());
        System.out.println(dateTime);
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