package cn.jarkata.commons.concurrent;

import org.junit.Test;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadPoolFactoryTest {

    @Test
    public void getSchedule() {
        ScheduledThreadPoolExecutor schedule = ThreadPoolFactory.newSchedule("test", 2);
        int corePoolSize = schedule.getCorePoolSize();
        System.out.println(corePoolSize);
        int maximumPoolSize = schedule.getMaximumPoolSize();
        System.out.print(maximumPoolSize);
        RejectedExecutionHandler rejectedExecutionHandler = schedule.getRejectedExecutionHandler();
        System.out.println(rejectedExecutionHandler);
    }
}