package cn.jarkata.commons.utils;

import org.junit.Test;

public class NetUtilsTest {

    @Test
    public void testGetIp() throws Exception {
        String address = NetUtils.getInet4Address();
        System.out.println(address);

    }
}