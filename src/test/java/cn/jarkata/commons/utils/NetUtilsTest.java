package cn.jarkata.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import java.net.InetSocketAddress;

public class NetUtilsTest {

    @Test
    public void testGetIp() throws Exception {
        String address = NetUtils.getInet4Address();
        System.out.println(address);

    }

    @Test
    public void testToString() {
        String socketAddress = NetUtils.toString(new InetSocketAddress(10000));
        System.out.println(socketAddress);
        Assert.assertEquals(socketAddress, "0.0.0.0:10000");
    }

    @Test
    public void getLocalAddress() {
        System.out.println(NetUtils.getLocalAddress());
    }
}