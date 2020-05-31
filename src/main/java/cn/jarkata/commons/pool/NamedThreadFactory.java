package cn.jarkata.commons.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 带有命名的线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {
    /**
     * 线程名称
     */
    private String threadNamePrefix;

    private static final AtomicLong threadCount = new AtomicLong(0);

    private static final AtomicLong poolCount = new AtomicLong(0);

    public NamedThreadFactory(String threadName) {
        this.threadNamePrefix = threadName + "-" + poolCount.getAndIncrement();
    }

    @Override
    public Thread newThread(Runnable r) {
        this.threadNamePrefix = threadNamePrefix + "-" + threadCount.getAndIncrement();
        Thread thread = new Thread(threadNamePrefix);
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        return thread;
    }
}
