package cn.jarkata.commons;

import org.junit.Test;

import java.util.Map;

public class MapsTest {

    @Test(expected = IllegalArgumentException.class)
    public void toMap() {
        Map<String, Object> map = Maps.toMap("24234");
        System.out.println(map);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toMap_Integer() {
        Map<String, Object> map = Maps.toMap(342423);
        System.out.println(map);
    }
}