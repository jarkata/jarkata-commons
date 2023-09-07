package cn.jarkata.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 网络工具类
 */
public class NetUtils {
    private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);


    /**
     * @param socketAddress socket地址对象
     * @return 返回仅带有IP地址及端口号的字符串
     */
    public static String toString(SocketAddress socketAddress) {
        if (Objects.isNull(socketAddress)) {
            return "";
        }
        String address = Objects.toString(socketAddress);
        int slashPos = address.indexOf("/");
        if (slashPos >= 0) {
            address = address.substring(slashPos + 1);
        }
        return address;
    }

    /**
     * 获取本地的IP地址
     *
     * @return 本地IP地址，发生异常时，返回127.0.0.1
     */
    public static String getLocalAddress() {
        try {
            return getInet4Address();
        } catch (Exception e) {
            logger.info("Get LocalAddress Exception", e);
            return "127.0.0.1";
        }
    }

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
        } catch (Exception ignored) {
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
        return InetAddress.getLocalHost().getHostAddress();
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