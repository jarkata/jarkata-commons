package cn.jarkata.commons.idcreator.impl;

import cn.jarkata.commons.idcreator.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SnowflakeIdCreator implements IdCreator {

    private final Logger logger = LoggerFactory.getLogger(SnowflakeIdCreator.class);

    /**
     * 基线时间
     */
    private final long timeEpoch;
    /**
     * 默认时间回拨时间
     */
    private final long baseBackupMills;
    /**
     * workId的长度
     */
    private static final long workIdBits = 10;
    /**
     * 最大workId
     */
    private static final long MAX_WORK_ID = ~(-1L << workIdBits);
    /**
     * 序列的位数
     */
    private static final long sequenceBits = 12;
    /**
     * workId左移位数
     */
    private static final long workIdShift = sequenceBits;
    /**
     * 时间戳左移位数
     */
    private static final long timestampShift = sequenceBits + workIdBits;
    /**
     * 取sequence的低12位
     */
    private static final long sequenceMask = ~(-1L << sequenceBits);

    private static final Random random = new Random();

    private static final AtomicLong fixStepAtomic = new AtomicLong(0);
    private static final AtomicLong lastTimestampAtomic = new AtomicLong(0);
    private static final AtomicLong sequenceAtomic = new AtomicLong(0);

    private LocalDateTime currentTime;

    // 2000-01-01 00:00 时间的毫秒值
    private static final long DEFAULT_TIME_EPOCH = 946656000000L;
    // 1年时间的毫秒值
    private static final long DEFAULT_BASE_BACKUP_MILLS = 365 * 24 * 3600 * 1000L;

    private final Object lock = new Object();

    public SnowflakeIdCreator() {
        this(DEFAULT_TIME_EPOCH, DEFAULT_BASE_BACKUP_MILLS);
    }

    /**
     * @param timeEpoch       基线日期
     * @param baseBackupMills 时钟回拨缓存时间
     */
    public SnowflakeIdCreator(long timeEpoch, long baseBackupMills) {
        // 2000-01-01 00:00 时间的毫秒值
        this.timeEpoch = timeEpoch;
        // 1年时间的毫秒值
        this.baseBackupMills = baseBackupMills;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * 当前的时间
     *
     * @return 返回根据时间戳
     */
    private long currentBaseTime() {
        long baseTimeEpoch = baseBackupMills - fixStepAtomic.get();
        if (baseTimeEpoch <= 0) {
            throw new IllegalArgumentException("time back to long");
        }
        LocalDateTime currentTime = getCurrentTime();
        if (Objects.isNull(currentTime)) {
            currentTime = LocalDateTime.now();
        }
        return currentTime.minusSeconds(baseTimeEpoch / 1000).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 下一个时间戳
     *
     * @param lastTimestamp 最后访问的时间
     * @return 下一个时间戳的毫秒数
     */
    private long tilNextTimestamp(long lastTimestamp) {
        long timestamp = currentBaseTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentBaseTime();
        }
        return timestamp;
    }

    /**
     * 获取计算之后的值
     *
     * @return 返回19位的long值
     */
    @Override
    public long createId(long workId) {
        logger.debug("workId={}", workId);
        if (workId > MAX_WORK_ID) {
            throw new IllegalArgumentException("workId more than " + MAX_WORK_ID);
        }
        long lastTimestamp = lastTimestampAtomic.get();
        long timestamp = currentBaseTime();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            //毫秒级的时间倒退，直接等待
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                } catch (Exception ex) {
                    logger.error("wait={} 异常", offset);
                }
            } else {
                //超过5ms的时间倒退，则直接修复
                fixStepAtomic.set(offset);
            }
            lastTimestamp = lastTimestampAtomic.get();
            timestamp = currentBaseTime();
            if (timestamp < lastTimestamp) {
                fixStepAtomic.set(lastTimestamp - timestamp);
                timestamp = tilNextTimestamp(lastTimestamp);
            }
        }
        lastTimestamp = lastTimestampAtomic.get();
        //最后的时间戳与当前时间相等
        if (lastTimestamp == timestamp) {
            sequenceAtomic.set(sequenceAtomic.incrementAndGet() & sequenceMask);
            if (sequenceAtomic.get() == 0) {
                long sequence = random.nextInt(100);
                sequenceAtomic.set(sequence);
                timestamp = tilNextTimestamp(lastTimestamp);
            }
        } else {
            long sequence = random.nextInt(199);
            sequenceAtomic.set(sequence);
        }
        lastTimestampAtomic.set(timestamp);
        return (timestamp - timeEpoch) << timestampShift | workId << workIdShift | sequenceAtomic.get();
    }
}
