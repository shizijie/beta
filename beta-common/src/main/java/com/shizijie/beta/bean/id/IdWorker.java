package com.shizijie.beta.bean.id;

import com.shizijie.beta.bean.port.ServicePort;
import com.shizijie.beta.redis.RedisService;
import com.shizijie.beta.utils.ip.IpUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author shizijie
 * @version 2018-12-09 上午10:11
 */
public class IdWorker {
    /** 开始时间截 */
    private final long twepoch = 1288834974657L;
    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;
    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;
    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;
    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;
    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);


    /** 工作机器ID(0~31) */
    private long workerId;
    /** 数据中心ID(0~31) */
    private long datacenterId;
    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;
    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;


    private RedisService redisService;
    private boolean open=false;
    private String primaryKey;
    //==============================Constructors=====================================
    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
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
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public IdWorker(RedisService redisService,String projectName,boolean open,String key){
        long workHash=(int)Math.pow(2,workerIdBits);
        long dataHash=(int)Math.pow(2,datacenterIdBits);
        if(open){
            this.redisService=redisService;
            this.primaryKey=IdWorker.class.getName()+"_"+key;
            if(!redisService.hasList(primaryKey)){
                if(redisService.lock(key)){
                    long start=System.currentTimeMillis();
                    for(int i=0;i<workHash;i++){
                        for(int j=0;j<dataHash;j++){
                            redisService.lPush(primaryKey,i+","+j+","+start);
                        }
                    }
                    redisService.unlock(key);
                }
            }

            String[] arr=getValueByPrimary(primaryKey);
            this.workerId=Long.parseLong(arr[0]);
            this.datacenterId=Long.parseLong(arr[1]);
            this.open=true;
        }else{
            this.workerId=Math.abs(projectName.hashCode())%workHash;
            String ip=IpUtils.getLocalIpByNetcard();
            String address=ip+":"+ ServicePort.getPort();
            this.datacenterId=Math.abs(address.hashCode())%dataHash;
        }
    }

    public String[] getValueByPrimary(String primaryKey){
        Object value=redisService.rPop(primaryKey);
        if(value==null|| StringUtils.isBlank(value.toString())){
            throw new IllegalArgumentException("(workerId+datacenterId).size is max");
        }
        String[] arr=value.toString().split(",");
        if(System.currentTimeMillis()<Long.parseLong(arr[2])){
            redisService.lPush(primaryKey,value.toString());
            getValueByPrimary(primaryKey);
        }
        return arr;
    }

    public void destory(){
        if(open){
            redisService.lPush(primaryKey,workerId+","+datacenterId+","+System.currentTimeMillis());
        }
    }

}
