package com.shizijie.beta.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @author shizijie
 * @version 2018-11-02 下午10:12
 */
@SuppressWarnings("unused,unchecked")
public interface RedisService {
    //======================================    Hash    =====================================================

    /**
     * 删除键为key的缓存（hash/set/list/String）
     * @param key key值
     */
    void delete(String key);

    /**
     * 查看哈希表 hashTable中，hashKey是否存在
     * @param hashTable 哈希表名
     * @param hashKey   key值
     * @return  true 存在 false 不存在
     */
    Boolean hashHasKey(String hashTable,String hashKey);

    /**
     * 查询哈希表hashTable中hashKey的值
     * @param hashTable 哈希表名
     * @param hashKey   key值
     * @return key对应的值
     */
    Object hashGet(String hashTable,String hashKey);

    /**
     * 查询哈希表中所有散列值
     * @param hashTable 哈希表名
     * @return  散列值
     */
    Map<Object,Object> hashGetAll(String hashTable);

    /**
     * hashKey值递增，如果不存在，就会创建一个，并把新增后的值返回
     * @param hashTable 哈希表名
     * @param hashKey   key值
     * @param num   递增值
     * @return  新增后值
     */
    Long hashIncr(String hashTable,String hashKey,Long num);

    /**
     * hashKey值递增，如果不存在，就会创建一个，并把新增后的值返回
     * @param hashTable 哈希表名
     * @param hashKey key值
     * @param num 递增值
     * @return  新增后值
     */
    Double hashIncr(String hashTable,String hashKey,Double num);

    /**
     * 向哈希表中放入数据，如果不存在将创建
     * @param hashTable 哈希表名
     * @param hashKey   key值
     * @param value key对应的值
     */
    void hashSet(String hashTable,String hashKey,Object value);

    /**
     * 获取哈希表中所有hashKey
     * @param hashTable 哈希表名
     * @return 哈希表中所有hashKey
     */
    Set<String> hashGetAllHashKey(String hashTable);

    /**
     * 获取哈希表中hashKey数量
     * @param hashTable 哈希表名
     * @return  hashKey数量
     */
    Long hashGetHashMapSize(String hashTable);

    /**
     * 获取哈希表中所有hashKey的value值
     * @param hashTable 哈希表名
     * @return  hashKey的value值
     */
    List<Object> hashGetAllHashKeyValue(String hashTable);

    /**
     * 删除哈希表里一个或多个hashKey
     * @param hashTable 哈希表名
     * @param hashKeys  hashKey
     * @return  删除成功数量
     */
    Long hashDelete(String hashTable,Object... hashKeys);

    //======================================    List    =====================================================

    /**
     * 从右向左压栈
     * @param key key
     * @param value value
     */
    void rPush(String key,Object value);

    /**
     * 从右出栈
     * @param key key
     * @return value
     */
    Object rPop(String key);

    /**
     * 从左向右压栈
     * @param key key
     * @param value value
     */
    void lPush(String key,Object value);

    /**
     * 从左出栈
     * @param key key
     * @return value
     */
    Object lPop(String key);

    /**
     * List长度
     * @param key key
     * @return 长度
     */
    Long listSize(String key);

    /**
     * 查询List中区间内元素，start end为起始位置
     * @param key key
     * @param start start
     * @param end   end
     * @return 区间内元素
     */
    List<Object> listRange(String key,Long start,Long end);

    /**
     * 移除N个值为value，如果没有返回0
     * @param key   key
     * @param count 数量
     * @param value value
     * @return 移除数量
     */
    Long listRemove(String key,Long count,Object value);

    /**
     * 根据索引查询List里的值
     * @param key key
     * @param index index
     * @return value
     */
    Object listGetIndex(String key,Long index);

    /**
     * 根据索引修改List里的值
     * @param key   key
     * @param index index
     * @param value value
     */
    void listSetIndex(String key,Long index,Object value);

    /**
     * 删除除了[start,end]以外的所有元素
     * @param key   key
     * @param start start
     * @param end end
     */
    void listTrimByRange(String key,Long start,Long end);

    //======================================    Set    =====================================================

    /**
     * 将values存入set
     * @param key   key
     * @param values    值，可以是多个
     * @return  成功数量
     */
    Long sSet(String key,Object... values);

    /**
     * 获取set集合大小
     * @param key   key
     * @return set大小
     */
    Long sSize(String key);

    /**
     * 获取set集合
     * @param key key
     * @return set集合
     */
    Set<Object> sGet(String key);

    /**
     * 检查元素是否存在set中
     * @param key key
     * @param value value
     * @return true 存在 false 不存在
     */
    Boolean sHashKey(String key,Object value);

    //======================================    zSet    =====================================================

    /**
     * 有序Set存入，序号score
     * @param key key
     * @param value value
     * @param score 序号
     * @return true 成功 false 失败
     */
    boolean zSet(String key,Object value,Double score);

    /**
     * 有序Set存入，序号score
     * @param key key
     * @param value value
     * @param score 序号
     * @return true 成功 false 失败
     */
    boolean zSet(String key,Object value,Long score);

    /**
     * 获取有序Set
     * @param key   key
     * @return set集合
     */
    Set<Object> zGet(String key);

    //======================================    String    =====================================================

    /**
     * 如果key已经存在并且是一个字符串append会将value加到key的值的末尾
     * 如果key不存在，就像执行set
     * @param key key
     * @param value value
     * @return 追加后value长度
     */
    Integer append(String key,String value);

    /**
     * 获取key的value
     * @param key key
     * @return value
     */
    Object get(String key);

    /**
     * 截取key值区间[start,end]的字符串
     * @param key   key
     * @param start start
     * @param end   end
     * @return 截取字符串
     */
    String subString(String key,Long start,Long end);

    /**
     * key的整数值递增
     * @param key key
     * @param num 递增值
     * @return 递增后值
     */
    Long incr(String key,Long num);

    /**
     * key的整数值递增
     * @param key key
     * @param num 递增值
     * @return 递增后值
     */
    Double incr(String key,Double num);

    /**
     * 设置key的值
     * @param key key
     * @param value value
     */
    void set(String key,Object value);

    /**
     * 设置key的值并返回旧值
     * @param key key
     * @param value value
     * @return 旧值
     */
    Object getSet(String key,Object value);

    /**
     * 设置key的值，过期时间为timeout
     * @param key   key
     * @param value value
     * @param timeout 过期时间
     */
    void set(String key,Object value,Long timeout);

    //======================================    分布式锁    =====================================================

    /**
     * 获取分布式锁 默认死锁过期时间为1S
     * @param key key
     * @return true 成功 false 失败
     */
    boolean lock(String key);

    /**
     * 获取分布式锁，死锁过期时间为expireTime 单位秒
     * @param key key
     * @param expireTime 过期时间
     * @return true 成功 false 失败
     */
    boolean lock(String key,Long expireTime);

    /**
     * 查询是否加锁
     * @param key key
     * @return true 加锁 false 无锁
     */
    boolean isLock(String key);

    /**
     * 解锁
     * @param key key
     */
    void unlock(String key);

    /**
     * 默认过期时间
     */
    Long LOCK_EXPIRE=1L;

    String LOCK_PREFIX="LOCK_";
}
