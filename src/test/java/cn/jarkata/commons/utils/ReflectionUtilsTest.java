package cn.jarkata.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReflectionUtilsTest {

    static class Card {
        private static final String str = "123";

        public String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @Test
    public void getAllField() {
        long start = System.currentTimeMillis();
        try {
            for (int index = 0; index < 10000000; index++) {
                Field[] allField = ReflectionUtils.getAllField(Card.class);
                Assert.assertEquals(2, allField.length);
            }
        } finally {
            long dur = System.currentTimeMillis() - start;
            System.out.println(dur);
        }
    }

    @Test
    public void getAllField_Object() {
        Card card = new Card();
        Field[] allField = ReflectionUtils.getAllField(card);
        Assert.assertEquals(2, allField.length);
    }

    @Test
    public void getAllField_Class() {
        List<Field> fieldList = ReflectionUtils.getFieldList(Card.class);
        Assert.assertEquals(2, fieldList.size());
    }

    @Test
    public void getFieldValue_Str() {
        long start = System.currentTimeMillis();
        try {
            for (int index = 0; index < 10000000; index++) {
                Card card = new Card();
                Object fieldValue = ReflectionUtils.getFieldValue(card, "str");
                Objects.requireNonNull(fieldValue);
                Assert.assertEquals(fieldValue, "123");
            }
        } finally {
            long dur = System.currentTimeMillis() - start;
            System.out.println(dur);
        }

    }

    @Test
    public void testObjectMap() {
        Card card = new Card();
        card.setTitle("test");
        Map<String, Object> objectMap = ReflectionUtils.toObjectMap(card);
        Object title = objectMap.get("title");
        Assert.assertEquals(title, "test");
    }

    @Test
    public void getDeclaredField() throws NoSuchFieldException {
        Field declaredField = ReflectionUtils.getDeclaredField(Card.class, "title");
        Objects.requireNonNull(declaredField);
        Assert.assertEquals(declaredField.getName(), "title");
    }

    @Test
    public void getFieldValue() {
        Card card = new Card();
        card.setTitle("test");
        Object title = ReflectionUtils.getFieldValue(card, "title");
        Assert.assertEquals(title, card.getTitle());
    }
}