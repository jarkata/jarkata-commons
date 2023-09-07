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

    @Test
    public void trimPath() {
        String path = FileUtils.trimPath("//fsadf//afsdf");
        Assert.assertEquals(path, "/fsadf/afsdf");
    }

    @Test
    public void trimSuffix() {
        String path = FileUtils.trimSuffix("/fsadf/afsdf.txt", ".txt");
        Assert.assertEquals(path, "/fsadf/afsdf");
    }

    @Test
    public void trimPrefix() {
        String path = FileUtils.trimPrefix("/fsadf/afsdf.txt", "/fsadf/");
        Assert.assertEquals(path, "afsdf.txt");
    }

    @Test
    public void toURL() {
        List<URL> list = FileUtils.toURL(new File("test.properties"));
        Assert.assertNotNull(list);
        System.out.println(list);
    }


    @Test
    public void toInputStream() {
        InputStream stream = FileUtils.toInputStream("23434");
        Assert.assertNotNull(stream);
    }

    @Test
    public void toFiles() {
        List<URL> list = FileUtils.toURL(new File("test.properties"));
        Assert.assertNotNull(list);
        List<File> fileList = FileUtils.toFiles(list.stream().map(URL::getFile).toArray(String[]::new));
        System.out.println(fileList);
        Assert.assertNotNull(fileList);
    }
}