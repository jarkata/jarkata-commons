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
    private final String threadPrefix;
    private final ThreadGroup threadGroup;
    private static final AtomicLong threadCount = new AtomicLong(0);

    private static final AtomicLong POOL_SEQ = new AtomicLong(0);

    public NamedThreadFactory(String threadName) {
        this.threadPrefix = "pool-" + POOL_SEQ.getAndIncrement() + "-" + threadName;
        SecurityManager s = System.getSecurityManager();
        this.threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String threadName = threadPrefix + "-" + threadCount.getAndIncrement();
        Thread thread = new Thread(threadGroup, runnable, threadName, 0);
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        return thread;
    }
}
