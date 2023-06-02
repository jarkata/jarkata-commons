package cn.jarkata.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
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
        Field[] allField = ReflectionUtils.getAllField(Card.class);
        Assert.assertEquals(2, allField.length);
    }

    @Test
    public void getAllField_Object() {
        Card card = new Card();
        Field[] allField = ReflectionUtils.getAllField(card);
        Assert.assertEquals(2, allField.length);
    }

    @Test
    public void getFieldValue_Str() {
        Card card = new Card();
        Object fieldValue = ReflectionUtils.getFieldValue(card, "str");
        Objects.requireNonNull(fieldValue);
        Assert.assertEquals(fieldValue, "123");
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