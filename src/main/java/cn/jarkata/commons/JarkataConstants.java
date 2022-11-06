package cn.jarkata.commons;

/**
 * 常量
 */
public class JarkataConstants {

    /**
     * 默认线程池队列大小
     */
    public static final int DEFAULT_THREAD_POOL_QUEUE = 5000;

    /**
     * 默认线程池关闭等待时间 10s
     */
    public static final int DEFAULT_THREAD_CLOSE_TIME_WAIT = 10000;
    /**
     * 最大线程数超过核心线程数的时，超过的部分线程数等待的最大时间
     */
    public static final int DEFAULT_THREAD_TASK_TIMEOUT = 10;

    public static final String LOCALHOST_KEY = "localhost";

    public static final String ANYHOST_VALUE = "0.0.0.0";

    /**
     * 默认空字符串
     */
    public static final String EMPTY_STR = "";
}