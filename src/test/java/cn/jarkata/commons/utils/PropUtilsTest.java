package cn.jarkata.commons.utils;

import org.junit.Test;

public class PropUtilsTest {

    @Test
    public void create_SUCCESS() {
        System.out.println(PropUtils.create("test.properties"));
    }

    @Test
    public void createMap_SUCCESS() {
        System.out.println(PropUtils.createMap("test.properties"));
    }

    @Test
    public void create_NOT_FOUND() {
        System.out.println(PropUtils.create("fadfa.txt"));
    }
}