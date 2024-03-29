package cn.jarkata.commons.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testTrimJson() {
        String json = StringUtils.trimJson("{\n" + "  \"test\": \"{\\\"test\\\":\\\"rwer\\\"}\"\n" + "}");
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject test = jsonObject.getJSONObject("test");
        Assert.assertNotNull(test);
    }


    @Test
    public void testTrimJsons() {
        String json = StringUtils.trimJson("{\n" + "  \"test\": \"{\\\"test\\\":\\\"rwer\\\"}\",\n" + "  \"test2\": \"[{\\\"test\\\":\\\"rwer\\\"},{\\\"test\\\":\\\"rwer\\\"}]\"\n" + "}");
        JSONObject jsonObject = JSON.parseObject(json);
        Object test = jsonObject.get("test2");
        Assert.assertTrue(test instanceof JSONArray);
    }

    @Test
    public void testLeftPad2() {
        long start = System.currentTimeMillis();
        String leftPad = StringUtils.leftPad("23", 5, "A");
        long dur = System.currentTimeMillis() - start;
        System.out.println(dur + " ：" + leftPad);

    }

    @Test
    public void testLeftPad() {
        long start = System.currentTimeMillis();
        for (int index = 0; index < 1; index++) {
            String leftPad = StringUtils.leftPad("23", 10, "A0");
            Assert.assertNotNull(leftPad);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("耗时：" + dur);

    }

    @Test
    public void trimJson() {
        String blank = StringUtils.replaceBlank("1  2");
        int length = blank.length();
        Assert.assertEquals(length, 3);
        Assert.assertEquals("1 2", blank);
    }

    @Test
    public void defaultIfBlank() {
        String blank = StringUtils.defaultIfBlank("1312", "");
        Assert.assertEquals("1312", blank);
    }

    @Test
    public void defaultIfBlank1() {
        String blank = StringUtils.defaultIfBlank(" ", "123");
        Assert.assertEquals("123", blank);
    }

    @Test
    public void rightPad() {
        String righted = StringUtils.rightPad("A", 3, "B");
        Assert.assertEquals("ABB", righted);
    }

    @Test
    public void trimToNull() {
        String trimmed = StringUtils.trimToNull(null);
        Assert.assertNull(trimmed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void requireNotBlank() {
        StringUtils.requireNotBlank("", "ID不能为空");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireNotBlank() {
        StringUtils.requireNotBlank("", new IllegalArgumentException("ID不能为空"));
    }

    @Test
    public void testRequireNotBlank2() {
        String val = StringUtils.requireNotBlank("1231", new IllegalArgumentException("ID不能为空"));
        Assert.assertEquals("1231", val);
    }

    @Test
    public void testRequireNotBlank3() {
        String val = StringUtils.requireNotBlank("1231", "ID不能为空");
        Assert.assertEquals("1231", val);
    }
}