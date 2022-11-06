package cn.jarkata.commons.utils;


import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class StreamUtilsTest {

    @Test
    public void testGetStream() {
        InputStream stream = StreamUtils.getStream("test.json");
        Assert.assertNotNull(stream);
        List<String> lines = StreamUtils.readLines(stream);
        System.out.println(lines);

    }
}