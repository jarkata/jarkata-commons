package cn.jarkata.commons.utils;

import cn.jarkata.commons.Props;
import org.junit.Test;

public class PropsTest {

    @Test
    public void create_SUCCESS() {
        System.out.println(Props.create("test.properties"));
    }

    @Test
    public void createMap_SUCCESS() {
        System.out.println(Props.createMap("test.properties"));
    }

    @Test
    public void create_NOT_FOUND() {
        System.out.println(Props.create("fadfa.txt"));
    }
}