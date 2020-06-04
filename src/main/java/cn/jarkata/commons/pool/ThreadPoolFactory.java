package cn.jarkata.commons.pool;

import cn.jarkata.commons.JarkataConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程池工厂类
 */
public class ThreadPoolFactory {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolFactory.class);

    private static final ConcurrentHashMap<String, ThreadPoolExecutor> CACHE = new ConcurrentHashMap<>();

    /**
     * 获取线程池
     *
     * @param poolName 线程池名称
     * @param coreSize 核心线程数
     * @return 线程池
     */
    public static ThreadPoolExecutor getThreadPool(String poolName, int coreSize) {
        return getThreadPool(poolName, coreSize, coreSize);
    }

    /**
     * 线程池
     *
     * @param poolName    线程池名称
     * @param coreSize    核心线程数
     * @param maxCoreSize 最大线程数
     * @return 线程池对象
     */
    public static ThreadPoolExecutor getThreadPool(String poolName, int coreSize, int maxCoreSize) {
        return CACHE.getOrDefault(poolName, init(poolName, coreSize, maxCoreSize,
            JarkataConstants.DEFAULT_THREAD_TASK_TIMEOUT,
            JarkataConstants.DEFAULT_THREAD_POOL_QUEUE,
            JarkataConstants.DEFAULT_THREAD_CLOSE_TIME_WAIT));
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
    public static ThreadPoolExecutor getThreadPool(String poolName, int coreSize, int maxCoreSize, int queueSize) {
        return getThreadPool(poolName, coreSize, maxCoreSize,
            JarkataConstants.DEFAULT_THREAD_TASK_TIMEOUT, queueSize,
            JarkataConstants.DEFAULT_THREAD_CLOSE_TIME_WAIT);
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
    public static ThreadPoolExecutor getThreadPool(String poolName, int coreSize,
                                                   int maxCoreSize, int queueSize, int timeout, int waitTimeouts) {
        return CACHE.getOrDefault(poolName,
            init(poolName, coreSize, maxCoreSize,
                timeout, queueSize, waitTimeouts));
    }

    /**
     * @param threadName  the Name of Thread
     * @param coreSize    核心线程数
     * @param maxSize     最大线程数
     * @param timieout    获取任务等待时间
     * @param queueSize   队列大小
     * @param waitTimeout 关闭线程等待时间
     * @return 线程池对象
     */
    private static ThreadPoolExecutor init(String threadName, int coreSize, int maxSize,
                                           int timieout, int queueSize, long waitTimeout) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            coreSize, maxSize, timieout, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize),
            new NamedThreadFactory(threadName), new ThreadPoolExecutor.CallerRunsPolicy());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (threadPoolExecutor.isShutdown()) {
                logger.warn("thread pool is shutdown");
                return;
            }
            threadPoolExecutor.shutdown();
            try {
                threadPoolExecutor.awaitTermination(waitTimeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error("close threadpool exception", e);
                threadPoolExecutor.shutdownNow();
            }
            logger.info("threadPool={} shutdown finish", threadName);
        }));
        return threadPoolExecutor;
    }


    public static ScheduledThreadPoolExecutor getSchedule(String threadName, int coreSize) {
        return (ScheduledThreadPoolExecutor) CACHE.getOrDefault(threadName, initScheduled(threadName, coreSize));
    }

    /**
     * 初始化定时执行线程池
     *
     * @param threadName 线程池名称
     * @param coreSize   核心线程数
     * @return 定时任务执行线程池
     */
    private static ScheduledThreadPoolExecutor initScheduled(String threadName, int coreSize) {
        ScheduledThreadPoolExecutor threadPoolExecutor =
            new ScheduledThreadPoolExecutor(coreSize, new NamedThreadFactory(threadName));
        threadPoolExecutor.setMaximumPoolSize(coreSize);
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //添加进程结束时的钩子函数
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (threadPoolExecutor.isShutdown()) {
                logger.warn("thread pool is shutdown");
                return;
            }
            threadPoolExecutor.shutdown();
            try {
                threadPoolExecutor.awaitTermination(JarkataConstants.DEFAULT_THREAD_CLOSE_TIME_WAIT, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error("close threadpool exception", e);
                threadPoolExecutor.shutdownNow();
            }
            logger.info("threadPool={} shutdown finish", threadName);
        }));
        return threadPoolExecutor;
    }
}
