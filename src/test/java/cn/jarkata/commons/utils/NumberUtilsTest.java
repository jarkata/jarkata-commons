package cn.jarkata.commons.utils;

import org.junit.Test;

import java.math.BigDecimal;

public class NumberUtilsTest {

    @Test
    public void toDecimal() {
        BigDecimal decimal = NumberUtils.toDecimal("1.01", BigDecimal.ZERO);
        System.out.println(decimal);
    }

    @Test
    public void toLong() {
        Long decimal = NumberUtils.toLong("1.01", 1L);
        System.out.println(decimal);
    }

    @Test
    public void toInt() {
        Integer anInt = NumberUtils.toInt("32", 2);
        System.out.println(anInt);
    }

    @Test
    public void toFloat() {
        Float aFloat = NumberUtils.toFloat("32.2", 3.1F);
        System.out.println(aFloat);
    }

    @Test
    public void toDouble() {
        Double aDouble = NumberUtils.toDouble("324", 1.01);
        System.out.println(aDouble);
    }
}