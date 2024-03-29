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
        for (int index = 0; index < 100000000; index++) {
            String leftPad = StringUtils.leftPad("23", 10, "A0");
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("耗时：" + dur);

    }

    @Test
    public void trimJson() {
        System.out.println(StringUtils.trimJson("1  2").length());
    }
}