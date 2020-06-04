package cn.jarkata.commons.idgen;

import cn.jarkata.commons.utils.DateUtils;
import org.junit.Test;

import java.time.LocalDateTime;

public class SnowflakeIdGenTest {

    @Test
    public void getId() {

        LocalDateTime baseLocalTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        long millis = DateUtils.toMillis(baseLocalTime);
        SnowflakeIdGen snowflakeIdGen = new SnowflakeIdGen(millis, 1, 365 * 24 * 3600 * 1000L);

        snowflakeIdGen.setCurrentTime(LocalDateTime.of(2020, 11, 20, 12, 59, 59));
        long id5 = snowflakeIdGen.getId();
        System.out.println(id5);

        snowflakeIdGen.setCurrentTime(LocalDateTime.of(2020, 12, 31, 12, 59, 59));
        long id4 = snowflakeIdGen.getId();
        System.out.println(id4);

        snowflakeIdGen.setCurrentTime(LocalDateTime.of(2999, 12, 31, 12, 59, 59));
        long id3 = snowflakeIdGen.getId();
        System.out.println(id3);

        snowflakeIdGen.setCurrentTime(LocalDateTime.of(3999, 12, 31, 12, 59, 59));
        long id2 = snowflakeIdGen.getId();
        System.out.println(id2);

        snowflakeIdGen.setCurrentTime(LocalDateTime.of(4030, 12, 31, 12, 59, 59));
        long id6 = snowflakeIdGen.getId();
        System.out.println(id6);
    }

    @Test
    public void test() {
        LocalDateTime of = LocalDateTime.of(2021, 5, 30, 12, 12, 11);
        System.out.println(of);
        long millis = DateUtils.toMillis(of);
        System.out.println(millis / 1000);
        String binaryString = Long.toBinaryString((millis) << 22);
        System.out.println(binaryString);

    }


    @Test
    public void testWorkId() {
        long id = 0L << 12;
        System.out.println(id);
    }
}