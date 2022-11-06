package cn.jarkata.commons.utils;

import java.net.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * 网络工具类
 */
public class NetUtils {


    /**
     * 获取当前节点的IP4地址
     *
     * @return IP4地址
     * @throws Exception 网络异常
     */
    public static String getInet4Address() throws Exception {
        List<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = getValidNetworkInterfaces();
        } catch (SocketException ignored) {
        }
        if (networkInterfaces == null) {
            return null;
        }
        for (NetworkInterface networkInterface : networkInterfaces) {
            if (networkInterface.isLoopback()) {
                continue;
            }
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress == null || inetAddress.isLoopbackAddress()) {
                    continue;
                }
                if (!inetAddress.isSiteLocalAddress()) {
                    continue;
                }
                if (inetAddress instanceof Inet4Address) {
                    return inetAddress.getHostAddress();
                }
            }
        }
        return InetAddress.getLocalHost()
                .getHostAddress();
    }

    /**
     * Get the valid {@link NetworkInterface network interfaces}
     *
     * @return non-null
     * @throws SocketException SocketException if an I/O error occurs.
     * @since 2.7.6
     */
    private static List<NetworkInterface> getValidNetworkInterfaces() throws SocketException {
        List<NetworkInterface> validNetworkInterfaces = new LinkedList<>();

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (ignoreNetworkInterface(networkInterface)) { // ignore
                continue;
            }
            validNetworkInterfaces.add(networkInterface);
        }
        return validNetworkInterfaces;
    }

    /**
     * @param networkInterface {@link NetworkInterface}
     * @return if the specified {@link NetworkInterface} should be ignored, return <code>true</code>
     * @throws SocketException SocketException if an I/O error occurs.
     * @since 2.7.6
     */
    private static boolean ignoreNetworkInterface(NetworkInterface networkInterface) throws SocketException {
        return networkInterface == null || networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp();
    }


}