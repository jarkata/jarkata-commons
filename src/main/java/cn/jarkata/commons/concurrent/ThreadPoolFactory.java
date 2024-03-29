package cn.jarkata.commons.concurrent;

import cn.jarkata.commons.JarkataConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * 线程池工厂类
 */
public class ThreadPoolFactory {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolFactory.class);

    private static final ConcurrentHashMap<String, ThreadPoolExecutor> THREAD_POOL_CACHE = new ConcurrentHashMap<>();

    private static final Object lock = new Object();


    /**
     * 获取线程池
     *
     * @param poolName 线程池名称
     * @param coreSize 核心线程数
     * @return 线程池
     */
    public static ThreadPoolExecutor newThreadPool(String poolName, int coreSize) {
        return newThreadPool(poolName, coreSize, coreSize);
    }

    /**
     * 线程池
     *
     * @param poolName    线程池名称
     * @param coreSize    核心线程数
     * @param maxCoreSize 最大线程数
     * @return 线程池对象
     */
    public static ThreadPoolExecutor newThreadPool(String poolName, int coreSize, int maxCoreSize) {
        ThreadPoolExecutor threadPoolExecutor = THREAD_POOL_CACHE.get(poolName);
        if (Objects.nonNull(threadPoolExecutor)) {
            return threadPoolExecutor;
        }
        synchronized (lock) {
            threadPoolExecutor = THREAD_POOL_CACHE.get(poolName);
            if (Objects.nonNull(threadPoolExecutor)) {
                return threadPoolExecutor;
            }
            threadPoolExecutor = init(poolName, coreSize, maxCoreSize, JarkataConstants.DEFAULT_THREAD_TASK_TIMEOUT, JarkataConstants.DEFAULT_THREAD_POOL_QUEUE, JarkataConstants.DEFAULT_THREAD_CLOSE_TIME_WAIT);
            THREAD_POOL_CACHE.put(poolName, threadPoolExecutor);
        }
        return threadPoolExecutor;
    }

    /**
     * 获取线程池
     *
     * @param poolName    线程池名称
     * @param coreSize    核心线程数
     * @param maxCoreSize 最大线程数
     * @param queueSize   队列数
     * @return 线程池对象
     */
    public static ThreadPoolExecutor newThreadPool(String poolName, int coreSize, int maxCoreSize, int queueSize) {
        return newThreadPool(poolName, coreSize, maxCoreSize, JarkataConstants.DEFAULT_THREAD_TASK_TIMEOUT, queueSize, JarkataConstants.DEFAULT_THREAD_CLOSE_TIME_WAIT);
    }

    /**
     * @param poolName     threadName
     * @param coreSize     thread pool coreSize
     * @param maxCoreSize  thread pool maxThreadSize
     * @param queueSize    the size of thread pool LinkedBlockQueue
     * @param timeout      the time of task poll from queue
     * @param waitTimeouts the time of close thread pool
     * @return thread pool
     */
    public static ThreadPoolExecutor newThreadPool(String poolName, int coreSize, int maxCoreSize, int queueSize, int timeout, int waitTimeouts) {
        ThreadPoolExecutor threadPoolExecutor = THREAD_POOL_CACHE.get(poolName);
        if (Objects.nonNull(threadPoolExecutor)) {
            return threadPoolExecutor;
        }
        synchronized (lock) {
            threadPoolExecutor = THREAD_POOL_CACHE.get(poolName);
            if (Objects.nonNull(threadPoolExecutor)) {
                return threadPoolExecutor;
            }
            threadPoolExecutor = init(poolName, coreSize, maxCoreSize, timeout, queueSize, waitTimeouts);
            THREAD_POOL_CACHE.put(poolName, threadPoolExecutor);
        }
        return threadPoolExecutor;
    }

    /**
     * @param threadName  the Name of Thread
     * @param coreSize    核心线程数
     * @param maxSize     最大线程数
     * @param timeout     获取任务等待时间
     * @param queueSize   队列大小
     * @param waitTimeout 关闭线程等待时间
     * @return 线程池对象
     */
    private static ThreadPoolExecutor init(String threadName, int coreSize, int maxSize, int timeout, int queueSize, long waitTimeout) {
        BlockingQueue<Runnable> blockingQueue;
        //如果队列长度小于等于0，则使用同步队列
        if (queueSize <= 0) {
            blockingQueue = new SynchronousQueue<>();
        } else {
            blockingQueue = new LinkedBlockingQueue<>(queueSize);
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(coreSize, maxSize, timeout, TimeUnit.MILLISECONDS, blockingQueue, new NamedThreadFactory(threadName), new ThreadPoolExecutor.CallerRunsPolicy());
        //添加进程结束时的钩子函数
        registerHook(threadName, threadPoolExecutor, waitTimeout);
        return threadPoolExecutor;
    }

    private static void registerHook(String threadName, ThreadPoolExecutor threadPoolExecutor, long waitTimeout) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (threadPoolExecutor.isShutdown()) {
                logger.warn("thread pool is shutdown");
                return;
            }
            boolean awaitTermination = false;
            try {
                threadPoolExecutor.shutdown();
                awaitTermination = threadPoolExecutor.awaitTermination(waitTimeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error("close thread pool exception", e);
                threadPoolExecutor.shutdownNow();
            }
            logger.info("threadPool={} shutdown finish,{}", threadName, awaitTermination);
        }));
    }

    public static ScheduledThreadPoolExecutor newSchedule(String threadName, int coreSize) {
        ThreadPoolExecutor scheduledThreadPoolExecutor = THREAD_POOL_CACHE.get(threadName);
        if (Objects.nonNull(scheduledThreadPoolExecutor)) {
            return (ScheduledThreadPoolExecutor) scheduledThreadPoolExecutor;
        }
        synchronized (lock) {
            scheduledThreadPoolExecutor = THREAD_POOL_CACHE.get(threadName);
            if (Objects.nonNull(scheduledThreadPoolExecutor)) {
                return (ScheduledThreadPoolExecutor) scheduledThreadPoolExecutor;
            }
            scheduledThreadPoolExecutor = initScheduled(threadName, coreSize);
            THREAD_POOL_CACHE.put(threadName, scheduledThreadPoolExecutor);
        }
        return (ScheduledThreadPoolExecutor) scheduledThreadPoolExecutor;
    }

    /**
     * 初始化定时执行线程池
     *
     * @param threadName 线程池名称
     * @param coreSize   核心线程数
     * @return 定时任务执行线程池
     */
    private static ScheduledThreadPoolExecutor initScheduled(String threadName, int coreSize) {
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(coreSize, new NamedThreadFactory(threadName));
        threadPoolExecutor.setMaximumPoolSize(coreSize);
        threadPoolExecutor.setKeepAliveTime(1L, TimeUnit.SECONDS);
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //添加进程结束时的钩子函数
        registerHook(threadName, threadPoolExecutor, JarkataConstants.DEFAULT_THREAD_CLOSE_TIME_WAIT);
        return threadPoolExecutor;
    }
}
