package cn.jarkata.commons.utils;


import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class FileUtilsTest {

    @Test
    public void testGetStream() {
        InputStream stream = FileUtils.getStream("test.json");
        Assert.assertNotNull(stream);
        List<String> lines = FileUtils.readLines(stream);
        System.out.println(lines);

    }
}