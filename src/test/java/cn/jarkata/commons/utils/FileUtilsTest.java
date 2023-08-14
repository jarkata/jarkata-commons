package cn.jarkata.commons.utils;


import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class FileUtilsTest {

    @Test
    public void testGetStream() {
        InputStream stream = FileUtils.getStream("test.json");
        Assert.assertNotNull(stream);
        List<String> lines = FileUtils.readLines(stream);
        System.out.println(lines);

    }


    @Test
    public void testGetFile() throws IOException {
        File stream = FileUtils.getFile("test.json");
        Assert.assertNotNull(stream);
        System.out.println(stream);
        List<String> readFile = FileUtils.readFile(stream);
        System.out.println(readFile);

    }

    @Test
    public void testTrimPrefix() {
        String trimPrefix = FileUtils.trimPrefix("C:/d/test.txt", "C:/");
        Assert.assertEquals("d/test.txt", trimPrefix);
    }
}