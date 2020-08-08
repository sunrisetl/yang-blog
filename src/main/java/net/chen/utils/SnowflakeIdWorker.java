package net.chen.utils;

/**
 * Created by Chen
 * 2020/8/2 20:12
 */
public class SnowflakeIdWorker {
    /** 开始时间*/
    private final long twepoch = 1420041600000L;
    /** 机器Id 所占位数*/
    private final long workerIdBits = 5L;
    /** 数据标识id 所占的位数*/
    private final long datacenterIdBits = 5L;
    /** 支持最大机器id 结果是31 */
    private final long maxWorkId = -1L ^ (-1L << workerIdBits);
    /** 支持最大数据标识id 结果是31*/
    private final long maxDatacenterId = -1L ^(-1L << datacenterIdBits);
    /** 序列在id中占的位数*/
    private final long sequenceBits = 12L;
    /** 机器ID 向左移 12位*/
    private final long workerIdShift = sequenceBits;
    /** 数据标识id 向左移17 位*/
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /** 时间截向左移 22位*/
    private final long timestampleLeftShift = sequenceBits+workerIdBits+datacenterIdBits;
    /** 生成序列的掩码*/
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    /** 工作机器id*/
    private long workerId ;
    /** 数据中心id*/
    private long datacenterId;
    /** 毫秒序列*/
    private long sequence = 0L;
    /** 上次生成Id 的时间截 */
    private long lastTimestamp = -1L;
    /** 构造函数*/
    public SnowflakeIdWorker(long workerId,long datacenterId){
        if (workerId > maxWorkId || workerId < 0){
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",maxWorkId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0){
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    /** 获得下个id*/
    public synchronized long nextId(){
        long timeStamp = timeGen();
        // 如果当前时间小于上一次ID 生成的时间截，说明系统时钟回退过这个时候应当抛出异常
        if (timeStamp < lastTimestamp){
            String.format("Clock moved backwards.   Refusing to generate id for %d milliseconds",lastTimestamp - timeStamp);
        }
        if (lastTimestamp == timeStamp){
            sequence = (sequence+1) & sequenceMask;
            //毫秒溢出
            if (sequence == 0){
                ///阻塞下一秒,获得新的时间截
                timeStamp = tilNextMills(lastTimestamp);
            }
        }
        else {
            sequence = 0L;
        }
        //上次生成id 的时间
        lastTimestamp = timeStamp;
        //移位并通过运算拼到一起组成64位Id
        return ((timeStamp - twepoch) << timestampleLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;

    }

            /** 阻塞到下一秒获得新的时间截*/

        protected long tilNextMills(long lastTimestamp) {
            long timeStamp = timeGen();
            while (timeStamp <= lastTimestamp){
                timeStamp = timeGen();
            }
            return timeStamp;

            /**
             * 返回以毫秒位单位的当前时间
             * */
    }
        protected  long timeGen(){
                return System.currentTimeMillis();
        }


}
