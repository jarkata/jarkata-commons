package cn.jarkata.commons;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DescartsUtilsTest {

    @Test
    public void descarts() {
        DescartsUtils descartsUtils = new DescartsUtils();
        descartsUtils.descarts(Arrays.asList("1", "2"), "k1")
                .descarts(Arrays.asList("A", "B", "C"), "k2")
                .descarts(Arrays.asList("测试1", "测试二", "D"), "key3")
                .descarts(Arrays.asList("测试1", "测试二"), "key4");
        List<Map<String, String>> dataMapList = descartsUtils.getDataMapList();
        System.out.println(dataMapList);
        System.out.println(dataMapList.size());
    }
}