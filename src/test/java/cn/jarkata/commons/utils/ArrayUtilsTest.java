package cn.jarkata.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ArrayUtilsTest {

    @Test
    public void asList() {
        List<String> list = ArrayUtils.asList("123");
        String val = list.get(0);
        Assert.assertEquals(val, "123");
    }
}