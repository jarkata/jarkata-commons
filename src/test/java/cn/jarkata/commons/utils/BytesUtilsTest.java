package cn.jarkata.commons.utils;

import org.junit.Test;

public class BytesUtilsTest {

    @Test
    public void toBinaryString() {
        byte[] dist = new byte[8];
        dist[0] = (64 >> 3);
        System.out.println(BytesUtils.toBinaryString(dist));
    }
}