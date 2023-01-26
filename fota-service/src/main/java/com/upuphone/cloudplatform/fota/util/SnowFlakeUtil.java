package com.upuphone.cloudplatform.fota.util;


import lombok.extern.slf4j.Slf4j;

/**
 * @author guangzheng.ding
 * @date 2021/11/30 10:22
 */
@Slf4j
public final class SnowFlakeUtil {

    private final long id;
    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间
     */
    private final long epoch = 1587433423721L;
    /**
     * 机器标识位数
     */
    private final long workerIdBits = 10L;
    /**
     * 机器ID最大值: 1023
     */
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;
    /**
     * 0，并发控制
     */
    private long sequence = 0L;
    /**
     * 毫秒内自增位
     */
    private final long sequenceBits = 12L;

    /**
     * 12
     */
    private final long workerIdShift = this.sequenceBits;
    /**
     * 22
     */
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;
    /**
     * 4095,111111111111,12位
     */
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;
    /**
     * 记录产生时间毫秒数，判断是否是同1毫秒
     */
    private long lastTimestamp = -1L;

    /**
     * 传入机器id
     *
     * @param id
     */
    private SnowFlakeUtil(long id) {
        if (id > this.maxWorkerId || id < 0) {
            throw new IllegalArgumentException(String.format("机器id不能大于%d或小于0", this.maxWorkerId));
        }
        this.id = id;
    }

    public synchronized long nextId() {
        // 获取当前时间毫秒数
        long timestamp = timeGen();
        if (this.lastTimestamp == timestamp) {
            //如果上一个timestamp与新产生的相等，则sequence加一(最大4095)
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                // 超过最大值进行按位与，结果为0，也就是当这一毫秒序号超过最大值，就会循环等待下一毫秒
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }

        // 如果时间回退，则报错或者返回-1，调用端进行判断
        if (timestamp < this.lastTimestamp) {
            log.error(String.format("时钟回退，拒绝 %d 毫秒内生成雪花id", (this.lastTimestamp - timestamp)));
            return -1;
        }

        this.lastTimestamp = timestamp;
        // 当前时间-初始时间，然后左移timestampLeftShift。
        // 将机器id左移workerIdShift
        // | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
        return timestamp - this.epoch << this.timestampLeftShift | this.id << this.workerIdShift | this.sequence;
    }

    /**
     * 如果说几十年后id重复了，把机器id加1，再用几十年
     */
    private static SnowFlakeUtil flowIdWorker = new SnowFlakeUtil(1);


    public static String getSnowFlakeId() {
        return flowIdWorker.nextId() + "";
    }

    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }

}
