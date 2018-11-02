package com.shizijie.beta.redis.service.impl;

import com.shizijie.beta.redis.service.RedisService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisServiceImpl implements RedisService {
    @Override
    public void delete(String key) {

    }

    @Override
    public Boolean hashHasKey(String hashTable, String hashKey) {
        return null;
    }

    @Override
    public Object hashGet(String hashTable, String hashKey) {
        return null;
    }

    @Override
    public Map<Object, Object> hashGetAll(String hashTable) {
        return null;
    }

    @Override
    public Long hashIncr(String hashTable, String hashKey, Long num) {
        return null;
    }

    @Override
    public Double hashIncr(String hashTable, String hashKey, Double num) {
        return null;
    }

    @Override
    public void hashSet(String hashTable, String hashKey, Object value) {

    }

    @Override
    public Set<String> hashGetAllHashKey(String hashTable) {
        return null;
    }

    @Override
    public Long hashGetHashMapSize(String hashTable) {
        return null;
    }

    @Override
    public List<Object> hashGetAllHashKeyValue(String hashTable) {
        return null;
    }

    @Override
    public Long hashDelete(String hashTable, Object... hashKeys) {
        return null;
    }

    @Override
    public void rPush(String key, Object value) {

    }

    @Override
    public Object rPop(String key) {
        return null;
    }

    @Override
    public void lPush(String key, Object value) {

    }

    @Override
    public Object lPop(String key) {
        return null;
    }

    @Override
    public Long listSize(String key) {
        return null;
    }

    @Override
    public List<Object> listRange(String key, Long start, Long end) {
        return null;
    }

    @Override
    public Long listRemove(String key, Long count, Object value) {
        return null;
    }

    @Override
    public Object listGetIndex(String key, Long index) {
        return null;
    }

    @Override
    public void listSetIndex(String key, Long index, Object value) {

    }

    @Override
    public void listTrimByRange(String key, Long start, Long end) {

    }

    @Override
    public Long sSet(String key, Object... values) {
        return null;
    }

    @Override
    public Long sSize(String key) {
        return null;
    }

    @Override
    public Set<Object> sGet(String key) {
        return null;
    }

    @Override
    public Boolean sHashKey(String key, Object value) {
        return null;
    }

    @Override
    public boolean zSet(String key, Object value, Double score) {
        return false;
    }

    @Override
    public boolean zSet(String key, Object value, Long score) {
        return false;
    }

    @Override
    public Set<Object> zGet(String key) {
        return null;
    }

    @Override
    public Integer append(String key, String value) {
        return null;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public String subString(String key, Long start, Long end) {
        return null;
    }

    @Override
    public Long incr(String key, Long num) {
        return null;
    }

    @Override
    public Double incr(String key, Double num) {
        return null;
    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public Object getSet(String key, Object value) {
        return null;
    }

    @Override
    public void set(String key, Object value, Long timeout) {

    }

    @Override
    public boolean lock(String key) {
        return false;
    }

    @Override
    public boolean lock(String key, Long expireTime) {
        return false;
    }

    @Override
    public boolean isLock(String key) {
        return false;
    }

    @Override
    public void unlock(String key) {

    }
}
