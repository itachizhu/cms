package org.itachi.cms.snowflake;

import java.net.InetAddress;

/**
 * from: https://github.com/twitter/snowflake
 * Created by itachi on 2017/7/28.
 * User: itachi
 * Date: 2017/7/28
 * Time: 09:01
 */
public final class IdWorker {
    // ==============================Fields===========================================

    /**
     * 机器id所占的位数
     */
    private static final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long dataCenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long maxWorkerId = ~(-1L << workerIdBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    private static IdWorker instance;

    public static IdWorker getInstance() {
        if (instance == null) {
            long workerId = 1L;
            String hostName;
            try {
                InetAddress localHost = InetAddress.getLocalHost();
                hostName = localHost.getHostName();
                if (null != hostName && !hostName.isEmpty()) {
                    StringBuilder hostNo = new StringBuilder();
                    for (int i =0; i < hostName.length(); i++) {
                        if (hostName.charAt(i) >= 48 && hostName.charAt(i) <= 57) {
                            // 取最后一组数字
                            if ((hostNo.length() == 0) || hostName.charAt(i - 1) >= 48 && hostName.charAt(i - 1) <= 57) {
                                hostNo.append(hostName.charAt(i));
                            } else {
                                hostNo = new StringBuilder(String.valueOf(hostName.charAt(i)));
                            }
                        }
                    }
                    if (hostNo.length() > 0) {
                        workerId = Long.parseLong(hostNo.toString()) % maxWorkerId;
                    }
                }
            } catch (Exception e) {
                workerId = 1L;
            }
            instance = new IdWorker(workerId, 0L);
        }
        return instance;
    }

    //==============================Constructors=====================================

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public IdWorker(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        /*
          支持的最大数据标识id，结果是31
        */
        long maxDataCenterId = ~(-1L << dataCenterIdBits);
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    // ==============================Methods==========================================

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内序列
        /*
          序列在id中占的位数
        */
        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            /*
              生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
            */
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //时间戳改变，毫秒内序列重置
            sequence = 0L;
        }
        //上次生成ID的时间截
        lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        /*
          开始时间截 (2015-01-01)
        */
        long twepoch = 1420041600000L;
        /*
          机器ID向左移12位
        */
        /*
          数据标识id向左移17位(12+5)
        */
        long dataCenterIdShift = sequenceBits + workerIdBits;
        /*
          时间截向左移22位(5+5+12)
        */
        long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (dataCenterId << dataCenterIdShift) //
                | (workerId << sequenceBits) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
