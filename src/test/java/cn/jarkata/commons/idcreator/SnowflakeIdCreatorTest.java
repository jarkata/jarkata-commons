package cn.jarkata.commons.idcreator;

import cn.jarkata.commons.idcreator.impl.SnowflakeIdCreator;
import cn.jarkata.commons.utils.DateUtils;
import org.junit.Test;

import java.time.LocalDateTime;

public class SnowflakeIdCreatorTest {

    @Test
    public void getId() {

        LocalDateTime baseLocalTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        long millis = DateUtils.toMillis(baseLocalTime);
        SnowflakeIdCreator snowflakeIdCreator = new SnowflakeIdCreator();

        snowflakeIdCreator.setCurrentTime(LocalDateTime.of(2020, 11, 20, 12, 59, 59));
        long id5 = snowflakeIdCreator.createId(2);
        System.out.println(id5);

        snowflakeIdCreator.setCurrentTime(LocalDateTime.of(2020, 12, 31, 12, 59, 59));
        long id4 = snowflakeIdCreator.createId(1);
        System.out.println(id4);

        snowflakeIdCreator.setCurrentTime(LocalDateTime.of(2999, 12, 31, 12, 59, 59));
        long id3 = snowflakeIdCreator.createId(2);
        System.out.println(id3);

        snowflakeIdCreator.setCurrentTime(LocalDateTime.of(3999, 12, 31, 12, 59, 59));
        long id2 = snowflakeIdCreator.createId(2);
        System.out.println(id2);

        snowflakeIdCreator.setCurrentTime(LocalDateTime.of(4030, 12, 31, 12, 59, 59));
        long id6 = snowflakeIdCreator.createId(2);
        System.out.println(id6);
    }

    @Test
    public void testCreateId() {
        String id = IdFactory.createId(1, 27);
        System.out.println(id);
        long start = System.currentTimeMillis();

        String id2 = IdFactory.createId(1, 31);

        long dur = System.currentTimeMillis() - start;

        System.out.println(dur);
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