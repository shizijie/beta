package com.shizijie.beta.redis.service.impl;

import com.shizijie.beta.redis.service.RedisService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author shizijie
 * @version 2018-11-02 下午10:12
 */
@SuppressWarnings("unused,unchecked")
public class RedisServiceImpl implements RedisService {
    @Getter
    @Setter
    private RedisTemplate redisTemplate;

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Boolean hashHasKey(String hashTable, String hashKey) {
        return redisTemplate.opsForHash().hasKey(hashTable,hashKey);
    }

    @Override
    public Object hashGet(String hashTable, String hashKey) {
        return redisTemplate.opsForHash().get(hashTable,hashKey);
    }

    @Override
    public Map<Object, Object> hashGetAll(String hashTable) {
        return redisTemplate.opsForHash().entries(hashTable);
    }

    @Override
    public Long hashIncr(String hashTable, String hashKey, Long num) {
        return redisTemplate.opsForHash().increment(hashTable,hashKey,num);
    }

    @Override
    public Double hashIncr(String hashTable, String hashKey, Double num) {
        return redisTemplate.opsForHash().increment(hashTable,hashKey,num);
    }

    @Override
    public void hashSet(String hashTable, String hashKey, Object value) {
        redisTemplate.opsForHash().put(hashTable,hashKey,value);
    }

    @Override
    public Set<String> hashGetAllHashKey(String hashTable) {
        return redisTemplate.opsForHash().keys(hashTable);
    }

    @Override
    public Long hashGetHashMapSize(String hashTable) {
        return redisTemplate.opsForHash().size(hashTable);
    }

    @Override
    public List<Object> hashGetAllHashKeyValue(String hashTable) {
        return redisTemplate.opsForHash().values(hashTable);
    }

    @Override
    public Long hashDelete(String hashTable, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(hashTable,hashKeys);
    }

    @Override
    public void rPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key,value);
    }

    @Override
    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key,value);
    }

    @Override
    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public Long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public List<Object> listRange(String key, Long start, Long end) {
        return redisTemplate.opsForList().range(key,start,end);
    }

    @Override
    public Long listRemove(String key, Long count, Object value) {
        return redisTemplate.opsForList().remove(key,count,value);
    }

    @Override
    public Object listGetIndex(String key, Long index) {
        return redisTemplate.opsForList().index(key,index);
    }

    @Override
    public void listSetIndex(String key, Long index, Object value) {
        redisTemplate.opsForList().set(key,index,value);
    }

    @Override
    public void listTrimByRange(String key, Long start, Long end) {
        redisTemplate.opsForList().trim(key,start,end);
    }

    @Override
    public Long sSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key,values);
    }

    @Override
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Boolean sHashKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key,value);
    }

    @Override
    public boolean zSet(String key, Object value, Double score) {
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    @Override
    public boolean zSet(String key, Object value, Long score) {
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    @Override
    public Set<Object> zGet(String key) {
        return redisTemplate.opsForZSet().range(key,0,-1);
    }

    @Override
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key,value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String subString(String key, Long start, Long end) {
        return redisTemplate.opsForValue().get(key,start,end);
    }

    @Override
    public Long incr(String key, Long num) {
        return redisTemplate.opsForValue().increment(key,num);
    }

    @Override
    public Double incr(String key, Double num) {
        return redisTemplate.opsForValue().increment(key,num);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public Object getSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key,value);
    }

    @Override
    public void set(String key, Object value, Long timeout) {
        redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean lock(String key) {
        return lock(key,null);
    }

    @Override
    public boolean lock(String key, Long expireTimeValue) {
        String lock=LOCK_PREFIX+key;
        if(expireTimeValue==null||expireTimeValue<=0){
            expireTimeValue=LOCK_EXPIRE;
        }
        final Long finalExpireTimeValue=expireTimeValue*1000;
        return (Boolean)redisTemplate.execute((RedisCallback) connection->{
            long expireAt=System.currentTimeMillis()+finalExpireTimeValue+1;
            Boolean success=connection.setNX(lock.getBytes(),String.valueOf(expireAt).getBytes());
            if(success){
                return true;
            }else{
                byte[] value=connection.get(lock.getBytes());
                if(Objects.nonNull(value)&&value.length>0){
                    long expireTime=Long.parseLong(new String(value));
                    if(expireTime<System.currentTimeMillis()){
                        byte[] oldValue=connection.getSet(lock.getBytes(),String.valueOf(System.currentTimeMillis()+finalExpireTimeValue).getBytes());
                        return Long.parseLong(new String(oldValue))<System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    @Override
    public boolean isLock(String key) {
        return get(LOCK_PREFIX+key)!=null;
    }

    @Override
    public void unlock(String key) {
        delete(LOCK_PREFIX+key);
    }
}
